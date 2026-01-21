package com.turkcell.order_service.messaging.relayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.order_service.messaging.outbox.OutboxMessage;
import com.turkcell.order_service.messaging.outbox.OutboxRepository;
import com.turkcell.order_service.messaging.outbox.OutboxStatus;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class OutboxEventRelayer {
    private final OutboxRepository outboxRepository;
    private final StreamBridge streamBridge;
    private final ObjectMapper objectMapper;

    public OutboxEventRelayer(OutboxRepository outboxRepository, StreamBridge streamBridge, ObjectMapper objectMapper) {
        this.outboxRepository = outboxRepository;
        this.streamBridge = streamBridge;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = 5000)
    public void publishPendingEvents() throws JsonProcessingException {
        System.out.println("Publish pending events çalıştı..");

        List<OutboxMessage> pendingEvents = outboxRepository.findByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);

        for (OutboxMessage pendingEvent : pendingEvents) {
            // Deserialize
            OrderCreatedEvent event = objectMapper.readValue(pendingEvent.getPayloadJson(), OrderCreatedEvent.class);

            Message<OrderCreatedEvent> message = MessageBuilder.withPayload(event).build();

            try {
                boolean isSent = streamBridge.send("orderCreated-out-0", message);
                if (!isSent) {
                    pendingEvent.setRetryCount(pendingEvent.getRetryCount() + 1);
                    if (pendingEvent.getRetryCount() > 5) {
                        pendingEvent.setStatus(OutboxStatus.FAILED);
                    }
                } else {
                    pendingEvent.setStatus(OutboxStatus.SENT);
                }
                pendingEvent.setProcessedAt(OffsetDateTime.now());
                outboxRepository.save(pendingEvent);
            } catch (Exception e) {
                pendingEvent.setRetryCount(pendingEvent.getRetryCount() + 1);
                if (pendingEvent.getRetryCount() > 5) {
                    pendingEvent.setStatus(OutboxStatus.FAILED);
                }
                pendingEvent.setProcessedAt(OffsetDateTime.now());
                outboxRepository.save(pendingEvent);
            }

        }
    }

}

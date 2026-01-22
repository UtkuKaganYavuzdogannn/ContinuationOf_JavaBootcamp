package com.turkcell.order_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.order_service.messaging.outbox.OutboxMessage;
import com.turkcell.order_service.messaging.outbox.OutboxRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public OrdersController(OutboxRepository outboxRepository,ObjectMapper objectMapper){
        this.objectMapper=objectMapper;
        this.outboxRepository=outboxRepository;
    }

    @PostMapping
    public String createOrder(@RequestBody CreateOrderDto dto) throws JsonProcessingException{
            OrderCreatedEvent event = new OrderCreatedEvent(dto.productId());

        OutboxMessage outboxMessage = new OutboxMessage();
        outboxMessage.setAggregateId(UUID.randomUUID()); // normalde db'de oluşan order'ın idsi
        outboxMessage.setAggregateType("Order");
        outboxMessage.setEventId(UUID.randomUUID());
        outboxMessage.setEventType("OrderCreatedEvent");
        outboxMessage.setPayloadJson(objectMapper.writeValueAsString(event));
        outboxRepository.save(outboxMessage);

        return dto.productId();
    }

    record CreateOrderDto(String productId) {
    }

    public record OrderCreatedEvent(String productId) {
    }
    }



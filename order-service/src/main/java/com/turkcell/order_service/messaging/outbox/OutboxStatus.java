package com.turkcell.order_service.messaging.outbox;

public enum OutboxStatus {
    PENDING,
    SENT,
    FAILED
}

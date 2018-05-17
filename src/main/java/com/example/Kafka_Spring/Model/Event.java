package com.example.Kafka_Spring.Model;

import java.util.HashMap;
import java.util.UUID;

public class Event {

    private UUID uuid;

    private Account account;

    private EventType eventType;

    private int offset;

    public Event(EventType eventType){
        this.uuid = UUID.randomUUID();
        this.eventType = eventType;
    }

    public Event(Account account, EventType eventType, int offset) {
        this.uuid = UUID.randomUUID();
        this.account = account;
        this.eventType = eventType;
        this.offset = offset;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}

package com.example.Kafka_Spring.Model;

public enum  EventType {
    CREATE("create"),
    CREDIT("credit"),
    DECREDIT("decredit"),
    SETCREDIT("setcredit"),
    CALLBACK("callback");

    public String type;
    EventType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
}

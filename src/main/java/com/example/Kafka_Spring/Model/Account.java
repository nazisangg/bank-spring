package com.example.Kafka_Spring.Model;

import java.util.HashMap;
import java.util.UUID;

public class Account {
    // common message
    private String name;
    private int balance;
    private int partition;
    private String topic;

    private int count;

    public Account(String name){
        this.name = name;
        this.balance = 0;
        this.count = 0;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Account(String name, int partition, String topic) {
        this.name = name;
        this.balance = 0;
        this.count = 0;
        this.partition = partition;
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        balance = balance;
    }

    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

}

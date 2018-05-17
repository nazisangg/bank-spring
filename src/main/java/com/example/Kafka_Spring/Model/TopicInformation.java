package com.example.Kafka_Spring.Model;

import java.util.HashMap;

public class TopicInformation {

    private String name;

    private HashMap<Integer, Partition> partitionMap;


    public TopicInformation(String name) {
        this.name = name;
        this.partitionMap = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Integer, Partition> getPartitionMap() {
        return partitionMap;
    }

    public void setPartitionMap(HashMap<Integer, Partition> partitionMap) {
        this.partitionMap = partitionMap;
    }
}

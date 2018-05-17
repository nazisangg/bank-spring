package com.example.Kafka_Spring.Model;

public class Partition {

    private int partitionNumber;

    private int maxOffset;

    public Partition(int partitionNumber, int maxOffset) {
        this.partitionNumber = partitionNumber;
        this.maxOffset = maxOffset;
    }

    public int getPartitionNumber() {
        return partitionNumber;
    }

    public void setPartitionNumber(int partitionNumber) {
        this.partitionNumber = partitionNumber;
    }

    public int getMaxoffset() {
        return maxOffset;
    }

    public void setMaxoffset(int maxoffset) {
        this.maxOffset = maxoffset;
    }
}

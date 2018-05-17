package com.example.Kafka_Spring.Consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumerParent<K extends Serializable,V extends Serializable> extends Thread {

    private String topics;

    private K key;

    private boolean interraptable;

    private String bootstrapServers = "127.0.0.1:9092";

    private String key_deseralizer = StringDeserializer.class.getName();

    private String value_deserializer = StringDeserializer.class.getName();

    private String group_id = "default";

    private String enable_auto_commit = "false";

    private String auto_commit_interval_ms = "1000";

    private String auto_offset_reset = "earliest";

    private Properties properties;

    private ArrayList<ConsumerRecord<K,V>> value;

    private org.apache.kafka.clients.consumer.KafkaConsumer<K,V> kafkaConsumer;

    public String getTopics() {
        return topics;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public boolean isInterraptable() {
        return interraptable;
    }

    public void setInterraptable(boolean interraptable) {
        this.interraptable = interraptable;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
        properties.setProperty("bootstrap.servers", bootstrapServers);

    }

    public K getKey() {
        return key;
    }

    public void setValue(ArrayList<ConsumerRecord<K, V>> value) {
        this.value = value;
    }

    public KafkaConsumer<K, V> getKafkaConsumer() {
        return kafkaConsumer;
    }

    public void setKafkaConsumer(KafkaConsumer<K, V> kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }

    public String getKey_deseralizer() {
        return key_deseralizer;
    }

    public void setKey_deseralizer(String key_deseralizer) {
        this.key_deseralizer = key_deseralizer;
        properties.setProperty("key.deseralizer", key_deseralizer);
    }

    public String getValue_deserializer() {
        return value_deserializer;
    }

    public void setValue_deserializer(String value_deserializer) {
        this.value_deserializer = value_deserializer;
        properties.setProperty("value.deserializer", value_deserializer);
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
        properties.setProperty("group.id", group_id);
    }

    public String getEnable_auto_commit() {
        return enable_auto_commit;
    }

    public void setEnable_auto_commit(String enable_auto_commit) {
        this.enable_auto_commit = enable_auto_commit;
        properties.setProperty("enable.auto.commit", enable_auto_commit);
    }

    public String getAuto_commit_interval_ms() {
        return auto_commit_interval_ms;
    }

    public void setAuto_commit_interval_ms(String auto_commit_interval_ms) {
        this.auto_commit_interval_ms = auto_commit_interval_ms;
        properties.setProperty("auto.commit.interval.ms",auto_commit_interval_ms);
    }

    public String getAuto_offset_reset() {
        return auto_offset_reset;
    }

    public void setAuto_offset_reset(String auto_offset_reset) {
        this.auto_offset_reset = auto_offset_reset;
        properties.setProperty("auto.offset.reset", auto_offset_reset);
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public ArrayList<ConsumerRecord<K, V>> getValue() {
        return value;
    }

    public KafkaConsumerParent(String topics, boolean interraptable) {
        //super(topics,interraptable);
        this.topics = topics;
        this.interraptable = interraptable;
        this.properties = new Properties();

        this.value = new ArrayList<>();
        Properties properties = new Properties();

        // kafka bootstrap server

        properties.setProperty("bootstrap.servers", bootstrapServers);

        properties.setProperty("key.deserializer", key_deseralizer);

        properties.setProperty("value.deserializer", value_deserializer);

        properties.setProperty("group.id", group_id);

        properties.setProperty("enable.auto.commit", enable_auto_commit);

        properties.setProperty("auto.commit.interval.ms",auto_commit_interval_ms);

        properties.setProperty("auto.offset.reset", auto_offset_reset);

        this.kafkaConsumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(properties);

    }




    public void run(){
        kafkaConsumer.subscribe(Arrays.asList(topics));
        //KafkaConsumerParent.seek(new TopicPartition(topics, 0), 666);
        while (true) {
            //KafkaConsumerParent.seek(new TopicPartition(topics, 0), 666);
            ConsumerRecords<K, V> consumerRecords = this.kafkaConsumer.poll(1000);
            for (ConsumerRecord<K, V> consumerRecord : consumerRecords) {


                    System.out.println("value: " + consumerRecord.value());;
                    this.value.add(consumerRecord);
//                }
            }
        }

    }

//    public static void main(String[] args) {
//        KafkaConsumerParent KafkaConsumerParent = new KafkaConsumerParent("bank-balance-exactly-once", false);
//        KafkaConsumerParent.run();
//    }

}

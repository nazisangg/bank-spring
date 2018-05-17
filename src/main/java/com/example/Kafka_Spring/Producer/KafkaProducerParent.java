package com.example.Kafka_Spring.Producer;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Instant;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class KafkaProducerParent {

    private Properties properties;

    private Producer<String, String> producer;

    private String topic;


    public KafkaProducerParent(String topic){
        properties = new Properties();
        this.setConfig();
        producer = new KafkaProducer<>(properties);
        this.topic = topic;
    }

    public void setConfig(){

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "3");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

    }

    public void setProperties(Properties properties){
        this.properties = properties;
    }

    public void sendMessage(ProducerRecord<String, String> producerRecord){
        try {
            producer.send(newRandomTransaction("john"));
            System.out.println("success");
        }catch (Exception e){
            System.out.println("Producer break by some reason"+ e.getLocalizedMessage());
            producer.close();
        }
    }

    public Producer getProducer(){
        return this.producer;
    }



    public void closeProducer(){
        this.producer.close();
    }

    public ProducerRecord<String, String> getProducerRecordFromString(String key, String record){
            return new ProducerRecord<String, String>
                    (this.topic, key, record);
    }


    public static ProducerRecord<String, String> newRandomTransaction(String name) {
        // creates an empty json {}
        ObjectNode transaction = JsonNodeFactory.instance.objectNode();

        // { "amount" : 46 } (46 is a random number between 0 and 100 excluded)
        Integer amount = ThreadLocalRandom.current().nextInt(0, 100);

        // Instant.now() is to get the current time using Java 8
        Instant now = Instant.now();

        // we write the data to the json document
        transaction.put("name", name);
        transaction.put("amount", amount);
        transaction.put("time", now.toString());
        return new ProducerRecord<>("bank-transactions", name, transaction.toString());
    }

}


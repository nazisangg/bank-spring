package com.example.Kafka_Spring.CallBack;


import com.example.Kafka_Spring.Consumer.KafkaConsumerParent;
import com.example.Kafka_Spring.Model.EventType;
import com.example.Kafka_Spring.Producer.KafkaProducerParent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RollBackProducer_state {
    private int i;


    public RollBackProducer_state() {
        this.i = 0;
    }

    public Producer<String, String> getProducer() {
        Properties properties = new Properties();

        // kafka bootstrap server
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // producer acks
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all"); // strongest producing guarantee
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "3");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");
        // leverage idempotent producer from Kafka 0.11 !
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true"); // ensure we don't push duplicates

        Producer<String, String> producer = new KafkaProducer<>(properties);

        return producer;
    }

    public void patchSend(Producer<String, String> producer) throws Exception{
        String uuid = UUID.randomUUID().toString();
        while (i<50) {
            System.out.println("Producing batch: " + i);
            try {
                producer.send(newRandomTransaction("john", uuid));
                Thread.sleep(100);
                producer.send(newRandomTransaction("stephane", uuid));
                Thread.sleep(100);
                producer.send(newRandomTransaction("alice", uuid));
                Thread.sleep(100);
                i += 1;
            } catch (InterruptedException | IOException e) {
                break;
            }
        }
        producer.close();
    }

    public KafkaConsumer<String, String> startConsumer(String topic){
        KafkaConsumerParent<String, String> kafkaConsumerParent
                = new KafkaConsumerParent(topic, false);

        String uuid = UUID.randomUUID().toString();
        kafkaConsumerParent.setGroup_id(uuid);
        KafkaConsumer<String, String> kafkaConsumer = kafkaConsumerParent.getKafkaConsumer();
        kafkaConsumer.subscribe(Arrays.asList(topic));

        return kafkaConsumer;

    }


    public int findPreviousState(KafkaConsumer<String, String> kafkaConsumer, int targetCount, String name) throws Exception{

        Boolean didNotFind = true;
        int state = 0;
        while (didNotFind){
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(1000);
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode actualObj = mapper.readTree(consumerRecord.value());
                try {
                    if (consumerRecord.key().equals(name) && actualObj.get("count").asInt() == 30) {
                        didNotFind = false;
                        state = actualObj.get("balance").asInt();
                    }
                }catch (NullPointerException e){
                    System.out.println("in catch");
                }
            }
        }

        return state;


    }


    public ProducerRecord<String, String> trasactionBuilder(String name, String topic, EventType eventType, int targetNumeber )throws Exception{

        ObjectNode transaction = JsonNodeFactory.instance.objectNode();
        Integer amount = ThreadLocalRandom.current().nextInt(0, 100);
        transaction.put("name", name);
        transaction.put("type", eventType.getType());
        transaction.put("amount", amount);

        Instant now = Instant.now();
        transaction.put("time", now.toString());


        if(eventType.getType().equals("callback")) {
            KafkaConsumer<String, String> kafkaConsumer = startConsumer(topic);
            Boolean didNotFind = true;
            int state = this.findPreviousState(kafkaConsumer,targetNumeber, name);
            transaction.put("rollbackto", state);
            kafkaConsumer.close();
        }


        return new ProducerRecord<>("rollbackinput", name, transaction.toString());



    }

    public ProducerRecord<String, String> newRandomTransaction(String name, String topic) throws Exception {

        List<EventType> eventList = new ArrayList();

        eventList.add(EventType.CREDIT);
        eventList.add(EventType.DECREDIT);

        EventType eventType = eventList.get(ThreadLocalRandom.current().nextInt(0,3));


        return this.trasactionBuilder(name,topic, eventType,0);
    }


}


package com.example.Kafka_Spring;

import com.example.Kafka_Spring.KAFKA.DataProducerDemo;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@SpringBootApplication
@RestController
public class KafkaSpringApplication {

//    @Autowired
//    private KafkaTemplate<String,String> kafkaTemplate;

//	@KafkaListener(topics = "bank-balance-exactly-once", groupId = "group-id")
//	public void kafkaListener(ConsumerRecord<String,JSONPObject> message){

//        System.out.println("messgae: "+ message);
//        System.out.println("OFFSET: " + message.offset());
//        System.out.println("TIMESTAMP: " + message.timestamp());
//	}

//    @KafkaListener(topics = "bank-balance-application-bank-balance-agg-changelog", groupId = "group-id")
//    public void kafkaListener(ConsumerRecord<String,JSONPObject> message){
//        System.out.println("aggmessgae: "+ message);
//    }

    private String uuid = UUID.randomUUID().toString();

    @KafkaListener(topics = "rollbackoutput", groupId = "TestGroup")
    public void kafkaListener_2(ConsumerRecord<String,JSONPObject> message){
        System.out.println(message);
    }




//    @KafkaListener(topics = "bank-transactions", groupId = "group-id")
//    public void kafkaListener(ConsumerRecord<String,JSONPObject> message){
//        System.out.println("here");
//        System.out.println("messgae: "+ message);
//        System.out.println("OFFSET: " + message.offset());
//        System.out.println("TIMESTAMP: " + message.timestamp());
//    }

//    @KafkaListener( groupId = "group-id",
//            topicPartitions =@TopicPartition( topic = "bank-balance-exactly-once",
//            partitionOffsets = @PartitionOffset(partition = "0",initialOffset = "1")))
//    public void kafkaListener(ConsumerRecord<String,JSONPObject> message){
//        System.out.println(message);
//    }



	public static void main(String[] args) {
		SpringApplication.run(KafkaSpringApplication.class, args);
	}
}

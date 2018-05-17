package com.example.Kafka_Spring.KAFKA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private DataProducerDemo data;

    @RequestMapping(value = "/produce", method = RequestMethod.GET)
    public String produceKafkaMessage(){
        data.start();
        return "success";
    }


    @RequestMapping(value = "/produce/add", method = RequestMethod.GET)
    public String addMoney(){
        data.start();
        return "success";
    }


}

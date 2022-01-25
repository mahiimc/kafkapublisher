package com.publisher.root.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private static final Logger log = LoggerFactory.getLogger(Producer.class);
    private static final String topic = "test";

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public  void sendMessage(String message){
        log.info("Producing Message : {}",message);
        this.kafkaTemplate.send(topic,message);

    }
}

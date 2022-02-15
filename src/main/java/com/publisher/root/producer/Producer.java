package com.publisher.root.producer;

import com.publisher.root.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class Producer {

    private static final Logger log = LoggerFactory.getLogger(Producer.class);
    private static final String topic = "navyatest";

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;
    //HashMap<String,Object> message
    public  void sendMessage(HashMap<String,Object> message){
        log.info("Producing Message : {}",message);
        try {
            this.kafkaTemplate.send(topic,message.toString());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }


    }
}

package com.publisher.root.controller;

import com.publisher.root.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publish")
public class ProducerController {
    @Autowired
    private Producer producer;

    @PostMapping("/{message}")
    public String publishMessage(@PathVariable("message")  String message){
        try {
            this.producer.sendMessage(message);
            return  "Published.";
        }
        catch (Exception ex){
            return ex.getMessage();
        }

    }
}

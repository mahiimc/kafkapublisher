package com.publisher.root.controller;


import com.publisher.root.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/publish")
public class ProducerController {
    @Autowired
    private Producer producer;

    public  volatile  HashMap<String, Object> hm = new HashMap<>();

    @GetMapping("/{message}")
    public void publishMessage(@PathVariable("message")  String message){

        try
        {
            CompletableFuture.runAsync(()->{
                RestTemplate restTemplate = new RestTemplate();
                synchronized (hm) {
                    try {
                        ResponseEntity<HashMap> responseData = restTemplate.exchange("https://reqres.in/api/users/3", HttpMethod.GET, null, HashMap.class);
                       // Response response = new ObjectMapper().convertValue(responseData.getBody().get("data"), Response.class);
                        hm.put("Response", responseData.getBody().get("data"));
                        System.out.println(hm);
                        hm.notify();
                        System.out.println("API Thread Release lock on HashMap object");
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            });
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        try {
            CompletableFuture.runAsync(() -> {
                synchronized (hm) {
                    System.out.println("Kafka Thread Started..");

                    try {
                        System.out.println("Kafka Thread waiting...");
                        hm.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Kafka Thread Resumed and Producing Message.");
                    this.producer.sendMessage(hm);
                }
            });

        }
        catch (Exception ex){

        }

    }
}

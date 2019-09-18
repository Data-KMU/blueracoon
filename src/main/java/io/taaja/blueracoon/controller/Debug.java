package io.taaja.blueracoon.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.taaja.blueracoon.config.Constants;
import io.taaja.blueracoon.services.DeDroneResponseBodyProcessorService;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@ConditionalOnExpression("${app.enableDebugController}")
@RequestMapping("/debug")
public class Debug {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private DeDroneResponseBodyProcessorService deDroneResponseBodyProcessorService;

    @GetMapping("/ping")
    public Object ping(){
        return ResponseEntity.ok("pong");
    }

    @GetMapping("/fail")
    public Object fail(){
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/produce")
    public Object produce(){
        String message = "vehicle";
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(Constants.TOPIC_VEHICLE, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + message + "] due to : " + ex.getMessage());
            }
        });

        return ResponseEntity.ok("produced");

    }

    @PostMapping("/jsonTest")
    public ResponseEntity jsonTest(
            @RequestBody String requestBody
    ) {
        try {
            deDroneResponseBodyProcessorService.processResponceBody(requestBody);
        } catch (IOException e) {
            e.printStackTrace();
            return Constants.RESPONSE_BAD_REQUEST;
        }

        return Constants.RESPONSE_OK;
    }



}
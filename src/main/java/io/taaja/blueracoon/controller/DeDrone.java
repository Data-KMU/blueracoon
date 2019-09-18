package io.taaja.blueracoon.controller;

import io.taaja.blueracoon.config.Constants;
import io.taaja.blueracoon.services.DeDroneResponseBodyProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_PREFIX + "/dedrone")
public class DeDrone {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private DeDroneResponseBodyProcessorService deDroneResponseBodyProcessorService;

    @PostMapping("/status")
    public ResponseEntity getFullTwin(){

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

        return Constants.RESPONSE_OK;
    }


}
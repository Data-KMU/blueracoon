package io.taaja.blueracoon.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.taaja.blueracoon.config.Constants;
import io.taaja.blueracoon.model.SensorStatus;
import jdk.jshell.spi.ExecutionControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class DeDroneResponseBodyProcessorService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    Logger logger = LoggerFactory.getLogger(DeDroneResponseBodyProcessorService.class);
    ObjectMapper mapper = new ObjectMapper();

    public Object processResponceBody(String requestBody) throws IOException {

        JsonNode node = null;

        node = mapper.readTree(requestBody);
        switch (node.get("version").intValue()){
            case 3:
                return processV3Body(node);
            default:
                throw new UnsupportedOperationException("DeDrone Version " + node.get("version").intValue() + " not supported.");
        }

    }

    private Object processV3Body(JsonNode node) throws IOException {

        switch (node.get("channel").asText()){
            case "alert":

                break;
            case "status":
                JsonNode allSensorNode = node.get("data").get("sensors");

                JsonNode sensorNode;
                Iterator<String> sensorNameIterator = allSensorNode.fieldNames();

                while( sensorNameIterator.hasNext() ){
                    String sensorId = sensorNameIterator.next();
                    sensorNode = allSensorNode.findValue(sensorId);
                    SensorStatus status = mapper.readValue(mapper.writeValueAsString(((JsonNode)sensorNode)), SensorStatus.class);
                    status.setId(sensorId);
                    System.out.println(mapper.writeValueAsString(status));
                    kafkaTemplate.send(Constants.TOPIC_VEHICLE, mapper.writeValueAsString(status));
                }

                System.out.println("test");
                break;
            default:
                throw new UnsupportedOperationException("DeDrone Status " + node.get("channel").asText() + " not supported.");
        }
        return null;

    }
}

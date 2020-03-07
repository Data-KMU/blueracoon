package io.taaja.blueracoon;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import io.taaja.blueracoon.model.Coordinates;
import io.taaja.blueracoon.model.SensorStatus;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;

/*
Die Klasse konsumiert eingehende Daten von dem Kafka Topic "sensors"
The result is pushed to the "sensor-data-stream" stream which is an in-memory stream.
 */

@ApplicationScoped
public class DeDroneResponseBodyProcessorService {

    ObjectMapper mapper = new ObjectMapper();


    @Incoming("sensors")
    @Outgoing("sensor-data-stream")
    @Broadcast
    public Object processResponseBody(String requestBody) throws IOException {
        JsonNode node = null;
        node = mapper.readTree(requestBody);
        if (node.get("version").intValue() == 3) {
            return processBodyV3(node);
        } else {
            throw new UnsupportedOperationException("Dedrone Version " + node.get("version").intValue() + " not supported.");
        }

    }


    public SensorStatus processBodyV3(JsonNode node) {
        if (node.get("channel").asText().equals("alert")) {
            //work in progress
            return new SensorStatus(node.get("id").asText(), node.get("detectionType").asText(), node.get("channel").asText(),
                    node.get("protocol").asText(), node.get("version").intValue(), new Coordinates(node.get("coordinates").get("longitude").asDouble(),
                    node.get("coordinates").get("latitude").asDouble(), node.get("coordinates").get("heading").asDouble()));
        }
        return null;
    }

}

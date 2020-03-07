package io.taaja.blueracoon.jackson;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import io.taaja.blueracoon.model.SensorStatus;

public class SensorDeserializer extends ObjectMapperDeserializer<SensorStatus> {
    public SensorDeserializer(){
        // pass the class to the parent.
        super(SensorStatus.class);
    }
}
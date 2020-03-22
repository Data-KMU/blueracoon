package io.taaja.blueracoon.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties()
public class AffectedSensors {
    private Sensor sensor;
}
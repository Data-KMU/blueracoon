package io.taaja.blueracoon.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties({"directions", "zones"})
public class Daten {
    private String alertId;
    private String alertState;
    private ArrayList<Detection> detections;
    private AffectedSensor affectedSensor;
    private String version;
}
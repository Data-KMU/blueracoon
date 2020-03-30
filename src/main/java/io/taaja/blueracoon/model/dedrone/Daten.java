package io.taaja.blueracoon.model.dedrone;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;

import lombok.Data;

@Data
@JsonIgnoreProperties({"directions", "zones"})
public class Daten {
    private String alertId;
    private String alertState;
    private ArrayList<Detection> detections;
    private AffectedSensor affectedSensor;
    private String version;
}
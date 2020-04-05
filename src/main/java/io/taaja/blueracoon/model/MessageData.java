package io.taaja.blueracoon.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MessageData {

    private String alertId;
    private AlertStateType alertState;
    private List<Detection> detections;
    private Map<String, List<Direction>> directions;
    private Map<String, AffectedSensor> affectedSensors;
    private Map<String, AffectedSensor> sensors;
    private String version;
    private Object zones;
}

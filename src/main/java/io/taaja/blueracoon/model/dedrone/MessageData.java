package io.taaja.blueracoon.model.dedrone;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties({"zones"})
public class MessageData {

    private String alertId;
    private AlertStateType alertState;
    private List<Detection> detections;
    private Map<String, List<Direction>> directions;
    private Map<String, AffectedSensor> affectedSensors;
    private String version;


}

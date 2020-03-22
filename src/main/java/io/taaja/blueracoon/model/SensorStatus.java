package io.taaja.blueracoon.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorStatus {
    private String channel;
    private int version;
    private Daten data;
}

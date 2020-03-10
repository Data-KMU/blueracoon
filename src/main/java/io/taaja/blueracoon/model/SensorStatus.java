package io.taaja.blueracoon.model;

import lombok.Data;

@Data
public class SensorStatus {
    private String id;
    private String detectionType;
    private String channel;
    private String protocol;
    private int version;
    private Coordinates coordinates;


}

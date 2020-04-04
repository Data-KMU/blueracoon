package io.taaja.blueracoon.model;

import lombok.Data;

@Data
public class Identification {
    private DetectionType detectionType;
    private String manufacturer;
    private String model;
    private String protocol;
}

package io.taaja.blueracoon.model.dedrone;

import lombok.Data;

@Data
public class Identification {
    private DetectionType detectionType;
    private String manufacturer;
    private String protocol;
}

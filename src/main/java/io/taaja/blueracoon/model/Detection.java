package io.taaja.blueracoon.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
public class Detection {
    private DetectionType detectionType;
    private int level;
    private List<String> sensors;
    private Identification identification;
    private int detectionId;
    private Object positions;
}
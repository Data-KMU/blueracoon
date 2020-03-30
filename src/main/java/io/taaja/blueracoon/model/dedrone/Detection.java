package io.taaja.blueracoon.model.dedrone;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties({"positions"})
public class Detection {
    private DetectionType detectionType;
    private int level;
    private List<String> sensors;
    private Identification identification;
    private int detectionId;
}
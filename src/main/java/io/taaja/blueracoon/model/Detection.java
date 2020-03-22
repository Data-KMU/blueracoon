package io.taaja.blueracoon.model;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties({"positions"})
public class Detection {
    private String detectionType;
    private int level;
    private ArrayList<String> sensors;
    private Identification identification;
    private String detectionId;
}
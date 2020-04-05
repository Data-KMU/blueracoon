package io.taaja.blueracoon.model;

import io.taaja.models.generic.Coordinates;
import lombok.Data;

import java.util.List;

@Data
public class Detection {
    private DetectionType detectionType;
    private int level;
    private List<Coordinates> positions;
    private List<String> sensors;
    private Identification identification;
    private int detectionId;
    private PositionStateType positionState;
    private float heading;

}
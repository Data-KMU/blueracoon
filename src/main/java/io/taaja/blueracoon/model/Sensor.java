package io.taaja.blueracoon.model;
import lombok.Data;

@Data
public class Sensor {
    private String type;
    private Coordinates coordinates;
    private String heading;
}
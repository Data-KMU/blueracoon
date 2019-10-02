package io.taaja.blueracoon.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(includeFieldNames=true)
public class SensorStatus {
    private String id;
    private String type;
    private Double heading;
    private Coordinates coordinates;
}

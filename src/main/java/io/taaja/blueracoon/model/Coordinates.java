package io.taaja.blueracoon.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(includeFieldNames=true)
public class Coordinates {
    private double latitude;
    private double longitude;
    private double altitude;
}

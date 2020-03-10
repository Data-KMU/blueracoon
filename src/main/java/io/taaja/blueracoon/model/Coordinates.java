package io.taaja.blueracoon.model;

import lombok.Data;

import java.util.Date;

@Data
public class Coordinates {
    private double longitude;
    private double latitude;
    private double heading;
    private double height;
    private Date created = new Date();

}

package io.taaja.blueracoon.model;

import lombok.Data;

@Data
public class Direction {
    private float direction;
    private float directionError;
    private Identification identification;

}
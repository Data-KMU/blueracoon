package io.taaja.blueracoon.model;

import io.taaja.models.generic.Coordinates;
import lombok.Data;

@Data
public class AffectedSensor {
    private ChannelType type;
    private Coordinates coordinates;
    private float heading;
}
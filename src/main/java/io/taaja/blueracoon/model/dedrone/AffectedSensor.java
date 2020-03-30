package io.taaja.blueracoon.model.dedrone;

import lombok.Data;

@Data
public class AffectedSensor {
    private ChannelType type;
    private Coordinates coordinates;
    private int heading;
}
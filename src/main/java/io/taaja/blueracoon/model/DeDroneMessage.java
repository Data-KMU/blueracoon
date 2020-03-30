package io.taaja.blueracoon.model;

import lombok.Data;

@Data
public class DeDroneMessage {

    private MessageChannel channel;
    private int version;
    private MessageData data;


}

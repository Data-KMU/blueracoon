package io.taaja.blueracoon.model.dedrone;

import lombok.Data;

@Data
public class DeDroneMessage {

    private MessageChannel channel;
    private int version;
    private MessageData data;


}

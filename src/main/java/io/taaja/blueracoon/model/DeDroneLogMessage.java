package io.taaja.blueracoon.model;

import lombok.Data;
import org.mongojack.Id;

import java.util.Date;
import java.util.UUID;

@Data
public class DeDroneLogMessage {

    @Id
    private String id;
    private DeDroneMessage originalDeDroneMessage;
    private Date created;

    public DeDroneLogMessage(){
        this.id = UUID.randomUUID().toString();
        this.created = new Date();
    }

    public DeDroneLogMessage(DeDroneMessage deDroneMessage){
        this.setOriginalDeDroneMessage(deDroneMessage);
    }



}

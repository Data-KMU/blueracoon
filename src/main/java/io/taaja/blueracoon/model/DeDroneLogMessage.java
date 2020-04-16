package io.taaja.blueracoon.model;

import lombok.Data;
import org.mongojack.Id;

import java.util.Date;
import java.util.UUID;

@Data
public class DeDroneLogMessage {

    @Id
    private String _id;
    private Object originalDeDroneMessage;
    private Date created;
    private String tag;

    public DeDroneLogMessage(Object deDroneMessage, String tag){
        this();
        this.setTag(tag);
        this.setOriginalDeDroneMessage(deDroneMessage);
    }

    public DeDroneLogMessage(){
        this.set_id(UUID.randomUUID().toString());
        this.setCreated(new Date());
    }

}

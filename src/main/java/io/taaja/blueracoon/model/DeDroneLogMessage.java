package io.taaja.blueracoon.model;

import lombok.Data;
import org.mongojack.Id;

import java.util.Date;
import java.util.UUID;

@Data
public class DeDroneLogMessage {

    @Id
    private String _id;
    private String originalDeDroneMessage;
    private Object parsedDeDroneMessage;
    private Date created;
    private String tag;

    public DeDroneLogMessage(String originalDeDroneMessage,  Object parsedDeDroneMessage, String tag){
        this();
        this.setTag(tag);
        this.setParsedDeDroneMessage(parsedDeDroneMessage);
        this.setOriginalDeDroneMessage(originalDeDroneMessage);
    }

    public DeDroneLogMessage(){
        this.set_id(UUID.randomUUID().toString());
        this.setCreated(new Date());
    }

}

package io.taaja.blueracoon;

import com.mongodb.client.FindIterable;
import io.taaja.AbstractRepository;
import io.taaja.blueracoon.model.DeDroneLogMessage;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class DeDroneLogRepository  extends AbstractRepository<Object> {


    public DeDroneLogRepository() {
        super("deDroneLog", Object.class);
    }

    public void insertOne(Object deDroneMessage, String tag){
        this.insertOne(new DeDroneLogMessage(deDroneMessage, tag));
    }

    public FindIterable<Object> getLogs(){
        return this.getCollection().find();
    }

}

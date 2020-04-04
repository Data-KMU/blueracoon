package io.taaja.blueracoon;

import com.mongodb.client.MongoClient;
import io.quarkus.runtime.StartupEvent;
import io.taaja.blueracoon.model.DeDroneLogMessage;
import io.taaja.blueracoon.model.DeDroneMessage;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.mongojack.JacksonMongoCollection;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;


@ApplicationScoped
public class DeDroneLogRepository {

    @Inject
    public MongoClient mongoClient;

    @ConfigProperty(name = "app.database")
    public String database;

    private JacksonMongoCollection<Object> deDroneLogMessageJacksonMongoCollection;

    void onStart(@Observes StartupEvent ev) {
        deDroneLogMessageJacksonMongoCollection = JacksonMongoCollection
                .builder()
                .build(this.mongoClient, this.database, "deDroneLog", Object.class);
    }

    public void insertOne(Object deDroneMessage){
        this.deDroneLogMessageJacksonMongoCollection.insertOne(deDroneMessage);
    }
}
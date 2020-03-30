package io.taaja.blueracoon;

// import io.taaja.blueracoon.kafkaio.ProducerService;
import io.taaja.blueracoon.kafkaio.ProducerService;
import io.taaja.blueracoon.model.DeDroneMessage;
import io.taaja.models.generic.Coordinates;
import io.taaja.models.kafka.update.PartialUpdate;
import io.taaja.models.kafka.update.actuator.PositionUpdate;
import lombok.SneakyThrows;
import lombok.extern.jbosslog.JBossLog;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/*
A simple resource retrieving the in-memory "sensor-data-stream" and sending the items as server-sent events.
 */

@Path("/dedrone")
@JBossLog
public class DeDroneResource {

     @Inject
     ProducerService producerService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sensorServer(DeDroneMessage deDroneMessage){

        producerService.publishCoordinates(
                new PositionUpdate(
                        deDroneMessage.getData().getAffectedSensors().entrySet().iterator().next().getValue().getCoordinates()
        ));

        return Response.ok().build();
    }


    @SneakyThrows
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String test(){


        PartialUpdate partialUpdate = new PartialUpdate();


        Coordinates coordinates = new Coordinates();
        coordinates.setAltitude(123);
        coordinates.setLatitude(456);
        coordinates.setLongitude(789);


        PositionUpdate positionUpdate = new PositionUpdate();
        positionUpdate.setPosition(coordinates);
        //partialUpdate.getActuators().put(UUID.randomUUID().toString(), coordinates);
        partialUpdate.getActuators().put(UUID.randomUUID().toString(), positionUpdate);

        return partialUpdate.toString();

    }
}

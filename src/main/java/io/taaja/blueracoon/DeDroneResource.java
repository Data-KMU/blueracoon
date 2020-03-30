package io.taaja.blueracoon;

// import io.taaja.blueracoon.kafkaio.ProducerService;
import io.taaja.blueracoon.kafkaio.ProducerService;
import io.taaja.blueracoon.model.dedrone.DeDroneMessage;
import io.taaja.blueracoon.model.dedrone.Coordinates;
import lombok.extern.jbosslog.JBossLog;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

        deDroneMessage.toString();



        return Response.ok().build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Coordinates test(){

        Coordinates coordinates = new Coordinates();
        coordinates.setAltitude(123);
        coordinates.setLatitude(456);
        coordinates.setLongitude(789);
        producerService.publishCoordinates(coordinates);
        return coordinates;

    }
}

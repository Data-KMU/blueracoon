package io.taaja.blueracoon;

import io.quarkus.scheduler.Scheduled;
// import io.taaja.blueracoon.kafkaio.ProducerService;
import io.taaja.blueracoon.kafkaio.ProducerService;
import io.taaja.blueracoon.model.Coordinates;
import io.taaja.blueracoon.model.SensorStatus;
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
    public Response sensorServer(SensorStatus sensorStatus){
        System.out.println(sensorStatus.toString());
        // System.out.println(sensorStatus.getData().getAffectedSensors().getSensor().getCoordinates());
        // producerService.publishCoordinates(sensorStatus.getCoordinates());

        return Response.ok().build();
    }


    /**
     *  https://www.freeformatter.com/cron-expression-generator-quartz.html
     */
    @PATCH
    @Scheduled(cron = "0/15 * * ? * * *")
    public void generateData(){
//        producerService.publishCoordinates(
//                test().getCoordinates()
//        );
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

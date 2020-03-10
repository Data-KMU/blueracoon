package io.taaja.blueracoon;

import io.smallrye.reactive.messaging.annotations.Channel;
import io.taaja.blueracoon.model.SensorStatus;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/*
A simple resource retrieving the in-memory "sensor-data-stream" and sending the items as server-sent events.
 */

@Path("/dedrone")
public class DeDroneResource {


    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sensorServer(SensorStatus sensorStatus){

        //send to kafka

        return Response.ok().build();
    }



    @Inject
    @Channel("sensor-data-stream") Publisher<SensorStatus> sensors;

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Publisher<SensorStatus> stream() {
        return sensors;
    }
}

package io.taaja.blueracoon;

// import io.taaja.blueracoon.kafkaio.ProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.StartupEvent;
import io.taaja.blueracoon.kafkaio.ProducerService;
import io.taaja.blueracoon.model.DeDroneMessage;
import io.taaja.models.generic.Coordinates;
import io.taaja.models.spatial.data.update.PartialUpdate;
import io.taaja.models.spatial.data.update.actuator.PositionUpdate;
import lombok.SneakyThrows;
import lombok.extern.jbosslog.JBossLog;
import org.checkerframework.checker.index.qual.IndexFor;
import org.mongojack.JacksonMongoCollection;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@JBossLog
@Path("/v1/dedrone")
public class DeDroneResource {

    @Inject
    ProducerService producerService;

    @Inject
    DeDroneLogRepository deDroneLogRepository;
    private ObjectMapper objectMapper;


    void onStart(@Observes StartupEvent ev) {
        this.objectMapper = new ObjectMapper();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sensorServer(DeDroneMessage deDroneMessage){

        //deDroneMessage welche Drohnen ID?
        String vehicleId = this.getVehicleIdFromDeDroneMessage(deDroneMessage);

        //todo: rework
        Coordinates fromDeDroneMessage = deDroneMessage.getCoordinates();

        if(fromDeDroneMessage == null) {
            log.info("DeDrone Message without Position was skipped");
        }else{
            log.info("Publish DeDrone Coordinates");
            producerService.publishCoordinatesFromVehicle(
                vehicleId,
                fromDeDroneMessage
            );

        }

        return Response.ok().build();
    }

    @POST
    @Path("/log")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sensorServerWithLog(Object rawDeDroneMessage){

        //log message
        this.deDroneLogRepository.insertOne(rawDeDroneMessage);

        DeDroneMessage deDroneMessage = this.objectMapper.convertValue(rawDeDroneMessage, DeDroneMessage.class);

        return this.sensorServer(deDroneMessage);
    }

    private String getVehicleIdFromDeDroneMessage(DeDroneMessage deDroneMessage) {
        //todo
        //return default id
        return "4c09c738-7a20-4eb6-8b85-1ca13c6453d1";
    }

}

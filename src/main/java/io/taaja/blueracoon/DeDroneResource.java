package io.taaja.blueracoon;

// import io.taaja.blueracoon.kafkaio.ProducerService;
import io.taaja.blueracoon.kafkaio.ProducerService;
import io.taaja.blueracoon.model.DeDroneMessage;
import io.taaja.models.generic.Coordinates;
import io.taaja.models.spatial.data.update.PartialUpdate;
import io.taaja.models.spatial.data.update.actuator.PositionUpdate;
import lombok.SneakyThrows;
import lombok.extern.jbosslog.JBossLog;
import org.checkerframework.checker.index.qual.IndexFor;

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sensorServer(DeDroneMessage deDroneMessage){

        //deDroneMessage welche Drohnen ID?
        String vehicleId = this.getVehicleIdFromDeDroneMessage(deDroneMessage);

        producerService.publishCoordinatesFromVehicle(
            vehicleId,
            deDroneMessage.getData().getAffectedSensors().entrySet().iterator().next().getValue().getCoordinates()
        );

        return Response.ok().build();
    }

    private String getVehicleIdFromDeDroneMessage(DeDroneMessage deDroneMessage) {
        //todo
        //return default id
        return "4c09c738-7a20-4eb6-8b85-1ca13c6453d1";
    }

    @POST
    @Path("/log")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sensorServerWithLog(DeDroneMessage deDroneMessage){

        //log message
        log.info("new message:  " + deDroneMessage.toString());
        this.deDroneLogRepository.insertOne(deDroneMessage);

        return this.sensorServer(deDroneMessage);
    }

}

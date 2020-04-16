package io.taaja.blueracoon;

// import io.taaja.blueracoon.kafkaio.ProducerService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import io.quarkus.runtime.StartupEvent;
import io.taaja.blueracoon.kafkaio.ProducerService;
import io.taaja.blueracoon.model.DeDroneLogMessage;
import io.taaja.blueracoon.model.DeDroneMessage;
import io.taaja.models.generic.Coordinates;
import lombok.extern.jbosslog.JBossLog;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Safes the deDrone message in the log DB
     *
     * @param rawDeDroneMessage
     * @param tag
     * @return
     */
    @POST
    @Path("/log")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sensorServerWithLog(Object rawDeDroneMessage, @QueryParam("tag") String tag){

        //log message
        this.deDroneLogRepository.insertOne(rawDeDroneMessage, tag);

        DeDroneMessage deDroneMessage = this.objectMapper.convertValue(rawDeDroneMessage, DeDroneMessage.class);

        return this.sensorServer(deDroneMessage);
    }

    private String getVehicleIdFromDeDroneMessage(DeDroneMessage deDroneMessage) {
        //todo
        //return default id
        return "4c09c738-7a20-4eb6-8b85-1ca13c6453d1";
    }


    /**
     * Iterate thru logs an check if the DeDrone Message can be parsed.
     * Use to evaluate the model
     * @return
     */
    @PATCH
    @Path("/log")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> checkLogs(){
        FindIterable<Object> logs = this.deDroneLogRepository.getLogs();
        List<String> res = new ArrayList<>();
        for (Object currentLog : logs){
            try {
                //check if DeDroneLogMessage
                try{
                    DeDroneLogMessage deDroneLogMessage = this.objectMapper.convertValue(currentLog, DeDroneLogMessage.class);
                    currentLog = deDroneLogMessage.getOriginalDeDroneMessage();
                }catch (IllegalArgumentException iae){}

                this.objectMapper.convertValue(currentLog, DeDroneMessage.class);

            }catch (Exception e){
                String msg = "can't parse message because " + e.getMessage();
                res.add(msg);
                log.error(msg, e);
            }
        }
        return res;
    }

    /**
     * returns the a log.
     * @param id
     * @return  {@link DeDroneLogMessage} or {@link DeDroneMessage}
     */
    @GET
    @Path("/log/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getLog(@PathParam("id") String id){
        return this.deDroneLogRepository.getLog(id);
    }


}

package io.taaja.blueracoon;

// import io.taaja.blueracoon.kafkaio.ProducerService;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import io.quarkus.runtime.StartupEvent;
import io.taaja.blueracoon.kafkaio.ProducerService;
import io.taaja.blueracoon.model.DeDroneLogMessage;
import io.taaja.blueracoon.model.DeDroneMessage;
import io.taaja.models.generic.Coordinates;
import lombok.SneakyThrows;
import lombok.extern.jbosslog.JBossLog;
import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@JBossLog
@Path("/v1/dedrone")
public class DeDroneResource {

    @Inject
    ProducerService producerService;

    @Inject
    DeDroneLogRepository deDroneLogRepository;

    private ObjectMapper objectMapper;


    @ConfigProperty(name = "app.file-buffer")
    private boolean fileBuffer;

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

        if("disabled".equals(tag)){
            log.info("skipping. log and processing disabled");
            return Response.ok().build();
        }

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
     * returns the log as  zip.
     * @return  {@link DeDroneLogMessage} or {@link DeDroneMessage}
     */
    @GET
    @Path("/allLog")
    @Produces("application/zip")
    @SneakyThrows
    public Response getLog(){

        FindIterable<Object> logs = this.deDroneLogRepository.getLogs();

        OutputStream outputStream;
        File temp = null;

        log.info("start processing logs");

        //file buffer in case OoM Error occurs
        if(fileBuffer){
            log.info("using file buffer 'temp.zip'");
            temp = new File("temp.zip");
            outputStream = new FileOutputStream(temp, false);
        } else {
            outputStream = new ByteArrayOutputStream();
        }

        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        zipOutputStream.setLevel(Deflater.BEST_COMPRESSION);
        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        jsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
        ObjectMapper notClosingMapper = new ObjectMapper(jsonFactory);


        int processed = 0;
        int skipped = 0;
        for(Object currentLog : logs){
            try {
                DeDroneLogMessage deDroneLogMessage = notClosingMapper.convertValue(currentLog, DeDroneLogMessage.class);
                String tag = deDroneLogMessage.getTag();
                if("disable".equals(tag) || "disabled".equals(tag)){
                    skipped++;
                    continue;
                }
                zipOutputStream.putNextEntry(
                        new ZipEntry(tag + "/" + deDroneLogMessage.getCreated().getTime() + ".json")
                );
                notClosingMapper.writeValue(zipOutputStream, deDroneLogMessage);
                processed++;
            }catch (IllegalArgumentException iae){
                log.warn("cant parse log: " + iae.getMessage(), iae);
                skipped++;
            }
        }

        log.info("Done. processed " + processed + " logs and skipped " + skipped);

        zipOutputStream.close();

        Object returnObj;
        long size;

        if(fileBuffer){
            log.info("done using file");
            size = FileUtils.sizeOf(temp);
            returnObj = temp;
        }else{
            log.info("done using buffer");
            ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) outputStream;
            size = byteArrayOutputStream.size();
            returnObj = byteArrayOutputStream.toByteArray();
        }

        log.info("Size  " + size / 1024 + " kb");
        Response.ResponseBuilder response = Response.ok(returnObj);
        response.header("Content-Disposition", "attachment; filename=\"logs.zip\"");
        response.header("Content-Length", size);

        return response.build();
    }


}

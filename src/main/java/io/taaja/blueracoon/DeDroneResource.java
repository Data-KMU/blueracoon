package io.taaja.blueracoon;

import io.taaja.blueracoon.model.Coordinates;
import io.taaja.blueracoon.model.SensorStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@Path("/dedrone/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeDroneResource {

    private Set<SensorStatus> sensorStatusSet = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public DeDroneResource() {
        sensorStatusSet.add(new SensorStatus("5d77941ffa50637e98ca5845", "drone", "alert", "Wi-Fi", 3, new Coordinates(12.17302, 47.5839863, 290.0)));
        sensorStatusSet.add(new SensorStatus("5d779403fa50637e98ca5844", "remote", "alert", "LBT", 3, new Coordinates(12.17302, 47.5839863, 290.0)));

    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Set<SensorStatus> list() {
        return sensorStatusSet;
    }

    @POST
    public Set<SensorStatus> add(SensorStatus sensorStatus) {
        sensorStatusSet.add(sensorStatus);
        return sensorStatusSet;
    }

    @DELETE
    public Set<SensorStatus> delete(SensorStatus sensorStatus) {
        sensorStatusSet.removeIf(existingFruit -> existingFruit.getId().contentEquals(sensorStatus.getId()));
        return sensorStatusSet;
    }


}

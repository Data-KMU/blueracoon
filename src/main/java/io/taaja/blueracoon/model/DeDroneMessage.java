package io.taaja.blueracoon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.taaja.models.generic.Coordinates;
import lombok.Data;

import java.util.List;

@Data
public class DeDroneMessage {

    private MessageChannel channel;
    private int version;
    private MessageData data;

    public Coordinates getCoordinates() {
        if(this.data == null ||  this.data.getDetections() == null || this.data.getDetections().isEmpty()){
            return null;
        }

        float longitudeSum = 0, latitudeSum = 0, altitudeSum = 0;

        for (Detection detection : this.data.getDetections()){
            if(detection.getPositions() != null && ! detection.getPositions().isEmpty()){

                List<Coordinates> positions = detection.getPositions();

                for(Coordinates coordinates : positions){

                    longitudeSum += coordinates.getAltitude();
                    latitudeSum += coordinates.getLatitude();
                    altitudeSum += coordinates.getLongitude();

                }

                int len = positions.size();

                Coordinates average = new Coordinates();
                average.setAltitude(altitudeSum /len);
                average.setLatitude(latitudeSum /len);
                average.setLongitude(longitudeSum /len);

                return average;

            }
        }

        return null;
    }
}

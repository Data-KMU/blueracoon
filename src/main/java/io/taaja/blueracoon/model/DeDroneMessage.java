package io.taaja.blueracoon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.taaja.models.generic.Coordinates;
import lombok.Data;
import org.mongojack.Id;

import java.util.List;

@Data
@JsonIgnoreProperties({"_id"})
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

                    altitudeSum += coordinates.getAltitude();
                    latitudeSum += coordinates.getLatitude();
                    longitudeSum += coordinates.getLongitude();

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

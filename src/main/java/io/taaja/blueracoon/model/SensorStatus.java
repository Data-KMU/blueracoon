package io.taaja.blueracoon.model;


public class SensorStatus {
    private String id;
    private String type;
    private Double heading;
    private Coordinates coordinates;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getHeading() {
        return heading;
    }

    public void setHeading(Double heading) {
        this.heading = heading;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}

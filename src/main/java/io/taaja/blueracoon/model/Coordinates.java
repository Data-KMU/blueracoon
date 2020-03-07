package io.taaja.blueracoon.model;

public class Coordinates {
    private double longitude;
    private double latitude;
    private double heading;

    public Coordinates(double longitude, double latitude, double heading) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.heading = heading;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", heading=" + heading +
                '}';
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }
}

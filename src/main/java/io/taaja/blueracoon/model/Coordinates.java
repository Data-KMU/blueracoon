package io.taaja.blueracoon.model;

public class Coordinates {
    private double longitude;
    private double altitude;
    private double heading;

    public Coordinates(double longitude, double altitude, double heading) {
        this.longitude = longitude;
        this.altitude = altitude;
        this.heading = heading;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "longitude=" + longitude +
                ", altitude=" + altitude +
                ", heading=" + heading +
                '}';
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }
}

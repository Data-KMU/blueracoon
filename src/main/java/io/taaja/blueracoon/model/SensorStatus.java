package io.taaja.blueracoon.model;

import javax.swing.*;

public class SensorStatus {
    private String id;
    private String detectionType;
    private String channel;
    private String protocol;
    private int version;
    private Coordinates coordinates;

    public SensorStatus(String id, String detectionType, String channel, String protocol, int version, Coordinates coordinates) {
        this.id = id;
        this.detectionType = detectionType;
        this.channel = channel;
        this.protocol = protocol;
        this.coordinates = coordinates;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "SensorStatus{" +
                "id='" + id + '\'' +
                ", detectionType='" + detectionType + '\'' +
                ", channel='" + channel + '\'' +
                ", protocol='" + protocol + '\'' +
                ", version='" + version + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetectionType() {
        return detectionType;
    }

    public void setDetectionType(String detectionType) {
        this.detectionType = detectionType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}

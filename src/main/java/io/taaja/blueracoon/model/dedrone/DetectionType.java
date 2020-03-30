package io.taaja.blueracoon.model.dedrone;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DetectionType {

    Remote("remote"),
    Drone("drone");

    private final String value;

    DetectionType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

}

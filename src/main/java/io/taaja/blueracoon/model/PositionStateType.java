package io.taaja.blueracoon.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PositionStateType {

    Alive("ALIVE"),
    Stale("STALE");

    private final String value;

    PositionStateType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }



}

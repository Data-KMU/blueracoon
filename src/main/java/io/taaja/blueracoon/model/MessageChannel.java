package io.taaja.blueracoon.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageChannel {

    Alert("alert"),
    Status("status");

    private final String value;

    MessageChannel(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }


}

package io.taaja.blueracoon.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ChannelType {

    Wifi("wifi"),
    Radio("radio");

    private final String value;

    ChannelType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }


}

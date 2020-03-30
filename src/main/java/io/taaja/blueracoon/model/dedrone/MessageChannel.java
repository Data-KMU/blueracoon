package io.taaja.blueracoon.model.dedrone;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageChannel {

    Alert("alert");

    private final String value;

    MessageChannel(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }


}

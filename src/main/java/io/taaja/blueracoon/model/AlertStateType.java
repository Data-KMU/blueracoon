package io.taaja.blueracoon.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AlertStateType {

    Start("start"),
    End("end"),
    Update("update");

    private final String value;

    AlertStateType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

}

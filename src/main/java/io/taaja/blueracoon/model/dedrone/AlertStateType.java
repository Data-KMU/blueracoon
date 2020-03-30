package io.taaja.blueracoon.model.dedrone;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AlertStateType {

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

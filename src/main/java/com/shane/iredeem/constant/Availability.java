package com.shane.iredeem.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Availability {
    NON_STOP("E", 0),
    AVAILABLE("Y", 1),
    UNAVAILABLE("N", 2);

    private final String code;
    private final int order;
    Availability(String code, int order) {
        this.code = code;
        this.order = order;
    }

    public int getOrder(){
        return this.order;
    }

    @JsonValue
    final String getValue() {
        return this.code;
    }
}

package com.shane.iredeem.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Cabin {
    FIRST("F"),
    BUSINESS("B"),
    PREMIUM_ECONOMY("N"),
    ECONOMY("R");

    private final String code;
    Cabin(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    @JsonValue
    final String getValue() {
        return this.code;
    }
}

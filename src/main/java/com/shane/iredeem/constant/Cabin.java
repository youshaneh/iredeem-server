package com.shane.iredeem.constant;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import javax.persistence.Converter;
import java.util.Arrays;

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
    public String getValue() {
        return this.code;
    }

    @Override
    public String toString() { return code; }

    public static Cabin fromCode(String code){
        return Arrays.stream(Cabin.values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
    }
}

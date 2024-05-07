package com.nhnacademy.front.mode;

public enum AircleanerMode {
    COMFORT(300f, 100f),
    NORMAL(400f, 200f);

    private final Float onValue;
    private final Float offValue;

    AircleanerMode(Float onValue, Float offValue) {
        this.onValue = onValue;
        this.offValue = offValue;
    }

    public Float getOnValue() {
        return onValue;
    }

    public Float getOffValue() {
        return offValue;
    }
}

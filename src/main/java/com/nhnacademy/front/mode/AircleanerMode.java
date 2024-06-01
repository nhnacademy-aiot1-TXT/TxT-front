package com.nhnacademy.front.mode;

/**
 * 공기청정기 모드를 처리하기 위한 클래스
 *
 * @author parksangwon
 * @version 1.0.0
 */
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

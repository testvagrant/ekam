package com.testvagrant.ekam.commons;

public enum Toggles {
    MOCK(SystemProperties.MOCK),
    LOGS(SystemProperties.LOGS),
    SWITCH_VIEW(SystemProperties.SWITCH_VIEW),
    TIMELINE(SystemProperties.TIMELINE);

    private Boolean active;

    Toggles(Boolean active) {
        this.active = active;
    }

    public boolean isOn() {
        return active;
    }

    public boolean isActive() {return active;}

    public boolean isOff() {
        return active == false;
    }
}

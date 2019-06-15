package com.jm.projectunion.common.eventbus;

public class TimeEvent {

    private String time;

    public TimeEvent(String time) {
        this.time = time;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

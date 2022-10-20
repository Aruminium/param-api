package com.example.wsbp.domain.models.ptj.value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class FinishTime implements Serializable {
    @JsonProperty("finish_time")
    private final Time time;
    private final static int MIN_START_HOUR = 8;
    private final static int MAX_START_HOUR = 22;
    private final static int MIN_START_MINUTES = 0;
    private final static int MAX_START_MINUTES = 59;

    public FinishTime(final Time time) {
        this.time = time;
    }

    public FinishTime(final int finishHour, final int finishMinutes) {
        if (finishHour < MIN_START_HOUR) throw new IllegalArgumentException("勤務時間は " + MIN_START_HOUR + " 時からです");
        if (finishHour > MAX_START_HOUR) throw new IllegalArgumentException("勤務時間は " + MAX_START_HOUR + " 時までです");
        if (finishMinutes < MIN_START_MINUTES) throw new IllegalArgumentException(finishMinutes + " は不正な値です");
        if (finishMinutes > MAX_START_MINUTES) throw new IllegalArgumentException(finishMinutes + " は不正な値です");
        time = Time.valueOf(String.format("%d:%d:00", finishHour, finishMinutes));
    }

    @JsonIgnore
    public Time getTime() {
        return time;
    }


    @JsonIgnore
    public int getHour() {
        SimpleDateFormat sdf = new SimpleDateFormat("H");
        return Integer.parseInt(sdf.format(time));
    }

    @JsonIgnore
    public int getMinutes() {
        SimpleDateFormat sdf = new SimpleDateFormat("m");
        return Integer.parseInt(sdf.format(time));
    }
}

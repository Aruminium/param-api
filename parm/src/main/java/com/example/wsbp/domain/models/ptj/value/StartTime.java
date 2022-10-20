package com.example.wsbp.domain.models.ptj.value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class StartTime implements Serializable {
    @JsonProperty("start_time")
    private final Time time;
    private final static int MIN_START_HOUR = 8;
    private final static int MAX_START_HOUR = 21;
    private final static int MIN_START_MINUTES = 0;
    private final static int MAX_START_MINUTES = 59;

    public StartTime(final Time time) {
        this.time = time;
    }

    public StartTime(final int startHour, final int startMinutes) {
        if (startHour < MIN_START_HOUR) throw new IllegalArgumentException("勤務時間は " + MIN_START_HOUR + " 時からです");
        if (startHour > MAX_START_HOUR) throw new IllegalArgumentException("勤務時間は " + MAX_START_HOUR + " 時までです");
        if (startMinutes < MIN_START_MINUTES) throw new IllegalArgumentException(startMinutes + " は不正な値です");
        if (startMinutes > MAX_START_MINUTES) throw new IllegalArgumentException(startMinutes + " は不正な値です");

        time = Time.valueOf(String.format("%d:%d:00", startHour, startMinutes));
    }

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

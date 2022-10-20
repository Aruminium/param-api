package com.example.wsbp.domain.models.ptj.value;

import java.io.Serializable;

public class BreakTimeMinutes implements Serializable {
    private final int breakTimeMinutes;
    private static final int MIN_BREAK_TIME_MINUTES = 0;
    private static final int MAX_BREAK_TIME_MINUTES = 60;

    public BreakTimeMinutes(final int breakTimeMinutes) {
        if (breakTimeMinutes < MIN_BREAK_TIME_MINUTES) throw new IllegalArgumentException("不正な値です");
        if (breakTimeMinutes > MAX_BREAK_TIME_MINUTES) throw new IllegalArgumentException("休憩時間は最大60分までです");
        this.breakTimeMinutes = breakTimeMinutes;
    }

    public int getBreakTimeMinutes() {
        return breakTimeMinutes;
    }
}

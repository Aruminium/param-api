package com.example.wsbp.domain.models.ptj.value;

import java.io.Serializable;

public class OfficeHours implements Serializable {
    private final float officeHours;
    private final static float MIN_OFFICE_HOURS = 0;
    private final static float MAX_OFFICE_HOURS = 8;

    public OfficeHours(final float officeHours) {
        if (officeHours < MIN_OFFICE_HOURS) throw new IllegalArgumentException("不正な値です");
        if (officeHours > MAX_OFFICE_HOURS) throw new IllegalArgumentException("8時間超過で働くことはできません");
        this.officeHours = officeHours;
    }

    public float getOfficeHours() {
        return officeHours;
    }
}

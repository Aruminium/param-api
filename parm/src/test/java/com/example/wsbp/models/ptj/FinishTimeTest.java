package com.example.wsbp.models.ptj;

import com.example.wsbp.domain.models.ptj.value.StartTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FinishTimeTest {
    @Test
    @DisplayName("HourとMinutesの入出力が等しいかテスト")
    void inputsAndOutputsEqualTest(){
        // 1桁 1桁
        inputsAndOutputsEqualTestFunc(9, 0);
        // 1桁 2桁
        inputsAndOutputsEqualTestFunc(9, 10);
        // 2桁 1桁
        inputsAndOutputsEqualTestFunc(10, 0);
        // 2桁 2桁
        inputsAndOutputsEqualTestFunc(10, 10);
    }

    void inputsAndOutputsEqualTestFunc(int finishHour, int finishMinutes){
        StartTime startTime = new StartTime(finishHour,finishMinutes);
        assertEquals(startTime.getHour(), finishHour);
        assertEquals(startTime.getMinutes(), finishMinutes);
    }
}

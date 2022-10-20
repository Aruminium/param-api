package com.example.wsbp.models.ptj;

import com.example.wsbp.domain.models.ptj.value.StartTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StartTimeTest {
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

    void inputsAndOutputsEqualTestFunc(int startHour, int startMinutes){
        StartTime startTime = new StartTime(startHour,startMinutes);
        assertEquals(startTime.getHour(), startHour);
        assertEquals(startTime.getMinutes(), startMinutes);
    }
}

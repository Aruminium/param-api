package com.example.wsbp.models.ptj;

import com.example.wsbp.domain.models.ptj.PartTimeJob;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;

public class PartTimeJobTest {
    @Test
    @DisplayName("Json変換テスト")
    void PtjMapperJson() throws JsonProcessingException {
        PartTimeJob partTimeJob = new PartTimeJob(1, "b0000000", 1, Date.valueOf("2022-09-01"), Time.valueOf("11:00:00"), Time.valueOf("12:00:00"), 0, 1, "eラーニング");
        System.out.println(partTimeJob.mapperJson());
        System.out.println(partTimeJob.getDate());
    }
}

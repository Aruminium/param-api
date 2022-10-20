package com.example.wsbp.models.request;

import com.example.wsbp.domain.models.ptj.PartTimeJob;
import com.example.wsbp.domain.models.request.PtjRequest;
import com.example.wsbp.domain.models.subject.Subject;
import com.example.wsbp.domain.models.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class PtjRequestTest {
    @Test
    @DisplayName("Json変換テスト")
    void SubjectMapperJsonTest() throws JsonProcessingException {
        User user = new User("b0000000", "hoge", "fugafuga");
        Subject subject = new Subject(1, "hoge", "fuga");
        List<PartTimeJob> ptjList = new ArrayList<>();
        ptjList.add(new PartTimeJob(1, "b0000000", 1, Date.valueOf("2022-09-01"), Time.valueOf("11:00:00"), Time.valueOf("12:00:00"), 0, 1, "eラーニング"));
        ptjList.add(new PartTimeJob(1, "b0000000", 1, Date.valueOf("2022-09-02"), Time.valueOf("11:00:00"), Time.valueOf("12:00:00"), 0, 1, "eラーニング"));
        ptjList.add(new PartTimeJob(1, "b0000000", 1, Date.valueOf("2022-09-03"), Time.valueOf("11:00:00"), Time.valueOf("12:00:00"), 0, 1, "eラーニング"));
        PtjRequest ptjRequest = new PtjRequest(user, subject, ptjList);
        System.out.println(ptjRequest.mapperJson());
    }
}

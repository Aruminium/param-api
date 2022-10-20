package com.example.wsbp.models.subject;

import com.example.wsbp.domain.models.subject.Subject;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SubjectTest {
    @Test
    @DisplayName("Json変換テスト")
    void SubjectMapperJsonTest() throws JsonProcessingException {
        Subject subject = new Subject(1, "hoge", "fuga");
        System.out.println(subject.mapperJson());
    }
}

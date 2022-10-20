package com.example.wsbp.models.user;

import com.example.wsbp.domain.models.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    @DisplayName("Json変換テスト")
    void UserMapperJsonTest() throws JsonProcessingException {
        User user = new User("b0000000", "山田太郎", "appleBanana");
        System.out.println(user.mapperJson());
    }
}

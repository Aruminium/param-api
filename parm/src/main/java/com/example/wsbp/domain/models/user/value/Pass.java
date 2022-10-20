package com.example.wsbp.domain.models.user.value;

import java.io.Serializable;

public class Pass implements Serializable {
    private final String pass;
    private static final int MIN_PASS_LENGTH = 8;
    private static final int MAX_PASS_LENGTH = 64;

    public Pass(final String pass) {
        if (pass == null) throw new IllegalArgumentException("パスワードが入力されていません");
        if (pass.length() < MIN_PASS_LENGTH) throw new IllegalArgumentException("パスワードは8文字以上です");
        if (pass.length() > MAX_PASS_LENGTH) throw new IllegalArgumentException("パスワードは64文字以下です");
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }
}

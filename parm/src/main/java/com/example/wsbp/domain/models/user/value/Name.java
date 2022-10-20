package com.example.wsbp.domain.models.user.value;

import java.io.Serializable;

public class Name implements Serializable {
    private final String name;
    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 32;

    public Name(final String name) {
        if (name == null) throw new IllegalArgumentException("ユーザ名が入力されていません");
        if (name.length() < MIN_NAME_LENGTH) throw new IllegalArgumentException("ユーザ名は1文字以上です");
        if (name.length() > MAX_NAME_LENGTH) throw new IllegalArgumentException("ユーザ名は32文字以下です");
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

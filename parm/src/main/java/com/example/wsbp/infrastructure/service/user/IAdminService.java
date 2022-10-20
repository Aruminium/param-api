package com.example.wsbp.infrastructure.service.user;

import java.security.NoSuchAlgorithmException;

public interface IAdminService {
    /**
     * ユーザ情報をAuthUserテーブルに記録する
     */
    void registerUser() throws NoSuchAlgorithmException;
}

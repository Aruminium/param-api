package com.example.wsbp.infrastructure.service.user;

import com.example.wsbp.domain.models.user.User;

import java.security.NoSuchAlgorithmException;

public interface IUserService {

    /**
     * ユーザ情報をAuthUserテーブルに記録する
     * user ユーザ情報
     */
    void registerUser(User user) throws NoSuchAlgorithmException;

    /**
     * ユーザ情報を削除する
     *
     * @param studentNumber 学籍番号
     */
    void removeUser(String studentNumber);

    /**
     * 学籍番号が一致するレコードがAuthUserテーブルにあるか検索する
     *
     * @param studentNumber 学籍番号
     * @param pass          パスワード
     * @return レコードの有無, 存在すれば<code>true</code>, それ以外は <code>false</code>
     */
    User loginUser(String studentNumber, String pass) throws NoSuchAlgorithmException;

}

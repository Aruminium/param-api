package com.example.wsbp.domain.models.user;

public interface IUserRepository {

    /**
     * ユーザ情報をusersテーブルに記録する
     *
     * @param user Userデータ
     */
    void registerUser(User user);

    /**
     * ユーザ情報を削除する
     *
     * @param studentNumber 学籍番号
     */
    void removeUser(String studentNumber);

    /**
     * 学籍番号とパスワードが一致するレコードがusersテーブルにあるか検索する
     *
     * @param studentNumber 学籍番号
     * @param pass          パスワード
     * @return {@link User}
     */
    User loginUser(String studentNumber, String pass);
}

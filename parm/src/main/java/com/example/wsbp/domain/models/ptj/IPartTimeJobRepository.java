package com.example.wsbp.domain.models.ptj;

import java.sql.Date;
import java.util.List;

public interface IPartTimeJobRepository {


    /**
     * ptj_requestsテーブルの情報を、日付で絞り込み検索する
     *
     * @param studentNumber 学籍番号
     * @param date          日付
     * @return レコードの内容を {@link PartTimeJob} の {@link List} で返す
     */
    List<PartTimeJob> find(String studentNumber, Date date);

    /**
     * ptj_requestsテーブルの情報を、日付で絞り込み検索する
     *
     * @param id            ptjId
     * @param studentNumber 学籍番号
     * @param date          日付
     * @return 一意な {@link PartTimeJob} を返す
     */
    PartTimeJob find(int id, String studentNumber, Date date);

    /**
     * @param studentNumber 学籍番号
     * @param date          日付(年-月)
     * @param subjectName   教科名
     */
    List<PartTimeJob> find(String studentNumber, Date date, String subjectName);
}

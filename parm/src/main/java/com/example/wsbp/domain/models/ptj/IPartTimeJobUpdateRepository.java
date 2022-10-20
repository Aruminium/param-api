package com.example.wsbp.domain.models.ptj;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface IPartTimeJobUpdateRepository {
    /**
     * アルバイト内容をptj_requestsテーブルに記録する
     *
     * @param userStudentNumber 学籍番号
     * @param subjectId         教科ID
     * @param ptjDate           勤務日
     * @param startTime         勤務開始時間
     * @param finishTime        勤務終了時間
     * @param breakTimeMinutes  休憩時間
     * @param officeHours       勤務時間
     * @param duties            勤務内容
     */
    void insert(String userStudentNumber, int subjectId, Date ptjDate, Time startTime, Time finishTime, int breakTimeMinutes, float officeHours, String duties);

    /**
     * アルバイト内容を修正する
     *
     * @param newPtjRequest アルバイト変更情報
     * @return データベースの更新行数
     */
    int update(PartTimeJob newPtjRequest);

    /**
     * ptj_requestsテーブルの情報を削除する
     *
     * @return データベースの更新行数
     */
    int delete(PartTimeJob ptjRequest);

    /**
     * ptj_requestsテーブルの情報を複数削除する
     */
    void deletes(List<PartTimeJob> ptjRequests);
}

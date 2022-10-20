package com.example.wsbp.infrastructure.service.ptj;

import com.example.wsbp.domain.models.ptj.PartTimeJob;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface IPartTimeJobUpdateService {

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
     * @param newPtjRequest 新しいアルバイト情報
     */
    void update(PartTimeJob newPtjRequest);

    /**
     * ptj_requestsテーブルのsubjectを削除する
     */
    void delete(PartTimeJob ptjRequest);

    void deletes(List<PartTimeJob> ptjRequestList);
}

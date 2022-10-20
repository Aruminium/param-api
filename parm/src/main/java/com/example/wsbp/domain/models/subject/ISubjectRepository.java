package com.example.wsbp.domain.models.subject;

import java.util.List;

public interface ISubjectRepository {
    /**
     * 教科情報を取得
     *
     * @return {@link List<Subject>}
     */
    List<String> find();

    /**
     * 教科名からSubjectオブジェクト検索
     *
     * @param name 教科名
     * @return {@link Subject}
     */
    Subject findForName(String name);

}

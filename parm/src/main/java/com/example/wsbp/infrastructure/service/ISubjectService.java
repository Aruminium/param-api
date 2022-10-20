package com.example.wsbp.infrastructure.service;

import com.example.wsbp.domain.models.subject.Subject;

import java.util.List;

public interface ISubjectService {

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

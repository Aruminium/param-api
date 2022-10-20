package com.example.wsbp.domain.models;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IPtjRequestEntity {
    /**
     * オブジェクトをJson形式に変換
     *
     * @return Json形式の文字列
     * @throws JsonProcessingException 変換失敗
     */
    String mapperJson() throws JsonProcessingException;
}

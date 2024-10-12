package com.heyqing.spark.service;

import com.heyqing.spark.model.vo.JsonParse;

/**
 * ClassName:SparkChatBotService
 * Package:com.heyqing.spark.service
 * Description:
 *
 * @Date:2024/9/27
 * @Author:Heyqing
 */
public interface SparkChatBotService {
    /**
     * 聊天
     *
     * @param newQuestion
     * @return
     */
    String chat(String newQuestion) throws Exception;
}

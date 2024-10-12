package com.heyqing.spark.controller;

import com.heyqing.spark.model.vo.JsonParse;
import com.heyqing.spark.service.SparkChatBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName:SparkChatBotController
 * Package:com.heyqing.spark.controller
 * Description:
 *
 * @Date:2024/9/27
 * @Author:Heyqing
 */
@CrossOrigin
@RestController
@RequestMapping("chat/bot")
@RequiredArgsConstructor
public class SparkChatBotController {

    private final SparkChatBotService sparkChatBotService;

    /**
     * 聊天
     *
     * @param newQuestion
     * @return
     */
    @GetMapping
    public String chat(@RequestParam String newQuestion) throws Exception {
        return sparkChatBotService.chat(newQuestion);
    }
}

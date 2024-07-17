package com.heyqing.chat_server;

import java.io.IOException;

/**
 * ClassName:Run
 * Package:com.heyqing.chat_server
 * Description:
 *
 * @Date:2024/7/16
 * @Author:Heyqing
 */
public class Run {
    public static void main(String[] args) throws IOException {
        new ChatService().start();
    }
}

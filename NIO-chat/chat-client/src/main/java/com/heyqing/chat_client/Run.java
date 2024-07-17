package com.heyqing.chat_client;

import java.io.IOException;

/**
 * ClassName:Run
 * Package:com.heyqing.chat_client
 * Description:
 *
 * @Date:2024/7/16
 * @Author:Heyqing
 */
public class Run {
    public static int port = 8000;
    public static void main(String[] args) throws IOException {
        new ChatClient().start("1");
    }
}

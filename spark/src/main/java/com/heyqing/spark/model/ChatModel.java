package com.heyqing.spark.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.heyqing.spark.model.dto.RoleContent;
import com.heyqing.spark.model.dto.Text;
import com.heyqing.spark.model.vo.AnswerContext;
import com.heyqing.spark.model.vo.JsonParse;
import com.heyqing.spark.utils.ConstantPropertiesUtils;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * ClassName:ChatModel
 * Package:com.heyqing.spark.utils
 * Description:
 *
 * @Date:2024/9/27
 * @Author:Heyqing
 */
public class ChatModel extends WebSocketListener {


    /**
     * 大模型的答案汇总
     */
    public static String totalAnswer = "";

    /**
     * 对话历史存储集合
     */
    public static List<RoleContent> historyList = new ArrayList<>();

    public static final Gson gson = new Gson();
    // 个性化参数
    private String userId;
    private Boolean wsCloseFlag;

    private String newQuestion;

    private AnswerContext answerContext;

    public ChatModel(String userId, Boolean wsCloseFlag, String newQuestion,AnswerContext answerContext) {
        this.userId = userId;
        this.wsCloseFlag = wsCloseFlag;
        this.newQuestion = newQuestion;
        this.answerContext = answerContext;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        System.out.print("大模型：");
        MyThread myThread = new MyThread(webSocket);
        myThread.start();
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        // System.out.println(userId + "用来区分那个用户的结果" + text);
        JsonParse myJsonParse = gson.fromJson(text, JsonParse.class);
        if (myJsonParse.getHeader().getCode() != 0) {
            System.out.println("发生错误，错误码为：" + myJsonParse.getHeader().getCode());
            System.out.println("本次请求的sid为：" + myJsonParse.getHeader().getSid());
            webSocket.close(1000, "");
        }
        List<Text> textList = myJsonParse.getPayload().getChoices().getText();
        for (Text temp : textList) {
            System.out.print(temp.getContent());
            totalAnswer = totalAnswer + temp.getContent();
        }
        if (myJsonParse.getHeader().getStatus() == 2) {
            // 可以关闭连接，释放资源
            answerContext.setAnswer(totalAnswer);
            System.out.println();
            System.out.println("*************************************************************************************");
            if (canAddHistory()) {
                RoleContent roleContent = new RoleContent();
                roleContent.setRole("assistant");
                roleContent.setContent(totalAnswer);
                historyList.add(roleContent);
            } else {
                historyList.remove(0);
                RoleContent roleContent = new RoleContent();
                roleContent.setRole("assistant");
                roleContent.setContent(totalAnswer);
                historyList.add(roleContent);
            }
            wsCloseFlag = true;
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        try {
            if (null != response) {
                int code = response.code();
                System.out.println("onFailure code:" + code);
                System.out.println("onFailure body:" + response.body().string());
                if (101 != code) {
                    System.out.println("connection failed");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 线程来发送音频与参数
    class MyThread extends Thread {
        private WebSocket webSocket;

        public MyThread(WebSocket webSocket) {
            this.webSocket = webSocket;
        }

        public void run() {
            String appid = ConstantPropertiesUtils.APPID;
            String domain = ConstantPropertiesUtils.DOMAIN;
            try {
                JSONObject requestJson = new JSONObject();
                JSONObject header = new JSONObject();  // header参数
                header.put("app_id", appid);
                header.put("uid", UUID.randomUUID().toString().substring(0, 10));
                JSONObject parameter = new JSONObject(); // parameter参数
                JSONObject chat = new JSONObject();
                chat.put("domain", domain);
                chat.put("temperature", 0.5);
                chat.put("max_tokens", 4096);
                parameter.put("chat", chat);
                JSONObject payload = new JSONObject(); // payload参数
                JSONObject message = new JSONObject();
                JSONArray text = new JSONArray();
                // 历史问题获取
                if (historyList.size() > 0) {
                    for (RoleContent tempRoleContent : historyList) {
                        text.add(JSON.toJSON(tempRoleContent));
                    }
                }
                // 最新问题
                RoleContent roleContent = new RoleContent();
                roleContent.setRole("user");
                roleContent.setContent(newQuestion);
                text.add(JSON.toJSON(roleContent));
                historyList.add(roleContent);
                message.put("text", text);
                payload.put("message", message);
                requestJson.put("header", header);
                requestJson.put("parameter", parameter);
                requestJson.put("payload", payload);
                // System.err.println(requestJson); // 可以打印看每次的传参明细
                webSocket.send(requestJson.toString());
                // 等待服务端返回完毕后关闭
                while (true) {
                    if (wsCloseFlag) {
                        break;
                    }
                }
                webSocket.close(1000, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 由于历史记录最大上线1.2W左右，需要判断是能能加入历史
     *
     * @return
     */
    public static boolean canAddHistory() {
        int history_length = 0;
        for (RoleContent temp : historyList) {
            history_length = history_length + temp.getContent().length();
        }
        if (history_length > 12000) {
            historyList.remove(0);
            historyList.remove(1);
            historyList.remove(2);
            historyList.remove(3);
            historyList.remove(4);
            return false;
        } else {
            return true;
        }
    }
}

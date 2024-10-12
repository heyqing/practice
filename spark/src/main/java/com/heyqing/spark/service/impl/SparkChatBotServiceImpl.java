package com.heyqing.spark.service.impl;

import com.heyqing.spark.model.ChatModel;
import com.heyqing.spark.model.vo.AnswerContext;
import com.heyqing.spark.model.vo.JsonParse;
import com.heyqing.spark.service.SparkChatBotService;
import com.heyqing.spark.utils.ConstantPropertiesUtils;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.heyqing.spark.model.ChatModel.totalAnswer;

/**
 * ClassName:SparkChatBotServiceImpl
 * Package:com.heyqing.spark.service.impl
 * Description:
 *
 * @Date:2024/9/27
 * @Author:Heyqing
 */
@Service
public class SparkChatBotServiceImpl implements SparkChatBotService {


    /**
     * 聊天
     *
     * @param newQuestion
     * @return
     */
    @Override
    public String chat(String newQuestion) throws Exception {
        String hostUrl = ConstantPropertiesUtils.HOST_URL;
        String apiSecret = ConstantPropertiesUtils.API_SECRET;
        String apiKey = ConstantPropertiesUtils.API_KEY;
        // 构建鉴权url
        String authUrl = getAuthUrl(hostUrl, apiSecret, apiKey);
        OkHttpClient client = new OkHttpClient.Builder().build();
        String url = authUrl.toString().replace("http://", "ws://").replace("https://", "wss://");
        Request request = new Request.Builder().url(url).build();
        AnswerContext context = new AnswerContext();
        totalAnswer = "";
        WebSocket webSocket = client.newWebSocket(request, new ChatModel(1 + "", false, newQuestion, context));
        Thread.sleep(200);
        return "大模型:" + context.getAnswer();
    }


    /***************************************************private********************************************************/

    /**
     * 鉴权方法
     *
     * @param hostUrl
     * @param apiSecret
     * @param apiKey
     * @return
     * @throws Exception
     */
    private String getAuthUrl(String hostUrl, String apiSecret, String apiKey) throws Exception {
        URL url = new URL(hostUrl);
        // 时间
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        // 拼接
        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";
        // System.err.println(preStr);
        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);

        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        // Base64加密
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        // System.err.println(sha);
        // 拼接
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        // 拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().//
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8))).//
                addQueryParameter("date", date).//
                addQueryParameter("host", url.getHost()).//
                build();

        // System.err.println(httpUrl.toString());
        return httpUrl.toString();
    }

}

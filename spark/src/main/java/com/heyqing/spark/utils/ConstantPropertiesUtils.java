package com.heyqing.spark.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ClassName:ConstantPropertiesUtils
 * Package:com.heyqing.spark.utils
 * Description:
 *
 * @Date:2024/9/27
 * @Author:Heyqing
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    @Value("${spark.hostUrl}")
    private String hostUrl;
    @Value("${spark.appid}")
    private String appid;
    @Value("${spark.apiSecret}")
    private String apiSecret;
    @Value("${spark.apiKey}")
    private String apiKey;
    @Value("${spark.domain}")
    private String domain;


    public static String HOST_URL;
    public static String APPID;
    public static String API_SECRET;
    public static String API_KEY;
    public static String DOMAIN;

    @Override
    public void afterPropertiesSet() throws Exception {
        HOST_URL = hostUrl;
        APPID = appid;
        API_SECRET = apiSecret;
        API_KEY = apiKey;
        DOMAIN = domain;
    }
}

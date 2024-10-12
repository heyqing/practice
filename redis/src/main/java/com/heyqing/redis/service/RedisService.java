package com.heyqing.redis.service;

/**
 * ClassName:RedisService
 * Package:com.heyqing.redis.service
 * Description:
 *
 * @Date:2024/10/3
 * @Author:Heyqing
 */
public interface RedisService {
    /**
     * 添加String
     */
    void addString();

    /**
     * 获取String
     *
     * @param key
     * @return
     */
    String getString(String key);

    /**
     * 删除String
     *
     * @param key
     */
    void deleteString(String key);

    /**
     * 修改String
     *
     * @param key
     */
    void updateString(String key);

    void addZSet();

    String getZSet(String key);

    String addSet();

    void updateSet(Integer i, String key);
}

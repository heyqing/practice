package com.heyqing.redis.service.impl;

import com.heyqing.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:RedisServiceImpl
 * Package:com.heyqing.redis.service.impl
 * Description:
 *
 * @Date:2024/10/3
 * @Author:Heyqing
 */
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    private static final String KEY_FIX = "redis:";
    private static final String VALUE_FIX = "何以晴：";
    private static final Long EXPIRE = 15L;

    /**
     * 添加String
     */
    @Override
    public void addString() {
        String FIX = "string:";
        String key = generateKey(FIX);
        String value = generateStringValue();
        stringRedisTemplate.opsForValue().set(key, value);
        System.out.println("addString -> key " + key + " value " + value);
        System.out.println("有效时间：15 s");
        stringRedisTemplate.expire(key, EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 获取String
     *
     * @param key
     * @return
     */
    @Override
    public String getString(String key) {
        System.out.println("getString -> key " + key);
        return stringRedisTemplate.opsForValue().get(KEY_FIX + "string:" + key);
    }

    /**
     * 删除String
     *
     * @param key
     */
    @Override
    public void deleteString(String key) {
        System.out.println("deleteString -> key " + key);
        stringRedisTemplate.delete(KEY_FIX + key);
    }

    @Override
    public void updateString(String key) {
        stringRedisTemplate.opsForValue().set(KEY_FIX + key, generateStringValue());
    }

    @Override
    public void addZSet() {
        String FIX = "zset:";
        String key = generateKey(FIX);
        Set<DefaultTypedTuple> value = generateZSetValue();
        redisTemplate.opsForZSet().add(key, value);
        System.out.println("addZSet -> key " + key + " value " + value);
    }

    @Override
    public String getZSet(String key) {
        Long end = redisTemplate.opsForZSet().size(KEY_FIX + "zset:" + key) - 1;
        Set range = redisTemplate.opsForZSet().range(KEY_FIX + "zset:" + key, 0L, end);
        System.out.println("getZSet:" + range);
        return range.toString();
    }

    @Override
    public String addSet() {
        String key = generateKey("set:");
        Set<Integer> value = generateSetValue();
        redisTemplate.opsForSet().add(key, value);
        return key;
    }

    @Override
    public void updateSet(Integer i, String key) {
        key = KEY_FIX + "set:" + key;
        Set<Integer> set = (Set<Integer>)redisTemplate.opsForValue().get(key);
        set.remove(i);
        redisTemplate.opsForSet().add(key, set);
    }

/****************************************private****************************************/
    /**
     * 生成key
     *
     * @return
     */
    private String generateKey(String fix) {
        int random = (int) Math.floor(Math.random() * 100);
        return KEY_FIX + fix + random;
    }

    private String generateStringValue() {
        return VALUE_FIX + UUID.randomUUID().toString();
    }

    private Set<DefaultTypedTuple> generateZSetValue() {
        Set<DefaultTypedTuple> set = new HashSet<>();
        for (int i = 0; i < Math.floor(Math.random() * 10); i++) {
            set.add(new DefaultTypedTuple(VALUE_FIX + UUID.randomUUID().toString(), Math.random() * 0.618));
        }
        return set;
    }

    private Set<Integer> generateSetValue() {
        Set<Integer> value = new HashSet<>();
        for (int i = 0; i < Math.floor(Math.random() * 10); i++) {
            value.add((int) (Math.random() * 100));
        }
        return value;
    }
}

package com.heyqing.redis.controller;

import com.heyqing.redis.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName:RedisController
 * Package:com.heyqing.redis.controller
 * Description:
 *
 * @Date:2024/10/3
 * @Author:Heyqing
 */
@Api(tags = "redis测试接口")
@RestController
@RequestMapping("redis")
@RequiredArgsConstructor
@CrossOrigin
public class RedisController {

    private final RedisService redisService;

    @ApiOperation(value = "添加-String")
    @PostMapping("addString")
    public void addString() {
        redisService.addString();
    }

    @ApiOperation(value = "添加-Set")
    @PostMapping("addSet")
    public String addSet() {
        return redisService.addSet();
    }


    @ApiOperation(value = "修改-Set")
    @PostMapping("updateSet/{i}/{key}")
    public void updateSet(@PathVariable Integer i,@PathVariable String key) {
        redisService.updateSet(i,key);
    }
    @ApiOperation(value = "修改-String")
    @PostMapping("updateString/{key}")
    public void updateString(@PathVariable String key) {
        redisService.updateString(key);
    }
    @ApiOperation(value = "获取-String")
    @GetMapping("getString/{key}")
    public String getString(@PathVariable("key") String key) {
        return redisService.getString(key);
    }

    @ApiOperation(value = "删除-String")
    @DeleteMapping("deleteString/{key}")
    public void deleteString(@PathVariable("key") String key){
        redisService.deleteString(key);
    }

    @ApiOperation(value = "添加-ZSet")
    @PostMapping("addZSet")
    public void addZSet() {
        redisService.addZSet();
    }
    @ApiOperation(value = "获取-ZSet")
    @GetMapping("getZSet/{key}")
    public String getZSet(@PathVariable("key") String key) {
        return redisService.getZSet(key);
    }
}

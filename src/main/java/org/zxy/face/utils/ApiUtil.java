package org.zxy.face.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.zxy.face.exceptions.ApiException;

import javax.annotation.Resource;

@Slf4j
public class ApiUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void verifyApi(String api) {
        //redisTemplate.opsForSet().;
        if (stringRedisTemplate.opsForValue().get(api) == null) {
            throw new ApiException();
        }
    }

    public void addApi(String api) {
        // 判断redis是否为空
        stringRedisTemplate.opsForValue().set(api, "");
    }
}

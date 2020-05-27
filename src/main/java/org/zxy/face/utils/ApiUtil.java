package org.zxy.face.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.zxy.face.exceptions.ApiException;

import javax.annotation.Resource;

@Slf4j
public class ApiUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final String PREFIX = "api_";

    public void verifyApi(String api) {
        //redisTemplate.opsForSet().;
        String value = stringRedisTemplate.opsForValue().get(PREFIX + api);
        if (value == null) {
            throw new ApiException();
        }

        // 否则保存的数字加1，代表此人访问了多少次；
        stringRedisTemplate.opsForValue().set(PREFIX + api, MathUtil.add(value));
    }

    public void addApi(String api) {
        // 判断redis是否为空
        stringRedisTemplate.opsForValue().set(PREFIX + api, "");
    }
}

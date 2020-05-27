package org.zxy.face.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.zxy.face.utils.HttpUtil;

@Component
public class HttpConfig {

    @Bean
    public HttpUtil httpUtil() {
        return new HttpUtil();
    }
}

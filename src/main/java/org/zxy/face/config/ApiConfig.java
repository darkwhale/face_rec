package org.zxy.face.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.zxy.face.utils.ApiUtil;

@Component
public class ApiConfig {

    @Bean
    public ApiUtil apiUtil() {
        return new ApiUtil();
    }
}

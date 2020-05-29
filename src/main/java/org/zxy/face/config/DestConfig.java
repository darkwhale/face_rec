package org.zxy.face.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "dest")
public class DestConfig {

    private String url;

    private Double threshold;
}

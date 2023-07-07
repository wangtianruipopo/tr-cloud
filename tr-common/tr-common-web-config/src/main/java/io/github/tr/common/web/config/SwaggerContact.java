package io.github.tr.common.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "swagger.contact")
@Data
public class SwaggerContact {
    private String name = "name";
    private String url = "url";
    private String email = "email";
}

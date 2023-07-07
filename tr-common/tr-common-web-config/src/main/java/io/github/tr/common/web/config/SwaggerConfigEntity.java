package io.github.tr.common.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerConfigEntity {
    private String title = "swagger title";
    private String description = "swagger description";
    private String termsOfServiceUrl = "localhost:8080/";
    private SwaggerContact contact;
    private String version = "1.0";
    private String groupName = "swagger groupName";
}

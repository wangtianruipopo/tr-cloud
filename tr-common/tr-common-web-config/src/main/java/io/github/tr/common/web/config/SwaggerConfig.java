package io.github.tr.common.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * <h1>集成swagger文档</h1>
 */
@EnableOpenApi
@Configuration
public class SwaggerConfig {

    /**
     * <h2>配置基本信息</h2>
     *
     * @return 配置基本信息
     */
    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger Test App Restful API")
                .description("swagger test app restful api")
                .termsOfServiceUrl("https://localhost:8080")
                .contact(new Contact("wangtianrui", "https://gitee.com/moxiaoxing", "425281019@qq.com"))
                .version("1.0")
                .build();
    }

    /**
     * <h2>配置文档生成最佳实践</h2>
     *
     * @param apiInfo 配置基本信息
     * @return swagger 文档
     */
    @Bean
    public Docket createRestApi(ApiInfo apiInfo) {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo)
                .groupName("SwaggerGroupOneAPI")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }
}
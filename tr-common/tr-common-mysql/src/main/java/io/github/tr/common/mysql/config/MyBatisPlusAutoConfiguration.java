package io.github.tr.common.mysql.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MyBatisPlusConfig.class)
public class MyBatisPlusAutoConfiguration {
}

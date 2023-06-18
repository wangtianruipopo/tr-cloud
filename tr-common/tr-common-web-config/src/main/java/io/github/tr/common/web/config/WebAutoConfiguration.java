package io.github.tr.common.web.config;

import io.github.tr.common.web.utils.HttpEndpointUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author wangtianrui
 */
@AutoConfiguration
public class WebAutoConfiguration {
    @Bean
    public HttpEndpointUtil httpEndpointUtils(){
        return new HttpEndpointUtil();
    }
}

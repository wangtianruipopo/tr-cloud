package io.github.wangtianruipopo.trcoregen.config;

import io.github.wangtianruipopo.trcoregen.service.ITablesService;
import io.github.wangtianruipopo.trcoregen.service.impl.MySqlTablesService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TablesServiceConfig {
    @Bean
    @ConditionalOnProperty(name = "db.type", havingValue = "MYSQL")
    public ITablesService local() {
        return new MySqlTablesService();
    }
}

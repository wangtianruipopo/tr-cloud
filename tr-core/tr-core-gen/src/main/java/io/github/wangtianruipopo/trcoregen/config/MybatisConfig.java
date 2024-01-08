package io.github.wangtianruipopo.trcoregen.config;

import com.baomidou.mybatisplus.annotation.DbType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {

    @Value("${db.type}")
    private String type;

    @Bean
    public DbType dbType() {
        switch (type) {
            case "MYSQL":
                return DbType.MYSQL;
            case "POSTGRE_SQL":
                return DbType.POSTGRE_SQL;
        }
        return null;
    }
}

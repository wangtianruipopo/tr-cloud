package io.github.wangtianruipopo.trcoregen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "io.github.wangtianruipopo.trcoregen.mapper")
public class TrCoreGenApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrCoreGenApplication.class, args);
    }

}

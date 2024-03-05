package io.github.tr.common.mysql.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import io.github.tr.common.mysql.interceptor.OrderByInnerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author wangtianrui
 */
@Configuration
public class MyBatisPlusConfig {

    @Resource
    private DbType dbType;

    @Autowired
    private Optional<InnerInterceptorContainer> innerInterceptorContainer;

    @Autowired
    private OrderByInnerInterceptor orderByInnerInterceptor;

    @Bean
    public OrderByInnerInterceptor orderByInnerInterceptor() {
        return new OrderByInnerInterceptor();
    }

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        DbType defaultType = DbType.MYSQL;
        if (dbType != null) {
            defaultType = dbType;
        }
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        if (innerInterceptorContainer.isPresent()) {
            List<InnerInterceptor> innerInterceptors = innerInterceptorContainer.get().innerInterceptors();
            innerInterceptors.forEach(interceptor::addInnerInterceptor);
        }
        interceptor.addInnerInterceptor(orderByInnerInterceptor);
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(defaultType));
        return interceptor;
    }
}

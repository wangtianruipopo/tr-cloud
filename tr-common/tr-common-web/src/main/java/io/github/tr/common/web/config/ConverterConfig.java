package io.github.tr.common.web.config;

import io.github.tr.common.web.converter.DateTimeConverter;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class ConverterConfig {
    @Bean
    public BeanUtilsBean dateTimeConverter() {
        DateTimeConverter dtConverter = new DateTimeConverter();
        ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
        convertUtilsBean.deregister(java.util.Date.class);
        convertUtilsBean.deregister(java.sql.Date.class);
        convertUtilsBean.deregister(LocalDate.class);
        convertUtilsBean.deregister(LocalDateTime.class);
        convertUtilsBean.register(dtConverter, java.util.Date.class);
        convertUtilsBean.register(dtConverter, java.sql.Date.class);
        convertUtilsBean.register(dtConverter, LocalDate.class);
        convertUtilsBean.register(dtConverter, LocalDateTime.class);
        // 这里一定要注册默认值，使用null也可以
        BigDecimalConverter bd = new BigDecimalConverter(null);
        convertUtilsBean.register(bd, java.math.BigDecimal.class);
        return new BeanUtilsBean(convertUtilsBean, new PropertyUtilsBean());
    }
}

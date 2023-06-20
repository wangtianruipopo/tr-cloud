package io.github.tr.common.web.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * @author wangtianrui
 */
@AutoConfiguration
public class ConverterAutoConfiguration {
    /**
     * 转换HTTP请求和响应
     *
     * @return /
     */
//    @Bean
//    public HttpMessageConverters httpMessageConverters() {
//        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//
//        FastJsonConfig config = new FastJsonConfig();
//        config.setWriterFeatures(JSONWriter.Feature.PrettyFormat);
//        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
//        converter.setFastJsonConfig(config);
//
//        return new HttpMessageConverters(converter);
//    }
//
//    @Bean
//    public Converter<String, LocalDate> stringToLocalDateConverter() {
//        return s -> {
//            if (StrUtil.isEmpty(s)) {
//                return null;
//            }
//            return LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        };
//    }
//
//    @Bean
//    public Converter<String, LocalDateTime> stringToLocalDateTimeConverter() {
//        return source -> {
//            if (StrUtil.isBlank(source)) {
//                return null;
//            }
//            try {
//                return LocalDateTime.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//            } catch (DateTimeParseException e) {
//                LocalDate localDate = LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                return LocalDateTime.of(localDate, LocalTime.of(0, 0));
//            }
//        };
//    }
}
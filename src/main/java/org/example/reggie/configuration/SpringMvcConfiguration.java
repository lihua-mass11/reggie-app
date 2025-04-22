package org.example.reggie.configuration;

import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.JacksonObjectMapper;
import org.example.reggie.controller.filters.UserLoginFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
@Configuration
@Import({ServletConfiguration.class})
@MapperScan("org.example.reggie.mapper")
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class SpringMvcConfiguration {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {

            /**
             * 静态资源对象
             * @param registry
             */
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                log.info("静态资源加载");
                registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
                registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
            }

            /**
             * 转换器扩展
             * @param converters the list of configured converters to be extended
             */
            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                log.info("转换器加载");
                JacksonObjectMapper jacksonObjectMapper = new JacksonObjectMapper();
                MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
                //将json转换类型设置为自定义
                jsonConverter.setObjectMapper(jacksonObjectMapper);
                converters.add(0,jsonConverter);
            }
        };
    }

}

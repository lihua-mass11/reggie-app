package org.example.reggie.configuration;

import org.example.reggie.controller.filters.UserLoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class ServletConfiguration {

    /**
     * 过滤器注册
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        UserLoginFilter loginFilter = new UserLoginFilter();
        filterRegistrationBean.setFilter(loginFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}

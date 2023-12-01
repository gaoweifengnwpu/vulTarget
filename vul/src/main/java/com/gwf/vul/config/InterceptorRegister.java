package com.gwf.vul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 拦截器注册
 */
@Configuration
public class InterceptorRegister implements WebMvcConfigurer {

    /**
     * 把我们定义的拦截器类注册为Bean
     */
    @Bean
    public HandlerInterceptor getInterceptor() {
        return new UserInterceptor();
    }

    /**
     * 添加拦截器，并配置拦截地址
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> pathPatterns = new ArrayList<>();
        pathPatterns.add("/users/*");
        pathPatterns.add("/blogs/*");
        pathPatterns.add("/files/*");
        pathPatterns.add("/roles/*");
        registry.addInterceptor(getInterceptor()).addPathPatterns(pathPatterns).excludePathPatterns("/users/login","/users/register");
    }

}
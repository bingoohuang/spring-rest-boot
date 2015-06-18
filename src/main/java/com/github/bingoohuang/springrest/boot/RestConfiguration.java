package com.github.bingoohuang.springrest.boot;

import com.github.bingoohuang.springrest.boot.interceptor.SignInterceptor;
import com.github.bingoohuang.springrest.boot.interceptor.ThreadLocalInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan
public class RestConfiguration extends WebMvcConfigurerAdapter {
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SignInterceptor());
        registry.addInterceptor(new ThreadLocalInterceptor());
    }
}

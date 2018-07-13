package com.github.bingoohuang.springrest.boot;

import com.github.bingoohuang.blackcat.instrument.spring.BlackcatInterceptor;
import com.github.bingoohuang.springrest.boot.interceptor.SignInterceptor;
import com.github.bingoohuang.springrest.boot.interceptor.ThreadLocalInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan
public class RestConfiguration implements WebMvcConfigurer {
    public void addInterceptors(InterceptorRegistry registry) {
        if (HasBlackcat) {
            registry.addInterceptor(new BlackcatInterceptor());
        }
        registry.addInterceptor(new SignInterceptor());
        registry.addInterceptor(new ThreadLocalInterceptor());
    }

    public static boolean classExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (Throwable e) { // including ClassNotFoundException
            return false;
        }
    }

    public static final boolean HasBlackcat = classExists(
            "com.github.bingoohuang.blackcat.instrument.callback.Blackcat");
}

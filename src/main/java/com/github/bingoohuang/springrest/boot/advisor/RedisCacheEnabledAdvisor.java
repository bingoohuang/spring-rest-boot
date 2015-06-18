package com.github.bingoohuang.springrest.boot.advisor;

import com.github.bingoohuang.springrest.boot.annotations.RedisCacheEnabled;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

public class RedisCacheEnabledAdvisor extends AbstractPointcutAdvisor {
    final StaticMethodMatcherPointcut pointcut = new StaticMethodMatcherPointcut() {
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return method.isAnnotationPresent(RedisCacheEnabled.class);
        }
    };

    @Autowired
    RedisCacheEnabledMethodInterceptor interceptor;

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.interceptor;
    }
}
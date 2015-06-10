package com.github.bingoohuang.springrest.boot.advisor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

@Component
public class RequestMappingAdvisor extends AbstractPointcutAdvisor {
    final StaticMethodMatcherPointcut pointcut = new StaticMethodMatcherPointcut() {

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
//            String targetClassName = targetClass.getName();
//            if (!targetClassName.startsWith("org.github.bingoohuang")
//                    &&  !targetClassName.startsWith("com.raiyee")) return false;
            return /*!targetClass.isInterface() &&*/
                    method.isAnnotationPresent(RequestMapping.class);
        }
    };

    @Autowired
    NullReturnValueInterceptor interceptor;

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.interceptor;
    }
}

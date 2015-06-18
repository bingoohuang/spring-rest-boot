package com.github.bingoohuang.springrest.boot.advisor;

import com.github.bingoohuang.springrest.boot.interceptor.ThreadLocalInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.servlet.http.HttpServletResponse;

public class NullReturnValueInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            Object retValue = invocation.proceed();

            nullProcess(invocation, retValue);

            return retValue;
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    private void nullProcess(MethodInvocation invocation, Object retValue) {
        if (retValue != null) return;

        Class<?> returnType = invocation.getMethod().getReturnType();
        if (returnType == void.class || returnType == Void.class) return;

        HttpServletResponse response = ThreadLocalInterceptor.getResponse();
        response.addHeader("returnNull", "true");
    }
}

package com.github.bingoohuang.springrest.boot.advisor;

import com.github.bingoohuang.springrest.boot.interceptor.ThreadLocalInterceptor;
import lombok.val;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class NullReturnValueInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            val retValue = invocation.proceed();
            nullProcess(invocation, retValue);
            return retValue;
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    private void nullProcess(MethodInvocation invocation, Object retValue) {
        if (retValue != null) return;

        val returnType = invocation.getMethod().getReturnType();
        if (returnType == void.class || returnType == Void.class) return;

        val response = ThreadLocalInterceptor.getResponse();
        response.addHeader("returnNull", "true");
    }
}

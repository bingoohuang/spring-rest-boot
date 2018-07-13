package com.github.bingoohuang.springrest.boot.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThreadLocalInterceptor extends HandlerInterceptorAdapter {
    static ThreadLocal<HttpServletRequest> httpServletRequestTl = new ThreadLocal<>();
    static ThreadLocal<HttpServletResponse> httpServletResponseTl = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        httpServletRequestTl.set(request);
        httpServletResponseTl.set(response);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception ex) {
        httpServletRequestTl.remove();
        httpServletResponseTl.remove();
    }


    public static HttpServletResponse getResponse() {
        return httpServletResponseTl.get();
    }

    public static HttpServletRequest getRequest() {
        return httpServletRequestTl.get();
    }
}

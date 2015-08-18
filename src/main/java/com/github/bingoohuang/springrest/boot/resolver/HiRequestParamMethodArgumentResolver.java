package com.github.bingoohuang.springrest.boot.resolver;

import com.github.bingoohuang.asmvalidator.ex.AsmValidateException;
import com.github.bingoohuang.utils.codec.Json;
import com.google.common.primitives.Primitives;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static com.github.bingoohuang.asmvalidator.AsmParamsValidatorFactory.createValidators;
import static com.github.bingoohuang.asmvalidator.AsmParamsValidatorFactory.validate;

public class HiRequestParamMethodArgumentResolver
        extends RequestParamMethodArgumentResolver {
    public HiRequestParamMethodArgumentResolver(boolean useDefaultResolution) {
        super(useDefaultResolution);
    }

    public HiRequestParamMethodArgumentResolver(
            ConfigurableBeanFactory beanFactory, boolean useDefaultResolution) {
        super(beanFactory, useDefaultResolution);
    }

    @Override
    protected Object resolveName(
            String name, MethodParameter parameter,
            NativeWebRequest webRequest
    ) throws Exception {
        Object arg = super.resolveName(name, parameter, webRequest);
        Object obj = tryUnJson(parameter, arg);

        asmValidate(parameter, obj);

        return obj;
    }

    private void asmValidate(MethodParameter mp, Object obj) {
        boolean ok = createValidators(mp.getMethod());
        if (!ok) return;

        try {
            validate(mp.getMethod(), mp.getParameterIndex(), obj);
        } catch (AsmValidateException ex) {
            RequestParam rp = mp.getParameterAnnotation(RequestParam.class);
            if (StringUtils.isNotEmpty(rp.value()))
                ex.replaceFieldName("arg" + mp.getParameterIndex(), rp.value());

            throw ex;
        }
    }

    private Object tryUnJson(MethodParameter parameter, Object arg) {
        if (!(arg instanceof String)) return arg;

        try {
            return tryUnJson(parameter, (String) arg);
        } catch (Throwable e) {
            // ignore
        }

        return arg;
    }

    private Object tryUnJson(MethodParameter parameter, String str) {
        Class<?> parameterType = parameter.getParameterType();
        if (!maybeJsonType(parameterType)) return str;

        return tryParseAsJson(parameter, str, parameterType);
    }

    private Object tryParseAsJson(
            MethodParameter parameter, String str, Class<?> parameterType) {
        char c = str.charAt(0);
        boolean maybeJson = c == '{';
        if (maybeJson) return Json.unJson(str, parameterType);

        boolean maybeJsonArray = c == '[';
        if (!maybeJsonArray) return str;

        Type genericParamType = parameter.getGenericParameterType();
        if (genericParamType instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) genericParamType;
            Class argClz = (Class) paramType.getActualTypeArguments()[0];

            return Json.unJsonArray(str, argClz);
        }

        return str;
    }

    private boolean maybeJsonType(Class<?> parameterType) {
        if (parameterType.isPrimitive()) return false;
        if (Primitives.isWrapperType(parameterType)) return false;
        if (CharSequence.class.isAssignableFrom(parameterType)) return false;

        return true;
    }
}

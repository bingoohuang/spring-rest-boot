package com.github.bingoohuang.springrest.boot.annotations;

import java.lang.annotation.*;


@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCacheEnabled {
    long expirationMillis() default -1;

    long aheadMillis() default 10000;
}
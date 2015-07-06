package com.github.bingoohuang.springrest.boot;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RestConfiguration.class)
public @interface RestBootScan {
}

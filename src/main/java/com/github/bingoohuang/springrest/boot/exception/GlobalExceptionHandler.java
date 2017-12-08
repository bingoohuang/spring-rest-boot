package com.github.bingoohuang.springrest.boot.exception;

import com.github.bingoohuang.utils.net.Http;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice @Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(RestException.class)
    public void handleConflict(RestException ex, HttpServletResponse response) {
        log.error("error occured", ex);
        Http.error(response, ex.getHttpStatusCode(), ex);
    }

    @ExceptionHandler(Throwable.class)
    public void handleConflict(Throwable ex, HttpServletResponse response) {
        log.error("error occured", ex);
        Http.error(response, 500, ex);
    }
}

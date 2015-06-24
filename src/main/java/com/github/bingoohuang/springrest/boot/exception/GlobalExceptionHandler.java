package com.github.bingoohuang.springrest.boot.exception;

import com.github.bingoohuang.utils.net.Http;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(RestException.class)
    public void handleConflict(RestException ex, HttpServletResponse response) {
        logger.error("error occured", ex);
        Http.error(response, ex.getHttpStatusCode(), ex);
    }

    @ExceptionHandler(Throwable.class)
    public void handleConflict(Throwable ex, HttpServletResponse response) {
        logger.error("error occured", ex);
        Http.error(response, 500, ex);
    }
}

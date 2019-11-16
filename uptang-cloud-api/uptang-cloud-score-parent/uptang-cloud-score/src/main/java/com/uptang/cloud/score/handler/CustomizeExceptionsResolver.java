package com.uptang.cloud.score.handler;

import com.uptang.cloud.score.exception.ExcelException;
import com.uptang.cloud.starter.web.domain.ApiOut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-09 15:31
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Slf4j
@RestControllerAdvice
public class CustomizeExceptionsResolver {

    @ExceptionHandler(ExcelException.class)
    public ApiOut handleError(ExcelException ex) {
        ex.printStackTrace();
        log.error("excel {}", ex.getMessage());
        return ApiOut.newPrompt(ex.getMessage());
    }
}

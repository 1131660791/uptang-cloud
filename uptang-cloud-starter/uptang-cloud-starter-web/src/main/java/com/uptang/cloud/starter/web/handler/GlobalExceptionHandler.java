package com.uptang.cloud.starter.web.handler;

import com.uptang.cloud.starter.common.enums.ResponseCodeEnum;
import com.uptang.cloud.starter.common.exception.AuthenticationException;
import com.uptang.cloud.starter.common.exception.BusinessException;
import com.uptang.cloud.starter.web.Constants;
import com.uptang.cloud.starter.web.domain.ApiOut;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 统一异常处理
 *
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String PACKAGE_PREFIX = "com.uptang.cloud";

    /**
     * 参数校验异常统一处理
     *
     * @param ex 异常信息
     * @return 统一异常处理信息
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiOut handleError(MissingServletRequestParameterException ex) {
        return ApiOut.newParameterRequiredResponse(ex.getParameterName() + ", " + ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiOut handleError(MethodArgumentTypeMismatchException ex) {
        return ApiOut.newParameterRequiredResponse(ex.getName() + ", " + ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiOut handleError(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("%s(%s)", error.getDefaultMessage(), error.getField()))
                .collect(Collectors.joining(" | "));

        return ApiOut.newResponse(ResponseCodeEnum.PARAMETER_VALIDATED, errorMessage);
    }

    @ExceptionHandler(BindException.class)
    public ApiOut handleError(BindException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("%s(%s)", error.getDefaultMessage(), error.getField()))
                .collect(Collectors.joining(" | "));

        return ApiOut.newParameterRequiredResponse(errorMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ApiOut handleError(ConstraintViolationException ex) {
        log.error(ex.getMessage(), ex);
        String errorMessage = ex.getConstraintViolations().stream()
                .map(error -> String.format("%s(%s)", error.getMessage(), error.getPropertyPath()))
                .collect(Collectors.joining(" | "));

        return ApiOut.newParameterRequiredResponse(errorMessage);
    }

    /**
     * 认证异常
     *
     * @param request   HttpServletRequest
     * @param exception AuthenticationException
     * @return 统一异常处理信息
     */
    @ResponseStatus(code = HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    @ExceptionHandler(value = AuthenticationException.class)
    public ApiOut handleAuthenticationException(HttpServletRequest request, AuthenticationException exception) {
        ResponseCodeEnum responseCode = Optional.ofNullable(exception.getResponseCode()).orElse(ResponseCodeEnum.UNAUTHORIZED);
        return ApiOut.newResponse(responseCode, exception.getMessage());
    }

    /**
     * 业务异常
     *
     * @param request   HttpServletRequest
     * @param exception AuthenticationException
     * @return 统一异常处理信息
     */
    @ExceptionHandler(value = BusinessException.class)
    public ApiOut handleBusinessException(HttpServletRequest request, BusinessException exception) {
        return handleThrowable(request, exception);
    }

    @ExceptionHandler(value = {Throwable.class, NoHandlerFoundException.class})
    public ApiOut handleThrowable(HttpServletRequest request, Throwable ex) {
        ResponseCodeEnum responseCode = ResponseCodeEnum.SYSTEM_ERROR;
        String message = ex.getMessage();
        Pair<String, String> debugMsgPair;
        if (ex instanceof BusinessException) {
            BusinessException exception = (BusinessException) ex;
            if (null != exception.getResponseCode()) {
                responseCode = exception.getResponseCode();
                message = exception.getMessage();
            }
            debugMsgPair = getCause(ex);
            log.warn(String.format("Code:%s-%s, Msg:%s", responseCode.name(), responseCode.getCode(), exception.getMessage()), exception);
        } else if (ex instanceof ServletRequestBindingException && StringUtils.contains(ex.getMessage(), Constants.TOKEN_PARA_NAME)) {
            debugMsgPair = Pair.of(ex.getMessage(), StringUtils.EMPTY);
            responseCode = ResponseCodeEnum.LOGIN_FAILED;
            message = "请登录";
        } else if (ex instanceof IllegalArgumentException && StringUtils.contains(ex.getMessage(), "/inner/")) {
            message = "调用内部服务参数异常";
            debugMsgPair = getCause(ex);
        } else {
            debugMsgPair = getCause(ex);
        }

        // 解决内部服务反序列化失败问题
        if (StringUtils.isNotEmpty(debugMsgPair.getRight()) && StringUtils.contains(ex.getMessage(), "Inner")) {
            log.error("调用内部服务({})失败", debugMsgPair.getRight());
            return null;
        }

        log.error(debugMsgPair.getLeft());
        return new ApiOut.Builder<>().code(responseCode).message(message).debugMessage(debugMsgPair.getLeft()).build();
    }


    /**
     * 获取错误日志堆栈
     *
     * @param throwable 异常
     * @return left: 异常消息, right: 文件名
     */
    private Pair<String, String> getCause(Throwable throwable) {
        if (null == throwable) {
            return Pair.of(StringUtils.EMPTY, StringUtils.EMPTY);
        }

        StringBuilder detailInfo = new StringBuilder();
        String message = throwable.getMessage();
        if (Strings.isBlank(message)) {
            message = throwable.toString();
        }

        detailInfo.append(message);
        String fileName = "";
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        if (ArrayUtils.isNotEmpty(stackTraceElements)) {
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                if (StringUtils.startsWithIgnoreCase(stackTraceElement.getClassName(), PACKAGE_PREFIX)) {
                    fileName = stackTraceElement.getFileName();
                    detailInfo.append("@").append(stackTraceElement.toString());
                    break;
                }
            }
        }
        return Pair.of(detailInfo.toString(), fileName);
    }
}
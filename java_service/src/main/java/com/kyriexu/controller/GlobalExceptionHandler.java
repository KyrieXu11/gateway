package com.kyriexu.controller;

import com.kyriexu.exception.BaseException;
import com.kyriexu.exception.ResultStatus;
import com.kyriexu.utils.RespBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author KyrieXu
 * @since 2021/3/23 16:23
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public RespBean handlerBaseException(BaseException e) {
        ResultStatus resultStatus = e.getResultStatus();
        return RespBean.error(resultStatus.getCode(), resultStatus.getMsg());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RespBean handleNoHandlerFound(NoHandlerFoundException e, HttpServletRequest request) {
        return RespBean.error("没有" + request.getRequestURI() + "这样的接口");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public RespBean httpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        return RespBean.error(request.getMethod() + "方法不被允许访问" + request.getRequestURI());
    }

}

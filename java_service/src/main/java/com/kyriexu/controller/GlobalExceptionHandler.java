package com.kyriexu.controller;

import com.kyriexu.exception.BaseException;
import com.kyriexu.exception.ResultCode;
import com.kyriexu.utils.RespBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * @author KyrieXu
 * @since 2021/3/23 16:23
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public RespBean handlerBaseException(BaseException e) {
        ResultCode resultCode = e.getResultStatus();
        return RespBean.error(resultCode.getCode(), resultCode.getMsg());
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

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespBean missingServletRequestParameterException(MissingServletRequestParameterException e) {
        return RespBean.error(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespBean validation(ValidationException e) {
        if (e instanceof ConstraintViolationException) {
            return RespBean.error(e.getLocalizedMessage());
        }
        return RespBean.error("未知错误");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespBean methodArgumentNotValid(MethodArgumentNotValidException e) {
        return RespBean.error(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespBean handleDuplicateKeyException(DuplicateKeyException e) {
        return RespBean.error("数据重复，请检查后提交");
    }
}

package com.kyriexu.exception;

/**
 * @author KyrieXu
 * @since 2020/3/29 16:52
 **/
public class BaseException extends RuntimeException {
    private ResultCode resultCode;

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public BaseException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}

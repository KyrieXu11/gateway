package com.kyriexu.exception;

/**
 * @author KyrieXu
 * @since 2020/3/29 16:52
 **/
public class BaseException extends RuntimeException {
    private ResultCode resultCode;

    public ResultCode getResultStatus() {
        return resultCode;
    }

    public void setResultStatus(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public BaseException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}

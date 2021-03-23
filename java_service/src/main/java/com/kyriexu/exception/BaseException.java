package com.kyriexu.exception;

/**
 * @author KyrieXu
 * @since 2020/3/29 16:52
 **/
public class BaseException extends RuntimeException {
    private ResultStatus resultStatus;

    public ResultStatus getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    public BaseException(ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }
}

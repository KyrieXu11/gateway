package com.kyriexu.exception;

import com.kyriexu.utils.Constant;

/**
 * @author KyrieXu
 * @since 2020/3/29 16:54
 **/
public enum ResultStatus {
    JSON_PROCESS_EXCEPTION(Constant.COMMON_FAIL, "JSON转化异常"),
    OLD_PASS_WRONG_EXCEPTION(Constant.COMMON_FAIL, "原来的密码错了，检查一下吧"),
    ADMIN_NOT_LOGIN_EXCEPTION(Constant.COMMON_FAIL, "管理员未登陆，检查一下吧"),
    NO_SUCH_ADMIN_EXCEPTION(Constant.COMMON_FAIL, "没有该用户名对应的用户"),
    PASS_WRONG_EXCEPTION(Constant.COMMON_FAIL, "密码不正确，检查一下再试试吧"),
    PASS_IS_NULL_EXCEPTION(Constant.COMMON_FAIL, "密码不能为空"),
    ARGS_NULL_EXCEPTION(Constant.COMMON_FAIL, "检查参数是否填写正确");

    private int code;

    private String msg;

    ResultStatus() {
    }

    ResultStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

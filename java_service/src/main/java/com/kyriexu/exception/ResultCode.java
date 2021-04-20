package com.kyriexu.exception;

import com.kyriexu.common.utils.Constant;

/**
 * @author KyrieXu
 * @since 2020/3/29 16:54
 **/
public enum ResultCode {
    JSON_PROCESS_EXCEPTION(Constant.COMMON_FAIL, "JSON转化异常"),
    PAGE_NUM_ILLEGAL(Constant.COMMON_FAIL, "页数或者页面大小参数非法"),
    UPDATE_HTTP_RULE_FAIL(Constant.COMMON_FAIL, "更新 HTTP 服务失败"),
    UPDATE_TCP_RULE_FAIL(Constant.COMMON_FAIL, "更新服务失败"),
    ADD_HTTP_RULE_FAIL(Constant.COMMON_FAIL, "添加 HTTP 服务失败"),
    ADD_TCP_RULE_FAILED(Constant.COMMON_FAIL, "添加服务失败"),
    SERVICE_ID_ILLEGAL(Constant.COMMON_FAIL, "检查服务ID是否正确"),
    SIZE_NUM_LIMIT(Constant.COMMON_FAIL, "一页最多只能50条数据哦"),
    IP_WEIGHT_DIFFERENT(Constant.COMMON_FAIL, "ip 和 权重的数量不匹配"),
    OLD_PASS_WRONG_EXCEPTION(Constant.COMMON_FAIL, "原来的密码错了，检查一下吧"),
    SERVICE_NAME_ALREADY_EXIST(Constant.COMMON_FAIL, "服务名已存在"),
    SERVICE_PREFIX_DOMAIN_ALREADY_EXIST(Constant.COMMON_FAIL, "服务接入前缀或域名已存在"),
    ADMIN_NOT_LOGIN_EXCEPTION(Constant.COMMON_FAIL, "管理员未登陆，检查一下吧"),
    NO_SUCH_ADMIN_EXCEPTION(Constant.COMMON_FAIL, "没有该用户名对应的用户"),
    PASS_WRONG_EXCEPTION(Constant.COMMON_FAIL, "密码不正确，检查一下再试试吧"),
    NO_SUCH_SERVICE_TYPE(Constant.COMMON_FAIL, "检查一下服务类型是否正确"),
    PASS_IS_NULL_EXCEPTION(Constant.COMMON_FAIL, "密码不能为空"),
    SERVICE_ALREADY_DELETED(Constant.COMMON_FAIL, "服务已经被删除了"),
    APP_ALREADY_DELETED(Constant.COMMON_FAIL, "租户已删除，请勿重复删除"),
    APP_NO_EXIST(Constant.COMMON_FAIL, "租户不存在"),
    APP_ALREADY_EXIST(Constant.COMMON_FAIL, "租户已存在"),
    APP_INFORMATION_ERROR(Constant.COMMON_FAIL, "租户信息填写错误"),
    SERVICE_NO_EXIST(Constant.COMMON_FAIL, "服务不存在"),
    INTERNAL_EXCEPTION(Constant.SERVER_ERROR, "内部出错了，待会再试试吧"),
    ARGS_NULL_EXCEPTION(Constant.COMMON_FAIL, "检查参数是否填写正确");

    private int code;
    private String msg;

    ResultCode() {
    }

    ResultCode(int code, String msg) {
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

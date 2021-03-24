package com.kyriexu.utils;

/**
 * @author KyrieXu
 * @since 2021/3/23 12:50
 **/
public class RespBean {
    private int code;
    private String msg;
    private Object data;

    private RespBean(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private RespBean() {
    }

    public static RespBean ok(int code, String msg, Object data) {
        return new RespBean(code, msg, data);
    }

    public static RespBean ok(int code, String msg) {
        return new RespBean(code, msg, null);
    }

    public static RespBean ok(String msg) {
        return new RespBean(Constant.SUCCESS, msg, null);
    }

    public static RespBean ok(Object data) {
        return new RespBean(Constant.SUCCESS, "", data);
    }

    public static RespBean ok(String msg,Object data) {
        return new RespBean(Constant.SUCCESS, msg, data);
    }

    public static RespBean error(String msg) {
        return new RespBean(Constant.COMMON_FAIL, msg, null);
    }

    public static RespBean error(int code, String msg) {
        return new RespBean(code, msg, null);
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

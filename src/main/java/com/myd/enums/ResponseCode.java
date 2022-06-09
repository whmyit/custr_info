package com.myd.enums;

/**
 * 描述:
 *
 * @author Yangjinming
 * @create 2018/3/26 21:31
 */
public enum ResponseCode {
    SUCCESS(200, "操作成功"),
    ERROR(500, "操作失败"),
    EXIST(ERROR.getCode(), "数据已存在"),
    NOT_EXIST(ERROR.getCode(), "数据为空"),
    GET_SUCCESS(SUCCESS.getCode(), "查询成功"),
    GET_FAIL(ERROR.getCode(), "查询失败"),
    PARAM_ERROR(ERROR.getCode(), "参数不满足要求"),
    PARAM_NULL(ERROR.getCode(), "参数为空");


    private int code;
    private String msg;

    ResponseCode(int code, String msg) {
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

package com.myd.enums;

/**
 * @description: description
 * @author: daizij
 * @create: 2018-05-23 15:54
 **/
public enum CommonEnum {
    STATUS_VALID("1", "有效"),
    STATUS_DEL("0", "删除");

    private String code;
    private String desc;

    CommonEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

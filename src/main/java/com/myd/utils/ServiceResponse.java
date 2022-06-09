package com.myd.utils;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myd.enums.ResponseCode;

import java.io.Serializable;

/**
 * 描述: 统一返回
 */
public class ServiceResponse<T> implements Serializable {
    private static final long serialVersionUID = -4012170306337511959L;
    /**
     * 返回状态 0成功
     * -1 错误
     */
    private int resultCode;
    /**
     * 访问描述
     */
    private String resultDesc;

    /**
     * 返回数据
     */
    private T data;

    private String code ;

    public ServiceResponse() {

    }

    public int getResultCode() {
        return resultCode;
    }

    public void setresultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getresultDesc() {
        return resultDesc;
    }

    public void setresultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private ServiceResponse(int resultCode) {
        this.resultCode = resultCode;
    }

    private ServiceResponse(int resultCode, T data) {
        this.resultCode = resultCode;
        this.data = data;
    }

    private ServiceResponse(int resultCode, String resultDesc) {
        this.resultCode = resultCode;
        this.resultDesc = resultDesc;
    }

    private ServiceResponse(int resultCode, String resultDesc, T data) {
        this.resultCode = resultCode;
        this.resultDesc = resultDesc;
        this.data = data;
    }

    /**
     * 在序列化时由于该方法调用了这个属性会影响 顾需要使之不在json序列化结果当中
     *
     * @return
     */
    @JsonIgnore
    public boolean isSuccess() {
        return this.resultCode == ResponseCode.SUCCESS.getCode();
    }

    /**
     * 默认操作成功返回（含默认成功返回码和描述）xq
     *
     * @param <T>
     * @return
     */
    public static <T> ServiceResponse<T> createSuccess() {
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg());
    }

    /**
     * 默认操作错误返回（含默认错误返回码和描述）
     *
     * @param <T>
     * @return
     */
    public static <T> ServiceResponse<T> createError() {
        return new ServiceResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMsg());
    }


    /**
     * 返回成功
     * 根据描述和数据
     *
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ServiceResponse<T> createBySuccessMsgData(String msg, T data) {
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 返回失败
     * 根据描述和数据
     *
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ServiceResponse<T> createByErrorMsgData(String msg, T data) {
        return new ServiceResponse<T>(ResponseCode.ERROR.getCode(), msg, data);
    }

    /**
     * 返回失败
     * 根据数据
     *
     * @param <T>
     * @return
     */
    public static <T> ServiceResponse<T> createErrorByData(T data) {
        return new ServiceResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMsg(), data);
    }

    /**
     * 返回成功
     * 根据数据
     *
     * @param <T>
     * @return
     */
    public static <T> ServiceResponse<T> createSuccessByData(T data) {
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), data);
    }

    /**
     * 返回自定义对应响应resultCode和DATA
     *
     * @param ResponseCode
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ServiceResponse<T> createByResponseCodeData(ResponseCode ResponseCode, T data) {
        return new ServiceResponse<T>(ResponseCode.getCode(), ResponseCode.getMsg(), data);
    }

    /**
     * 返回自定义对应响应resultCode 无DATA数据
     *
     * @param ResponseCode
     * @param <T>
     * @return
     */
    public static <T> ServiceResponse<T> createByResponseCode(ResponseCode ResponseCode) {
        return new ServiceResponse<T>(ResponseCode.getCode(), ResponseCode.getMsg());
    }

    /**
     * 根据resultCode和msg返回
     *
     * @param
     * @return
     */
    public static <T> ServiceResponse<T> createByCodeMsg(int resultCode, String msg) {
        return new ServiceResponse<T>(resultCode, msg);
    }

}

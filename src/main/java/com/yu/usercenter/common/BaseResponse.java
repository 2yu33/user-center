package com.yu.usercenter.common;

/**
 * 结果的统一返回集
 * @param <T>
 */
public class BaseResponse<T> {
    int code;
    T data;
    String message;
    public BaseResponse(int code,T data, String message){
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public BaseResponse(int code,T data){
        this(code,data,"");
    }
}

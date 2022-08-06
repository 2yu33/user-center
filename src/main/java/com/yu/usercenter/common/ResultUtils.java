package com.yu.usercenter.common;

public class ResultUtils {
    public static <T> BaseResponse<T> success(T data){
       return new BaseResponse<>(200,data,"ok");
    }
}

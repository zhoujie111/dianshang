package com.zhoujie.dianshang.common;

import com.alibaba.fastjson.annotation.JSONField;

public class ServerResponse<T> {

    private int status;
    private String msg;

//    @JSONField(serialzeFeatures= {SerializerFeature.WriteMapNullValue})
    /**
     * fastjson默认会过滤掉value=null的key
     * 通过使用以上注解 再json序列化时候阔以显示
     */
    private T data;

    private ServerResponse(int status){
        this.status = status;
    }

    private ServerResponse(int status,String msg){
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status,T data){
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status,String msg,T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @JSONField(serialize = false)
    public boolean isSuccess(){
        return  this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus(){
        return status;
    }

    public String getMsg(){
        return msg;
    }

    public T getData(){
        return data;
    }

    public static ServerResponse createBySuccess(){
        return new ServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc());
    }

    public static <T>ServerResponse<T> createBySuccess(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T>ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T>ServerResponse<T> createBySuccess(String msg,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    public static <T>ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.FAILURE.getCode(),ResponseCode.FAILURE.getDesc());
    }

    public static <T>ServerResponse<T> createByError(String errorMsg){
        return new ServerResponse<T>(ResponseCode.FAILURE.getCode(),errorMsg);
    }

    public static <T>ServerResponse<T> createByError(int errorCode,String errorMsg){
        return new ServerResponse<T>(errorCode,errorMsg);
    }

}

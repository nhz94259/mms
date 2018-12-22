package com.ant.mms.utils;

import com.ant.mms.enums.ResultEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by wolf
 */

@lombok.Data
public class R<T> implements Serializable{

    private static final long serialVersionUID = 1L;

    private Status status= new Status();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Data data ;

    public R() {
    }

    public R(ResultEnum result) {
        this.status=new Status(result);
    }
    public R(Integer code, String msg) {
        this.status = new Status(code,msg);
    }

    public R(ResultEnum result,Data data) {
        this.status=new Status(result);
        this.data=data;
    }

    public Object getData(String key){
        return  this.data.get(key) ;
    }

    //默认 【code:1】【msg:成功】
    public static <T> R<T> success() {
        return new R<T>();
    }
    public Boolean  isOk() {
        if (this.getStatus().getCode().equals(ResultEnum.SUCCESS.getCode()))
            return true;
        return false;
    }
    public static <T> R<T> successMessage(String msg){
        return new R<T>(ResultEnum.SUCCESS.getCode(),msg);
    }

    public static <T> R<T>  error() {
        return new R<T>(ResultEnum.UNKNOW_ERROR.getCode(),ResultEnum.UNKNOW_ERROR.getMessage() );
    }

    public static <T> R<T> errorMessage(String errorMessage){
        return new R<T>(ResultEnum.ERROR.getCode(),errorMessage);
    }

    public static <T> R<T> errorCodeMessage(int errorCode,String errorMessage){
        return new R<T>(errorCode,errorMessage);
    }

    public static <T> R<T> errorResultEnum(ResultEnum resultEnum){
        return new R<T>(resultEnum.getCode(),resultEnum.getMessage());
    }

    public  R  put(String key, Object value) {

        Data data= Optional.ofNullable( this.getData()).orElseGet( ()->new Data());
        data.put(key, value);
        this.setData(data);
        return  this;
    }

    public  R  put(Map<String, Object> map) {
        Data data = new Data(map);
        this.setData(data);
        return  this;
    }




    /*数据集*/
    @Getter
    class Data<T> extends HashMap<String, Object> {

        private static final long serialVersionUID = 1L;

        public Data() {}

        public Data(String key, Object value) {
            put( key,value);
        }

        public Data(Map<String, Object> map) {
            put(map);
        }

        public  Data put(Map<String, Object> map) {

            this.putAll(map);
            return this;
        }

        public Data put(String key, Object value) {
            super.put(key, value);
            return this;
        }
        public  Object get(String key){
            return  this.get(key);
        }
    }

    /*状态集*/
    @lombok.Data
    public static class Status {

        /** 错误码. */
        private Integer code;

        /** 提示信息. */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String msg;

        public Status(Integer code, String msg){
            this.code=code;
            this.msg=msg;
        }
        /** 提示信息默认返回 code:1  msg：成功. */
        public Status(){
            this.code= ResultEnum.SUCCESS.getCode();
            this.msg=ResultEnum.SUCCESS.getMessage();
        }

        public Status(ResultEnum result){
            this.code= result.SUCCESS.getCode();
            this.msg=result.SUCCESS.getMessage();
        }

    }

}


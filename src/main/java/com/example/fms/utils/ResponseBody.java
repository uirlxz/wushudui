package com.example.fms.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;


@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ResponseBody<T> implements Serializable {

    private int code;

    private String msg;

    private T data;

    public ResponseBody() {

    }
    public ResponseBody(int code){
        this.code = code;
    }
    public ResponseBody(int code, String msg) {
        this.code = code;
        this.msg = msg;

    }

    public ResponseBody(int code, T data) {
        this.code = code;
        this.data = data;
    } public ResponseBody(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

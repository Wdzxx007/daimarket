package com.jishi.daichao.bean;

/**
 * 返回数据的父类
 * Created by laulee on 17/6/7.
 */

public class ResponseBean<T> {

    private int suc;
    private T data;
    private String msg;

    //http 响应码 code定义成功失败
    public int getCode() {
        return suc;
    }

    //http 部分是传递boolean类型定义成功失败
    public boolean isError() {
        return suc != 0;
    }

    public String getMessage() {
        return msg;
    }

    public T getValues() {
        return data;
    }
}

package com.jishi.daichao.bean;

/**
 * Created by Administrator on 2018/11/27.
 */

public class TheMessageEvent {
    private String message;

    public TheMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

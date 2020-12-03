package com.jishi.daichao.http.callback;

/**
 * Created by laulee on 17/8/7.
 */

public interface HttpCode {

    public static final int SUCCESS = 200;
    public static final int ERROR = 666666;
    public static final int TOKENERROR = 401;
    //系统异常
    public static final int SERVICE_ERROR = 999999;
    public static final int SERVICES_ERROR = 9999;
    //客户端异常
    public static final int CLIENT_ERROR = 00000001;
    //客户端已经是最高版本
    public static final int CLIENTHIGEST_ERROR = 000000013;

}

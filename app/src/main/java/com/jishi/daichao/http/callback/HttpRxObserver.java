package com.jishi.daichao.http.callback;

import com.jishi.daichao.http.error.ApiException;
import com.jishi.daichao.http.error.ErrorException;
import com.jishi.daichao.rx.BaseObserver;
import com.jishi.daichao.utils.ToastUtil;


/**
 * Created by laulee on 17/11/16.
 */

public abstract class HttpRxObserver<T> extends BaseObserver<T> {

    private final String TOKEN_ERROR = "当前账户登入权限已过期,请重新登入";

    @Override
    public void onApiException(ApiException exception) {
        switch (exception.getCode()) {
            case HttpCode.ERROR:
                ToastUtil.showToast( exception.getMessage());
                onError(exception.getCode(),exception.getMessage());
                break;
            case HttpCode.SERVICE_ERROR:
                ToastUtil.showToast( exception.getMessage());
                onError(exception.getCode(),exception.getMessage());
                break;
            case HttpCode.SERVICES_ERROR:
                ToastUtil.showToast( exception.getMessage());
                onError(exception.getCode(),exception.getMessage());
                break;
            case HttpCode.CLIENT_ERROR:
                ToastUtil.showToast( exception.getMessage());
                onError(exception.getCode(),exception.getMessage());
                break;
            case HttpCode.CLIENTHIGEST_ERROR:
                ToastUtil.showToast( exception.getMessage());
                onError(exception.getCode(),exception.getMessage());
                break;
            case HttpCode.TOKENERROR:
                ToastUtil.showToast( exception.getMessage());
                onError(exception.getCode(),exception.getMessage());
                break;
            default:
                onError(exception.getCode(),exception.getMessage());
                break;
        }
    }

    @Override
    public void onException(ErrorException exception) {
        onError(exception.getCode(),exception.getMessage());
    }
}

package com.jishi.daichao.rx;

import com.jishi.daichao.bean.ResponseBean;
import com.jishi.daichao.http.callback.ModelCallBack;
import com.jishi.daichao.http.error.ApiException;
import com.jishi.daichao.http.error.ErrorException;
import com.jishi.daichao.http.error.ExceptionEngine;

import io.reactivex.observers.DisposableObserver;

/**
 * 封装回调
 * Created by laulee on 17/11/16.
 */

public abstract class BaseObserver<T> extends DisposableObserver<ResponseBean<T>>
        implements ModelCallBack<T> {
    @Override
    public void onError(Throwable e) {
        //网络或解析错误
        _onError(ExceptionEngine.handleException(e));
    }

    //接口异常处理，比如token错误
    protected void _onApiError(ApiException e) {
        onApiException(e);
    }

    @Override
    public void onNext(ResponseBean<T> tResponseBean) {
        if (!tResponseBean.isError()) {
            onSuccess(tResponseBean.getValues());
        } else {
            _onApiError(
                    new ApiException(tResponseBean.getCode(), tResponseBean.getMessage()));
        }
    }


    //服务器异常处理以及解析数据错误处理
    protected void _onError(ErrorException e) {
        onException(e);
    }

    @Override
    public void onComplete() {

    }

}

package com.jishi.daichao.presenter;


import com.jishi.daichao.base.params.AuthCodeParams;
import com.jishi.daichao.base.params.ImageCodeParams;
import com.jishi.daichao.entity.ImageCodeEntity;
import com.jishi.daichao.http.RetrofitModel;
import com.jishi.daichao.http.api.SystemApi;
import com.jishi.daichao.http.callback.HttpRxObserver;
import com.jishi.daichao.mvp.presenter.RxPresenter;
import com.jishi.daichao.presenter.contact.LoginAndRegisterContact;
import com.jishi.daichao.presenter.contact.LoginPasswordContact;
import com.jishi.daichao.utils.LogUtil;
import com.jishi.daichao.utils.ParameterEncryptionUtil;
import com.jishi.daichao.utils.ToastUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/5/11.
 */

public class LoginPasswordPresenter extends RxPresenter<LoginPasswordContact.IView> implements LoginPasswordContact.IPresenter {

    @Override
    public void doLoginPassword(String mobile) {

    }

    @Override
    public void getImageCode(ImageCodeParams imageCodeParams) {
        addSubscrebe(RetrofitModel.getInstance().getSystemApi().getImageCode(ParameterEncryptionUtil.getInstance().obj2Map(imageCodeParams)), new HttpRxObserver<ImageCodeEntity>() {
            @Override
            public void onSuccess(ImageCodeEntity imageCodeEntity) {
                mView.getImageCodeSuccess(imageCodeEntity);
            }

            @Override
            public void onError(int code, String message) {
                mView.getImageCodeFailed(message);
            }
        });
    }
}

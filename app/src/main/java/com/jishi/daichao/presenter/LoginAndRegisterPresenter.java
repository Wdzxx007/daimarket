package com.jishi.daichao.presenter;


import com.jishi.daichao.base.params.AuthCodeParams;
import com.jishi.daichao.http.api.SystemApi;
import com.jishi.daichao.http.callback.HttpRxObserver;
import com.jishi.daichao.mvp.presenter.RxPresenter;
import com.jishi.daichao.presenter.contact.LoginAndRegisterContact;
import com.jishi.daichao.utils.ParameterEncryptionUtil;


/**
 * Created by Administrator on 2018/5/11.
 */

public class LoginAndRegisterPresenter extends RxPresenter<LoginAndRegisterContact.IView> implements LoginAndRegisterContact.IPresenter {
    private SystemApi systemApi;

    LoginAndRegisterPresenter(SystemApi systemApi) {
        this.systemApi = systemApi;
    }


    @Override
    public void getAuthCode(String mobile) {

    }
}

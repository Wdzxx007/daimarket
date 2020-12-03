package com.jishi.daichao.presenter;

import com.jishi.daichao.base.params.AuthCodeParams;
import com.jishi.daichao.http.api.SystemApi;
import com.jishi.daichao.http.callback.HttpRxObserver;
import com.jishi.daichao.mvp.presenter.RxPresenter;
import com.jishi.daichao.presenter.contact.AuthCodeContact;
import com.jishi.daichao.presenter.contact.LoginAndRegisterContact;
import com.jishi.daichao.utils.ParameterEncryptionUtil;

/**
 * Created by Administrator on 2018/5/11.
 */

public class AuthCodePresenter extends RxPresenter<AuthCodeContact.IView> implements AuthCodeContact.IPresenter {
    private SystemApi systemApi;

    AuthCodePresenter(SystemApi systemApi) {
        this.systemApi = systemApi;
    }


    @Override
    public void checkAuthCode(String mobile) {

    }

    @Override
    public void retryGetAuthCode() {

    }
}

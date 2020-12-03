package com.jishi.daichao.presenter;


import com.jishi.daichao.http.api.SystemApi;
import com.jishi.daichao.mvp.presenter.RxPresenter;
import com.jishi.daichao.presenter.contact.AuthCodeContact;
import com.jishi.daichao.presenter.contact.RetrivePasswordContact;

/**
 * Created by Administrator on 2018/5/11.
 */

public class RetrivePasswordPresenter extends RxPresenter<RetrivePasswordContact.IView> implements RetrivePasswordContact.IPresenter {
    private SystemApi systemApi;

    RetrivePasswordPresenter(SystemApi systemApi) {
        this.systemApi = systemApi;
    }


    @Override
    public void getAuthCode(String mobile) {

    }

    @Override
    public void checkAuthCode() {

    }
}

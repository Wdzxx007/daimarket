package com.jishi.daichao.presenter;


import com.jishi.daichao.http.api.SystemApi;
import com.jishi.daichao.mvp.presenter.RxPresenter;
import com.jishi.daichao.presenter.contact.AuthCodeContact;
import com.jishi.daichao.presenter.contact.SetPwdContact;

/**
 * Created by Administrator on 2018/5/11.
 */

public class SetPwdPresenter extends RxPresenter<SetPwdContact.IView> implements SetPwdContact.IPresenter {
    private SystemApi systemApi;

    SetPwdPresenter(SystemApi systemApi) {
        this.systemApi = systemApi;
    }


    @Override
    public void passwordSubmit(String mobile) {

    }
}

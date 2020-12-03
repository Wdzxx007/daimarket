package com.jishi.daichao.presenter;


import com.jishi.daichao.http.api.SystemApi;
import com.jishi.daichao.mvp.presenter.RxPresenter;
import com.jishi.daichao.presenter.contact.ResetPwdContact;
import com.jishi.daichao.presenter.contact.SetPwdContact;


/**
 * Created by Administrator on 2018/5/11.
 */

public class ResetPwdPresenter extends RxPresenter<ResetPwdContact.IView> implements ResetPwdContact.IPresenter {
    private SystemApi systemApi;

    ResetPwdPresenter(SystemApi systemApi) {
        this.systemApi = systemApi;
    }


    @Override
    public void passwordSubmit(String mobile) {

    }
}

package com.jishi.daichao.presenter;


import com.jishi.daichao.http.api.SystemApi;
import com.jishi.daichao.mvp.presenter.RxPresenter;
import com.jishi.daichao.presenter.contact.AuthCodeContact;
import com.jishi.daichao.presenter.contact.BorrowFragmentContact;


/**
 * Created by Administrator on 2018/5/11.
 */

public class BorrowFragmentPresenter extends RxPresenter<BorrowFragmentContact.IView> implements BorrowFragmentContact.IPresenter {
    private SystemApi systemApi;

    BorrowFragmentPresenter(SystemApi systemApi) {
        this.systemApi = systemApi;
    }


    @Override
    public void getBorrowList() {

    }
}

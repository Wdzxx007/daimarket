package com.jishi.daichao.presenter;


import com.jishi.daichao.http.api.SystemApi;
import com.jishi.daichao.mvp.presenter.RxPresenter;
import com.jishi.daichao.presenter.contact.BorrowFragmentContact;
import com.jishi.daichao.presenter.contact.HomeFragmentContact;


/**
 * Created by Administrator on 2018/5/11.
 */

public class HomeFragmentPresenter extends RxPresenter<HomeFragmentContact.IView> implements HomeFragmentContact.IPresenter {
    private SystemApi systemApi;

    HomeFragmentPresenter(SystemApi systemApi) {
        this.systemApi = systemApi;
    }


    @Override
    public void getHomeInfo() {

    }
}

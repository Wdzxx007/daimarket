package com.jishi.daichao.presenter.contact;

import com.jishi.daichao.mvp.view.IBaseView;

import java.util.Map;

/**
 * Created by Administrator on 2019/2/14.
 */

public interface LoginAndRegisterContact {
    interface IView extends IBaseView {

        void onServerFiled(String s);

        void getAuthCodeSuccess();

    }

    interface IPresenter {
        void getAuthCode(String mobile);
    }
}

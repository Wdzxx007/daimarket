package com.jishi.daichao.presenter.contact;

import com.jishi.daichao.mvp.view.IBaseView;

/**
 * Created by Administrator on 2019/2/14.
 */

public interface AuthCodeContact {
    interface IView extends IBaseView {

        void onServerFiled(String s);

        void checkAuthCodeSuccess();

        void retryGetAuthCodeSuccess();

    }

    interface IPresenter {

        void checkAuthCode(String mobile);

        void retryGetAuthCode();
    }
}

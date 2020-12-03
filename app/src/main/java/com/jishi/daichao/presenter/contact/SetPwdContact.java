package com.jishi.daichao.presenter.contact;

import com.jishi.daichao.mvp.view.IBaseView;

/**
 * Created by Administrator on 2019/2/14.
 */

public interface SetPwdContact {
    interface IView extends IBaseView {

        void onServerFiled(String s);

        void passwordSubmitSuccess();

    }

    interface IPresenter {
        void passwordSubmit(String mobile);
    }
}

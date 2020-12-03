package com.jishi.daichao.presenter.contact;

import com.jishi.daichao.base.params.ImageCodeParams;
import com.jishi.daichao.entity.ImageCodeEntity;
import com.jishi.daichao.mvp.view.IBaseView;

/**
 * Created by Administrator on 2019/2/14.
 */

public interface LoginPasswordContact {
    interface IView extends IBaseView {

        void onServerFiled(String s);

        void doLoginPasswordSuccess();

        void getImageCodeSuccess(ImageCodeEntity imageCodeEntity);

        void getImageCodeFailed(String s);

    }

    interface IPresenter {
        void doLoginPassword(String mobile);

        void getImageCode(ImageCodeParams imageCodeParams);
    }
}

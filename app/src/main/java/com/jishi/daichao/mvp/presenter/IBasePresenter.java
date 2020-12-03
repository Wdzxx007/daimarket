package com.jishi.daichao.mvp.presenter;

import com.jishi.daichao.mvp.view.IBaseView;

/**
 * Created by laulee on 16/12/25.
 */

public interface IBasePresenter<V extends IBaseView> {

    void attachView(V view);

    void destroyView();
}

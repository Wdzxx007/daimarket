package com.jishi.daichao.mvp.view;

import android.content.Context;

/**
 * Created by laulee on 16/12/18.
 */

public interface IBaseView {

    Context getContext();

    //    LifecycleProvider getLifecleProvider();

    void showLoadingProgress();

    void hideLoadingProgress();
}

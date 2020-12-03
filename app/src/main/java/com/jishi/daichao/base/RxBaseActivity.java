package com.jishi.daichao.base;

import android.content.Context;
import android.os.Bundle;

import com.jishi.daichao.mvp.presenter.RxPresenter;
import com.jishi.daichao.mvp.view.IBaseView;
import com.jishi.daichao.widget.LoadingDialog;


/**
 * 基类presenter activity
 * Created by laulee on 16/12/18.
 */

public abstract class RxBaseActivity<P extends RxPresenter> extends BaseActivity
        implements IBaseView {

    protected P mPresenter; //presenter 对象

    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.destroyView();
        super.onDestroy();
    }

    protected void initPresenter() {
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoadingProgress() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    @Override
    public void hideLoadingProgress() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}

package com.jishi.daichao.fragments;
import android.view.View;
import com.jishi.daichao.R;
import com.jishi.daichao.base.RxBaseFragment;
import com.jishi.daichao.presenter.HomeFragmentPresenter;
import com.jishi.daichao.presenter.contact.HomeFragmentContact;

/**
 * Created by Administrator on 2019/2/13.
 */

public class HomeFragment extends RxBaseFragment<HomeFragmentPresenter> implements HomeFragmentContact.IView {
    @Override
    public void showLoadingProgress() {

    }

    @Override
    public void hideLoadingProgress() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home;
    }



    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initParams() {

    }

    @Override
    public void onServerFiled(String s) {

    }

    @Override
    public void getHomeInfoSuccess() {

    }
}

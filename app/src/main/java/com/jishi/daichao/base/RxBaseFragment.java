package com.jishi.daichao.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jishi.daichao.mvp.presenter.RxPresenter;
import com.jishi.daichao.mvp.view.IBaseView;


/**
 * Created by laulee on 17/2/27.
 */

public abstract class RxBaseFragment<P extends RxPresenter> extends BaseFragment
        implements IBaseView {

    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState ) {
        return super.onCreateView( inflater, container, savedInstanceState );
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState ) {
        if(mPresenter!=null){
            mPresenter.attachView( this );
        }
        super.onViewCreated( view, savedInstanceState );
    }


    protected abstract void initView( View view );

    protected abstract void initParams();

    @Override
    public void onDestroy() {
        super.onDestroy( );
        if( mPresenter != null )
            mPresenter.destroyView( );
    }

    @Override
    public Context getContext() {
        return getActivity( );
    }
}

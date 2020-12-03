package com.jishi.daichao.mvp.presenter;

import com.jishi.daichao.mvp.view.IBaseView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by laulee on 17/3/13.
 * 基于Rx的presenter
 */

public class RxPresenter<T extends IBaseView> implements IBasePresenter<T> {

    protected T mView;
    protected CompositeDisposable mCompositeDisposable;
    //    protected M mModel;
    //    protected LifecycleProvider lifecycleProvider;

    protected void unSubscribe() {
        if( mCompositeDisposable != null ) {
            mCompositeDisposable.clear( );
        }
    }

    protected void addSubscrebe( Disposable disposable ) {
        if( mCompositeDisposable == null ) {
            mCompositeDisposable = new CompositeDisposable( );
        }
        mCompositeDisposable.add( disposable );
    }

    @Override
    public void attachView( T view ) {
        this.mView = view;
    }

    @Override
    public void destroyView() {
        this.mView = null;
        unSubscribe( );
    }

    /**
     * 线程调度
     *
     * @param observable
     * @param consumer
     * @return
     */
    public <T> void addSubscrebe( Observable<T> observable, DisposableObserver<T> consumer ) {
        Disposable disposable = observable.subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) ).subscribeWith( consumer );
        addSubscrebe( disposable );
    }
}

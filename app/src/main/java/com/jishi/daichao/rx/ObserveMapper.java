package com.jishi.daichao.rx;

import com.jishi.daichao.bean.ResponseBean;
import com.jishi.daichao.http.error.ApiException;
import com.jishi.daichao.utils.JsonUtil;
import com.jishi.daichao.utils.LogUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by laulee on 17/3/13.
 */

public class ObserveMapper {

    public static <T> T mapResult( ResponseBean<T> httpResult ) {
        return httpResult.getValues( );
    }

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new ObservableTransformer<T, T>( ) {
            @Override
            public Observable<T> apply( Observable<T> observable ) {
                return observable.subscribeOn( Schedulers.io( ) )
                        .observeOn( AndroidSchedulers.mainThread( ) );
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<ResponseBean<T>, T> transformer() {   //compose判断结果
        return new ObservableTransformer<ResponseBean<T>, T>( ) {
            @Override
            public Observable<T> apply( Observable<ResponseBean<T>> httpResult ) {
                return httpResult.flatMap( new Function<ResponseBean<T>, Observable<T>>( ) {
                    @Override
                    public Observable<T> apply( ResponseBean<T> httpResult ) {
                        LogUtil.json( JsonUtil.toJson( httpResult ) );
                        if( !httpResult.isError( ) ) {
                            return createData( httpResult.getValues( ) );
                        } else {
                            return Observable
                                    .error( new ApiException( httpResult.getCode( ), "请求错误" ) );
                        }
                    }

                } );
            }
        };
    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData( final T data ) {
        return Observable.create( new ObservableOnSubscribe<T>( ) {
            @Override
            public void subscribe( ObservableEmitter<T> subscriber ) {
                try {
                    subscriber.onNext( data );
                    subscriber.onComplete( );
                } catch( Exception e ) {
                    subscriber.onError( e );
                }
            }
        } );
    }
}

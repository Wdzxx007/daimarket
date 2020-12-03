package com.jishi.daichao.http;

import android.util.Log;


import com.jishi.daichao.utils.ProConfigUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2018/7/9.
 */

public class RetrofitWrapper {
    private static volatile RetrofitWrapper sInstance;
    private Retrofit retrofit;

    private RetrofitWrapper() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ProConfigUtil.getConfigKey("BASE_URL"))      //访问主机地址
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()) //解析方式
                .client(getOkhttpClient())
                .build();
    }

    public static RetrofitWrapper getInstance() {
        if (null == sInstance) {
            synchronized (RetrofitWrapper.class) {
                if (null == sInstance) {
                    sInstance = new RetrofitWrapper();
                }
            }
        }
        return sInstance;
    }

    ;

    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }

    private OkHttpClient getOkhttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        //打印retrofit日志
                        Log.e("RetrofitLog", "retrofitBack = " + message);
                    }
                });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //加密拦截器
        //        EncodeInterceptor encodeInterceptor = new EncodeInterceptor( );
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)                           // 自动重连
                .connectTimeout(3, TimeUnit.MINUTES)                     // 连接超时
                .readTimeout(20, TimeUnit.SECONDS)                        // 读取超时
                .writeTimeout(20, TimeUnit.SECONDS)                       // 秒写入超时
                .build();
        return okHttpClient;
    }

    /**
     * 请求拦截器，修改请求header
     */
    private class RequestInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json; charset=UTF-8")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Accept", "*/*")
                    .addHeader("Access-Control-Allow-Origin", "*")
                    .addHeader("Access-Control-Allow-Headers", "X-Requested-With")
                    .addHeader("Vary", "Accept-Encoding")
                    .addHeader("Authorization", "66666")
                    .build();
            Log.v("zcb", "request:" + request.toString());
            Log.v("zcb", "request headers:" + request.headers().toString());

            return chain.proceed(request);
        }
    }

}

package com.jishi.daichao.app;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.jishi.daichao.BuildConfig;
import com.jishi.daichao.utils.LogUtil;
import com.jishi.daichao.utils.SharedPreferencesUtil;
import com.jishi.daichao.utils.ToastUtil;

/**
 * Created by Administrator on 2018/8/1.
 */

public class App extends Application {
    protected static Application instance;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        instance = this;
        //log
        LogUtil.initLog(BuildConfig.DEBUG, this);
        //初始化Toast
        ToastUtil.getInstance(this);
        //初始化SharedPreferencesUtil
        SharedPreferencesUtil.init(instance);
        MultiDex.install(this);
    }

    // 清除缓存
    public void clear() {

    }

    public static Context getContext() {
        return mContext;
    }

    public static Application getInstance() {
        return instance;
    }
}

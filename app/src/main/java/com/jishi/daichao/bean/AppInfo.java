package com.jishi.daichao.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2018/11/12.
 */

public class AppInfo {
    public int versionCode = 0;
    //名称
    public String appname = "";
    //包
    public String packagename = "";
    public String versionName = "";

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Drawable getAppicon() {
        return appicon;
    }

    public void setAppicon(Drawable appicon) {
        this.appicon = appicon;
    }

    //图标
    public Drawable appicon = null;
}

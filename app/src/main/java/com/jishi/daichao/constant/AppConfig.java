package com.jishi.daichao.constant;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.jishi.daichao.MainActivity;
import com.jishi.daichao.app.App;
import com.jishi.daichao.utils.ListDataSave;
import com.jishi.daichao.utils.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by sean on 15/12/26.
 */
public class AppConfig {
    //Activity储存
    private static Map<String, FragmentActivity> activities
            = new HashMap<String, FragmentActivity>();

    private static String mobile;
    private static String randomCode;


    public static void addActivity(Context context) {
        if (context instanceof FragmentActivity) {
            activities.put(context.getClass().getName(), (FragmentActivity) context);
        }
    }

    public static void removeActivity(Context context) {
        if (context instanceof FragmentActivity) {
            activities.remove(context.getClass().getName());
        }
    }

    /**
     * 关闭页面
     */
    public static void closePage() {
        Set<Activity> activitySet = com.jishi.daichao.app.ActivityManager.getAllActivities();
        Iterator<Activity> iter = activitySet.iterator();
        while (iter.hasNext()) {
            Activity activity = iter.next();
            if (!activity.getClass().getName().equals(MainActivity.class.getName())) {
                activity.finish();
            }
        }

        SharedPreferencesUtil.saveAccessToken("");

        SharedPreferencesUtil.saveMobilePhone("");
        ListDataSave listDataSave = new ListDataSave(App.getContext(), "Moneylist");
        listDataSave.setDataList("userMoneylist", null);
        AppConfig.setMobile("");
        AppConfig.setRandomCode("");
    }


    public static String getMobile() {
        return mobile;
    }

    public static String getRandomCode() {
        return randomCode;
    }


    public static void setMobile(String mobile) {
        AppConfig.mobile = mobile;
    }

    public static void setRandomCode(String randomCode) {
        AppConfig.randomCode = randomCode;
    }


}

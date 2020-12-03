package com.jishi.daichao.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

/**
 * Created by laulee on 17/10/25.
 * sharedPreferences工具类
 */

public class SharedPreferencesUtil {
    private static final String fileName = "KKD_android";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String MOBILE_PHONE = "mobile_phone";
    private static Context mContext;

    public static void init( Context context ) {
        mContext = context.getApplicationContext( );
    }

    /**
     * 获得保存数据
     *
     * @param key
     * @param defValue
     * @return
     */
    private static String getString(String key, @Nullable String defValue ) {
        return PreferenceManager.getDefaultSharedPreferences( mContext ).getString( key, defValue );
    }

    /**
     * 保存字符串
     *
     * @param key
     * @param value
     */
    private static void saveString(String key, @Nullable String value ) {
        PreferenceManager.getDefaultSharedPreferences( mContext ).edit( ).putString( key, value )
                .apply( );
    }

    /**
     * 保存token
     *
     * @param value
     */
    public static void saveAccessToken( String value ) {
        saveString( ACCESS_TOKEN, value );
    }

    /**
     * 获得token
     *
     * @return
     */
    public static String getAccessToken() {
        return getString( ACCESS_TOKEN, "" );
    }


    /**
     * 保存手机号
     *
     * @param value
     */
    public static void saveMobilePhone( String value ) {
        saveString( MOBILE_PHONE, value );
    }

    /**
     * 获得手机号
     *
     * @return
     */
    public static String getMobilePhone() {
        return getString( MOBILE_PHONE, "" );
    }
    /**
     * 保存数据到文件
     */
    public static void saveData(Context context, String key, Object value) {
        String type = value.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) value);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) value);
        } else if ("String".equals(type)) {
            editor.putString(key, (String) value);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) value);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) value);
        }
        editor.commit();
    }

    /**
     * 从文件中读取数据
     */
    public static Object getData(Context context, String key, Object defaultValue) {
        String type = defaultValue.getClass().getSimpleName();
        SharedPreferences sharedPreferences = context.getSharedPreferences
                (fileName, Context.MODE_PRIVATE);

        //defValue为为默认值，如果当前获取不到数据就返回它
        if ("Integer".equals(type)) {
            return sharedPreferences.getInt(key, (Integer) defaultValue);
        } else if ("Boolean".equals(type)) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultValue);
        } else if ("String".equals(type)) {
            return sharedPreferences.getString(key, (String) defaultValue);
        } else if ("Float".equals(type)) {
            return sharedPreferences.getFloat(key, (Float) defaultValue);
        } else if ("Long".equals(type)) {
            return sharedPreferences.getLong(key, (Long) defaultValue);
        }
        return null;
    }


    /**
     * 清除所有数据
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
        //不知道哪个大神说apply方法好像更高效一点,哈哈哈哈哈,水平不够,我觉得只要app不崩溃
    }

}

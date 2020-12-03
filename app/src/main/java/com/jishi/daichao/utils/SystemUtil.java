package com.jishi.daichao.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;


import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sean on 15/9/23.
 */
public class SystemUtil {
    private static final String SAVE_TOKEN = "saveToken";
    private static final String SAVE_TYPE = "saveType";
    private static final String IS_FIRST_LOAD = "isFirstLoad";
    private static final String IS_OPEN_PUSH = "isOpenPush";
    private static final String LOCAL_PASSWORD = "localPassword";
    private static final int TOTAL_ITEM = 5;
    private static final String SUPER_PASSWORD_ERROR = "密码中不能包含汉字和空格哦";
    private static final String IDENTITY_CARD_ERROR = "请输入正确的身份证号";
    private static String token;
    private static String PHONE_NUMBER_ERROR = "请输入正确手机号码";
    private static String PASSWORD_ERROR = "密码应在6~20位之间";
    private static final String APP_SAVE = "appSave";
    private static final String DEVICE_DENSITY = "deviceDensity";
    private static final String DEVICE_WIDTH = "deviceWidth";
    private static final String DEVICE_HEIGHT = "deviceHeight";
    private static String deviceId;
    private static float deviceDensity = -1;
    private static int deviceWidth = -1;
    private static int deviceHeight = -1;
    private static final String CARD_ID_ERROR = "银行卡号不正确";

    /**
     * 检测网络连接
     *
     * @return
     */
    public static boolean isNetworkConnected( Context context ) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getApplicationContext( ).getSystemService( Context.CONNECTIVITY_SERVICE );
        return connectivityManager.getActiveNetworkInfo( ) != null;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px( Context context, float dpValue ) {
        final float scale = context.getResources( ).getDisplayMetrics( ).density;
        return (int) ( dpValue * scale + 0.5f );
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp( Context context, float pxValue ) {
        final float scale = context.getResources( ).getDisplayMetrics( ).density;
        return (int) ( pxValue / scale + 0.5f );
    }

    /**
     * 获取设备唯一ID
     *
     * @param context
     * @return
     */
    public static String getDeviceOnlyId( Context context ) {

        if( deviceId != null ) {
            return deviceId;
        } else {
            TelephonyManager TelephonyMgr = (TelephonyManager) context
                    .getSystemService( Context.TELEPHONY_SERVICE );
            deviceId = TelephonyMgr.getDeviceId( );
            return deviceId;
        }
    }



    /**
     * 检查手机号
     *
     * @param phoneNum 手机号码
     * @return
     */
    public static boolean checkPhoneNum( String phoneNum ) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        Pattern p = Pattern.compile( regex );
        if( !p.matcher( phoneNum ).matches( ) ) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断是否登录
     *
     * @return
     */
    public static boolean checkLoginState() {
        return !TextUtils.isEmpty( SharedPreferencesUtil.getAccessToken( ) );
    }

    /**
     * 获取设备dp值
     *
     * @param context
     * @return
     */
    public static float getDeviceDensity( Context context ) {
        SharedPreferences sp = context.getSharedPreferences( APP_SAVE, Context.MODE_PRIVATE );
        deviceDensity = sp.getFloat( DEVICE_DENSITY, -1 );
        WindowManager windowManager = (WindowManager) context
                .getSystemService( Context.WINDOW_SERVICE );
        DisplayMetrics dm = new DisplayMetrics( );
        windowManager.getDefaultDisplay( ).getMetrics( dm );
        deviceDensity = dm.density;
        deviceWidth = dm.widthPixels;
        deviceHeight = dm.heightPixels;

        SharedPreferences.Editor editor = sp.edit( );
        editor.putFloat( DEVICE_DENSITY, deviceDensity );
        editor.putInt( DEVICE_WIDTH, deviceWidth );
        editor.putInt( DEVICE_HEIGHT, deviceHeight );
        editor.commit( );
        return deviceDensity;
    }

    /**
     * 获取设备宽度
     *
     * @param context
     * @return
     */
    public static int getDeviceWidth( Context context ) {
        SharedPreferences sp = context.getSharedPreferences( APP_SAVE, Context.MODE_PRIVATE );
        deviceWidth = sp.getInt( DEVICE_WIDTH, -1 );
        WindowManager windowManager = (WindowManager) context
                .getSystemService( Context.WINDOW_SERVICE );
        DisplayMetrics dm = new DisplayMetrics( );
        windowManager.getDefaultDisplay( ).getMetrics( dm );
        deviceDensity = dm.density;
        deviceWidth = dm.widthPixels;
        deviceHeight = dm.heightPixels;

        SharedPreferences.Editor editor = sp.edit( );
        editor.putFloat( DEVICE_DENSITY, deviceDensity );
        editor.putInt( DEVICE_WIDTH, deviceWidth );
        editor.putInt( DEVICE_HEIGHT, deviceHeight );
        editor.commit( );
        return deviceWidth;
    }

    /**
     * 获取用户设备高度
     *
     * @param context
     * @return
     */
    public static int getDeviceHeight( Context context ) {
        SharedPreferences sp = context.getSharedPreferences( APP_SAVE, Context.MODE_PRIVATE );
        deviceHeight = sp.getInt( DEVICE_HEIGHT, -1 );
        if( deviceHeight < 0 && context instanceof Activity ) {
            DisplayMetrics dm = new DisplayMetrics( );
            ( (Activity) context ).getWindowManager( ).getDefaultDisplay( ).getMetrics( dm );
            deviceDensity = dm.density;
            deviceWidth = dm.widthPixels;
            deviceHeight = dm.heightPixels;

            SharedPreferences.Editor editor = sp.edit( );
            editor.putFloat( DEVICE_DENSITY, deviceDensity );
            editor.putInt( DEVICE_WIDTH, deviceWidth );
            editor.putInt( DEVICE_HEIGHT, deviceHeight );
            editor.commit( );
            return deviceHeight;
        } else {
            return deviceHeight;
        }
    }


    /**
     * 隐藏手机号
     * @param phone
     * @return
     */
    public static String hidePhone( String phone ) {
        if( checkPhoneNum( phone ) ) {
            StringBuffer stringBuffer = new StringBuffer( );
            stringBuffer.append( phone.substring( 0, 3 ) );
            stringBuffer.append( "****" );
            stringBuffer.append( phone.substring( 7, phone.length( ) ) );
            return stringBuffer.toString( );
        } else {
            return phone;
        }
    }


    /**
     * 隐藏用户姓名
     *
     * @param name
     * @return
     */
    public static String hideUserName( String name ) {
        StringBuffer stringBuffer = new StringBuffer( );
        if( !TextUtils.isEmpty( name ) ) {
            int length = name.length( );
            for( int i = 0; i < length; i++ ) {
                if( i == 0 || ( i == length - 1 && length > 2 ) )
                    stringBuffer.append( name.charAt( i ) );
                else
                    stringBuffer.append( "*" );
            }
            return stringBuffer.toString( );
        } else
            return "";
    }

    /**
     * 获取设备信息
     *
     * @param context
     * @return
     */
    public static String getDeviceInfo(Context context ) {
        Build build = new Build( );
        String model = Build.MODEL;

        return model;
    }

    /**
     * 获取meta_data
     *
     * @param context 上下文对象
     * @param key meta_data name
     * @return meta_data value
     */
    public static String getAppMetaData(Context context, String key ) {
        if( context == null || TextUtils.isEmpty( key ) ) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = context.getPackageManager( );
            if( packageManager != null ) {
                ApplicationInfo applicationInfo = packageManager
                        .getApplicationInfo( context.getPackageName( ), PackageManager.GET_META_DATA );
                if( applicationInfo != null ) {
                    if( applicationInfo.metaData != null ) {
                        resultData = applicationInfo.metaData.get( key ) + "";
                    }
                }

            }
        } catch( PackageManager.NameNotFoundException e ) {
            e.printStackTrace( );
        }

        return resultData;
    }

    /**
     * 获取app版本
     *
     * @param context
     * @return
     */
    public static String getAPPVersion(Context context ) {
        String version = "";
        try {
            PackageManager manager = context.getPackageManager( );
            PackageInfo info = manager.getPackageInfo( context.getPackageName( ), 0 );
            version = info.versionName;
            return version;
        } catch( Exception e ) {
            e.printStackTrace( );
            return version;
        }
    }
    /**
     * 截取小数点后两位（不四舍五入）
     *
     * @param number
     * @return
     */
    public static String getTwoAfterPoint(Object number ) {
        if( number instanceof String) {
            BigDecimal bigDecimal = new BigDecimal( String.valueOf( number ) );
            bigDecimal = bigDecimal.setScale( 2, BigDecimal.ROUND_FLOOR );
            return String.valueOf( bigDecimal );
        }
        return number.toString();
    }
    /**
     * 检查token
     *
     * @return
     */
    public static boolean checkToken( Context context ) {
        return SystemUtil.getToken( context ) != null && SystemUtil.getToken( context )
                .length( ) > 0;
    }


    /**
     * 检查手机号
     *
     * @param phoneNum 手机号码
     * @param context
     * @return
     */
    public static boolean checkPhoneNum(String phoneNum, Context context ) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        Pattern p = Pattern.compile( regex );
        if( !p.matcher( phoneNum ).matches( ) ) {
            //            showToast( PHONE_NUMBER_ERROR );
            return false;
        } else {
            return true;
        }
    }
    //截取数字
    public static String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    /**
     * 验证身份证号
     *
     * @param identityNum
     * @param context
     * @return
     */
    public static boolean checkIdentityNum(String identityNum, Context context ) {
        if( identityNum == null ) {
            ToastUtil.showToast( IDENTITY_CARD_ERROR );
            return false;
        }
        String regex = "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X))$";
        Pattern p = Pattern.compile( regex );
        if( !p.matcher( identityNum ).matches( ) ) {
            ToastUtil.showToast(IDENTITY_CARD_ERROR );
            return false;
        } else {
            return true;
        }
    }

    /**
     * 设置edittext不能编辑
     * @param editText
     */
    public static void prohibitEdit( EditText editText ) {
        editText.setFocusable( false );
        editText.setFocusableInTouchMode( false );
    }

    /**
     * 隐藏电话号码
     *
     * @param phoneNum
     * @param context
     * @return
     */
    public static String hidePhoneNum(String phoneNum, Context context ) {
        if( checkPhoneNum( phoneNum, context ) ) {
            StringBuffer sb = new StringBuffer( );
            sb.append( phoneNum.substring( 0, 3 ) );
            sb.append( "****" );
            sb.append( phoneNum.substring( phoneNum.length( ) - 4, phoneNum.length( ) ) );

            return sb.toString( );
        } else {
            return phoneNum;
        }
    }

    /**
     * 隐藏银行卡
     *
     * @param bankCardNum 银行卡号
     * @param context
     * @return
     */
    public static String hideBankCardNum(String bankCardNum, Context context ) {
        if( checkBankCard( bankCardNum, context ) ) {
            StringBuffer sb = new StringBuffer( );
            sb.append( "****" );
            sb.append( "    " );
            sb.append( "****" );
            sb.append( "   " );
            sb.append( "****" );
            sb.append( "    " );
            if( bankCardNum.length( ) % 2 == 0 ) {
                sb.append(
                        bankCardNum.substring( bankCardNum.length( ) - 4, bankCardNum.length( ) ) );
            } else if( bankCardNum.length( ) % 2 == 1 ) {
                sb.append(
                        bankCardNum.substring( bankCardNum.length( ) - 3, bankCardNum.length( ) ) );
            }
            return sb.toString( );
        } else {
            return bankCardNum;
        }
    }

    /**
     * @param isFistLoad
     * @param context
     */
    public static void isFistLoad( boolean isFistLoad, Context context ) {
        SharedPreferences sp = context.getSharedPreferences( APP_SAVE, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sp.edit( );
        editor.putBoolean( IS_FIRST_LOAD, isFistLoad );
        editor.apply( );
    }


    /**
     * @param context
     */
    public static boolean getFistLoad( Context context ) {
        SharedPreferences sp = context.getSharedPreferences( APP_SAVE, Context.MODE_PRIVATE );
        return sp.getBoolean( IS_FIRST_LOAD, true );
    }


    /**
     * 消息推送本地判断
     *
     * @param isOpen
     * @param context
     */
    public static void isOpenPush( boolean isOpen, Context context ) {
        SharedPreferences sp = context.getSharedPreferences( APP_SAVE, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sp.edit( );
        editor.putBoolean( IS_OPEN_PUSH, isOpen );
        editor.apply( );
    }

    /**
     * @param context
     */
    public static boolean getPushStatus( Context context ) {
        SharedPreferences sp = context.getSharedPreferences( APP_SAVE, Context.MODE_PRIVATE );
        return sp.getBoolean( IS_OPEN_PUSH, true );
    }


    /**
     * @param phoneNumber
     * @param password
     * @param context
     */
    public static void saveLocalPw(String phoneNumber, String password, Context context ) {
        try {
            JSONObject userPassword = new JSONObject( );
            userPassword.put( phoneNumber, password );
            SharedPreferences sp = context.getSharedPreferences( APP_SAVE, Context.MODE_PRIVATE );
            SharedPreferences.Editor editor = sp.edit( );
            editor.putString( LOCAL_PASSWORD, userPassword.toString( ) );
            editor.commit( );
        } catch( JSONException e ) {
           LogUtil.e(e.toString());
        }

    }


    /**
     * @param context
     */
    public static String getLocalPw(Context context, String phoneNumber ) {
        SharedPreferences sp = context.getSharedPreferences( APP_SAVE, Context.MODE_PRIVATE );
        try {
            String userPassword = sp.getString( LOCAL_PASSWORD, "" );
            JSONObject jsonStr = new JSONObject( userPassword );
            return jsonStr.get( phoneNumber ).toString( );
        } catch( Exception e ) {
            e.printStackTrace( );
            return "";
        }

    }

    /**
     * 检查登入密码是否合理
     *
     * @param passWord
     * @param context
     * @return
     */
    public static boolean checkPassWord(String passWord, Context context ) {
        if( passWord.length( ) > 5 && passWord.length( ) < 20 ) {
            if( !isChineseChar( passWord ) && !isSuperChar( passWord ) ) {
                return true;
            } else {
                ToastUtil.showToast( SUPER_PASSWORD_ERROR );
                return false;
            }
        } else {
            ToastUtil.showToast( PASSWORD_ERROR );
            return false;
        }
    }

    /**
     * 判断时候含有汉字
     *
     * @param str
     * @return
     */
    public static boolean isChineseChar( String str ) {
        boolean temp = false;
        Pattern p = Pattern.compile( "[\u4e00-\u9fa5]" );
        Matcher m = p.matcher( str );
        if( m.find( ) ) {
            temp = true;
        }
        return temp;
    }

    /**
     * 判断时候含有空格
     *
     * @param str
     * @return
     */
    public static boolean isSuperChar( String str ) {
        boolean temp = false;
        Pattern p = Pattern.compile( "[\\s]" );
        Matcher m = p.matcher( str );
        if( m.find( ) ) {
            temp = true;
        }
        return temp;
    }


    /**
     * 检查银行卡是否合理
     *
     * @param cardId
     * @param context
     * @return
     */
    public static boolean checkBankCard(String cardId, Context context ) {
        if( cardId.length( ) > 15 && cardId.length( ) < 20 ) {
            return true;
        } else {
            ToastUtil.showToast( CARD_ID_ERROR );
            return false;
        }
    }


    /**
     * 设置
     *
     * @param number
     * @return
     */
    public static String updateNumber(Object number ) {
        DecimalFormat format = new DecimalFormat( "###0.00" );
        if( number instanceof String) {
            BigDecimal bigNumber = new BigDecimal( (String) number );
            number = bigNumber.doubleValue( );
        }
        return format.format( number );
    }


    /**
     * 保存用户信息
     *
     * @param context
     * @param saveToken token登入验证
     */
    public static void saveToken(Context context, String saveToken ) {
        token = saveToken;
        SharedPreferences sp = context.getSharedPreferences( APP_SAVE, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sp.edit( );
        editor.putString( SAVE_TOKEN, saveToken );
        editor.commit( );
    }


    /**
     * 获取token
     *
     * @param context
     * @return
     */
    public static String getToken(Context context ) {
        if( token == null ) {
            SharedPreferences sp = context.getSharedPreferences( APP_SAVE, Context.MODE_PRIVATE );
            token = sp.getString( SAVE_TOKEN, "" );
        }
        return token;

    }




    /**
     * 调起手机应用市场
     */
    public static void commentary( Context context ) {
        try {
            Uri uri = Uri.parse( "market://details?id=" + context.getPackageName( ) );
            Intent intent = new Intent( Intent.ACTION_VIEW, uri );
            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity( intent );
        }
        catch( Exception e ) {
            ToastUtil.showToast( "未找到手机中的应用市场" );
        }
    }


    /**
     * 更新用户信息
     *
     * @param context
     */
    //    public static void updateUserInfo( Context context, ServiceCallBack callBack ) {
    //        updateUserInfo( context, false, callBack );
    //    }
    //
    //    public static void updateUserInfo( Context context, boolean isShow, ServiceCallBack
    // callBack ) {
    //        UserInfoParams params = new UserInfoParams( context );
    //        HttpService<UserInfoEntity> httpService = new HttpService<UserInfoEntity>( context,
    //                isShow );
    //        httpService.postEntity( ApiConfig.getApiGetUserInfo( ), params.getParams( ),
    //                UserInfoEntity.class, callBack );
    //    }


    /**
     * 设置listView为设置item个数的高度
     *
     * @param listView
     */
    public static void setListViewHeight( ListView listView ) {
        if( listView == null ) {
            return;
        }

        ListAdapter adapter = listView.getAdapter( );
        if( adapter != null ) {
            //listView设置最大高度为5个item
            int count = adapter.getCount( );
            if( count > TOTAL_ITEM )
                count = TOTAL_ITEM;
            //总高度
            int totalHeight = 0;
            for( int i = 0; i < count; i++ ) {
                View listItem = adapter.getView( i, null, listView );
                listItem.measure( 0, 0 );
                int itemHeight = listItem.getMeasuredHeight( );
                totalHeight += itemHeight;
            }
            //设置listView高度
            ViewGroup.LayoutParams params = listView.getLayoutParams( );
            params.height = totalHeight;
            listView.setLayoutParams( params );
        }
    }


    /**
     * 绑定推送service服务
     *
     * @param context
     */
    //    public static void BandPushService( Context context, String deviceToken ) {
    //        if( !checkToken( context ) ) {
    //            return;
    //        }
    //        BandPushParams pushParams = new BandPushParams( context );
    //        pushParams.access_token = getToken( context );
    //        pushParams.source_from_description = getDeviceInfo( context );
    //        pushParams.device_token = deviceToken;
    //        BandPush( context, pushParams );
    //    }


    //    private static void BandPush( Context context, BandPushParams params ) {
    //        HttpService<Object> httpService = new HttpService<Object>( context );
    //        httpService.post( ApiConfig.getApiBandPush( ), params.getParams( ),
    //                new ServiceCallBack<Object>( ) {
    //                    @Override
    //                    public void onCodeBack() {
    //
    //                    }
    //
    //                    @Override
    //                    public void onFiled( String s ) {
    //
    //                    }
    //                } );
    //    }


    /**
     * 修改限额信息
     *
     * @param limitInfo
     */
    public static String setCardLimitInfo(String limitInfo ) {
        if( TextUtils.isEmpty( limitInfo ) ) {
            return "0";
        }
        String[] limit = limitInfo.replace( ".", "/" ).split( "/" );
        int len = limit[0].length( );
        StringBuffer sb = new StringBuffer( );
        if( len > 4 ) {
            long money = Long.valueOf( limit[0] );
            sb.append( money / 10000 );
            sb.append( "." );
            sb.append( money % 10000 / 1000 );
            sb.append( money % 1000 / 100 );
            sb.append( "万元" );
            return sb.toString( );
        } else {
            sb.append( limit[0] );
            sb.append( "元" );
            return sb.toString( );
        }
    }

    public static String hidePointNumber(String number ) {
        if( TextUtils.isEmpty( number ) ) {
            return number;
        }
        number = number.replace( ".", "/" );
        String[] numberStr = number.split( "/" );
        if( numberStr.length > 1 ) {
            return numberStr[0];
        }
        return number;
    }



    /**
     * 截取String后四位的数字
     * @return
     */
    public static String getNumberFour(String phoneNumber){
       String number =  phoneNumber.substring(phoneNumber.length()-4,phoneNumber.length());
        return number;
    }


    /**
     * 生成四个随机数字
     * @return
     */
    //根据指定长度生成纯数字的随机数
    public static String createFourData(int length) {
        StringBuilder sb=new StringBuilder();
        Random rand=new Random();
        for(int i=0;i<length;i++)
        {
            sb.append(rand.nextInt(10));
        }
        String data=sb.toString();
       return data;
    }

    /*
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    public static boolean isInteger( String str ) {
        Pattern pattern = Pattern.compile( "^[-\\+]?[\\d]*$" );
        return pattern.matcher( str ).matches( );
    }

    /*
     * 判断是否为浮点数，包括double和float
     * @param str 传入的字符串
     * @return 是浮点数返回true,否则返回false
     */
    public static boolean isDouble( String str ) {
        Pattern pattern = Pattern.compile( "^[-\\+]?[.\\d]*$" );
        return pattern.matcher( str ).matches( );
    }
    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return  手机IMEI
     */
    public static final String getIMEI(Context context) {

        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            String imei = telephonyManager.getDeviceId();
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 去掉手机号内除数字外的所有字符
     *
     * @param phoneNum 手机号
     * @return
     */
    public static String formatPhoneNum(String phoneNum) {
        String regex = "(\\+86)|[^0-9]";
        Pattern pattern = Pattern.compile(regex );
        Matcher matcher = pattern.matcher(phoneNum);
        return matcher.replaceAll("");
    }

}

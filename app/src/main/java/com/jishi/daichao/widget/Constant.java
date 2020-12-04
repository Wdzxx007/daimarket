package com.jishi.daichao.widget;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 常量
 *
 * @author Administrator
 */
public class Constant {
    public static WeakReference<ArrayList<String>> picList;

    public static final String EXTRA_RESULT_SELECTION = "media_selection";
    public static final int CODE_CROP = 10003;
    public static final int CODE_PREVIEW_CAMERA = 10004;
    public static final int FLAG_SELECT_PREVIEW = 1;
    public static final int FLAG_CAMERA_PREVIEW = 2;
    public static final int REQUEST_CODE_TO_LIST = 0;

    public static final int QR_ERROR_NO_CAMERA_PERMITION = 3;//没有照相机启动权限
    public static final int REQUEST_CODE_URL_QR_CALL = 2;//url输入调用二维码
    public static final String USER_NAME = "userName";
    public static final String ADDRESSID = "addressid";
    public static final String LOAD_URL = "url";
    public static final String LOAD_RICH_TEXT = "text";
    public static final String LOAD_TITLE = "title";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";
    public static final String DB_NAME = "daimarket_db";
}
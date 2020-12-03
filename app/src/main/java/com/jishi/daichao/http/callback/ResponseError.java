package com.jishi.daichao.http.callback;

import com.jishi.daichao.utils.ToastUtil;

/**
 * Created by Administrator on 2018/7/10.
 */

public class ResponseError {

    public static void judgeHttpCode(String code) {
        switch (code) {

            case "400":
                ToastUtil.showToast("请求错误，请检查");
                break;
            case "500":
                ToastUtil.showToast("服务器内部错误");
                break;
            case "404":
                ToastUtil.showToast("请求地址不存在");
                break;
        }
    }

}

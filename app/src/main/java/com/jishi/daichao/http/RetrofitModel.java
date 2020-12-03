package com.jishi.daichao.http;

import com.jishi.daichao.base.params.ImageCodeParams;
import com.jishi.daichao.entity.ImageCodeEntity;
import com.jishi.daichao.http.api.SystemApi;
import com.jishi.daichao.utils.JsonUtil;
import com.jishi.daichao.utils.ParameterEncryptionUtil;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by Administrator on 2018/7/9.
 */

public class RetrofitModel {
    private static RetrofitModel sInstance;
    private SystemApi systemApi;

    public static RetrofitModel getInstance() {
        if (null == sInstance) {
            sInstance = new RetrofitModel();
        }
        return sInstance;
    }

    private RetrofitModel() {
        systemApi = (SystemApi) RetrofitWrapper.getInstance().create(SystemApi.class);
    }

    public SystemApi getSystemApi(){
        return systemApi;
    }
}

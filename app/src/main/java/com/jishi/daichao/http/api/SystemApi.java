package com.jishi.daichao.http.api;


import com.jishi.daichao.bean.ResponseBean;
import com.jishi.daichao.entity.ImageCodeEntity;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * system模块接口
 * Created by laulee on 17/8/4.
 */

public interface SystemApi {

    //获取图形验证码
    @POST("/login/getCaptcha")
    @FormUrlEncoded
    public Observable<ResponseBean<ImageCodeEntity>> getImageCode(@FieldMap Map<String, String> params);


}

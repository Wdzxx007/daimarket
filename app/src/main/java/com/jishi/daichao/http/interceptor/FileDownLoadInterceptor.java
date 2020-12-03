package com.jishi.daichao.http.interceptor;

import com.jishi.daichao.http.FileProgroessBody;
import com.jishi.daichao.http.callback.DownLoadProgressListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 文件下载拦截器
 * Created by laulee on 17/11/27.
 */

public class FileDownLoadInterceptor implements Interceptor {
    private final DownLoadProgressListener downLoadProgressListener;

    public FileDownLoadInterceptor( DownLoadProgressListener downLoadProgressListener ) {
        this.downLoadProgressListener = downLoadProgressListener;
    }

    @Override
    public Response intercept( Chain chain ) throws IOException {
        Response orginalResponse = chain.proceed( chain.request( ) );
        orginalResponse.newBuilder( )
                .body( new FileProgroessBody( orginalResponse.body( ), downLoadProgressListener ));
        return orginalResponse;
    }
}

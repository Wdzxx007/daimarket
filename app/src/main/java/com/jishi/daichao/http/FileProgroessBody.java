package com.jishi.daichao.http;

import android.support.annotation.Nullable;

import com.jishi.daichao.http.callback.DownLoadProgressListener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 文件进度body
 * Created by laulee on 17/11/27.
 */

public class FileProgroessBody extends ResponseBody {
    private ResponseBody responseBody;
    private DownLoadProgressListener downLoadProgressListener;
    private BufferedSource bufferedSource;

    public FileProgroessBody( ResponseBody body,
            DownLoadProgressListener downLoadProgressListener ) {
        this.responseBody = body;
        this.downLoadProgressListener = downLoadProgressListener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType( );
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength( );
    }

    @Override
    public BufferedSource source() {
        if( bufferedSource == null ) {
            bufferedSource = Okio.buffer( source( responseBody.source( ) ) );
        }
        return bufferedSource;
    }

    private Source source( Source source ) {
        return new ForwardingSource( source ) {
            long totalByteRead = 0L;

            @Override
            public long read( Buffer sink, long byteCount ) throws IOException {
                long byteRead = super.read( sink, byteCount );
                totalByteRead += byteRead != -1 ? byteRead : 0;
                downLoadProgressListener
                        .onProgress( totalByteRead, responseBody.contentLength( ), byteRead == -1 );
                return byteRead;
            }
        };
    }
}

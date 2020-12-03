package com.jishi.daichao.http.callback;

/**
 * Created by laulee on 17/11/27.
 */

public interface DownLoadProgressListener {

    public void onProgress(long total, long cententLength, boolean done);
}

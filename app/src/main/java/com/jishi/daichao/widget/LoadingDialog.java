package com.jishi.daichao.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.jishi.daichao.R;

/**
 * 加载提示框
 * Created by laulee on 17/12/6.
 */

public class LoadingDialog extends Dialog {
    private ProgressDrawable mProgressDrawable;
    private ImageView img_loading;
    public LoadingDialog(@NonNull Context context) {
        super(context,R.style.noTitleDialog);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context,R.style.noTitleDialog );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog);
        img_loading = (ImageView)findViewById(R.id.img_loading);
        mProgressDrawable = new ProgressDrawable();
        mProgressDrawable.setColor(Color.parseColor("#fff8f8f8"));
        img_loading.setImageDrawable(mProgressDrawable);
        img_loading.animate().setInterpolator(new LinearInterpolator());
        img_loading.animate().rotation(36000).setDuration(100000);
        if (mProgressDrawable != null) {
            mProgressDrawable.start();
        }
    }

    @Override
    protected void onStop() {
        if (mProgressDrawable != null) {
            mProgressDrawable.stop();
        }
    }
}

package com.jishi.daichao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.jishi.daichao.R;
import com.jishi.daichao.base.RxBaseActivity;
import com.jishi.daichao.bean.TheMessageEvent;
import com.jishi.daichao.presenter.AuthCodePresenter;
import com.jishi.daichao.presenter.contact.AuthCodeContact;
import com.jishi.daichao.utils.ToastUtil;
import com.jishi.daichao.widget.PwdEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/2/15.
 */

public class AuthCodeActivity extends RxBaseActivity<AuthCodePresenter> implements AuthCodeContact.IView {
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_pwd)
    PwdEditText etPwd;
    @BindView(R.id.tv_resend_code)
    TextView tvResendCode;
    private final int SPACE_TIME = 1 * 1000;
    private final int COUNT_DOWN_TIME = 60 * SPACE_TIME;
    CountDownTimer timer = new CountDownTimer(COUNT_DOWN_TIME, SPACE_TIME) {
        @Override
        public void onTick(long millisUntilFinished) {
            tvResendCode.setText(millisUntilFinished / SPACE_TIME + "S后重新获取");
        }

        @Override
        public void onFinish() {
            tvResendCode.setText(getResources().getString(R.string.get_auth_code));
            tvResendCode.setClickable(true);
            cancel();
        }
    };


    @Override
    protected void initParams() {
        countDown();
        EventBus.getDefault().register(this);
        etPwd.setOnInputFinishListener(new PwdEditText.OnInputFinishListener() {
            @Override
            public void onInputFinish(String password) {
                Intent intent = new Intent(AuthCodeActivity.this, SetPwdActivity.class);
                startActivity(intent);
                ToastUtil.showToast(password);
            }
        });

    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_authcode;
    }

    @Override
    protected boolean hasToolBar() {
        return false;
    }

    @Override
    public void onServerFiled(String s) {

    }

    @Override
    public void checkAuthCodeSuccess() {
        Intent intent = new Intent(AuthCodeActivity.this, SetPwdActivity.class);
        startActivity(intent);

    }

    @Override
    public void retryGetAuthCodeSuccess() {

    }

    //处理事件逻辑
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receiveEventBus(TheMessageEvent messageEvent) {
        tvPhone.setText("短信验证码已发送至 " + messageEvent.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //evenbus解除注册
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
        if (timer != null)
            timer.cancel();
    }

    @OnClick({R.id.img_close, R.id.tv_resend_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.tv_resend_code:
                countDown();
                break;

        }

    }

    /**
     * 倒计时
     */
    private void countDown() {
        //设计控件控件不能点击
        tvResendCode.setClickable(false);
        timer.start();
    }

}

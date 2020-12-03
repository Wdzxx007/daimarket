package com.jishi.daichao.ui;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.jishi.daichao.R;
import com.jishi.daichao.base.RxBaseActivity;
import com.jishi.daichao.presenter.RetrivePasswordPresenter;
import com.jishi.daichao.presenter.contact.RetrivePasswordContact;
import com.jishi.daichao.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/2/18.
 */

public class RetrievePasswordActivity extends RxBaseActivity<RetrivePasswordPresenter> implements RetrivePasswordContact.IView, TextWatcher {
    @BindView(R.id.edt_login_phone)
    EditText edtLoginPhone;
    @BindView(R.id.edt_check_code)
    EditText edtCheckCode;
    @BindView(R.id.img_showCode)
    ImageView imgShowCode;
    @BindView(R.id.edt_auth_code)
    EditText edtAuthCode;
    @BindView(R.id.btn_pwd_login)
    Button btnPwdLogin;
    @BindView(R.id.tv_get_auth_code)
    TextView tvGetAuthCode;
    //产生的验证码
    private String realCode;
    private final int SPACE_TIME = 1 * 1000;
    private final int COUNT_DOWN_TIME = 60 * SPACE_TIME;
    CountDownTimer timer = new CountDownTimer(COUNT_DOWN_TIME, SPACE_TIME) {
        @Override
        public void onTick(long millisUntilFinished) {
            tvGetAuthCode.setText("(" + millisUntilFinished / SPACE_TIME + "s)重新获取");
            tvGetAuthCode.setTextColor(getResources().getColor(R.color.next_grey_color));
        }

        @Override
        public void onFinish() {
            tvGetAuthCode.setText(getResources().getString(R.string.get_auth_code));
            tvGetAuthCode.setTextColor(getResources().getColor(R.color.black));
            tvGetAuthCode.setClickable(true);
            cancel();
        }
    };


    @Override
    protected void initParams() {
        setTitle("找回密码");
        edtAuthCode.addTextChangedListener(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_retrieve_password;
    }


    @OnClick({R.id.img_showCode, R.id.btn_pwd_login, R.id.tv_get_auth_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_showCode:
                break;
            case R.id.btn_pwd_login:
                Intent intent = new Intent(RetrievePasswordActivity.this, ResetPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_get_auth_code:
                String checkCode = edtCheckCode.getText().toString().toLowerCase();
                if (!checkCode.equals(realCode)) {
                    ToastUtil.showToast("图形验证码错误");
                    return;
                }
                if (TextUtils.isEmpty(edtLoginPhone.getText())) {
                    ToastUtil.showToast("手机号码不能为空");
                    return;
                }
                countDown();
                break;
        }
    }

    @Override
    public void onServerFiled(String s) {


    }

    @Override
    public void checkAuthCodeSuccess() {

    }

    @Override
    public void getAuthCodeSuccess() {

    }

    /**
     * 倒计时
     */
    private void countDown() {
        //设计控件控件不能点击
        tvGetAuthCode.setClickable(false);
        timer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null)
            timer.cancel();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s)) {
            btnPwdLogin.setBackgroundResource(R.drawable.radius_donext_grey_style);
        } else {
            btnPwdLogin.setBackgroundResource(R.drawable.radius_next_grey_style);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

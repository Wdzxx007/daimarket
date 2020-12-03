package com.jishi.daichao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jishi.daichao.R;
import com.jishi.daichao.base.RxBaseActivity;
import com.jishi.daichao.base.params.ImageCodeParams;
import com.jishi.daichao.entity.ImageCodeEntity;
import com.jishi.daichao.http.api.SystemApi;
import com.jishi.daichao.presenter.LoginPasswordPresenter;
import com.jishi.daichao.presenter.contact.LoginPasswordContact;
import com.jishi.daichao.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/2/18.
 */

public class PwdLoginActivity extends RxBaseActivity<LoginPasswordPresenter> implements LoginPasswordContact.IView, TextWatcher {
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.edt_login_phone)
    EditText edtLoginPhone;
    @BindView(R.id.img_clear)
    ImageView imgClear;
    @BindView(R.id.edt_login_password)
    EditText edtLoginPassword;
    @BindView(R.id.img_password_close)
    ImageView imgPasswordClose;
    @BindView(R.id.btn_pwd_login)
    Button btnPwdLogin;
    @BindView(R.id.tv_forget_pwd)
    TextView tvForgetPwd;
    @BindView(R.id.tv_authcode_login)
    TextView tvAuthcodeLogin;
    private boolean isHidden = true;

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mPresenter = new LoginPasswordPresenter();
    }

    @Override
    protected void initParams() {
        ImageCodeParams imageCodeParams = new ImageCodeParams();
        imageCodeParams.phoneNumber = "";
        mPresenter.getImageCode(imageCodeParams);
        edtLoginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.toString().length();
                //删除数字
                if (count == 0) {
                    if (length == 4) {
                        edtLoginPhone.setText(s.subSequence(0, 3));
                    }
                    if (length == 9) {
                        edtLoginPhone.setText(s.subSequence(0, 8));
                    }
                }
                //添加数字
                if (count == 1) {
                    if (length == 4) {
                        String part1 = s.subSequence(0, 3).toString();
                        String part2 = s.subSequence(3, length).toString();
                        edtLoginPhone.setText(part1 + " " + part2);
                    }
                    if (length == 9) {
                        String part1 = s.subSequence(0, 8).toString();
                        String part2 = s.subSequence(8, length).toString();
                        edtLoginPhone.setText(part1 + " " + part2);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //将光标移动到末尾
                edtLoginPhone.setSelection(edtLoginPhone.getText().toString().length());
            }
        });
        edtLoginPassword.addTextChangedListener(this);

    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_pwd_login;
    }


    @OnClick({R.id.img_close, R.id.img_clear, R.id.img_password_close, R.id.btn_pwd_login, R.id.tv_forget_pwd, R.id.tv_authcode_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.img_clear:
                edtLoginPhone.setText("");
                break;
            case R.id.img_password_close:
                if (isHidden) {
                    //设置EditText文本为可见的
                    edtLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imgPasswordClose.setImageResource(R.mipmap.icon_open_eye);
                } else {
                    //设置EditText文本为隐藏的
                    edtLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imgPasswordClose.setImageResource(R.mipmap.icon_lose_eye);
                }
                isHidden = !isHidden;
                edtLoginPassword.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = edtLoginPassword.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.btn_pwd_login:
                if (TextUtils.isEmpty(edtLoginPhone.getText().toString())) {
                    ToastUtil.showToast("手机号码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(edtLoginPassword.getText().toString())) {
                    ToastUtil.showToast("密码不能为空");
                    return;
                }
                break;
            case R.id.tv_forget_pwd:
                Intent intent1 = new Intent(PwdLoginActivity.this, RetrievePasswordActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_authcode_login:
                Intent intent = new Intent(PwdLoginActivity.this, LoginAndRegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onServerFiled(String s) {

    }

    @Override
    public void doLoginPasswordSuccess() {

    }

    @Override
    public void getImageCodeSuccess(ImageCodeEntity imageCodeEntity) {
        ToastUtil.showToast(imageCodeEntity.getSign());
    }

    @Override
    public void getImageCodeFailed(String s) {
        ToastUtil.showToast(s);
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

    @Override
    protected boolean hasToolBar() {
        return false;
    }
}

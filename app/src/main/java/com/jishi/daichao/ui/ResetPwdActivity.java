package com.jishi.daichao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import com.jishi.daichao.MainActivity;
import com.jishi.daichao.R;
import com.jishi.daichao.base.RxBaseActivity;
import com.jishi.daichao.presenter.ResetPwdPresenter;
import com.jishi.daichao.presenter.contact.ResetPwdContact;
import com.jishi.daichao.utils.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/2/15.
 */

public class ResetPwdActivity extends RxBaseActivity<ResetPwdPresenter> implements ResetPwdContact.IView, TextWatcher {


    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.edt_check_password)
    EditText edtCheckPassword;
    @BindView(R.id.btn_set_pwd)
    Button btnSetPwd;

    @Override
    protected void initParams() {
        setTitle("设置密码");
        edtCheckPassword.addTextChangedListener(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_reset_password;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s)) {
            btnSetPwd.setBackgroundResource(R.drawable.radius_donext_grey_style);

        } else {
            btnSetPwd.setBackgroundResource(R.drawable.radius_next_grey_style);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @OnClick(R.id.btn_set_pwd)
    public void onViewClicked() {
        if (TextUtils.isEmpty(edtPassword.getText().toString())) {
            ToastUtil.showToast("登录密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(edtCheckPassword.getText().toString())) {
            ToastUtil.showToast("请输入确认的登录密码");
            return;
        }
        if (edtPassword.getText().length() < 6 || edtPassword.getText().length() > 16) {
            ToastUtil.showToast("密码长度请在6到16位之间");
            return;
        }
        if (!edtPassword.getText().toString().equals(edtCheckPassword.getText().toString())) {
            ToastUtil.showToast("密码不一致");
            return;
        }
        finish();
    }

    @Override
    public void onServerFiled(String s) {

    }

    @Override
    public void passwordSubmitSuccess() {

    }
}

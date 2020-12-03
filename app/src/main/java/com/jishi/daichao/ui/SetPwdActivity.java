package com.jishi.daichao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.jishi.daichao.R;
import com.jishi.daichao.base.RxBaseActivity;
import com.jishi.daichao.presenter.SetPwdPresenter;
import com.jishi.daichao.presenter.contact.SetPwdContact;
import com.jishi.daichao.utils.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/2/15.
 */

public class SetPwdActivity extends RxBaseActivity<SetPwdPresenter> implements SetPwdContact.IView, TextWatcher {
    @BindView(R.id.edt_pwd)
    EditText edtPwd;
    @BindView(R.id.edt_check_pwd)
    EditText edtCheckPwd;
    @BindView(R.id.btn_set_pwd)
    Button btnSetPwd;

    @Override
    protected void initParams() {
        setTitle("设置密码");
        edtCheckPwd.addTextChangedListener(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_set_password;
    }

    @Override
    public void onServerFiled(String s) {

    }

    @Override
    public void passwordSubmitSuccess() {
        Intent intent = new Intent(SetPwdActivity.this, PwdLoginActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.btn_set_pwd)
    public void onViewClicked() {
        if (TextUtils.isEmpty(edtPwd.getText().toString())) {
            ToastUtil.showToast("登录密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(edtCheckPwd.getText().toString())) {
            ToastUtil.showToast("请输入确认的登录密码");
            return;
        }
        if (edtPwd.getText().length() < 6 || edtPwd.getText().length() > 16) {
            ToastUtil.showToast("密码长度请在6到16位之间");
            return;
        }
        if (!edtPwd.getText().toString().equals(edtCheckPwd.getText().toString())) {
            ToastUtil.showToast("密码不一致");
            return;
        }
        Intent intent = new Intent(SetPwdActivity.this, PwdLoginActivity.class);
        startActivity(intent);
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
}

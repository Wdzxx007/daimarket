package com.jishi.daichao.ui;

import android.app.Dialog;
import android.content.Intent;
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
import com.jishi.daichao.bean.TheMessageEvent;
import com.jishi.daichao.presenter.LoginAndRegisterPresenter;
import com.jishi.daichao.presenter.contact.LoginAndRegisterContact;
import com.jishi.daichao.utils.SystemUtil;
import com.jishi.daichao.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/2/14.
 */

public class LoginAndRegisterActivity extends RxBaseActivity<LoginAndRegisterPresenter> implements LoginAndRegisterContact.IView, TextWatcher {
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.edt_login_phone)
    EditText edtLoginPhone;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    @BindView(R.id.tv_password_login)
    TextView tvPasswordLogin;
    @BindView(R.id.edt_invite_code)
    EditText edtInviteCode;
    @BindView(R.id.img_clear)
    ImageView imgClear;

    @Override
    protected void initParams() {
        EventBus.getDefault().register(this);
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
                if (!TextUtils.isEmpty(s)) {
                    btnGetCode.setBackgroundResource(R.drawable.radius_donext_grey_style);
                    imgClear.setVisibility(View.VISIBLE);
                } else {
                    btnGetCode.setBackgroundResource(R.drawable.radius_next_grey_style);
                    imgClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //将光标移动到末尾
                edtLoginPhone.setSelection(edtLoginPhone.getText().toString().length());
                //处理s
            }
        });

    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_login_register;
    }

    @OnClick({R.id.img_close, R.id.btn_get_code, R.id.tv_password_login, R.id.img_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.btn_get_code:
                createDialog();
                break;
            case R.id.tv_password_login:
                Intent intent = new Intent(LoginAndRegisterActivity.this, PwdLoginActivity.class);
                startActivity(intent);
                break;
            case R.id.img_clear:
                edtLoginPhone.setText("");
                break;
        }
    }

    @Override
    protected boolean hasToolBar() {
        return false;
    }

    @Override
    public void onServerFiled(String s) {
        ToastUtil.showToast(s);
    }

    @Override
    public void getAuthCodeSuccess() {
        EventBus.getDefault().postSticky(new TheMessageEvent(edtLoginPhone.getText().toString()));
        Intent intent = new Intent(this, AuthCodeActivity.class);
        startActivity(intent);
    }

    private void createDialog() {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    //任意写一个方法，给这个方法一个@Subscribe注解，参数类型可以自定义，但是一定要与你发出的类型相同
    @Subscribe
    public void getEventBus(String num) {
        if (num != null) {
        }
    }
}

package com.jishi.daichao.ui;
import android.content.Intent;
import com.jishi.daichao.MainActivity;
import com.jishi.daichao.R;
import com.jishi.daichao.base.RxBaseActivity;
import com.jishi.daichao.utils.SystemUtil;
import com.jishi.daichao.utils.ToastUtil;
import java.util.List;
/**
 * Created by Administrator on 2018/8/2.
 */

public class WelcomePage extends RxBaseActivity {

    @Override
    protected void initParams() {
        init();
    }

    public void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //休眠1秒
                    Thread.sleep(1000);


                    getStart();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void getStart() {
//        AndPermission.with(WelcomePage.this)
//                .runtime()
//                .permission(Permission.READ_PHONE_STATE)
//                .onGranted(new Action<List<String>>() {
//                    @Override
//                    public void onAction(List<String> data) {
//                        ToastUtil.showToast(SystemUtil.getDeviceOnlyId(WelcomePage.this));
                        Intent intent = new Intent(WelcomePage.this, MainActivity.class);
                        startActivity(intent);
                        finish();
//                    }
//                }).onDenied(new Action<List<String>>() {
//            @Override
//            public void onAction(List<String> data) {
//                ToastUtil.showToast("权限被拒绝");
//            }
//        }).start();
//        String str = (String) SharedPreferencesUtil.getData(this, "firstlog", "");
//        if (str != "") {
//            startActivity(new Intent(WelcomePage.this, MainActivity.class));
//            this.finish();
//        } else {
//            startActivity(new Intent(WelcomePage.this, ExhibitionActivity.class));
//            SharedPreferencesUtil.saveData(this, "firstlog", "true");
//            this.finish();
//        }
    }

    @Override
    protected boolean hasToolBar() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_welcome;
    }
}

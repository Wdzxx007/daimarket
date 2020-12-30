package com.jishi.daichao;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.jishi.daichao.base.RxBaseActivity;
import com.jishi.daichao.fragments.BorrowFragment;
import com.jishi.daichao.fragments.ChargeFragment;
import com.jishi.daichao.fragments.HomeFragment;
import com.jishi.daichao.fragments.MineFragment;
import com.jishi.daichao.ui.LoginAndRegisterActivity;
import com.jishi.daichao.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends RxBaseActivity {
    private int selectTabId = 0;
    private final int USER_LOGIN = 1;
    private List<android.app.Fragment> fragmentEntities;
    private android.app.Fragment mContent;
    private Intent intent;
    private long firstTime = 0;
    @BindView(R.id.contentView)
    FrameLayout contentView;
    @BindView(R.id.imgTabHome)
    ImageView imgTabHome;
    @BindView(R.id.tvTabHome)
    TextView tvTabHome;
    @BindView(R.id.ll_main_page_home)
    LinearLayout llMainPageHome;
    @BindView(R.id.imgTabBorrow)
    ImageView imgTabBorrow;
    @BindView(R.id.tvTabBorrow)
    TextView tvTabBorrow;
    @BindView(R.id.ll_main_page_borrow)
    LinearLayout llMainPageBorrow;
    @BindView(R.id.imgTabCharge)
    ImageView imgTabCharge;
    @BindView(R.id.lblTabCharge)
    TextView lblTabCharge;
    @BindView(R.id.ll_main_page_charge)
    LinearLayout llMainPageCharge;
    @BindView(R.id.imgTabMine)
    ImageView imgTabMine;
    @BindView(R.id.tvTabMine)
    TextView tvTabMine;
    @BindView(R.id.ll_main_page_mine)
    LinearLayout llMainPageMine;

    @Override
    protected void initParams() {
        initDate();
        mContent = new android.app.Fragment();
        setCurrentTab(0);
    }

    @Override
    protected boolean hasToolBar() {
        return false;
    }

    /**
     * 初始化Fragment数据
     *
     * @return
     */
    private void initDate() {
        fragmentEntities = new ArrayList<android.app.Fragment>();
        fragmentEntities.add(new HomeFragment());
        fragmentEntities.add(new BorrowFragment());
        fragmentEntities.add(new ChargeFragment());
        fragmentEntities.add(new MineFragment());
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @OnClick({R.id.ll_main_page_home, R.id.ll_main_page_borrow, R.id.ll_main_page_charge, R.id.ll_main_page_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_main_page_home:
                setCurrentTab(0);
                break;
            case R.id.ll_main_page_borrow:
                setCurrentTab(1);
                break;
            case R.id.ll_main_page_charge:
                setCurrentTab(2);
                break;
            case R.id.ll_main_page_mine:
                setCurrentTab(3);
                break;
        }
    }

    /**
     * 设置选中Tab
     */

    public void setCurrentTab(int position) {
        selectTabId = position;
        switch (position) {
            case 0:
                initCheckDrawable();
                imgTabHome.setImageResource(R.mipmap.icon_home_select);
                tvTabHome.setTextColor(getResources().getColor(R.color.main_tab_home_selcet_text));
                ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white_background).fitsSystemWindows(true).init();
                break;
            case 1:
                initCheckDrawable();
                imgTabBorrow.setImageResource(R.mipmap.icon_dai_select);
                tvTabBorrow.setTextColor(getResources().getColor(R.color.main_tab_home_selcet_text));
                ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white_background).fitsSystemWindows(true).init();
                break;
            case 2:
                if (!SystemUtil.checkToken(this)) {
                    Intent intent = new Intent(this, LoginAndRegisterActivity.class);
                    startActivityForResult(intent, USER_LOGIN);
                    return;
                }
                initCheckDrawable();
                imgTabCharge.setImageResource(R.mipmap.icon_charge_select);
                lblTabCharge.setTextColor(getResources().getColor(R.color.main_tab_home_selcet_text));
                ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white_background).fitsSystemWindows(true).init();
                break;
            case 3:
                initCheckDrawable();
                imgTabMine.setImageResource(R.mipmap.icon_mine_select);
                tvTabMine.setTextColor(getResources().getColor(R.color.main_tab_home_selcet_text));
                ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.remain_start_red).fitsSystemWindows(true).init();
                break;
        }
        switchContent(fragmentEntities.get(position));
    }

    /**
     * 重置图片
     */
    private void initCheckDrawable() {
        imgTabHome.setImageResource(R.mipmap.icon_home_unselect);
        tvTabHome.setTextColor(getResources().getColor(R.color.main_tab_home_unselcet_text));
        imgTabBorrow.setImageResource(R.mipmap.icon_dai_unselect);
        tvTabBorrow.setTextColor(getResources().getColor(R.color.main_tab_home_unselcet_text));
        imgTabCharge.setImageResource(R.mipmap.icon_charge_unselect);
        lblTabCharge.setTextColor(getResources().getColor(R.color.main_tab_home_unselcet_text));
        imgTabMine.setImageResource(R.mipmap.icon_mine_unselect);
        tvTabMine.setTextColor(getResources().getColor(R.color.main_tab_home_unselcet_text));
    }

    /**
     * 切换Fragment
     *
     * @param to
     */
    public void switchContent(Fragment to) {
        FragmentTransaction transaction = getFragmentManager( ).beginTransaction( );
        if (mContent != to) {
            // 先判断是否被add过
            if (!to.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                transaction.hide(mContent).add(R.id.contentView, to).commitAllowingStateLoss();
            } else {
                // 隐藏当前的fragment，显示下一个
                transaction.hide(mContent).show(to).commitAllowingStateLoss();
            }
        }
        mContent = to;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (secondTime - firstTime < 2000) {
                System.exit(0);
            } else {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}

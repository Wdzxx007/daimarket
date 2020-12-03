package com.jishi.daichao.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;

import com.jishi.daichao.R;

/**
 * Created by sean on 15/12/27.
 */
public abstract class BaseWindow implements View.OnClickListener {
    private Context context;
    private View showView;

    private PopupWindow popWindow;

    public BaseWindow(Context context, View showView) {
        this.context = context;
        this.showView = showView;
        createPopWindow();
    }

    /*
     *
     * 创建popWindow
     * */
    private void createPopWindow() {
        popWindow = new PopupWindow(initPopView(), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        // 设置点击窗口外边窗口消失
        popWindow.setOutsideTouchable(false);
        // 设置此参数获得焦点，否则无法点击
        popWindow.setFocusable(true);
        // 设置动画
        //popWindow.setAnimationStyle(R.style.PopupAnimation);
        // 刷新状态（必须刷新否则无效）
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.black_null_background));
        //popWindow.setBackgroundDrawable( dw );
        popWindow.update();
    }

    protected abstract View initPopView();

    /**
     * 展示popWindow
     */
    public void showView(View view) {
        if (popWindow.isShowing()) {
            return;
        }
        //在控件的下方展示
        if (view != null) {
            popWindow.showAsDropDown(view);
            return;
        }
        //默认底部展示
        popWindow.showAtLocation(showView, Gravity.BOTTOM, 0, 0);
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(showView, InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(showView.getWindowToken(), 0); //强制隐藏键盘
    }


    public Context getContext() {
        return context;
    }

    public PopupWindow getPopWindow() {
        return popWindow;
    }

    public void dismiss() {
        popWindow.dismiss();
    }

    @Override
    public void onClick(View v) {
        if (v == null)
            return;
    }
}

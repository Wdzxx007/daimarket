package com.jishi.daichao.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.jishi.daichao.R;

/**
 * Created by Administrator on 2019/2/21.
 */

public class LoanSelectWindow extends BaseWindow {
    private ISelectListener iSelectListener;

    public LoanSelectWindow(Context context, View showView) {
        super(context, showView);
    }

    @Override
    protected View initPopView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.loan_select_window, null);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.findViewById(R.id.tv_comprehensive).setOnClickListener(this);
        view.findViewById(R.id.tv_easy_pass).setOnClickListener(this);
        view.findViewById(R.id.tv_limit_up).setOnClickListener(this);
        view.findViewById(R.id.tv_limit_down).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_comprehensive:
                if (iSelectListener != null)
                    iSelectListener.selectComprehensive();
                dismiss();
                break;
            case R.id.tv_easy_pass:
                if (iSelectListener != null)
                    iSelectListener.selectEasyPass();
                dismiss();
                break;
            case R.id.tv_limit_up:
                if (iSelectListener != null)
                    iSelectListener.selectLimitUp();
                dismiss();
                break;
            case R.id.tv_limit_down:
                if (iSelectListener != null)
                    iSelectListener.selectLimitDown();
                dismiss();
                break;
        }

    }

    public interface ISelectListener {
        void selectComprehensive();

        void selectEasyPass();

        void selectLimitUp();

        void selectLimitDown();
    }

    public void setiSelectListener(ISelectListener iSelectListener) {
        this.iSelectListener = iSelectListener;
    }


}

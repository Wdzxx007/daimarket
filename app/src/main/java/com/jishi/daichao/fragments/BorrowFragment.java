package com.jishi.daichao.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jishi.daichao.R;
import com.jishi.daichao.adapter.BorrowListAdapter;
import com.jishi.daichao.base.RxBaseFragment;
import com.jishi.daichao.entity.BorrowListEntity;
import com.jishi.daichao.presenter.BorrowFragmentPresenter;
import com.jishi.daichao.presenter.contact.BorrowFragmentContact;
import com.jishi.daichao.widget.LoanSelectWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2019/2/13.
 */

public class BorrowFragment extends RxBaseFragment<BorrowFragmentPresenter> implements BorrowFragmentContact.IView {
    @BindView(R.id.line_invisible)
    View lineInvisible;
    @BindView(R.id.tv_select_comprehensive)
    TextView tvSelectComprehensive;
    @BindView(R.id.tv_low_rate)
    TextView tvLowRate;
    @BindView(R.id.tv_speed_loan)
    TextView tvSpeedLoan;
    @BindView(R.id.rv_borrow_list)
    RecyclerView rvBorrowList;
    @BindView(R.id.refreshLayoutborrow)
    SmartRefreshLayout refreshLayoutborrow;
    private LoanSelectWindow loanSelectWindow;
    private boolean isClick;
    private BorrowListAdapter adapter;
    private BorrowListEntity[] borrowListEntities = {new BorrowListEntity("优惠券通知", "速贷", "放款快", "3000-10000", "参考利率0.08%", "1855人已申请", "20"),
            new BorrowListEntity("优惠券通知", "速贷", "放款快", "3000-10000", "参考利率0.08%", "1855人已申请", "20"),
            new BorrowListEntity("优惠券通知", "速贷", "放款快", "3000-10000", "参考利率0.08%", "1855人已申请", "20")
            , new BorrowListEntity("优惠券通知", "速贷", "放款快", "3000-10000", "参考利率0.08%", "1855人已申请", "20")

    };
    private List<BorrowListEntity> borrowListEntityList = new ArrayList<>();

    @Override
    public void showLoadingProgress() {

    }

    @Override
    public void hideLoadingProgress() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_borrow;
    }


    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initParams() {
        initData();
        if (isClick) {
//            Drawable nav_up=getResources().getDrawable(R.drawable.button_nav_up);
//            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
//            textview1.setCompoundDrawables(null, null, nav_up, null);
//            tvSelectComprehensive
        }
        adapter = new BorrowListAdapter(getActivity(), borrowListEntityList);
        rvBorrowList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBorrowList.setAdapter(adapter);

    }


    @OnClick({R.id.tv_select_comprehensive, R.id.tv_low_rate, R.id.tv_speed_loan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_comprehensive:
                createLoanSelectWindow();
                break;
            case R.id.tv_low_rate:
                break;
            case R.id.tv_speed_loan:
                break;
        }
    }

    /**
     * 创建条件选择窗口
     */
    private void createLoanSelectWindow() {
        if (loanSelectWindow == null) {
            loanSelectWindow = new LoanSelectWindow(getActivity(), getActivity().findViewById(R.id.line_invisible));
            loanSelectWindow.setiSelectListener(new LoanSelectWindow.ISelectListener() {
                @Override
                public void selectComprehensive() {
                    tvSelectComprehensive.setText("综合");
                }

                @Override
                public void selectEasyPass() {
                    tvSelectComprehensive.setText("易通过");
                }

                @Override
                public void selectLimitUp() {
                    tvSelectComprehensive.setText("额度升序");

                }

                @Override
                public void selectLimitDown() {
                    tvSelectComprehensive.setText("额度降序");
                }
            });

        }
        loanSelectWindow.showView(getActivity().findViewById(R.id.line_invisible));
    }

    @Override
    public void onServerFiled(String s) {

    }

    @Override
    public void getBorrowListSuccess() {

    }

    private void initData() {
        borrowListEntityList.clear();
        for (int i = 0; i < 8; i++) {
            Random random = new Random();
            int index = random.nextInt(borrowListEntities.length);
            borrowListEntityList.add(borrowListEntities[index]);
        }
    }
}

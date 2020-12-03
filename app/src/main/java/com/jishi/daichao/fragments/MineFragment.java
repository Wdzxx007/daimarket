package com.jishi.daichao.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jishi.daichao.R;
import com.jishi.daichao.adapter.GridMenuAdapter;
import com.jishi.daichao.base.RxBaseFragment;
import com.jishi.daichao.entity.GridMenuEntity;
import com.jishi.daichao.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2019/2/13.
 */

public class MineFragment extends RxBaseFragment implements GridMenuAdapter.OnGridMenuItemListener {
    @BindView(R.id.img_set)
    ImageView imgSet;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.rl_user_phone)
    RelativeLayout rlUserPhone;
    @BindView(R.id.rv_me_asset_view_gridmenu)
    RecyclerView rvMeAssetViewGridmenu;
    private List<GridMenuEntity> gridMenuEntities;
    private GridMenuAdapter gridMenuAdapter;

    @Override
    public void showLoadingProgress() {

    }

    @Override
    public void hideLoadingProgress() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mine;
    }


    @Override
    protected void initView(View view) {
        rvMeAssetViewGridmenu.setHasFixedSize(true);
        rvMeAssetViewGridmenu.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }

    @Override
    protected void initParams() {
        refreshGridMenuView();
    }


    @OnClick(R.id.img_set)
    public void onViewClicked() {
    }

    /**
     * 刷新菜单按钮view
     */
    private void refreshGridMenuView() {

        gridMenuEntities = GridMenuEntity.getGridMenuEntityList();
        if (gridMenuAdapter != null) {
            gridMenuAdapter.update(gridMenuEntities);
        } else {
            gridMenuAdapter = new GridMenuAdapter(getActivity(), gridMenuEntities);
            gridMenuAdapter.setOnGridMenuItemListener(this);
            rvMeAssetViewGridmenu.setAdapter(gridMenuAdapter);
        }

    }

    @Override
    public void onGridMenuItemClick(View view, int position, GridMenuEntity gridMenuEntity) {
        Intent intent = null;
        switch (gridMenuEntity.getImgIconResId()) {
            case R.mipmap.icon_mine_invite:
                //邀请有礼
                ToastUtil.showToast("邀请有礼");

                break;
            case R.mipmap.icon_mine_question:
                //贷款问答
                ToastUtil.showToast("贷款问答");
                break;
            case R.mipmap.icon_mine_contact:
                //联系方式
                ToastUtil.showToast("联系方式");
                break;
        }

        if (intent != null)
            startActivity(intent);
    }
}

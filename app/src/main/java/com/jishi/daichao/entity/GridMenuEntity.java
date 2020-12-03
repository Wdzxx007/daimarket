package com.jishi.daichao.entity;


import com.jishi.daichao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/8.
 */

public class GridMenuEntity {
    public static List<GridMenuEntity> gridMenuEntityList;
    public String title;
    public int imgIconResId;
    public int imgTagResId;

    public GridMenuEntity(String title, int imgIconResId, int imgTagResId) {
        this.title = title;
        this.imgIconResId = imgIconResId;
        this.imgTagResId = imgTagResId;
    }

    public static List<GridMenuEntity> getGridMenuEntityList() {
        gridMenuEntityList = new ArrayList<>();
        //账户九宫格
        gridMenuEntityList.add(new GridMenuEntity("邀请有礼", R.mipmap.icon_mine_invite, 0));
        gridMenuEntityList.add(new GridMenuEntity("贷款问答", R.mipmap.icon_mine_question, 0));
        gridMenuEntityList.add(new GridMenuEntity("联系方式", R.mipmap.icon_mine_contact, 0));
        return gridMenuEntityList;
    }


    public String getTitle() {
        return title;
    }

    public int getImgIconResId() {
        return imgIconResId;
    }

    public int getImgTagResId() {
        return imgTagResId;
    }
}

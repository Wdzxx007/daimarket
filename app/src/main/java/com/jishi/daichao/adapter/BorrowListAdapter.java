package com.jishi.daichao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jishi.daichao.R;
import com.jishi.daichao.entity.BorrowListEntity;

import java.util.List;

/**
 * Created by jialijiang on 17/3/1.
 */

public class BorrowListAdapter extends RecyclerView.Adapter<BorrowListAdapter.ViewHolder> {

    private Context mContext;

    private List<BorrowListEntity> borrowListEntityList;

    public BorrowListAdapter(Context context, List list) {
        this.mContext = context;
        this.borrowListEntityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.borrow_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BorrowListEntity borrowListEntity = borrowListEntityList.get(position);
        holder.tv_borrow_title.setText(borrowListEntity.getTitle());
        holder.tv_borrow_requirement.setText(borrowListEntity.getRequirement());
        holder.tv_borrow_money.setText(borrowListEntity.getMonety());
        holder.tv_borrow_rate.setText(borrowListEntity.getRate());
        holder.tv_borrow_apply_people.setText(borrowListEntity.getPeopleCount());
        holder.progress.setProgress(Integer.valueOf(borrowListEntity.getSchedule()));


    }

    @Override
    public int getItemCount() {
        return borrowListEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_borrow;
        ImageView img_hot;
        TextView tv_borrow_title;
        TextView tv_borrow_requirement;
        TextView tv_borrow_money;
        TextView tv_borrow_rate;
        TextView tv_borrow_apply_people;
        TextView tv_borrow_full_amount;
        ProgressBar progress;

        public ViewHolder(View view) {
            super(view);
            tv_borrow_title = (TextView) view.findViewById(R.id.tv_borrow_title);
            img_borrow = (ImageView) view.findViewById(R.id.img_borrow);
            img_hot = (ImageView) view.findViewById(R.id.img_hot);
            tv_borrow_requirement = (TextView) view.findViewById(R.id.tv_borrow_requirement);
            tv_borrow_money = (TextView) view.findViewById(R.id.tv_borrow_money);
            tv_borrow_rate = (TextView) view.findViewById(R.id.tv_borrow_rate);
            tv_borrow_apply_people = (TextView) view.findViewById(R.id.tv_borrow_apply_people);
            tv_borrow_full_amount = (TextView) view.findViewById(R.id.tv_borrow_full_amount);
            progress = (ProgressBar) view.findViewById(R.id.progress);
        }
    }
}

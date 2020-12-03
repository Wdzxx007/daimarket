package com.jishi.daichao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jishi.daichao.R;
import com.jishi.daichao.entity.GridMenuEntity;

import java.util.List;

/**
 * Created by laulee on 16/8/1.
 */
public class GridMenuAdapter extends RecyclerView.Adapter<GridMenuAdapter.ViewHolder>
{
    private Context mContext;
    private OnGridMenuItemListener onGridMenuItemListener;
    private List<GridMenuEntity> gridMenuEntities;

    public GridMenuAdapter(Context context)
    {
        this.mContext = context;
    }


    public GridMenuAdapter(Context context, List<GridMenuEntity> gridMenuEntities)
    {
        this.mContext = context;
        this.gridMenuEntities = gridMenuEntities;
    }

    @Override
    public GridMenuAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.grid_menu_item, null);
        return new ViewHolder (view);
    }

    @Override
    public void onBindViewHolder (final GridMenuAdapter.ViewHolder holder, final int position)
    {

        if ( position < gridMenuEntities.size () )
        {
            final GridMenuEntity gridMenuEntity = gridMenuEntities.get (position);
            holder.imgIcon.setImageResource (gridMenuEntity.getImgIconResId ());
            holder.lblContent.setText (gridMenuEntity.getTitle ());
            if ( onGridMenuItemListener != null )
            {
                holder.viewGroup.setOnClickListener (new View.OnClickListener ()
                {
                    @Override
                    public void onClick (View v)
                    {
                        onGridMenuItemListener.onGridMenuItemClick (holder.viewGroup, position, gridMenuEntity);
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount ()
    {
        if ( gridMenuEntities.size () % 3 == 0 )
            return gridMenuEntities.size ();
        else
            return (gridMenuEntities.size () / 3 + 1) * 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imgIcon;
        private TextView lblContent;
        private TextView lbl_grid_menu_item_hot;
        private View viewGroup;

        public ViewHolder (View itemView)
        {
            super (itemView);
            viewGroup = itemView.findViewById (R.id.ll_grid_menu_item);
            lblContent = (TextView) itemView.findViewById (R.id.lbl_grid_menu_item_title);
            imgIcon = (ImageView) itemView.findViewById (R.id.img_grid_menu_item_icon);
        }
    }

    public interface OnGridMenuItemListener
    {
        void onGridMenuItemClick(View view, int position, GridMenuEntity gridMenuEntity);
    }

    public void setOnGridMenuItemListener (OnGridMenuItemListener onGridMenuItemListener)
    {
        this.onGridMenuItemListener = onGridMenuItemListener;
    }

    public void updateView ()
    {
        notifyDataSetChanged ();
    }

    /**
     * 更新
     *
     * @param gridMenuEntities
     */
    public void update (List<GridMenuEntity> gridMenuEntities)
    {
        this.gridMenuEntities = gridMenuEntities;
        notifyDataSetChanged ();
    }
}

package com.jm.projectunion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseRecyclerViewHolder;
import com.jm.projectunion.dao.bean.AreaBean;
import com.zhy.autolayout.utils.AutoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Young on 2017/12/13.
 */

public class AreaSelShowAdapter extends BaseAdapter<AreaBean> {

    private Context context;
    private OnItemClickListener listener;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int position, AreaBean data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (null != data) {

            holder.city_name.setText(data.getName());
            holder.city_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != listener) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    public void setOnclickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        //View view = LayoutInflater.from(context).inflate(R.layout.activity_area_item, viewGroup, false);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_area_item, viewGroup, false);
        return view;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {

        public ViewHolder(View view) {
            super(view);
            AutoUtils.autoSize(view);
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.city_del)
        ImageView city_del;
        @BindView(R.id.city_name)
        TextView city_name;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}

package com.jm.projectunion.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class HCAreaSelShowAdapter extends BaseAdapter<AreaBean> {

    private Context context;
    private OnItemClickListener listener;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int position, AreaBean data) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        if (null != data) {
            holder.city_name.setText(data.getName());
            if(data.isIfmy()==true) {
                holder.city_name.setBackgroundResource(R.drawable.bg_border_radius_h);
            }else {
                if(position%2!=0)
                    holder.city_name.setBackgroundColor(Color.rgb(192, 192, 192));
                if(position%2==0)
                    holder.city_name.setBackgroundColor(Color.rgb(176, 176, 176));
            }
            holder.city_name.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View view) {
                    if (null != listener) {
                        //holder.city_name.setBackgroundColor(Color.rgb(254, 253, 227));
                        listener.onItemClick(position);
                    }
                    if (holder.city_name.getBackground().equals(view.getResources().getDrawable(R.drawable.bg_border_radius_h))){
                        if(position%2!=0)
                            holder.city_name.setBackgroundColor(Color.rgb(192, 192, 192));
                        if(position%2==0)
                            holder.city_name.setBackgroundColor(Color.rgb(176, 176, 176));
                    }else{
                        holder.city_name.setBackground(view.getResources().getDrawable(R.drawable.bg_border_radius_h));
                        //data.isIfmy()=false;
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
        View view = LayoutInflater.from(context).inflate(R.layout.activity_area_item_hc, viewGroup, false);
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
        @BindView(R.id.city_name)
        TextView city_name;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}

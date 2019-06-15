package com.jm.projectunion.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseRecyclerViewHolder;
import com.jm.projectunion.home.entiy.InfoListResult;
import com.zhy.autolayout.utils.AutoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YangPan on 2017/11/5.
 */

public class RedPacketAdapter extends BaseAdapter<InfoListResult.InfoListBean> {

    private Context mContext;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int position, InfoListResult.InfoListBean data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (null != data) {
            holder.title.setText(data.getTitle());
            holder.price.setText(data.getRedMoney());
            holder.num.setText(data.getRedRecNum() + "/" + data.getRedNum());
            Glide.with(mContext).load(data.getImage()+"?imageslim").asBitmap().error(R.mipmap.img_def).into(holder.img);
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_redpacket_item, viewGroup, false);
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

        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.num)
        TextView num;
        @BindView(R.id.price)
        TextView price;
    }
}

package com.jm.projectunion.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseRecyclerViewHolder;
import com.jm.projectunion.common.utils.StringUtils;
import com.jm.projectunion.message.entity.MsgSystemResult;
import com.zhy.autolayout.utils.AutoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Young on 2017/11/8.
 */

public class MsgSystemAdapter extends BaseAdapter<MsgSystemResult.MsgSystemBean> {

    private Context mContext;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int position, MsgSystemResult.MsgSystemBean data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (null != data) {
            holder.title.setText(data.getName());
            holder.time.setText(data.getCtime());
            holder.intro.setText(StringUtils.delHTMLTag(data.getContent()));
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_msg_system_item, viewGroup, false);
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

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.intro)
        TextView intro;
    }
}

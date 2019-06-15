package com.jm.projectunion.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseRecyclerViewHolder;
import com.jm.projectunion.mine.entity.CollectResult;
import com.zhy.autolayout.utils.AutoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Young on 2017/10/30.
 */

public class MineCollectAdapter extends BaseAdapter<CollectResult.CollectBean> {
    private Context mContext;
    private OnDelClickListener onClickListener;

    public void setDelOnclicklistener(OnDelClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int position, final CollectResult.CollectBean data) {
        ViewHolder mViewHolder = (ViewHolder) viewHolder;
        if (data != null) {
            mViewHolder.distance.setVisibility(View.VISIBLE);
            mViewHolder.del.setVisibility(View.VISIBLE);
            mViewHolder.date.setTextColor(mContext.getResources().getColor(R.color.text_normal));
            mViewHolder.title.setText(data.getName());
            mViewHolder.date.setText(data.getCtime());
            mViewHolder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onDel(data.getCollectId(), position);
                }
            });
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.global_txt, viewGroup, false);
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
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.distance)
        TextView distance;
        @BindView(R.id.del)
        TextView del;
    }

    public interface OnDelClickListener {
        void onDel(String id, int position);
    }
}

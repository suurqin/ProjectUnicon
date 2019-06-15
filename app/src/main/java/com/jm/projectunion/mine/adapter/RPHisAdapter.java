package com.jm.projectunion.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseRecyclerViewHolder;
import com.jm.projectunion.mine.entity.RPHisResult;
import com.zhy.autolayout.utils.AutoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Young on 2017/12/15.
 */

public class RPHisAdapter extends BaseAdapter<RPHisResult.RPHisBean> {

    private Context mContext;
    private OnItemDelClickListener listener;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int position, RPHisResult.RPHisBean data) {
        ViewHolder mViewHolder = (ViewHolder) viewHolder;
        if (null != data) {
            mViewHolder.title.setText(data.getTitle());
            mViewHolder.money.setText("+" + data.getMoney());
            mViewHolder.date.setText(data.getCtime());
            mViewHolder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != listener) {
                        listener.onDel(position);
                    }
                }
            });
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_income_item, viewGroup, false);
        return view;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public void setOnItemDelListener(OnItemDelClickListener listener) {
        this.listener = listener;
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
        @BindView(R.id.money)
        TextView money;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.del)
        TextView del;
    }

    public interface OnItemDelClickListener {
        void onDel(int position);
    }
}

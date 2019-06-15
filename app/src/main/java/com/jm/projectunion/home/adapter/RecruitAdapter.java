package com.jm.projectunion.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseRecyclerViewHolder;
import com.jm.projectunion.home.dto.ReleaseInfoDto;
import com.jm.projectunion.home.entiy.InfoListResult;
import com.jm.projectunion.home.entiy.RecruitBean;
import com.zhy.autolayout.utils.AutoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Young on 2017/10/30.
 */

public class RecruitAdapter extends BaseAdapter<InfoListResult.InfoListBean> {

    private Context mContext;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int position, InfoListResult.InfoListBean data) {
        ViewHolder mViewHolder = (ViewHolder) viewHolder;
        if (data != null) {
            mViewHolder.date.setVisibility(View.VISIBLE);
            mViewHolder.distance.setVisibility(View.VISIBLE);
            mViewHolder.title.setText(data.getTitle());
            mViewHolder.date.setText(data.getDate());
            mViewHolder.distance.setText(data.getDistance());
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
    }
}

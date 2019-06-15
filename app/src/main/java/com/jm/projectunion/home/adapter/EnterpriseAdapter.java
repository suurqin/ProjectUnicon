package com.jm.projectunion.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseRecyclerViewHolder;
import com.jm.projectunion.home.entiy.InfoListResult;
import com.zhy.autolayout.utils.AutoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Young on 2017/10/30.
 */

public class EnterpriseAdapter extends BaseAdapter<InfoListResult.InfoListBean> {
    private Context mContext;
    private OnDelClickListener onClickListener;
    private boolean isShowDel = false;

    public EnterpriseAdapter(boolean isShowDel) {
        this.isShowDel = isShowDel;
    }

    public void setDelOnclicklistener(OnDelClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int position, final InfoListResult.InfoListBean data) {
        ViewHolder mViewHolder = (ViewHolder) viewHolder;
        if (data != null) {
            mViewHolder.date.setVisibility(View.VISIBLE);
            if (isShowDel) {
                mViewHolder.del.setVisibility(View.VISIBLE);
            }
            String title_str = data.getTitle().length() > 15 ? data.getTitle().substring(0, 15) + "..." : data.getTitle();
            mViewHolder.title.setText(title_str);
//            mViewHolder.distance.setText(data.getDistance());
            mViewHolder.distance.setVisibility(View.GONE);
            mViewHolder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onDel(data.getEntityId(), position);
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

package com.jm.projectunion.friends.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseRecyclerViewHolder;
import com.jm.projectunion.common.widget.CircleImageView;
import com.jm.projectunion.friends.bean.FriendBean;
import com.jm.projectunion.message.chat.ChatActivity;
import com.jm.projectunion.message.chat.helper.Constant;
import com.zhy.autolayout.utils.AutoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Young on 2017/11/23.
 */

public class FriendChildAdapter extends BaseAdapter<FriendBean> {
    private Context mContext;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int position, final FriendBean data) {
        ViewHolder mViewHolder = (ViewHolder) viewHolder;
        if (data != null) {
            mViewHolder.friend_num.setVisibility(View.GONE);
            mViewHolder.friend_im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ToastUtils.showShort(mContext, "跳转聊天页面，需要IM支持，暂不做处理");
                    mContext.startActivity(new Intent(mContext, ChatActivity.class).putExtra(Constant.EXTRA_USER_ID, data.getPhone()).putExtra(Constant.EXTRA_USER_NICKNAME, data.getRealname()));
//                    startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(Constant.EXTRA_USER_ID, "robotone"));
                }
            });

            mViewHolder.friend_tel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(Intent.ACTION_DIAL);
                    intent1.setData(Uri.parse("tel:" + data.getPhone()));
                    mContext.startActivity(intent1);
                }
            });

            //wfx 修改  开始
            String vipTypeStr = data.getVipType();
            String vipTypeName = "无法获取";
            if("0".equals(vipTypeStr)){
                vipTypeName =  "普通会员";
            }else if("1".equals(vipTypeStr)){
                vipTypeName =  "高级会员";
            }else if("2".equals(vipTypeStr)){
                vipTypeName =  "企业会员";
            }
            mViewHolder.vip_type.setText(vipTypeName);
            mViewHolder.name.setText(data.getRealname());
            Glide.with(mContext).load(data.getAvatar() + "?imageslim").asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.app_logo).into(mViewHolder.avator);
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_friends_item_group, viewGroup, false);
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

        @BindView(R.id.friend_im)
        ImageView friend_im;
        @BindView(R.id.friend_tel)
        ImageView friend_tel;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.avator)
        CircleImageView avator;
        @BindView(R.id.friend_num)
        TextView friend_num;
        @BindView(R.id.vip_type_group)
        TextView vip_type;

    }
}

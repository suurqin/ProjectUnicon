package com.jm.projectunion.friends.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.jm.projectunion.R;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.common.widget.CircleImageView;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.friends.activity.FriendInfoActivity;
import com.jm.projectunion.friends.bean.FriendBean;
import com.jm.projectunion.friends.bean.FriendRect;
import com.jm.projectunion.message.chat.ChatActivity;
import com.jm.projectunion.message.chat.helper.Constant;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Young on 2017/11/13.
 */

public class FriendsAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private List<FriendRect> recList;

    public FriendsAdapter(Activity context) {
        this.context = context;
    }

    public void setData(List<FriendRect> recList) {
        this.recList = recList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return null == recList ? 0 : recList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return null == recList.get(groupPosition).getRecList() ? 0 : recList.get(groupPosition).getRecList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final ViewHoldGroup holderGroup;
        final FriendRect bean = recList.get(groupPosition);
        if (null == convertView) {
            holderGroup = new ViewHoldGroup();
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_friends_item_group, parent, false);

            //wfx 修改
            holderGroup.vip_type = convertView.findViewById(R.id.vip_type_group);
            holderGroup.avator = convertView.findViewById(R.id.avator);
            holderGroup.name = convertView.findViewById(R.id.name);
            holderGroup.num = convertView.findViewById(R.id.friend_num);
            holderGroup.child_top = convertView.findViewById(R.id.child_top);
            holderGroup.friend_tel = convertView.findViewById(R.id.friend_tel);
            holderGroup.friend_im = convertView.findViewById(R.id.friend_im);
            convertView.setTag(holderGroup);
            AutoUtils.auto(convertView);
        } else {
            holderGroup = (ViewHoldGroup) convertView.getTag();
        }

        holderGroup.name.setText(bean.getRealname());

        //wfx 修改  开始
        String vipTypeStr = bean.getVipType();
        String vipTypeName = "无法获取";
        if("0".equals(vipTypeStr)){
            vipTypeName =  "普通会员";
        }else if("1".equals(vipTypeStr)){
            vipTypeName =  "高级会员";
        }else if("2".equals(vipTypeStr)){
            vipTypeName =  "企业会员";
        }
        holderGroup.vip_type.setText(vipTypeName);

        //wfx 修改  结束

        holderGroup.num.setText(bean.getRecNum1());
        Glide.with(context).load(bean.getAvatar() + "?imageslim").asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.app_logo).into(holderGroup.avator);
        holderGroup.avator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendRect bean = recList.get(groupPosition);
                Bundle bundle = new Bundle();
                bundle.putString("name", bean.getRealname());
                bundle.putString("id", bean.getUserId());
                UiGoto.startAtyWithBundle(context, FriendInfoActivity.class, bundle);
            }
        });
        holderGroup.friend_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.showShort(context, "跳转聊天页面，需要IM支持，咱不做处理");
                context.startActivity(new Intent(context, ChatActivity.class).putExtra(Constant.EXTRA_USER_ID, bean.getPhone()).putExtra(Constant.EXTRA_USER_NICKNAME, bean.getRealname()));
            }
        });

        holderGroup.friend_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_DIAL);
                intent1.setData(Uri.parse("tel:" + bean.getPhone()));
                context.startActivity(intent1);
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild, View convertView, ViewGroup parent) {
        final ViewHoldChild holderChild;
        final FriendBean bean = recList.get(groupPosition).getRecList().get(childPosition);
        if (null == convertView) {
            holderChild = new ViewHoldChild();
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_friends_item_child, parent, false);
            //wfx 修改
            holderChild.vip_type = convertView.findViewById(R.id.vip_type_child);
            holderChild.avator = convertView.findViewById(R.id.avator);
            holderChild.name = convertView.findViewById(R.id.name);
            holderChild.friend_add = convertView.findViewById(R.id.friend_add);
            holderChild.friend_im = convertView.findViewById(R.id.friend_im);
            convertView.setTag(holderChild);
            AutoUtils.auto(convertView);
        } else {
            holderChild = (ViewHoldChild) convertView.getTag();
        }

        if ("1".equals(bean.getStat())) {
            holderChild.friend_im.setVisibility(View.VISIBLE);
        } else {
            holderChild.friend_im.setVisibility(View.GONE);
        }
        //wfx 修改  开始
        String vipTypeStr = bean.getVipType();
        String vipTypeName = "无法获取";
        if("0".equals(vipTypeStr)){
            vipTypeName =  "普通会员";
        }else if("1".equals(vipTypeStr)){
            vipTypeName =  "高级会员";
        }else if("2".equals(vipTypeStr)){
            vipTypeName =  "企业会员";
        }
        holderChild.vip_type.setText(vipTypeName);

        //wfx 修改  结束

        holderChild.name.setText(bean.getRealname());
        Glide.with(context).load(bean.getAvatar() + "?imageslim").asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.app_logo).into(holderChild.avator);
        holderChild.friend_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.showShort(context, "跳转聊天页面，需要IM支持，咱不做处理");
                context.startActivity(new Intent(context, ChatActivity.class).putExtra(Constant.EXTRA_USER_ID, bean.getPhone()).putExtra(Constant.EXTRA_USER_NICKNAME, bean.getRealname()));
            }
        });
        holderChild.friend_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    EMClient.getInstance().contactManager().addContact(bean.getPhone(), "让信息交流更方便");
//                } catch (HyphenateException e) {
//                    e.printStackTrace();
//                }
                ApiClient.addFriend(context, bean.getPhone(), new ResultCallback<ResultData>() {
                    @Override
                    public void onSuccess(ResultData response) {
                        ToastUtils.showShort(context, response.getMsg());
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.showShort(context, msg);
                    }
                });
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class ViewHoldGroup {
        CircleImageView avator;
        TextView name;
        TextView num;
        //wfx 修改 会员类型  0会员，1高级会员，2VIP
        TextView vip_type;
        ImageView friend_tel;
        ImageView friend_im;
        RelativeLayout child_top;
    }

    static class ViewHoldChild {
        CircleImageView avator;
        TextView name;
        ImageView friend_add;
        ImageView friend_im;
        //wfx 修改 会员类型  0会员，1高级会员，2VIP
        TextView vip_type;
    }
}

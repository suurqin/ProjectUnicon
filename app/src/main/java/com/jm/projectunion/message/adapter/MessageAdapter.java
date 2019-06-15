package com.jm.projectunion.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.widget.CircleImageView;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by Young on 2017/11/9.
 */

public class MessageAdapter extends BaseAdapter {

    public Context context;

    public MessageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_message_item, viewGroup, false);
            holder.useravatar = convertView.findViewById(R.id.useravator);
            holder.username = convertView.findViewById(R.id.username);
            holder.msg = convertView.findViewById(R.id.msg);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder {
        CircleImageView useravatar;
        TextView username;
        TextView msg;
    }
}

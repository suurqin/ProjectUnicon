package com.jm.projectunion.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by Young on 2017/10/26.
 */

public class ShareAdapter extends BaseAdapter {

    private final int[] imgs = {R.mipmap.share_wx, R.mipmap.share_circle, R.mipmap.share_qzone, R.mipmap.share_qq,
            R.mipmap.share_sina, R.mipmap.share_copy};
    private final String[] labels = {"微信", "朋友圈", "QQ空间", "QQ好友"};
    //"新浪微博", "复制链接"
    private Context context;

    public ShareAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return labels.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_share_item, parent, false);
            holder.icon = convertView.findViewById(R.id.icon);
            holder.label = convertView.findViewById(R.id.label);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.icon.setImageResource(imgs[position]);
        holder.label.setText(labels[position]);
        return convertView;
    }

    static class ViewHolder {
        ImageView icon;
        TextView label;
    }
}

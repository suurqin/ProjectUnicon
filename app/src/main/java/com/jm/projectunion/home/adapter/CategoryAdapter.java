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

public class CategoryAdapter extends BaseAdapter {

    private final int[] imgs = {
            R.mipmap.home_icon_1, R.mipmap.home_icon_2, R.mipmap.home_icon_3, R.mipmap.home_icon_4, R.mipmap.home_icon_5,
            R.mipmap.home_icon_6, R.mipmap.home_icon_7, R.mipmap.home_icon_8, R.mipmap.home_icon_9, R.mipmap.home_icon_10,
            R.mipmap.home_icon_19,R.mipmap.home_icon_11, R.mipmap.home_icon_12, R.mipmap.home_icon_16,R.mipmap.home_icon_13,
            R.mipmap.home_icon_20,R.mipmap.home_icon_15, R.mipmap.home_icon_14,  R.mipmap.home_icon_17,R.mipmap.home_icon_18 };

    private final String[] labels = {
            "法律服务", "房屋租售",       "招工",        "商业服务",  "保险服务",
            "建筑证件", "二手车设备",    "劳务市场",    "二手建材",  "农民工之家",
            "便民商家", "找队伍",         "找人才",     "企业专区",     "红包",
            "功能介绍", "找机械",       "找材料",   "项目专区" ,    "信息发布"
    };

    private Context context;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgs.length;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_home_item, parent, false);
            holder.icon = convertView.findViewById(R.id.icon);
            holder.label = convertView.findViewById(R.id.label);
            convertView.setTag(holder);
            AutoUtils.auto(convertView);
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

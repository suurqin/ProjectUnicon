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

public class InfoCategoryAdapter extends BaseAdapter {

    private final int[] imgs = {R.mipmap.release_icon_1, R.mipmap.release_icon_2, R.mipmap.release_icon_3, R.mipmap.release_icon_4, R.mipmap.release_icon_5,
            R.mipmap.release_icon_6, R.mipmap.release_icon_7, R.mipmap.release_icon_8, R.mipmap.release_icon_9, R.mipmap.release_icon_10, R.mipmap.release_icon_11,
            R.mipmap.release_icon_12, R.mipmap.release_icon_13, R.mipmap.release_icon_14};
    private final String[] labels = {"发布置顶广告", "发布法律服务", "发布房屋租售", "发布招工信息", "发布商业服务",
            "发布二手建材", "发布保险服务", "发布二手车设备", "发布零工信息", "发布建筑证件", "农民工之家",
            "发布便民商家", "发布项目专区", "发布红包"};

    private Context context;

    public InfoCategoryAdapter(Context context) {
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
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.label.setVisibility(View.VISIBLE);
        holder.icon.setImageResource(imgs[position]);
        holder.label.setText(labels[position]);
        return convertView;
    }

    static class ViewHolder {
        ImageView icon;
        TextView label;
    }
}

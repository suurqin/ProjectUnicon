package com.jm.projectunion.information.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jm.projectunion.R;
import com.jm.projectunion.common.manager.Config;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Young on 2017/10/31.
 */

public class PicAdapter extends BaseAdapter {

    private Context context;
    private onPicListener listener;
    private List<String> photos = new ArrayList<>();
    private int max;

    public PicAdapter(Context context, List<String> photos, int max) {
        this.context = context;
        this.photos = photos;
        this.max = max;
    }

    public void setData(List<String> photos) {
        this.photos = photos;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (null != photos) {
            if (max == photos.size()) {
                return max;
            } else {
                return photos.size() + 1;
            }
        } else {
            photos = new ArrayList<>();
            return 1;
        }
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.pic_item, parent, false);
            holder.pic_thumb = convertView.findViewById(R.id.pic_thumb);
            holder.pic_del = convertView.findViewById(R.id.pic_del);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == photos.size()) {
            holder.pic_del.setVisibility(View.GONE);
            holder.pic_thumb.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Glide.with(context).load(R.mipmap.pic_add).asBitmap().into(holder.pic_thumb);
            holder.pic_thumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onAddPic();
                }
            });
        } else {
            holder.pic_del.setVisibility(View.VISIBLE);
            holder.pic_thumb.setOnClickListener(null);
            System.out.println(photos.get(position));
            holder.pic_thumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(context).load(photos.get(position)).into(holder.pic_thumb);
        }
        holder.pic_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != listener) {
                    listener.onDelPic(position);
                }
            }
        });
        return convertView;
    }

    public void setOnPicListener(onPicListener listener) {
        this.listener = listener;
    }

    static class ViewHolder {
        ImageView pic_thumb;
        ImageView pic_del;
    }

    public interface onPicListener {
        void onAddPic();

        void onDelPic(int position);
    }
}

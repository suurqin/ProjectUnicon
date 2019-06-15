package com.jm.projectunion.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jm.projectunion.ImageViewActivity;
import com.jm.projectunion.R;
import com.jm.projectunion.common.manager.Config;
import com.jm.projectunion.common.utils.TextViewUtils;
import com.jm.projectunion.utils.UiGoto;

import java.util.List;

/**
 * Created by bobo on 2017/12/19.
 */

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<String> piclist;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    public ImageAdapter(Context context, List<String> piclist) {
        this.context = context;
        this.piclist = piclist;
    }

    public void setData(List<String> piclist) {
        this.piclist = piclist;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == piclist ? 0 : piclist.size();
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, TextViewUtils.getRealHeight(context, 450)));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(piclist.get(i)+"?imageslim").asBitmap().error(R.mipmap.img_def).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiGoto.startAtyWithType(context, ImageViewActivity.class, piclist.get(i));
            }
        });
        return imageView;
    }
}

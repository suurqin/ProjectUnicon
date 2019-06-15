package com.jm.projectunion.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jm.projectunion.R;
import com.jm.projectunion.home.activity.DetailsActivity;
import com.jm.projectunion.home.entiy.BannerResult;
import com.jm.projectunion.utils.UiGoto;

import java.util.List;

/**
 * Created by Young on 2017/10/26.
 */

public class GuideAdapter extends PagerAdapter {
    private Context context;
    private int[] resIds;

    public GuideAdapter(Context context, int[] resIds) {
        this.context = context;
        this.resIds = resIds;
    }

    @Override
    public int getCount() {
        return null == this.resIds ? 0 : this.resIds.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(resIds[position]).asBitmap().error(R.mipmap.img_def).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}

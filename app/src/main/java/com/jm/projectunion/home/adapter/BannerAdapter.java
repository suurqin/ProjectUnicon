package com.jm.projectunion.home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jm.projectunion.R;
import com.jm.projectunion.common.manager.Config;
import com.jm.projectunion.home.activity.DetailsActivity;
import com.jm.projectunion.home.entiy.BannerResult;
import com.jm.projectunion.utils.UiGoto;

import java.util.List;

/**
 * Created by Young on 2017/10/26.
 */

public class BannerAdapter extends PagerAdapter {
    private Context context;
    private List<BannerResult.Banner> data;
    private boolean clickEable;

    public BannerAdapter(Context context, List<BannerResult.Banner> data, boolean clickEable) {
        this.context = context;
        this.data = data;
        this.clickEable = clickEable;
    }

    @Override
    public int getCount() {
        return null == this.data ? 0 : this.data.size();
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
        if (clickEable) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", DetailsActivity.TYPE_BANNER);
                    bundle.putString("id", data.get(position).getArticleId());
                    bundle.putString("bannerType", data.get(position).getArticleType());
                    bundle.putString("atype", "1");
                    UiGoto.startAtyWithBundle(context, DetailsActivity.class, bundle);
                }
            });
        }
        Glide.with(context).load(data.get(position).getUrl() + "?imageslim").asBitmap().error(R.mipmap.img_def).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}

package com.jm.projectunion;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jm.projectunion.common.base.BaseActivity;
import com.jm.projectunion.utils.UiGoto;

import butterknife.BindView;

/**
 * Created by YangPan on 2017/12/24.
 */

public class ImageViewActivity extends BaseActivity {

    @BindView(R.id.img_show)
    ImageView img_show;

    @Override
    public void initView() {
        String url = getIntent().getStringExtra(UiGoto.HOME_TYPE);
        Glide.with(this).load(url).asBitmap().error(R.mipmap.img_def).into(img_show);
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_img_show;
    }
}

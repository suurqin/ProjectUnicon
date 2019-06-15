package com.jm.projectunion;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.jm.projectunion.adapter.GuideAdapter;
import com.jm.projectunion.common.base.BaseActivity;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.utils.UiGoto;

import butterknife.BindView;

/**
 * Created by YangPan on 2017/12/23.
 */

public class GuideActivity extends BaseActivity {

    @BindView(R.id.guide)
    ViewPager guide;
    @BindView(R.id.start)
    ImageView start;

    private PrefUtils prefUtils;
    private int[] resIds = {R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};

    @Override
    public void initView() {
        prefUtils = PrefUtils.getInstance(this);
        guide.setAdapter(new GuideAdapter(this, resIds));
        guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == resIds.length - 1) {
                    start.setVisibility(View.VISIBLE);
                } else {
                    start.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != prefUtils.getUserInfo()) {
                    UiGoto.startAty(GuideActivity.this, MainActivity.class);
                } else {
                    UiGoto.startAty(GuideActivity.this, LoginActivity.class);
                }
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_guide;
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }
}

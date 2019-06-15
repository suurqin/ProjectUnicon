package com.jm.projectunion.home.activity;

import android.support.v4.app.Fragment;

import com.jm.projectunion.common.base.SingleFragmentActivity;
import com.jm.projectunion.home.fragment.ReleaseInfoCategoryFragment;

/**
 * Created by YangPan on 2017/11/10.
 */

public class ReleaseInfoCategoryActivity extends SingleFragmentActivity {
    @Override
    public void initView() {
        setTitleText("信息发布");
    }

    @Override
    public Fragment createFragment() {
        return new ReleaseInfoCategoryFragment();
    }
}

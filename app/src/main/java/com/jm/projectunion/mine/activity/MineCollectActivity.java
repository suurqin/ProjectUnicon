package com.jm.projectunion.mine.activity;

import android.support.v4.app.Fragment;

import com.jm.projectunion.common.base.SingleFragmentActivity;
import com.jm.projectunion.mine.fragment.MineCollectFragment;

/**
 * Created by Young on 2017/11/7.
 */

public class MineCollectActivity extends SingleFragmentActivity {
    @Override
    public void initView() {
        setTitleText("我的收藏");
    }

    @Override
    public Fragment createFragment() {
        return new MineCollectFragment();
    }
}

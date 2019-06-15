package com.jm.projectunion.mine.activity;

import android.support.v4.app.Fragment;

import com.jm.projectunion.common.base.SingleFragmentActivity;
import com.jm.projectunion.mine.fragment.GrapRPacketHistListFragment;

/**
 * Created by Young on 2017/11/8.
 */

public class GrapRPacketHistActivity extends SingleFragmentActivity {
    @Override
    public void initView() {
        setTitleText("抢红包记录");
    }

    @Override
    public Fragment createFragment() {
        return new GrapRPacketHistListFragment();
    }
}

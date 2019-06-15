package com.jm.projectunion.home.activity;

import android.support.v4.app.Fragment;

import com.jm.projectunion.common.base.SingleFragmentActivity;
import com.jm.projectunion.home.fragment.RedPacketFragment;

/**
 * Created by YangPan on 2017/11/5.
 */

public class RedPacketActivity extends SingleFragmentActivity {
    @Override
    public void initView() {
        setTitleText("红包");
    }

    @Override
    public Fragment createFragment() {
        return new RedPacketFragment();
    }
}

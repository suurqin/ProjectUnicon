package com.jm.projectunion.mine.activity;

import android.support.v4.app.Fragment;

import com.jm.projectunion.common.base.SingleFragmentActivity;
import com.jm.projectunion.mine.fragment.LeaguerFragment;
import com.jm.projectunion.utils.UiGoto;

/**
 * Created by Young on 2017/11/8.
 */

public class LeaguerActivity extends SingleFragmentActivity {
    @Override
    public void initView() {
        setTitleText("用户购买");
    }

    @Override
    public Fragment createFragment() {
        String time = getIntent().getStringExtra(UiGoto.HOME_TYPE);
        LeaguerFragment leaguerFragment = LeaguerFragment.newInstance(time);
        return leaguerFragment;
    }
}

package com.jm.projectunion.mine.activity;

import android.support.v4.app.Fragment;

import com.jm.projectunion.common.base.SingleFragmentActivity;
import com.jm.projectunion.mine.fragment.CashFragment;

/**
 * Created by Young on 2017/11/7.
 */

public class CashActivity extends SingleFragmentActivity {
    @Override
    public void initView() {
        setTitleText("提现");
    }

    @Override
    public Fragment createFragment() {
        return new CashFragment();
    }
}

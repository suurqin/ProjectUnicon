package com.jm.projectunion.mine.activity;

import android.support.v4.app.Fragment;

import com.jm.projectunion.common.base.SingleFragmentActivity;
import com.jm.projectunion.mine.fragment.ModifyPwFragment;

/**
 * Created by Young on 2017/11/7.
 */

public class ModifyPwActivity extends SingleFragmentActivity {
    @Override
    public void initView() {
        setTitleText("修改密码");
    }

    @Override
    public Fragment createFragment() {
        return new ModifyPwFragment();
    }
}

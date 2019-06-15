package com.jm.projectunion.home.activity;

import android.support.v4.app.Fragment;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.common.base.SingleFragmentActivity;
import com.jm.projectunion.home.fragment.UseHelpFragment;

/**
 * Created by YangPan on 2017/10/29.
 */

public class UseHelpActivity extends SingleFragmentActivity {

    @Override
    public void initView() {
        setTitleText("使用帮助");
    }

    @Override
    public Fragment createFragment() {
        return new UseHelpFragment();
    }
}

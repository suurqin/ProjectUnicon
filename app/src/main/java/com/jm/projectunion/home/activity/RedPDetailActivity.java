package com.jm.projectunion.home.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jm.projectunion.common.base.SingleFragmentActivity;
import com.jm.projectunion.home.entiy.InfoListResult;
import com.jm.projectunion.home.fragment.RedPDetailFragment;
import com.jm.projectunion.utils.UiGoto;

/**
 * Created by Young on 2017/11/17.
 */

public class RedPDetailActivity extends SingleFragmentActivity {
    @Override
    public void initView() {

    }

    @Override
    public Fragment createFragment() {
        Bundle bundle = getIntent().getBundleExtra(UiGoto.BUNDLE);
        String rpId = bundle.getString("id");
        String receiveStatu = bundle.getString("receiveStatu");
        String title = bundle.getString("title");
        setTitleText(title);
        RedPDetailFragment redPDetailFragment = RedPDetailFragment.newInstance(rpId, receiveStatu);
        return redPDetailFragment;
    }
}

package com.jm.projectunion.mine.activity;

import android.support.v4.app.Fragment;
import android.view.View;

import com.jm.projectunion.common.base.SingleFragmentActivity;
import com.jm.projectunion.mine.fragment.BalanceFragment;
import com.jm.projectunion.utils.UiGoto;

/**
 * Created by Young on 2017/11/7.
 */

public class BalanceActivity extends SingleFragmentActivity {
    @Override
    public void initView() {
        setTitleText("余额");
        setEnsureText("明细");

        getEnsureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiGoto.startAty(BalanceActivity.this, IncomeDetailsActivity.class);
            }
        });
    }

    @Override
    public Fragment createFragment() {
        return new BalanceFragment();
    }
}

package com.jm.projectunion.common.base;

import android.support.v4.app.Fragment;

import com.jm.projectunion.R;


/**
 * 一个Fragment的容器
 *
 * @author Young
 * @date 2015年7月2日 下午7:24:32
 */
public abstract class SingleFragmentActivity extends BaseTitleActivity {
    private Fragment mFragment;

    @Override
    public void initData() {
        if (mFragment == null) {
            mFragment = createFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.single_fragment_content, mFragment).commit();
    }

    public abstract Fragment createFragment();

    @Override
    protected int getContentResId() {
        return R.layout.base_single_fragment_activity;
    }

}

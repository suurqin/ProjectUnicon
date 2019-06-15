package com.jm.projectunion;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jm.projectunion.common.base.BaseWebFragment;
import com.jm.projectunion.common.base.SingleFragmentActivity;
import com.jm.projectunion.utils.UiGoto;

/**
 * Created by Young on 2017/12/14.
 */

public class WebViewActivity extends SingleFragmentActivity {

    private String url;

    @Override
    public void initView() {
        Bundle bundle = getIntent().getBundleExtra(UiGoto.BUNDLE);
        url = bundle.getString("url");
        String title = bundle.getString("title");
//        setTitleText("免责条款");
        setTitleText(title);
    }

    @Override
    public Fragment createFragment() {
        BaseWebFragment webFragment = BaseWebFragment.newInstance(url);
        return webFragment;
    }
}

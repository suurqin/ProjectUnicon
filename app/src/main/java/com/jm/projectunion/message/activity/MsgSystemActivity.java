package com.jm.projectunion.message.activity;

import android.support.v4.app.Fragment;

import com.jm.projectunion.common.base.SingleFragmentActivity;
import com.jm.projectunion.message.fragment.MsgSystemListFragment;
import com.jm.projectunion.utils.UiGoto;

/**
 * Created by Young on 2017/11/8.
 */

public class MsgSystemActivity extends SingleFragmentActivity {

    public static final String MSG_SYS = "2";
    public static final String MSG_INDU = "3";
    public static final String MSG_DATA = "4";

    private String type;

    @Override
    public void initView() {
        type = getIntent().getStringExtra(UiGoto.HOME_TYPE);
        if (MSG_SYS.equals(type)) {
            setTitleText("系统消息");
        } else if (MSG_INDU.equals(type)) {
            setTitleText("行业动态");
        } else if (MSG_DATA.equals(type)) {
            setTitleText("常用数据");
        }
    }

    @Override
    public Fragment createFragment() {
        return MsgSystemListFragment.newInstance(type);
    }
}

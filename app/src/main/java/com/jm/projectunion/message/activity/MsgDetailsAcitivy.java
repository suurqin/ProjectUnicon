package com.jm.projectunion.message.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.message.entity.MsgSystemResult;
import com.jm.projectunion.utils.UiGoto;

import butterknife.BindView;

/**
 * Created by Young on 2017/11/8.
 */

public class MsgDetailsAcitivy extends BaseTitleActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.intro)
    TextView intro;

    @Override
    public void initView() {
        Bundle bundle = getIntent().getBundleExtra(UiGoto.BUNDLE);
        MsgSystemResult.MsgSystemBean bean = (MsgSystemResult.MsgSystemBean) bundle.getSerializable("msg");
        if ("2".equals(bean.getType())) {
            setTitleText("系统消息");
        } else if ("3".equals(bean.getType())) {
            setTitleText("行业动态");
        } else if ("4".equals(bean.getType())) {
            setTitleText("常用数据");
        }
        title.setText(bean.getName());
        time.setText(bean.getCtime());
        intro.setText(bean.getContent());
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_msg_detail;
    }
}

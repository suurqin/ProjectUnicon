package com.jm.projectunion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.utils.UiGoto;

import butterknife.BindView;

public class MsgActivity extends BaseTitleActivity {

    @BindView(R.id.tv_ctime)
    TextView tvCtime;
    @BindView(R.id.tv_content)
    TextView tvContent;

    String title = "";
    String ctime = "";
    String content = "";

    @Override
    protected int getContentResId() {
        return R.layout.activity_msg;
    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getBundleExtra(UiGoto.BUNDLE);
        title = bundle.getString("title");
        ctime = bundle.getString("ctime");
        content = bundle.getString("content");
//        setTitleText("免责条款");
        setTitleText(title);
    }

    @Override
    public void initData() {
        tvCtime.setText(ctime);
        content = content.replaceAll("<p>","");
        content = content.replaceAll("</p>","");
        content = content.replaceAll("</br>","");
        content = content.replaceAll("<br>","");
        tvContent.setText(content);
    }
}

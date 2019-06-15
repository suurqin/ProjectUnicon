package com.jm.projectunion.home.activity;

import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.home.entiy.FuncDetailResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import butterknife.BindView;

/**
 * Created by bobo on 2017/12/18.
 */

public class UserHelpDetailActivity extends BaseTitleActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView content;

    private String id;

    @Override
    public void initView() {
        setTitleText("问题详情");
        id = getIntent().getStringExtra(UiGoto.HOME_TYPE);
        getData(id);
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_userhelp_detail;
    }

    private void getData(String id) {
        showDialogLoading();
        ApiClient.getFuncDetail(this, id, new ResultCallback<FuncDetailResult>() {
            @Override
            public void onSuccess(FuncDetailResult response) {
                hideDialogLoading();
                System.out.println("result--helpd" + response.toString());
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        FuncDetailResult.FuncDetailBean bean = response.getData();
                        title.setText(bean.getQuestion());
                        content.setText(bean.getAnswer());
                    }
                    ToastUtils.showShort(UserHelpDetailActivity.this, response.getMsg());
                }
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                ToastUtils.showShort(UserHelpDetailActivity.this, msg);
            }
        });
    }
}

package com.jm.projectunion.mine.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.HashMap;
import java.util.Map;

public class IdCardAuthRelultActivity extends BaseTitleActivity {
    private String params = null;
    private String sign = null;


    //对校验结果进行处理
    private void getIdCardAuthResult(){

        ApiClient.addCardStat(this, new ResultCallback<ResultData>() {
            @Override
            public void onSuccess(ResultData response) {
                hideDialogLoading();
                Boolean flag =Boolean.parseBoolean(response.getData());
                if(flag){
                    ToastUtils.showShort(IdCardAuthRelultActivity.this, "验证成功!");
                    finish();

                }else{
                    ToastUtils.showShort(IdCardAuthRelultActivity.this, "验证失败!");
                    finish();
                }
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                ToastUtils.showShort(IdCardAuthRelultActivity.this, msg);
            }
        });
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_id_card_auth_relult;
    }

    @Override
    public void initView() {
        setTitleText("实名认证结果");
    }

    @Override
    public void initData() {
        getIdCardAuthResult();
    }
}

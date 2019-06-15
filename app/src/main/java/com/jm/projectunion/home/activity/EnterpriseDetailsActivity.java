package com.jm.projectunion.home.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.common.widget.NoScrollListView;
import com.jm.projectunion.home.adapter.ImageAdapter;
import com.jm.projectunion.home.dto.ReleaseInfoDto;
import com.jm.projectunion.home.entiy.EnterpriseDetailResult;
import com.jm.projectunion.home.entiy.InfoDetailsResult;
import com.jm.projectunion.information.dto.EnterpriseInfoDto;
import com.jm.projectunion.information.entity.EnterpriseInfoDetailResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import butterknife.BindView;

/**
 * Created by bobo on 2017/12/19.
 */

public class EnterpriseDetailsActivity extends BaseTitleActivity {

    @BindView(R.id.company_name)
    TextView company_name;
    @BindView(R.id.company_money)
    TextView company_money;
    @BindView(R.id.company_master)
    TextView company_master;
    @BindView(R.id.company_address)
    TextView company_address;
    @BindView(R.id.service_scope)
    TextView service_scope;
    @BindView(R.id.building_type)
    TextView building_type;
    @BindView(R.id.company_pic)
    NoScrollListView company_pic;

    @Override
    public void initView() {
        setTitleText("企业详情");
        Bundle bundle = getIntent().getBundleExtra(UiGoto.BUNDLE);
        String id = bundle.getString("id");
        getData(id);
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_enterprise_info;
    }

    private void getData(String id) {
        showDialogLoading();
        ApiClient.getEnterpriseInfoDetail(this, id, new ResultCallback<EnterpriseInfoDetailResult>() {
            @Override
            public void onSuccess(EnterpriseInfoDetailResult response) {
                hideDialogLoading();
                System.out.println("result-eDl=" + response.toString());
                if (null != response) {
                    ToastUtils.showShort(EnterpriseDetailsActivity.this, response.getMsg());
                    if ("0".equals(response.getCode())) {
                        setDataInView(response.getData());
                    }
                }
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                ToastUtils.showShort(EnterpriseDetailsActivity.this, msg);
            }
        });
    }

    private void setDataInView(EnterpriseInfoDto data) {
        if (null != data) {
            company_name.setText("公司名称：" + data.getName());
            company_money.setText("注册资金：" + data.getRegisterMoney());
            company_master.setText("法人名称：" + data.getMaster());
            company_master.setText("公司地址：" + data.getAddrDetails());
            company_pic.setAdapter(new ImageAdapter(this, data.getImgs()));
        }
    }
}

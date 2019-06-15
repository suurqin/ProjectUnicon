package com.jm.projectunion.mine.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseExtraFragment;
import com.jm.projectunion.utils.api.ApiClient;

import butterknife.BindView;

/**
 * Created by Young on 2017/11/7.
 */

public class RechargeFragment extends BaseExtraFragment {

    @BindView(R.id.pay_mode)
    RadioGroup pay_mode;
    @BindView(R.id.pay_wx)
    RadioButton pay_wx;
    @BindView(R.id.pay_alipay)
    RadioButton pay_alipay;
    @BindView(R.id.submit)
    Button submit;

    @Override
    public void initView(View view) {
        submit.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_recharge;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.submit:
                if (pay_wx.isChecked()) {
                    payByWX();
                } else if (pay_alipay.isChecked()) {
                    payByAlipay();
                }
                break;
        }
    }

    private void payByWX() {

    }

    private void payByAlipay() {

    }

    private void submit() {
    }
}

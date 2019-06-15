package com.jm.projectunion.mine.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.StringUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.mine.entity.BanksResult;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import butterknife.BindView;

/**
 * Created by Young on 2017/12/13.
 */

public class BankAddActivity extends BaseTitleActivity {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.number)
    EditText number;
    @BindView(R.id.add_btn)
    Button add_btn;

    @Override
    public void initView() {
        setTitleText("添加支付宝");
        add_btn.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_bank_add;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.add_btn:
                if (TextUtils.isEmpty(name.getText().toString().trim())) {
                    ToastUtils.showShort(this, "请填写姓名");
                    return;
                }
                if (TextUtils.isEmpty(number.getText().toString().trim())) {
                    ToastUtils.showShort(this, "请填写支付宝账号");
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("请确认支付宝账号");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        addBank();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
    }

    private void addBank() {
        String cardId = number.getText().toString().trim();
//        if (!StringUtils.checkBankCard(cardId)) {
//            ToastUtils.showShort(this, "银行卡号错误");
//            return;
//        }
        showDialogLoading();
        BanksResult.BankBean bankBean = new BanksResult.BankBean();
        bankBean.setUserId(PrefUtils.getInstance(this).getUserInfo().getUserId());
        bankBean.setCardId(cardId);
        bankBean.setUserName(name.getText().toString().trim());
        ApiClient.addBank(this, bankBean, new ResultCallback<ResultData>() {
            @Override
            public void onSuccess(ResultData response) {
                hideDialogLoading();
                ToastUtils.showShort(BankAddActivity.this, response.getMsg());
                if ("0".equals(response.getCode())) {
                    finish();
                }
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                ToastUtils.showShort(BankAddActivity.this, msg);
            }
        });
    }
}

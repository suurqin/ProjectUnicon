package com.jm.projectunion.mine.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.information.activity.UserInfoEditActivity;
import com.jm.projectunion.mine.entity.BanksResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;

public class IDentityActivity extends BaseTitleActivity {

    @BindView(R.id.auth_btn)
    Button authBtn;
    @BindView(R.id.authname)
    EditText authname;
    @BindView(R.id.authidentity)
    EditText authIdentity;

    @Override
    protected int getContentResId() {
        return R.layout.activity_identity;
    }

    @Override
    public void initView() {
        setTitleText("实名认证");
        authBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    //=========================================wfx修改 芝麻认证 开始======================================================

    /**
     * 启动支付宝进行认证
     * @param url 开放平台返回的URL
     */
    private void doVerify(String url) {
        if (hasApplication()) {
            Intent action = new Intent(Intent.ACTION_VIEW);
            StringBuilder builder = new StringBuilder();
            // 这里使用固定appid 20000067
            builder.append("alipays://platformapi/startapp?appId=20000067&url=");
            builder.append(URLEncoder.encode(url));
            action.setData(Uri.parse(builder.toString()));
            startActivity(action);
        } else {
            // 处理没有安装支付宝的情况
            new AlertDialog.Builder(this)
                    .setMessage("是否下载并安装支付宝完成认证?")
                    .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent action = new Intent(Intent.ACTION_VIEW);
                            action.setData(Uri.parse("https://m.alipay.com"));
                            startActivity(action);
                        }
                    }).setNegativeButton("算了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
    }

    /**
     * 判断是否安装了支付宝
     * @return true 为已经安装
     */
    private boolean hasApplication() {
        PackageManager manager = this.getPackageManager();
        Intent action = new Intent(Intent.ACTION_VIEW);
        action.setData(Uri.parse("alipays://"));
        List list = manager.queryIntentActivities(action, PackageManager.GET_RESOLVED_FILTER);
        return list != null && list.size() > 0;
    }

    //=========================================wfx修改 芝麻认证 结束======================================================

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.auth_btn:
                getUrl();
//                hideDialogLoading();
                finish();
                break;
        }
    }

    private void getUrl(){
//        showDialogLoading();
        String cardId = authIdentity.getText().toString().trim();
        String name = authname.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            ToastUtils.showShort(IDentityActivity.this, "请填写真实姓名");
            return;
        }
        if(TextUtils.isEmpty(cardId)){
            ToastUtils.showShort(IDentityActivity.this, "请填写身份证号");
            return;
        }
        BanksResult.BankBean bankBean = new BanksResult.BankBean();
        bankBean.setUserId(PrefUtils.getInstance(this).getUserInfo().getUserId());
        bankBean.setCardId(cardId);
        bankBean.setUserName(name);
        ApiClient.addUserIdentityCard(this, bankBean, new ResultCallback<ResultData>() {
            @Override
            public void onSuccess(ResultData response) {
//                hideDialogLoading();
                String url = response.getData();
                if(url == null || "".equals(url)){
                    ToastUtils.showShort(IDentityActivity.this, "请填写正确的信息再试!");
                }else{
                    doVerify(url);

                }
            }

            @Override
            public void onError(String msg) {
//                hideDialogLoading();
                ToastUtils.showShort(IDentityActivity.this, msg);
            }
        });
    }


}

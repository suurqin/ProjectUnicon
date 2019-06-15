package com.jm.projectunion;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jm.projectunion.common.base.BaseActivity;
import com.jm.projectunion.common.utils.AppInfoUtils;
import com.jm.projectunion.common.utils.DialogUtils;
import com.jm.projectunion.common.utils.EncryptUtil;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.entity.LoginOrRegistResult;
import com.jm.projectunion.entity.Result;
import com.jm.projectunion.entity.VersionResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;
import com.jm.projectunion.wiget.DialogConfirmView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Young on 2017/10/26.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_user)
    EditText login_user;
    @BindView(R.id.login_pw)
    EditText login_pw;
    @BindView(R.id.no_account)
    TextView no_account;
    @BindView(R.id.no_forget)
    TextView no_forget;
    @BindView(R.id.login_btn)
    Button login_btn;

    private Dialog dialog;
    private DialogConfirmView dialogConfirmView;
    private PrefUtils prefUtils;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        prefUtils = PrefUtils.getInstance(this);
        no_account.setOnClickListener(this);
        no_forget.setOnClickListener(this);
        login_btn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        checkVersion();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.login_btn:
                String username = login_user.getText().toString().trim();
                String pw = login_pw.getText().toString();
                if (null == username || "".equals(username)) {
                    dialogConfirmView = DialogConfirmView.newInstance(this);
                    View view = dialogConfirmView.create(null, "请输入您的手机号");
                    dialogConfirmView.setPositiveClickListener(new DialogConfirmView.onPositiveClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (null != dialog) {
                                dialog.dismiss();
                            }
                        }
                    });
                    dialog = DialogUtils.showCustomDialog(this, view, false);
                } else if (null == pw || "".equals(pw)) {
                    dialogConfirmView = DialogConfirmView.newInstance(this);
                    View view = dialogConfirmView.create(null, "请输入密码");
                    dialogConfirmView.setPositiveClickListener(new DialogConfirmView.onPositiveClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (null != dialog) {
                                dialog.dismiss();
                            }
                        }
                    });
                    dialog = DialogUtils.showCustomDialog(this, view, false);
                } else {
                    login(username, pw);
                }
                break;
            case R.id.no_forget:
                UiGoto.startAtyWithType(this, FrogetPasswordActivity.class, FrogetPasswordActivity.FORGET);
                break;
            case R.id.no_account:
                UiGoto.startAty(this, RegistActivity.class);
                finish();
                break;
        }
    }

    /**
     * 登陆
     */
    private void login(final String phone, final String pwd) {
        showDialogLoading();
        Map<String, String> params = new HashMap<>();
        params.put("username", phone);
        params.put("password", pwd);
        ApiClient.login(this, params, new ResultCallback<LoginOrRegistResult>() {
            @Override
            public void onSuccess(LoginOrRegistResult response) {
                System.out.println("result-login=" + response.toString());
                hideDialogLoading();
                if ("0".equals(response.getCode())) {
                    LoginOrRegistResult.RegistBean bean = response.getData();
                    bean.setPhone(phone);
                    prefUtils.setUserinfo(bean);
                    loginIM(phone);
                    UiGoto.startAty(LoginActivity.this, MainActivity.class);
                    finish();
                }
                ToastUtils.showShort(LoginActivity.this, response.getMsg());
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                ToastUtils.showShort(LoginActivity.this, msg);
            }
        });
    }

    /**
     * 检查版本
     */
    private void checkVersion() {
        final VersionResult.VersionBean bean = prefUtils.getVersion();
        if (null != bean) {
            if (AppInfoUtils.getAppVersionCode(LoginActivity.this) < Math.round(Float.valueOf(bean.getVersion()))) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                alertDialogBuilder.setTitle("版本升级");
                alertDialogBuilder.setMessage("APP版本更新");
                if (!"1".equals(bean.getIsForce())) {
                    alertDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                }
                alertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(bean.getUrl());
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = alertDialogBuilder.create();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();//将dialog显示出来
            }
        }
    }

    private void loginIM(String user) {
        String pwd = EncryptUtil.getMD5("HX" + user);
        EMClient.getInstance().login(user, pwd, new EMCallBack() {

            @Override
            public void onSuccess() {
                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                System.out.println("IM登陆--success");
            }

            @Override
            public void onProgress(int progress, String status) {
                System.out.println("IM登陆---loading");
            }

            @Override
            public void onError(final int code, final String message) {
                System.out.println("IM登陆---error" + message);
            }
        });
    }
}

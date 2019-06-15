package com.jm.projectunion;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jm.projectunion.common.base.BaseActivity;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.StringUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.entity.LoginOrRegistResult;
import com.jm.projectunion.entity.Result;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Young on 2017/10/26.
 * 注册页面
 * 修改人:韩冰
 */

public class RegistActivity extends BaseActivity {

    @BindView(R.id.regist_user)
    EditText regist_user;
    @BindView(R.id.regist_safecode)
    EditText regist_safecode;
    @BindView(R.id.regist_count_down)
    TextView regist_count_down;
    @BindView(R.id.regist_pw)
    EditText regist_pw;
    @BindView(R.id.regist_mobile)
    EditText regist_mobile;
    @BindView(R.id.confirm_protocal)
    CheckBox confirm_protocal;
    @BindView(R.id.protocal)
    TextView protocal;
    @BindView(R.id.go_login)
    TextView go_login;
    @BindView(R.id.login_btn)
    Button login_btn;

    private Handler mHandler;
    private long startTime = 0;
    private long duration;
    private Runnable mTicker;
    private int TEMP_TIME = 60000;
    private PrefUtils prefUtils;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        prefUtils = PrefUtils.getInstance(this);
        regist_count_down.setOnClickListener(this);
        protocal.setOnClickListener(this);
        go_login.setOnClickListener(this);
        login_btn.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.regist_count_down:
                String phones = regist_user.getText().toString().trim();
                if (TextUtils.isEmpty(phones)) {
                    ToastUtils.showShort(this, "请填写电话号码");
                } else {
                    if (StringUtils.isMobile(phones)) {
                        getSmsCode(phones);
                    } else {
                        ToastUtils.showShort(this, "请填写正确电话号码");
                    }
                }
                break;
            case R.id.go_login:
                UiGoto.startAty(this, LoginActivity.class);
                finish();
                break;
            case R.id.protocal:
                Bundle bundle = new Bundle();
                bundle.putString("url", "file:///android_asset/protocal.html");
                bundle.putString("title", "工程联盟服务协议");
                UiGoto.startAtyWithBundle(this, WebViewActivity.class, bundle);
                break;
            case R.id.login_btn:
                String phone = regist_user.getText().toString().trim();
                String code = regist_safecode.getText().toString().trim();
                String pwd = regist_pw.getText().toString().trim();
                String mobile = regist_mobile.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showShort(this, "请填写电话号码");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.showShort(this, "请填写短信验证码");
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtils.showShort(this, "请填写密码");
                    return;
                }
//                if (pwd.length() < 6 || pwd.length() > 14) {
//                    ToastUtils.showShort(this, "密码长度6-14个字符");
//                    return;
//                }
//                if (StringUtils.compileExChar(pwd)) {
//                    ToastUtils.showShort(this, "密码不能包含特殊字符");
//                    return;
//                }
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtils.showShort(this, "请填推荐人电话");
                    return;
                }
                if (!confirm_protocal.isChecked()) {
                    ToastUtils.showShort(this, "请阅读协议并同意");
                    return;
                }
                regist(phone, code, pwd, mobile);
                break;
        }
    }

    /**
     * 获取短信验证码
     */
    private void getSmsCode(String phone) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("type", "0");
        ApiClient.smsCode(this, params, new ResultCallback<Result>() {
            @Override
            public void onSuccess(Result response) {
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        ToastUtils.showShort(RegistActivity.this, "请求验证码成功，注意查收短信");
                        countDown();
                    } else {
                        ToastUtils.showShort(RegistActivity.this, response.getMsg());
                    }
                }
            }

            @Override
            public void onError(String msg) {
                ToastUtils.showShort(RegistActivity.this, msg);
            }
        });
    }

    /**
     * 注册
     *
     * @param phone
     * @param code
     * @param pwd
     */
    private void regist(final String phone, String code, String pwd, String mobile) {
        showDialogLoading();
        Map<String, String> params = new HashMap<>();
        params.put("username", phone);
        params.put("code", code);
        params.put("password", pwd);
        params.put("mobile", mobile);
        ApiClient.regist(this, params, new ResultCallback<LoginOrRegistResult>() {
            @Override
            public void onSuccess(LoginOrRegistResult response) {
                hideDialogLoading();
                System.out.print("result-regist=" + response.toString());
                ToastUtils.showShort(RegistActivity.this, response.getMsg());
                if ("0".equals(response.getCode())) {
                    LoginOrRegistResult.RegistBean bean = response.getData();
                    bean.setPhone(phone);
                    prefUtils.setUserinfo(bean);
                    UiGoto.startAty(RegistActivity.this, MainActivity.class);
                    finish();
                }
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                ToastUtils.showShort(RegistActivity.this, msg);
            }
        });
    }

    /**
     * 倒计时
     */
    public void countDown() {
        mTicker = new Runnable() {
            public void run() {
                duration = System.currentTimeMillis() - startTime;
                if (duration >= TEMP_TIME) {
                    mHandler.removeCallbacks(mTicker);
                    regist_count_down.setText("点击重新发送");
                } else {
                    regist_count_down.setText((int) ((TEMP_TIME - duration) / 1000f) + "s后重新发送");
                    mHandler.postDelayed(mTicker, 1000);
                }
            }
        };

        mHandler = new Handler();
        startTime = System.currentTimeMillis();
        mHandler.post(mTicker);
    }
}

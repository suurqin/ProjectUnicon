package com.jm.projectunion;

import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.common.utils.StringUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.entity.Result;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Young on 2017/11/7.
 */

public class FrogetPasswordActivity extends BaseTitleActivity {

    public static final String PAY = "pay";
    public static final String FORGET = "froget";

    @BindView(R.id.regist_count_down)
    TextView regist_count_down;
    @BindView(R.id.regist_user)
    EditText user;
    @BindView(R.id.regist_safecode)
    EditText code;
    @BindView(R.id.regist_pw)
    EditText pwd;
    @BindView(R.id.login_btn)
    Button submit;

    private String modyType;

    private Handler mHandler;
    private long startTime = 0;
    private long duration;
    private Runnable mTicker;
    private int TEMP_TIME = 60000;

    @Override
    public void initView() {
        String type = getIntent().getStringExtra(UiGoto.HOME_TYPE);
        if (PAY.equals(type)) {
            setTitleText("修改支付密码");
            pwd.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            modyType = "2";
            pwd.setHint("输入新的支付密码");
        } else if (FORGET.equals(type)) {
            setTitleText("忘记密码");
            modyType = "1";
        }
        regist_count_down.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_forgetpw;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.regist_count_down:
                String phones = user.getText().toString().trim();
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
            case R.id.login_btn:
                String phone = user.getText().toString().trim();
                String codeNum = code.getText().toString().trim();
                String pwdStr = pwd.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showShort(this, "请填写电话号码");
                    return;
                }
                if (TextUtils.isEmpty(codeNum)) {
                    ToastUtils.showShort(this, "请填写短信验证码");
                    return;
                }
                if (TextUtils.isEmpty(pwdStr)) {
                    ToastUtils.showShort(this, "请填写密码");
                    return;
                }
//                if (pwdStr.length() < 6 || pwdStr.length() > 14) {
//                    ToastUtils.showShort(this, "密码长度6-14个字符");
//                    return;
//                }
//                if (StringUtils.compileExChar(pwdStr)) {
//                    ToastUtils.showShort(this, "密码不能包含特殊字符");
//                    return;
//                }
                modify(phone, pwdStr, codeNum);
                break;
        }
    }

    /**
     * 修改密码
     */
    private void modify(String phone, String password, String code) {
        showDialogLoading();
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("passwd", password);
        params.put("code", code);
        params.put("type", modyType);//1：登陆密码，2：修改支付密码
        ApiClient.modifyPayPWD(this, params, new ResultCallback<Result>() {
            @Override
            public void onSuccess(Result response) {
                System.out.println("result-pwd=" + response.toString());
                hideDialogLoading();
                ToastUtils.showShort(FrogetPasswordActivity.this, response.getMsg());
                if ("0".equals(response.getCode())) {
                    finish();
                }
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                ToastUtils.showShort(FrogetPasswordActivity.this, msg);
            }
        });
    }

    /**
     * 获取短信验证码
     */
    private void getSmsCode(String phone) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("type", modyType);//0：默认注册，1：忘记密码，2：修改支付密码
        ApiClient.smsCode(this, params, new ResultCallback<Result>() {
            @Override
            public void onSuccess(Result response) {
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        ToastUtils.showShort(FrogetPasswordActivity.this, "请求验证码成功，注意查收短信");
                        countDown();
                    } else {
                        ToastUtils.showShort(FrogetPasswordActivity.this, response.getMsg());
                    }
                }
            }

            @Override
            public void onError(String msg) {
                ToastUtils.showShort(FrogetPasswordActivity.this, msg);
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

package com.jm.projectunion.mine.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseExtraFragment;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.StringUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.entity.Result;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Young on 2017/11/7.
 */

public class ModifyPwFragment extends BaseExtraFragment {

    @BindView(R.id.old_pw)
    EditText old_pw;
    @BindView(R.id.new_pw)
    EditText new_pw;
    @BindView(R.id.confirm_pw)
    EditText confirm_pw;
    @BindView(R.id.login_btn)
    Button login_btn;

    private PrefUtils prefUtils;

    @Override
    public void initView(View view) {
        prefUtils = PrefUtils.getInstance(getActivity());
        login_btn.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_modifypw;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.login_btn:
                String oldPwd = old_pw.getText().toString();
                String newPwd = new_pw.getText().toString();
                String confirmPwd = confirm_pw.getText().toString();
                if (TextUtils.isEmpty(oldPwd)) {
                    showToast("旧密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(newPwd)) {
                    showToast("新密码不能为空");
                    return;
                }
                if (oldPwd.equals(newPwd)) {
                    showToast("新旧密码不能一样");
                    return;
                }
                if (!newPwd.equals(confirmPwd)) {
                    showToast("新密码不一致");
                    return;
                }
//                if (newPwd.length() < 6 || newPwd.length() > 14) {
//                    showToast("密码长度6-14个字符");
//                    return;
//                }
//                if (StringUtils.compileExChar(newPwd)) {
//                    showToast("密码不能包含特殊字符");
//                    return;
//                }
                modifyPWD(oldPwd, newPwd);
                break;
        }
    }

    /**
     * 修改密码
     *
     * @param oldPWD
     * @param newPWD
     */
    private void modifyPWD(String oldPWD, String newPWD) {
        showDialogLoading();
        Map<String, String> params = new HashMap<>();
        params.put("userId", prefUtils.getUserInfo().getUserId());
        params.put("passwd", oldPWD);
        params.put("newPasswd", newPWD);
        ApiClient.modifyPWD(getActivity(), params, new ResultCallback<Result>() {
            @Override
            public void onSuccess(Result response) {
                System.out.println("result-modify=" + response.toString());
                hideDialogLoading();
                ToastUtils.showShort(getActivity(), response.getMsg());
                if ("0".equals(response.getCode())) {
                    getActivity().finish();
                }
            }

            @Override
            public void onError(String msg) {
                ToastUtils.showShort(getActivity(), msg);
            }
        });
    }

    private void showToast(String msg) {
        ToastUtils.showShort(getActivity(), msg);
    }
}

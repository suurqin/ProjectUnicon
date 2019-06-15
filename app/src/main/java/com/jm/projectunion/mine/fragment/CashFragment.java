package com.jm.projectunion.mine.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseExtraFragment;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.common.widget.NoScrollListView;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.mine.adapter.CrashBankAdapter;
import com.jm.projectunion.mine.entity.BanksResult;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Young on 2017/11/7.
 */

public class CashFragment extends BaseExtraFragment {

    @BindView(R.id.banks)
    NoScrollListView banks;
    @BindView(R.id.money)
    EditText money;
    @BindView(R.id.submit)
    Button submit;

    private CrashBankAdapter adapter;
    private List<BanksResult.BankBean> data;
    private String selBankId;
    private String userName;

    @Override
    public void initView(View view) {
        submit.setOnClickListener(this);
        banks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selBankId = data.get(position).getCardId();
                userName = data.get(position).getUserName();
                adapter.setSelPosition(position);
            }
        });
    }

    @Override
    public void initData() {
        adapter = new CrashBankAdapter(getActivity());
        banks.setAdapter(adapter);
        getBanks();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_cash;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.submit:
                String str_money = money.getText().toString().trim();
                if (TextUtils.isEmpty(str_money)) {
                    ToastUtils.showShort(getActivity(), "提现金额不能为空");
                    return;
                }
                if (Integer.valueOf(str_money) < 100) {
                    ToastUtils.showShort(getActivity(), "提现金额不能低于100元");
                    return;
                }
                if (Integer.valueOf(str_money) % 100 != 0) {
                    ToastUtils.showShort(getActivity(), "提现金额为100的整数倍");
                    return;
                }
                if (TextUtils.isEmpty(selBankId)) {
                    ToastUtils.showShort(getActivity(), "选择正确的银行卡");
                    return;
                }
                submitCrash(str_money, selBankId, userName);
                break;
        }
    }

    private void getBanks() {
        showDialogLoading();
        ApiClient.getBanks(getActivity(), new ResultCallback<BanksResult>() {
            @Override
            public void onSuccess(BanksResult response) {
                System.out.println("result--banklist=" + response.toString());
                hideDialogLoading();
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        data = response.getData();
                        if (null != data && data.size() > 0) {
                            selBankId = data.get(0).getCardId();
                            userName = data.get(0).getUserName();
                        }
                        adapter.notifyData(data);
                    }
                }
                ToastUtils.showShort(getActivity(), response.getMsg());
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                ToastUtils.showShort(getActivity(), msg);
            }
        });
    }

    /**
     * 提现
     * userId *用户Id
     * userName *用户真实姓名
     * money *金额
     * cardId *银行卡Id
     */
    private void submitCrash(String money, String crashId, String userName) {
        showDialogLoading();
        Map<String, String> params = new HashMap<>();
        params.put("userId", PrefUtils.getInstance(getActivity()).getUserInfo().getUserId());
        params.put("money", money);
        params.put("cardId", crashId);
        params.put("userName", userName);
        ApiClient.submitCrash(getActivity(), params, new ResultCallback<ResultData>() {
            @Override
            public void onSuccess(ResultData response) {
                hideDialogLoading();
                System.out.println("result-cash==" + response.toString());
                if (null != response) {
                    ToastUtils.showShort(getActivity(), response.getMsg());
                }
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                ToastUtils.showShort(getActivity(), msg);
            }
        });
    }
}

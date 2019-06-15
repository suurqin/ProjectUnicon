package com.jm.projectunion.mine.fragment;

import android.view.View;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseExtraFragment;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.common.widget.NoScrollListView;
import com.jm.projectunion.mine.activity.BankAddActivity;
import com.jm.projectunion.mine.adapter.BankAdapter;
import com.jm.projectunion.mine.entity.BanksResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Young on 2017/11/7.
 */

public class BalanceFragment extends BaseExtraFragment {

    @BindView(R.id.banklist)
    NoScrollListView banklist;
    @BindView(R.id.bank_add)
    TextView bank_add;

    private BankAdapter adapter;
    private List<BanksResult.BankBean> data = new ArrayList<>();

    @Override
    public void initView(View view) {
        adapter = new BankAdapter(getActivity(), data);
        banklist.setAdapter(adapter);
        bank_add.setOnClickListener(this);
    }

    @Override
    public void initData() {
    }

    @Override
    public void onResume() {
        super.onResume();
        getBanks();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_balance;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bank_add:
                UiGoto.startAty(getActivity(), BankAddActivity.class);
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
                        adapter.notifyData(response.getData());
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
}

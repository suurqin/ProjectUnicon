package com.jm.projectunion.home.fragment;

import android.os.Bundle;
import android.view.View;

import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseListFragment;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.dao.AreaDaoUtil;
import com.jm.projectunion.dao.bean.AreaBean;
import com.jm.projectunion.home.activity.EnterpriseDetailsActivity;
import com.jm.projectunion.home.adapter.EnterpriseAdapter;
import com.jm.projectunion.home.dto.ListDto;
import com.jm.projectunion.home.entiy.InfoListResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.List;

/**
 * Created by YangPan on 2017/10/29.
 */

public class EnterpriseListFragment extends BaseListFragment {

    private PrefUtils prefUtils;
    private AreaDaoUtil daoUtil;
    private List<InfoListResult.InfoListBean> data;
    private String types = "1#2";
    private String values;

    @Override
    public void initData() {
        super.initData();
        prefUtils = PrefUtils.getInstance(getActivity());
        daoUtil = new AreaDaoUtil(getActivity());
        AreaBean bean = daoUtil.queryByName(prefUtils.getKeyLocationCity());
        if (null != bean) {
            values = String.valueOf(bean.getAreaId());
        }
        getData(types, values, "0");
    }

    @Override
    protected void onLoadMoreDate() {
        getData(types, values, data.get(data.size() - 1).getLastId());
    }

    @Override
    protected void onRefreshDate() {
        getData(types, values, "0");
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        InfoListResult.InfoListBean bean = (InfoListResult.InfoListBean) data;
        Bundle bundle = new Bundle();
        bundle.putString("id", bean.getEntityId());
        UiGoto.startAtyWithBundle(getActivity(), EnterpriseDetailsActivity.class, bundle);
    }

    @Override
    public BaseAdapter createAdapter() {
        return new EnterpriseAdapter(false);
    }

    private void getData(String types, String values, String lastId) {
        showDialogLoading();
        ListDto dto = new ListDto();
        dto.setArticleType("31");
        dto.setOrder("1");
        dto.setNum("20");
        dto.setTypes(types);
        dto.setValues(values);
        dto.setLastId(lastId);
        dto.setLatitude(String.valueOf(prefUtils.getKeyLocationLat()));
        dto.setLongitude(String.valueOf(prefUtils.getKeyLocationLong()));
        ApiClient.getInfoList(getActivity(), dto, new ResultCallback<InfoListResult>() {
            @Override
            public void onSuccess(InfoListResult response) {
                hideDialogLoading();
                System.out.println("result-enterList=" + response.toString());
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        data = response.getData();
                        setDataResult(data);
                    }
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

    public void getDataFromOut(String value) {
        mCurrentPage = 1;
        values = value;
        getData(types, values, "0");
    }
}


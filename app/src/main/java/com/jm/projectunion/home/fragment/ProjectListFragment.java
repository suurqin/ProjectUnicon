package com.jm.projectunion.home.fragment;

import android.os.Bundle;
import android.view.View;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseListFragment;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.dao.AreaDaoUtil;
import com.jm.projectunion.dao.bean.AreaBean;
import com.jm.projectunion.home.activity.ProjectDetailActivity;
import com.jm.projectunion.home.adapter.ProjectItemAdapter;
import com.jm.projectunion.home.dto.ListDto;
import com.jm.projectunion.home.entiy.InfoListResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Young on 2017/10/30.
 */

public class ProjectListFragment extends BaseListFragment {

    private PrefUtils prefUtils = PrefUtils.getInstance(getActivity());;
    private AreaDaoUtil daoUtil;
    private List<InfoListResult.InfoListBean> data = new ArrayList<>();
    private String types = "1#2#";
    private String values;

    @Override
    public void initData() {
        super.initData();
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
        UiGoto.startAtyWithBundle(getActivity(), ProjectDetailActivity.class, bundle);
    }

    @Override
    public BaseAdapter createAdapter() {
        return new ProjectItemAdapter();
    }

    private void getData(String types, String values, String lastId) {
        showDialogLoading();
        ListDto dto = new ListDto();
        dto.setArticleType("89");
        dto.setOrder("1");
        dto.setNum("20");
        dto.setLastId(lastId);
        dto.setTypes(types);
        dto.setValues(values);
        dto.setLatitude(String.valueOf(prefUtils.getKeyLocationLat()));
        dto.setLongitude(String.valueOf(prefUtils.getKeyLocationLong()));
        ApiClient.getInfoList(getActivity(), dto, new ResultCallback<InfoListResult>() {
            @Override
            public void onSuccess(InfoListResult response) {
                System.out.println("result-prolist=" + response.toString());
                hideDialogLoading();
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

    public void getDataFromOut(String type, String value) {
        mCurrentPage = 1;
        if (null != type) {
            types += type;
        }
        values = value;
        getData(types, values, "0");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.base_list_recyclerview_project;
    }
}

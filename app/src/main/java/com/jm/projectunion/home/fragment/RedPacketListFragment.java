package com.jm.projectunion.home.fragment;

import android.os.Bundle;
import android.view.View;

import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseListFragment;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.dao.AreaDaoUtil;
import com.jm.projectunion.home.activity.RedPDetailActivity;
import com.jm.projectunion.home.adapter.RedPacketAdapter;
import com.jm.projectunion.home.dto.ListDto;
import com.jm.projectunion.home.entiy.InfoListResult;
import com.jm.projectunion.home.entiy.RedPacketResult;
import com.jm.projectunion.mine.entity.RPHisResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.List;

/**
 * Created by YangPan on 2017/11/5.
 */

public class RedPacketListFragment extends BaseListFragment {

    private PrefUtils prefUtils;
    private AreaDaoUtil areaDaoUtil;
    private List<InfoListResult.InfoListBean> data;
    private String areaType = "5";

    @Override
    public void initData() {
        super.initData();
        prefUtils = PrefUtils.getInstance(getActivity());
        areaDaoUtil = new AreaDaoUtil(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        mCurrentPage = 1;
        getData(areaType, "0");
    }

    @Override
    protected void onLoadMoreDate() {
        getData(areaType, data.get(data.size() - 1).getLastId());
    }

    @Override
    protected void onRefreshDate() {
        getData(areaType, "0");
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        InfoListResult.InfoListBean bean = (InfoListResult.InfoListBean) data;
        Bundle bundle = new Bundle();
        bundle.putString("id", bean.getEntityId());
        bundle.putString("receiveStatu", bean.getReceiveStatus());
        bundle.putString("title", bean.getTitle());
        UiGoto.startAtyWithBundle(getActivity(), RedPDetailActivity.class, bundle);
    }

    @Override
    public BaseAdapter createAdapter() {
        return new RedPacketAdapter();
    }

    private void getData(String areaType, String lastId) {
        showDialogLoading();
        this.areaType = areaType;
        ListDto dto = new ListDto();
        dto.setArticleType("88");
        dto.setOrder("1");
        dto.setNum("20");
        //TODO wfx修改   Caused by: java.lang.NullPointerException: Attempt to invoke virtual method 'int com.jm.projectunion.dao.bean.AreaBean.getAreaId()' on a null object reference
        if(areaDaoUtil == null){
            areaDaoUtil = new AreaDaoUtil(getActivity());
        }
        dto.setValues(areaDaoUtil.queryByName(prefUtils.getKeyLocationCity()).getAreaId() + "");
        dto.setLastId(lastId);
        dto.setTypes(areaType);
        dto.setLatitude(String.valueOf(prefUtils.getKeyLocationLat()));
        dto.setLongitude(String.valueOf(prefUtils.getKeyLocationLong()));
        ApiClient.getInfoList(getActivity(), dto, new ResultCallback<InfoListResult>() {
            @Override
            public void onSuccess(InfoListResult response) {
                hideDialogLoading();
                System.out.println("result-rplis=" + response.toString());
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

    public void getDataFromOut(String areaType) {
        mCurrentPage = 1;
        getData(areaType, "0");
    }
}

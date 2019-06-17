package com.jm.projectunion.home.fragment;

import android.os.Bundle;
import android.view.View;

import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseListFragment;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.dao.AreaDaoUtil;
import com.jm.projectunion.dao.bean.AreaBean;
import com.jm.projectunion.friends.activity.FriendInfoActivity;
import com.jm.projectunion.home.activity.DetailsActivity;
import com.jm.projectunion.home.adapter.RecruitAdapter;
import com.jm.projectunion.home.dto.ListDto;
import com.jm.projectunion.home.entiy.InfoListResult;
import com.jm.projectunion.home.entiy.RecruitBean;
import com.jm.projectunion.utils.GlobalVar;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Young on 2017/10/30.
 */

public class RecruitListFragment extends BaseListFragment {

    private static final String ARTICLE_TYPE = "atype";
    private int atype;
    private PrefUtils prefUtils;
    private AreaDaoUtil daoUtil;
    private List<InfoListResult.InfoListBean> data;

    private String types = "1#2#";
    private String values;

    public static RecruitListFragment newInstance(int articleType) {
        RecruitListFragment fragment = new RecruitListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARTICLE_TYPE, articleType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initData() {
        super.initData();
        prefUtils = PrefUtils.getInstance(getActivity());
        daoUtil = new AreaDaoUtil(getActivity());
        Bundle bundle = getArguments();
        if (bundle != null) {
            atype = bundle.getInt(ARTICLE_TYPE);
        }
        AreaBean bean = daoUtil.queryByName(prefUtils.getKeyLocationCity());
        if (null != bean) {
            if(atype == 4){
                while (bean != null && !bean.getPid().equals("1")){
                    bean = daoUtil.queryById(bean.getPid());
                }
            }
            values = String.valueOf(bean.getAreaId());
        }
        getData(atype + "", types, values, "0");
    }

    @Override
    protected void onLoadMoreDate() {
        if (data.size() > 0) {
            getData(atype + "", types, values, data.get(data.size() - 1).getLastId());
        }
    }

    @Override
    protected void onRefreshDate() {
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        InfoListResult.InfoListBean bean = (InfoListResult.InfoListBean) data;
        Bundle bundle = new Bundle();
        bundle.putString("type", DetailsActivity.TYPE_INFO);
        bundle.putString("id", bean.getEntityId());
        bundle.putString("atype", atype + "");
        if (atype >= 21 && atype <= 24) {
            bundle.putString("name", GlobalVar.getCategoryName(String.valueOf(atype)));
            UiGoto.startAtyWithBundle(getActivity(), FriendInfoActivity.class, bundle);
        } else {
            UiGoto.startAtyWithBundle(getActivity(), DetailsActivity.class, bundle);
        }
    }

    @Override
    public BaseAdapter createAdapter() {
        return new RecruitAdapter();
    }

    private void getData(String atype, String types, String values, String lastId) {
        showDialogLoading();
        ListDto dto = new ListDto();
        dto.setArticleType(atype);
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
                System.out.println("result---re=" + response.toString());
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
                ToastUtils.showShort(getActivity(), msg);
                hideDialogLoading();
            }
        });
    }

    public void getDataFromOut(String type, String value) {
        mCurrentPage = 1;
        if (null != type) {
            types += type;
        }
        values = value;
        getData(atype + "", types, values, "0");
    }
}

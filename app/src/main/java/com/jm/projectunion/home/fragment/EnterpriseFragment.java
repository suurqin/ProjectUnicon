package com.jm.projectunion.home.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jm.projectunion.AreaSelActivity;
import com.jm.projectunion.HCAreaSelActivity;
import com.jm.projectunion.R;
import com.jm.projectunion.WebViewActivity;
import com.jm.projectunion.common.base.BaseExtraFragment;
import com.jm.projectunion.common.utils.DialogUtils;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.dao.AreaDaoUtil;
import com.jm.projectunion.dao.bean.AreaBean;
import com.jm.projectunion.home.entiy.ServiceResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;
import com.jm.projectunion.wiget.popupwindow.OnItemClickListener;
import com.jm.projectunion.wiget.popupwindow.PullPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by YangPan on 2017/10/29.
 */

public class EnterpriseFragment extends BaseExtraFragment {

    @BindView(R.id.top)
    LinearLayout top;
    @BindView(R.id.region)
    TextView region;
    @BindView(R.id.classification)
    TextView classification;

    private PrefUtils prefUtils;
    private EnterpriseListFragment enterpriseListFragment;
    private List<ServiceResult.ServiceBean> data;
    private List<String> categorys = new ArrayList<>();
    private PullPopupWindow pullPopupWindow_category;
    private String aredId;
    private String categoryId;
    private AreaDaoUtil daoUtil;

    @Override
    public void initView(View view) {
        daoUtil = new AreaDaoUtil(getActivity());
        enterpriseListFragment = new EnterpriseListFragment();
        getChildFragmentManager().beginTransaction()
                .add(R.id.content, enterpriseListFragment).commit();
        prefUtils = PrefUtils.getInstance(getActivity());
        AreaBean areaBean = daoUtil.queryByName(prefUtils.getKeyLocationCity());
        if (null != areaBean) {
            aredId = String.valueOf(areaBean.getAreaId());
        }
        region.setText(prefUtils.getKeyLocationCity());

        region.setOnClickListener(this);
        classification.setOnClickListener(this);

        pullPopupWindow_category = new PullPopupWindow(getActivity(), categorys);
        pullPopupWindow_category.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                classification.setText(categorys.get(position));
                categoryId = data.get(position).getCategoryId();
                getData(aredId, categoryId);
            }
        });
    }

    @Override
    public void initData() {
        getCategorys("21");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_enterprise;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.region:
//                Intent intent = new Intent(getActivity(), LocationActivity.class);
//                getActivity().startActivityForResult(intent, 100);
                Intent intent = new Intent(getActivity(), HCAreaSelActivity.class);
                intent.putExtra(UiGoto.HOME_TYPE, HCAreaSelActivity.FLAG);
                intent.putExtra(UiGoto.BUNDLE, aredId);
                getActivity().startActivityForResult(intent, 100);
                break;
            case R.id.classification:
                pullPopupWindow_category.setData(categorys);
                pullPopupWindow_category.showAtBottom(top);
//                Intent i = new Intent(getActivity(), WebViewActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("url", "http://39.106.138.102:8092/h5/p/enterprise.html");
////                bundle.putString("url", "file:///android_asset/h5/enterprise.html");
//                bundle.putString("title", "类别选择");
//                i.putExtra(UiGoto.BUNDLE, bundle);
//                startActivityForResult(i, 200);
                break;
        }
    }
    /**
     * 获取企业分类
     */
    private void getCategorys(String cid) {
        final Dialog loading = DialogUtils.showLoading(getActivity());
        ApiClient.getService(getActivity(), "2", cid, new ResultCallback<ServiceResult>() {
            @Override
            public void onSuccess(ServiceResult response) {
                System.out.println("result-eP=" + response.toString());
                if (null != response) {
                    ToastUtils.showShort(getActivity(), response.getMsg());
                    if ("0".equals(response.getCode())) {
                        data = response.getData();
                        for (ServiceResult.ServiceBean bean : data) {
                            categorys.add(bean.getName());
                        }
                    }
                }
                loading.dismiss();
            }

            @Override
            public void onError(String msg) {
                ToastUtils.showShort(getActivity(), msg);
                loading.dismiss();
            }
        });
    }
    /**
     * 获取企业分类----旧

    private void getCategorys() {
        ApiClient.getService(getActivity(), "1", "", new ResultCallback<ServiceResult>() {
            @Override
            public void onSuccess(ServiceResult response) {
                System.out.println("result-eC=" + response.toString());
                if (null != response) {
                    ToastUtils.showShort(getActivity(), response.getMsg());
                    if ("0".equals(response.getCode())) {
                        data = response.getData();
                        for (ServiceResult.ServiceBean bean : data) {
                            categorys.add(bean.getName());
                        }
                    }
                }
            }

            @Override
            public void onError(String msg) {
                ToastUtils.showShort(getActivity(), msg);
            }
        });
    }
     */
    /**
     * 设置选定区域
     *
     * @param city
     */
    public void setArea(String city, String cityId) {
        region.setText(city);
        this.aredId = cityId;
        getData(aredId, categoryId);
    }

    /**
     * 设置分类
     *
     * @param categoryId
     */
    public void setCategory(String categoryId) {
        if (!TextUtils.isEmpty(categoryId)) {
            classification.setText("已选择");
        } else {
            classification.setText("分类");
        }
        this.categoryId = categoryId;
        getData(aredId, categoryId);
    }

    private void getData(String valueArea, String categoryValue) {
        String values = "";
        if (!TextUtils.isEmpty(valueArea)) {
            values += (valueArea + "#");
        }
        if (!TextUtils.isEmpty(categoryValue)) {
            values += categoryValue;
        }
        enterpriseListFragment.getDataFromOut(values);
    }
}

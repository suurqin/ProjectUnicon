package com.jm.projectunion.home.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jm.projectunion.HCAreaSelActivity;
import com.jm.projectunion.LocationActivity;
import com.jm.projectunion.LocationProvinceActivity;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseExtraFragment;
import com.jm.projectunion.common.utils.DialogUtils;
import com.jm.projectunion.common.utils.FileUtils;
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
 * Created by Young on 2017/10/30.
 */

public class RecruitFragment extends BaseExtraFragment {

    private static final String FILE_NAME = "filename";
    private static final String ARTICLE_TYPE = "atype";

    @BindView(R.id.top)
    LinearLayout top;
    @BindView(R.id.region)
    TextView region;
    @BindView(R.id.classification)
    TextView classification;
    @BindView(R.id.sorting)
    TextView sorting;

    private PrefUtils prefUtils;
    private AreaDaoUtil daoUtil;
    private PullPopupWindow pullPopupWindow_category;
    private PullPopupWindow pullPopupWindow_sort;
    private RecruitListFragment recruitListFragment;
    private String filename;
    private int atype;
    private List<String> categorys = new ArrayList<>();
    private List<String> sorts = new ArrayList<>();
    private List<ServiceResult.ServiceBean> data;

    private String aredId;
    private String categroyId;
    private String sortId;

    public static RecruitFragment newInstance(String filename, int articleType) {
        RecruitFragment fragment = new RecruitFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FILE_NAME, filename);
        bundle.putInt(ARTICLE_TYPE, articleType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            filename = bundle.getString(FILE_NAME, "");
            atype = bundle.getInt(ARTICLE_TYPE);
        }
        recruitListFragment = RecruitListFragment.newInstance(atype);
        getChildFragmentManager().beginTransaction()
                .add(R.id.content, recruitListFragment).commit();
        prefUtils = PrefUtils.getInstance(getActivity());
        daoUtil = new AreaDaoUtil(getActivity());

        AreaBean areaBean;
        //region 区域 将城市名赋值给页面
        if(atype == 4){
            //省信息
            areaBean = daoUtil.queryByName(prefUtils.getKeyLocationCity());
            while (areaBean != null && !areaBean.getPid().equals("1")){
                areaBean = daoUtil.queryById(areaBean.getPid());
            }
            //areaBean = daoUtil.queryByName(prefUtils.getKeyLocationProvince());
            region.setText(areaBean.getName());
//            areaBean = daoUtil.queryByName(prefUtils.getKeyLocationCity());
//            region.setText(prefUtils.getKeyLocationCity());
        }else{
            areaBean = daoUtil.queryByName(prefUtils.getKeyLocationCity());
            region.setText(prefUtils.getKeyLocationCity());
        }
        if (null != areaBean) {
            aredId = String.valueOf(areaBean.getAreaId());
        }

        region.setOnClickListener(this);
        classification.setOnClickListener(this);
        sorting.setOnClickListener(this);

        pullPopupWindow_category = new PullPopupWindow(getActivity(), categorys);
        pullPopupWindow_category.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                classification.setText(categorys.get(position));
                if (atype >= 21 && atype <= 24) {
                    categroyId = data.get(position).getCategoryId();
                } else {
                    int categorySuffix;
                    if (atype >= 10) {
                        categorySuffix = atype * 100;
                    } else {
                        categorySuffix = atype * 1000;
                    }
                    categroyId = String.valueOf(categorySuffix + position);
                }
                getData(aredId, categroyId, sortId);
            }
        });

        pullPopupWindow_sort = new PullPopupWindow(getActivity(), sorts);
        pullPopupWindow_sort.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                sorting.setText(sorts.get(position));
                sortId = String.valueOf(position + 3);
                getData(aredId, categroyId, sortId);
            }
        });
    }

    @Override
    public void initData() {
        if (atype >= 21 && atype <= 24) {
            getCategorys(String.valueOf(atype));
        } else {
            categorys = FileUtils.readFileFromAssets(getActivity(), filename);
        }
        sorts.add("最新发布");
        sorts.add("距离最近");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_law;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.region:
                if (atype >= 21 && atype <= 24) {
//                    Intent intent1 = new Intent(getActivity(), HCAreaSelActivity.class);
//                    intent1.putExtra(UiGoto.HOME_TYPE, HCAreaSelActivity.FLAG);
//                    intent1.putExtra(UiGoto.BUNDLE, aredId);
//                    getActivity().startActivityForResult(intent1, 100);
                    Intent intent = new Intent(getActivity(),LocationActivity.class);
                    intent.putExtra("location",region.getText());
                    getActivity().startActivityForResult(intent, 100);
                } else if (atype == 4) {
//                    Intent intent = new Intent(getActivity(),LocationProvinceActivity.class);
//                    getActivity().startActivityForResult(intent, 100);


                    Intent intent = new Intent(getActivity(),LocationActivity.class);
                    intent.putExtra("location",region.getText());
                    getActivity().startActivityForResult(intent, 100);
                }else {
                    Intent intent = new Intent(getActivity(),LocationActivity.class);
                    intent.putExtra("location",region.getText());
                    getActivity().startActivityForResult(intent, 100);
                }
                break;
            case R.id.classification:
                pullPopupWindow_category.setData(categorys);
                pullPopupWindow_category.showAtBottom(top);
                break;
            case R.id.sorting:
                pullPopupWindow_category.setData(sorts);
                pullPopupWindow_sort.showAtBottom(top);
                break;
        }
    }

    /**
     * 设置选定区域
     *
     * @param city
     */
    public void setArea(String city, String cityId) {
        region.setText(city);
        this.aredId = cityId;
        getData(aredId, categroyId, sortId);
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

    private void getData(String valueArea, String categoryValue, String sortValue) {
        String values = "";
        if (!TextUtils.isEmpty(valueArea)) {
            values += (valueArea + "#");
        }
        if (!TextUtils.isEmpty(categoryValue)) {
            values += categoryValue;
        }
        recruitListFragment.getDataFromOut(sortValue, values);
    }
}

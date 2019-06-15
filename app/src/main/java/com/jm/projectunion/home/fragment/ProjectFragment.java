package com.jm.projectunion.home.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jm.projectunion.LocationActivity;
import com.jm.projectunion.LocationProvinceActivity;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseExtraFragment;
import com.jm.projectunion.common.utils.FileUtils;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.dao.AreaDaoUtil;
import com.jm.projectunion.dao.bean.AreaBean;
import com.jm.projectunion.wiget.popupwindow.OnItemClickListener;
import com.jm.projectunion.wiget.popupwindow.PullPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Young on 2017/10/30.
 */

public class ProjectFragment extends BaseExtraFragment {


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
    private ProjectListFragment projectListFragment;
    private List<String> categorys = new ArrayList<>();
    private List<String> sorts = new ArrayList<>();

    private String aredId;
    private String categroyId;
    private String sortId;

    @Override
    public void initView(View view) {
        projectListFragment = new ProjectListFragment();
        getChildFragmentManager().beginTransaction()
                .add(R.id.content, projectListFragment).commit();

        prefUtils = PrefUtils.getInstance(getActivity());
        //项目专区  选择省
        daoUtil = new AreaDaoUtil(getActivity());
//        AreaBean areaBean = daoUtil.queryByName(prefUtils.getKeyLocationProvince());
        AreaBean areaBean = daoUtil.queryByName(prefUtils.getKeyLocationCity());
//        region.setText(prefUtils.getKeyLocationCity());
        if(null != areaBean) {
            aredId = String.valueOf(areaBean.getAreaId());
        }
        region.setText(prefUtils.getKeyLocationCity());

        region.setOnClickListener(this);
        classification.setOnClickListener(this);
        sorting.setOnClickListener(this);

        pullPopupWindow_category = new PullPopupWindow(getActivity(), categorys);
        pullPopupWindow_category.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                classification.setText(categorys.get(position));
                categroyId = String.valueOf(8900 + position);
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
        getData(aredId, categroyId, sortId);
    }

    @Override
    public void initData() {
        categorys = FileUtils.readFileFromAssets(getActivity(), "category_project.txt");
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
//                Intent intent = new Intent(getActivity(), LocationProvinceActivity.class);
                Intent intent = new Intent(getActivity(), LocationActivity.class);
                intent.putExtra("location",region.getText());
                getActivity().startActivityForResult(intent, 100);
//                Intent intent = new Intent(getActivity(), AreaSelActivity.class);
//                intent.putExtra(UiGoto.HOME_TYPE, AreaSelActivity.FLAG);
//                intent.putExtra(UiGoto.BUNDLE, aredId);
//                getActivity().startActivityForResult(intent, 100);
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
    public void setArea(String city, String cityIds) {
        region.setText(city);
        this.aredId = cityIds;
        getData(aredId, categroyId, sortId);
    }

    private void getData(String valueArea, String categoryValue, String sortValue) {
        String values = "";
        if (!TextUtils.isEmpty(valueArea)) {
            values += (valueArea + "#");
        }
        if (!TextUtils.isEmpty(categoryValue)) {
            values += categoryValue;
        }
        projectListFragment.getDataFromOut(sortValue, values);
    }
}

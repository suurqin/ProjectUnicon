package com.jm.projectunion.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jm.projectunion.AreaSelActivity;
import com.jm.projectunion.LocationActivity;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseExtraFragment;
import com.jm.projectunion.common.utils.FileUtils;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.dao.AreaDaoUtil;
import com.jm.projectunion.dao.bean.AreaBean;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.wiget.popupwindow.OnItemClickListener;
import com.jm.projectunion.wiget.popupwindow.PullPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Young on 2017/10/27.
 */

public class LawFragment extends BaseExtraFragment {

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
    private LawListFragment lawListFragment;
    private PullPopupWindow pullPopupWindow_category;
    private PullPopupWindow pullPopupWindow_sort;
    private List<String> categorys = new ArrayList<>();
    private List<String> sorts = new ArrayList<>();
    private String filename;
    private int atype;

    private String aredId;
    private String categroyId;
    private String sortId;

    public static LawFragment newInstance(String filename, int articleType) {
        LawFragment fragment = new LawFragment();
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
        prefUtils = PrefUtils.getInstance(getActivity());
        daoUtil = new AreaDaoUtil(getActivity());
        AreaBean areaBean = daoUtil.queryByName(prefUtils.getKeyLocationCity());
        if (null != areaBean) {
            aredId = String.valueOf(areaBean.getAreaId());
        }
        region.setText(prefUtils.getKeyLocationCity());

        lawListFragment = LawListFragment.newInstance(atype);
        getChildFragmentManager().beginTransaction()
                .add(R.id.content, lawListFragment).commit();
        region.setOnClickListener(this);
        classification.setOnClickListener(this);
        sorting.setOnClickListener(this);


        pullPopupWindow_category = new PullPopupWindow(getActivity(), categorys);
        pullPopupWindow_category.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                classification.setText(categorys.get(position));
                int categorySuffix;
                if (atype >= 10) {
                    categorySuffix = atype * 100;
                } else {
                    categorySuffix = atype * 1000;
                }
                categroyId = String.valueOf(categorySuffix + position);
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
        categorys = FileUtils.readFileFromAssets(getActivity(), filename);
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
                pullPopupWindow_sort.setData(sorts);
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

    private void getData(String valueArea, String categoryValue, String sortValue) {
        String values = "";
        if (!TextUtils.isEmpty(valueArea)) {
            values += (valueArea + "#");
        }
        if (!TextUtils.isEmpty(categoryValue)) {
            values += categoryValue;
        }
        lawListFragment.getDataFromOut(sortValue, values);
    }
}

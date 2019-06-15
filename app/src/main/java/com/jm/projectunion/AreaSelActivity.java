package com.jm.projectunion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jm.projectunion.adapter.AreaSelShowAdapter;
import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.dao.AreaDaoUtil;
import com.jm.projectunion.dao.bean.AreaBean;
import com.jm.projectunion.utils.UiGoto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Young on 2017/12/19.
 */

public class AreaSelActivity extends BaseTitleActivity {

    public static final String FLAG = "single";

    @BindView(R.id.area_num)
    TextView area_num;
    //列表视图
    @BindView(R.id.sel_result)
    RecyclerView sel_result;
    @BindView(R.id.sel_provice)
    TextView sel_provice;
    @BindView(R.id.sel_city)
    TextView sel_city;
    @BindView(R.id.sel_town)
    TextView sel_town;
    @BindView(R.id.btn_add)
    Button btn_add;
    @BindView(R.id.btn_confirm)
    Button btn_confirm;

    private AreaDaoUtil daoUtil;
    private String id_provice;
    private String id_city;
    private String id_town;
    private String id_all = "";
    private String name_provice;
    private String name_city;
    private String name_town;
    private List<AreaBean> selCitys = new ArrayList<>();
    //子列表样式
    private AreaSelShowAdapter adapter;
    private boolean isMroeSel = true;

    @Override
    public void initView() {
        setTitleText("区域选择");
        daoUtil = new AreaDaoUtil(this);
        String type = getIntent().getStringExtra(UiGoto.HOME_TYPE);
        id_all = getIntent().getStringExtra(UiGoto.BUNDLE);
        if (FLAG.equals(type)) {
            isMroeSel = false;
        }
        if (isMroeSel) {
            area_num.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(id_all)) {
            if (id_all.contains(",")) {
                String[] cityIds = id_all.split(",");
                for (String id : cityIds) {
                    selCitys.add(daoUtil.queryById(id));
                }
            } else {
                selCitys.add(daoUtil.queryById(id_all));
            }
        }

        LinearLayoutManager ms = new LinearLayoutManager(this);
        //设置布局管理器水平布局
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        sel_result.setLayoutManager(ms);
        adapter = new AreaSelShowAdapter();
        adapter.setOnclickListener(new AreaSelShowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selCitys.remove(position);
                adapter.remove(position);
            }
        });
        sel_result.setAdapter(adapter);
        adapter.replace(selCitys);

        sel_provice.setOnClickListener(this);
        sel_city.setOnClickListener(this);
        sel_town.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_area_sel;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sel_provice:
                final List<AreaBean> provices = daoUtil.queryByLevel("1");
                List<String> provices_str = new ArrayList<>();
                for (AreaBean bean : provices) {
                    provices_str.add(bean.getName());
                }
                String[] str_names_provice = new String[provices_str.size()];
                AlertDialog.Builder builder_provice = new AlertDialog.Builder(this, 3);
                // 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
                builder_provice.setItems(provices_str.toArray(str_names_provice), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        id_provice = provices.get(which).getAreaId() + "";
                        name_provice = provices.get(which).getName();
                        sel_provice.setText(name_provice);
                        dialog.dismiss();
                    }
                }).create().show();
                sel_city.setText("请选择");
                sel_town.setText("请选择");
                id_city = null;
                id_town = null;
                break;
            case R.id.sel_city:
                if (TextUtils.isEmpty(id_provice)) {
                    return;
                }
                final List<AreaBean> citys = daoUtil.queryByPid(id_provice);
                List<String> citys_str = new ArrayList<>();
                for (AreaBean bean : citys) {
                    citys_str.add(bean.getName());
                }
                String[] str_names_city = new String[citys_str.size()];
                AlertDialog.Builder builder_citys = new AlertDialog.Builder(this, 3);
                // 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
                builder_citys.setItems(citys_str.toArray(str_names_city), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        id_city = citys.get(which).getAreaId() + "";
                        name_city = citys.get(which).getName();
                        sel_city.setText(name_city);
                        dialog.dismiss();
                    }
                }).create().show();
                sel_town.setText("请选择");
                id_town = null;
                break;
            case R.id.sel_town:
                if (TextUtils.isEmpty(id_provice)) {
                    return;
                }
                if (TextUtils.isEmpty(id_city)) {
                    return;
                }
                final List<AreaBean> towns = daoUtil.queryByPid(id_city);
                List<String> towns_str = new ArrayList<>();
                for (AreaBean bean : towns) {
                    towns_str.add(bean.getName());
                }
                String[] str_names_town = new String[towns_str.size()];
                AlertDialog.Builder builder_town = new AlertDialog.Builder(this, 3);
                // 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
                builder_town.setItems(towns_str.toArray(str_names_town), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        id_city = towns.get(which).getAreaId() + "";
                        name_town = towns.get(which).getName();
                        sel_town.setText(name_town);
                        dialog.dismiss();
                    }
                }).create().show();
                break;
            case R.id.btn_add:
                if (isMroeSel) {
                    if (null != selCitys && selCitys.size() >= 3) {
                        ToastUtils.showShort(this, "最多可以选择3个地区");
                        return;
                    }
                } else {
                    if (null != selCitys && selCitys.size() >= 1) {
                        ToastUtils.showShort(this, "最多可以选择1个地区");
                        return;
                    }
                }
                if (id_town != null) {
                    selCitys.add(daoUtil.queryById(id_town));
                } else if (id_city != null) {
                    selCitys.add(daoUtil.queryById(id_city));
                } else if (id_provice != null) {
                    selCitys.add(daoUtil.queryById(id_provice));
                }
                adapter.replace(selCitys);
                break;
            case R.id.btn_confirm:
                if (null != selCitys && selCitys.size() > 0) {
                    String names = "";
                    String ids = "";
                    for (AreaBean bean : selCitys) {
                        names += (bean.getName() + ",");
                        ids += (bean.getAreaId() + ",");
                    }
                    names = names.substring(0, names.length() - 1);
                    ids = ids.substring(0, ids.length() - 1);

                    Intent mIntent = new Intent();
                    mIntent.putExtra("city", names);
                    mIntent.putExtra("cityIds", ids);
                    // 设置结果，并进行传送
                    setResult(100, mIntent);
                    finish();
                } else {
                    ToastUtils.showShort(this, "未选择任何地区");
                }
                break;
        }
    }
}

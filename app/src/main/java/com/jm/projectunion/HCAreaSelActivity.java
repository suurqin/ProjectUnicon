package com.jm.projectunion;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.jm.projectunion.adapter.HCAreaSelShowAdapter;
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

public class HCAreaSelActivity extends BaseTitleActivity {

    public static final String FLAG = "single";

    @BindView(R.id.area_num)
    TextView area_num;
    //列表视图
    @BindView(R.id.sel_result)
    RecyclerView sel_result;
    @BindView(R.id.sel_results)
    RecyclerView sel_results;
    @BindView(R.id.sel_resultss)
    RecyclerView sel_resultss;
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
    private List<AreaBean> getCity = new ArrayList<AreaBean>();

    //选中的城市
    private List<AreaBean> selCityList = new ArrayList<AreaBean>();

    private List<AreaBean> selCitys = new ArrayList<AreaBean>();
    private List<AreaBean> selCityss = new ArrayList<AreaBean>();
    private List<AreaBean> selCitysss = new ArrayList<AreaBean>();
    //子列表样式
    private HCAreaSelShowAdapter adapter;
    private HCAreaSelShowAdapter adapters;
    private HCAreaSelShowAdapter adapterss;
    private boolean isMroeSel = true;
    private int areasId=-1;
    private int count = 0;
    private AreaBean id_provices;
    private AreaBean id_citys;
    //private List<AreaBean> id_towns= new ArrayList<AreaBean>();

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
                    AreaBean ab = daoUtil.queryById(id);
                    if(null!=ab){
                        count++;
                        if(ab.getLevel().equals("3")){
                            getCity.add(ab);
                            id_citys = daoUtil.queryById(ab.getPid());
                            id_provices = daoUtil.queryById(id_citys.getPid());
                        }else if(ab.getLevel().equals("2")){
                            //增加城市
                            selCityList.add(ab);
                            id_citys = ab;
                            id_provices = daoUtil.queryById(ab.getPid());
                        }else if(ab.getLevel().equals("1")){
                            id_provices =ab;
                        }
                    }
                }
            } else {
                //getCity.add(daoUtil.queryById(id_all));
                AreaBean ab = daoUtil.queryById(id_all);
                if(null!=ab){
                    count++;
                    if(ab.getLevel().equals("3")){
                        getCity.add(ab);
                        id_citys = daoUtil.queryById(ab.getPid());
                        id_provices = daoUtil.queryById(id_citys.getPid());
                    }else if(ab.getLevel().equals("2")){
                        //增加城市
                        selCityList.add(ab);
                        id_citys = ab;
                        id_provices = daoUtil.queryById(ab.getPid());
                    }else if(ab.getLevel().equals("1")){
                        id_provices =ab;
                    }
                }
            }
        }
        //省选择
        LinearLayoutManager ms = new LinearLayoutManager(this);
        //设置布局管理器水平布局
        ms.setOrientation(LinearLayoutManager.VERTICAL);
        sel_result.setLayoutManager(ms);
        adapter = new HCAreaSelShowAdapter();
        adapter.setOnclickListener(new HCAreaSelShowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                areasId = selCitys.get(position).getAreaId();
                selCityss = daoUtil.queryByPid(areasId+"");
                List<AreaBean> provices = daoUtil.queryByLevel("1");
                provices.get(position).setIfmy(true);
                adapter.replace(provices);
                adapters.replace(selCityss);
                id_provice = selCitys.get(position).getAreaId() + "";
                name_provice =selCitys.get(position).getName();
                count = 0;
                area_num.setText("最多选择3个区域("+count+"/3)");
            }
        });
        sel_result.setAdapter(adapter);
        List<AreaBean> provices = daoUtil.queryByLevel("1");
        if(null!=id_provices)
        for(AreaBean ab : provices){
            if(ab.getAreaId()==id_provices.getAreaId()){
                ab.setIfmy(true);
            }
        }
        selCitys=provices;
        adapter.replace(selCitys);

        //市选择
        LinearLayoutManager mss = new LinearLayoutManager(this);
        //设置布局管理器水平布局
        mss.setOrientation(LinearLayoutManager.VERTICAL);
        sel_results.setLayoutManager(mss);
        adapters = new HCAreaSelShowAdapter();
        adapters.setOnclickListener(new HCAreaSelShowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(areasId==-1){
                    areasId=2;
                }

                //区数据
//                List<AreaBean> provices = daoUtil.queryByPid(areasId+"");
//                provices.get(position).setIfmy(true);
//
//                selCitysss = daoUtil.queryByPid(selCityss.get(position).getAreaId()+"");
//                adapterss.replace(selCitysss);

                id_city = selCityss.get(position).getAreaId() + "";
                name_city =selCityss.get(position).getName();
//                count=0;

                if(selCityss.get(position).isIfmy()){
                    selCityss.get(position).setIfmy(false);
                    count-=1;
                    area_num.setText("最多选择3个区域("+count+"/3)");
                }else{
                    if (count >= 3) {
                        selCityss.get(position).setIfmy(false);
                        ToastUtils.showShort(HCAreaSelActivity.this, "最多可以选择3个地区");
                    }else{
                        selCityss.get(position).setIfmy(true);
                        count+=1;
                        area_num.setText("最多选择3个区域("+count+"/3)");
                    }
                }

                adapters.replace(selCityss);
            }
        });
        sel_results.setAdapter(adapters);
        List<AreaBean> provicess=null;
        if(null!=id_citys){
            areasId = Integer.parseInt(id_citys.getPid());
            provicess = daoUtil.queryByPid(id_citys.getPid());
            for(AreaBean ab : provicess){
                for (AreaBean ac : selCityList) {
                    if (ab.getAreaId() == ac.getAreaId()) {
                        ab.setIfmy(true);
                    }
                }
            }
        }else if(null!=id_provices){
            areasId = id_provices.getAreaId();
            provicess = daoUtil.queryByPid(areasId+"");
        }else{
            provicess = daoUtil.queryByPid("2");
        }

        selCityss=provicess;
        adapters.replace(selCityss);

//        //区选择
//        LinearLayoutManager msss = new LinearLayoutManager(this);
//        //设置布局管理器水平布局
//        msss.setOrientation(LinearLayoutManager.VERTICAL);
//        sel_resultss.setLayoutManager(msss);
//        adapterss = new HCAreaSelShowAdapter();
//        adapterss.setOnclickListener(new HCAreaSelShowAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                if(selCitysss.get(position).isIfmy()){
//                    selCitysss.get(position).setIfmy(false);
//                    count-=1;
//                    if(isMroeSel){
//                        area_num.setText("最多选择3个区域("+count+"/3)");
//                    }else{
//                        area_num.setText("最多选择3个区域("+count+"/1)");
//                    }
//                }else {
//                    if (isMroeSel) {
//                        if (count >= 3) {
//                            ToastUtils.showShort(HCAreaSelActivity.this, "最多可以选择3个地区");
//                            selCitysss.get(position).setIfmy(false);
//                        }else{
//                            selCitysss.get(position).setIfmy(true);
//                            count+=1;
//                            area_num.setText("最多选择3个区域("+count+"/3)");
//                        }
//                    } else {
//                        if (count >= 1) {
//                            ToastUtils.showShort(HCAreaSelActivity.this, "最多可以选择1个地区");
//                            selCitysss.get(position).setIfmy(false);
//                        }else{
//                            selCitysss.get(position).setIfmy(true);
//                            count+=1;
//                            area_num.setText("最多选择3个区域("+count+"/1)");
//                        }
//                    }
//                }
//                adapterss.replace(selCitysss);
//            }
//        });
//        sel_resultss.setAdapter(adapterss);
//        List<AreaBean> provicesss=null;
//        if(null!=id_citys){
//            provicesss = daoUtil.queryByPid(id_citys.getAreaId()+"");
//            for(AreaBean ab : provicesss){
//                for (AreaBean ac : getCity){
//                    if(ab.getAreaId()==ac.getAreaId()){
//                        ab.setIfmy(true);
//                        count+=1;
//                        if (isMroeSel) {
//                            area_num.setText("最多选择3个区域("+count+"/3)");
//                        }else{
//                            area_num.setText("最多选择3个区域("+count+"/1)");
//                        }
//                    }
//                }
//            }
//        }else {
//            provicesss = daoUtil.queryByPid("52");
//        }
//        selCitysss=provicesss;
//        adapterss.replace(selCitysss);

        area_num.setText("最多选择3个区域("+count+"/3)");
        btn_confirm.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_area_sel_hc;
        // return R.layout.activity_area_sel;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
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
                if (null != selCityss && selCityss.size() > 0) {
                    String names = "";
                    String ids = "";
                    for (AreaBean bean : selCityss) {
                        if(bean.isIfmy()){
                            names += (bean.getName() + ",");
                            ids += (bean.getAreaId() + ",");
                        }
                    }
                    if (null==names||names.equals("")){

                        ToastUtils.showShort(this, "未选择任何城市");
                        return;

//                        if(null!=name_city)
//                        names += name_city+",";
//                        if(null!=id_city)
//                        ids += id_city+",";
                    }
                    if (null==names||names.equals("")||names.equals(",")){
                        names += name_provice+",";
                        ids += id_provice+",";
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
                    String names = "";
                    String ids = "";
                    if (names.equals("")){
                        names += name_city+",";
                        ids += id_city+",";
                    }
                    if (null==names||names.equals("")||names.equals(",")){
                        if(null!=name_city)
                            names += name_city+",";
                        if(null!=id_city)
                            ids += id_city+",";
                    }
                    if(null==names||names.equals("")||names.equals(",")||names.equals(",,")){
                        ToastUtils.showShort(this, "未选择任何地区");
                        return;
                    }
                    names = names.substring(0, names.length() - 1);
                    ids = ids.substring(0, ids.length() - 1);

                    Intent mIntent = new Intent();
                    mIntent.putExtra("city", names);
                    mIntent.putExtra("cityIds", ids);
                    // 设置结果，并进行传送
                    setResult(100, mIntent);
                    finish();
                }
                break;
        }
    }
}

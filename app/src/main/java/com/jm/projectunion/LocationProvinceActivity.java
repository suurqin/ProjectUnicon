package com.jm.projectunion;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.common.widget.sortlistview.CharacterParser;
import com.jm.projectunion.common.widget.sortlistview.PinyinComparator;
import com.jm.projectunion.common.widget.sortlistview.SideBar;
import com.jm.projectunion.common.widget.sortlistview.SortAdapter;
import com.jm.projectunion.common.widget.sortlistview.SortModel;
import com.jm.projectunion.dao.AreaDaoUtil;
import com.jm.projectunion.dao.bean.AreaBean;
import com.jm.projectunion.location.LocationService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Young on 2017/10/27.
 */

public class LocationProvinceActivity extends BaseTitleActivity {

    @BindView(R.id.cur_location)
    TextView cur_location;
    @BindView(R.id.filter)
    EditText filter;
    @BindView(R.id.use_cur_location)
    TextView use_cur_location;
    @BindView(R.id.country_lvcountry)
    ListView country_lvcountry;
    @BindView(R.id.sidrbar)
    SideBar sideBar;
    @BindView(R.id.dialog)
    TextView dialog;

    private LocationService locationService;
    private PrefUtils prefUtils;
    private SortAdapter adapter;
    public String level="1";//级别

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> sourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    private AreaDaoUtil daoUtil;
    private String currentCity;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            country_lvcountry.setSelection(msg.what);
        }
    };

    @Override
    public void initView() {
        setTitleText("定位");
        daoUtil = new AreaDaoUtil(this);
        prefUtils = PrefUtils.getInstance(this);
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        showDialogLoading();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<AreaBean> citys = daoUtil.queryAll();
                List<String> str_citys = new ArrayList<>();
                for (AreaBean bean : citys) {
                    if(null!=level &&! "".equals(level) &&level.equals(bean.getLevel())){
                        str_citys.add(bean.getName());
                    }else if(null==level || "".equals(level)){
                        str_citys.add(bean.getName());
                    }

                }
                sourceDateList = filledData(str_citys);
                // 根据a-z进行排序源数据
                Collections.sort(sourceDateList, pinyinComparator);
                adapter = new SortAdapter(LocationProvinceActivity.this, sourceDateList);
                country_lvcountry.setAdapter(adapter);
                country_lvcountry.setSelected(true);
                hideDialogLoading();
            }
        }, 500);

        sideBar.setTextView(dialog);
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    handler.sendEmptyMessage(position);
                }
            }
        });

        //根据输入框输入值的改变来过滤搜索
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterData(s.toString());
            }
        });

        country_lvcountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent();
                mIntent.putExtra("city", ((SortModel) adapter.getItem(position)).getName());
                // 设置结果，并进行传送
                setResult(100, mIntent);
                finish();
            }
        });
        currentCity = prefUtils.getKeyLocationProvince();
        if (!TextUtils.isEmpty(currentCity)) {
            cur_location.setText("当前定位：" + currentCity);
        } else {
            cur_location.setText("定位失败，重新定位");
            cur_location.setOnClickListener(this);
        }
        use_cur_location.setOnClickListener(this);

    }

    @Override
    public void initData() {
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_location;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.cur_location:
                // -----------location config ------------
                locationService = ((MyApplication) getApplication()).locationService;
                //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
                locationService.registerListener(mListener);
                //注册监听
                locationService.setLocationOption(locationService.getDefaultLocationClientOption());
                locationService.start();
                break;
            case R.id.use_cur_location:
                if (!TextUtils.isEmpty(currentCity)) {
                    prefUtils.setKeyLocationCity(currentCity);
                    Intent mIntent = new Intent();
                    mIntent.putExtra("city", currentCity);
                    // 设置结果，并进行传送
                    setResult(100, mIntent);
                    finish();
                } else {
                    ToastUtils.showShort(this, "定位失败,重新定位");
                }
                break;
        }
    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(List<String> date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date.get(i));
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date.get(i));
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = sourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : sourceDateList) {
                String name = sortModel.getName();
                if (name.toUpperCase().indexOf(
                        filterStr.toString().toUpperCase()) != -1
                        || characterParser.getSelling(name).toUpperCase()
                        .startsWith(filterStr.toString().toUpperCase())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                currentCity = location.getProvince();
                if (!TextUtils.isEmpty(currentCity)) {
                    cur_location.setText("当前定位：" + currentCity);
                } else {
                    cur_location.setText("定位失败，重新定位");
                }
                locationService.stop();
            }
        }

        public void onConnectHotSpotMessage(String s, int i) {
        }
    };

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

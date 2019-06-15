package com.jm.projectunion.home.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.jm.projectunion.LocationActivity;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.common.utils.FileUtils;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.StringUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.dao.AreaDaoUtil;
import com.jm.projectunion.dao.bean.AreaBean;
import com.jm.projectunion.entity.Result;
import com.jm.projectunion.home.entiy.ProjectDetailResult;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;
import com.jm.projectunion.wiget.popupwindow.BottomListPopupWindow;
import com.jm.projectunion.wiget.popupwindow.OnItemClickListener;
import com.jm.projectunion.wiget.popupwindow.PullPopupWindow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Young on 2017/12/13.
 */

public class ReleaseProjectActivity extends BaseTitleActivity {

    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.category)
    TextView category;
    @BindView(R.id.level_s)
    TextView level_s;
    @BindView(R.id.money)
    EditText money;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.people)
    TextView people;
    @BindView(R.id.build_area)
    EditText build_area;
    @BindView(R.id.start_date)
    TextView start_date;
    @BindView(R.id.end_date)
    TextView end_date;
    @BindView(R.id.work_category)
    TextView work_category;
    @BindView(R.id.intro)
    EditText intro;
    @BindView(R.id.release)
    Button release;

    private PrefUtils prefUtils;
    private AreaDaoUtil daoUtil;
    private List<String> projectType = new ArrayList<>();//建设状态
    private List<String> type = new ArrayList<>();//业主类型
    private List<String> ptype = new ArrayList<>();//发包方式
    private List<String> categorys = new ArrayList<>();//项目分类
    private String categoryId;
    private String peojectId;
    private String typeId;
    private String ptypeId;
    private String cityId;
    private int mYear, mMonth, mDay;//日期

    private Calendar cal;
    private BottomListPopupWindow pullPopupWindow_projectCategory;
    private BottomListPopupWindow pullPopupWindow_projectType;
    private BottomListPopupWindow pullPopupWindow_type;
    private BottomListPopupWindow pullPopupWindow_ptype;

    @Override
    public void initView() {
        setData();
        setTitleText("信息发布");
        prefUtils = PrefUtils.getInstance(this);
        daoUtil = new AreaDaoUtil(this);
        category.setOnClickListener(this);
        level_s.setOnClickListener(this);
        people.setOnClickListener(this);
        address.setOnClickListener(this);
        start_date.setOnClickListener(this);
        end_date.setOnClickListener(this);
        work_category.setOnClickListener(this);
        release.setOnClickListener(this);

        pullPopupWindow_projectCategory = new BottomListPopupWindow(this, categorys);
        pullPopupWindow_projectCategory.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                showToast("分类" + projectType.get(position));
                categoryId = String.valueOf(8900 + position);
                category.setText(categorys.get(position));
            }
        });

        pullPopupWindow_projectType = new BottomListPopupWindow(this, projectType);
        pullPopupWindow_projectType.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                showToast("分类" + projectType.get(position));
                peojectId = position + "";
                level_s.setText(projectType.get(position));
            }
        });

        pullPopupWindow_type = new BottomListPopupWindow(this, type);
        pullPopupWindow_type.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                showToast("分类" + type.get(position));
                people.setText(type.get(position));
                typeId = position + 1 + "";
            }
        });

        pullPopupWindow_ptype = new BottomListPopupWindow(this, ptype);
        pullPopupWindow_ptype.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                showToast("分类" + ptype.get(position));
                work_category.setText(ptype.get(position));
                ptypeId = position + 1 + "";
            }
        });
    }

    @Override
    public void initData() {
        cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);       //获取年月日时分秒
        mMonth = cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        mDay = cal.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_release_project;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.category:
                pullPopupWindow_projectCategory.showAtBottom(findViewById(R.id.main));
                break;
            case R.id.level_s:
                pullPopupWindow_projectType.showAtBottom(findViewById(R.id.main));
                break;
            case R.id.people:
                pullPopupWindow_type.showAtBottom(findViewById(R.id.main));
                break;
            case R.id.address:
                Intent intent = new Intent(this, LocationActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.start_date:
                showDataDialog(start_date);
                break;
            case R.id.end_date:
                showDataDialog(end_date);
                break;
            case R.id.work_category:
                pullPopupWindow_ptype.showAtBottom(findViewById(R.id.main));
                break;
            case R.id.release:
                release();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (100 == resultCode && null != data) {
            String city = data.getStringExtra("city");
            address.setText(city);
            AreaBean bean = daoUtil.queryByName(city);
            if (null != bean) {
                cityId = String.valueOf(bean.getAreaId());
            }
        }
    }

    private void setData() {
        categorys = FileUtils.readFileFromAssets(this, "category_project.txt");
        projectType = FileUtils.readFileFromAssets(this, "project_type.txt");
        type = FileUtils.readFileFromAssets(this, "project_owner.txt");
        ptype = FileUtils.readFileFromAssets(this, "project_job.txt");
    }

    /**
     * 显示日期控件
     *
     * @param view
     */
    private void showDataDialog(final TextView view) {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int year, int month, int day) {
                if (year >= mYear && month >= mMonth && day >= mDay) {
                    view.setText(year + "-" + (++month) + "-" + day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                } else {
                    ToastUtils.showShort(ReleaseProjectActivity.this, "日期选择错误");
                }
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_LIGHT, listener, mYear, mMonth, mDay);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
        dialog.show();
    }

    /**
     * 发布
     */
    private void release() {
        String str_title = title.getText().toString().trim();
        String str_name = name.getText().toString().trim();
        String str_phone = phone.getText().toString().trim();
        String str_money = money.getText().toString().trim();
        String str_address = address.getText().toString().trim();
        String str_build_area = build_area.getText().toString().trim();
        String str_sData = start_date.getText().toString().trim();
        String str_eData = end_date.getText().toString().trim();
        String str_intro = intro.getText().toString().trim();

        ProjectDetailResult.ProjectBean bean = new ProjectDetailResult.ProjectBean();
        bean.setUserId(prefUtils.getUserInfo().getUserId());
        bean.setLatitude(String.valueOf(prefUtils.getKeyLocationLat()));
        bean.setLongitude(String.valueOf(prefUtils.getKeyLocationLong()));
        if (TextUtils.isEmpty(str_title)) {
            showToast("填写标题");
            return;
        }
        bean.setProjectName(str_title);
        if (TextUtils.isEmpty(str_name)) {
            showToast("姓名");
            return;
        }
        bean.setLinkman(str_name);
        if (!StringUtils.isMobile(str_phone)) {
            showToast("电话格式错误");
            return;
        }
        bean.setLinkphone(str_phone);
        if (TextUtils.isEmpty(peojectId)) {
            showToast("类型");
            return;
        }
        bean.setProjectType(peojectId);
        if (TextUtils.isEmpty(str_money)) {
            showToast("造价");
            return;
        }
        bean.setPrice(str_money);
        if (TextUtils.isEmpty(str_address)) {
            showToast("地址");
            return;
        }
        bean.setAddr(str_address);
        if (TextUtils.isEmpty(cityId)) {
            showToast("地区");
            return;
        }
        bean.setAreaIds(cityId);
        if (TextUtils.isEmpty(typeId)) {
            showToast("业主");
            return;
        }
        bean.setType(typeId);
        if (TextUtils.isEmpty(str_build_area)) {
            showToast("面积");
            return;
        }
        bean.setArea(str_build_area);

        if (TextUtils.isEmpty(str_sData)) {
            showToast("开工日期");
            return;
        }
        bean.setSdate(str_sData);

        if (TextUtils.isEmpty(str_eData)) {
            showToast("竣工日期");
            return;
        }
        bean.setEdate(str_eData);

        if (TextUtils.isEmpty(ptypeId)) {
            showToast("发包类型");
            return;
        }
        bean.setPtype(ptypeId);
        if (TextUtils.isEmpty(str_intro)) {
            showToast("简介");
            return;
        }
        bean.setDesp(str_intro);

        if (TextUtils.isEmpty(categoryId)) {
            showToast("项目分类");
            return;
        }
        bean.setCategoryId(categoryId);

        showDialogLoading();
        ApiClient.releseProjectInfo(this, bean, new ResultCallback<Result>() {
            @Override
            public void onSuccess(Result response) {
                hideDialogLoading();
                if (null != response) {
                    showToast(response.getMsg());
                    if ("0".equals(response.getCode())) {
                        finish();
                    }
                }
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                showToast(msg);
            }
        });
    }

    private void showToast(String msg) {
        ToastUtils.showShort(this, msg);
    }
}

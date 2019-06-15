package com.jm.projectunion.home.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.common.utils.DateUtils;
import com.jm.projectunion.common.utils.FileUtils;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.home.entiy.InfoDetailsResult;
import com.jm.projectunion.home.entiy.ProjectDetailResult;
import com.jm.projectunion.mine.entity.CollectResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by bobo on 2017/12/19.
 */

public class ProjectDetailActivity extends BaseTitleActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.collect)
    TextView collect;
    @BindView(R.id.tel)
    TextView tel;
    @BindView(R.id.im)
    TextView im;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.category)
    TextView category;
    @BindView(R.id.projecttype)
    TextView projecttype;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.build_area)
    TextView build_area;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.ptype)
    TextView ptype;
    @BindView(R.id.desp)
    TextView desp;

    private String id;
    private List<String> categorys = new ArrayList<>();//项目分类
    private List<String> projectType = new ArrayList<>();//建设状态
    private List<String> types = new ArrayList<>();//业主类型
    private List<String> ptypes = new ArrayList<>();//发包方式

    @Override
    public void initView() {
        setTitleText("项目详情");
        id = getIntent().getBundleExtra(UiGoto.BUNDLE).getString("id");
        collect.setOnClickListener(this);
        tel.setOnClickListener(this);
        im.setOnClickListener(this);
        getData(id);
    }

    @Override
    public void initData() {
        categorys = FileUtils.readFileFromAssets(this, "category_project.txt");
        projectType = FileUtils.readFileFromAssets(this, "project_type.txt");
        types = FileUtils.readFileFromAssets(this, "project_owner.txt");
        ptypes = FileUtils.readFileFromAssets(this, "project_job.txt");
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_project_detail;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.collect:
                CollectResult.CollectBean bean = new CollectResult.CollectBean();
                bean.setUserId(PrefUtils.getInstance(this).getUserInfo().getUserId());
                bean.setName(title.getText().toString());
                bean.setContent(id);
                bean.setType("89");
                bean.setCtime(DateUtils.getStringByFormat(System.currentTimeMillis(), DateUtils.YYYY_MM_DD_HH_MM_SS));
                collect(bean);
                break;
            case R.id.tel:
                Intent intent1 = new Intent(Intent.ACTION_DIAL);
                intent1.setData(Uri.parse("tel:" + phone.getText().toString().trim()));
                startActivity(intent1);
                break;
            case R.id.im:
                break;
        }
    }

    private void getData(String id) {
        showDialogLoading();
        ApiClient.getProjectDetail(this, id, new ResultCallback<ProjectDetailResult>() {
            @Override
            public void onSuccess(ProjectDetailResult response) {
                hideDialogLoading();
                System.out.println("result-projectDt==" + response.toString());
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        setDataInView(response.getData());
                    }
                    ToastUtils.showShort(ProjectDetailActivity.this, response.getMsg());
                }
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                ToastUtils.showShort(ProjectDetailActivity.this, msg);
            }
        });
    }

    /**
     * 收藏信息
     *
     * @param bean
     */
    private void collect(CollectResult.CollectBean bean) {
        showDialogLoading();
        ApiClient.collectInfo(this, bean, new ResultCallback<InfoDetailsResult>() {
            @Override
            public void onSuccess(InfoDetailsResult response) {
                hideDialogLoading();
                if (null != response) {
                    ToastUtils.showShort(ProjectDetailActivity.this, response.getMsg());
                }
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                ToastUtils.showShort(ProjectDetailActivity.this, msg);
            }
        });
    }

    private void setDataInView(ProjectDetailResult.ProjectBean bean) {
        if (null != bean) {
            title.setText(bean.getProjectName());
            name.setText(bean.getLinkman());
            phone.setText(bean.getLinkphone());
            address.setText(bean.getAddr());

            String categoryIds = bean.getCategoryId();
            String categoryName = "";
            if (categoryIds.contains(",")) {
                String[] ids = categoryIds.split(",");
                for (String id : ids) {
                    int i_id = Integer.valueOf(id) % 8900;
                    categoryName += categorys.get(i_id) + "/";
                }
            } else {
                categoryName = categorys.get(Integer.valueOf(categoryIds) % 8900);
            }
            category.setText("分类：" + categoryName);

            int projectTypeId = Integer.valueOf(bean.getProjectType());
            if (projectTypeId >= 0) {
                projecttype.setText("项目类型：" + projectType.get(projectTypeId));//待调整
            }
            int typeId = Integer.valueOf(bean.getType()) - 1;
            if (typeId >= 0) {
                type.setText("业主类型：" + types.get(typeId));//待调整
            }
            int pTypeId = Integer.valueOf(bean.getPtype()) - 1;
            if (pTypeId >= 0) {
                ptype.setText("发包方式：" + ptypes.get(pTypeId));//待调整
            }

            build_area.setText("建筑面积：" + bean.getArea());
            date.setText(bean.getSdate() + "至" + bean.getEdate());
            money.setText("项目造价：" + bean.getPrice());
            desp.setText(bean.getDesp());
        }
    }
}

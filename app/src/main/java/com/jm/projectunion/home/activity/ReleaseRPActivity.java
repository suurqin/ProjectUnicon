package com.jm.projectunion.home.activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.common.manager.Config;
import com.jm.projectunion.common.utils.AccurateArithUtils;
import com.jm.projectunion.common.utils.FileUtils;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.StringUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.dao.AreaDaoUtil;
import com.jm.projectunion.dao.bean.AreaBean;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.home.entiy.RedPacketResult;
import com.jm.projectunion.information.adapter.PicAdapter;
import com.jm.projectunion.mine.activity.RechargeActivity;
import com.jm.projectunion.mine.entity.UserInfoResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;
import com.jm.projectunion.wiget.popupwindow.BottomCancelPopupWindow;
import com.jm.projectunion.wiget.popupwindow.OnItemClickListener;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.yanzhenjie.album.Album;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

/**
 * Created by YangPan on 2017/11/10.
 * <p>
 * 发布红包
 */

public class ReleaseRPActivity extends BaseTitleActivity {

    private static final String LIST = "list";
    private static final int LIST_PHOTO_REQUEST = 11; //相册_列表
    private static final int LIST_CAMERA_REQUEST = 22;//相机_列表

    @BindView(R.id.city_type)
    RadioGroup city_type;
    @BindView(R.id.redpacket_sex)
    RadioGroup redpacket_sex;
    @BindView(R.id.redpacket_age)
    RadioGroup redpacket_age;
    @BindView(R.id.age_min)
    EditText age_min;
    @BindView(R.id.age_max)
    EditText age_max;
    @BindView(R.id.redpacket_single)
    EditText redpacket_single;
    @BindView(R.id.redpacket_num)
    EditText redpacket_num;
    @BindView(R.id.money_all)
    TextView money_all;
    @BindView(R.id.money_need)
    TextView money_need;
    @BindView(R.id.recharge)
    TextView recharge;
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.intro1)
    EditText intro1;
    @BindView(R.id.intro2)
    EditText intro2;
    @BindView(R.id.intro3)
    EditText intro3;
    @BindView(R.id.intro4)
    EditText intro4;
    @BindView(R.id.pic)
    GridView pic;
    @BindView(R.id.release)
    Button release;

    private PicAdapter picAdapter;
    private long pic_name;
    private int digits = 2;
    private String typeCity = "1";
    private String typeSex = "0";
    private String typeAge = "0";
    private List<String> photos = new ArrayList<>();
    private List<String> upload_pics = new ArrayList<>();
    private Iterator<String> iPaths;
    private AreaDaoUtil daoUtil;
    private PrefUtils prefUtils;

    @Override
    public void initView() {
        setTitleText("信息发布");

        daoUtil = new AreaDaoUtil(this);
        prefUtils = PrefUtils.getInstance(this);

        picAdapter = new PicAdapter(this, photos, 4);
        picAdapter.setOnPicListener(new PicAdapter.onPicListener() {
            @Override
            public void onAddPic() {
                showPicSel(LIST);
            }

            @Override
            public void onDelPic(int position) {
                photos.remove(position);
                picAdapter.setData(photos);
            }
        });
        pic.setAdapter(picAdapter);

        city_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.city:
                        typeCity = "1";
                        break;
                    case R.id.country:
                        typeCity = "2";
                        break;
                }
            }
        });
        redpacket_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.sex_no:
                        typeSex = "0";
                        break;
                    case R.id.sex_male:
                        typeSex = "1";
                        break;
                    case R.id.sex_female:
                        typeSex = "2";
                        break;
                }
            }
        });

        redpacket_age.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.age_no:
                        typeAge = "0";
                        break;
                    case R.id.age_detail:
                        typeAge = "1";
                        break;
                }
            }
        });

        redpacket_single.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //删除“.”后面超过2位后的数据
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > digits) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + digits + 1);
                        redpacket_single.setText(s);
                        redpacket_single.setSelection(s.length()); //光标移到最后
                    }
                }
                //如果"."在起始位置,则起始位置自动补0
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    redpacket_single.setText(s);
                    redpacket_single.setSelection(2);
                }

                //如果起始位置为0,且第二位跟的不是".",则无法后续输入
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        redpacket_single.setText(s.subSequence(0, 1));
                        redpacket_single.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        redpacket_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(redpacket_single.getText().toString().trim())) {
                    if (!TextUtils.isEmpty(redpacket_num.getText().toString().trim()) && Integer.valueOf(redpacket_num.getText().toString().trim()) > 0) {
                        int sum = AccurateArithUtils.mult(redpacket_single.getText().toString().trim(), redpacket_num.getText().toString().trim());
                        money_need.setText(sum + "");
                    } else {
                        money_need.setText(redpacket_single.getText().toString().trim());
                    }
                }
            }
        });

        recharge.setOnClickListener(this);
        release.setOnClickListener(this);
    }

    @Override
    public void initData() {
        getToken();
        getData();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_release_redp;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LIST_CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功
                        File photoFile = new File(FileUtils.getImagePath(), pic_name + ".jpg");
                        if (photoFile.exists()) {
                            uploadImageToQiniu(photoFile.getAbsolutePath());
//                            photos.add(photoFile.getAbsolutePath());
//                            picAdapter.setData(photos);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case LIST_PHOTO_REQUEST:
                if (data != null) {
                    List<String> pathList = Album.parseResult(data);
                    if (null == pathList) {
                        return;
                    }
                    iPaths = pathList.iterator();
//                         uploadImageToQiniu(StringUtils.getPath(this, data));
                    uploadImageToQiniu(iPaths.next());
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.recharge:
                UiGoto.startAty(this, RechargeActivity.class);
                break;
            case R.id.release:
                release();
                break;
        }
    }

    /**
     * 图片来源选择框
     */
    private void showPicSel(final String type) {
        final List<String> picSrcList = new ArrayList<>();
        picSrcList.add("拍照");
        picSrcList.add("从手机相册选择");
        final BottomCancelPopupWindow bottomPopupWindow = new BottomCancelPopupWindow(this, picSrcList);
        bottomPopupWindow.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        getPicFromCamera(LIST_CAMERA_REQUEST);
                        break;
                    case 1:
                        getPicFromPhoto(LIST_PHOTO_REQUEST);
                        break;
                }
            }
        });
        bottomPopupWindow.showAtBottom(findViewById(R.id.main));
    }

    /**
     * 打开相册
     */
    private void getPicFromPhoto(int type) {
//        Intent intent = new Intent(Intent.ACTION_PICK, null);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                "image/*");
//        startActivityForResult(intent, type);
        int limitCount = 1;
        if (LIST_PHOTO_REQUEST == type) {
            limitCount = 4 - upload_pics.size();
        }
        Album.startAlbum(this, type
                , limitCount                                                         // 指定选择数量。
                , ContextCompat.getColor(this, R.color.colorPrimary)        // 指定Toolbar的颜色。
                , ContextCompat.getColor(this, R.color.colorPrimaryDark));  // 指定状态栏的颜色。
    }

    /**
     * 打开相机
     */
    private void getPicFromCamera(int type) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        pic_name = System.currentTimeMillis();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(FileUtils.getImagePath(), pic_name + ".jpg")));
        startActivityForResult(intent, type);
    }

    private String token_qn;

    /**
     * 获取七牛凭证
     */
    private void getToken() {
        ApiClient.getQNToke(this, new ResultCallback<ResultData>() {
            @Override
            public void onSuccess(ResultData response) {
                System.out.println("result-token=" + response.toString());
                if ("0".equals(response.getCode())) {
                    token_qn = response.getData();
                }
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 上传图片到七牛
     *
     * @param filePath 要上传的图片路径
     */
    private void uploadImageToQiniu(final String filePath) {
        showDialogLoading("图片上传中......");
        UploadManager uploadManager = new UploadManager();
        // 设置图片名字
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String key = "icon_" + sdf.format(new Date());
        uploadManager.put(filePath, key, token_qn, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                // info.error中包含了错误信息，可打印调试
                // 上传成功后将key值上传到自己的服务器
                hideDialogLoading();
                if (info.isOK()) {
                    String str_key = Config.IMG_HOST + key;
                    showToast("上传成功");
                    upload_pics.add(str_key);
                    photos.add(filePath);
                    picAdapter.setData(photos);
                    if (iPaths.hasNext()) {
                        uploadImageToQiniu(iPaths.next());
                    }
                } else {
                    showToast("上传失败");
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }
                System.out.println("result-qiniu" + key + "," + info + "," + res);
            }
        }, null);
    }

    /**
     * 发布信息
     */
    private void release() {
        String str_minAge = age_min.getText().toString().trim();
        String str_maxAge = age_max.getText().toString().trim();
        String str_single = redpacket_single.getText().toString().trim();
        String str_num = redpacket_num.getText().toString().trim();
        String str_title = title.getText().toString().trim();
        String str_phone = phone.getText().toString().trim();
        String str_address = address.getText().toString().trim();

        RedPacketResult.RedPacketBean dto = new RedPacketResult.RedPacketBean();
        dto.setUserId(prefUtils.getUserInfo().getUserId());
        if ("1".equals(typeCity)) {
            AreaBean areaBean = daoUtil.queryByName(prefUtils.getKeyLocationCity());
            if (null != areaBean) {
                dto.setCityId(String.valueOf(areaBean.getAreaId()));
            }
        } else {
            dto.setCityId("0");
        }
        dto.setType(typeCity);
        dto.setSex(typeSex);
        if ("1".equals(typeAge)) {
            if (TextUtils.isEmpty(str_minAge) || TextUtils.isEmpty(str_maxAge)) {
                showToast("请正确填写年龄");
                return;
            } else {
                int i_minAge = Integer.valueOf(str_minAge);
                int i_maxAge = Integer.valueOf(str_maxAge);
                if (i_minAge >= i_maxAge) {
                    showToast("请正确填写年龄");
                    return;
                }
                if (i_maxAge > 100) {
                    showToast("最大年龄100");
                    return;
                }
            }
            typeAge = str_minAge + "-" + str_maxAge;
        }
        dto.setAge(typeAge);

        if (TextUtils.isEmpty(str_single) || Double.valueOf(str_single) < 0.1) {
            showToast("金额填写格式错误");
            return;
        }
        dto.setPrice(str_single);
        if (TextUtils.isEmpty(str_num) || Integer.valueOf(str_num) % 100 != 0) {
            showToast("红包数目格式错误");
            return;
        }
        dto.setNum(str_num);
        if (TextUtils.isEmpty(str_title)) {
            showToast("填写标题");
            return;
        }
        if (str_title.length() > 6) {
            showToast("标题限制6个字");
            return;
        }
        dto.setTitle(str_title);
        if (!StringUtils.isMobile(str_phone)) {
            showToast("填写正确的手机格式");
            return;
        }
        dto.setPhone(str_phone);
        if (TextUtils.isEmpty(str_address)) {
            showToast("填写地址");
            return;
        }
        dto.setAddr(str_address);
        if (upload_pics.size() < 4) {
            showToast("请添加4张图片");
            return;
        }
        dto.setImg1(upload_pics.get(0));
        dto.setImg2(upload_pics.get(1));
        dto.setImg3(upload_pics.get(2));
        dto.setImg4(upload_pics.get(3));

        String str_intro1 = intro1.getText().toString();
        String str_intro2 = intro2.getText().toString();
        String str_intro3 = intro3.getText().toString();
        String str_intro4 = intro4.getText().toString();
        if (!TextUtils.isEmpty(str_intro1) && str_intro1.length() > 15) {
            showToast("描述一不能超过15个字");
            return;
        }
        dto.setImg1Desc(str_intro1);
        if (!TextUtils.isEmpty(str_intro2) && str_intro2.length() > 15) {
            showToast("描述二不能超过15个字");
            return;
        }
        dto.setImg2Desc(str_intro2);
        if (!TextUtils.isEmpty(str_intro3) && str_intro3.length() > 15) {
            showToast("描述三不能超过15个字");
            return;
        }
        dto.setImg3Desc(str_intro3);
        if (!TextUtils.isEmpty(str_intro4) && str_intro4.length() > 15) {
            showToast("描述四不能超过15个字");
            return;
        }
        dto.setImg4Desc(str_intro4);

        showDialogLoading();
        ApiClient.releaseRP(this, dto, new ResultCallback<ResultData>() {
            @Override
            public void onSuccess(ResultData response) {
                hideDialogLoading();
                System.out.println("result-rpRel==" + response.toString());
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

    /**
     * 获取信息
     */
    private void getData() {
        ApiClient.getUserInfo(this, new ResultCallback<UserInfoResult>() {
            @Override
            public void onSuccess(UserInfoResult response) {
                System.out.println("result-user=" + response.toString());
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        UserInfoResult.UserInfoBean bean = response.getData();
                        if (null != bean) {
                            if (TextUtils.isEmpty(bean.getAccount())) {
                                money_all.setText("0元");
                            } else {
                                money_all.setText(bean.getAccount() + "元");
                            }
                        }
                    } else {
                        ToastUtils.showShort(ReleaseRPActivity.this, response.getMsg());
                    }
                }
            }

            @Override
            public void onError(String msg) {
                ToastUtils.showShort(ReleaseRPActivity.this, msg);
            }
        });
    }

    private void showToast(String msg) {
        ToastUtils.showShort(this, msg);
    }
}

package com.jm.projectunion.information.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jm.projectunion.HCAreaSelActivity;
import com.jm.projectunion.R;
import com.jm.projectunion.WebViewActivity;
import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.common.manager.Config;
import com.jm.projectunion.common.utils.DialogUtils;
import com.jm.projectunion.common.utils.FileUtils;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.information.adapter.PicAdapter;
import com.jm.projectunion.information.dto.EnterpriseInfoDto;
import com.jm.projectunion.information.entity.EnterpriseInfoDetailResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;
import com.jm.projectunion.wiget.DialogConfirmView;
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
 * Created by Young on 2017/10/31.
 */

public class EnterpriseEditActivity extends BaseTitleActivity {

    private static final String NULL_TEXT = "";
    private static final String BUSINESS = "business";
    private static final int BUSINESS_PHOTO_REQUEST = 1; //相册_营业执照
    private static final int BUSINESS_CAMERA_REQUEST = 2;//相机_营业执照
    private static final String BUSINESS_PIC = "business.jpg";//图片名字

    private static final String ID_FRONT = "front";
    private static final int ID_FRONT_PHOTO_REQUEST = 3; //相册_身份证正面
    private static final int ID_FRONT_CAMERA_REQUEST = 4;//相机_身份证正面
    private static final String ID_FRONT_PIC = "idfront.jpg";//图片名字

    private static final String ID_REVE = "reve";
    private static final int ID_REVE_PHOTO_REQUEST = 5; //相册_身份证反面
    private static final int ID_REVE_CAMERA_REQUEST = 6;//相机_身份证反面
    private static final String ID_REVE_PIC = "idreve.jpg";//图片名字

    private static final String LIST = "list";
    private static final int LIST_PHOTO_REQUEST = 11; //相册_列表
    private static final int LIST_CAMERA_REQUEST = 22;//相机_列表

    private String bussiness_name = FileUtils.getImagePath() + File.separator + BUSINESS_PIC;
    private String id_front_name = FileUtils.getImagePath() + File.separator + ID_FRONT_PIC;
    private String id_reve_name = FileUtils.getImagePath() + File.separator + ID_REVE_PIC;

    @BindView(R.id.business_pic)
    ImageView business_pic;
    @BindView(R.id.business_del)
    ImageView business_del;
    @BindView(R.id.id_front_pic)
    ImageView id_front_pic;
    @BindView(R.id.id_front_del)
    ImageView id_front_del;
    @BindView(R.id.id_reverse_pic)
    ImageView id_reverse_pic;
    @BindView(R.id.id_reverse_del)
    ImageView id_reverse_del;
    @BindView(R.id.company_name)
    EditText company_name;
    @BindView(R.id.legal_name)
    EditText legal_name;
    @BindView(R.id.registered_capital)
    EditText registered_capital;
    @BindView(R.id.mobile)
    EditText mobile;
    @BindView(R.id.lanline)
    EditText lanline;
    @BindView(R.id.company_address)
    EditText company_address;
    @BindView(R.id.details_address)
    EditText details_address;
    @BindView(R.id.industry_category)
    TextView industry_category;
    @BindView(R.id.service_scope)
    TextView service_scope;
    @BindView(R.id.pic)
    GridView pic;
    @BindView(R.id.pic_num)
    TextView pic_num;
    @BindView(R.id.release)
    Button release;
    @BindView(R.id.save)
    Button save;

    private PicAdapter picAdapter;
    private Dialog dialog;
    private DialogConfirmView dialogConfirmView;
    private PrefUtils prefUtils;
    private long pic_name;
    private String cityIds;
    private String candos;
    private String orgId;

    private String token_qn;//七牛凭证
    private String bussiness_str = null;
    private String id_front_str = null;
    private String id_reve_str = null;
    private List<String> upload_pics = new ArrayList<>();//上传图片列表之后返回的地址集合
    private Iterator<String> iPaths;

    @Override
    public void initView() {
        setTitleText("完善信息");
        prefUtils = PrefUtils.getInstance(this);
        business_pic.setOnClickListener(this);
        business_del.setOnClickListener(this);
        id_front_pic.setOnClickListener(this);
        id_front_del.setOnClickListener(this);
        id_reverse_pic.setOnClickListener(this);
        id_reverse_del.setOnClickListener(this);
        service_scope.setOnClickListener(this);
        industry_category.setOnClickListener(this);
        release.setOnClickListener(this);
        save.setOnClickListener(this);

        dialogConfirmView = DialogConfirmView.newInstance(this);
        View view = dialogConfirmView.create(null, "完善基本信息（专业信息为选填）后才可填写企业信息");
        dialogConfirmView.setPositiveClickListener(new DialogConfirmView.onPositiveClickListener() {
            @Override
            public void onClick(View view) {
                if (null != dialog) {
                    getEnterprise();
                    dialog.dismiss();
                }
            }
        });
        dialog = DialogUtils.showCustomDialog(this, view, false);
    }

    @Override
    public void initData() {
        getToken();
        picAdapter = new PicAdapter(this, upload_pics, 5);
        picAdapter.setOnPicListener(new PicAdapter.onPicListener() {
            @Override
            public void onAddPic() {
                showPicSel(LIST);
            }

            @Override
            public void onDelPic(int position) {
                upload_pics.remove(position);
                pic_num.setText(upload_pics.size() + "/5");
                picAdapter.setData(upload_pics);
            }
        });
        pic.setAdapter(picAdapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_enterprise_edit;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (100 == resultCode && null != data) {
            String city = data.getStringExtra("city");
            cityIds = data.getStringExtra("cityIds");
            service_scope.setText(cityIds.split(",").length + "/3");
        }

        if (200 == resultCode && null != data) {
            //Todo:: 获取行业类别
            candos = data.getStringExtra("categoryIds");
            industry_category.setText(candos.split(",").length + "/16");
        }

        switch (requestCode) {
            case BUSINESS_CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功
                        File photoFile = new File(bussiness_name);
                        if (photoFile.exists()) {
//                            bussiness_str = bussiness_name;
//                            Glide.with(this).load(photoFile).asBitmap().into(business_pic);
                            business_del.setVisibility(View.VISIBLE);
                            uploadImageToQiniu(BUSINESS, bussiness_name);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case BUSINESS_PHOTO_REQUEST:
                if (data != null) {
//                    bussiness_str = StringUtils.getPath(this, data.getData());
//                    Glide.with(this).load(data.getData()).asBitmap().into(business_pic);

                    business_del.setVisibility(View.VISIBLE);
//                    uploadImageToQiniu(BUSINESS, StringUtils.getPath(this, data));
                    List<String> pathList = Album.parseResult(data);
                    if (null == pathList || pathList.size() == 0) {
                        return;
                    }
                    uploadImageToQiniu(BUSINESS, pathList.get(0));
                }
                break;
            case ID_FRONT_CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功
                        File photoFile = new File(id_front_name);
                        if (photoFile.exists()) {
//                            id_front_str = id_front_name;
//                            Glide.with(this).load(photoFile).asBitmap().into(id_front_pic);
                            id_front_del.setVisibility(View.VISIBLE);
                            uploadImageToQiniu(ID_FRONT, id_front_name);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case ID_FRONT_PHOTO_REQUEST:
                if (data != null) {
//                    id_front_str = StringUtils.getPath(this, data.getData());
//                    Glide.with(this).load(data.getData()).asBitmap().into(id_front_pic);
                    id_front_del.setVisibility(View.VISIBLE);
//                    uploadImageToQiniu(ID_FRONT, StringUtils.getPath(this, data));
                    List<String> pathList = Album.parseResult(data);
                    if (null == pathList || pathList.size() == 0) {
                        return;
                    }
                    uploadImageToQiniu(ID_FRONT, pathList.get(0));
                }
                break;
            case ID_REVE_CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功
                        File photoFile = new File(id_reve_name);
                        if (photoFile.exists()) {
//                            id_reve_str = id_reve_name;
//                            Glide.with(this).load(photoFile).asBitmap().into(id_reverse_pic);
                            id_reverse_del.setVisibility(View.VISIBLE);
                            uploadImageToQiniu(ID_REVE, id_reve_name);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case ID_REVE_PHOTO_REQUEST:
                if (data != null) {
//                    id_reve_str = StringUtils.getPath(this, data.getData());
//                    Glide.with(this).load(data.getData()).asBitmap().into(id_reverse_pic);
                    id_reverse_del.setVisibility(View.VISIBLE);
//                    uploadImageToQiniu(ID_REVE, StringUtils.getPath(this, data));
                    List<String> pathList = Album.parseResult(data);
                    if (null == pathList || pathList.size() == 0) {
                        return;
                    }
                    uploadImageToQiniu(ID_REVE, pathList.get(0));
                }
                break;
            case LIST_CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功
                        File photoFile = new File(FileUtils.getImagePath(), pic_name + ".jpg");
                        if (photoFile.exists()) {
                            uploadImageToQiniu(LIST, photoFile.getAbsolutePath());
                        }
                        break;
                    default:
                        break;
                }
                break;
            case LIST_PHOTO_REQUEST:
                if (data != null) {
//                    photos.add(StringUtils.getPath(this, data));
//                    picAdapter.setData(photos);

//                    uploadImageToQiniu(LIST, StringUtils.getPath(this, data));
                    List<String> pathList = Album.parseResult(data);
                    if (null == pathList) {
                        return;
                    }
                    iPaths = pathList.iterator();
                    uploadImageToQiniu(LIST, iPaths.next());
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
            case R.id.business_pic:
                showPicSel(BUSINESS);
                break;
            case R.id.business_del:
                business_del.setVisibility(View.GONE);
                bussiness_str = null;
                Glide.with(this).load(R.mipmap.pic_add).asBitmap().into(business_pic);
                break;
            case R.id.id_front_pic:
                showPicSel(ID_FRONT);
                break;
            case R.id.id_front_del:
                id_front_del.setVisibility(View.GONE);
                id_front_str = null;
                Glide.with(this).load(R.mipmap.pic_add).asBitmap().into(id_front_pic);
                break;
            case R.id.id_reverse_pic:
                showPicSel(ID_REVE);
                break;
            case R.id.id_reverse_del:
                id_reverse_del.setVisibility(View.GONE);
                id_reve_str = null;
                Glide.with(this).load(R.mipmap.pic_add).asBitmap().into(id_reverse_pic);
                break;
            case R.id.service_scope:
                Intent intent = new Intent(this, HCAreaSelActivity.class);
                intent.putExtra(UiGoto.BUNDLE, cityIds);
                startActivityForResult(intent, 100);
                break;
            case R.id.industry_category:
                Intent i = new Intent(this, WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", "http://39.106.138.102:8092/h5/p/enterprise.html");
//                bundle.putString("url", "file:///android_asset/h5/enterprise.html");
                bundle.putString("title", "类别选择");
                i.putExtra(UiGoto.BUNDLE, bundle);
                startActivityForResult(i, 200);
                break;
            case R.id.release:
                submit(1);
                break;
            case R.id.save:
                submit(0);
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
                        if (BUSINESS.equals(type)) {
                            getPicFromCamera(BUSINESS_CAMERA_REQUEST);
                        } else if (ID_FRONT.equals(type)) {
                            getPicFromCamera(ID_FRONT_CAMERA_REQUEST);
                        } else if (ID_REVE.equals(type)) {
                            getPicFromCamera(ID_REVE_CAMERA_REQUEST);
                        } else {
                            getPicFromCamera(LIST_CAMERA_REQUEST);
                        }
                        break;
                    case 1:
                        if (BUSINESS.equals(type)) {
                            getPicFromPhoto(BUSINESS_PHOTO_REQUEST);
                        } else if (ID_FRONT.equals(type)) {
                            getPicFromPhoto(ID_FRONT_PHOTO_REQUEST);
                        } else if (ID_REVE.equals(type)) {
                            getPicFromPhoto(ID_REVE_PHOTO_REQUEST);
                        } else {
                            getPicFromPhoto(LIST_PHOTO_REQUEST);
                        }
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
            limitCount = 5 - upload_pics.size();
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
        if (BUSINESS_CAMERA_REQUEST == type) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(bussiness_name)));
        } else if (ID_FRONT_CAMERA_REQUEST == type) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(id_front_name)));
        } else if (ID_REVE_CAMERA_REQUEST == type) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(id_reve_name)));
        } else {
            pic_name = System.currentTimeMillis();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(FileUtils.getImagePath(), pic_name + ".jpg")));
        }
        startActivityForResult(intent, type);
    }

    /**
     * 获取企业详情
     */
    private void getEnterprise() {
        showDialogLoading();
        ApiClient.getEnterpriseInfoDetail(this, new ResultCallback<EnterpriseInfoDetailResult>() {
            @Override
            public void onSuccess(EnterpriseInfoDetailResult response) {
                System.out.println("result-enterpriseInfo=" + response.toString());
                hideDialogLoading();
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        EnterpriseInfoDto bean = response.getData();
                        if (null != bean) {
                            orgId = bean.getOrgId();
                            company_name.setText(bean.getName());
                            bussiness_str = bean.getRegisterPic();
                            Glide.with(EnterpriseEditActivity.this).load(bean.getRegisterPic()).asBitmap().into(business_pic);
                            id_front_str = bean.getMasterPic1();
                            Glide.with(EnterpriseEditActivity.this).load(bean.getMasterPic1()).asBitmap().into(id_front_pic);
                            id_reve_str = bean.getMasterPic2();
                            Glide.with(EnterpriseEditActivity.this).load(bean.getMasterPic2()).asBitmap().into(id_reverse_pic);
                            legal_name.setText(null == bean.getMaster() ? NULL_TEXT : bean.getMaster());
                            registered_capital.setText(null == bean.getRegisterMoney() ? NULL_TEXT : bean.getRegisterMoney());
                            mobile.setText(null == bean.getMobile() ? NULL_TEXT : bean.getMobile());
                            lanline.setText(null == bean.getPhone() ? NULL_TEXT : bean.getPhone());
                            if (!TextUtils.isEmpty(bean.getAddr())) {
                                company_address.setText(bean.getAddr());
                            } else {
                                company_address.setText(prefUtils.getKeyLocationAddress());
                            }
                            details_address.setText(null == bean.getAddrDetails() ? NULL_TEXT : bean.getAddrDetails());
                            candos = bean.getContent();
                            cityIds = bean.getArea();
                            if (!TextUtils.isEmpty(candos)) {
                                industry_category.setText(candos.split(",").length + "/16");
                            }
                            if (!TextUtils.isEmpty(bean.getArea())) {
                                service_scope.setText(bean.getArea().split(",").length + "/3");
                            }
                            upload_pics = bean.getImgs();
                            pic_num.setText(upload_pics.size() + "/5");
                            picAdapter.setData(upload_pics);
                        }
                    }
                    showToast(response.getMsg());
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
     * 保存企业信息
     *
     * @param dto
     */
    private void saveEnterprise(EnterpriseInfoDto dto) {
        showDialogLoading();
        ApiClient.saveEnterInfo(this, dto, new ResultCallback<ResultData>() {
            @Override
            public void onSuccess(ResultData response) {
                System.out.println("result-enterprise=" + response.toString());
                hideDialogLoading();
                showToast(response.getMsg());
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                showToast(msg);
            }
        });
    }

    /**
     * 页面提交
     */
    private void submit(int flag) {
        String name_company = company_name.getText().toString().trim();
        String name_legel = legal_name.getText().toString().trim();
        String registered_money = registered_capital.getText().toString().trim();
        String phone = mobile.getText().toString().trim();
        String landPhone = lanline.getText().toString().trim();
        String addr_company = company_address.getText().toString().trim();
        String addr_details = details_address.getText().toString().trim();

        EnterpriseInfoDto bean = new EnterpriseInfoDto();
        bean.setUserId(prefUtils.getUserInfo().getUserId());
        bean.setLatitude(String.valueOf(prefUtils.getKeyLocationLat()));
        bean.setLongitude(String.valueOf(prefUtils.getKeyLocationLong()));
        bean.setFlag(flag);
        if (!TextUtils.isEmpty(orgId)) {
            bean.setOrgId(orgId);
        }
        if (TextUtils.isEmpty(bussiness_str)) {
            showToast("营业执照不能为空");
            return;
        }
        bean.setRegisterPic(bussiness_str);

        if (TextUtils.isEmpty(id_front_str)) {
            showToast("身份证照片不能为空");
            return;
        }
        bean.setMasterPic1(id_front_str);

        if (TextUtils.isEmpty(id_reve_str)) {
            showToast("身份证照片不能为空");
            return;
        }
        bean.setMasterPic2(id_reve_str);

        if (upload_pics.size() <= 0) {
            showToast("简介照片不能为空");
            return;
        }
        bean.setImgs(upload_pics);

        if (TextUtils.isEmpty(name_company)) {
            showToast("公司名称不能为空");
            return;
        }
        bean.setName(name_company);

        if (TextUtils.isEmpty(name_legel)) {
            showToast("法人姓名不能为空");
            return;
        }
        bean.setMaster(name_legel);

        if (TextUtils.isEmpty(registered_money)) {
            showToast("注册资金不能为空");
            return;
        }
        bean.setRegisterMoney(registered_money);

        if (TextUtils.isEmpty(phone)) {
            showToast("联系电话不能为空");
            return;
        }
        bean.setMobile(phone);

        if (TextUtils.isEmpty(landPhone)) {
            showToast("公司座机不能为空");
            return;
        }
        bean.setPhone(landPhone);

        if (TextUtils.isEmpty(addr_company)) {
            showToast("公司地址不能为空");
            return;
        }
        bean.setAddr(addr_company);

        if (TextUtils.isEmpty(addr_details)) {
            showToast("公司详细地址不能为空");
            return;
        }
        bean.setAddrDetails(addr_details);

        if (TextUtils.isEmpty(cityIds)) {
            showToast("服务范围不能为空");
            return;
        }
        bean.setArea(cityIds);
        if (TextUtils.isEmpty(candos)) {
            showToast("资质内容不能为空");
            return;
        }
        bean.setContent(candos);
        saveEnterprise(bean);
    }

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
    private void uploadImageToQiniu(final String type, final String filePath) {
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
                    System.out.println("result-qiniu==Upload Success::" + str_key);
                    if (type.equals(BUSINESS)) {
                        bussiness_str = str_key;
                        Glide.with(EnterpriseEditActivity.this).load(filePath).asBitmap().into(business_pic);
                    } else if (type.equals(ID_FRONT)) {
                        id_front_str = str_key;
                        Glide.with(EnterpriseEditActivity.this).load(filePath).asBitmap().into(id_front_pic);
                    } else if (type.equals(ID_REVE)) {
                        id_reve_str = str_key;
                        Glide.with(EnterpriseEditActivity.this).load(filePath).asBitmap().into(id_reverse_pic);
                    } else if (type.equals(LIST)) {
                        upload_pics.add(str_key);
                        pic_num.setText(upload_pics.size() + "/5");
                        picAdapter.setData(upload_pics);
                        if (iPaths.hasNext()) {
                            uploadImageToQiniu(LIST, iPaths.next());
                        }
                    }
                    showToast("上传成功");
                } else {
                    System.out.println("result-qiniu===Upload Fail");
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                    showToast("上传失败");
                }
                System.out.println("result-qiniu" + key + ",\r\n " + info + ",\r\n " + res);
            }
        }, null);
    }

    private void showToast(String msg) {
        ToastUtils.showShort(this, msg);
    }
}

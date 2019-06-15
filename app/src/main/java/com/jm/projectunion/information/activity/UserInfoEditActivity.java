package com.jm.projectunion.information.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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
import com.jm.projectunion.common.utils.StringUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.common.widget.CircleImageView;
import com.jm.projectunion.dao.AreaDaoUtil;
import com.jm.projectunion.dao.bean.AreaBean;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.information.adapter.PicAdapter;
import com.jm.projectunion.information.entity.UserInfoDetailResult;
import com.jm.projectunion.message.chat.EaseIMHelper;
import com.jm.projectunion.utils.GlobalVar;
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
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Young on 2017/10/26.
 */

public class UserInfoEditActivity extends BaseTitleActivity {

    private static final String HEADER = "header";
    private static final int HEADER_PHOTO_REQUEST = 1; //相册_头像
    private static final int HEADER_CAMERA_REQUEST = 2;//相机_头像

    private static final String LIST = "list";
    private static final int LIST_PHOTO_REQUEST = 11; //相册_列表
    private static final int LIST_CAMERA_REQUEST = 22;//相机_列表

    private static final int PHOTO_CLIP = 3;//裁剪

    private long pic_name;
    private String head_img = FileUtils.getImagePath() + File.separator + "headImg.jpg";
    private String head_str;
    private Uri uritempFile;//头像裁剪
    private Bitmap bmp_header;

    @BindView(R.id.header_img)
    CircleImageView header_img;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.age)
    EditText age;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.re_location)
    TextView re_location;
    @BindView(R.id.address_detail)
    EditText address_detail;
    @BindView(R.id.intro)
    EditText intro;
    @BindView(R.id.work_age)
    TextView work_age;
    @BindView(R.id.service_scope)
    TextView service_scope;
    @BindView(R.id.industry_category)
    TextView industry_category;
    @BindView(R.id.pic)
    GridView pic;
    @BindView(R.id.pic_num)
    TextView pic_num;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.release)
    Button release;
    @BindView(R.id.goon)
    TextView goon;

    private PicAdapter picAdapter;
    private String token_qn;//七牛凭证
    private PrefUtils prefUtils;
    private AreaDaoUtil daoUtil;

    private String cityIds;
    private String candos;
    private String upload_head;//上传头像之后返回的地址
    private List<String> upload_pics = new ArrayList<>();//上传图片列表之后返回的地址集合
    private Iterator<String> iPaths;
    private boolean isCommite = false;

    @Override
    public void initView() {
        setTitleText("个人信息");
        prefUtils = PrefUtils.getInstance(this);
        daoUtil = new AreaDaoUtil(this);

        header_img.setOnClickListener(this);
        sex.setOnClickListener(this);
        work_age.setOnClickListener(this);
        service_scope.setOnClickListener(this);
        re_location.setOnClickListener(this);
        industry_category.setOnClickListener(this);
        save.setOnClickListener(this);
        release.setOnClickListener(this);


        goon.setOnClickListener(this);
    }

    @Override
    public void initData() {
        getToken();
        getData();
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
        return R.layout.activity_userinfo_edit;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.header_img:
                showPicSel(HEADER);
                break;
            case R.id.sex:
                showSexSel();
                break;
            case R.id.re_location:
                address.setText(prefUtils.getKeyLocationAddress());
                break;
            case R.id.work_age:
                showWAgeSel();
                break;
            case R.id.service_scope:
                //新适配器 未完成
                Intent intent = new Intent(this, HCAreaSelActivity.class);
                //Intent intent = new Intent(this, AreaSelActivity.class);
                intent.putExtra(UiGoto.BUNDLE, cityIds);
                startActivityForResult(intent, 100);
                break;
            case R.id.industry_category:
                Intent i = new Intent(this, WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", "http://39.106.138.102:8092/h5/p/personal.html");
//                bundle.putString("url", "file:///android_asset/h5/personal.html");
                bundle.putString("title", "类别选择");
                i.putExtra(UiGoto.BUNDLE, bundle);
                startActivityForResult(i, 200);
                break;
            case R.id.save:
                submit("0");
                break;
            case R.id.release:
                submit("1");
                break;
            case R.id.goon:
                //TODO wfx修改 暂时屏蔽继续填写企业信息
//                showToast("暂无权限");
                UiGoto.startAty(this, EnterpriseEditActivity.class);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (100 == resultCode && null != data) {
//            String city = data.getStringExtra("city");
            cityIds = data.getStringExtra("cityIds");
            service_scope.setText(cityIds.split(",").length + "/3");
        }

        if (200 == resultCode && null != data) {
            //Todo:: 获取行业类别
            candos = data.getStringExtra("categoryIds");
            industry_category.setText(candos.split(",").length + "/16");
        }
        //拍照返回
        switch (requestCode) {
            case HEADER_CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功
                        File photoFile = new File(head_img);
                        if (photoFile.exists()) {
                            photoClip(Uri.fromFile(photoFile));
                        }
                        break;
                    default:
                        break;
                }
                break;
            case HEADER_PHOTO_REQUEST:
                if (data != null) {
                    photoClip(data.getData());
//                    List<String> pathList = Album.parseResult(data);
//                        if (null == pathList || pathList.size() == 0) {
//                            return;
//                    }
//                    photoClip(Uri.parse(pathList.get(0)));
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
                    List<String> pathList = Album.parseResult(data);
                    if (null == pathList) {
                        return;
                    }
                    iPaths = pathList.iterator();
                    uploadImageToQiniu(LIST, iPaths.next());
                }
                break;
            case PHOTO_CLIP:
                try {
                    bmp_header = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                    head_str = FileUtils.saveFile(this, "temphead.jpg", bmp_header);
                    uploadImageToQiniu(HEADER, head_str);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    showToast("系统异常");
                }
                break;
            default:
                break;
        }

    }

    private Dialog dialog;
    private DialogConfirmView dialogConfirmView;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isCommite) {
                dialogConfirmView = DialogConfirmView.newInstance(UserInfoEditActivity.this);
                View view = dialogConfirmView.create("提示", "是否保存信息");
                dialogConfirmView.setNegative("否");
                dialogConfirmView.setPositive("是");
                dialogConfirmView.setNegativeClickListener(new DialogConfirmView.onNegativeClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != dialog) {
                            dialog.dismiss();
                        }
                        finish();
                    }
                });
                dialogConfirmView.setPositiveClickListener(new DialogConfirmView.onPositiveClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != dialog) {
                            dialog.dismiss();
                        }
                        submit("1");
                    }
                });
                dialog = DialogUtils.showCustomDialog(UserInfoEditActivity.this, view, false);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 打开相册
     */
    private void getPicFromPhoto(int type) {
        if(HEADER_PHOTO_REQUEST == type) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*");
            startActivityForResult(intent, type);
        }else {
            int limitCount = 1;
            if (LIST_PHOTO_REQUEST == type) {
                limitCount = 5 - upload_pics.size();
            }
            Album.startAlbum(this, type
                    , limitCount                                                         // 指定选择数量。
                    , ContextCompat.getColor(this, R.color.colorPrimary)        // 指定Toolbar的颜色。
                    , ContextCompat.getColor(this, R.color.colorPrimaryDark));  // 指定状态栏的颜色。
        }
    }

    /**
     * 打开相机
     */
    private void getPicFromCamera(int type) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        if (HEADER_CAMERA_REQUEST == type) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(head_img)));
        } else {
            pic_name = System.currentTimeMillis();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(FileUtils.getImagePath(), pic_name + ".jpg")));
        }
        startActivityForResult(intent, type);
    }

    /**
     * 图片裁剪
     *
     * @param uri
     */
    private void photoClip(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
//        intent.putExtra("return-data", true);
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, PHOTO_CLIP);
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
                        if (HEADER.equals(type)) {
                            getPicFromCamera(HEADER_CAMERA_REQUEST);
                        } else {
                            getPicFromCamera(LIST_CAMERA_REQUEST);
                        }
                        break;
                    case 1:
                        if (HEADER.equals(type)) {
                            getPicFromPhoto(HEADER_PHOTO_REQUEST);
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
     * 性别选择框
     */
    private void showSexSel() {
        final List<String> sexList = new ArrayList<>();
        sexList.add("男");
        sexList.add("女");
        BottomCancelPopupWindow bottomPopupWindow = new BottomCancelPopupWindow(this, sexList);
        bottomPopupWindow.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                sex.setText(sexList.get(position));
            }
        });
        bottomPopupWindow.showAtBottom(findViewById(R.id.main));
    }

    /**
     * 工龄选择框
     */
    private void showWAgeSel() {
        final List<String> wAgeList = new ArrayList<>();
        for (String age : GlobalVar.WORK_AGE) {
            wAgeList.add(age);
        }
        BottomCancelPopupWindow bottomPopupWindow = new BottomCancelPopupWindow(this, wAgeList);
        bottomPopupWindow.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                work_age.setText(wAgeList.get(position));
            }
        });
        bottomPopupWindow.showAtBottom(findViewById(R.id.main));
    }

    /**
     * 获取信息
     */
    private void getData() {
        showDialogLoading();
        ApiClient.getUserInfoDetail(this, prefUtils.getUserInfo().getUserId(),
                new ResultCallback<UserInfoDetailResult>() {
                    @Override
                    public void onSuccess(UserInfoDetailResult response) {
                        hideDialogLoading();
                        if (null != response) {
                            System.out.println("result-user=" + response.toString());
                            if (null != response.getData()) {
                                UserInfoDetailResult.UserInfoDetailBean bean = response.getData();
                                Glide.with(UserInfoEditActivity.this).load(bean.getAvatar()).asBitmap().error(R.mipmap.ic_launcher).into(header_img);
                                name.setText(null == bean.getRealname() ? "" : bean.getRealname());
                                sex.setText(null == bean.getSex() ? "请选择" : ("1".equals(bean.getSex()) ? "男" : "女"));
                                age.setText(null == bean.getAge() ? "" : bean.getAge());
                                phone.setText(null == bean.getPhone() ? "" : bean.getPhone());
                                if (TextUtils.isEmpty(bean.getAddress())) {
                                    address.setText(prefUtils.getKeyLocationAddress());
                                } else {
                                    address.setText(bean.getAddress());
                                }
                                address_detail.setText(null == bean.getAddressDetails() ? "" : bean.getAddressDetails());
                                intro.setText(null == bean.getDesc() ? "" : bean.getDesc());
                                upload_pics = bean.getImgs();
                                pic_num.setText(upload_pics.size() + "/5");
                                picAdapter.setData(upload_pics);
                                if (null != bean.getWorkYear()) {
                                    if ("20".equals(bean.getWorkYear())) {
                                        work_age.setText("20年以上");
                                    } else {
                                        work_age.setText(bean.getWorkYear() + "年");
                                    }
                                }
                                cityIds = bean.getService();
                                if (null != cityIds) {
                                    service_scope.setText(cityIds.split(",").length + "/3");
                                }
                                candos = bean.getIndustryType();
                                if (null != candos) {
                                    industry_category.setText(candos.split(",").length + "/16");
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
                    showToast("上传成功");
                    if (type.equals(HEADER)) {
                        upload_head = str_key;
                        Glide.with(UserInfoEditActivity.this).load(filePath).asBitmap().into(header_img);
                    } else if (type.equals(LIST)) {
                        upload_pics.add(str_key);
                        pic_num.setText(upload_pics.size() + "/5");
                        picAdapter.setData(upload_pics);
                        if (iPaths.hasNext()) {
                            uploadImageToQiniu(LIST, iPaths.next());
                        }
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
     * 保存用户信息
     *
     * @param bean
     */
    private void saveUser(final UserInfoDetailResult.UserInfoDetailBean bean) {
        showDialogLoading();
        ApiClient.saveUserInfoDetail(this, bean, new ResultCallback<ResultData>() {
            @Override
            public void onSuccess(ResultData response) {
                System.out.println("result-submit=" + response.toString());
                showToast(response.getMsg());
                hideDialogLoading();
                if ("0".equals(response.getCode())) {
                    isCommite = true;
                    prefUtils.setUserPic(bean.getAvatar());
                    prefUtils.setUserNickname(bean.getRealname());
                    updateRemoteNick(bean.getRealname());
                    if (null != bmp_header) {
                        uploadUserAvatar(FileUtils.bitmapToBytes(bmp_header));
                    }
                }
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                showToast(msg);
                isCommite = false;
            }
        });
    }

    /**
     * 全部提交
     */
    private void submit(String releaseType) {
        UserInfoDetailResult.UserInfoDetailBean bean = new UserInfoDetailResult.UserInfoDetailBean();
        bean.setUserId(prefUtils.getUserInfo().getUserId());
        bean.setAvatar(upload_head);
        bean.setImgs(upload_pics);
        bean.setLatitude(String.valueOf(prefUtils.getKeyLocationLat()));
        bean.setLongitude(String.valueOf(prefUtils.getKeyLocationLong()));
        bean.setPublishTime(String.valueOf(System.currentTimeMillis()));
        bean.setPublish(releaseType);
        AreaBean areaBean = daoUtil.queryByName(prefUtils.getKeyLocationCity());
        if (null != areaBean) {
            bean.setCityId(String.valueOf(areaBean.getAreaId()));
        }
        String username = name.getText().toString().trim();
        if(username!=null && username!="" && username.length()<2){

        }

        String usersex = sex.getText().toString().trim();
        String userage = age.getText().toString().trim();
        String userphone = phone.getText().toString().trim();
        String useraddress = address.getText().toString().trim();
        String useraddress_detail = address_detail.getText().toString().trim();
        String userintro = intro.getText().toString().trim();
        String userwork_age = StringUtils.getNumbers(work_age.getText().toString().trim());

        if (TextUtils.isEmpty(username)) {
            showToast("填写姓名");
            return;
        }else{
            if(username.length()<2){
                showToast("姓名必须大于2个字");
                return;
            }
        }
        bean.setRealname(username);

        String sexType;
        if (TextUtils.isEmpty(usersex)) {
            showToast("选择性别");
            return;
        } else {
            if ("男".equals(usersex)) {
                sexType = "1";
            } else {
                sexType = "2";
            }
        }
        bean.setSex(sexType);

        if (TextUtils.isEmpty(userage)) {
            showToast("填写年龄");
            return;
        }
        bean.setAge(userage);

        if (!StringUtils.isMobile(userphone)) {
            showToast("手机号格式错误");
            return;
        }
        bean.setPhone(userphone);

        if (TextUtils.isEmpty(useraddress)) {
            showToast("填写地址");
            return;
        }
        bean.setAddress(useraddress);

//        if (TextUtils.isEmpty(useraddress_detail)) {
//            showToast("填写详细地址");
//            return;
//        }
        bean.setAddressDetails(useraddress_detail);

        if (!TextUtils.isEmpty(userintro) && userintro.length() > 50) {
            showToast("简介超过50字");
            return;
        }
        bean.setDesc(userintro);

//        if (TextUtils.isEmpty(userwork_age)) {
//            showToast("选择工龄");
//            return;
//        }
        bean.setWorkYear(userwork_age);

//        if (TextUtils.isEmpty(cityIds)) {
//            showToast("选择服务范围");
//            return;
//        }
        bean.setService(cityIds);

//        if (TextUtils.isEmpty(candos)) {
//            showToast("选择行业类别");
//            return;
//        }
        bean.setIndustryType(candos);

        saveUser(bean);
        if(releaseType.equals("1")){
            hideDialogLoading();
            finish();
        }
    }

    //同步环信昵称
    private void updateRemoteNick(final String nickName) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                boolean updatenick = EaseIMHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(nickName);
                if (UserInfoEditActivity.this.isFinishing()) {
                    return;
                }
            }
        }).start();
    }

    //同步环信头像
    private void uploadUserAvatar(final byte[] data) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                final String avatarUrl = EaseIMHelper.getInstance().getUserProfileManager().uploadUserAvatar(data);

            }
        }).start();

    }

    private void showToast(String msg) {
        ToastUtils.showShort(this, msg);
    }
}

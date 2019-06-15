package com.jm.projectunion.home.activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jm.projectunion.HCAreaSelActivity;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.common.manager.Config;
import com.jm.projectunion.common.utils.FileUtils;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.StringUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.dao.AreaDaoUtil;
import com.jm.projectunion.dao.bean.AreaBean;
import com.jm.projectunion.entity.Result;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.home.HomeFragment;
import com.jm.projectunion.home.dto.ReleaseInfoDto;
import com.jm.projectunion.home.fragment.ReleaseInfoCategoryFragment;
import com.jm.projectunion.information.adapter.PicAdapter;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;
import com.jm.projectunion.wiget.popupwindow.BottomCancelPopupWindow;
import com.jm.projectunion.wiget.popupwindow.BottomListPopupWindow;
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

public class InfoReleaseActivity extends BaseTitleActivity {
    private static final String HEADER = "header";
    private static final int HEADER_PHOTO_REQUEST = 1; //相册_头像
    private static final int HEADER_CAMERA_REQUEST = 2;//相机_头像

    private static final String LIST = "list";
    private static final int LIST_PHOTO_REQUEST = 11; //相册_列表
    private static final int LIST_CAMERA_REQUEST = 22;//相机_列表

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.ll_name)
    LinearLayout ll_name;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.ll_category)
    LinearLayout ll_category;
    @BindView(R.id.category)
    TextView category;
    @BindView(R.id.house)
    LinearLayout house;
    @BindView(R.id.house_type)
    RadioGroup house_type;
    @BindView(R.id.prices)
    LinearLayout prices;
    @BindView(R.id.price_mark)
    TextView price_mark;
    @BindView(R.id.price)
    EditText price;
    @BindView(R.id.areas)
    LinearLayout areas;
    @BindView(R.id.area)
    TextView area;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.address_detail)
    EditText address_detail;
    @BindView(R.id.intro)
    EditText intro;
    @BindView(R.id.pic)
    GridView pic;
    @BindView(R.id.pic_num)
    TextView pic_num;
    @BindView(R.id.release)
    Button release;

    private String articleType;//发布类型
    private String fileName = "category_";
    private List<String> photos = new ArrayList<>();
    private List<String> upload_pics = new ArrayList<>();
    private Iterator<String> iPaths;
    private String houseTypId = "1";
    private PicAdapter picAdapter;
    private PrefUtils prefUtils;
    private AreaDaoUtil daoUtil;
    private long pic_name;
    private String cityIds;
    private String head_img = FileUtils.getImagePath() + File.separator + "headImg.jpg";
    private String upload_head;
    private List<String> categorys = new ArrayList<>();
    private String categoryId = "0";
    private int categorySuffix;
    private BottomListPopupWindow bottomPopupWindow;

    @Override
    public void initView() {
        daoUtil = new AreaDaoUtil(this);
        prefUtils = PrefUtils.getInstance(this);
        setTitleText("信息发布");
        articleType = getIntent().getStringExtra(UiGoto.HOME_TYPE);
        int int_artivleType = Integer.valueOf(articleType);
        if (int_artivleType >= 10) {
            categorySuffix = int_artivleType * 100;
        } else {
            categorySuffix = int_artivleType * 1000;
        }
        address.setText(prefUtils.getKeyLocationAddress());
        AreaBean bean = daoUtil.queryByName(prefUtils.getKeyLocationCity());
        if (null != bean) {
            cityIds = String.valueOf(bean.getAreaId());
        }
        picAdapter = new PicAdapter(this, photos, 5);
        picAdapter.setOnPicListener(new PicAdapter.onPicListener() {
            @Override
            public void onAddPic() {
                showPicSel(LIST);
            }

            @Override
            public void onDelPic(int position) {
                photos.remove(position);
                upload_pics.remove(position);
                picAdapter.setData(photos);
                pic_num.setText(photos.size() + "/5");
            }
        });
        pic.setAdapter(picAdapter);

        img.setOnClickListener(this);
        category.setOnClickListener(this);
        area.setOnClickListener(this);
        release.setOnClickListener(this);
        bottomPopupWindow = new BottomListPopupWindow(this, categorys);
        bottomPopupWindow.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                categoryId = String.valueOf(categorySuffix + position);
                category.setText(categorys.get(position));
            }
        });

        house_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.house_lease:
                        houseTypId = "1";
                        break;
                    case R.id.house_sell:
                        houseTypId = "2";
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        getToken();
        fileName = fileName + initCategory(articleType);
        categorys = FileUtils.readFileFromAssets(this, fileName);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_info_release;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img:
                showPicSel(HEADER);
                break;
            case R.id.category:
                bottomPopupWindow.setData(categorys);
                bottomPopupWindow.showAtBottom(findViewById(R.id.main));
                break;
            case R.id.area:
                Intent intent = new Intent(this, HCAreaSelActivity.class);
                intent.putExtra(UiGoto.BUNDLE, cityIds);
                startActivityForResult(intent, 100);
                break;
            case R.id.release:
                release();
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
            area.setText(cityIds.split(",").length + "/3");
        }
        switch (requestCode) {
            case HEADER_CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功
                        File photoFile = new File(head_img);
                        if (photoFile.exists()) {
                            uploadImageToQiniu(HEADER, head_img);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case HEADER_PHOTO_REQUEST:
                if (data != null) {
//                    uploadImageToQiniu(HEADER, StringUtils.getPath(this, data));
                    List<String> pathList = Album.parseResult(data);
                    if (null == pathList || pathList.size() == 0) {
                        return;
                    }
                    uploadImageToQiniu(HEADER, pathList.get(0));
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
            default:
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
        if (HEADER_CAMERA_REQUEST == type) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(head_img)));
        } else {
            pic_name = System.currentTimeMillis();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(FileUtils.getImagePath(), pic_name + ".jpg")));
        }
        startActivityForResult(intent, type);
    }

    /**
     * 发布信息
     *
     * @param dto
     */
    private void releaseInfo(ReleaseInfoDto dto) {
        showDialogLoading();
        ApiClient.releseInfo(this, dto, new ResultCallback<Result>() {
            @Override
            public void onSuccess(Result response) {
                hideDialogLoading();
                System.out.println("error-" + response.getMsg());
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        ToastUtils.showShort(InfoReleaseActivity.this, "发布成功");
                        finish();
                    } else {
                        ToastUtils.showShort(InfoReleaseActivity.this, response.getMsg());
                    }
                }
            }

            @Override
            public void onError(String msg) {
                System.out.println("error-" + msg);
                hideDialogLoading();
                ToastUtils.showShort(InfoReleaseActivity.this, msg);
            }
        });
    }

    /**
     * 提交
     */
    private void release() {
        ReleaseInfoDto dto = new ReleaseInfoDto();
        dto.setLatitude(String.valueOf(prefUtils.getKeyLocationLat()));
        dto.setLongitude(String.valueOf(prefUtils.getKeyLocationLong()));
        dto.setArticleType(articleType);
        dto.setUserId(prefUtils.getUserInfo().getUserId());
        dto.setImgs(upload_pics);
        dto.setStatus("1");

        String str_title = title.getText().toString().trim();
        String str_name = name.getText().toString().trim();
        String str_phone = phone.getText().toString().trim();
        String str_category = category.getText().toString().trim();
        String str_addr = address.getText().toString().trim();
        String str_addr_detail = address_detail.getText().toString().trim();
        String str_intro = intro.getText().toString().trim();
        if (TextUtils.isEmpty(upload_head)) {
            showToast("封面图不能为空");
            return;
        }
        dto.setImage(upload_head);
        if (TextUtils.isEmpty(str_title)) {
            showToast("标题不能为空");
            return;
        }
        dto.setTitle(str_title);
        if (ll_name.getVisibility() == View.VISIBLE) {
            if (TextUtils.isEmpty(str_name)) {
                showToast("名称不能为空");
                return;
            }
            dto.setAuthor(str_name);
        }
        if (!StringUtils.isMobile(str_phone)) {
            showToast("手机号格式错误");
            return;
        }
        dto.setPhone(str_phone);
        if (ll_category.getVisibility() == View.VISIBLE) {
            if (TextUtils.isEmpty(str_category)) {
                showToast("分类不能为空");
                return;
            }
        }
        dto.setCategoryIds(categoryId);
        if (TextUtils.isEmpty(str_addr)) {
            showToast("地址不能为空");
            return;
        }
        dto.setAddr(str_addr);
        if (TextUtils.isEmpty(str_addr_detail)) {
            showToast("详细地址不能为空");
            return;
        }
        dto.setAddrDetails(str_addr_detail);
//        if (TextUtils.isEmpty(str_intro)) {
//            showToast("简介不能为空");
//            return;
//        }
        dto.setDesp(str_intro);

        /**
         * 价格相关
         */
        if (house.getVisibility() == View.VISIBLE) {
            dto.setType(houseTypId);
        }

        if (prices.getVisibility() == View.VISIBLE) {
            String str_price = price.getText().toString().trim();
            if (TextUtils.isEmpty(str_price)) {
                showToast("价格不能为空");
                return;
            }
            dto.setPrice(str_price);
        }
        if (area.getVisibility() == View.VISIBLE) {
            if (TextUtils.isEmpty(cityIds)) {
                showToast("区域不能为空");
                return;
            }
        }
        dto.setAreaIds(cityIds);

        releaseInfo(dto);
    }

    /**
     * 分类文件
     *
     * @param articleType
     * @return
     */
    private String initCategory(String articleType) {
        String filename = "";
        ll_name.setVisibility(View.VISIBLE);
        ll_category.setVisibility(View.VISIBLE);
        house.setVisibility(View.GONE);
        prices.setVisibility(View.GONE);
        areas.setVisibility(View.GONE);
        switch (articleType) {
            case ReleaseInfoCategoryFragment.ITEM_IMG_ABS:
                ll_name.setVisibility(View.GONE);
                ll_category.setVisibility(View.GONE);
                break;
            case ReleaseInfoCategoryFragment.ITEM_IMG_LAW:
                filename = HomeFragment.ITEM_IMG_LAW;
                break;
            case ReleaseInfoCategoryFragment.ITEM_IMG_HOUSE:
                filename = HomeFragment.ITEM_IMG_HOUSE;
                house.setVisibility(View.VISIBLE);
                prices.setVisibility(View.VISIBLE);
                break;
            case ReleaseInfoCategoryFragment.ITEM_TITLE_RECRUIT:
                filename = HomeFragment.ITEM_TITLE_RECRUIT;
                prices.setVisibility(View.VISIBLE);
                price_mark.setText("薪资");
                break;
            case ReleaseInfoCategoryFragment.ITEM_IMG_BUSINESS_SERVICE:
                filename = HomeFragment.ITEM_IMG_BUSINESS_SERVICE;
                break;
            case ReleaseInfoCategoryFragment.ITEM_IMG_BUILDING_USED:
                filename = HomeFragment.ITEM_IMG_BUILDING_USED;
                prices.setVisibility(View.VISIBLE);
                break;
            case ReleaseInfoCategoryFragment.ITEM_IMG_INSURANCE:
                filename = HomeFragment.ITEM_IMG_INSURANCE;
                break;
            case ReleaseInfoCategoryFragment.ITEM_IMG_EQUIPMENT_USED:
                filename = HomeFragment.ITEM_IMG_EQUIPMENT_USED;
                prices.setVisibility(View.VISIBLE);
                break;
            case ReleaseInfoCategoryFragment.ITEM_IMG_ODDJOB:
                filename = HomeFragment.ITEM_IMG_ODDJOB;
                break;
            case ReleaseInfoCategoryFragment.ITEM_IMG_ARCHITECT:
                filename = HomeFragment.ITEM_IMG_ARCHITECT;
                areas.setVisibility(View.VISIBLE);
                break;
            case ReleaseInfoCategoryFragment.ITEM_TITLE_MIGRANT_WORKER:
                filename = HomeFragment.ITEM_TITLE_MIGRANT_WORKER;
                break;
            case ReleaseInfoCategoryFragment.ITEM_IMG_CONVENIENCE:
                filename = HomeFragment.ITEM_IMG_CONVENIENCE;
                prices.setVisibility(View.VISIBLE);
                break;
        }
        filename = filename + ".txt";
        return filename;
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
                    if (type.equals(HEADER)) {
                        upload_head = str_key;
                        Glide.with(InfoReleaseActivity.this).load(filePath).asBitmap().into(img);
                    } else if (type.equals(LIST)) {
                        upload_pics.add(str_key);
                        photos.add(filePath);
                        pic_num.setText(photos.size() + "/5");
                        picAdapter.setData(photos);
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

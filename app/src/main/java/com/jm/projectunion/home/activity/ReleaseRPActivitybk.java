package com.jm.projectunion.home.activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.common.utils.FileUtils;
import com.jm.projectunion.common.utils.StringUtils;
import com.jm.projectunion.wiget.popupwindow.BottomCancelPopupWindow;
import com.jm.projectunion.wiget.popupwindow.OnItemClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by YangPan on 2017/11/10.
 * <p>
 * 发布红包
 */

public class ReleaseRPActivitybk extends BaseTitleActivity {

    private static final String PIC_A = "pic_a";
    private static final int PIC_A_PHOTO_REQUEST = 10;
    private static final int PIC_A_CAMERA_REQUEST = 11;
    private static final String PIC_A_NAME = FileUtils.getImagePath() + File.separator + "pica,jpg";

    private static final String PIC_B = "pic_b";
    private static final int PIC_B_PHOTO_REQUEST = 20;
    private static final int PIC_B_CAMERA_REQUEST = 21;
    private static final String PIC_B_NAME = FileUtils.getImagePath() + File.separator + "picb,jpg";

    private static final String PIC_C = "pic_c";
    private static final int PIC_C_PHOTO_REQUEST = 30;
    private static final int PIC_C_CAMERA_REQUEST = 31;
    private static final String PIC_C_NAME = FileUtils.getImagePath() + File.separator + "picc,jpg";

    private static final String PIC_D = "pic_d";
    private static final int PIC_D_PHOTO_REQUEST = 40;
    private static final int PIC_D_CAMERA_REQUEST = 41;
    private static final String PIC_D_NAME = FileUtils.getImagePath() + File.separator + "picd,jpg";

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
    @BindView(R.id.pic1)
    ImageView pic1;
    @BindView(R.id.pic2)
    ImageView pic2;
    @BindView(R.id.pic3)
    ImageView pic3;
    @BindView(R.id.pic4)
    ImageView pic4;
    @BindView(R.id.release)
    Button release;

    private String pic_a_str;
    private String pic_b_str;
    private String pic_c_str;
    private String pic_d_str;

    @Override
    public void initView() {
        setTitleText("信息发布");
//        setEnsureText("发布");
//        getEnsureView().setTextColor(getResources().getColor(R.color.text_normal));

        pic1.setOnClickListener(this);
        pic2.setOnClickListener(this);
        pic3.setOnClickListener(this);
        pic4.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_release_redp;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.pic1:
                showPicSel(PIC_A);
                break;
            case R.id.pic2:
                showPicSel(PIC_B);
                break;
            case R.id.pic3:
                showPicSel(PIC_C);
                break;
            case R.id.pic4:
                showPicSel(PIC_D);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PIC_A_CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功
                        File photoFile = new File(PIC_A_NAME);
                        if (photoFile.exists()) {
                            pic_a_str = PIC_A_NAME;
                            pic1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            Glide.with(this).load(photoFile).asBitmap().into(pic1);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case PIC_A_PHOTO_REQUEST:
                if (data != null) {
                    pic_a_str = StringUtils.getPath(this, data);
                    pic1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(this).load(data.getData()).asBitmap().into(pic1);
                }
                break;
            case PIC_B_CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功
                        File photoFile = new File(PIC_B_NAME);
                        if (photoFile.exists()) {
                            pic_b_str = PIC_B_NAME;
                            pic2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            Glide.with(this).load(photoFile).asBitmap().into(pic2);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case PIC_B_PHOTO_REQUEST:
                if (data != null) {
                    pic_b_str = StringUtils.getPath(this, data);
                    pic2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(this).load(data.getData()).asBitmap().into(pic2);
                }
                break;
            case PIC_C_CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功
                        File photoFile = new File(PIC_C_NAME);
                        if (photoFile.exists()) {
                            pic_c_str = PIC_C_NAME;
                            pic3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            Glide.with(this).load(photoFile).asBitmap().into(pic3);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case PIC_C_PHOTO_REQUEST:
                if (data != null) {
                    pic_c_str = StringUtils.getPath(this, data);
                    pic3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(this).load(data.getData()).asBitmap().into(pic3);
                }
                break;
            case PIC_D_CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功
                        File photoFile = new File(PIC_D_NAME);
                        if (photoFile.exists()) {
                            pic_d_str = PIC_D_NAME;
                            pic4.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            Glide.with(this).load(photoFile).asBitmap().into(pic4);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case PIC_D_PHOTO_REQUEST:
                if (data != null) {
                    pic_d_str = StringUtils.getPath(this, data);
                    pic4.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(this).load(data.getData()).asBitmap().into(pic4);
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
                        if (PIC_A.equals(type)) {
                            getPicFromCamera(PIC_A_CAMERA_REQUEST);
                        } else if (PIC_B.equals(type)) {
                            getPicFromCamera(PIC_B_CAMERA_REQUEST);
                        } else if (PIC_C.equals(type)) {
                            getPicFromCamera(PIC_C_CAMERA_REQUEST);
                        } else if (PIC_D.equals(type)) {
                            getPicFromCamera(PIC_D_CAMERA_REQUEST);
                        }
                        break;
                    case 1:
                        if (PIC_A.equals(type)) {
                            getPicFromPhoto(PIC_A_PHOTO_REQUEST);
                        } else if (PIC_B.equals(type)) {
                            getPicFromPhoto(PIC_B_PHOTO_REQUEST);
                        } else if (PIC_C.equals(type)) {
                            getPicFromPhoto(PIC_C_PHOTO_REQUEST);
                        } else if (PIC_D.equals(type)) {
                            getPicFromPhoto(PIC_D_PHOTO_REQUEST);
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
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, type);
    }

    /**
     * 打开相机
     */
    private void getPicFromCamera(int type) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        if (PIC_A_CAMERA_REQUEST == type) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(PIC_A_NAME)));
        } else if (PIC_B_CAMERA_REQUEST == type) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(PIC_B_NAME)));
        } else if (PIC_C_CAMERA_REQUEST == type) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(PIC_C_NAME)));
        } else if (PIC_D_CAMERA_REQUEST == type) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(PIC_D_NAME)));
        }
        startActivityForResult(intent, type);
    }
}

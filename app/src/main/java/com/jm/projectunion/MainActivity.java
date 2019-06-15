package com.jm.projectunion;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.EMLog;
import com.jm.projectunion.common.base.BaseActivity;
import com.jm.projectunion.common.manager.AppManager;
import com.jm.projectunion.common.runtimepermissions.PermissionsManager;
import com.jm.projectunion.common.runtimepermissions.PermissionsResultAction;
import com.jm.projectunion.common.utils.AppInfoUtils;
import com.jm.projectunion.common.utils.DialogUtils;
import com.jm.projectunion.common.utils.EncryptUtil;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.common.widget.NoSlideViewPager;
import com.jm.projectunion.dao.AreaDaoUtil;
import com.jm.projectunion.dao.bean.AreaBean;
import com.jm.projectunion.dao.entity.AreaResult;
import com.jm.projectunion.entity.Result;
import com.jm.projectunion.entity.VersionResult;
import com.jm.projectunion.eventbus.IMLoginOutEvent;
import com.jm.projectunion.friends.FriendFragment;
import com.jm.projectunion.home.HomeFragment;
import com.jm.projectunion.information.activity.UserInfoEditActivity;
import com.jm.projectunion.location.LocationService;
import com.jm.projectunion.message.EaseConversationListFragment;
import com.jm.projectunion.message.entity.MsgSystemResult;
import com.jm.projectunion.mine.MineFragment;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;
import com.jm.projectunion.wiget.DialogConfirmView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static boolean isForeground = false;

    @BindView(R.id.ixt_viewpager)
    NoSlideViewPager mViewpager;
    @BindView(R.id.home)
    TextView home;
    @BindView(R.id.message)
    TextView message;
    @BindView(R.id.info)
    TextView info;
    @BindView(R.id.friends)
    TextView friends;
    @BindView(R.id.mine)
    TextView mine;
    @BindView(R.id.loading)
    RelativeLayout loading;
    @BindView(R.id.loading_pic)
    ImageView loading_pic;

    private HomeFragment homeFragment;
    private EaseConversationListFragment messageFragment;
    private FriendFragment friendFragment;
    private MineFragment mineFragment;

    private ArrayList<Fragment> mFragmentList;
    private MainFragmentAdapter adapter;

    private Dialog dialog;
    private DialogConfirmView dialogConfirmView;
    private MessageReceiver mMessageReceiver;

    private LocationService locationService;
    private PrefUtils prefUtils;
    private AreaDaoUtil daoUtil;
    private boolean isLocation = true;
    private long mLastClickTime = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loading.setVisibility(View.GONE);
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        initPermission();

        daoUtil = new AreaDaoUtil(this);
        prefUtils = PrefUtils.getInstance(this);
        prefUtils.setFirst(false);

        mFragmentList = new ArrayList<Fragment>();
        homeFragment = new HomeFragment();
        friendFragment = new FriendFragment();
        messageFragment = new EaseConversationListFragment();
        mineFragment = new MineFragment();

        mFragmentList.add(homeFragment);
        mFragmentList.add(messageFragment);
        mFragmentList.add(friendFragment);
        mFragmentList.add(mineFragment);

        adapter = new MainFragmentAdapter(getSupportFragmentManager(), mFragmentList);
        mViewpager.setAdapter(adapter);
        mViewpager.setCurrentItem(0);
        mViewpager.setOffscreenPageLimit(mFragmentList.size());

        selectIndex(R.id.home);
        home.setOnClickListener(this);
        message.setOnClickListener(this);
        friends.setOnClickListener(this);
        mine.setOnClickListener(this);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiGoto.startAty(MainActivity.this, UserInfoEditActivity.class);
            }
        });
        registerMessageReceiver();
    }

    @Override
    public void initData() {

        // -----------location config ------------
        locationService = ((MyApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();

        loginIM(prefUtils.getUserInfo().getPhone());
        checkVersion();
        if (null == daoUtil.queryAll() || daoUtil.queryAll().size() <= 0) {
            //TODO wfx修改 这里 隐藏加载中的图片
//            loading.setVisibility(View.VISIBLE);
//            Glide.with(this).load(R.drawable.loading).asGif().diskCacheStrategy(DiskCacheStrategy.ALL).into(loading_pic);
            getAreaData();
        }



    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        isForeground = true;
        locationService.start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        selectIndex(v.getId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (100 == resultCode && null != data) {
            String city = data.getStringExtra("city");
//            String cityId = data.getStringExtra("cityIds");
            //TODO wfx修改  增加判断
            if (!TextUtils.isEmpty(city)) {
                prefUtils.setKeyLocationCity(city);
                homeFragment.setLocation(city);
            }
        }
    }

    /**
     * 选项卡切换
     */
    private void selectIndex(int id) {
        home.setTextColor(getResources().getColor(R.color.text_normal));
        message.setTextColor(getResources().getColor(R.color.text_normal));
        friends.setTextColor(getResources().getColor(R.color.text_normal));
        mine.setTextColor(getResources().getColor(R.color.text_normal));

        Drawable drawable001 = this.getResources().getDrawable(R.drawable.home1);
        drawable001.setBounds(0,0,drawable001.getMinimumWidth(),drawable001.getMinimumHeight());
        home.setCompoundDrawables(null,drawable001,null,null);

        Drawable drawable002 = this.getResources().getDrawable(R.drawable.msg1);
        drawable002.setBounds(0,0,drawable002.getMinimumWidth(),drawable002.getMinimumHeight());
        message.setCompoundDrawables(null,drawable002,null,null);

        Drawable drawable003 = this.getResources().getDrawable(R.drawable.friend1);
        drawable003.setBounds(0,0,drawable003.getMinimumWidth(),drawable003.getMinimumHeight());
        friends.setCompoundDrawables(null,drawable003,null,null);

        Drawable drawable004 = this.getResources().getDrawable(R.drawable.my1);
        drawable004.setBounds(0,0,drawable004.getMinimumWidth(),drawable004.getMinimumHeight());
        mine.setCompoundDrawables(null,drawable004,null,null);

        switch (id) {
            case R.id.home:
                mViewpager.setCurrentItem(0);
                home.setTextColor(getResources().getColor(R.color.text_selected));
                Drawable drawable = this.getResources().getDrawable(R.drawable.home2);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                home.setCompoundDrawables(null,drawable,null,null);
                break;
            case R.id.message:
                mViewpager.setCurrentItem(1);
                message.setTextColor(getResources().getColor(R.color.text_selected));
                Drawable drawable1 = this.getResources().getDrawable(R.drawable.msg2);
                drawable1.setBounds(0,0,drawable1.getMinimumWidth(),drawable1.getMinimumHeight());
                message.setCompoundDrawables(null,drawable1,null,null);
                break;
            case R.id.friends:
                mViewpager.setCurrentItem(2);
                friends.setTextColor(getResources().getColor(R.color.text_selected));
                Drawable drawable3 = this.getResources().getDrawable(R.drawable.friend2);
                drawable3.setBounds(0,0,drawable3.getMinimumWidth(),drawable3.getMinimumHeight());
                friends.setCompoundDrawables(null,drawable3,null,null);
                break;
            case R.id.mine:
                mViewpager.setCurrentItem(3);
                mine.setTextColor(getResources().getColor(R.color.text_selected));
                Drawable drawable4 = this.getResources().getDrawable(R.drawable.my2);
                drawable4.setBounds(0,0,drawable4.getMinimumWidth(),drawable4.getMinimumHeight());
                mine.setCompoundDrawables(null,drawable4,null,null);
                break;
        }
    }

    /**
     * 初始化权限申请
     */
    public void initPermission() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
//                ToastUtils.showShort(MainActivity.this, "权限 " + permission + " 被阻止");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isSingalClick(2000)) {
                setCostomMsg("再按一次退出程序");
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出应用
     */
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onMessageEvent(IMLoginOutEvent event) {
//        PrefUtils.getInstance(this).clear();
//        EMClient.getInstance().logout(true);
        clearLoginStatu();
        //wfx 修改  暂时不清除 PrefUtils 中的数据
        PrefUtils.getInstance(MainActivity.this).clear();
        EMClient.getInstance().logout(true);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        AppManager.getInstance().finishAllExcept(LoginActivity.class);
    }

    public boolean isSingalClick(int interval) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < interval) {
            return false;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        return true;
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    /**
     * 被踢清除登录状态
     */
    private void clearLoginStatu() {
        ApiClient.quitlogin(this, new ResultCallback<Result>() {
            @Override
            public void onSuccess(Result response) {
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 检查版本
     */
    private void checkVersion() {
        final VersionResult.VersionBean bean = prefUtils.getVersion();
        if (null != bean) {
            if (AppInfoUtils.getAppVersionCode(MainActivity.this) < (Math.round(Float.valueOf(bean.getVersion())))) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("版本升级");
                alertDialogBuilder.setMessage("APP版本更新");
                if (!"1".equals(bean.getIsForce())) {
                    alertDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                }
                alertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(bean.getUrl());
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = alertDialogBuilder.create();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();//将dialog显示出来
            }
        }
    }

    private void loginIM(String user) {
        if (EMClient.getInstance().isLoggedInBefore() && EMClient.getInstance().isConnected()) {
            return;
        }
        String pwd = EncryptUtil.getMD5("HX" + user);
        EMClient.getInstance().login(user, pwd, new EMCallBack() {

            @Override
            public void onSuccess() {
                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                System.out.println("IM登陆--success");
            }

            @Override
            public void onProgress(int progress, String status) {
                System.out.println("IM登陆---loading");
            }

            @Override
            public void onError(final int code, final String message) {
                System.out.println("IM登陆---error" + message);
            }
        });
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    Gson gson = new Gson();
                    MsgSystemResult.MsgSystemBean bean = gson.fromJson(extras, MsgSystemResult.MsgSystemBean.class);
                    if ("1".equals(bean.getType())) {
//                        setCostomMsg("节日祝福");
                        dialogConfirmView = DialogConfirmView.newInstance(MainActivity.this);
                        View view = dialogConfirmView.create("节日祝福", bean.getContent());
                        dialogConfirmView.setPositiveClickListener(new DialogConfirmView.onPositiveClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (null != dialog) {
                                    dialog.dismiss();
                                }
                            }
                        });
                        dialog = DialogUtils.showCustomDialog(MainActivity.this, view, false);
                    } else if ("2".equals(bean.getType())) {
                        messageFragment.setMsgmark(0);
                    } else if ("3".equals(bean.getType())) {
                        messageFragment.setMsgmark(1);
                    } else if ("4".equals(bean.getType())) {
                        messageFragment.setMsgmark(2);
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    private void setCostomMsg(String msg) {
        //消息界面提示
        ToastUtils.showShort(this, msg);
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(final BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                if (isLocation) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            homeFragment.setLocation(location.getCity());
                        }
                    });
                    String city = location.getCity();
                    //TODO wfx修改  增加判断
                    if (!TextUtils.isEmpty(city)) {
                        prefUtils.setKeyLocationCity(city);
                    }
                    String province = location.getProvince();
                    prefUtils.setKeyLocationProvince(province);
                    String address = location.getProvince();
                    if (!address.equals(location.getCity())) {
                        address += location.getCity();
                    }
                    prefUtils.setKeyLocationAddress(address + location.getDistrict());
                    prefUtils.setKeyLocationLat(location.getLatitude());//纬度
                    prefUtils.setKeyLocationLong(location.getLongitude());//经度
                    locationService.stop();
                }
                isLocation = false;
            }
        }

        public void onConnectHotSpotMessage(String s, int i) {
//            Toast.makeText(getBaseContext(),"执行了 mListener  定位2",Toast.LENGTH_LONG).show();
        }
    };

    /**
     * 获取区域列表
     */
    private void getAreaData() {
        ApiClient.getArea(this, new ResultCallback<AreaResult>() {
            @Override
            public void onSuccess(final AreaResult response) {
                System.out.println("result-areslit:" + response.toString());
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        new Thread() {
                            @Override
                            public void run() {
                                for (AreaBean bean : response.getData()) {
                                    daoUtil.addItem(bean);
                                }
                                handler.sendEmptyMessage(0);
                            }
                        }.start();
                    }
                    ToastUtils.showShort(MainActivity.this, response.getMsg());
                }
            }

            @Override
            public void onError(String msg) {
                ToastUtils.showShort(MainActivity.this, msg);
            }
        });
    }

}

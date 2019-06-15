package com.jm.projectunion.utils.api;

import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jm.projectunion.common.manager.Config;
import com.jm.projectunion.common.utils.AppInfoUtils;
import com.jm.projectunion.common.utils.Base64Utils;
import com.jm.projectunion.common.utils.DeviceUtil;
import com.jm.projectunion.common.utils.NetworkUtil;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.dao.entity.AreaResult;
import com.jm.projectunion.entity.LoginOrRegistResult;
import com.jm.projectunion.entity.Result;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.entity.VersionResult;
import com.jm.projectunion.friends.bean.FriendsResult;
import com.jm.projectunion.home.entiy.BannerResult;
import com.jm.projectunion.home.entiy.FuncDetailResult;
import com.jm.projectunion.home.entiy.FuncListResult;
import com.jm.projectunion.home.dto.ListDto;
import com.jm.projectunion.home.dto.ReleaseInfoDto;
import com.jm.projectunion.home.entiy.InfoDetailsResult;
import com.jm.projectunion.home.entiy.InfoListResult;
import com.jm.projectunion.home.entiy.ProjectDetailResult;
import com.jm.projectunion.home.entiy.RedPacketResult;
import com.jm.projectunion.home.entiy.ServiceResult;
import com.jm.projectunion.information.dto.EnterpriseInfoDto;
import com.jm.projectunion.information.entity.EnterpriseInfoDetailResult;
import com.jm.projectunion.information.entity.UserInfoDetailResult;
import com.jm.projectunion.message.entity.MsgSystemResult;
import com.jm.projectunion.mine.dto.OrderDto;
import com.jm.projectunion.mine.entity.BanksResult;
import com.jm.projectunion.mine.entity.CollectResult;
import com.jm.projectunion.mine.entity.IncomeListResult;
import com.jm.projectunion.mine.entity.RPHisResult;
import com.jm.projectunion.mine.entity.UserInfoResult;
import com.jm.projectunion.mine.entity.VipTypeResult;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by yunzhao.liu on 2016/12/5
 */

public class ApiClient {

    private static final String CMS_RELEASE_INFO = "cms/C001";//发布信息
    private static final String CMS_INFO_DETAIL = "cms/C002";//查看信息详情（除去项目和红包）
    private static final String CMS_INFO_LIST = "cms/C003";//查看信息列表
    private static final String CMS_RELEASE_RP = "cms/C004";//发布红包信息
    //    private static final String CMS_RP_LIST = "cms/C005";//红包列表信息
    private static final String CMS_RP_DETAIL = "cms/C006";//红包详情
    private static final String CMS_RELEASE_PROJECT = "cms/C007";//发布项目信息
    //    private static final String CMS_PROJECT_LIST = "cms/C008";//项目信息列表
    private static final String CMS_PROJECT_DETAIL = "cms/C009";//项目详情
    private static final String CMS_COLLECT_INFO = "cms/C012";//收藏信息
    private static final String CMS_GP_RP = "cms/C013";//抢红包
    private static final String CMS_BANNER_LIST = "cms/C014";//banner
    private static final String CMS_RELESE_HIS = "cms/C015";//查询发布记录
    private static final String CMS_RELEASE_INFO_DEL = "cms/C016";//删除发布记录

    private static final String COMMON_SMS_CODE = "common/P001";//发送验证码
    private static final String COMMON_AREA = "common/P002";//获取所有区域信息
    private static final String COMMON_SERVICE = "common/P004";//获取所有服务信息
    private static final String COMMON_FUN_LIST = "common/P005";//获取功能列表
    private static final String COMMON_FUN_DETAIL = "common/P006";//获取功能详情
    private static final String COMMON_SHARE = "common/P007";//获取分享链接
    private static final String COMMON_APP_VERSION = "common/P008";//版本更新
    private static final String COMMON_MESSAGE = "common/P009";//推送消息历史

    private static final String REGIST = "user/R001";
    private static final String LOGIN = "user/R002";
    private static final String USER_SAVE = "user/R003";//用户信息保存
    private static final String QUIT_LOGIN = "user/R004";
    private static final String USER_INFO = "user/R005";//获取用户信息
    private static final String USER_ENTERPRISE_INFO = "user/R011";//根据用户ID获取企业详情(获取自己的企业信息)
    private static final String USER_ENTERPRISE_O_INFO = "user/R012";//获取企业详情(获取其他企业信息)
    private static final String ENTERPRISE_INFO = "user/R006";//保存企业信息
    private static final String QINIU_TOKEN = "user/R008";//获取七牛凭证

    private static final String USER_CENTER_INFO = "usercenter/U001";//  个人中心主页信息
    private static final String USER_CENTER_PWD_CODE = "usercenter/U002";//  修改支付/登陆密码
    private static final String USER_CENTER_PWD = "usercenter/U003";//  修改登陆密码无需验证码
    private static final String USER_CENTER_FRIENDS = "usercenter/U004";//  好友列表
    //    private static final String USER_CENTER_RECHARGE = "usercenter/U005";//  充值
    private static final String USER_CENTER_BANKS = "usercenter/U007";//  我的银行卡片
    private static final String USER_CENTER_UNBANK = "usercenter/U008";// 银行卡片解绑
    private static final String USER_CENTER_INCOME = "usercenter/U009";// 收支明细
    private static final String USER_CENTER_DEL_INCOME = "usercenter/U018";// 收支明细删除
    private static final String USER_CENTER_VIP = "usercenter/U010";//会员类型
    private static final String USER_CENTER_LEAGUER = "usercenter/U011";//会员购买
    private static final String USER_CENTER_COLLECT = "usercenter/U012";//收藏
    private static final String USER_CENTER_GRAPRP = "usercenter/U013";//抢红包记录
    private static final String USER_CENTER_ADDFRIEND = "usercenter/U015";//添加好友
    private static final String USER_CENTER_ADDBANK = "usercenter/U017";//添加银行卡
    private static final String USER_CENTER_DELRPHIS = "usercenter/U019";//删除红包记录
    private static final String USER_CENTER_DELCOLLECTION = "usercenter/U021";//删除收藏记录

    private static final String USER_CENTER_ISTRUECARD = "usercenter/U00171";//判断身份证是否有效
    private static final String USER_CENTER_ADDUSERIDENTITYCARD = "usercenter/U0017";//U0017   添加或更新身份证
    private static final String USER_CENTER_ADDCARDSTAT = "usercenter/U00172";//根据ID获取修改身份证状态

    private static final String PAY = "pay/Z001";//支付订单
    private static final String USER_CENTER_CASH = "pay/Z002";//  提现

    /**
     * 发布信息
     */
    public static void releseInfo(Activity activity, ReleaseInfoDto dto, ResultCallback<Result> callback) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("body", gson.toJson(dto));
        post(activity, CMS_RELEASE_INFO, params, Result.class, callback);
    }

    /**
     * 获取信息详情（除项目及红包）
     *
     * @param activity
     * @param callback
     */
    public static void getInfoDetails(Activity activity, String infoId, String bannerType, ResultCallback<InfoDetailsResult> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("id", infoId);
        params.put("htTop", bannerType);
        post(activity, CMS_INFO_DETAIL, params, InfoDetailsResult.class, callback);
    }

    /**
     * 获取信息列表（除项目及红包）
     *
     * @param activity
     * @param callback
     */
    public static void getInfoList(Activity activity, ListDto dto, ResultCallback<InfoListResult> callback) {
        dto.setUserId(PrefUtils.getInstance(activity).getUserInfo().getUserId());
        Map<String, String> params = new HashMap<>();
        Gson gson = new Gson();
        params.put("body", gson.toJson(dto));
        post(activity, CMS_INFO_LIST, params, InfoListResult.class, callback);
    }

    /**
     * 发布红包信息
     *
     * @param activity
     * @param dto
     * @param callback
     */
    public static void releaseRP(Activity activity, RedPacketResult.RedPacketBean dto, ResultCallback<ResultData> callback) {
        Map<String, String> params = new HashMap<>();
        Gson gson = new Gson();
        params.put("body", gson.toJson(dto));
        post(activity, CMS_RELEASE_RP, params, ResultData.class, callback);
    }

    /**
     * 获取红包详情
     *
     * @param activity
     * @param id
     * @param callback
     */
    public static void getRPDetail(Activity activity, String id, ResultCallback<RedPacketResult> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        post(activity, CMS_RP_DETAIL, params, RedPacketResult.class, callback);
    }

    /**
     * 项目详情
     *
     * @param activity
     * @param projectId
     * @param callback
     */
    public static void getProjectDetail(Activity activity, String projectId, ResultCallback<ProjectDetailResult> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("id", projectId);
        post(activity, CMS_PROJECT_DETAIL, params, ProjectDetailResult.class, callback);
    }

    /**
     * 收藏信息
     *
     * @param activity
     * @param bean
     * @param callback
     */
    public static void collectInfo(Activity activity, CollectResult.CollectBean bean, ResultCallback<InfoDetailsResult> callback) {
        bean.setUserId(PrefUtils.getInstance(activity).getUserInfo().getUserId());
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("body", gson.toJson(bean));
        post(activity, CMS_COLLECT_INFO, params, InfoDetailsResult.class, callback);
    }

    /**
     * 抢红包
     * userId *用户ID
     * redPacketId *红包
     * title *标题
     * money *金额
     *
     * @param activity
     * @param callback
     */
    public static void gpRedPack(Activity activity, String redPacketId, String title, String money, ResultCallback<ResultData> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", PrefUtils.getInstance(activity).getUserInfo().getUserId());
        params.put("redPacketId", redPacketId);
        params.put("title", title);
        params.put("money", money);
        post(activity, CMS_GP_RP, params, ResultData.class, callback);
    }

    /**
     * 获取banner列表
     *
     * @param activity
     * @param callback
     */
    public static void getBanners(Activity activity, ResultCallback<BannerResult> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", PrefUtils.getInstance(activity).getUserInfo().getUserId());
        post(activity, CMS_BANNER_LIST, params, BannerResult.class, callback);
    }

    /**
     * 获取发布记录
     * <p>
     * lastId *列表最后一个Id
     * num *查询数量
     * order 顺序发布时间，默认0，0：倒序 ，1:正序
     * type *类型：1首页置顶，2红包，3交易市场
     *
     * @param activity
     * @param callback
     */
    public static void getReleaseHit(Activity activity, String type, String lastId, String num, String order, ResultCallback<InfoListResult> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", PrefUtils.getInstance(activity).getUserInfo().getUserId());
        params.put("lastId", lastId);
        params.put("num", num);
        params.put("order", order);
        params.put("type", type);
        post(activity, CMS_RELESE_HIS, params, InfoListResult.class, callback);
    }

    /**
     * 删除发布记录
     *
     * @param activity
     * @param id
     * @param callback
     */
    public static void delReleaseInfo(Activity activity, String id, ResultCallback<Result> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        post(activity, CMS_RELEASE_INFO_DEL, params, Result.class, callback);
    }

    /**
     * 发布项目信息
     *
     * @param activity
     * @param dto
     * @param callback
     */
    public static void releseProjectInfo(Activity activity, ProjectDetailResult.ProjectBean dto, ResultCallback<Result> callback) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("body", gson.toJson(dto));
        post(activity, CMS_RELEASE_PROJECT, params, Result.class, callback);
    }

    /**
     * 获取验证码
     */
    public static void smsCode(Activity activity, Map<String, String> params, ResultCallback<Result> callback) {
        post(activity, COMMON_SMS_CODE, params, Result.class, callback);
    }

    /**
     * 获取所有区域信息
     *
     * @param activity
     * @param callback
     */
    public static void getArea(Activity activity, ResultCallback<AreaResult> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("userid", PrefUtils.getInstance(activity).getUserInfo().getUserId());
        post(activity, COMMON_AREA, params, AreaResult.class, callback);
    }

    /**
     * 获取所有服务信息
     *
     * @param activity
     * @param callback
     */
    public static void getService(Activity activity, String type, String cid, ResultCallback<ServiceResult> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        params.put("cid", cid);
        post(activity, COMMON_SERVICE, params, ServiceResult.class, callback);
    }

    /**
     * 获取功能列表
     *
     * @param activity
     * @param callback
     */
    public static void getFuncList(Activity activity, ResultCallback<FuncListResult> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", PrefUtils.getInstance(activity).getUserInfo().getUserId());
        post(activity, COMMON_FUN_LIST, params, FuncListResult.class, callback);
    }

    /**
     * 功能详情
     *
     * @param activity
     * @param callback
     */
    public static void getFuncDetail(Activity activity, String id, ResultCallback<FuncDetailResult> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        post(activity, COMMON_FUN_DETAIL, params, FuncDetailResult.class, callback);
    }

    /**
     * 获取分享链接
     *
     * @param activity
     * @param callback
     */
    public static void getShare(Activity activity, ResultCallback<ResultData> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", PrefUtils.getInstance(activity).getUserInfo().getPhone());
        post(activity, COMMON_SHARE, params, ResultData.class, callback);
    }

    /**
     * 获取版本更新
     *
     * @param activity
     * @param callback
     */
    public static void getAppVersion(Activity activity, ResultCallback<VersionResult> callback) {
        Map<String, String> params = new HashMap<>();
//        params.put("userId", PrefUtils.getInstance(activity).getUserInfo().getUserId());
        post(activity, COMMON_APP_VERSION, params, VersionResult.class, callback);
    }

    /**
     * 获取推送消息
     *
     * @param activity
     * @param type     1节日祝福，2系统消息，3行业动态，4常用数据
     * @param callback
     */
    public static void getMessage(Activity activity, String type, ResultCallback<MsgSystemResult> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        post(activity, COMMON_MESSAGE, params, MsgSystemResult.class, callback);
    }

    /**
     * 注册
     */
    public static void regist(Activity activity, Map<String, String> params, ResultCallback<LoginOrRegistResult> callBack) {
        post(activity, REGIST, params, LoginOrRegistResult.class, callBack);
    }

    /**
     * 登陆
     */
    public static void login(Activity activity, Map<String, String> params, ResultCallback<LoginOrRegistResult> callBack) {
        post(activity, LOGIN, params, LoginOrRegistResult.class, callBack);
    }

    /**
     * 退出登陆
     */
    public static void quitlogin(Activity activity, ResultCallback<Result> callBack) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", PrefUtils.getInstance(activity).getUserInfo().getUserId());//OKHttp bug没用占位
        post(activity, QUIT_LOGIN, params, Result.class, callBack);
    }

    /**
     * 获取用户详细信息
     *
     * @param activity
     * @param callBack
     */
    public static void getUserInfoDetail(Activity activity, String userId, ResultCallback<UserInfoDetailResult> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("id", userId);
//        params.put("id", "1");
        post(activity, USER_INFO, params, UserInfoDetailResult.class, callBack);
    }

    /**
     * 获取企业详情（自己企业）
     *
     * @param activity
     * @param callBack
     */
    public static void getEnterpriseInfoDetail(Activity activity, ResultCallback<EnterpriseInfoDetailResult> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("id", PrefUtils.getInstance(activity).getUserInfo().getUserId());
        post(activity, USER_ENTERPRISE_INFO, params, EnterpriseInfoDetailResult.class, callBack);
    }

    /**
     * 获取企业详情(他人企业)
     *
     * @param activity
     * @param id
     * @param callBack
     */
    public static void getEnterpriseInfoDetail(Activity activity, String id, ResultCallback<EnterpriseInfoDetailResult> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        post(activity, USER_ENTERPRISE_O_INFO, params, EnterpriseInfoDetailResult.class, callBack);
    }

    /**
     * 获取七牛凭证
     *
     * @param activity
     * @param callBack
     */
    public static void getQNToke(Activity activity, ResultCallback<ResultData> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("type", "image");
        post(activity, QINIU_TOKEN, params, ResultData.class, callBack);
    }

    /**
     * 保存用户信息
     */
    public static void saveUserInfoDetail(Activity activity, UserInfoDetailResult.UserInfoDetailBean bean, ResultCallback<ResultData> callBack) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("body", gson.toJson(bean));
        post(activity, USER_SAVE, params, ResultData.class, callBack);
    }

    /**
     * 保存企业信息
     *
     * @param activity
     * @param dto
     * @param callBack
     */
    public static void saveEnterInfo(Activity activity, EnterpriseInfoDto dto, ResultCallback<ResultData> callBack) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("body", gson.toJson(dto));
        post(activity, ENTERPRISE_INFO, params, ResultData.class, callBack);
    }

    /**
     * 获取个人中心信息
     *
     * @param activity
     * @param callBack
     */
    public static void getUserInfo(Activity activity, ResultCallback<UserInfoResult> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("id", PrefUtils.getInstance(activity).getUserInfo().getUserId());
//        params.put("id", "1");
        post(activity, USER_CENTER_INFO, params, UserInfoResult.class, callBack);
    }

    /**
     * 判断身份是否有效
     *
     * @param activity
     * @param callBack
     */
    public static void isTureCard(Activity activity, ResultCallback<ResultData> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", PrefUtils.getInstance(activity).getUserInfo().getUserId());
//        params.put("id", "1");
        post(activity, USER_CENTER_ISTRUECARD, params, ResultData.class, callBack);
    }

    /**
     * 判断身份是否有效
     *
     * @param activity
     * @param callBack
     */
    public static void addCardStat(Activity activity, ResultCallback<ResultData> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", PrefUtils.getInstance(activity).getUserInfo().getUserId());
//        params.put("id", "1");
        post(activity, USER_CENTER_ADDCARDSTAT, params, ResultData.class, callBack);
    }

    /**
     * 获取个人中心信息
     *
     * @param activity
     * @param callBack
     */
    public static void addUserIdentityCard(Activity activity, BanksResult.BankBean bean, ResultCallback<ResultData> callBack) {
        Map<String, String> params = new HashMap<>();
        Gson gson = new Gson();
        params.put("body", gson.toJson(bean));
        post(activity, USER_CENTER_ADDUSERIDENTITYCARD, params, ResultData.class, callBack);
    }

    /**
     * 忘记密码/修改支付密码
     *
     * @param activity
     * @param params
     * @param callBack
     */
    public static void modifyPayPWD(Activity activity, Map<String, String> params, ResultCallback<Result> callBack) {
        post(activity, USER_CENTER_PWD_CODE, params, Result.class, callBack);
    }

    /**
     * 修改登陆密码
     *
     * @param activity
     * @param params
     * @param callBack
     */
    public static void modifyPWD(Activity activity, Map<String, String> params, ResultCallback<Result> callBack) {
        post(activity, USER_CENTER_PWD, params, Result.class, callBack);
    }

    /**
     * 获取好友列表
     *
     * @param activity
     * @param type     *类型：0：好友，1：一度好友
     * @param callBack
     */
    public static void getFriends(Activity activity, String type, ResultCallback<FriendsResult> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", PrefUtils.getInstance(activity).getUserInfo().getUserId());
        params.put("type", type);
        post(activity, USER_CENTER_FRIENDS, params, FriendsResult.class, callBack);
    }

    /**
     * 提现
     *
     * @param activity
     * @param params
     * @param callBack
     */
    public static void submitCrash(Activity activity, Map<String, String> params, ResultCallback<ResultData> callBack) {
        post(activity, USER_CENTER_CASH, params, ResultData.class, callBack);
    }

    /**
     * 获取银行卡列表
     *
     * @param activity
     * @param callBack
     */
    public static void getBanks(Activity activity, ResultCallback<BanksResult> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", PrefUtils.getInstance(activity).getUserInfo().getUserId());
        post(activity, USER_CENTER_BANKS, params, BanksResult.class, callBack);
    }

    /**
     * 解绑银行卡
     *
     * @param activity
     * @param callBack
     */
    public static void delBank(Activity activity, String cardId, ResultCallback<Result> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("id", cardId);
        post(activity, USER_CENTER_UNBANK, params, Result.class, callBack);
    }

    /**
     * 收支明细
     *
     * @param activity
     * @param type     0全部，1收入，2支出
     * @param callBack
     */
    public static void getIncomeDetail(Activity activity, String type, ResultCallback<IncomeListResult> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", PrefUtils.getInstance(activity).getUserInfo().getUserId());
        params.put("type", type);
        post(activity, USER_CENTER_INCOME, params, IncomeListResult.class, callBack);
    }

    /**
     * 删除收支明细
     *
     * @param activity
     * @param accountLogId
     * @param callBack
     */
    public static void delIncomeItem(Activity activity, String accountLogId, ResultCallback<ResultData> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("accountLogId", accountLogId);
        post(activity, USER_CENTER_DEL_INCOME, params, ResultData.class, callBack);
    }

    /**
     * 获取会员类型
     *
     * @param activity
     * @param callBack
     */
    public static void getVipType(Activity activity, ResultCallback<VipTypeResult> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("id", PrefUtils.getInstance(activity).getUserInfo().getUserId());
        post(activity, USER_CENTER_VIP, params, VipTypeResult.class, callBack);
    }

    /**
     * 会员购买
     *
     * @param activity
     * @param vipType  1：高级会员，2：企业会员
     * @param callBack
     */
    public static void buyVip(Activity activity, String vipType, String money, ResultCallback<ResultData> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", PrefUtils.getInstance(activity).getUserInfo().getUserId());
        params.put("type", vipType);
        params.put("money", money);
        post(activity, USER_CENTER_LEAGUER, params, ResultData.class, callBack);
    }

    /**
     * 获取收藏
     *
     * @param activity
     * @param callBack
     */
    public static void getCollect(Activity activity, ResultCallback<CollectResult> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", PrefUtils.getInstance(activity).getUserInfo().getUserId());
        post(activity, USER_CENTER_COLLECT, params, CollectResult.class, callBack);
    }

    /**
     * 添加好友
     *
     * @param activity
     * @param callBack
     */
    public static void addFriend(Activity activity, String phone, ResultCallback<ResultData> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("userId", PrefUtils.getInstance(activity).getUserInfo().getUserId());
        post(activity, USER_CENTER_ADDFRIEND, params, ResultData.class, callBack);
    }

    /**
     * 绑定银行卡
     *
     * @param activity
     * @param callBack
     */
    public static void addBank(Activity activity, BanksResult.BankBean bean, ResultCallback<ResultData> callBack) {
        Map<String, String> params = new HashMap<>();
        Gson gson = new Gson();
        params.put("body", gson.toJson(bean));
        post(activity, USER_CENTER_ADDBANK, params, ResultData.class, callBack);
    }

    /**
     * 红包记录
     *
     * @param activity
     * @param type     　类型：1 抢红包，0 发红包
     * @param callBack
     */
    public static void getRPHis(Activity activity, String type, ResultCallback<RPHisResult> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", PrefUtils.getInstance(activity).getUserInfo().getUserId());
        params.put("type", type);
        post(activity, USER_CENTER_GRAPRP, params, RPHisResult.class, callBack);
    }

    /**
     * 删除红包记录
     *
     * @param activity
     * @param repId
     * @param callBack
     */
    public static void delRPHis(Activity activity, String repId, ResultCallback<Result> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", PrefUtils.getInstance(activity).getUserInfo().getUserId());
        params.put("redLogId", repId);
        post(activity, USER_CENTER_DELRPHIS, params, Result.class, callBack);
    }

    /**
     * 删除收藏记录
     *
     * @param activity
     * @param collectId
     * @param callBack
     */
    public static void delCol(Activity activity, String collectId, ResultCallback<Result> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("collectId", collectId);
        post(activity, USER_CENTER_DELCOLLECTION, params, Result.class, callBack);
    }

    /**
     * 创建订单
     *
     * @param activity
     * @param dto
     * @param callback
     */
    public static void createPay(Activity activity, OrderDto dto, ResultCallback<ResultData> callback) {
        Map<String, String> params = new HashMap<>();
        Gson gson = new Gson();
        params.put("body", gson.toJson(dto));
        post(activity, PAY, params, ResultData.class, callback);
    }

    private static <T> void post(Activity activity, String url, Map<String, String> params, Class clazz, ResultCallback<T> callback) {

        if (!NetworkUtil.isConnected(activity)) {
            callback.onError("网络连接断开，请检查网络");
            return;
        }
        PrefUtils prefUtils = PrefUtils.getInstance(activity);
        LoginOrRegistResult.RegistBean userInfo = prefUtils.getUserInfo();
        ApiCallback apiCallback = new ApiCallback(activity, callback, clazz);

        /**设置Header
         * token string 令牌
         * clientId string 设备编号
         * clientTye string 终端类型：1 IOS,2 Android,3 H5,4 其他
         * clientVersion string 终端系统版本
         * version string  应用版本
         * secret string  加密方式
         * ts string  时间戳
         * sign string 签名
         */
        Map<String, String> headers = new HashMap<>();
        if (null != userInfo) {
            headers.put("token", userInfo.getToken());
        }
        headers.put("clientId", DeviceUtil.getIMEIDeviceId(activity));
        headers.put("clientTye", "2");
        headers.put("clientVersion", DeviceUtil.getAndroidVersion());
        headers.put("version", String.valueOf(AppInfoUtils.getAppVersionCode(activity)));
        headers.put("secret", "0");
        headers.put("ts", String.valueOf(System.currentTimeMillis()));
        headers.put("sign", "");

        Map<String, String> encodParams = new HashMap<>();

        if (null != params) {
            for (String key : params.keySet()) {
                String value = params.get(key);
                if (TextUtils.isEmpty(value)) {
                    continue;
                }
                String baseValue = Base64Utils.encode(value.getBytes());
                System.out.println("key:" + key + ":" + baseValue);
                encodParams.put(key, baseValue);

            }
        }

        OkHttpUtils.post()
                .url(getAbsoluteUrl(url))
                .headers(headers)
                .params(encodParams)
                .build()
                .execute(apiCallback);
    }

    private static <T> void get(Activity activity, String url, Map<String, String> params, Class clazz, ResultCallback<T> callback) {
        if (!NetworkUtil.isConnected(activity)) {
            callback.onError("网络连接断开，请检查网络");
            return;
        }
        PrefUtils prefUtils = PrefUtils.getInstance(activity);
        LoginOrRegistResult.RegistBean userInfo = prefUtils.getUserInfo();
        ApiCallback apiCallback = new ApiCallback(activity, callback, clazz);

        /**设置Header
         * token string 令牌
         * clientId string 设备编号
         * clientTye string 终端类型：1 IOS,2 Android,3 H5,4 其他
         * clientVersion string 终端系统版本
         * version string  应用版本
         * secret string  加密方式
         * ts string  时间戳
         * sign string 签名
         */
        Map<String, String> headers = new HashMap<>();
        if (null != userInfo) {
            headers.put("token", userInfo.getToken());
        }
        headers.put("clientId", DeviceUtil.getIMEIDeviceId(activity));
        headers.put("clientTye", "2");
        headers.put("clientVersion", DeviceUtil.getAndroidVersion());
        headers.put("version", String.valueOf(AppInfoUtils.getAppVersionCode(activity)));
        headers.put("secret", "0");
        headers.put("ts", String.valueOf(System.currentTimeMillis()));
        headers.put("sign", "");

        Map<String, String> encodParams = new HashMap<>();
        if (null != params) {
            for (String key : params.keySet()) {
                String value = params.get(key);
                if (TextUtils.isEmpty(value)) {
                    continue;
                }
                String baseValue = Base64Utils.encode(value.getBytes());
                System.out.println("key:" + key + ":" + baseValue);
                encodParams.put(key, baseValue);
            }
        }

        OkHttpUtils.get()
                .headers(headers)
                .url(getAbsoluteUrl(url))
                .params(encodParams)
                .build()
                .execute(apiCallback);
    }

    /**
     * 获取服务器绝对路径
     *
     * @param relativeUrl 相对路径
     * @return 返回绝对路径地址
     */
    public static String getAbsoluteUrl(String relativeUrl) {
        return Config.BASE_HOST_URL + relativeUrl;
    }
}

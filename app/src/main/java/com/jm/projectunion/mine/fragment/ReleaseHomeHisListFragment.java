package com.jm.projectunion.mine.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseListFragment;
import com.jm.projectunion.common.utils.DialogUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.entity.Result;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.home.activity.DetailsActivity;
import com.jm.projectunion.home.activity.EnterpriseDetailsActivity;
import com.jm.projectunion.home.activity.ProjectDetailActivity;
import com.jm.projectunion.home.activity.RedPDetailActivity;
import com.jm.projectunion.home.adapter.EnterpriseRedpAdapter;
import com.jm.projectunion.home.entiy.InfoListResult;
import com.jm.projectunion.home.entiy.RedPacketResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YangPan on 2017/10/29.
 */

public class ReleaseHomeHisListFragment extends BaseListFragment {

    public static final String TYPE_HOME = "1";
    public static final String TYPE_RP = "2";
    public static final String TYPE_ALL = "3";

    private String type;
    private List<InfoListResult.InfoListBean> data = new ArrayList<>();
    private Dialog dialog;

    @Override
    public void initView(View view) {
        super.initView(view);
        type = getArguments().getString("type");
        dialog = DialogUtils.showLoading(getActivity());
        getData(type, "0");
    }

    @Override
    protected void onLoadMoreDate() {
        if (data.size() > 0) {
            getData(type, data.get(data.size() - 1).getLastId());
        }
    }

    @Override
    protected void onRefreshDate() {
        getData(type, "0");
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        InfoListResult.InfoListBean bean = (InfoListResult.InfoListBean) data;
        if ("88".equals(bean.getArticleType())) {
            //红包
            Bundle bundle = new Bundle();
            bundle.putString("id", bean.getEntityId());
            bundle.putString("receiveStatu", "1");
            bundle.putString("title", bean.getTitle());
            UiGoto.startAtyWithBundle(getActivity(), RedPDetailActivity.class, bundle);
        } else if ("31".equals(bean.getArticleType())) {
            //企业
            Bundle bundle = new Bundle();
            bundle.putString("id", bean.getEntityId());
            UiGoto.startAtyWithBundle(getActivity(), EnterpriseDetailsActivity.class, bundle);
        } else if ("89".equals(bean.getArticleType())) {
            //项目
            Bundle bundle = new Bundle();
            bundle.putString("id", bean.getEntityId());
            UiGoto.startAtyWithBundle(getActivity(), ProjectDetailActivity.class, bundle);
        } else {
            Bundle bundle = new Bundle();
            if ("1".equals(bean.getArticleType())) {
                bundle.putString("type", DetailsActivity.TYPE_BANNER);
            } else {
                bundle.putString("type", DetailsActivity.TYPE_INFO);
            }
            bundle.putString("id", bean.getEntityId());
            bundle.putString("atype", bean.getArticleType());
            UiGoto.startAtyWithBundle(getActivity(), DetailsActivity.class, bundle);
        }
    }

    @Override
    public BaseAdapter createAdapter() {
        /**
         *  if (TYPE_ALL.equals(type)) {
         ToastUtils.showShort(getActivity(), "全部");
         getData("3", "0");
         } else if (TYPE_HOME.equals(type)) {
         ToastUtils.showShort(getActivity(), "置顶");
         getData("1", "0");
         } else if (TYPE_RP.equals(type)) {
         ToastUtils.showShort(getActivity(), "红包");
         getData("2", "0");
         }
         */
        final EnterpriseRedpAdapter adapter = new EnterpriseRedpAdapter(true);
        adapter.setDelOnclicklistener(new EnterpriseRedpAdapter.OnDelClickListener() {
            @Override
            public void onDel(String id, final int position) {
                ApiClient.delReleaseInfo(getActivity(), id, new ResultCallback<Result>() {
                    @Override
                    public void onSuccess(Result response) {
                        if ("0".equals(response.getCode())) {
                            data.remove(position);
                            adapter.replace(data);
                        }
                    }

                    @Override
                    public void onError(String msg) {

                    }
                });
            }
        });
        adapter.setOnAgeClickListener(new EnterpriseRedpAdapter.OnAgeClickListener() {
            private RedPacketResult.RedPacketBean dton;
            @Override
            public void onAge(String id, final int position) {
                ApiClient.getRPDetail(getActivity(), id, new ResultCallback<RedPacketResult>() {
                    @Override
                    public void onSuccess(RedPacketResult response) {
                        dton = response.getData();

                        ApiClient.releaseRP(getActivity(), dton, new ResultCallback<ResultData>() {
                            @Override
                            public void onSuccess(ResultData response) {

                                if (null != response) {
                                    ToastUtils.showShort(getActivity(),response.getMsg());
                                    if ("0".equals(response.getCode())) {
                                        adapter.removeAll();
                                        getData(type, "0");
                                        adapter.replace(data);
                                        ToastUtils.showShort(getActivity(), "成功123");
                                    }
                                }
                                //getActivity().finish();
                            }

                            @Override
                            public void onError(String msg) {
                            }
                        });
                    }

                    @Override
                    public void onError(String msg) {

                    }
                });
            }
        });
        return adapter;
    }

    private void getData(String type, String lastId) {
        this.type = type;
        /**
         * lastId *列表最后一个Id
         * num *查询数量
         * order 顺序发布时间，默认0，0：倒序 ，1:正序
         * type *类型：1首页置顶，2红包，3交易市场
         *
         */
        ApiClient.getReleaseHit(getActivity(), type, lastId, "20", "0", new ResultCallback<InfoListResult>() {
            @Override
            public void onSuccess(InfoListResult response) {
                dialog.dismiss();
                System.out.println("result-his==" + response.toString());
                if (null != response) {
                    ToastUtils.showShort(getActivity(), response.getMsg());
                    if ("0".equals(response.getCode())) {
                        data = response.getData();
                        setDataResult(data);
                    }
                }
            }

            @Override
            public void onError(String msg) {
                dialog.dismiss();
                ToastUtils.showShort(getActivity(), msg);
            }
        });
    }
}

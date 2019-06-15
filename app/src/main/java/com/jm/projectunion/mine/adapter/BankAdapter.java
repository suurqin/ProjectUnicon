package com.jm.projectunion.mine.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.common.widget.CircleImageView;
import com.jm.projectunion.entity.Result;
import com.jm.projectunion.mine.entity.BanksResult;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Young on 2017/11/10.
 */

public class BankAdapter extends BaseAdapter {

    private Activity context;
    private List<BanksResult.BankBean> data;

    public BankAdapter(Activity context, List<BanksResult.BankBean> data) {
        this.context = context;
        this.data = data;
    }

    public void notifyData(List<BanksResult.BankBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == this.data ? 0 : this.data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        final BanksResult.BankBean bankBean = data.get(position);
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_bank_item, viewGroup, false);
            holder.bank_num = convertView.findViewById(R.id.bank_num);
            holder.bank_del = convertView.findViewById(R.id.bank_del);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String cardId = bankBean.getCardId();
        holder.bank_num.setText(cardId.substring(cardId.length() - 4, cardId.length()));
        holder.bank_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delBank(position, bankBean.getUserCardId());
            }
        });
        return convertView;
    }

    static class ViewHolder {
        CircleImageView bank_pic;
        TextView bank_name;
        TextView bank_type;
        TextView bank_num;
        TextView bank_del;
    }

    private void delBank(final int position, String cardId) {
        ApiClient.delBank(context, cardId, new ResultCallback<Result>() {
            @Override
            public void onSuccess(Result response) {
                ToastUtils.showShort(context, response.getMsg());
                if ("0".equals(response.getCode())) {
                    data.remove(position);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String msg) {
                ToastUtils.showShort(context, msg);
            }
        });
    }
}

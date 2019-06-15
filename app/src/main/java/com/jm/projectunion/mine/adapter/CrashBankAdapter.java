package com.jm.projectunion.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.widget.CircleImageView;
import com.jm.projectunion.mine.entity.BanksResult;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Young on 2017/11/10.
 */

public class CrashBankAdapter extends BaseAdapter {

    private Context context;
    private int selPosition = 0;
    private List<BanksResult.BankBean> data;

    public CrashBankAdapter(Context context) {
        this.context = context;
    }

    public void notifyData(List<BanksResult.BankBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == data ? 0 : data.size();
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        BanksResult.BankBean bankBean = data.get(position);
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_crash_bank_item, viewGroup, false);
            holder.bank_title = convertView.findViewById(R.id.bank_title);
            holder.check_flag = convertView.findViewById(R.id.check_flag);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (selPosition == position) {
            holder.check_flag.setImageResource(R.mipmap.radio_sel);
        } else {
            holder.check_flag.setImageResource(R.mipmap.radio_nor);
        }

        holder.bank_title.setText("持卡人：" + bankBean.getUserName() + "(" + bankBean.getCardId().substring(bankBean.getCardId().length() - 4, bankBean.getCardId().length()) + ")");

        return convertView;
    }

    public void setSelPosition(int position) {
        this.selPosition = position;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView bank_title;
        ImageView check_flag;
    }
}

package com.jm.projectunion.wiget.popupwindow;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.utils.TextViewUtils;
import com.jm.projectunion.home.entiy.ServiceResult;

import java.util.List;

/**
 * Created by Young on 2017/11/7.
 */

public class ServicePopupWindowAdapter extends BaseAdapter {

    private Context context;
    private List<ServiceResult.ServiceBean> list;

    public ServicePopupWindowAdapter(Context context) {
        this.context = context;
    }

    public ServicePopupWindowAdapter(Context context, List<ServiceResult.ServiceBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<ServiceResult.ServiceBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == list ? 0 : list.size();
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        ServiceResult.ServiceBean bean = list.get(position);
        TextView textView = new TextView(context);
        if (0 == position) {
            textView.setTextColor(context.getResources().getColor(R.color.text_selected));
        } else {
            textView.setTextColor(context.getResources().getColor(R.color.text_normal));
        }
        textView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, TextViewUtils.getRealHeight(context, 100)));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextViewUtils.getRealTextSize(context, 34));
        textView.setBackgroundResource(R.drawable.item_txt_bg);
        textView.setPadding(30, 0, 0, 0);
        textView.setText(bean.getName());
        return textView;
    }
}

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

import java.util.List;

/**
 * Created by Young on 2017/11/7.
 */

public class BottomPopupWindowAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public BottomPopupWindowAdapter(Context context) {
        this.context = context;
    }

    public BottomPopupWindowAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<String> list) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView = new TextView(context);
        textView.setTextColor(context.getResources().getColor(R.color.text_selected));
        textView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, TextViewUtils.getRealHeight(context, 116)));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextViewUtils.getRealTextSize(context, 36));
        textView.setBackgroundResource(R.drawable.item_txt_bg);
        textView.setText(list.get(i));
        return textView;
    }
}

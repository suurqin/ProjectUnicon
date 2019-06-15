package com.jm.projectunion.friends.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jm.projectunion.R;
import com.jm.projectunion.common.utils.TextViewUtils;

import java.util.List;

/**
 * Created by bobo on 2017/12/19.
 */

public class TextViewAdapter extends BaseAdapter {

    private Context context;
    private List<String> piclist;

    public TextViewAdapter(Context context) {
        this.context = context;
    }

    public TextViewAdapter(Context context, List<String> piclist) {
        this.context = context;
        this.piclist = piclist;
    }

    public void setData(List<String> piclist) {
        this.piclist = piclist;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == piclist ? 0 : piclist.size();
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
        textView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        textView.setPadding(0, 10, 0, 10);
        textView.setTextColor(context.getResources().getColor(R.color.text_normal));
        textView.setBackgroundResource(R.drawable.tab_item_bg);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,TextViewUtils.getRealTextSize(context, 20));
        textView.setText(piclist.get(i));
        return textView;
    }
}

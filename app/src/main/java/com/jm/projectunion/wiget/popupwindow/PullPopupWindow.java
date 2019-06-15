package com.jm.projectunion.wiget.popupwindow;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.jm.projectunion.R;

import java.util.List;

/**
 * Created by Young on 2017/11/7.
 */

public class PullPopupWindow extends PopupWindow {

    private Activity context;
    private View mMenuView;
    private ListView listView;
    private BottomPopupWindowAdapter adapter;
    private OnItemClickListener listener;

    public PullPopupWindow(Activity context, List<String> data) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mMenuView = inflater.inflate(R.layout.popupwindow_pull, null);

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.translation_half));
        this.setBackgroundDrawable(dw);

        listView = mMenuView.findViewById(R.id.list);
        adapter = new BottomPopupWindowAdapter(context, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                dismiss();
                if (null != listener) {
                    listener.onItemClick(position);
                }
            }
        });
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = mMenuView.findViewById(R.id.list).getBottom();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (y > height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 获取内容组件
     *
     * @return
     */
    public ListView getContent() {
        return listView;
    }

    public void setData(List<String> data) {
        adapter.setData(data);
    }

    /**
     * 展示位置
     *
     * @param anchor
     */
    public void showAtBottom(View anchor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    /**
     * 展示位置
     *
     * @param view
     */
    public void showAtParentBottom(View view) {
        this.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

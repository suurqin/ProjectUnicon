package com.jm.projectunion.wiget.popupwindow;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.utils.DensityUtils;

import java.util.List;

/**
 * Created by Young on 2017/11/7.
 */

public class BottomCancelPopupWindow extends PopupWindow {

    private Activity context;
    private View mMenuView;
    private ListView listView;
    private TextView cancel;
    private OnItemClickListener listener;

    public BottomCancelPopupWindow(Activity context, final List<String> data) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mMenuView = inflater.inflate(R.layout.popupwindow_bottom_cancel, null);

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
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) listView.getLayoutParams();
        if (data.size() > 5) {
            layoutParams.height = DensityUtils.getScreenHeight(context) / 2;
        }
        listView.setAdapter(new BottomPopupWindowAdapter(context, data));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                dismiss();
                if (null != listener) {
                    listener.onItemClick(position);
                }
            }
        });

        cancel = mMenuView.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = mMenuView.findViewById(R.id.list).getTop();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
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

    /**
     * 展示位置
     *
     * @param view
     */
    public void showAtBottom(View view) {
        this.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

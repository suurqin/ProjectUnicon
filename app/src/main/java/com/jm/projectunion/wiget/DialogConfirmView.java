package com.jm.projectunion.wiget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jm.projectunion.R;


/**
 * 积分兑换弹出框
 * Created by Young on 2017/1/7.
 */

public class DialogConfirmView {
    private static DialogConfirmView integralView = new DialogConfirmView();
    private static Context mContext;
    private onNegativeClickListener negativeClickListener;
    private onPositiveClickListener positiveClickListener;

    private TextView confirm_title, confirm_content, btn_positive, btn_negative;

    private DialogConfirmView() {
    }

    public static DialogConfirmView newInstance(Context context) {
        mContext = context;
        return integralView;
    }

    /**
     * 初始化对话框布局
     *
     * @return
     */
    public View create(String title, String content) {
        View view = View.inflate(mContext, R.layout.dialog_confirm, null);
        confirm_title = view.findViewById(R.id.confirm_title);
        confirm_content = view.findViewById(R.id.confirm_content);
        if (!TextUtils.isEmpty(title)) {
            confirm_title.setText(title);
        }
        confirm_content.setText(content);
        btn_negative = view.findViewById(R.id.btn_nagetive);
        btn_positive = view.findViewById(R.id.btn_positive);
        btn_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != negativeClickListener) {
                    negativeClickListener.onClick(v);
                }
            }
        });

        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != positiveClickListener) {
                    positiveClickListener.onClick(v);
                }
            }
        });
        return view;
    }

    public DialogConfirmView setNegative(String negative) {
        if (null == negative) {
            btn_negative.setVisibility(View.GONE);
        } else {
            btn_negative.setVisibility(View.VISIBLE);
            btn_negative.setText(negative);
        }
        return this;
    }

    public DialogConfirmView setPositive(String positive) {
        if (null == positive) {
            btn_positive.setVisibility(View.GONE);
        } else {
            btn_positive.setVisibility(View.VISIBLE);
            btn_positive.setText(positive);
        }
        return this;
    }

    public void setNegativeClickListener(onNegativeClickListener clickListener) {
        this.negativeClickListener = clickListener;
    }

    public void setPositiveClickListener(onPositiveClickListener clickListener) {
        this.positiveClickListener = clickListener;
    }

    public interface onNegativeClickListener {
        void onClick(View view);
    }

    public interface onPositiveClickListener {
        void onClick(View view);
    }
}

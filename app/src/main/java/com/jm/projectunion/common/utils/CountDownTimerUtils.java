package com.jm.projectunion.common.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * Created by ${mengyangguang} on 2016/11/22.
 */

public class CountDownTimerUtils extends CountDownTimer {
    private TextView mTextView;
    private Context mContext;

    public CountDownTimerUtils(Context context, long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        this.mContext = context;
        mTextView = textView;
    }

    @Override
    public void onTick(long l) {
        mTextView.setEnabled(false); //设置不可点击
        mTextView.setText(l / 1000 + "s");  //设置倒计时时间
//        mTextView.setTextColor(mContext.getResources().getColor(R.color.white));
//        mTextView.setBackgroundResource(R.drawable.common_btn_gray); //设置按钮为灰色，这时是不能点击的
        SpannableString spannableString = new SpannableString(mTextView.getText().toString());  //获取按钮上的文字
        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
        spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
        mTextView.setText(spannableString);
    }

    @Override
    public void onFinish() {
        mTextView.setText("重发验证码");
        mTextView.setEnabled(true);//重新获得点击
//        mTextView.setTextColor(mContext.getResources().getColor(R.color.white));
//        mTextView.setBackgroundResource(R.drawable.common_btn_red);  //还原背景色
    }
}

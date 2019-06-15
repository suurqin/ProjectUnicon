package com.jm.projectunion.common.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jm.projectunion.R;


/**
 * Dialog工具类
 *
 * @author Seasbary
 * @date 2015-5-5 下午7:16:35
 */
public class DialogUtils {
    /**
     * 自定义Dialog内容
     *
     * @param context
     * @param view
     * @return
     */
    public static Dialog showCustomDialog(Context context, View view, boolean isoutside) {
        Dialog dialog = new Dialog(context, R.style.version_dialog);
        dialog.setContentView(view);
        dialog.setCancelable(isoutside);
        dialog.setCanceledOnTouchOutside(isoutside);
        dialog.show();
        return dialog;
    }

    /**
     * 加载中遮罩层
     *
     * @param context
     * @return
     */
    public static Dialog showLoading(Context context) {
        return showLoading(context, "加载中....", true);
    }

    /**
     * 加载中遮罩层
     *
     * @param context
     * @return
     */
    public static Dialog showLoading(Context context, String msg) {
        return showLoading(context, msg, true);
    }

    /**
     * 加载中遮罩层
     *
     * @return
     */
    public static Dialog showLoading(Context context, String messge, boolean hasbg) {
        Dialog dialog = null;
        View mView = LayoutInflater.from(context).inflate(
                R.layout.base_loading, null);
        if (hasbg) {
            mView.setBackgroundResource(R.drawable.icon_loading_bg);
        }
        mView.setVisibility(View.VISIBLE);
        mView.setPadding(30, 30, 30, 30);
        TextView tv = (TextView) mView.findViewById(R.id.base_loading_msg);
        ImageView im = (ImageView) mView.findViewById(R.id.base_loading_im);
        //wfx修改 去除加载中图标
//        Glide.with(context).load(R.drawable.loading).asGif().diskCacheStrategy(DiskCacheStrategy.ALL).into(im);
        tv.setTextColor(context.getResources().getColor(R.color.white));
        if (messge != null) {
            tv.setText(messge);
            tv.setVisibility(View.VISIBLE);
        }

        dialog = new Dialog(context, R.style.CommonLoadingShadeDialog);
        dialog.setContentView(mView);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = DensityUtils.getScreenWidth(context); //设置宽度
        lp.height = DensityUtils.getScreenHeight(context); //设置高度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        return dialog;
    }
}

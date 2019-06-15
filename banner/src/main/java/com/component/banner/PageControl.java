package com.component.banner;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * GridView页面选项卡圆点显示控制
 *
 * @author han
 * @creation 2015-5-25
 */
public class PageControl {

    private LinearLayout layout;
    private ImageView[] imageViews;
    private ImageView imageView;
    private int pageSize;

    private int currentPage = 0;
    private Context mContext;

    private int defaultResId;
    private int selectResId;

    public PageControl(Context context, LinearLayout layout, int pageSize, int defaultResId, int selectResId) {
        this.mContext = context;
        this.pageSize = pageSize;
        this.layout = layout;
        this.defaultResId = defaultResId;
        this.selectResId = selectResId;
        layout.removeAllViews();
        initDots();
    }

    private void initDots() {
        imageViews = new ImageView[pageSize];
        for (int i = 0; i < pageSize; i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.setMargins(8, 0, 8, 0);

            imageView = new ImageView(mContext);
            imageView.setLayoutParams(lp);

            imageViews[i] = imageView;
            if (i == 0) {
                // 默认进入程序后第一张图片被选中;
                imageViews[i].setBackgroundResource(selectResId);
            } else {
                imageViews[i].setBackgroundResource(defaultResId);
            }
            layout.addView(imageViews[i]);
        }
    }

    boolean isFirst() {
        return this.currentPage == 0;
    }

    public void selectPage(int current) {
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[current].setBackgroundResource(selectResId);
            if (current != i) {
                imageViews[i].setBackgroundResource(defaultResId);
            }
        }
    }
}

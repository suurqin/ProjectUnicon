package com.component.banner;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Young on 2016/3/2.
 */
public class BannerView extends RelativeLayout {

    private static int BANNER_MOVE = 3000;
    private static final int WHEEL = 100;
    private static final int WHEEL_WAIT = 101;

    private Context context;
    private PageControl pageControl;
    private ViewPager banner;
    private long releaseTime = 0; // 手指松开、页面不滚动时间，防止手机松开后短时间进行切换\
    private boolean isScrolling = false;
    private int currentPosition;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (WHEEL == msg.what) {

                if (!isScrolling) {
                    if (banner.getCurrentItem() == (banner.getAdapter().getCount() - 1)) {
                        banner.setCurrentItem(0);
                    } else {
                        banner.setCurrentItem(banner.getCurrentItem() + 1);
                    }
                }
                releaseTime = System.currentTimeMillis();

                handler.removeCallbacks(bannerRun);
                handler.postDelayed(bannerRun, BANNER_MOVE);
            }

            if (WHEEL_WAIT == msg.what) {
                handler.removeCallbacks(bannerRun);
                handler.postDelayed(bannerRun, BANNER_MOVE);
            }
        }
    };

    public BannerView(Context context) {
        super(context);
        init(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BannerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        this.context = context;
    }

    public ViewPager getBannerContent() {
        return banner;
    }

    /**
     * @param banner
     * @param defaultResId 默认索引图标
     * @param selectResId  选中索引图标
     */
    public void setBanner(final ViewPager banner, int defaultResId, int selectResId, final int banners) {
        if (null != banner) {
            this.removeAllViews();
            PagerAdapter adapter = banner.getAdapter();
            if (null == adapter) {
                Toast.makeText(context, "Adapter设置失败", Toast.LENGTH_LONG).show();
                return;
            }
            this.banner = banner;
            banner.setOffscreenPageLimit(banners);
            banner.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            this.addView(banner);
            //页码
            LinearLayout pointsContainer = new LinearLayout(context);
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            pointsContainer.setLayoutParams(lp);
            pointsContainer.setPadding(0, 0, 0, 30);
            pageControl = new PageControl(context, pointsContainer, banners, defaultResId, selectResId);
            //布局规则
            LayoutParams lp2 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            lp2.addRule(RelativeLayout.CENTER_IN_PARENT);

            banner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPosition = position;
                    pageControl.selectPage(position % banners);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == 1) { // viewPager在滚动
                        isScrolling = true;
                        return;
                    } else if (state == 0) { // viewPager滚动结束
                        releaseTime = System.currentTimeMillis();
                        banner.setCurrentItem(currentPosition, false);

                    }
                    isScrolling = false;
                }
            });

            this.addView(pointsContainer, lp2);
            if (banners > 1) {
                handler.removeCallbacks(bannerRun);
                handler.postDelayed(bannerRun, BANNER_MOVE);
            }
        } else {
            Toast.makeText(context, "设置的Banner错误", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param banner
     * @param defaultResId 默认索引图标
     * @param selectResId  选中索引图标
     */
    public void setBanner(final ViewPager banner, int defaultResId, int selectResId, final int banners, int duration) {
        if (null != banner) {
            BANNER_MOVE = duration;
            this.removeAllViews();
            PagerAdapter adapter = banner.getAdapter();
            if (null == adapter) {
                Toast.makeText(context, "Adapter设置失败", Toast.LENGTH_LONG).show();
                return;
            }
            this.banner = banner;
            banner.setOffscreenPageLimit(banners);
            banner.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            this.addView(banner);
            //页码
            LinearLayout pointsContainer = new LinearLayout(context);
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            pointsContainer.setLayoutParams(lp);
            pointsContainer.setPadding(0, 0, 0, 30);
            pageControl = new PageControl(context, pointsContainer, banners, defaultResId, selectResId);
            //布局规则
            LayoutParams lp2 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            lp2.addRule(RelativeLayout.CENTER_IN_PARENT);

            banner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPosition = position;
                    pageControl.selectPage(position % banners);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == 1) { // viewPager在滚动
                        isScrolling = true;
                        return;
                    } else if (state == 0) { // viewPager滚动结束
                        releaseTime = System.currentTimeMillis();
                        banner.setCurrentItem(currentPosition, false);

                    }
                    isScrolling = false;
                }
            });

            this.addView(pointsContainer, lp2);
            if (banners > 1) {
                handler.removeCallbacks(bannerRun);
                handler.postDelayed(bannerRun, BANNER_MOVE);
            }
        } else {
            Toast.makeText(context, "设置的Banner错误", Toast.LENGTH_SHORT).show();
        }
    }

    private Runnable bannerRun = new Runnable() {
        @Override
        public void run() {
            long now = System.currentTimeMillis();
            // 检测上一次滑动时间与本次之间是否有触击(手滑动)操作，有的话等待下次轮播
            if (now - releaseTime > BANNER_MOVE - 500) {
                handler.sendEmptyMessage(WHEEL);
            } else {
                handler.sendEmptyMessage(WHEEL_WAIT);
            }
        }
    };

    public void stopRun(boolean isRun) {
        handler.removeCallbacks(bannerRun);
        if (!isRun) {
            handler.postDelayed(bannerRun, BANNER_MOVE);
        }
    }
}

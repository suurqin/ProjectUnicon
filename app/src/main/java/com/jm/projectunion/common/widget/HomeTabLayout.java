package com.jm.projectunion.common.widget;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.manager.OnClickListenerWrapper;
import com.jm.projectunion.common.utils.TextViewUtils;


public class HomeTabLayout extends LinearLayout {

    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;

    private ArgbEvaluator mColorEvaluator;
    int mTextNormalColor, mTextSelectedColor;

    private int mLastPosition;
    private int mSelectedPosition;
    private float mSelectionOffset;
    private int selTextSize;

    private String mTitles[] = {"首页", "消息", "信息完善", "好友", "我的"};
//    private int mIconRes[][] = {
//            {R.drawable.zre_ixt_tab_zixun_nor, R.drawable.zre_ixt_tab_zixun_press},
//            {R.drawable.zre_ixt_tab_kecheng_nor, R.drawable.zre_ixt_tab_kecheng_press},
//            {R.drawable.zre_ixt_tab_zhibo_nor, R.drawable.zre_ixt_tab_zhibo_press},
//            {R.drawable.zre_ixt_tab_shuji_nor, R.drawable.zre_ixt_tab_shuji_press},
//            {R.drawable.zre_ixt_tab_my_nor, R.drawable.zre_ixt_tab_my_press}
//    };

    private View[] mIconLayouts;

    public HomeTabLayout(Context context) {
        this(context, null);
    }

    public HomeTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        selTextSize = TextViewUtils.getRealTextSize(context, 20);
        mColorEvaluator = new ArgbEvaluator();
        mTextNormalColor = getResources().getColor(R.color.text_normal);
        mTextSelectedColor = getResources().getColor(R.color.text_selected);
    }

    public void setViewPager(ViewPager viewPager) {
        removeAllViews();
        mViewPager = viewPager;
        if (viewPager != null && viewPager.getAdapter() != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
            populateTabLayout();
        }
    }

    private void populateTabLayout() {
        final PagerAdapter adapter = mViewPager.getAdapter();
        final OnClickListenerWrapper tabClickListener = new TabClickListener();
        mIconLayouts = new View[adapter.getCount()];

        for (int i = 0; i < adapter.getCount(); i++) {

            final View tabView = LayoutInflater.from(getContext()).inflate(R.layout.home_view_bottom_tab, this, false);
            mIconLayouts[i] = tabView;
            TabIconView iconView = (TabIconView) tabView.findViewById(R.id.main_bottom_tab_icon);
            iconView.setVisibility(View.GONE);
            TextView textView = (TextView) tabView.findViewById(R.id.main_bottom_tab_text);
            textView.setText(mTitles[i]);
//            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, selTextSize);

            if (tabView == null) {
                throw new IllegalStateException("tabView is null.");
            }

            LayoutParams lp = (LayoutParams) tabView.getLayoutParams();
            lp.width = 0;
            lp.weight = 1;

            tabView.setOnClickListener(tabClickListener);
            addView(tabView);

            if (i == mViewPager.getCurrentItem()) {
                iconView.transformPage(0);
                tabView.setSelected(true);
                textView.setTextColor(mTextSelectedColor);
            }
        }
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
        private int mScrollState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            onViewPagerPageChanged(position, positionOffset);

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {

            for (int i = 0; i < getChildCount(); i++) {
                ((TabIconView) mIconLayouts[i].findViewById(R.id.main_bottom_tab_icon))
                        .transformPage(position == i ? 0 : 1);
                ((TextView) mIconLayouts[i].findViewById(R.id.main_bottom_tab_text))
                        .setTextColor(position == i ? mTextSelectedColor : mTextNormalColor);
            }

            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                onViewPagerPageChanged(position, 0f);
            }

            for (int i = 0, size = getChildCount(); i < size; i++) {
                getChildAt(i).setSelected(position == i);
            }


            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageSelected(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollState = state;

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    }

    private void onViewPagerPageChanged(int position, float positionOffset) {
        mSelectedPosition = position;
        mSelectionOffset = positionOffset;
        if (positionOffset == 0f && mLastPosition != mSelectedPosition) {
            mLastPosition = mSelectedPosition;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int childCount = getChildCount();
        if (childCount > 0) {
            if (mSelectionOffset > 0f && mSelectedPosition < (getChildCount() - 1)) {

                View selectedTab = getChildAt(mSelectedPosition);
                View nextTab = getChildAt(mSelectedPosition + 1);

                View selectedIconView = ((LinearLayout) selectedTab).getChildAt(0);
                View nextIconView = ((LinearLayout) nextTab).getChildAt(0);

                View selectedTextView = ((LinearLayout) selectedTab).getChildAt(1);
                View nextTextView = ((LinearLayout) nextTab).getChildAt(1);

                //draw icon alpha
                if (selectedIconView instanceof TabIconView && nextIconView instanceof TabIconView) {
                    ((TabIconView) selectedIconView).transformPage(mSelectionOffset);
                    ((TabIconView) nextIconView).transformPage(1 - mSelectionOffset);
                }

                //draw text color
                Integer selectedColor = (Integer) mColorEvaluator.evaluate(mSelectionOffset,
                        mTextSelectedColor,
                        mTextNormalColor);
                Integer nextColor = (Integer) mColorEvaluator.evaluate(1 - mSelectionOffset,
                        mTextSelectedColor,
                        mTextNormalColor);

                if (selectedTextView instanceof TextView && nextTextView instanceof TextView) {
                    ((TextView) selectedTextView).setTextColor(selectedColor);
                    ((TextView) nextTextView).setTextColor(nextColor);
                }
            }
        }
    }

    private class TabClickListener extends OnClickListenerWrapper {
        @Override
        protected void onSingleClick(View v) {
            for (int i = 0; i < getChildCount(); i++) {
                if (v == getChildAt(i)) {
//                    mViewPager.setCurrentItem(i, false);
                    onPageClickLitener.onPageItemClick(i);
                    return;
                }
            }
        }
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mViewPagerPageChangeListener = listener;
    }

    public interface OnPageClickLitener {
        void onPageItemClick(int currentItem);
    }

    private OnPageClickLitener onPageClickLitener;

    public void setOnPageClickLitener(OnPageClickLitener mOnPageClickLitener) {
        this.onPageClickLitener = mOnPageClickLitener;
    }
}

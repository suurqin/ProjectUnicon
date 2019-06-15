
package com.jm.projectunion.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.utils.DensityUtils;


/**
 * Created by wuyexiong on 4/25/15.
 */
public class TabIconView extends ImageView {

    private Paint mPaint;
    private Bitmap mSelectedIcon;
    private Bitmap mNormalIcon;
    private Rect mSelectedRect;
    private Rect mNormalRect;
    private int mSelectedAlpha = 0;
private Context mContext;
    public TabIconView(Context context) {
        super(context);
        mContext=context;
    }

    public TabIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
    }

    public TabIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
    }

    public final void init(int normal, int selected) {
        this.mNormalIcon = createBitmap(normal);
        this.mSelectedIcon = createBitmap(selected);
        int width= (int) getResources().getDimension(R.dimen.zre_common_home_tab_icon_width);
        int height= (int) getResources().getDimension(R.dimen.zre_common_home_tab_icon_height);

        this.mNormalRect = new Rect(0, 0,width* DensityUtils.getScreenWidth(mContext)/750,height* DensityUtils.getScreenWidth(mContext)/750);
        this.mSelectedRect = new Rect(0, 0,width* DensityUtils.getScreenWidth(mContext)/750,height* DensityUtils.getScreenWidth(mContext)/750);
        this.mPaint = new Paint(1);
    }


    private Bitmap createBitmap(int resId) {
        return BitmapFactory.decodeResource(getResources(), resId);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPaint == null) {
            return;
        }
        this.mPaint.setAlpha(255 - this.mSelectedAlpha);
        canvas.drawBitmap(this.mNormalIcon, null, this.mNormalRect, this.mPaint);
        this.mPaint.setAlpha(this.mSelectedAlpha);
        canvas.drawBitmap(this.mSelectedIcon, null, this.mSelectedRect, this.mPaint);
    }

    public final void changeSelectedAlpha(int alpha) {
        this.mSelectedAlpha = alpha;
        invalidate();
    }

    public final void transformPage(float offset) {
        changeSelectedAlpha((int) (255 * (1 - offset)));
    }
}

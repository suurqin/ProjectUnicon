package com.jcodecraeer.xrecyclerview;

import android.content.Context;

/**
 * Created by Young on 2017/1/4.
 */

public class RefreshHeaderFactory {

    public static final int HEADER_NORMAL = 0;
    public static final int HEADER_PIC = 1;

    public static BaseRefreshHeader getHeader(Context context, int style) {
        switch (style) {
            case HEADER_NORMAL:
                return new ArrowRefreshHeader(context);
            case HEADER_PIC:
                return new PicRefreshHeader(context);
            default:
                return new BaseRefreshHeader(context);
        }
    }
}

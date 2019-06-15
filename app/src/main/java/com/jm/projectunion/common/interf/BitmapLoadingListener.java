package com.jm.projectunion.common.interf;

import android.graphics.Bitmap;

public interface BitmapLoadingListener {

    void onSuccess(Bitmap b);

    void onError();
}
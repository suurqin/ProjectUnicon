package com.jm.projectunion.common.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by yunzhao.liu on 2017/3/9
 */

public class MyGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        int cacheSize100MegaBytes = 104857600;

        builder.setDiskCache(
                new InternalCacheDiskCacheFactory(context, cacheSize100MegaBytes)
        );

//        上面的代码将设置磁盘缓存到应用的内部目录，并且设置了最大的大小为 100M。
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        // nothing to do here
    }
}

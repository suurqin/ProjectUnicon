package com.jm.projectunion.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 图片工具类
 * @author Young
 * @date 2015-7-17 下午8:26:48
 */
public class ImageUtils {
    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     * @param filePath
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int width, int height) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width, height);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }
    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static Bitmap getSmallBitmap(String filePath) {
        return getSmallBitmap(filePath, 480, 800);
    }

    public static Bitmap getSmallBitmap(Context context, Uri uri, int width,
                                        int height) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        ParcelFileDescriptor parcelFileDescriptor = null;
        Bitmap image = null;
        try {
            parcelFileDescriptor = context.getContentResolver()
                    .openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor
                    .getFileDescriptor();
            BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, width, height);
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            image = BitmapFactory.decodeFileDescriptor(fileDescriptor, null,
                    options);
            parcelFileDescriptor.close();
        } catch (FileNotFoundException e) {
            ToastUtils.showShort(context, "没有找到文件");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public static Bitmap getSmallBitmap(Context context, Uri uri) {
        return getSmallBitmap(context, uri, 480, 800);
    }

    public static byte[] bmpToByteArray(Bitmap thumb, boolean needRecycle) {
        return bmpToByteArray(thumb, 100, needRecycle);
    }

    /**
     * 压缩图片
     * 【注意】：如果设置压缩格式为Bitmap.CompressFormat.PNG的话，则不管quality是多少，都不会进行质量的压缩
     *
     * @param thumb
     * @param quality
     * @param needRecycle
     * @return
     */
    public static byte[] bmpToByteArray(Bitmap thumb, int quality, boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        // 【注意】：如果设置压缩格式为Bitmap.CompressFormat.PNG的话，则不管quality是多少，都不会进行质量的压缩
        thumb.compress(Bitmap.CompressFormat.JPEG, quality, output);
        if (needRecycle) {
            thumb.recycle();
            thumb = null;
            System.gc();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

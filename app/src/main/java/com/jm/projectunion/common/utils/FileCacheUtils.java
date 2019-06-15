package com.jm.projectunion.common.utils;

import android.content.Context;
import android.os.Environment;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;


/**
 * 文件缓存工具类
 */
public class FileCacheUtils {
    private static String CACHE_FILE_NAME = "adbRestructure";
    private static File CacheRoot;

    /**
     * 存储Json文件
     *
     * @param c
     * @param json     json字符串
     * @param fileName 存储的文件名
     * @param append   true 增加到文件末，false则覆盖掉原来的文件
     */
    public static void writeJson(Context c, String json, String fileName,
                                 boolean append) {
        if (c == null)
            return;
        CacheRoot = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? c
                .getExternalCacheDir() : c.getCacheDir();
        FileOutputStream fos = null;
        ObjectOutputStream os = null;
        try {
            File file = new File(CacheRoot + "/" + CACHE_FILE_NAME);
            if (!file.exists()) {
                file.mkdir();
            }

            File ff = new File(CacheRoot + "/" + CACHE_FILE_NAME, fileName);
            boolean boo = ff.exists();
            fos = new FileOutputStream(ff, append);
            os = new ObjectOutputStream(fos);
            if (append && boo) {
                FileChannel fc = fos.getChannel();
                fc.truncate(fc.position() - 4);

            }
            os.writeObject(json);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件夹
     *
     * @param folderPath
     * @param context
     */
    public static void delFolder(String folderPath, Context context) {
        try {
            delAllFile(folderPath, context); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 清空缓存文件
     *
     * @param c
     * @return
     */
    public static boolean clearAllCache(Context c) {

        CacheRoot = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? c
                .getExternalCacheDir() : c.getCacheDir();
        String path = CacheRoot + "/" + CACHE_FILE_NAME;
        return delAllFile(path, c);
    }

    public static boolean delAllFile(String path, Context c) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i], c);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i], c);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }


    /**
     * 读取json数据
     *
     * @param c
     * @param fileName
     * @return 返回值为list
     */

    @SuppressWarnings("resource")
    public static List<String> readJson(Context c, String fileName) {

        CacheRoot = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? c
                .getExternalCacheDir() : c.getCacheDir();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<String> result = new ArrayList<String>();
        File des = new File(CacheRoot + "/" + CACHE_FILE_NAME, fileName);
        if (!des.exists()) {
            try {
                des.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fis = new FileInputStream(des);
            ois = new ObjectInputStream(fis);
            while (fis.available() > 0)
                result.add((String) ois.readObject());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return result;
    }


    /**
     * 读取数据
     *
     * @param c
     * @param fileName
     * @return 返回值为list
     */

    @SuppressWarnings("resource")
    public static Object getCacheData(Context c, String fileName, Class t) {
        List<String> lst = readJson(c, fileName);
        Gson gson = new Gson();
        if (lst.size() > 0) {
            return gson.fromJson(lst.get(0), t);
        }
        return null;
    }
}

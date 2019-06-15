package com.jm.projectunion.utils.api;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Young on 2017/11/28.
 */

public class ApiCallback<T> extends Callback {

    private Activity activity;
    private Gson gson;
    private Class<T> clazz;
    private ResultCallback<T> callback;

    public ApiCallback(Activity activity, ResultCallback<T> callback, Class<T> clazz) {
        this.activity = activity;
        this.callback = callback;
        this.clazz = clazz;
        gson = new Gson();
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        final T t = gson.fromJson(response.body().string(), clazz);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null != t) {
                    callback.onSuccess(t);
                } else {
                    callback.onError("数据请求失败，请稍后重试");
                }
            }
        });
        return null;
    }

    @Override
    public void onError(final Call call, final Exception e, int id) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                call.cancel();
                callback.onError(e.getMessage());
            }
        });
    }

    @Override
    public void onResponse(Object response, int id) {

    }
}

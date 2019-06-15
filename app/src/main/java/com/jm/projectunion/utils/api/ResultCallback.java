package com.jm.projectunion.utils.api;

/**
 * Created by Young on 2017/11/28.
 */

public abstract class ResultCallback<T> {

    public abstract void onSuccess(T response);

    public abstract void onError(String msg);
}

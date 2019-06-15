package com.jm.projectunion.common.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

import com.google.gson.Gson;
import com.jm.projectunion.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 描述：应用个性化设置(SharedPreferences)的包装类
 */
public class SharedPreWrapper {

    public final static String TAG = SharedPreWrapper.class.getSimpleName();

    private static SharedPreWrapper commSharedPrefInstance;

    public final static String SP_NAME_APP = "SP_APP";

    private String spName = SP_NAME_APP;

    private SharedPreferences mSharedPref;

    private Editor mEditor;

    /**
     * 获取一个全应用通用的SharedPreferences，默认只读模式
     */
    public static SharedPreWrapper getInstance() {
        return getInstance(Context.MODE_PRIVATE);
    }

    /**
     * 获取一个全应用通用的制定访问权限的SharedPreferences
     *
     * @param mode 设置SharedPreferences访问模式，The mode must be one of
     *             Context.MODE_APPEND, Context.MODE_PRIVATE,
     *             Context.MODE_WORLD_READABLE, Context.MODE_WORLD_WRITEABLE.
     */
    public static SharedPreWrapper getInstance(int mode) {
        if (commSharedPrefInstance == null) {
            synchronized (SP_NAME_APP) {
                if (commSharedPrefInstance == null) {
                    commSharedPrefInstance = new SharedPreWrapper(mode);
                }
            }
        }
        return commSharedPrefInstance;
    }

    private SharedPreWrapper(int mode) {
        if (mode == Context.MODE_APPEND || mode == Context.MODE_PRIVATE
                || mode == Context.MODE_MULTI_PROCESS
                || mode == Context.MODE_WORLD_READABLE
                || mode == Context.MODE_WORLD_WRITEABLE) {
            /*
             * MODE_MULTI_PROCESS这个值是一个标志，在Android2.3及以前，这个标志位都是默认开启的，
			 * 允许多个进程访问同一个SharedPrecferences对象。而以后的Android版本，必须通过明确的将
			 * MODE_MULTI_PROCESS这个值传递给mode参数，才能开启多进程访问。
			 */
            mode = (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) ? (mode & 4)
                    : mode;
            mSharedPref = MyApplication.getInstance().getSharedPreferences(spName, mode);
            mEditor = mSharedPref.edit();
        } else {
            throw new RuntimeException("No such SharedPreferences access mode: " + mode
                    + ". The mode must be one of Context.MODE_APPEND, "
                    + "Context.MODE_PRIVATE, Context.MODE_WORLD_READABLE,"
                    + "Context.MODE_WORLD_WRITEABLE.");
        }
    }

    /**
     * @param spName SharedPreferences的名称，默认只读模式
     */
    public SharedPreWrapper(String spName) {
        this(spName, Context.MODE_PRIVATE);
    }

    /**
     * @param spName SharedPreferences的名称
     * @param mode   设置SharedPreferences访问模式，The mode must be one of
     *               Context.MODE_APPEND, Context.MODE_PRIVATE,
     *               Context.MODE_WORLD_READABLE, Context.MODE_WORLD_WRITEABLE.
     */
    public SharedPreWrapper(String spName, int mode) {
        this.spName = spName;
        if (mode == Context.MODE_APPEND || mode == Context.MODE_PRIVATE
                || mode == Context.MODE_WORLD_READABLE
                || mode == Context.MODE_WORLD_WRITEABLE) {
            /*
             * MODE_MULTI_PROCESS这个值是一个标志，在Android2.3及以前，这个标志位都是默认开启的，
			 * 允许多个进程访问同一个SharedPrecferences对象。而以后的Android版本，必须通过明确的将
			 * MODE_MULTI_PROCESS这个值传递给mode参数，才能开启多进程访问。
			 */
            mode = (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) ? (mode & 4)
                    : mode;
            mSharedPref = MyApplication.getInstance().getSharedPreferences(spName, mode);
            mEditor = mSharedPref.edit();
        } else {
            throw new RuntimeException("No such SharedPreferences access mode: " + mode
                    + ". The mode must be one of Context.MODE_APPEND, "
                    + "Context.MODE_PRIVATE, Context.MODE_WORLD_READABLE,"
                    + "Context.MODE_WORLD_WRITEABLE.");
        }
    }

    /**
     * 将javabean实体存入SharedPreferences中。<br>
     * <font color='red'>注意：bean中的属性值只能是以下几种简单类型 :
     * int,long,float,boolean,String</font>
     *
     * @param bean
     * @throws JSONException
     */
    public boolean putJavaBean(Object bean) {
        return putJsonStr(new Gson().toJson(bean));
    }

    /**
     * 将JSONObject各个字段-值对存入SharedPreferences中。<br>
     * <font color='red'>注意：JSONObject中的key必须是String类型，key对应的value只能是以下几种简单类型 :
     * int,long,float,boolean,String</font>
     *
     * @param json
     */
    public boolean putJSONObject(JSONObject json) {
        Iterator<?> it = json.keys();
        try {
            while (it.hasNext()) {
                String key = (String) it.next();
                putObject(key, json.get(key));
            }
            return mEditor.commit();
        } catch (JSONException e) {
        }
        return false;
    }

    /**
     * 将json字符串存入SharedPreferences中
     *
     * @param json
     * @throws JSONException
     */
    public boolean putJsonStr(String json) {
        try {
            return putJSONObject(new JSONObject(json));
        } catch (JSONException e) {
        }
        return false;
    }

    /**
     * 批量存入String类型的值到SharedPreferences中
     *
     * @param key_vals
     */
    public boolean putBatchStr(Map<String, String> key_vals) {
        Set<Entry<String, String>> keyvels = key_vals.entrySet();
        for (Entry<String, String> entry : keyvels) {
            mEditor.putString(entry.getKey(), entry.getValue());
        }
        return mEditor.commit();
    }

    /**
     * 批量存入不同数据类型的值到SharedPreferences中
     *
     * @param key_vals
     */
    public boolean putBatch(Map<String, Object> key_vals) {
        Set<Entry<String, Object>> keyvels = key_vals.entrySet();
        for (Entry<String, Object> entry : keyvels) {
            String key = entry.getKey();
            Object val = entry.getValue();
            if (val instanceof String) {
                mEditor.putString(key, (String) val);
            } else if (val instanceof Long) {
                mEditor.putLong(key, (Long) val);
            } else if (val instanceof Integer) {
                mEditor.putInt(key, (Integer) val);
            } else if (val instanceof Float) {
                mEditor.putFloat(key, (Float) val);
            } else if (val instanceof Boolean) {
                mEditor.putBoolean(key, (Boolean) val);
            } else {
                throw new RuntimeException("SharedPreferences can't put such type:"
                        + val);
            }
        }
        return mEditor.commit();
    }

    public boolean putObjectAutoCommit(String key, Object val) {
        putObject(key, val);
        return mEditor.commit();
    }

    public boolean putStringAutoCommit(String key, String value) {
        mEditor.putString(key, value);
        return mEditor.commit();
    }

    public boolean putIntAutoCommit(String key, int value) {
        mEditor.putInt(key, value);
        return mEditor.commit();
    }

    public boolean putLongAutoCommit(String key, long value) {
        mEditor.putLong(key, value);
        return mEditor.commit();
    }

    public boolean putFloatAutoCommit(String key, float value) {
        mEditor.putFloat(key, value);
        return mEditor.commit();
    }

    public boolean putBooleanAutoCommit(String key, boolean value) {
        mEditor.putBoolean(key, value);
        return mEditor.commit();
    }

    // ---------------------不自动提交-----------------------//

    public void putObject(String key, Object val) {
        if (val instanceof String) {
            mEditor.putString(key, (String) val);
        } else if (val instanceof Long) {
            mEditor.putLong(key, (Long) val);
        } else if (val instanceof Integer) {
            mEditor.putInt(key, (Integer) val);
        } else if (val instanceof Float) {
            mEditor.putFloat(key, (Float) val);
        } else if (val instanceof Boolean) {
            mEditor.putBoolean(key, (Boolean) val);
        } else {
            throw new RuntimeException("SharedPreferences can't put such type:" + val);
        }
    }

    public void putString(String key, String value) {
        mEditor.putString(key, value);
    }

    public void putInt(String key, int value) {
        mEditor.putInt(key, value);
    }

    public void putLong(String key, long value) {
        mEditor.putLong(key, value);
    }

    public void putFloat(String key, float value) {
        mEditor.putFloat(key, value);
    }

    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
    }

    public String getString(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return mSharedPref.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return mSharedPref.getFloat(key, defValue);
    }

    public Map<String, ?> getAll() {
        return mSharedPref.getAll();
    }

    public void commit() {
        mEditor.commit();
    }

    public void clear() {
        mEditor.clear();
        commit();
    }

    public void remove(String key) {
        mEditor.remove(key);
        commit();
    }


}

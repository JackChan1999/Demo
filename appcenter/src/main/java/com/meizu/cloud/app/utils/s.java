package com.meizu.cloud.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class s {
    public static void a(Context context, String url, String ifSinceModified) {
        if (context != null) {
            Editor editor = context.getSharedPreferences("data_cache", 8).edit();
            editor.putString("If-Modified-Since:" + url, ifSinceModified);
            editor.apply();
        }
    }

    public static String a(Context context, String url) {
        if (context == null) {
            return "";
        }
        return context.getSharedPreferences("data_cache", 8).getString("If-Modified-Since:" + url, "");
    }

    public static void a(Context context, String url, String jsonStr, int start, int max) {
        if (context != null) {
            Editor editor = context.getSharedPreferences("data_cache", 8).edit();
            editor.putString(url + start + max, jsonStr);
            editor.apply();
        }
    }

    public static void a(Context context, String url, String jsonStr, int start, int max, long time) {
        if (context != null) {
            Editor editor = context.getSharedPreferences("data_cache", 8).edit();
            editor.putString(url + start + max, jsonStr);
            editor.putLong(url + "_savetime", time);
            editor.apply();
        }
    }

    public static void a(Context context, String url, String jsonStr, long time) {
        if (context != null) {
            Editor editor = context.getSharedPreferences("data_cache", 8).edit();
            editor.putString(url, jsonStr);
            editor.putLong(url + "_savetime", time);
            editor.apply();
        }
    }

    public static String a(Context context, String url, int start, int max) {
        if (context == null) {
            return "";
        }
        return context.getSharedPreferences("data_cache", 8).getString(url + start + max, "");
    }

    public static boolean a(Context context, String url, long time) {
        if (context == null) {
            return false;
        }
        long lastTime = context.getSharedPreferences("data_cache", 8).getLong(url + "_savetime", 0);
        if (time <= 0 || time - lastTime < 0 || time - lastTime >= 604800000) {
            return true;
        }
        return false;
    }

    public static boolean a(Context context, String url, int start, int max, long time) {
        if (context == null) {
            return false;
        }
        long lastTime = context.getSharedPreferences("data_cache", 8).getLong(url + start + max + "_savetime", 0);
        if (time <= 0 || time - lastTime < 0 || time - lastTime >= 604800000) {
            return true;
        }
        return false;
    }

    public static String b(Context context, String url) {
        if (context == null) {
            return "";
        }
        return context.getSharedPreferences("data_cache", 8).getString(url, "");
    }

    public static void a(Context context, String appId, Set<String> data) {
        if (context != null) {
            Editor editor = context.getSharedPreferences("comment_praised", 8).edit();
            editor.putStringSet(appId, data);
            editor.apply();
        }
    }

    public static Set<String> c(Context context, String key) {
        if (context == null) {
            return null;
        }
        return context.getSharedPreferences("comment_praised", 8).getStringSet(key, null);
    }

    public static void a(Context context) {
        if (context != null) {
            SharedPreferences mSharedPreferences = context.getSharedPreferences("activity_upload", 8);
            long lastTime = mSharedPreferences.getLong("timestamp", 0);
            long nowTime = System.currentTimeMillis();
            if (lastTime <= 0) {
                Editor editor = mSharedPreferences.edit();
                editor.putLong("timestamp", nowTime);
                editor.apply();
            } else if (nowTime - lastTime >= 604800000) {
                Editor editor1 = mSharedPreferences.edit();
                editor1.clear();
                editor1.apply();
                Editor editor2 = mSharedPreferences.edit();
                editor2.putLong("timestamp", nowTime);
                editor2.apply();
            }
        }
    }

    public static boolean b(Context context, String activityId, String packageName) {
        if (context == null) {
            return false;
        }
        Set<String> setData = context.getSharedPreferences("activity_upload", 8).getStringSet(activityId, null);
        if (setData == null || setData.size() < 1 || TextUtils.isEmpty(packageName) || !setData.contains(packageName)) {
            return false;
        }
        return true;
    }

    public static void a(Context context, String activityId, List<String> packageNames) {
        if (context != null && packageNames != null && packageNames.size() >= 1) {
            SharedPreferences mSharedPreferences = context.getSharedPreferences("activity_upload", 8);
            Set<String> setData = mSharedPreferences.getStringSet(activityId, null);
            Editor editor = mSharedPreferences.edit();
            for (String pkgName : packageNames) {
                if (setData == null) {
                    setData = new HashSet();
                }
                setData.add(pkgName);
            }
            editor.putStringSet(activityId, setData);
            editor.apply();
        }
    }

    public static void d(Context context, String saveStr) {
        Editor editor2 = context.getSharedPreferences("plugin_save", 8).edit();
        editor2.putString("lastlist", saveStr);
        editor2.apply();
    }

    public static String b(Context context) {
        return context.getSharedPreferences("plugin_save", 8).getString("lastlist", "[]");
    }

    public static Boolean c(Context context) {
        return Boolean.valueOf(context.getSharedPreferences("plugin_save", 8).getBoolean("firstopen", true));
    }

    public static void a(Context context, Boolean firstOpen) {
        Editor editor2 = context.getSharedPreferences("plugin_save", 8).edit();
        editor2.putBoolean("firstopen", firstOpen.booleanValue());
        editor2.apply();
    }

    public static int d(Context context) {
        return context.getSharedPreferences("plugin_save", 8).getInt("pattlecolor", -419430401);
    }

    public static void a(Context context, int pattleColor) {
        Editor editor2 = context.getSharedPreferences("plugin_save", 8).edit();
        editor2.putInt("pattlecolor", pattleColor);
        editor2.apply();
    }

    public static boolean e(Context context) {
        return context.getSharedPreferences("plugin_save", 8).getBoolean("wallpaperChanged", false);
    }

    public static void a(Context context, boolean isChanged) {
        Editor editor2 = context.getSharedPreferences("plugin_save", 8).edit();
        editor2.putBoolean("wallpaperChanged", isChanged);
        editor2.apply();
    }

    public static boolean f(Context context) {
        return context.getSharedPreferences("plugin_save", 8).getBoolean("wallpaperSavedSuccess", false);
    }

    public static void b(Context context, boolean success) {
        Editor editor2 = context.getSharedPreferences("plugin_save", 8).edit();
        editor2.putBoolean("wallpaperSavedSuccess", success);
        editor2.apply();
    }
}

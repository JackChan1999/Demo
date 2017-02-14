package com.meizu.cloud.app.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Pair;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meizu.cloud.app.utils.j;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class m {
    public static final String a = m.class.getSimpleName();

    public static class a {
        public static List<Pair<String, Integer>> a(Context context, String spName) {
            return b(context, spName);
        }

        public static int a(Context context, String packageName, String spName) {
            return d(context, spName, packageName);
        }

        public static synchronized void a(Context context, JSONArray ignoreApps, String spName) {
            synchronized (a.class) {
                a(context, spName, ignoreApps);
            }
        }

        public static synchronized void a(Context context, String packageName, int versionCode, String spName) {
            synchronized (a.class) {
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("pkg", (Object) packageName);
                jsonObject.put("ver", Integer.valueOf(versionCode));
                jsonArray.add(jsonObject);
                a(context, jsonArray, spName);
            }
        }

        public static synchronized void b(Context context, String appPackage, String spName) {
            synchronized (a.class) {
                f(context, spName, appPackage);
            }
        }

        public static boolean c(Context context, String packageName, String spName) {
            return e(context, spName, packageName);
        }

        private static List<Pair<String, Integer>> b(Context context, String preferenceName) {
            SharedPreferences sp = context.getSharedPreferences(preferenceName, 0);
            List<Pair<String, Integer>> appInfos = null;
            if (sp != null) {
                Set<? extends Entry<String, ?>> set = sp.getAll().entrySet();
                appInfos = new ArrayList(set.size());
                for (Entry<String, ?> entry : set) {
                    appInfos.add(Pair.create(entry.getKey(), (Integer) entry.getValue()));
                }
            }
            return appInfos;
        }

        private static int d(Context context, String preferenceName, String packageName) {
            if (packageName == null || packageName.length() <= 0 || !e(context, preferenceName, packageName)) {
                return -1;
            }
            SharedPreferences sp = context.getSharedPreferences(preferenceName, 0);
            if (sp != null) {
                return sp.getInt(packageName, -1);
            }
            return -1;
        }

        private static boolean e(Context context, String preferenceName, String packageName) {
            if (packageName == null || packageName.length() <= 0) {
                return false;
            }
            SharedPreferences sp = context.getSharedPreferences(preferenceName, 0);
            if (sp != null) {
                return sp.contains(packageName);
            }
            return false;
        }

        private static synchronized void a(Context context, String preferenceName, JSONArray ignoreApps) {
            synchronized (a.class) {
                if (ignoreApps != null) {
                    SharedPreferences sp = context.getSharedPreferences(preferenceName, 0);
                    if (sp != null) {
                        Editor editor = sp.edit();
                        for (int i = 0; i < ignoreApps.size(); i++) {
                            JSONObject jsonObject = (JSONObject) ignoreApps.get(i);
                            String pkg = jsonObject.getString("pkg").trim();
                            Integer ver = jsonObject.getInteger("ver");
                            if (!(TextUtils.isEmpty(pkg) || ver == null)) {
                                editor.putInt(pkg, ver.intValue());
                            }
                        }
                        editor.apply();
                        j.b(m.a, "App ignore mark saved!");
                    }
                }
            }
        }

        private static synchronized void f(Context context, String preferenceName, String appPackage) {
            synchronized (a.class) {
                SharedPreferences sp = context.getSharedPreferences(preferenceName, 0);
                if (sp != null && sp.contains(appPackage)) {
                    Editor editor = sp.edit();
                    editor.remove(appPackage);
                    editor.apply();
                    j.b(m.a, "Ignore mark remove!");
                }
            }
        }
    }

    public static class b {
        public static JSONArray a(Context context) {
            return JSON.parseArray(context.getSharedPreferences("update_apps_info", 0).getString("last_check_app", "[]"));
        }

        public static synchronized void a(Context context, String json) {
            synchronized (b.class) {
                SharedPreferences sp = context.getSharedPreferences("update_apps_info", 0);
                if (sp.edit().clear().commit()) {
                    sp.edit().putString("last_check_app", json).commit();
                }
            }
        }

        public static synchronized void a(Context context, JSONArray jsonArray) {
            synchronized (b.class) {
                JSONArray array = a(context);
                if (array.size() > 0) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        boolean isFound = false;
                        for (int j = 0; j < array.size(); j++) {
                            if (object.get("package_name").equals(array.getJSONObject(j).get("package_name"))) {
                                array.set(j, object);
                                isFound = true;
                                break;
                            }
                        }
                        if (!isFound) {
                            array.add(object);
                        }
                    }
                } else {
                    array.addAll(jsonArray);
                }
                a(context, array.toJSONString());
            }
        }
    }

    public static class c {
        public static void a(Context context, String pkgName) {
            context.getSharedPreferences("sys_app_update_mark", 0).edit().putBoolean(pkgName, true).apply();
        }

        public static void b(Context context, String pkgName) {
            context.getSharedPreferences("sys_app_update_mark", 0).edit().remove(pkgName).apply();
        }

        public static Set<String> a(Context context) {
            return context.getSharedPreferences("sys_app_update_mark", 0).getAll().keySet();
        }
    }

    public static class d {
        public static void a(Context context, String key, long timeStamp) {
            context.getSharedPreferences("timestamp", 0).edit().putLong(key, timeStamp).apply();
        }

        public static long a(Context context, String key) {
            return context.getSharedPreferences("timestamp", 0).getLong(key, 0);
        }
    }
}

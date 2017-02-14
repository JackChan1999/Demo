package com.meizu.cloud.app.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.l;
import com.android.volley.n.b;
import com.android.volley.s;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.model.SystemAppsInfo;
import com.meizu.cloud.app.utils.g;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class j {
    private static final String a = j.class.getSimpleName();
    private static String b = "/public/history/white_list";

    public static class a {
        public static Set<String> a(Context context) {
            return context.getSharedPreferences("system_app_list", 0).getStringSet("system_apps", new HashSet(0));
        }

        public static long b(Context context) {
            return context.getSharedPreferences("system_app_list", 0).getLong("last_edit_time", -1);
        }

        public static void a(Context context, long time) {
            context.getSharedPreferences("system_app_list", 0).edit().putLong("last_request_time", time).apply();
        }

        public static long c(Context context) {
            return context.getSharedPreferences("system_app_list", 0).getLong("last_request_time", -1);
        }

        public static void a(Context context, SystemAppsInfo systemAppsInfo) {
            SharedPreferences preferences = context.getSharedPreferences("system_app_list", 0);
            preferences.edit().putStringSet("system_apps", systemAppsInfo.data).apply();
            Editor edit = preferences.edit();
            String str = "last_edit_time";
            long longValue = (systemAppsInfo.update_time == null || !g.c(systemAppsInfo.update_time)) ? 0 : Long.valueOf(systemAppsInfo.update_time).longValue();
            edit.putLong(str, longValue).apply();
            i.a(systemAppsInfo.data);
            for (String packageName : systemAppsInfo.data) {
                if (i.a(context, packageName) != null) {
                    x.d(context).b(packageName);
                }
            }
        }
    }

    public static void a(final Context context) {
        TypeReference reference = new TypeReference<ResultModel<SystemAppsInfo>>() {
        };
        List paramPairs = new ArrayList();
        paramPairs.add(new com.meizu.volley.b.a("update_time", String.valueOf(a.b(context))));
        l jsonRequest = new FastJsonRequest(reference, RequestConstants.getRuntimeDomainUrl(context, b), 0, paramPairs, new b<ResultModel<SystemAppsInfo>>() {
            public void a(ResultModel<SystemAppsInfo> response) {
                if (response == null || response.getCode() != 200 || response.getValue() == null) {
                    Log.d(j.a, "Get system app list failed.");
                    return;
                }
                SystemAppsInfo systemAppsInfo = (SystemAppsInfo) response.getValue();
                long lastEditedTime = a.b(context);
                long serverEditedTime = -1;
                if (!TextUtils.isEmpty(systemAppsInfo.update_time) && g.c(systemAppsInfo.update_time)) {
                    serverEditedTime = Long.valueOf(systemAppsInfo.update_time).longValue();
                }
                if (serverEditedTime != lastEditedTime || serverEditedTime == -1) {
                    a.a(context, systemAppsInfo);
                }
                a.a(context, System.currentTimeMillis());
            }
        }, new com.android.volley.n.a() {
            public void a(s error) {
                Log.d(j.a, "Get system app list failed.");
            }
        });
        jsonRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(context));
        com.meizu.volley.b.a(context).a().a(jsonRequest);
    }
}

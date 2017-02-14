package com.meizu.update.push;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.update.g.a;
import com.meizu.update.h.g;
import com.meizu.update.service.MzUpdateComponentService;
import org.json.JSONException;
import org.json.JSONObject;

public class b {
    public static boolean a(Context context, String data) {
        try {
            String packageName = context.getPackageName();
            JSONObject object = new JSONObject(data);
            if (object.has(packageName)) {
                try {
                    a.a(context).a(a.a.PushMessageReceived, object.getJSONObject(packageName).getString("version"), g.b(context, packageName));
                } catch (Exception e) {
                }
                return true;
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
            com.meizu.update.h.b.c("unknown server push : " + data);
        }
        return false;
    }

    public static boolean a(Context context, Intent intent) {
        try {
            String packageName = context.getPackageName();
            String extraPackageInfo = intent.getExtras().getString(packageName);
            if (!TextUtils.isEmpty(extraPackageInfo)) {
                try {
                    a.a(context).a(a.a.PushMessageReceived, new JSONObject(extraPackageInfo).getString("version"), g.b(context, packageName));
                } catch (Exception e) {
                }
                return true;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            com.meizu.update.h.b.c("unknown server push : " + intent);
        }
        return false;
    }

    public static void a(Context context) {
        if (!TextUtils.isEmpty(PushManager.getPushId(context)) && !b(context)) {
            MzUpdateComponentService.b(context);
        }
    }

    public static final void b(Context context, String skipVersion) {
        Editor e = c(context).edit();
        e.putString("skip_version", skipVersion);
        e.apply();
    }

    public static final boolean c(Context context, String version) {
        String skipVersion = c(context).getString("skip_version", null);
        if (TextUtils.isEmpty(skipVersion) || !skipVersion.equals(version)) {
            return false;
        }
        return true;
    }

    public static final void a(Context context, boolean register) {
        Editor e = c(context).edit();
        if (register) {
            e.putString("push_version", g.a(context));
        } else {
            e.putString("push_version", "");
        }
        e.apply();
    }

    public static final boolean b(Context context) {
        String pushVersion = c(context).getString("push_version", null);
        if (TextUtils.isEmpty(pushVersion)) {
            return false;
        }
        return pushVersion.equals(g.a(context));
    }

    public static final SharedPreferences c(Context context) {
        return context.getSharedPreferences("mz_update_component_history", 0);
    }

    public static final void d(Context context) {
        if (!e(context)) {
            com.meizu.update.h.b.c("cloud server not enable, skip register");
        } else if (TextUtils.isEmpty(PushManager.getPushId(context))) {
            com.meizu.update.h.b.b(context, "Request sip register");
            PushManager.register(context);
        } else if (!b(context)) {
            MzUpdateComponentService.b(context);
        }
    }

    public static final boolean e(Context context) {
        if (TextUtils.isEmpty(g.b(context, "com.meizu.cloud"))) {
            return false;
        }
        return true;
    }
}

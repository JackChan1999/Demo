package com.meizu.statsapp;

import android.util.Log;
import com.meizu.cloud.pushsdk.constants.MeizuConstants;
import defpackage.aov;

public class UsageStatusLog {
    private static final String TAG = "UsageStats_";
    public static boolean sDebug = false;

    public static void initLog() {
        try {
            Object a = aov.a(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES, "get", new Object[]{"persist.meizu.usagestats.debug"});
            if (a != null) {
                sDebug = Boolean.valueOf(a.toString()).booleanValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void d(String str, String str2) {
        if (sDebug) {
            Log.d(TAG + str, str2);
        }
    }

    public static void d(String str, String str2, Throwable th) {
        if (sDebug) {
            Log.d(TAG + str, str2, th);
        }
    }
}

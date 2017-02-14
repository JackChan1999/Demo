package com.meizu.statsapp;

import android.util.Log;
import com.meizu.cloud.pushsdk.constants.MeizuConstants;
import com.meizu.statsapp.util.b;

public class UsageStatusLog {
    private static final String TAG = "UsageStats_";
    public static boolean sDebug = false;

    public static void initLog() {
        try {
            Object temp = b.a(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES, "get", new Object[]{"persist.meizu.usagestats.debug"});
            if (temp != null) {
                sDebug = Boolean.valueOf(temp.toString()).booleanValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void d(String tag, String msg) {
        if (sDebug) {
            Log.d(TAG + tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (sDebug) {
            Log.d(TAG + tag, msg, tr);
        }
    }
}

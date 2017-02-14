package com.meizu.cloud.pushsdk.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PushPreferencesUtils {
    private static final String MZ_PUSH_PREFERENCE = "mz_push_preference";
    private static final String MZ_PUSH_PREFIX_NOTIFICATION_ID = ".notification_id";
    private static final String MZ_PUSH_PREFIX_PUSH_TASK_ID = ".notification_push_task_id";

    public static SharedPreferences getSharePerferenceByName(Context context, String str) {
        return context.getSharedPreferences(str, 0);
    }

    public static void putStringByKey(Context context, String str, String str2, String str3) {
        getSharePerferenceByName(context, str).edit().putString(str2, str3).commit();
    }

    public static void putIntBykey(Context context, String str, String str2, int i) {
        getSharePerferenceByName(context, str).edit().putInt(str2, i).commit();
    }

    public static String getStringBykey(Context context, String str, String str2) {
        return getSharePerferenceByName(context, str).getString(str2, null);
    }

    public static void putPushIdByPkg(Context context, String str, String str2) {
        putStringByKey(context, MZ_PUSH_PREFERENCE, str, str2);
    }

    public static String getPushIdByPkg(Context context, String str) {
        return getSharePerferenceByName(context, MZ_PUSH_PREFERENCE).getString(str, null);
    }

    public static void putDiscardNotificationIdByPackageName(Context context, String str, int i) {
        putIntBykey(context, MZ_PUSH_PREFERENCE, str + MZ_PUSH_PREFIX_NOTIFICATION_ID, i);
    }

    public static int getDiscardNotificationId(Context context, String str) {
        return getSharePerferenceByName(context, MZ_PUSH_PREFERENCE).getInt(str + MZ_PUSH_PREFIX_NOTIFICATION_ID, 0);
    }

    public static void putDiscardNotificationTaskId(Context context, String str, int i) {
        putIntBykey(context, MZ_PUSH_PREFERENCE, str + MZ_PUSH_PREFIX_PUSH_TASK_ID, i);
    }

    public static int getDiscardNotificationTaskId(Context context, String str) {
        return getSharePerferenceByName(context, MZ_PUSH_PREFERENCE).getInt(str + MZ_PUSH_PREFIX_PUSH_TASK_ID, 0);
    }
}

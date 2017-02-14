package com.meizu.cloud.pushsdk;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.util.SystemUtils;

public class PushManager {
    static final String KEY_PUSH_ID = "pushId";
    static final String PUSH_ID_PREFERENCE_NAME = "com.meizu.flyme.push";
    private static final String TAG = "PushManager-v1.2.3";

    public static String getPushId(Context context) {
        return context.getSharedPreferences(PUSH_ID_PREFERENCE_NAME, 0).getString(KEY_PUSH_ID, null);
    }

    public static void register(Context context) {
        String appVersionName = SystemUtils.getAppVersionName(context, "com.meizu.cloud");
        Log.i(TAG, context.getPackageName() + " start register cloudVersion_name " + appVersionName);
        Intent intent = new Intent(PushConstants.MZ_PUSH_ON_START_PUSH_REGISTER);
        if ("com.meizu.cloud".equals(SystemUtils.getMzPushServicePackageName(context))) {
            intent.setClassName("com.meizu.cloud", "com.meizu.cloud.pushsdk.pushservice.MzPushService");
            intent.putExtra("sender", context.getPackageName());
        } else if (TextUtils.isEmpty(appVersionName) || !SystemUtils.compareVersion(appVersionName, "4.5.7")) {
            Log.i(TAG, "flyme 3.x start register cloud versionName " + appVersionName);
            intent.setAction(PushConstants.REQUEST_REGISTRATION_INTENT);
            intent.setPackage("com.meizu.cloud");
            intent.putExtra(PushConstants.EXTRA_APPLICATION_PENDING_INTENT, PendingIntent.getBroadcast(context, 0, new Intent(), 0));
            intent.putExtra("sender", context.getPackageName());
        } else {
            Log.i(TAG, "flyme 4.x start register cloud versionName " + appVersionName);
            intent.setPackage("com.meizu.cloud");
            intent.putExtra("sender", context.getPackageName());
        }
        context.startService(intent);
    }

    public static void unRegister(Context context) {
        Object appVersionName = SystemUtils.getAppVersionName(context, "com.meizu.cloud");
        Log.i(TAG, context.getPackageName() + " start unRegister cloud versionName " + appVersionName);
        Intent intent = new Intent(PushConstants.MZ_PUSH_ON_STOP_PUSH_REGISTER);
        if ("com.meizu.cloud".equals(SystemUtils.getMzPushServicePackageName(context))) {
            intent.setClassName("com.meizu.cloud", "com.meizu.cloud.pushsdk.pushservice.MzPushService");
            intent.putExtra("sender", context.getPackageName());
        } else if (TextUtils.isEmpty(appVersionName) || !SystemUtils.compareVersion(appVersionName, "4.5.7")) {
            intent.setAction(PushConstants.REQUEST_UNREGISTRATION_INTENT);
            intent.setPackage("com.meizu.cloud");
            intent.putExtra(PushConstants.EXTRA_APPLICATION_PENDING_INTENT, PendingIntent.getBroadcast(context, 0, new Intent(), 0));
            intent.putExtra("sender", context.getPackageName());
        } else {
            intent.setPackage("com.meizu.cloud");
            intent.putExtra("sender", context.getPackageName());
        }
        context.startService(intent);
    }
}

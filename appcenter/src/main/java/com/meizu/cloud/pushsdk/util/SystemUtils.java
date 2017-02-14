package com.meizu.cloud.pushsdk.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.meizu.cloud.pushsdk.constants.PushConstants;

public class SystemUtils {
    public static boolean compareVersion(String str, String str2) {
        String[] split = str.split("\\.");
        String[] split2 = str2.split("\\.");
        int min = Math.min(split.length, split2.length);
        int i = 0;
        for (int i2 = 0; i2 < min; i2++) {
            i = split[i2].length() - split2[i2].length();
            if (i != 0) {
                break;
            }
            i = split[i2].compareTo(split2[i2]);
            if (i != 0) {
                break;
            }
        }
        if (i == 0) {
            i = split.length - split2.length;
        }
        return i >= 0;
    }

    public static String getAppVersionName(Context context, String str) {
        String str2;
        Throwable e;
        String str3 = "";
        try {
            str2 = context.getPackageManager().getPackageInfo(str, 0).versionName;
            if (str2 != null) {
                try {
                    if (str2.length() > 0) {
                        return str2;
                    }
                } catch (Exception e2) {
                    e = e2;
                    Log.e("VersionInfo", "Exception", e);
                    return str2;
                }
            }
            return "";
        } catch (Throwable e3) {
            Throwable th = e3;
            str2 = str3;
            e = th;
            Log.e("VersionInfo", "Exception", e);
            return str2;
        }
    }

    public static String getCurProcessName(Context context) {
        int myPid = Process.myPid();
        for (RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == myPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    public static String getMzPushServicePackageName(Context context) {
        String packageName = context.getPackageName();
        try {
            Object servicesByPackageName = getServicesByPackageName(context, "com.meizu.cloud");
            if (!TextUtils.isEmpty(servicesByPackageName) && servicesByPackageName.contains("mzservice_v1")) {
                return "com.meizu.cloud";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("SystemUtils", "startservice package name " + packageName);
        return packageName;
    }

    private static String getServicesByContext(Context context) {
        ServiceInfo[] serviceInfoArr;
        try {
            serviceInfoArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 4).services;
        } catch (NameNotFoundException e) {
            serviceInfoArr = null;
        }
        if (serviceInfoArr == null) {
            return null;
        }
        for (int i = 0; i < serviceInfoArr.length; i++) {
            Log.i("SystemUtils", "serviceinfo name " + serviceInfoArr[i].name + " processName " + serviceInfoArr[i].processName);
            if ("com.meizu.cloud.pushsdk.pushservice.MzPushService".equals(serviceInfoArr[i].name)) {
                return serviceInfoArr[i].processName;
            }
        }
        return null;
    }

    private static String getServicesByPackageName(Context context, String str) {
        ServiceInfo[] serviceInfoArr;
        try {
            serviceInfoArr = context.getPackageManager().getPackageInfo(str, 4).services;
        } catch (NameNotFoundException e) {
            serviceInfoArr = null;
        }
        if (serviceInfoArr == null) {
            return null;
        }
        for (int i = 0; i < serviceInfoArr.length; i++) {
            Log.i("SystemUtils", "serviceinfo name " + serviceInfoArr[i].name + " processName " + serviceInfoArr[i].processName);
            if ("com.meizu.cloud.pushsdk.pushservice.MzPushService".equals(serviceInfoArr[i].name)) {
                return serviceInfoArr[i].processName;
            }
        }
        return null;
    }
}

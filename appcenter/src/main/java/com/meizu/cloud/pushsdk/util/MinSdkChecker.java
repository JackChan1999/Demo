package com.meizu.cloud.pushsdk.util;

import android.os.Build.VERSION;

public class MinSdkChecker {
    public static boolean isSupportBigTextStyleAndAction() {
        return VERSION.SDK_INT >= 16;
    }

    public static boolean isSupportDeviceDefaultLight() {
        return VERSION.SDK_INT >= 14;
    }

    public static boolean isSupportKeyguardState() {
        return VERSION.SDK_INT >= 16;
    }

    public static boolean isSupportNotificationBuild() {
        return VERSION.SDK_INT >= 16;
    }

    public static boolean isSupportSendNotification() {
        return VERSION.SDK_INT >= 21;
    }
}

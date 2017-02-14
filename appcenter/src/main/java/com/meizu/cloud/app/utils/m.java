package com.meizu.cloud.app.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class m {
    public static boolean a(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
        if (cm != null) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isAvailable()) {
                if (info.getType() == 1) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public static boolean b(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected() && info.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    public static String c(Context context) {
        String mNetWorkType = "";
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return "none";
        }
        String type = networkInfo.getTypeName();
        if (type.equalsIgnoreCase("WIFI")) {
            return "wifi";
        }
        if (!type.equalsIgnoreCase("MOBILE")) {
            return mNetWorkType;
        }
        if (!TextUtils.isEmpty(Proxy.getDefaultHost())) {
            return "wap";
        }
        int mobile = d(context);
        if (mobile == 2) {
            return "2g";
        }
        if (mobile == 3) {
            return "3g";
        }
        return "4g";
    }

    private static int d(Context context) {
        switch (((TelephonyManager) context.getSystemService("phone")).getNetworkType()) {
            case 3:
                return 3;
            case 5:
                return 3;
            case 6:
                return 3;
            case 8:
                return 3;
            case 9:
                return 3;
            case 10:
                return 3;
            case 12:
                return 3;
            case 13:
                return 4;
            case 14:
                return 3;
            case 15:
                return 3;
            default:
                return 2;
        }
    }
}

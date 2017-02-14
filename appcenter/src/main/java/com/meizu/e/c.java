package com.meizu.e;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import java.util.Locale;

public class c {
    public static String a = "App-Version";

    public static final String a() {
        return Locale.getDefault().getLanguage().toLowerCase() + "-" + Locale.getDefault().getCountry().toLowerCase();
    }

    public static final String a(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isAvailable()) {
                    if (info.getType() == 1) {
                        return "wifi";
                    }
                    TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
                    if (tm.getNetworkType() == 1 || tm.getNetworkType() == 2) {
                        return "2g";
                    }
                    return "3g";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "none";
    }
}

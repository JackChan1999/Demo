package com.meizu.gslb.g;

import android.content.Context;
import android.telephony.TelephonyManager;

public class f {
    public static String a(Context context) {
        return b(context);
    }

    private static String b(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        return telephonyManager.getSimState() == 5 ? telephonyManager.getSimOperator() : "";
    }
}

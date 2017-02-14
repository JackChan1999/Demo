package com.meizu.cloud.c.a;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.meizu.cloud.c.c.b;
import com.meizu.cloud.c.c.c;

public class a {
    private static String a;
    private static String b;
    private static String c;
    private static Boolean d;

    public static synchronized String a(Context context) {
        String str;
        synchronized (a.class) {
            if (a == null) {
                a = c.a(context, "ro.serialno");
            }
            Log.e("PhoneUtils", "Get Mz Phone SN " + a + "XXX");
            str = a;
        }
        return str;
    }

    public static synchronized String a() {
        String str;
        synchronized (a.class) {
            if (TextUtils.isEmpty(b)) {
                if (b()) {
                    b = Build.MODEL;
                } else {
                    try {
                        b = (String) b.a("android.os.BuildExt", "MZ_MODEL");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (TextUtils.isEmpty(b) || b.toLowerCase().equals("unknown")) {
                    Log.e("PhoneUtils", "get Mz Phone Model returns null or UNKNOWN");
                    b = Build.MODEL;
                }
            }
            str = b;
        }
        return str;
    }

    public static synchronized String b(Context context) {
        String str;
        synchronized (a.class) {
            if (TextUtils.isEmpty(c)) {
                try {
                    String MZ_T_M = "android.telephony.MzTelephonyManager";
                    String METHOD_GET_DEVICE_ID = "getDeviceId";
                    c = (String) b.a("android.telephony.MzTelephonyManager", "getDeviceId", null, null);
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }
                if (TextUtils.isEmpty(c)) {
                    c = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
                }
            }
            Log.e("PhoneUtils", "Get Mz Phone IMEI " + c);
            str = c;
        }
        return str;
    }

    public static synchronized boolean b() {
        boolean booleanValue;
        synchronized (a.class) {
            if (d != null) {
                booleanValue = d.booleanValue();
            } else {
                try {
                    d = ((Boolean) b.a("android.os.BuildExt", "isFlymeRom", null)).booleanValue() ? Boolean.TRUE : Boolean.FALSE;
                    booleanValue = d.booleanValue();
                } catch (Exception e) {
                    e.printStackTrace();
                    booleanValue = false;
                }
            }
        }
        return booleanValue;
    }
}

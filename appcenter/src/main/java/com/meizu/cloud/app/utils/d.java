package com.meizu.cloud.app.utils;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.meizu.cloud.c.a.a;
import com.meizu.cloud.c.b.a.c;
import com.meizu.cloud.c.b.b;
import java.io.File;
import java.util.Locale;

public class d {
    public static int a = -1;
    public static int b = -1;
    private static int c = -1;
    private static int d = -1;
    private static Boolean e = null;
    private static Boolean f = null;
    private static Boolean g = null;

    public static String a(Context context) {
        return a.b(context);
    }

    public static String b(Context context) {
        return a.a(context);
    }

    public static String a() {
        return Build.DISPLAY;
    }

    public static String a(String imei, String sn) {
        String device = imei;
        if (TextUtils.isEmpty(device)) {
            device = "000000000000000";
        }
        if (TextUtils.isEmpty(sn)) {
            return device;
        }
        return device + "_" + sn;
    }

    public static void c(Context context) {
        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService("window");
        a = windowManager.getDefaultDisplay().getWidth();
        b = windowManager.getDefaultDisplay().getHeight();
    }

    public static int b() {
        return a;
    }

    public static String c() {
        return "" + a + "x" + b;
    }

    public static String d() {
        String res = "";
        if (!TextUtils.isEmpty(Locale.getDefault().getLanguage())) {
            res = res + Locale.getDefault().getLanguage().toLowerCase() + "-";
        }
        if (TextUtils.isEmpty(Locale.getDefault().getCountry().toUpperCase())) {
            return res;
        }
        return res + Locale.getDefault().getCountry().toUpperCase();
    }

    public static String e() {
        return a.a();
    }

    public static int d(Context context) {
        if (c < 0) {
            TypedValue tv = new TypedValue();
            if (context.getTheme().resolveAttribute(16843499, tv, true)) {
                c = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
            } else {
                c = 120;
            }
        }
        return c;
    }

    public static int e(Context context) {
        if (d < 0) {
            try {
                Class<?> c = c.a().a("com.android.internal.R$dimen");
                d = context.getResources().getDimensionPixelSize(Integer.parseInt(c.getField("status_bar_height").get(c.newInstance()).toString()));
            } catch (Exception e) {
                e.printStackTrace();
                d = 62;
            }
        }
        return d;
    }

    public static int f(Context context) {
        return d(context) + e(context);
    }

    public static void a(Context context, ViewGroup viewGroup) {
        viewGroup.setClipChildren(false);
        viewGroup.setClipToPadding(false);
        viewGroup.setPadding(viewGroup.getPaddingLeft(), viewGroup.getPaddingTop() + f(context), viewGroup.getPaddingRight(), viewGroup.getPaddingBottom() + d(context));
    }

    public static void b(Context context, ViewGroup viewGroup) {
        viewGroup.setClipChildren(false);
        viewGroup.setClipToPadding(false);
        viewGroup.setPadding(viewGroup.getPaddingLeft(), viewGroup.getPaddingTop() + f(context), viewGroup.getPaddingRight(), viewGroup.getPaddingBottom());
    }

    public static String g(Context context) {
        return ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
    }

    public static String h(Context context) {
        return VERSION.RELEASE;
    }

    public static String i(Context context) {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        return Formatter.formatFileSize(context, ((long) stat.getBlockSize()) * ((long) stat.getBlockCount()));
    }

    public static String j(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getSimOperatorName();
    }

    public static String f() {
        int bool = 0;
        try {
            if (new File("/system/bin/su").exists() || new File("/system/xbin/su").exists()) {
                bool = 1;
            } else {
                bool = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(bool);
    }

    public static boolean k(Context context) {
        try {
            if (e != null) {
                return e.booleanValue();
            }
            String str = b.a(context, "ro.yunos.project");
            boolean z = !TextUtils.isEmpty(str) && Boolean.parseBoolean(str);
            Boolean valueOf = Boolean.valueOf(z);
            e = valueOf;
            return valueOf.booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean g() {
        try {
            if (f != null) {
                return f.booleanValue();
            }
            f = (Boolean) com.meizu.cloud.c.c.b.a("android.os.BuildExt", "isProductInternational", null);
            return f.booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean h() {
        if (g != null) {
            return g.booleanValue();
        }
        String ORIGINAL_CLASS_NAME = "flyme.config.FlymeFeature";
        String str = "YUNOS_PROJECT";
        try {
            g = (Boolean) com.meizu.cloud.c.c.b.a("flyme.config.FlymeFeature", "YUNOS_PROJECT");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return g.booleanValue();
    }

    public static String i() {
        return System.getProperty("http.agent");
    }

    public static void a(Activity mContext, boolean fullScreen) {
        Window win = mContext.getWindow();
        LayoutParams winParams = win.getAttributes();
        if (fullScreen) {
            winParams.flags |= 1024;
        } else {
            winParams.flags &= -1025;
        }
        win.setAttributes(winParams);
    }

    public static boolean l(Context context) {
        try {
            return b.a(context, "persist.sys.use.flyme.icon", "true").equals("true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

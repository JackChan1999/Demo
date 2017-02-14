package com.meizu.update.h;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.meizu.cloud.pushsdk.constants.MeizuConstants;
import com.meizu.f.a;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;

public class g {
    private static String a;
    private static String b;
    private static final char[] c = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static Boolean d = null;

    public static String a(Context context) {
        return a(context, context.getPackageName());
    }

    public static String a(Context context, String packageName) {
        String versionName = b(context, packageName);
        if (l(context) || !c()) {
            return versionName;
        }
        return versionName + "_i";
    }

    public static String b(Context context, String packageName) {
        try {
            return context.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    public static final String b(Context context) {
        String version = null;
        try {
            version = a("ro.build.mask.id", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(version)) {
            return Build.DISPLAY;
        }
        return version;
    }

    public static final String c(Context context) {
        return VERSION.RELEASE;
    }

    public static final String d(Context context) {
        if (l(context)) {
            return "All";
        }
        return a();
    }

    public static String a() {
        String model = null;
        if (!b()) {
            try {
                model = (String) a.a("android.os.BuildExt", "MZ_MODEL");
            } catch (Exception e) {
            }
        }
        if (TextUtils.isEmpty(model)) {
            return Build.MODEL;
        }
        return model;
    }

    public static boolean b() {
        boolean b = false;
        try {
            b = ((Boolean) a.a("android.os.BuildExt", "isFlymeRom", null)).booleanValue();
        } catch (Exception e) {
        }
        return b;
    }

    public static final String e(Context context) {
        return g(context);
    }

    public static final String f(Context context) {
        if (a == null) {
            a = a("ro.serialno", null);
        }
        return a;
    }

    public static String g(Context context) {
        if (TextUtils.isEmpty(b)) {
            String MZ_T_M;
            String METHOD_GET_DEVICE_ID;
            try {
                MZ_T_M = "android.telephony.MzTelephonyManager";
                METHOD_GET_DEVICE_ID = "getDeviceId";
                b = (String) a.a("android.telephony.MzTelephonyManager", "getDeviceId", null, null);
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            if (TextUtils.isEmpty(b)) {
                try {
                    MZ_T_M = "com.meizu.telephony.MzTelephonymanager";
                    METHOD_GET_DEVICE_ID = "getDeviceId";
                    b = (String) a.a("com.meizu.telephony.MzTelephonymanager", "getDeviceId", new Class[]{Context.class, Integer.TYPE}, new Object[]{context, Integer.valueOf(0)});
                } catch (Exception e) {
                }
            }
            if (TextUtils.isEmpty(b)) {
                b = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            }
        }
        return b;
    }

    public static final boolean c(Context context, String path) {
        try {
            File file = new File(path);
            if (file.isFile() && file.exists() && context.getPackageManager().getPackageArchiveInfo(path, 0) != null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static final PackageInfo d(Context context, String path) {
        try {
            File file = new File(path);
            if (file.isFile() && file.exists()) {
                return context.getPackageManager().getPackageArchiveInfo(path, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final long a(String path) {
        try {
            File file = new File(path);
            if (file.exists() && file.isFile()) {
                return file.length();
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public static final String h(Context context) {
        return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
    }

    public static String a(byte[] data) {
        int l = data.length;
        char[] out = new char[(l << 1)];
        int j = 0;
        for (int i = 0; i < l; i++) {
            int i2 = j + 1;
            out[j] = c[(data[i] & 240) >>> 4];
            j = i2 + 1;
            out[i2] = c[data[i] & 15];
        }
        return new String(out);
    }

    public static String b(String original) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(original.getBytes("utf-8"));
            return a(md5.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean c() {
        try {
            return ((Boolean) Class.forName("android.os.BuildExt").getMethod("isProductInternational", new Class[0]).invoke(null, new Object[0])).booleanValue();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return false;
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
            return false;
        } catch (IllegalAccessException e4) {
            e4.printStackTrace();
            return false;
        }
    }

    private static String a(String key, String defaultValue) {
        try {
            return (String) a.a(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES, "get", new Object[]{key});
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static boolean e(Context context, String packageName) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(packageName, 0);
            if (ai == null) {
                return false;
            }
            if ((ai.flags & 1) == 0 && (ai.flags & 128) == 0) {
                return false;
            }
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static final Bitmap a(String packageName, Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            Drawable iconDrawable = pm.getPackageInfo(packageName, 0).applicationInfo.loadIcon(pm);
            if (iconDrawable != null) {
                return a(iconDrawable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Bitmap a(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, drawable.getOpacity() != -1 ? Config.ARGB_8888 : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    public static boolean i(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
        if (connectivity == null) {
            return false;
        }
        NetworkInfo info = connectivity.getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            return false;
        }
        return true;
    }

    public static boolean j(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
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

    public static String a(double fFileSize) {
        int nSize;
        if (fFileSize < 1024.0d) {
            nSize = 0;
            if (fFileSize > 0.0d) {
                nSize = (int) fFileSize;
            }
            return String.format("%dB", new Object[]{Integer.valueOf(nSize)});
        } else if (fFileSize >= 1024.0d && fFileSize < 10240.0d) {
            return String.format("%dKB", new Object[]{Integer.valueOf((int) (fFileSize / 1024.0d))});
        } else if (fFileSize >= 10240.0d && fFileSize < 102400.0d) {
            return String.format("%dKB", new Object[]{Integer.valueOf((int) (fFileSize / 1024.0d))});
        } else if (fFileSize >= 102400.0d && fFileSize < 1048576.0d) {
            nSize = (int) (fFileSize / 1024.0d);
            return String.format("%dKB", new Object[]{Integer.valueOf(nSize)});
        } else if (fFileSize >= 1048576.0d && fFileSize < 1.048576E8d) {
            return String.format("%.2fMB", new Object[]{Double.valueOf(fFileSize / 1048576.0d)});
        } else if (fFileSize >= 1.048576E8d && fFileSize < 1.073741824E9d) {
            return String.format("%.1fMB", new Object[]{Double.valueOf(fFileSize / 1048576.0d)});
        } else if (fFileSize >= 1.073741824E9d && fFileSize < 1.073741824E10d) {
            return String.format("%.2fGB", new Object[]{Double.valueOf(fFileSize / 1.073741824E9d)});
        } else if (fFileSize < 1.073741824E10d || fFileSize >= 1.073741824E11d) {
            nSize = (int) (fFileSize / 1.073741824E9d);
            return String.format("%dGB", new Object[]{Integer.valueOf(nSize)});
        } else {
            return String.format("%.1fGB", new Object[]{Double.valueOf(fFileSize / 1.073741824E9d)});
        }
    }

    private static boolean l(Context context) {
        if (d == null) {
            try {
                ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                if (info.metaData != null) {
                    d = Boolean.valueOf(info.metaData.getBoolean("system_independent", false));
                    Log.d("MzUpdateComponent", "sSystemIndependent : " + d);
                }
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            if (d == null) {
                d = Boolean.valueOf(false);
            }
        }
        return d.booleanValue();
    }

    public static boolean d() {
        try {
            return ((Boolean) a.a("android.os.BuildExt", "IS_SHOPDEMO")).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    public static String k(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
        if (tm.getSimState() == 5) {
            return tm.getSimOperator();
        }
        return "";
    }
}

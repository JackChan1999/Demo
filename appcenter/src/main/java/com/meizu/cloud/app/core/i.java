package com.meizu.cloud.app.core;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import com.meizu.cloud.app.core.j.a;
import com.meizu.cloud.c.b.a.c;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class i {
    private static Set<String> a;

    public static void a(Set<String> particularApps) {
        a = particularApps;
    }

    public static List<Pair<String, Integer>> a(Context context, int fliter) {
        List<PackageInfo> packageInfos = context.getPackageManager().getInstalledPackages(0);
        List<Pair<String, Integer>> resultPackageInfos = new ArrayList();
        if (!g(context, context.getPackageName())) {
            fliter |= 4;
        }
        switch (fliter) {
            case 0:
                resultPackageInfos.clear();
                for (PackageInfo packageInfo : packageInfos) {
                    resultPackageInfos.add(Pair.create(packageInfo.packageName, Integer.valueOf(packageInfo.versionCode)));
                }
                break;
            case 1:
                resultPackageInfos.clear();
                for (PackageInfo packageInfo2 : packageInfos) {
                    if ((packageInfo2.applicationInfo.flags & 1) != 0) {
                        resultPackageInfos.add(Pair.create(packageInfo2.packageName, Integer.valueOf(packageInfo2.versionCode)));
                    }
                }
                break;
            case 2:
                resultPackageInfos.clear();
                for (PackageInfo packageInfo22 : packageInfos) {
                    if ((packageInfo22.applicationInfo.flags & 1) == 0 && (packageInfo22.applicationInfo.flags & 128) == 0 && !packageInfo22.packageName.equals(context.getPackageName())) {
                        resultPackageInfos.add(Pair.create(packageInfo22.packageName, Integer.valueOf(packageInfo22.versionCode)));
                    }
                }
                break;
            case 3:
                resultPackageInfos.clear();
                for (PackageInfo packageInfo222 : packageInfos) {
                    if ((packageInfo222.applicationInfo.flags & 262144) != 0) {
                        resultPackageInfos.add(Pair.create(packageInfo222.packageName, Integer.valueOf(packageInfo222.versionCode)));
                    }
                }
                break;
            case 5:
                resultPackageInfos.clear();
                for (PackageInfo packageInfo2222 : packageInfos) {
                    if (a(packageInfo2222.packageName, context) || ((packageInfo2222.applicationInfo.flags & 1) == 0 && (packageInfo2222.applicationInfo.flags & 128) == 0)) {
                        resultPackageInfos.add(Pair.create(packageInfo2222.packageName, Integer.valueOf(packageInfo2222.versionCode)));
                    }
                }
                break;
            default:
                packageInfos.clear();
                break;
        }
        return resultPackageInfos;
    }

    public static List<PackageInfo> b(Context context, int fliter) {
        List<PackageInfo> packageInfos = context.getPackageManager().getInstalledPackages(0);
        List<PackageInfo> resultPackageInfos = new ArrayList();
        if (!g(context, context.getPackageName())) {
            fliter |= 4;
        }
        switch (fliter) {
            case 0:
                resultPackageInfos.clear();
                resultPackageInfos.addAll(packageInfos);
                break;
            case 1:
                resultPackageInfos.clear();
                for (PackageInfo packageInfo : packageInfos) {
                    if ((packageInfo.applicationInfo.flags & 1) != 0) {
                        resultPackageInfos.add(packageInfo);
                    }
                }
                break;
            case 2:
                resultPackageInfos.clear();
                for (PackageInfo packageInfo2 : packageInfos) {
                    if ((packageInfo2.applicationInfo.flags & 1) == 0 && (packageInfo2.applicationInfo.flags & 128) == 0 && !packageInfo2.packageName.equals(context.getPackageName())) {
                        resultPackageInfos.add(packageInfo2);
                    }
                }
                break;
            case 3:
                resultPackageInfos.clear();
                for (PackageInfo packageInfo22 : packageInfos) {
                    if ((packageInfo22.applicationInfo.flags & 262144) != 0) {
                        resultPackageInfos.add(packageInfo22);
                    }
                }
                break;
            case 5:
                resultPackageInfos.clear();
                for (PackageInfo packageInfo222 : packageInfos) {
                    if (a(packageInfo222.packageName, context) || ((packageInfo222.applicationInfo.flags & 1) == 0 && (packageInfo222.applicationInfo.flags & 128) == 0)) {
                        resultPackageInfos.add(packageInfo222);
                    }
                }
                break;
            default:
                packageInfos.clear();
                break;
        }
        return resultPackageInfos;
    }

    public static boolean a(String packageName, Context context) {
        if (a == null) {
            a = a.a(context);
        }
        for (String s : a) {
            if (s.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    public static PackageInfo a(Context context, String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    public static Drawable b(Context context, String packageName) {
        Drawable appIcon = null;
        try {
            appIcon = context.getPackageManager().getApplicationIcon(packageName);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return appIcon;
    }

    public static Drawable c(Context context, String packageName) {
        return a(context, a(context, packageName));
    }

    public static Drawable a(Context context, PackageInfo packageInfo) {
        Drawable appIcon = null;
        if (packageInfo != null) {
            try {
                AssetManager assetManager = (AssetManager) AssetManager.class.newInstance();
                c.a().a(assetManager.getClass(), "addAssetPath", String.class).invoke(assetManager, new Object[]{packageInfo.applicationInfo.sourceDir});
                Resources defaultRes = context.getResources();
                Resources res = new Resources(assetManager, defaultRes.getDisplayMetrics(), defaultRes.getConfiguration());
                if (packageInfo.applicationInfo.icon != 0) {
                    appIcon = res.getDrawable(packageInfo.applicationInfo.icon);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return b(context, packageInfo.packageName);
            }
        }
        return appIcon;
    }

    public static Intent d(Context context, String packageName) {
        return context.getPackageManager().getLaunchIntentForPackage(packageName);
    }

    public static ApplicationInfo a(Context context) {
        ApplicationInfo info = null;
        try {
            info = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static final String e(Context context, String packageName) {
        try {
            return context.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static final int f(Context context, String packageName) {
        int i = 0;
        try {
            return context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return i;
        }
    }

    public static final String b(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            return pm.getApplicationLabel(pm.getApplicationInfo(context.getPackageName(), 0)).toString();
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static final boolean g(Context context, String pkgName) {
        try {
            return a(context.getPackageManager().getApplicationInfo(pkgName, 1));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static final boolean a(ApplicationInfo applicationInfo) {
        if ((applicationInfo.flags & 1) != 0) {
            return true;
        }
        return false;
    }

    public static final boolean c(Context context) {
        try {
            if (context.getPackageManager().getPackageInfo("com.meizu.mstore", 0) != null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

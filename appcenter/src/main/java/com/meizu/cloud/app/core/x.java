package com.meizu.cloud.app.core;

import a.a.a.c;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.meizu.cloud.app.c.b;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class x {
    public static final String a = x.class.getSimpleName();
    private static String d;
    private static x g;
    r b;
    ExecutorService c = Executors.newSingleThreadExecutor();
    private Context e;
    private Vector<Pair<String, Integer>> f = new Vector();
    private String h;
    private o i;
    private q j;

    private class a extends BroadcastReceiver {
        final /* synthetic */ x a;

        private a(x xVar) {
            this.a = xVar;
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String fullPackageName = intent.getDataString();
            String packageName = null;
            if (!TextUtils.isEmpty(fullPackageName)) {
                packageName = fullPackageName.substring(8);
            }
            if (!TextUtils.isEmpty(packageName)) {
                if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                    this.a.b(packageName);
                } else if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                    final boolean bReplace = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                    if (!bReplace || i.a(packageName, this.a.e)) {
                        this.a.c(packageName);
                    }
                    m.a.b(context, packageName, "ignore_update_apps");
                    m.a.b(context, packageName, "ignore_notify_apps");
                    d.a(this.a.e).d(packageName);
                    final String finalPackageName = packageName;
                    new Thread(new Runnable(this) {
                        final /* synthetic */ a c;

                        public void run() {
                            Process.setThreadPriority(10);
                            this.c.a.b.a(finalPackageName);
                            if (!bReplace) {
                                this.c.a.b.d(finalPackageName);
                            }
                        }
                    }).start();
                } else {
                    String launchAction = this.a.e();
                    if (!TextUtils.isEmpty(action) && !TextUtils.isEmpty(launchAction) && action.equals(launchAction)) {
                        f.a(this.a.e).a(packageName);
                    }
                }
            }
        }
    }

    public static boolean a(Context context) {
        if (PushConstants.EXTRA_APPLICATION_PENDING_INTENT.equals(c(context))) {
            return true;
        }
        return false;
    }

    public static boolean b(Context context) {
        if ("game".equals(c(context))) {
            return true;
        }
        return false;
    }

    public static String c(Context context) {
        if (TextUtils.isEmpty(d)) {
            d = i.a(context).metaData.getString("domain");
        }
        return d;
    }

    public c a(String packageName, int versionCode) {
        Iterator i$ = this.f.iterator();
        while (i$.hasNext()) {
            Pair<String, Integer> info = (Pair) i$.next();
            if (((String) info.first).equals(packageName)) {
                if (((Integer) info.second).intValue() == Integer.MAX_VALUE) {
                    return c.a(Integer.MAX_VALUE);
                }
                return c.a(versionCode - ((Integer) info.second).intValue());
            }
        }
        return c.a(Integer.MIN_VALUE);
    }

    public boolean a(String pkgName) {
        Iterator i$ = this.f.iterator();
        while (i$.hasNext()) {
            if (((String) ((Pair) i$.next()).first).equals(pkgName)) {
                return true;
            }
        }
        return false;
    }

    public List<Pair<String, Integer>> a() {
        return this.f;
    }

    public static final synchronized x d(Context context) {
        x xVar;
        synchronized (x.class) {
            if (g == null) {
                g = new x(context.getApplicationContext());
            }
            xVar = g;
        }
        return xVar;
    }

    public x(Context context) {
        this.e = context;
        this.f.addAll(i.a(this.e, 5));
        e(context);
    }

    private void e(final Context context) {
        if (System.currentTimeMillis() - m.d.a(context, "last_clean_time") > 604800000) {
            this.c.execute(new Runnable(this) {
                final /* synthetic */ x b;

                public void run() {
                    Process.setThreadPriority(10);
                    File[] files = new File(context.getCacheDir(), "app_icons").listFiles();
                    if (files != null && files.length > 0) {
                        for (File icon : files) {
                            String name = icon.getName();
                            boolean isFound = false;
                            Iterator i$ = this.b.f.iterator();
                            while (i$.hasNext()) {
                                if (((String) ((Pair) i$.next()).first).equals(name)) {
                                    isFound = true;
                                    break;
                                }
                            }
                            if (!isFound) {
                                Log.d(x.a, "clean + " + name + " icon result:" + icon.delete());
                            }
                        }
                        m.d.a(context, "last_clean_time", System.currentTimeMillis());
                    }
                }
            });
        }
    }

    public void b() {
        this.j = q.a(this.e);
        this.j.a();
        this.j.c();
        this.i = new o(this.e);
        this.i.b();
        this.b = r.a(this.e);
        d();
        if (a(this.e)) {
            if (System.currentTimeMillis() - com.meizu.cloud.app.core.j.a.c(this.e) >= 86400000) {
                j.a(this.e);
            }
        }
    }

    public void b(String packageName) {
        PackageInfo packageInfo = i.a(this.e, packageName);
        if (packageInfo != null) {
            String from = this.e.getPackageManager().getInstallerPackageName(packageName);
            if (!i.a(packageInfo.applicationInfo) || i.a(packageName, this.e)) {
                boolean found = a(packageInfo, from);
                this.i.b();
                if (!this.e.getPackageName().equals(from)) {
                    this.j.a(packageName);
                    if (!found) {
                        d.a(this.e.getApplicationContext()).c(packageName);
                    }
                }
            }
        }
    }

    public boolean a(PackageInfo packageInfo, String from) {
        boolean isFound = false;
        if (packageInfo != null) {
            int i = 0;
            while (i < this.f.size()) {
                Pair<String, Integer> info = (Pair) this.f.get(i);
                if (((String) info.first).equals(packageInfo.packageName)) {
                    if (((Integer) info.second).intValue() != packageInfo.versionCode) {
                        this.f.set(i, Pair.create(packageInfo.packageName, Integer.valueOf(packageInfo.versionCode)));
                    }
                    isFound = true;
                    c.a().d(new b(new s(), true, packageInfo.packageName));
                    if (!isFound) {
                        this.f.addElement(Pair.create(packageInfo.packageName, Integer.valueOf(packageInfo.versionCode)));
                        c.a().d(new com.meizu.cloud.app.c.a(from, packageInfo.packageName, 1));
                    }
                } else {
                    i++;
                }
            }
            if (isFound) {
                this.f.addElement(Pair.create(packageInfo.packageName, Integer.valueOf(packageInfo.versionCode)));
                c.a().d(new com.meizu.cloud.app.c.a(from, packageInfo.packageName, 1));
            }
        }
        return isFound;
    }

    public boolean c(String packageName) {
        Iterator i$ = this.f.iterator();
        while (i$.hasNext()) {
            Pair<String, Integer> appInfo = (Pair) i$.next();
            if (packageName.equals(appInfo.first)) {
                boolean result = this.f.removeElement(appInfo);
                if (!result) {
                    return result;
                }
                this.i.b();
                c.a().d(new com.meizu.cloud.app.c.a(null, packageName, -1));
                return result;
            }
        }
        return false;
    }

    private void d() {
        IntentFilter appStatusIntentFilter = new IntentFilter();
        appStatusIntentFilter.addDataScheme("package");
        appStatusIntentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        appStatusIntentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        String firstLaunchIntent = e();
        if (!TextUtils.isEmpty(firstLaunchIntent)) {
            appStatusIntentFilter.addAction(firstLaunchIntent);
        }
        this.e.registerReceiver(new a(), appStatusIntentFilter);
    }

    private String e() {
        if (!TextUtils.isEmpty(this.h)) {
            return this.h;
        }
        try {
            this.h = (String) com.meizu.cloud.c.b.a.b.a("android.content.IntentExt", "MZ_ACTION_PACKAGE_FIRST_LAUNCH");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.h;
    }

    public o c() {
        return this.i;
    }
}

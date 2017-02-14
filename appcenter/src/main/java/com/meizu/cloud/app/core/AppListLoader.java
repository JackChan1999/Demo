package com.meizu.cloud.app.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v4.content.a;
import android.util.Log;
import com.meizu.cloud.app.utils.h;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppListLoader extends a<List<a>> {
    private static final String r = AppListLoader.class.getSimpleName();
    private static final Comparator<a> s = new Comparator<a>() {
        public /* synthetic */ int compare(Object obj, Object obj2) {
            return a((a) obj, (a) obj2);
        }

        public int a(a lhs, a rhs) {
            if (lhs.e() == rhs.e()) {
                return 0;
            }
            return lhs.e() < rhs.e() ? 1 : -1;
        }
    };
    final PackageManager o = h().getPackageManager();
    List<a> p;
    PackageIntentReceiver q;

    public static class PackageIntentReceiver extends BroadcastReceiver {
        final AppListLoader a;

        public PackageIntentReceiver(AppListLoader loader) {
            this.a = loader;
            IntentFilter filter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
            filter.addAction("android.intent.action.PACKAGE_REMOVED");
            filter.addAction("android.intent.action.PACKAGE_CHANGED");
            filter.addDataScheme("package");
            this.a.h().registerReceiver(this, filter);
            IntentFilter sdFilter = new IntentFilter();
            sdFilter.addAction("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
            sdFilter.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
            this.a.h().registerReceiver(this, sdFilter);
        }

        public void onReceive(Context context, Intent intent) {
            this.a.y();
        }
    }

    public /* synthetic */ void a(Object obj) {
        b((List) obj);
    }

    public /* synthetic */ void b(Object obj) {
        a((List) obj);
    }

    public /* synthetic */ Object d() {
        return z();
    }

    public AppListLoader(Context context) {
        super(context);
    }

    public List<a> z() {
        List<PackageInfo> apps = i.b(h(), 2);
        if (apps == null) {
            apps = new ArrayList();
        }
        Context context = h();
        List<a> entries = new ArrayList(apps.size());
        for (int i = 0; i < apps.size(); i++) {
            a entry = new a(this, ((PackageInfo) apps.get(i)).applicationInfo);
            entry.a(context);
            entry.a(((PackageInfo) apps.get(i)).lastUpdateTime);
            String iconFileName = entry.a().packageName;
            if (!new File(context.getCacheDir(), "app_icons" + File.separator + iconFileName).exists()) {
                a(context, entry.c(), iconFileName);
            }
            entry.d();
            entries.add(entry);
        }
        Collections.sort(entries, s);
        return entries;
    }

    public static void a(Context context, Drawable icon, String name) {
        try {
            File dir = new File(context.getCacheDir(), "app_icons");
            if (!dir.exists()) {
                Log.d(r, "dir.mkdirs()->result = " + dir.mkdirs());
            }
            InputStream inputStream = h.b(h.a(icon));
            FileOutputStream fos = new FileOutputStream(new File(dir, name));
            byte[] buffer = new byte[8192];
            while (true) {
                int count = inputStream.read(buffer);
                if (count > 0) {
                    fos.write(buffer, 0, count);
                } else {
                    fos.close();
                    inputStream.close();
                    Log.d(r, "copyImage2Data->" + name);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a(List<a> apps) {
        if (k() && apps != null) {
            c(apps);
        }
        List<a> oldApps = this.p;
        this.p = apps;
        if (i()) {
            super.b((Object) apps);
        }
        if (oldApps != null) {
            c(oldApps);
        }
    }

    protected void m() {
        if (this.p != null) {
            a(this.p);
        }
        if (this.q == null) {
            this.q = new PackageIntentReceiver(this);
        }
        if (v() || this.p == null) {
            o();
        }
    }

    protected void q() {
        n();
    }

    public void b(List<a> apps) {
        super.a(apps);
        c(apps);
    }

    protected void u() {
        super.u();
        q();
        if (this.p != null) {
            c(this.p);
            this.p = null;
        }
        if (this.q != null) {
            h().unregisterReceiver(this.q);
            this.q = null;
        }
    }

    protected void c(List<a> list) {
    }
}

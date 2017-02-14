package com.meizu.cloud.app.update.exclude;

import a.a.a.c;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.meizu.cloud.app.c.b;
import com.meizu.cloud.app.core.s;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class d {
    private static d a;
    private Context b;
    private ContentResolver c = null;
    private List<String> d = new CopyOnWriteArrayList();

    private class a extends ContentObserver {
        final /* synthetic */ d a;

        public a(d dVar, Handler handler) {
            this.a = dVar;
            super(handler);
        }

        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            this.a.b();
        }
    }

    public static d a(Context context) {
        if (a == null) {
            a = new d(context);
        }
        return a;
    }

    private d(Context context) {
        this.b = context.getApplicationContext();
        this.c = this.b.getApplicationContext().getContentResolver();
        this.d.addAll(a());
        this.c.registerContentObserver(Uri.parse("content://com.meizu.cloud.app.update.exclude/AppUpdateExcludeProvider/"), true, new a(this, new Handler(Looper.getMainLooper())));
    }

    private void b() {
        new Thread(new Runnable(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void run() {
                synchronized (this.a.d) {
                    this.a.d.clear();
                    List<String> strings = this.a.a();
                    this.a.d.addAll(strings);
                    c.a().d(new b(new s().e(true), true, (String[]) strings.toArray(new String[strings.size()])));
                }
            }
        }).start();
    }

    public void a(String packageName) {
        ContentValues values = new ContentValues();
        values.put("packageName", "" + packageName);
        try {
            PackageInfo packageInfo = this.b.getPackageManager().getPackageInfo(packageName, 0);
            if (packageInfo != null) {
                values.put("appName", "" + packageInfo.applicationInfo.loadLabel(this.b.getPackageManager()).toString());
                values.put("versionCode", "" + packageInfo.versionCode);
                values.put("versionName", "" + packageInfo.versionName);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        this.c.insert(Uri.parse("content://com.meizu.cloud.app.update.exclude/AppUpdateExcludeProvider/"), values);
    }

    public void b(String package_name) {
        this.c.delete(Uri.parse("content://com.meizu.cloud.app.update.exclude/AppUpdateExcludeProvider/"), package_name, null);
    }

    public List<String> a() {
        List<String> packageList = new ArrayList();
        Cursor c = this.c.query(Uri.parse("content://com.meizu.cloud.app.update.exclude/AppUpdateExcludeProvider/"), null, null, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                packageList.add(c.getString(c.getColumnIndex("packageName")));
            }
            c.close();
        }
        return packageList;
    }
}

package com.meizu.cloud.base.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.meizu.c.a;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.jobscheduler.JobSchedulerService;
import com.meizu.cloud.app.utils.d;
import com.meizu.cloud.app.utils.q;
import com.meizu.cloud.download.app.NetworkStatusManager;
import com.meizu.gslb.a.b;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseApplication extends Application {
    protected static BaseApplication a;
    private static boolean b = false;
    private static boolean c = false;
    private static List<Runnable> d = new ArrayList();

    public void onCreate() {
        super.onCreate();
        a.a().a(getApplicationContext());
        d.c(this);
        NetworkStatusManager.a(this, getPackageName(), "wifi_only");
        if (q.a(this)) {
            a(this, true, null);
        }
        com.meizu.gslb.b.d.a((Context) this, new com.meizu.cloud.statistics.a.a());
        b.a().a(10000).b();
        a = this;
    }

    public static void a(Context context) {
        a(context, false, null);
    }

    public static void a(Context context, boolean bDelayed, Runnable runnable) {
        if (!b) {
            long j;
            b = true;
            if (runnable != null) {
                synchronized (d) {
                    d.add(runnable);
                }
            }
            Handler handler = new Handler(Looper.getMainLooper());
            Runnable anonymousClass1 = new Runnable() {
                public void run() {
                    BaseApplication.c();
                }
            };
            if (bDelayed) {
                j = 250;
            } else {
                j = 0;
            }
            handler.postDelayed(anonymousClass1, j);
        } else if (runnable == null) {
        } else {
            if (c) {
                runnable.run();
                return;
            }
            synchronized (d) {
                d.add(runnable);
            }
        }
    }

    public static void a(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (c) {
            runnable.run();
            return;
        }
        synchronized (d) {
            d.add(runnable);
        }
        c();
    }

    private static void c() {
        if (!c) {
            JobSchedulerService.a(a, JobSchedulerService.class);
            x.d(a).b();
            c = true;
            synchronized (d) {
                for (Runnable runnable1 : d) {
                    runnable1.run();
                }
                d.clear();
            }
        }
    }

    public static Context a() {
        return a;
    }
}

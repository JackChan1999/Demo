package com.meizu.cloud.app.update.exclude;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import com.meizu.cloud.app.core.r;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.app.downlad.e;
import com.meizu.cloud.app.downlad.f.c;
import com.meizu.cloud.app.downlad.f.l;
import com.meizu.cloud.app.downlad.g;
import com.meizu.cloud.app.jobscheduler.JobSchedulerService;
import com.meizu.cloud.app.request.model.GameEntryInfo;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo;
import com.meizu.cloud.app.utils.k;
import com.meizu.cloud.app.utils.m;
import java.util.Calendar;
import java.util.List;

public class a {
    private static final String a = a.class.getSimpleName();
    private static a b;
    private Context c;
    private d d;
    private Handler e;
    private AlarmManager f;
    private int g = 0;
    private boolean h = true;
    private boolean i = false;
    private JobSchedulerService j;
    private r k;

    public static a a(Context context) {
        if (b == null) {
            b = new a(context);
        }
        return b;
    }

    private a(Context context) {
        this.c = context.getApplicationContext();
        this.d = d.a(this.c);
        this.k = r.a(this.c);
        this.e = new Handler(Looper.getMainLooper());
        this.f = (AlarmManager) this.c.getSystemService("alarm");
        this.j = JobSchedulerService.a(new Runnable(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.j = JobSchedulerService.a(null);
            }
        });
    }

    private void c() {
        b(21600, 21600);
        if (b(this.c) && c(this.c)) {
            List updateApps = this.k.c(this.c);
            if (updateApps.size() > 0) {
                a(updateApps);
            }
        }
    }

    private void a(List<ServerUpdateAppInfo<GameEntryInfo>> updateApps) {
        boolean bAutoInstall = com.meizu.cloud.app.settings.a.a(this.c).d();
        k.a(this.c, a, "autoDownload check,autoInstall?" + bAutoInstall);
        int i = 0;
        while (i < updateApps.size()) {
            g taskProperty;
            if (((ServerUpdateAppInfo) updateApps.get(i)).getAppStructItem().isDownloaded(this.c, true)) {
                if (bAutoInstall && ((ServerUpdateAppInfo) updateApps.get(i)).auto_install == 1) {
                    ((ServerUpdateAppInfo) updateApps.get(i)).getAppStructItem().page_info = new int[]{0, 21, 0};
                    taskProperty = new g(8, 0);
                    taskProperty.c(true).d(false);
                    this.d.c(((ServerUpdateAppInfo) updateApps.get(i)).getAppStructItem(), taskProperty);
                }
            } else if (((ServerUpdateAppInfo) updateApps.get(i)).price <= 0.0d && !b(((ServerUpdateAppInfo) updateApps.get(i)).package_name, ((ServerUpdateAppInfo) updateApps.get(i)).getAppStructItem().version_code)) {
                taskProperty = new g(8, 0);
                taskProperty.a(bAutoInstall).c(true).d(false);
                ((ServerUpdateAppInfo) updateApps.get(i)).appStructItem.page_info = new int[]{0, 21, 0};
                e downloadWrapper = this.d.a((ServerUpdateAppInfo) updateApps.get(i), taskProperty);
                if (downloadWrapper.f() != c.TASK_STARTED) {
                    this.d.a(null, downloadWrapper);
                }
            }
            i++;
        }
    }

    private void d() {
        for (e downloadWrapper : this.d.c(0)) {
            if (downloadWrapper.j().a(0) && downloadWrapper.j().c()) {
                l stateEnum = t.a(downloadWrapper);
                if (stateEnum != null) {
                    downloadWrapper.a(stateEnum, downloadWrapper.S());
                    this.d.a(null, downloadWrapper);
                }
            }
        }
    }

    private boolean b(Context context) {
        return com.meizu.cloud.app.settings.a.a(context).c() && m.a(context);
    }

    private boolean c(Context context) {
        return this.g > 20 && !this.h && this.i;
    }

    private void a(long timeStart, long timeRepeat) {
        this.f.cancel(e());
        PendingIntent pendingIntent = e();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(13, (int) timeStart);
        this.f.setRepeating(0, cal.getTimeInMillis(), 1000 * timeRepeat, pendingIntent);
    }

    private void b(long timeStart, long timeRepeat) {
        if (this.j != null) {
            this.j.a(new Builder(JobSchedulerService.a[0], new ComponentName(this.c, JobSchedulerService.class)).setMinimumLatency(timeStart * 1000).setOverrideDeadline(timeStart * 1000).setRequiredNetworkType(2).build(), new Runnable(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void run() {
                    k.b(this.a.c, a.a, "schedule to auto update");
                    this.a.a();
                }
            });
            return;
        }
        a(timeStart, timeRepeat);
    }

    private PendingIntent e() {
        Intent intent = new Intent();
        intent.setAction("auto.update.action");
        return PendingIntent.getBroadcast(this.c, 0, intent, 0);
    }

    public void a(int total, int current, boolean bPowerConnected) {
        this.i = bPowerConnected;
        if (this.i && current > 20 && this.g <= 20) {
            a(true);
        }
        this.g = current;
        this.e.post(new Runnable(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void run() {
                if (this.a.g < 20) {
                    this.a.d();
                }
            }
        });
    }

    public void a(final boolean bPowerConnected) {
        this.e.post(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                this.b.i = bPowerConnected;
                if (!this.b.i) {
                    this.b.f.cancel(this.b.e());
                    if (this.b.j != null) {
                        this.b.j.a(JobSchedulerService.a[0]);
                    }
                    this.b.d();
                } else if (this.b.c(this.b.c) && this.b.b(this.b.c) && this.b.k.b(this.b.c).size() > 0) {
                    this.b.b(120, 120);
                    k.b(this.b.c, a.a, ">>Power connected and scheduler download");
                }
            }
        });
    }

    public void b(final boolean bScreenOn) {
        this.e.post(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                if (bScreenOn) {
                    this.b.h = true;
                    this.b.f.cancel(this.b.e());
                    if (this.b.j != null) {
                        this.b.j.a(JobSchedulerService.a[0]);
                    }
                    this.b.d();
                } else if (this.b.h) {
                    this.b.h = false;
                    if (this.b.c(this.b.c) && this.b.b(this.b.c) && this.b.k.b(this.b.c).size() > 0) {
                        this.b.b(120, 120);
                        k.b(this.b.c, a.a, ">>Screen off and scheduler download");
                    }
                }
            }
        });
    }

    public void a() {
        this.e.post(new Runnable(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.c();
            }
        });
    }

    public void a(String packageName, int versionCode) {
        SharedPreferences sharedPreferences = this.c.getSharedPreferences("error_exclude_apps", 0);
        String key = packageName + "_" + versionCode;
        if (sharedPreferences.contains(key)) {
            sharedPreferences.edit().putInt(key, sharedPreferences.getInt(key, 0) + 1).apply();
            return;
        }
        sharedPreferences.edit().putInt(key, 1).apply();
    }

    public boolean b(String packageName, int versionCode) {
        SharedPreferences sharedPreferences = this.c.getSharedPreferences("error_exclude_apps", 0);
        String key = packageName + "_" + versionCode;
        if (!sharedPreferences.contains(key) || sharedPreferences.getInt(key, 0) < 2) {
            return false;
        }
        return true;
    }

    public void c(String packageName, int versionCode) {
        SharedPreferences sharedPreferences = this.c.getSharedPreferences("error_exclude_apps", 0);
        String key = packageName + "_" + versionCode;
        if (sharedPreferences.contains(key)) {
            sharedPreferences.edit().remove(key).apply();
        }
    }
}

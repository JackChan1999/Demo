package com.meizu.cloud.app.downlad;

import android.app.Notification.Builder;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.download.service.DownloadService;
import com.meizu.cloud.download.service.c;

public class AppDownloadService extends DownloadService {
    private static AppDownloadService b;
    private boolean a = false;

    public static AppDownloadService a() {
        return b;
    }

    public void onCreate() {
        super.onCreate();
        b = this;
    }

    public void b() {
        if (!this.a) {
            startForeground(Integer.MIN_VALUE, new Builder(this).build());
            this.a = true;
            Log.i("AppDownloadService", "startForeground");
        }
    }

    public void c() {
        if (this.a) {
            stopForeground(true);
            this.a = false;
            Log.i("AppDownloadService", "stopForeground");
        }
    }

    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == 60 || level == 5) {
            h.a((Context) this);
            System.gc();
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return 2;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    protected c d() {
        return new b(this);
    }
}

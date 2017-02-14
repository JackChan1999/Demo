package com.meizu.statsapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class UsageStatsManagerService extends Service {
    private a a;

    public IBinder onBind(Intent intent) {
        if (this.a == null) {
            boolean online = intent.getBooleanExtra("online", false);
            boolean upload = intent.getBooleanExtra("upload", true);
            Context context = getApplicationContext();
            if (context == null) {
                context = this;
            }
            this.a = UsageStatsManagerServer.a(context, online, upload);
        }
        return this.a.asBinder();
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
    }
}

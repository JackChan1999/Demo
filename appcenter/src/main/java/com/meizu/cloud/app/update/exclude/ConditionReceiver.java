package com.meizu.cloud.app.update.exclude;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import com.meizu.cloud.app.settings.a;

public class ConditionReceiver extends BroadcastReceiver {
    public boolean a = false;
    private int b = -1;

    public void onReceive(Context context, Intent intent) {
        boolean z = false;
        boolean bAutoDownload = a.a(context).c();
        if (!"android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
            Log.d("ConditionReceiver", "Action: " + intent.getAction() + " bAutoDownload: " + bAutoDownload);
        }
        if (bAutoDownload) {
            a(context);
            if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                Bundle bundle = intent.getExtras();
                int total = bundle.getInt("scale");
                int battery = (bundle.getInt("level") * 100) / total;
                if (battery != this.b) {
                    this.b = battery;
                    int status = intent.getIntExtra("status", -1);
                    a a = a.a(context);
                    if (status == 2 || status == 5) {
                        z = true;
                    }
                    a.a(total, battery, z);
                    return;
                }
                return;
            } else if ("android.intent.action.USER_PRESENT".equals(intent.getAction())) {
                a.a(context).b(true);
                return;
            } else if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                a.a(context).b(false);
                return;
            } else if ("auto.update.action".equals(intent.getAction())) {
                a.a(context).a();
                return;
            } else if ("android.intent.action.ACTION_POWER_CONNECTED".equals(intent.getAction()) || "android.intent.action.ACTION_POWER_DISCONNECTED".equals(intent.getAction())) {
                a.a(context).a("android.intent.action.ACTION_POWER_CONNECTED".equals(intent.getAction()));
                return;
            } else {
                return;
            }
        }
        b(context);
    }

    private void a(Context context) {
        if (!this.a) {
            this.a = true;
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.SCREEN_OFF");
            filter.addAction("android.intent.action.BATTERY_CHANGED");
            filter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
            filter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
            context.getApplicationContext().registerReceiver(this, filter);
        }
    }

    private void b(Context context) {
        if (this.a) {
            this.a = false;
            context.getApplicationContext().unregisterReceiver(this);
        }
    }
}

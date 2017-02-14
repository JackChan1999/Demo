package com.meizu.statsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UsageStatsReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            e.a(false);
        } else if ("com.meizu.usagestats.check_upload".equals(intent.getAction())) {
            e.a(true);
        }
    }
}

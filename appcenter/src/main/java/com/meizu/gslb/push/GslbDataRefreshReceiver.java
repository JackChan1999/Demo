package com.meizu.gslb.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class GslbDataRefreshReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if ("com.meizu.flyme.gslb.push.broadcast".equals(intent.getAction())) {
            a.a(context, intent);
        }
    }
}

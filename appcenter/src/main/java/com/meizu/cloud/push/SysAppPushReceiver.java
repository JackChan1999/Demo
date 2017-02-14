package com.meizu.cloud.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.app.core.m.c;
import com.meizu.cloud.app.core.q;
import com.meizu.cloud.app.utils.k;

public class SysAppPushReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if ("com.meizu.mstore.intent.mappsupdate".equals(intent.getAction())) {
            String pkgName = intent.getStringExtra("packageName");
            if (!TextUtils.isEmpty(pkgName) && !"sysupdate".equals(pkgName)) {
                c.a(context, pkgName);
                k.b(context, "SysAppPushReceiver", "checkSysAppUpdate:" + pkgName);
                q.a(context, pkgName);
            }
        }
    }
}

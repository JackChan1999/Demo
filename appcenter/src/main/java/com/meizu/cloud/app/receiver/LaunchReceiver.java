package com.meizu.cloud.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.compaign.a;
import com.meizu.cloud.compaign.task.BaseTask;

public class LaunchReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if ("com.meizu.cloud.appcommon.intent.LAUNCH_APP".equals(intent.getAction())) {
            a(context, intent.getStringExtra("package_name"));
        }
    }

    private int a(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            return -1;
        }
        BaseTask launchTask = a.a(context).b(packageName);
        if (launchTask != null) {
            a.a(context).a(launchTask);
        }
        intent.addFlags(268435456);
        intent.addFlags(2097152);
        context.startActivity(intent);
        return 1;
    }
}

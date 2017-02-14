package com.meizu.update.f;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.meizu.update.UpdateInfo;
import com.meizu.update.service.MzUpdateComponentService;

public class a {
    public static void a(Context context, UpdateInfo info) {
        try {
            PendingIntent pi = MzUpdateComponentService.g(context, info);
            Intent intent = new Intent("com.meizu.appupdate.intent.wakeup");
            intent.putExtra("PendingIntent", pi);
            intent.setPackage("com.meizu.cloud");
            context.startService(intent);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    public static Intent a(Context context) {
        try {
            return context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        } catch (Exception e) {
            return null;
        }
    }
}

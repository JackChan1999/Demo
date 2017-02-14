package com.meizu.flyme.appcenter.desktopplugin.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.app.settings.a;
import com.meizu.cloud.app.utils.s;
import com.meizu.flyme.appcenter.desktopplugin.b.c;

public class PluginUpdateReceiver extends BroadcastReceiver {
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals("com.meizu.cloud.appcommon.intent.UPDATE_PLUGIN_ICON")) {
            new Thread(new Runnable(this) {
                final /* synthetic */ PluginUpdateReceiver b;

                public void run() {
                    new c().a(context, 0);
                }
            }).start();
        } else if (intent.getAction().equals("com.meizu.flyme.launcher.restore_finish")) {
            if (a.a(context).g()) {
                c.a(context, true);
            }
        } else if (intent.getAction().equals("android.intent.action.WALLPAPER_CHANGED") || intent.getAction().equals("com.meizu.mstore.plugin_wallpapercolor_recalculate")) {
            s.a(context, true);
            new d(context).a(context);
        }
    }
}

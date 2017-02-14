package com.meizu.update;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.meizu.update.b.c;
import com.meizu.update.c.a;
import com.meizu.update.c.e;
import com.meizu.update.display.d;
import com.meizu.update.h.g;
import com.meizu.update.service.MzUpdateComponentService;

public class b {
    public static final void a(Context context, a listener, long checkInterval, boolean manual) {
        new c(context, listener, checkInterval).a(manual);
        b(context);
    }

    public static final d a(Activity activity, e listener, UpdateInfo info) {
        return a(activity, listener, info, false, false);
    }

    public static final d a(Context context, e listener, UpdateInfo info, boolean systemAlert, boolean manual) {
        return a(context, listener, info, systemAlert, manual, null, null);
    }

    public static final d a(Context context, e listener, UpdateInfo info, boolean systemAlert, boolean manual, String customTitle, String customDesc) {
        if (info == null || !info.mExistsUpdate) {
            com.meizu.update.h.b.d("request display while no update!");
            return null;
        }
        b.b.b(context);
        if (com.meizu.update.h.e.a()) {
            com.meizu.update.h.b.d("request display while update in process, skip!");
            return null;
        }
        com.meizu.update.display.a dialog;
        String apkFilePath = com.meizu.update.a.a.c(context, info.mVersionName);
        if (g.c(context, apkFilePath)) {
            dialog = new d(context, null, info, apkFilePath);
        } else {
            com.meizu.update.display.a updateDisplayManager = new com.meizu.update.display.g(context, listener, info, false, true);
            updateDisplayManager.b(manual);
            dialog = updateDisplayManager;
        }
        dialog.a(systemAlert);
        dialog.a(customTitle);
        dialog.b(customDesc);
        return dialog.b();
    }

    public static final boolean a(Context context, String data) {
        if (!com.meizu.update.push.b.a(context, data)) {
            return false;
        }
        MzUpdateComponentService.a(context);
        return true;
    }

    public static final boolean a(Context context, Intent intent) {
        if (!com.meizu.update.push.b.a(context, intent)) {
            return false;
        }
        MzUpdateComponentService.a(context);
        return true;
    }

    public static final void a(Context context) {
        com.meizu.update.push.b.a(context);
    }

    public static final void b(Context context) {
        com.meizu.update.push.b.d(context);
    }
}

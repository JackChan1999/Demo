package com.meizu.update.b;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class b {
    public static final boolean a(Context context, long interval) {
        long lastCheck = a(context);
        if (d(context) == 1 || lastCheck <= 0) {
            return true;
        }
        if (Math.abs(System.currentTimeMillis() - lastCheck) <= (interval > 0 ? interval : 259200000)) {
            return false;
        }
        return true;
    }

    public static final long a(Context context) {
        SharedPreferences sp = com.meizu.update.push.b.c(context);
        if (sp != null) {
            return sp.getLong("check_update_time", 0);
        }
        return 0;
    }

    public static final void b(Context context) {
        b(context, System.currentTimeMillis());
    }

    private static final void b(Context context, long time) {
        Editor e = com.meizu.update.push.b.c(context).edit();
        e.putLong("check_update_time", time);
        e.apply();
    }

    public static final void c(Context context) {
        b(context, 0);
    }

    private static final int d(Context context) {
        SharedPreferences sp = com.meizu.update.push.b.c(context);
        if (sp != null) {
            return sp.getInt("cur_need_update", 0);
        }
        return 0;
    }

    public static final void a(Context context, int status) {
        Editor e = com.meizu.update.push.b.c(context).edit();
        e.putInt("cur_need_update", status);
        e.apply();
    }
}

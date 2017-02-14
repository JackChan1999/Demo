package com.meizu.update.a;

import android.content.Context;

public class b {
    public static final void a(Context context, String data) {
        com.meizu.update.push.b.c(context).edit().putString("last_check_update_info_data", data).apply();
    }
}

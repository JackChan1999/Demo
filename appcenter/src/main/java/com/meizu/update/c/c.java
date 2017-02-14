package com.meizu.update.c;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.meizu.update.UpdateInfo;
import com.meizu.update.b;

public class c {
    public static void a(Context context, a listener) {
        b.a(context, listener, -1, false);
    }

    public static void a(Activity activity, UpdateInfo info) {
        b.a(activity, null, info);
    }

    public static final boolean a(Context context, String data) {
        return b.a(context, data);
    }

    public static final boolean a(Context context, Intent intent) {
        return b.a(context, intent);
    }
}

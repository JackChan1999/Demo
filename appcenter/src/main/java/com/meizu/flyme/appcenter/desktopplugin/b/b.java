package com.meizu.flyme.appcenter.desktopplugin.b;

import android.content.Context;
import com.meizu.cloud.statistics.a;

public class b extends a {
    private static b b;
    private Context a;

    private b(Context context) {
        super(context);
        this.a = context;
    }

    public static synchronized b b(Context context) {
        b bVar;
        synchronized (b.class) {
            if (b == null) {
                b = new b(context.getApplicationContext());
            }
            bVar = b;
        }
        return bVar;
    }
}

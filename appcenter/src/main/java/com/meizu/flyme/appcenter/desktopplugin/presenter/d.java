package com.meizu.flyme.appcenter.desktopplugin.presenter;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import java.lang.reflect.Array;

public class d {
    protected final int[][] a = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{50, 100}));
    private Context b;
    private e c;

    public d(Context context) {
        this.b = context.getApplicationContext();
    }

    public void a(Context context) {
        if (this.c != null) {
            this.c.c = false;
        }
        Runnable anonymousClass1 = new e(this, context.getApplicationContext(), this) {
            final long a = SystemClock.elapsedRealtime();
            final /* synthetic */ d b;

            void a() {
                Log.d("WallPaerColor", "time : " + (SystemClock.elapsedRealtime() - this.a) + "ms");
                this.b.c = null;
            }
        };
        this.c = anonymousClass1;
        new Thread(anonymousClass1).start();
    }
}

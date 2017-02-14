package com.meizu.update.b;

import android.content.Context;
import com.meizu.update.UpdateInfo;
import com.meizu.update.c.a;

public class c {
    private a a;

    public c(Context context, a listener, long checkInterval) {
        this.a = new a(context, listener, checkInterval);
    }

    public void a(final boolean manualCheck) {
        new Thread(new Runnable(this) {
            final /* synthetic */ c b;

            public void run() {
                UpdateInfo info = this.b.a.a(manualCheck);
                if (info != null) {
                    this.b.a.a(info);
                } else {
                    this.b.a.a();
                }
            }
        }).start();
    }
}

package com.meizu.cloud.thread.component;

import android.app.Service;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public abstract class AsyncExecuteService extends Service {
    protected Handler a = new Handler(this) {
        final /* synthetic */ AsyncExecuteService a;

        {
            this.a = r1;
        }

        public void handleMessage(Message msg) {
            boolean bHandle = false;
            int i = msg.what;
            if (null == null) {
                bHandle = this.a.a(msg);
            }
            if (!bHandle) {
                AsyncExecuteService.a("msg not be handle = " + msg.what);
            }
        }
    };
    private c b = new c();

    protected boolean a(Message msg) {
        return false;
    }

    protected static void a(String tip) {
        Log.w("AsyncExecuteService", "" + tip);
    }
}

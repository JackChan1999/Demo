package com.meizu.cloud.thread.component;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ExecBaseActivity extends AsyncExecuteActivity {
    protected boolean r = false;
    protected Handler s = new Handler(this) {
        final /* synthetic */ ExecBaseActivity a;

        {
            this.a = r1;
        }

        public void handleMessage(Message msg) {
            if (this.a.r) {
                boolean bHandle = false;
                int i = msg.what;
                if (null == null) {
                    bHandle = this.a.a(msg);
                }
                if (!bHandle) {
                    ExecBaseActivity.b("msg not be handle = " + msg.what);
                    return;
                }
                return;
            }
            ExecBaseActivity.a(" skip msg while activity is destroy");
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.r = true;
    }

    protected void onDestroy() {
        super.onDestroy();
        this.r = false;
    }

    protected boolean a(Message msg) {
        return false;
    }

    protected static void a(String tip) {
        Log.d("AsyncExecuteActivity", "" + tip);
    }

    protected static void b(String tip) {
        Log.w("AsyncExecuteActivity", "" + tip);
    }
}

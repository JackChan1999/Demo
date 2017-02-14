package com.meizu.cloud.base.app;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.meizu.update.UpdateInfo;
import com.meizu.update.c.a;
import com.meizu.update.c.b;
import com.meizu.update.c.c;

public class BaseUpdateActivity extends BaseCommonActivity {
    private a m = new a(this) {
        final /* synthetic */ BaseUpdateActivity a;

        {
            this.a = r1;
        }

        public void a(int code, UpdateInfo info) {
            switch (code) {
                case 0:
                    this.a.a(info);
                    return;
                default:
                    return;
            }
        }
    };
    protected boolean q;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void n() {
        v();
    }

    protected void v() {
        c.a((Context) this, this.m);
    }

    private void a(final UpdateInfo info) {
        if (this.q) {
            Log.w("BaseUpdateActivity", "handle update while activity stop, skip");
        } else if (info.mExistsUpdate) {
            runOnUiThread(new Runnable(this) {
                final /* synthetic */ BaseUpdateActivity b;

                public void run() {
                    c.a(this.b, info);
                }
            });
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onStart() {
        this.q = false;
        super.onStart();
        b.a(this);
    }

    protected void onStop() {
        this.q = true;
        super.onStop();
        b.b(this);
    }
}

package com.meizu.cloud.thread.component;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class b extends a {
    protected static final String TAG = "AsyncExecuteFragment";
    protected boolean mRunning = false;
    protected Handler mUiHandler = new Handler(this) {
        final /* synthetic */ b a;

        {
            this.a = r1;
        }

        public void handleMessage(Message msg) {
            if (this.a.mRunning) {
                boolean bHandle = false;
                int i = msg.what;
                if (null == null) {
                    bHandle = this.a.handleMessage(msg);
                }
                if (!bHandle) {
                    b.logW("msg not be handle = " + msg.what);
                    return;
                }
                return;
            }
            b.logT(" skip msg while fragment is destroy");
        }
    };

    protected final void runOnUi(Runnable runnable) {
        if (this.mRunning) {
            this.mUiHandler.post(runnable);
        } else {
            logT(" skip runOnUi while fragment is destroy");
        }
    }

    protected final void runOnUi(Runnable runnable, long delayMillis) {
        if (this.mRunning) {
            this.mUiHandler.postDelayed(runnable, delayMillis);
        } else {
            logT(" skip runOnUi while fragment is destroy");
        }
    }

    protected final boolean sendMessage(int what, Object obj, int arg1, int arg2) {
        return this.mUiHandler.sendMessage(this.mUiHandler.obtainMessage(what, arg1, arg2, obj));
    }

    protected final boolean sendMessage(int what, Object obj) {
        return this.mUiHandler.sendMessage(this.mUiHandler.obtainMessage(what, 0, 0, obj));
    }

    protected final boolean sendMessageDelayed(int what, Object obj, int arg1, int arg2, long delayMillis) {
        return this.mUiHandler.sendMessageDelayed(this.mUiHandler.obtainMessage(what, arg1, arg2, obj), delayMillis);
    }

    protected final boolean sendMessageDelayed(int what, long delayMillis) {
        return this.mUiHandler.sendEmptyMessageDelayed(what, delayMillis);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mRunning = true;
    }

    public void onDestroy() {
        super.onDestroy();
        this.mRunning = false;
    }

    protected boolean handleMessage(Message msg) {
        return false;
    }

    protected static void logT(String tip) {
        Log.d(TAG, "" + tip);
    }

    protected static void logW(String tip) {
        Log.w(TAG, "" + tip);
    }
}

package com.meizu.cloud.thread.component;

import android.support.v4.app.Fragment;
import com.meizu.cloud.thread.c;

public class a extends Fragment {
    protected static final boolean DEBUG = true;
    protected static final String TAG = "AsyncExecuteFragment";
    private c mExecHelper = new c();

    public c asyncExec(Runnable runnable) {
        return this.mExecHelper.a(runnable);
    }
}

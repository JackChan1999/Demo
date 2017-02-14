package com.meizu.cloud.base.b;

import android.os.Process;
import com.meizu.cloud.base.b.d.a;

public abstract class o extends d implements a {
    protected boolean mIsListeningPagerState;
    private boolean mIsScrolling;

    protected void registerPagerScrollStateListener() {
        if (getParentFragment() instanceof m) {
            ((m) getParentFragment()).a((a) this);
            this.mIsListeningPagerState = true;
        } else if (getParentFragment() != null && (getParentFragment().getParentFragment() instanceof m)) {
            ((m) getParentFragment().getParentFragment()).a((a) this);
            this.mIsListeningPagerState = true;
        }
    }

    protected void unregisterPagerScrollStateListener() {
        if (getParentFragment() instanceof m) {
            ((m) getParentFragment()).a((a) this);
        } else if (getParentFragment() != null && (getParentFragment().getParentFragment() instanceof m)) {
            ((m) getParentFragment().getParentFragment()).a((a) this);
        }
        this.mIsListeningPagerState = false;
    }

    public void onPageScrollStateChanged(int state) {
        this.mIsScrolling = state != 0;
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    protected void postOnPagerIdle(final Runnable runnable) {
        if (this.mIsListeningPagerState) {
            asyncExec(new Runnable(this) {
                final /* synthetic */ o b;

                public void run() {
                    Process.setThreadPriority(10);
                    long start = System.currentTimeMillis();
                    while (System.currentTimeMillis() - start < 100) {
                        try {
                            Thread.sleep(10);
                            if (!this.b.mRunning || this.b.getActivity() == null) {
                                return;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (this.b.mIsScrolling) {
                            start = System.currentTimeMillis();
                        }
                    }
                    this.b.runOnUi(runnable);
                }
            });
        } else {
            runOnUi(runnable);
        }
    }
}

package com.meizu.cloud.download.c;

import android.os.Process;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class f implements ThreadFactory {
    private final int a;
    private final AtomicInteger b = new AtomicInteger();
    private final String c;

    public f(String name, int priority) {
        this.c = name;
        this.a = priority;
    }

    public Thread newThread(Runnable r) {
        return new Thread(this, r, this.c + '-' + this.b.getAndIncrement()) {
            final /* synthetic */ f a;

            public void run() {
                Process.setThreadPriority(this.a.a);
                super.run();
            }
        };
    }
}

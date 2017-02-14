package com.meizu.cloud.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

class f {
    private ScheduledExecutorService a = Executors.newScheduledThreadPool(1);
    private Map<Object, ScheduledFuture<?>> b = new HashMap();

    f() {
    }
}

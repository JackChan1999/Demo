package com.meizu.cloud.download.service;

import com.meizu.cloud.download.b.e;
import com.meizu.cloud.download.service.e.a;

public abstract class j extends c {
    protected abstract e a(DownloadTaskInfo downloadTaskInfo);

    public j(DownloadService service) {
        super(service);
    }

    protected e a(a stateListener, com.meizu.cloud.download.a.a.a<b> jobExecutor, DownloadTaskInfo info) {
        return new e(this, this.a.getApplicationContext(), stateListener, jobExecutor, info) {
            final /* synthetic */ j a;

            protected e a(DownloadTaskInfo info) {
                return this.a.a(info);
            }
        };
    }
}

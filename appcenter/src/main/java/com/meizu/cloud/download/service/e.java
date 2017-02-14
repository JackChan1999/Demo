package com.meizu.cloud.download.service;

import android.content.Context;
import android.os.Bundle;
import com.meizu.cloud.download.c.g.b;
import com.meizu.cloud.download.c.g.c;

public class e extends com.meizu.cloud.download.a.a<b> {
    private Context a;
    private final DownloadTaskInfo b;
    private b c = new b();
    private a d;
    private a e;

    public interface a {
        String a(c cVar, String str, Bundle bundle);

        void a(long j, long j2);

        void a(long j, String str, long j2);

        void a(long j, String str, String str2);

        void a(DownloadTaskInfo downloadTaskInfo);

        void a(DownloadTaskInfo downloadTaskInfo, int i);

        void a(DownloadTaskInfo downloadTaskInfo, b bVar);

        void a(DownloadTaskInfo downloadTaskInfo, boolean z);

        void a(String str);

        boolean a();

        void b(DownloadTaskInfo downloadTaskInfo);
    }

    public e(Context context, a stateListener, com.meizu.cloud.download.a.a.a<b> jobExecutor, DownloadTaskInfo task) {
        super(jobExecutor);
        this.b = task;
        this.c.b = 0;
        this.c.a = null;
        this.d = stateListener;
        this.a = context;
    }

    public b<b> e() {
        this.e = new a(this.a, this.b, this.d, a(this.b));
        return this.e;
    }

    public void a(b resource) {
        if (resource != null) {
            this.b.i = resource.b;
            if (resource.b != 0) {
                this.d.b(this.b);
                this.d.a(this.b, 4);
            } else if (!(this.b.h == 3 || this.b.h == 6)) {
                this.d.a(this.b, resource);
            }
        } else if (this.b.m && this.b.h != 2) {
            this.d.a(this.b, this.b.n);
        }
        synchronized (this.b) {
            this.b.l = null;
        }
    }

    protected void b(b resource) {
    }

    public void c() {
        super.c();
    }

    public void d() {
        super.d();
    }

    protected com.meizu.cloud.download.b.e a(DownloadTaskInfo info) {
        return new com.meizu.cloud.download.b.c();
    }
}

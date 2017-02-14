package com.meizu.cloud.app.core;

public class u {
    private boolean a;
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean e;

    public boolean a() {
        return this.b;
    }

    public boolean b() {
        return this.c;
    }

    public void a(boolean isHistoryVersionPage) {
        e();
        this.c = isHistoryVersionPage;
    }

    public boolean c() {
        return this.d;
    }

    public void b(boolean isDownloadManagerPage) {
        e();
        this.d = isDownloadManagerPage;
    }

    public boolean d() {
        return this.e;
    }

    public void c(boolean isDetailPage) {
        e();
        this.e = isDetailPage;
    }

    private void e() {
        this.a = false;
        this.b = false;
        this.c = false;
        this.d = false;
        this.e = false;
    }
}

package com.meizu.cloud.app.b.a;

import android.content.Context;
import android.text.TextUtils;
import com.meizu.cloud.download.b.e;
import com.meizu.cloud.download.service.DownloadTaskInfo;
import com.meizu.update.d.b.c;
import com.meizu.update.d.c.b;
import com.meizu.update.d.c.d;

public class a implements e {
    private b a = new b(0);
    private d b;
    private com.meizu.update.d.d.a c;
    private String d;

    public a(Context context, DownloadTaskInfo info) {
        this.b = new com.meizu.update.d.c.a(context, info.b(), info.c(), info.d(), info.a(), info.e());
        this.a.a(info.f());
        this.c = new com.meizu.update.d.d.a(context);
        this.d = !TextUtils.isEmpty(info.c()) ? info.c() : "";
    }

    public String a() {
        return this.a.c();
    }

    public com.meizu.cloud.download.b.e.a a(Context context, String originalUrl) {
        c transform = this.a.a(context, originalUrl);
        if (transform == null) {
            return null;
        }
        transform.a(this.b);
        return new com.meizu.cloud.download.b.e.a(transform.a, transform.b);
    }

    public com.meizu.cloud.download.b.e.a b(Context context, String originalUrl) {
        c transform = this.a.b(context, originalUrl);
        if (transform != null) {
            return new com.meizu.cloud.download.b.e.a(transform.a, transform.b);
        }
        return null;
    }

    public com.meizu.cloud.download.b.a a(long httpRange, long contentLength) {
        com.meizu.update.d.c.c result = this.b.a(httpRange, contentLength);
        if (result.b()) {
            return com.meizu.cloud.download.b.a.a();
        }
        return com.meizu.cloud.download.b.a.a(result.c());
    }

    public com.meizu.cloud.download.b.a a(String filePath) {
        com.meizu.update.d.c.c result = this.b.a(filePath);
        if (result.b()) {
            return com.meizu.cloud.download.b.a.a();
        }
        return com.meizu.cloud.download.b.a.a(result.c());
    }

    public void a(boolean enable) {
    }

    public void b() {
        this.a.d();
    }

    public void a(String requestUrl, int responseCode, String redirectUrl, String msg) {
        String logVersion = null;
        if (this.b != null) {
            logVersion = this.b.d();
        }
        this.c.a(this.d, requestUrl, responseCode, redirectUrl, msg, logVersion);
    }

    public void b(String requestUrl, int responseCode, String redirectUrl, String msg) {
        String logVersion = null;
        if (this.b != null) {
            logVersion = this.b.d();
        }
        this.c.b(this.d, requestUrl, responseCode, redirectUrl, msg, logVersion);
    }

    public void a(String requestUrl, String redirectUrl, String msg) {
        String logVersion = null;
        if (this.b != null) {
            logVersion = this.b.d();
        }
        this.c.a(this.d, requestUrl, redirectUrl, msg, logVersion);
    }

    public void b(String requestUrl, String redirectUrl, String msg) {
        String logVersion = null;
        if (this.b != null) {
            logVersion = this.b.d();
        }
        this.c.b(this.d, requestUrl, redirectUrl, msg, logVersion);
    }
}

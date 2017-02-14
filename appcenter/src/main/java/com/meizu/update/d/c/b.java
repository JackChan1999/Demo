package com.meizu.update.d.c;

import android.content.Context;
import android.text.TextUtils;
import com.meizu.update.d.b.a;
import com.meizu.update.d.b.c;
import java.util.ArrayList;
import java.util.List;

public class b implements e {
    private final int a;
    private boolean b = false;
    private int c = -1;
    private List<String> d;
    private int e = 0;
    private boolean f = false;
    private int g = 0;

    public b(int maxRetryCount) {
        this.a = maxRetryCount;
    }

    public void a() {
        this.c++;
        if (this.f) {
            this.f = false;
            int i = this.g + 1;
            this.g = i;
            if (i <= 10) {
                com.meizu.update.h.b.c("Reduce download time while relocate 302: " + this.g);
                this.c--;
            }
        }
        com.meizu.update.h.b.c("start download time: " + (this.c + 1));
    }

    public boolean b() {
        return this.c < this.a;
    }

    public void a(String backupUrl) {
        if (TextUtils.isEmpty(backupUrl)) {
            this.d = null;
            return;
        }
        if (this.d == null) {
            this.d = new ArrayList(1);
        } else {
            this.d.clear();
        }
        this.d.add(backupUrl);
        this.e = 0;
    }

    public void a(List<String> urls) {
        this.d = urls;
        this.e = 0;
    }

    public String c() {
        if (this.d == null || this.d.size() <= this.e) {
            return null;
        }
        List list = this.d;
        int i = this.e;
        this.e = i + 1;
        return (String) list.get(i);
    }

    public c a(Context context, String url) {
        if (this.b) {
            com.meizu.update.h.b.d("Relocate had used before!");
        } else {
            com.meizu.update.d.b.b proxy = new a().a(context);
            if (proxy != null) {
                this.g = 0;
                this.b = true;
                c transform = proxy.a(url);
                if (transform != null) {
                    com.meizu.update.h.b.d("Transform url success: " + transform.a);
                    return transform;
                }
                com.meizu.update.h.b.d("Cant transform url: " + url + ", proxy: " + proxy);
            } else {
                com.meizu.update.h.b.d("Get relocate ip failed!");
            }
        }
        return null;
    }

    public void d() {
        if (this.b) {
            this.b = false;
        }
        a.a();
    }

    public c b(Context context, String originalUrl) {
        return null;
    }

    public void e() {
        this.f = true;
    }
}

package com.meizu.cloud.app.core;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import com.meizu.cloud.app.utils.h;
import java.io.File;

public class a {
    private final AppListLoader a;
    private final ApplicationInfo b;
    private final File c;
    private String d;
    private Drawable e;
    private boolean f;
    private long g;
    private long h;

    public a(AppListLoader loader, ApplicationInfo info) {
        this.a = loader;
        this.b = info;
        this.c = new File(info.sourceDir);
    }

    public ApplicationInfo a() {
        return this.b;
    }

    public String b() {
        return this.d;
    }

    public Drawable c() {
        File iconFile = new File(this.a.h().getCacheDir(), "app_icons" + File.separator + a().packageName);
        if (iconFile.exists()) {
            this.e = h.a(BitmapFactory.decodeFile(iconFile.getAbsolutePath()));
        }
        if (this.e == null) {
            if (this.c.exists()) {
                this.e = i.c(this.a.h(), this.b.packageName);
            } else {
                this.f = false;
            }
        } else if (!this.f && this.c.exists()) {
            this.f = true;
            this.e = i.c(this.a.h(), this.b.packageName);
        }
        if (this.e == null) {
            this.e = this.a.h().getResources().getDrawable(17301651);
        }
        return this.e;
    }

    public long d() {
        if (this.h == 0) {
            this.h = this.c.length();
        }
        return this.h;
    }

    public long e() {
        return this.g;
    }

    public String toString() {
        return this.d;
    }

    public void a(Context context) {
        if (this.d != null && this.f) {
            return;
        }
        if (this.c.exists()) {
            this.f = true;
            CharSequence label = this.b.loadLabel(context.getPackageManager());
            this.d = label != null ? label.toString() : this.b.packageName;
            return;
        }
        this.f = false;
        this.d = this.b.packageName;
    }

    public void a(long lastUpdateTime) {
        this.g = lastUpdateTime;
    }
}

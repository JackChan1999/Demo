package com.meizu.cloud.app.downlad;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.a.b;
import com.meizu.cloud.app.block.Blockable;
import com.meizu.cloud.app.core.d;
import com.meizu.cloud.app.core.k;
import com.meizu.cloud.app.core.t.a;
import com.meizu.cloud.app.downlad.f.c;
import com.meizu.cloud.app.downlad.f.f;
import com.meizu.cloud.app.downlad.f.h;
import com.meizu.cloud.app.downlad.f.j;
import com.meizu.cloud.app.downlad.f.l;
import com.meizu.cloud.app.downlad.f.n;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.DownloadInfo;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.request.structitem.HistoryVersions.VersionItem;
import com.meizu.cloud.app.request.structitem.PatchItem;
import com.meizu.cloud.app.utils.g;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.download.service.DownloadTaskInfo;

public final class e implements Blockable, a {
    @com.google.gson.a.a
    @b(a = "TaskProperty")
    g a;
    @com.google.gson.a.a
    @b(a = "AppStructItem")
    AppStructItem b;
    DownloadTaskInfo c;
    @com.google.gson.a.a
    @b(a = "VersionItem")
    VersionItem d;
    PatchItem e;
    @com.google.gson.a.a
    @b(a = "ServerUpdateAppInfo")
    ServerUpdateAppInfo f;
    @com.google.gson.a.a
    @b(a = "DownloadInfo")
    DownloadInfo g;
    private l h;
    private int i;
    private String j;
    private long k;
    private String l;
    private String m;
    private boolean n;
    private boolean o;
    private boolean p;
    private boolean q;
    private boolean r;
    private long s;
    private long t;
    private float u;
    private String v;
    private String w;
    private String x;
    private long y;
    private long z;

    public e(AppStructItem item) {
        this(item, new g());
    }

    public e(AppStructItem item, g taskProperty) {
        this.h = f.a.NOT_INSTALL;
        this.i = Integer.MIN_VALUE;
        this.j = null;
        this.r = true;
        this.p = true;
        this.b = item;
        if (taskProperty != null) {
            this.a = taskProperty;
        } else {
            this.a = new g();
        }
    }

    public e(AppStructItem item, VersionItem versionItem, g taskProperty) {
        this.h = f.a.NOT_INSTALL;
        this.i = Integer.MIN_VALUE;
        this.j = null;
        this.r = true;
        this.p = true;
        this.b = item;
        this.d = versionItem;
        if (taskProperty != null) {
            this.a = taskProperty;
        } else {
            this.a = new g();
        }
    }

    public e(Context context, ServerUpdateAppInfo updateItem, g taskProperty) {
        this.h = f.a.NOT_INSTALL;
        this.i = Integer.MIN_VALUE;
        this.j = null;
        this.r = true;
        this.p = true;
        this.f = updateItem;
        this.b = updateItem.getAppStructItem();
        this.e = updateItem.getPatchItem(context);
        if (taskProperty != null) {
            this.a = taskProperty;
        } else {
            this.a = new g();
        }
    }

    public String a() {
        return this.l;
    }

    public void a(String rawPackageName) {
        this.l = rawPackageName;
    }

    public final void a(DownloadInfo downloadInfo) {
        downloadInfo.download_url = g.b(downloadInfo.download_url);
        this.g = downloadInfo;
        this.b.software_file = this.g.download_url;
    }

    public final String b() {
        return this.b.software_file;
    }

    public final DownloadInfo c() {
        return this.g;
    }

    public final void a(l state, DownloadTaskInfo taskInfo) {
        this.q = true;
        a(state);
        if (f() instanceof c) {
            this.c = taskInfo;
        } else {
            this.c = null;
        }
    }

    public final void d() {
        this.h = f.a.NOT_INSTALL;
        this.b.software_file = null;
        this.c = null;
        this.i = -1;
        this.j = null;
        this.d = null;
        this.e = null;
        this.g = null;
        this.q = true;
    }

    public final boolean e() {
        return this.q;
    }

    private void a(l state) {
        if (state != null) {
            this.h = state;
        } else {
            d();
        }
    }

    public l f() {
        return this.h;
    }

    public String g() {
        return this.b.package_name;
    }

    public int h() {
        return this.b.version_code;
    }

    public int i() {
        return this.b.id;
    }

    public g j() {
        return this.a;
    }

    public String k() {
        return this.b.name;
    }

    public String l() {
        return this.b.version_name;
    }

    public AppStructItem m() {
        return this.b;
    }

    public String n() {
        return this.m == null ? a.a(this.b.package_name, this.b.version_code) : this.m;
    }

    public void b(String destFile) {
        this.m = destFile;
    }

    public final int o() {
        return (this.c == null || this.c.d == 0) ? 0 : (int) ((this.c.e * 100) / this.c.d);
    }

    public long p() {
        if (this.e != null) {
            return this.e.version_patch_size;
        }
        if (this.d != null) {
            return (long) this.d.size;
        }
        if (this.b.size != 0 || this.c == null) {
            return this.b.size;
        }
        return this.c.d;
    }

    public long q() {
        return this.c == null ? -1 : this.c.mId;
    }

    public int r() {
        return this.c == null ? 0 : this.c.f;
    }

    public int s() {
        return this.c == null ? 0 : this.c.g;
    }

    public long t() {
        return this.c == null ? 0 : this.c.e;
    }

    public void u() {
        this.k = System.currentTimeMillis();
    }

    public long v() {
        return this.k;
    }

    public void a(int errorType) {
        this.i = errorType;
    }

    public void c(String errorMessage) {
        this.j = errorMessage;
    }

    public String a(Context context) {
        l anEnum = f();
        if (anEnum instanceof n) {
            if (anEnum != n.FAILURE) {
                return null;
            }
            if (this.i == RequestConstants.CODE_APP_NOT_FOUND) {
                return context.getString(i.app_not_exist);
            }
            if (this.i == RequestConstants.CODE_APP_SIGN_ERROR) {
                return this.j;
            }
            return context.getString(i.get_download_url_failed);
        } else if (anEnum instanceof c) {
            if (this.i == 7) {
                return context.getString(i.server_disconnection);
            }
            if (this.i == 1) {
                return context.getString(i.disk_not_enough);
            }
            if (this.i == 2) {
                return context.getString(i.file_path_not_found);
            }
            return null;
        } else if (anEnum instanceof f) {
            return d.a(context, this.i);
        } else {
            if (anEnum == h.PATCHED_FAILURE) {
                return context.getString(i.update_pack_incompatible);
            }
            if (anEnum instanceof j) {
                return this.j;
            }
            return null;
        }
    }

    public String w() {
        return this.b.icon;
    }

    public String x() {
        return this.c == null ? null : this.c.k;
    }

    public int y() {
        return this.i;
    }

    public boolean z() {
        if (this.e == null || TextUtils.isEmpty(this.e.version_patch_md5)) {
            return false;
        }
        return true;
    }

    public boolean A() {
        if (this.f != null) {
            return true;
        }
        return false;
    }

    public String B() {
        return this.e == null ? null : this.e.version_origin_file_path;
    }

    public String C() {
        return this.e == null ? null : this.e.version_patch_md5;
    }

    public int D() {
        return this.e == null ? Integer.MIN_VALUE : this.e.version_code_local;
    }

    public boolean E() {
        return this.b.price > 0.0d;
    }

    public boolean F() {
        return this.d != null;
    }

    public int G() {
        return this.d == null ? -1 : this.d.version_code;
    }

    public String H() {
        return this.d == null ? "" : this.d.version_name;
    }

    public void I() {
        this.d = null;
        J();
    }

    public void J() {
        this.b.software_file = null;
        this.g = null;
    }

    public void K() {
        this.e = null;
        J();
    }

    public void L() {
        this.i = Integer.MIN_VALUE;
        this.j = null;
    }

    public int[] M() {
        return this.b.page_info;
    }

    public boolean N() {
        return this.b.paid;
    }

    public void a(boolean isPaid) {
        this.b.paid = isPaid;
    }

    public double O() {
        return this.b.price;
    }

    public boolean P() {
        return this.n;
    }

    public void b(boolean isTaskNotStart) {
        this.n = isTaskNotStart;
    }

    public boolean Q() {
        return this.o;
    }

    public void c(boolean isTry) {
        this.o = isTry;
    }

    public boolean R() {
        return this.p;
    }

    public void d(boolean isNewTask) {
        this.p = isNewTask;
    }

    public DownloadTaskInfo S() {
        return this.c;
    }

    public void e(boolean isAutoStart) {
        this.r = isAutoStart;
    }

    public boolean T() {
        return this.r;
    }

    public void a(e downloadWrapper) {
        this.h = downloadWrapper.h;
        this.i = downloadWrapper.i;
        this.j = downloadWrapper.j;
        this.k = downloadWrapper.k;
        this.l = downloadWrapper.l;
        this.m = downloadWrapper.m;
        this.n = downloadWrapper.n;
        this.o = downloadWrapper.o;
        this.p = downloadWrapper.p;
        this.a = downloadWrapper.a;
        this.b = downloadWrapper.b;
        this.c = downloadWrapper.c;
        this.d = downloadWrapper.d;
        this.e = downloadWrapper.e;
        this.f = downloadWrapper.f;
        this.g = downloadWrapper.g;
    }

    public void b(e downloadWrapper) {
        this.c = downloadWrapper.c;
    }

    public Class getBlockClass() {
        return getClass();
    }

    public k U() {
        if (F()) {
            return new k(this.b, this.d);
        }
        if (A()) {
            return new k(this.f);
        }
        return new k(this.b);
    }

    public void f(boolean isPause) {
        if (!isPause) {
            long curTime = System.currentTimeMillis();
            if (curTime <= this.s) {
                curTime = this.s;
            }
            this.s = curTime;
        } else if (this.s > 0) {
            long curDurTime = System.currentTimeMillis() - this.s;
            this.t = curDurTime > 0 ? this.t + curDurTime : this.t;
            this.s = 0;
        }
    }

    public void V() {
        long curDownloadTime = System.currentTimeMillis() - this.s;
        this.y = curDownloadTime > 0 ? this.t + curDownloadTime : this.t;
        this.z = t();
        this.u = this.y > 0 ? (float) ((this.z * 1000) / (this.y * 1024)) : 0.0f;
        this.t = 0;
        this.s = 0;
    }

    public float W() {
        return this.u;
    }

    public void X() {
        this.x = b();
        this.v = com.meizu.cloud.statistics.c.d(this.x);
        this.w = com.meizu.cloud.statistics.c.e(this.v);
    }

    public String Y() {
        return this.v;
    }

    public String Z() {
        return this.w;
    }

    public int aa() {
        return this.c == null ? 0 : this.c.i;
    }

    public String ab() {
        return this.x;
    }

    public long ac() {
        return this.y;
    }

    public long ad() {
        return this.z;
    }

    public static final String c(e wrapper) {
        return new com.google.gson.g().a().b().a((Object) wrapper);
    }
}

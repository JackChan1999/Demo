package com.meizu.cloud.app.downlad;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Pair;
import com.meizu.cloud.app.core.d.c;
import com.meizu.cloud.app.core.i;
import com.meizu.cloud.app.core.r;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.downlad.f.f;
import com.meizu.cloud.app.downlad.f.g;
import com.meizu.cloud.app.downlad.f.h;
import com.meizu.cloud.app.downlad.f.j;
import com.meizu.cloud.app.downlad.f.l;
import com.meizu.cloud.app.downlad.f.m;
import com.meizu.cloud.app.downlad.f.n;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.AppStructDetailsItem;
import com.meizu.cloud.app.request.model.DownloadInfo;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.request.structitem.AppStructItem.Columns;
import com.meizu.cloud.app.request.structitem.HistoryVersions.VersionItem;
import com.meizu.cloud.app.utils.c.a;
import com.meizu.cloud.app.utils.k;
import com.meizu.cloud.download.service.DownloadService;
import com.meizu.cloud.download.service.DownloadTaskInfo;
import com.meizu.cloud.statistics.b;
import com.meizu.e.e;
import com.meizu.upgrade.Bspatch;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class d {
    private static final String a = d.class.getSimpleName();
    private static d b;
    private Context c;
    private h d;
    private a e;
    private com.meizu.cloud.app.core.d f;
    private c g;
    private r h;
    private CopyOnWriteArraySet<e> i = new CopyOnWriteArraySet();
    private CopyOnWriteArraySet<e> j = new CopyOnWriteArraySet();
    private CopyOnWriteArraySet<e> k = new CopyOnWriteArraySet();
    private CopyOnWriteArraySet<e> l = new CopyOnWriteArraySet();
    private CopyOnWriteArraySet<Pair<m, g>> m = new CopyOnWriteArraySet();
    private Handler n;
    private c o = new c(this) {
        final /* synthetic */ d a;

        {
            this.a = r1;
        }

        public void a(e downloadWrapper, int returnCode) {
            if (downloadWrapper != null) {
                if (returnCode == 1) {
                    if (i.g(this.a.c, downloadWrapper.g())) {
                        com.meizu.cloud.app.core.m.c.b(this.a.c, downloadWrapper.g());
                    }
                    downloadWrapper.a(f.INSTALL_SUCCESS, null);
                    this.a.a(null, downloadWrapper);
                    String imei = com.meizu.cloud.app.utils.d.a(this.a.c);
                    String sn = com.meizu.cloud.app.utils.d.b(this.a.c);
                    ArrayList<Integer> ids = new ArrayList();
                    ids.add(Integer.valueOf(downloadWrapper.i()));
                    String url = RequestConstants.getRuntimeDomainUrl(this.a.c, RequestConstants.DOWNLOAD_CALLBACK);
                    long[] code = null;
                    String appKey = "apps";
                    if (url.contains("api-game.meizu.com")) {
                        code = a.b;
                        appKey = "games";
                    } else if (url.contains("api-app.meizu.com")) {
                        code = a.a;
                        appKey = "apps";
                    }
                    com.meizu.cloud.app.utils.f.a(this.a.c, url, imei, sn, appKey, ids, code, downloadWrapper);
                    com.meizu.cloud.app.update.exclude.a.a(this.a.c).c(downloadWrapper.g(), downloadWrapper.h());
                    if (downloadWrapper.g().equals("com.meizu.cloud")) {
                        Intent intent = new Intent("com.meizu.cloud.service.MzCloudService");
                        intent.setPackage("com.meizu.cloud");
                        this.a.c.startService(intent);
                    }
                } else {
                    downloadWrapper.J();
                    downloadWrapper.a(returnCode);
                    downloadWrapper.a(f.INSTALL_FAILURE, null);
                    this.a.a(null, downloadWrapper);
                    if (downloadWrapper.j().a(0) && downloadWrapper.j().c()) {
                        com.meizu.cloud.app.update.exclude.a.a(this.a.c).a(downloadWrapper.g(), downloadWrapper.h());
                    }
                }
                if (AppDownloadService.a() == null) {
                    return;
                }
                if (this.a.j.size() == 0) {
                    AppDownloadService.a().c();
                } else {
                    AppDownloadService.a().b();
                }
            }
        }

        public void a(String packageName, int returnCode) {
            if (!TextUtils.isEmpty(packageName)) {
                e wrapper = this.a.b(packageName);
                if (returnCode != 1) {
                    if (wrapper != null) {
                        wrapper.a(returnCode);
                        wrapper.a(f.DELETE_FAILURE, null);
                        this.a.a(null, wrapper);
                    }
                } else if (wrapper != null) {
                    wrapper.a(f.DELETE_SUCCESS, null);
                    this.a.a(null, wrapper);
                }
            }
        }
    };
    private ExecutorService p = Executors.newSingleThreadExecutor();

    private d(Context context) {
        DownloadService.a(context.getApplicationContext(), AppDownloadService.class);
        this.c = context;
        this.d = new h(context);
        this.e = new a(context);
        this.f = new com.meizu.cloud.app.core.d(context);
        this.g = new c(context);
        this.h = r.a(context);
        this.n = new Handler(context.getMainLooper());
        com.meizu.cloud.app.core.f.a(context.getApplicationContext()).a(this);
    }

    public static synchronized d a(Context context) {
        d dVar;
        synchronized (d.class) {
            if (b == null) {
                b = new d(context.getApplicationContext());
            }
            dVar = b;
        }
        return dVar;
    }

    public e a(AppStructItem appInfo, g taskProperty) {
        if (l(appInfo.package_name)) {
            return new e(appInfo, taskProperty);
        }
        e downloadWrapper = a(appInfo.package_name, appInfo.version_code);
        if (downloadWrapper == null) {
            return new e(appInfo, taskProperty);
        }
        if (!downloadWrapper.R()) {
            return downloadWrapper;
        }
        downloadWrapper.d(false);
        return downloadWrapper;
    }

    public e a(AppStructItem appInfo, DownloadInfo downloadInfo, g taskProperty) {
        e downloadWrapper;
        if (f(appInfo.package_name)) {
            downloadWrapper = a(appInfo.package_name, appInfo.version_code);
            if (downloadWrapper != null) {
                if (downloadWrapper.R()) {
                    downloadWrapper.d(false);
                }
                return downloadWrapper;
            }
            e wrapper = new e(appInfo, taskProperty);
            wrapper.a(downloadInfo);
            return wrapper;
        }
        downloadWrapper = new e(appInfo, taskProperty);
        downloadWrapper.a(downloadInfo);
        return downloadWrapper;
    }

    public e a(AppStructItem appInfo, VersionItem versionItem, g taskProperty) {
        if (l(appInfo.package_name)) {
            return new e(appInfo, versionItem, taskProperty);
        }
        e downloadWrapper = a(appInfo.package_name, versionItem.version_code);
        if (downloadWrapper == null) {
            return new e(appInfo, versionItem, taskProperty);
        }
        if (downloadWrapper.R()) {
            downloadWrapper.d(false);
        }
        if (downloadWrapper.G() != versionItem.version_code) {
            return new e(appInfo, versionItem, taskProperty);
        }
        return downloadWrapper;
    }

    public e b(AppStructItem appInfo, g taskProperty) {
        if (l(appInfo.package_name)) {
            e downloadWrapper = new e(appInfo, taskProperty);
            downloadWrapper.c(true);
            return downloadWrapper;
        }
        downloadWrapper = a(appInfo.package_name, appInfo.version_code);
        if (downloadWrapper != null) {
            if (downloadWrapper.R()) {
                downloadWrapper.d(false);
            }
            return downloadWrapper;
        }
        e wrapper = new e(appInfo, taskProperty);
        wrapper.c(true);
        return wrapper;
    }

    public e a(ServerUpdateAppInfo updateAppInfo, g taskProperty) {
        if (l(updateAppInfo.package_name)) {
            return new e(this.c, updateAppInfo, taskProperty);
        }
        e downloadWrapper = a(updateAppInfo.package_name, updateAppInfo.version_code);
        if (downloadWrapper == null) {
            return new e(this.c, updateAppInfo, taskProperty);
        }
        if (!downloadWrapper.R()) {
            return downloadWrapper;
        }
        downloadWrapper.d(false);
        return downloadWrapper;
    }

    public synchronized void a(m stateEvent) {
        if (this.m != null) {
            this.m.add(Pair.create(stateEvent, new g()));
        }
    }

    public synchronized void a(m stateEvent, g taskProperty) {
        if (this.m != null) {
            this.m.add(Pair.create(stateEvent, taskProperty));
        }
    }

    public synchronized void b(m stateEvent) {
        if (this.m != null) {
            Iterator i$ = this.m.iterator();
            while (i$.hasNext()) {
                Pair<m, g> eventCallback = (Pair) i$.next();
                if (eventCallback.first == stateEvent) {
                    this.m.remove(eventCallback);
                }
            }
        }
    }

    private boolean l(String packageName) {
        if (f(packageName)) {
            return false;
        }
        return true;
    }

    public void a(final FragmentActivity ui, final e downloadWrapper) {
        if (downloadWrapper != null) {
            this.n.post(new Runnable(this) {
                final /* synthetic */ d c;

                public void run() {
                    int stateResult = this.c.d(downloadWrapper);
                    if (stateResult == 1) {
                        a.a.a.c.a().d(new com.meizu.cloud.app.c.i(1));
                    }
                    l anEnum = downloadWrapper.f();
                    if (anEnum instanceof f.a) {
                        if (stateResult == 1) {
                            if (this.c.e(downloadWrapper.g())) {
                                com.meizu.cloud.app.core.f.a().a(downloadWrapper.g());
                                com.meizu.cloud.app.core.f.a().b(downloadWrapper.g());
                            }
                            if (downloadWrapper.c() == null) {
                                try {
                                    this.c.d.a(ui, downloadWrapper);
                                } catch (com.meizu.c.d e) {
                                    com.meizu.cloud.app.utils.a.a(this.c.c, this.c.c.getString(com.meizu.cloud.b.a.i.interface_not_exist));
                                    this.c.e(downloadWrapper);
                                }
                            } else {
                                if (downloadWrapper.f() != n.SUCCESS) {
                                    downloadWrapper.a(n.SUCCESS, null);
                                }
                                this.c.b(downloadWrapper);
                                if (downloadWrapper.j().a(1) || downloadWrapper.j().a(3)) {
                                    this.c.q(downloadWrapper);
                                }
                            }
                        }
                    } else if (anEnum instanceof n) {
                        if (stateResult == 1) {
                            this.c.k(downloadWrapper);
                            if (anEnum == n.SUCCESS) {
                                this.c.b(downloadWrapper);
                                if (downloadWrapper.j().a(1) || downloadWrapper.j().a(3)) {
                                    this.c.q(downloadWrapper);
                                }
                            } else if (anEnum == n.FAILURE) {
                                this.c.h(downloadWrapper);
                                if (!downloadWrapper.m().is_fromPlugin) {
                                    b.a().a("download_status", downloadWrapper.m().install_page, com.meizu.cloud.statistics.c.b(this.c.c(downloadWrapper), 0, downloadWrapper.a(this.c.c), downloadWrapper));
                                }
                            } else if (anEnum == n.CANCEL) {
                                this.c.d.a(downloadWrapper);
                                this.c.e(downloadWrapper);
                            } else if (anEnum == n.FETCHING) {
                                appStructItem = downloadWrapper.m();
                                if (appStructItem.is_fromPlugin && appStructItem.is_Plugin_CPD) {
                                    com.meizu.cloud.statistics.a.a(this.c.c.getApplicationContext()).a(appStructItem, 0);
                                }
                            }
                        }
                    } else if (anEnum instanceof f.c) {
                        if (stateResult == 1) {
                            this.c.n(downloadWrapper);
                            if (anEnum == f.c.TASK_COMPLETED) {
                                downloadWrapper.V();
                                if (downloadWrapper.j().a(1) || downloadWrapper.j().a(3)) {
                                    this.c.r(downloadWrapper);
                                }
                                this.c.a(downloadWrapper, downloadWrapper.S());
                                if (!downloadWrapper.m().is_fromPlugin) {
                                    if (this.c.a(downloadWrapper, 1) != -1) {
                                        com.meizu.cloud.statistics.a.a(this.c.c).a(downloadWrapper.m(), this.c.a(downloadWrapper, 1));
                                    }
                                    b.a().a("download_status", downloadWrapper.m().install_page, com.meizu.cloud.statistics.c.b(this.c.c(downloadWrapper), 1, "", downloadWrapper));
                                } else if (downloadWrapper.m().is_Plugin_CPD) {
                                    com.meizu.cloud.statistics.a.a(this.c.c).a(downloadWrapper.m(), 1);
                                }
                            } else if (anEnum == f.c.TASK_ERROR) {
                                downloadWrapper.V();
                                if (downloadWrapper.j().a(1) || downloadWrapper.j().a(3)) {
                                    this.c.r(downloadWrapper);
                                }
                                this.c.h(downloadWrapper);
                                if (downloadWrapper.m().is_fromPlugin) {
                                    b.a().a("jxcj_downloadfail", "plugin", com.meizu.cloud.statistics.c.b(downloadWrapper.m().package_name));
                                } else {
                                    b.a().a("download_status", downloadWrapper.m().install_page, com.meizu.cloud.statistics.c.b(this.c.c(downloadWrapper), 0, downloadWrapper.a(this.c.c), downloadWrapper));
                                }
                            } else if (anEnum == f.c.TASK_REMOVED) {
                                downloadWrapper.V();
                                if (downloadWrapper.j().a(1) || downloadWrapper.j().a(3)) {
                                    this.c.r(downloadWrapper);
                                }
                                try {
                                    if (!TextUtils.isEmpty(downloadWrapper.x())) {
                                        File file = new File(downloadWrapper.x());
                                        if (file.exists() && file.isFile()) {
                                            file.setLastModified(System.currentTimeMillis());
                                        }
                                    }
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                                this.c.e.c(downloadWrapper.q());
                                if (downloadWrapper.T()) {
                                    this.c.e(downloadWrapper);
                                } else {
                                    this.c.f(downloadWrapper);
                                }
                                this.c.a(downloadWrapper, true);
                            } else if (anEnum == f.c.TASK_CREATED || anEnum == f.c.TASK_WAITING || anEnum == f.c.TASK_STARTED) {
                                if (anEnum == f.c.TASK_STARTED && (downloadWrapper.j().a(1) || downloadWrapper.j().a(3))) {
                                    this.c.q(downloadWrapper);
                                }
                            } else if (anEnum == f.c.TASK_RESUME) {
                                this.c.e.a(downloadWrapper.q());
                                appStructItem = downloadWrapper.m();
                                if (appStructItem != null && appStructItem.is_fromPlugin) {
                                    b.a().a("jxcj_downloadpause", "plugin", com.meizu.cloud.statistics.c.b(appStructItem.package_name));
                                }
                            } else if (anEnum == f.c.TASK_PAUSED) {
                                appStructItem = downloadWrapper.m();
                                if (appStructItem != null && appStructItem.is_fromPlugin) {
                                    b.a().a("jxcj_pause", "plugin", com.meizu.cloud.statistics.c.b(appStructItem.package_name));
                                }
                            }
                        } else if (stateResult == 0 && downloadWrapper.f() == f.c.TASK_STARTED) {
                            this.c.o(downloadWrapper);
                        }
                    } else if (anEnum instanceof h) {
                        if (stateResult == 1) {
                            this.c.m(downloadWrapper);
                            if (anEnum == h.PATCHED_FAILURE) {
                                this.c.h(downloadWrapper);
                            } else if (anEnum == h.PATCHED_SUCCESS) {
                            }
                        }
                    } else if (anEnum instanceof j) {
                        if (stateResult == 1) {
                            this.c.l(downloadWrapper);
                            if (anEnum == j.FAILURE) {
                                this.c.h(downloadWrapper);
                            } else if (anEnum == j.SUCCESS) {
                                com.meizu.cloud.app.core.c compareResult = x.d(this.c.c).a(downloadWrapper.g(), downloadWrapper.h());
                                if (compareResult == com.meizu.cloud.app.core.c.NOT_INSTALL || compareResult == com.meizu.cloud.app.core.c.UPGRADE || compareResult == com.meizu.cloud.app.core.c.DOWNGRADE) {
                                    this.c.b(downloadWrapper);
                                } else if (compareResult == com.meizu.cloud.app.core.c.OPEN) {
                                    downloadWrapper.a(f.INSTALL_SUCCESS, null);
                                    this.c.a(null, downloadWrapper);
                                }
                            } else if (anEnum == j.CANCEL) {
                                this.c.e(downloadWrapper);
                            }
                        }
                    } else if ((anEnum instanceof f) && stateResult == 1) {
                        this.c.p(downloadWrapper);
                        if (anEnum == f.INSTALL_FAILURE) {
                            this.c.h(downloadWrapper);
                            if (!downloadWrapper.m().is_fromPlugin) {
                                b.a().a(Columns.INSTALL_STATUS, downloadWrapper.m().install_page, com.meizu.cloud.statistics.c.a(this.c.c(downloadWrapper), 0, downloadWrapper.a(this.c.c), downloadWrapper));
                            }
                        } else if (anEnum == f.DELETE_SUCCESS) {
                            if (downloadWrapper.F()) {
                                this.c.f.a(downloadWrapper, this.c.o);
                            } else {
                                this.c.e(downloadWrapper);
                            }
                        } else if (anEnum == f.INSTALL_SUCCESS) {
                            if (downloadWrapper.F()) {
                                if (downloadWrapper.b instanceof AppStructDetailsItem) {
                                    AppStructDetailsItem appStructDetailsItem = downloadWrapper.b;
                                    final ServerUpdateAppInfo updateAppInfo = ServerUpdateAppInfo.toServerUpdateAppInfo(this.c.c, downloadWrapper.b);
                                    updateAppInfo.version_create_time = appStructDetailsItem.version_time;
                                    updateAppInfo.update_description = appStructDetailsItem.update_description;
                                    if (x.b(this.c.c)) {
                                        updateAppInfo.category_id = 2;
                                    }
                                    this.c.p.execute(new Runnable(this) {
                                        final /* synthetic */ AnonymousClass1 b;

                                        public void run() {
                                            this.b.c.h.a(updateAppInfo);
                                        }
                                    });
                                }
                            } else if (downloadWrapper.A()) {
                                downloadWrapper.I();
                                this.c.p.execute(new Runnable(this) {
                                    final /* synthetic */ AnonymousClass1 a;

                                    {
                                        this.a = r1;
                                    }

                                    public void run() {
                                        this.a.c.h.a(downloadWrapper.f.id);
                                        this.a.c.h.a(downloadWrapper.f, downloadWrapper.v());
                                    }
                                });
                            } else if (downloadWrapper.j().f() == 8) {
                                this.c.p.execute(new Runnable(this) {
                                    final /* synthetic */ AnonymousClass1 a;

                                    {
                                        this.a = r1;
                                    }

                                    public void run() {
                                        this.a.c.h.a(downloadWrapper.m(), downloadWrapper.v());
                                        ServerUpdateAppInfo serverUpdateAppInfo = ServerUpdateAppInfo.toServerUpdateAppInfo(this.a.c.c, downloadWrapper.m());
                                        serverUpdateAppInfo.is_latest_version = 1;
                                        this.a.c.h.a(serverUpdateAppInfo);
                                    }
                                });
                            } else if (x.b(this.c.c)) {
                                this.c.p.execute(new Runnable(this) {
                                    final /* synthetic */ AnonymousClass1 a;

                                    {
                                        this.a = r1;
                                    }

                                    public void run() {
                                        ServerUpdateAppInfo updateAppInfo = ServerUpdateAppInfo.toServerUpdateAppInfo(this.a.c.c, downloadWrapper.b);
                                        updateAppInfo.category_id = 2;
                                        updateAppInfo.is_latest_version = 1;
                                        this.a.c.h.a(updateAppInfo);
                                    }
                                });
                            }
                            this.c.i(downloadWrapper);
                            this.c.a(downloadWrapper, false);
                            if (downloadWrapper.m().is_fromPlugin) {
                                appStructItem = downloadWrapper.m();
                                if (appStructItem.is_Plugin_CPD) {
                                    com.meizu.cloud.statistics.a.a(this.c.c.getApplicationContext()).a(appStructItem, 2);
                                }
                                b.a().a("jxcj_install_success", "plugin", com.meizu.cloud.statistics.c.b(appStructItem.package_name));
                            } else {
                                if (this.c.a(downloadWrapper, 2) != -1) {
                                    com.meizu.cloud.statistics.a.a(this.c.c).a(downloadWrapper.m(), this.c.a(downloadWrapper, 2));
                                }
                                b.a().a(Columns.INSTALL_STATUS, downloadWrapper.m().install_page, com.meizu.cloud.statistics.c.a(this.c.c(downloadWrapper), 1, "", downloadWrapper));
                            }
                        } else if (anEnum == f.INSTALL_START && downloadWrapper.m().is_fromPlugin) {
                            b.a().a("jxcj_install", "plugin", com.meizu.cloud.statistics.c.b(downloadWrapper.m().package_name));
                        }
                    }
                    if (f.c(anEnum)) {
                        if (downloadWrapper.z()) {
                            downloadWrapper.K();
                        }
                        if (downloadWrapper.A() && downloadWrapper.y() == RequestConstants.CODE_APP_NOT_FOUND) {
                            this.c.p.execute(new Runnable(this) {
                                final /* synthetic */ AnonymousClass1 a;

                                {
                                    this.a = r1;
                                }

                                public void run() {
                                    this.a.c.h.a(downloadWrapper.g());
                                }
                            });
                        }
                        if (!downloadWrapper.j().e()) {
                            this.c.g(downloadWrapper);
                        }
                        if (!com.meizu.cloud.app.core.d.a(downloadWrapper.y())) {
                            this.c.a(downloadWrapper, true);
                        }
                    }
                    if (!((anEnum instanceof f.c) && anEnum == f.c.TASK_STARTED)) {
                        k.a(this.c.c, d.a, downloadWrapper.g() + ":" + anEnum.toString() + ":" + stateResult);
                    }
                    if (AppDownloadService.a() == null) {
                        return;
                    }
                    if (this.c.j.size() == 0) {
                        AppDownloadService.a().c();
                    } else {
                        AppDownloadService.a().b();
                    }
                }
            });
        }
    }

    private boolean b(e downloadWrapper) {
        if (!downloadWrapper.T()) {
            downloadWrapper.a(f.c.TASK_PAUSED, null);
            a(null, downloadWrapper);
        } else if (this.e.a(downloadWrapper) != -1) {
            return true;
        }
        return false;
    }

    public synchronized void a(String packageName) {
        Iterator i$ = this.i.iterator();
        while (i$.hasNext()) {
            e downloadWrapper = (e) i$.next();
            if (downloadWrapper.g().equals(packageName)) {
                downloadWrapper.e(true);
                b(downloadWrapper);
                this.j.add(downloadWrapper);
                this.i.remove(downloadWrapper);
                break;
            }
        }
    }

    public synchronized void a() {
        Iterator i$ = this.i.iterator();
        while (i$.hasNext()) {
            e downloadWrapper = (e) i$.next();
            this.j.add(downloadWrapper);
            downloadWrapper.e(true);
            b(downloadWrapper);
        }
        this.i.clear();
    }

    private int c(e wrapper) {
        if (wrapper.j().f() != 8) {
            return 1;
        }
        if (wrapper.j().a(0)) {
            return 3;
        }
        if (wrapper.j().a(1)) {
            return 2;
        }
        return 1;
    }

    private int a(e wrapper, int from) {
        if (wrapper.j().f() == 8) {
            if (from == 1) {
                return 5;
            }
            if (from == 2) {
                return 6;
            }
            return -1;
        } else if (wrapper.j().f() == 20) {
            return -1;
        } else {
            if (from == 1) {
                return 1;
            }
            return 2;
        }
    }

    public e b(String packageName) {
        Iterator i$ = this.j.iterator();
        while (i$.hasNext()) {
            e downloadWrapper = (e) i$.next();
            if (downloadWrapper.g().equals(packageName)) {
                return downloadWrapper;
            }
        }
        i$ = this.i.iterator();
        while (i$.hasNext()) {
            downloadWrapper = (e) i$.next();
            if (downloadWrapper.g().equals(packageName)) {
                return downloadWrapper;
            }
        }
        return null;
    }

    public e a(String packageName, int versionCode) {
        Iterator i$ = this.j.iterator();
        while (i$.hasNext()) {
            e downloadWrapper = (e) i$.next();
            if (downloadWrapper.g().equals(packageName) && (!downloadWrapper.F() || downloadWrapper.G() == versionCode)) {
                return downloadWrapper;
            }
        }
        i$ = this.i.iterator();
        while (i$.hasNext()) {
            downloadWrapper = (e) i$.next();
            if (downloadWrapper.g().equals(packageName)) {
                if (!downloadWrapper.F()) {
                    return downloadWrapper;
                }
                if (downloadWrapper.G() == versionCode) {
                    return downloadWrapper;
                }
            }
        }
        return null;
    }

    public e a(long id) {
        Iterator i$ = this.j.iterator();
        while (i$.hasNext()) {
            e downloadWrapper = (e) i$.next();
            if (downloadWrapper.q() == id) {
                return downloadWrapper;
            }
        }
        i$ = this.i.iterator();
        while (i$.hasNext()) {
            downloadWrapper = (e) i$.next();
            if (downloadWrapper.q() == id) {
                return downloadWrapper;
            }
        }
        return null;
    }

    private synchronized int d(e downloadWrapper) {
        int i = -1;
        synchronized (this) {
            Iterator i$;
            e wrapper;
            if (downloadWrapper.T()) {
                if (downloadWrapper.R()) {
                    downloadWrapper.d(false);
                    i$ = this.j.iterator();
                    while (i$.hasNext()) {
                        if (((e) i$.next()).g().equals(downloadWrapper.g())) {
                            break;
                        }
                    }
                    if (this.j.add(downloadWrapper)) {
                        i = 1;
                    }
                } else if (downloadWrapper.f() == f.DELETE_START && com.meizu.cloud.app.core.d.a(downloadWrapper.y())) {
                    downloadWrapper.L();
                    if (this.j.add(downloadWrapper)) {
                        i = 1;
                    }
                } else {
                    i$ = this.j.iterator();
                    while (i$.hasNext()) {
                        wrapper = (e) i$.next();
                        if (wrapper.g().equals(downloadWrapper.g())) {
                            if (downloadWrapper.f() != wrapper.f()) {
                                wrapper.a(downloadWrapper);
                                i = 1;
                            } else if (wrapper.f() == f.c.TASK_STARTED) {
                                wrapper.b(downloadWrapper);
                                i = 0;
                            } else if (downloadWrapper != wrapper) {
                                wrapper.a(downloadWrapper);
                                i = 1;
                            } else if (downloadWrapper.e()) {
                                i = 1;
                            }
                        }
                    }
                }
            } else if (downloadWrapper.R()) {
                downloadWrapper.d(false);
                i$ = this.i.iterator();
                while (i$.hasNext()) {
                    if (((e) i$.next()).g().equals(downloadWrapper.g())) {
                        break;
                    }
                }
                if (this.i.add(downloadWrapper)) {
                    i = 1;
                }
            } else {
                i$ = this.i.iterator();
                while (i$.hasNext()) {
                    wrapper = (e) i$.next();
                    if (wrapper.g().equals(downloadWrapper.g())) {
                        if (downloadWrapper.f() != wrapper.f()) {
                            wrapper.a(downloadWrapper);
                            i = 1;
                        } else if (downloadWrapper != wrapper) {
                            wrapper.a(downloadWrapper);
                            i = 1;
                        } else if (downloadWrapper.e()) {
                            i = 1;
                        }
                    }
                }
            }
        }
        return i;
    }

    private synchronized boolean e(e downloadWrapper) {
        boolean isPopSuccess;
        isPopSuccess = this.j.remove(downloadWrapper) || a(downloadWrapper.g(), this.j);
        if (isPopSuccess) {
            a.a.a.c.a().d(new com.meizu.cloud.app.c.i(-1));
        }
        return isPopSuccess;
    }

    private synchronized boolean f(e downloadWrapper) {
        boolean isPopSuccess;
        isPopSuccess = this.i.remove(downloadWrapper) || a(downloadWrapper.g(), this.i);
        if (isPopSuccess) {
            a.a.a.c.a().d(new com.meizu.cloud.app.c.i(-1));
        }
        return isPopSuccess;
    }

    private synchronized boolean g(e downloadWrapper) {
        boolean z;
        z = this.k.remove(downloadWrapper) || a(downloadWrapper.g(), this.k);
        return z;
    }

    private synchronized boolean a(String pkgName, Set<e> set) {
        boolean remove;
        for (e wrapper : set) {
            if (wrapper.g().equals(pkgName)) {
                remove = set.remove(wrapper);
                break;
            }
        }
        remove = false;
        return remove;
    }

    private synchronized boolean h(e downloadWrapper) {
        downloadWrapper.u();
        e(downloadWrapper);
        return this.k.add(downloadWrapper);
    }

    private synchronized boolean i(e downloadWrapper) {
        downloadWrapper.u();
        e(downloadWrapper);
        return this.l.add(downloadWrapper);
    }

    public synchronized boolean c(String packageName) {
        boolean remove;
        Iterator i$ = this.k.iterator();
        while (i$.hasNext()) {
            e downloadWrapper = (e) i$.next();
            if (downloadWrapper.g().equals(packageName)) {
                l anEnum = downloadWrapper.f();
                if (anEnum == n.FAILURE || anEnum == f.c.TASK_ERROR || anEnum == f.INSTALL_FAILURE || anEnum == f.DELETE_FAILURE || anEnum == h.PATCHED_FAILURE || anEnum == j.FAILURE) {
                    com.meizu.cloud.app.core.f.a().b(packageName);
                    remove = this.k.remove(downloadWrapper);
                    break;
                }
            }
        }
        remove = false;
        return remove;
    }

    public synchronized boolean d(String packageName) {
        boolean isSuccess = false;
        synchronized (this) {
            Iterator i$ = this.l.iterator();
            while (i$.hasNext()) {
                e wrapper = (e) i$.next();
                if (wrapper.g().equals(packageName)) {
                    if (f.a(wrapper.f()) && this.l.remove(wrapper)) {
                        isSuccess = true;
                    }
                    if (isSuccess) {
                        com.meizu.cloud.app.core.f.a().a(packageName);
                    }
                }
            }
            i$ = this.k.iterator();
            while (i$.hasNext()) {
                wrapper = (e) i$.next();
                if (wrapper.g().equals(packageName)) {
                    if (f.a(wrapper.f()) && this.k.remove(wrapper)) {
                        isSuccess = true;
                    }
                    if (isSuccess) {
                        com.meizu.cloud.app.core.f.a().b(packageName);
                    }
                }
            }
        }
        return isSuccess;
    }

    public synchronized boolean b() {
        List<e> stayRemoves;
        stayRemoves = new ArrayList();
        Iterator i$ = this.l.iterator();
        while (i$.hasNext()) {
            e downloadWrapper = (e) i$.next();
            if (downloadWrapper.f() == f.INSTALL_SUCCESS) {
                stayRemoves.add(downloadWrapper);
            }
        }
        com.meizu.cloud.app.core.f.a().a(1000);
        return this.l.removeAll(stayRemoves);
    }

    public synchronized boolean e(String pkgName) {
        boolean isRemove;
        isRemove = a(pkgName, this.k) || a(pkgName, this.l);
        if (isRemove) {
            a.a.a.c.a().d(new com.meizu.cloud.app.c.f(pkgName));
        }
        return isRemove;
    }

    public synchronized void c() {
        Iterator i$ = this.j.iterator();
        while (i$.hasNext()) {
            e downloadWrapper = (e) i$.next();
            l stateEnum = t.a(downloadWrapper);
            if (stateEnum != null) {
                downloadWrapper.a(stateEnum, downloadWrapper.S());
                a(null, downloadWrapper);
            }
        }
        i$ = this.i.iterator();
        while (i$.hasNext()) {
            downloadWrapper = (e) i$.next();
            stateEnum = t.a(downloadWrapper);
            if (stateEnum != null) {
                downloadWrapper.a(stateEnum, downloadWrapper.S());
                a(null, downloadWrapper);
            }
        }
    }

    public int d() {
        return a(1, 3).size();
    }

    public synchronized List<e> a(int... taskPropertys) {
        List<e> startWrappers;
        List<DownloadTaskInfo> startTasks = this.e.c();
        startWrappers = new ArrayList(startTasks.size());
        for (int taskProperty : taskPropertys) {
            for (DownloadTaskInfo startTask : startTasks) {
                e wrapper = a(startTask.mId);
                if (wrapper != null && wrapper.f() == f.c.TASK_STARTED && wrapper.j().a(taskProperty)) {
                    startWrappers.add(wrapper);
                }
            }
        }
        return startWrappers;
    }

    public synchronized List<e> b(int... taskPropertys) {
        List<e> waitWrappers;
        waitWrappers = new ArrayList(this.j.size());
        for (int taskProperty : taskPropertys) {
            Iterator i$ = this.j.iterator();
            while (i$.hasNext()) {
                e waittingTask = (e) i$.next();
                if ((waittingTask.f() == n.FETCHING || waittingTask.f() == f.c.TASK_CREATED || waittingTask.f() == f.c.TASK_WAITING) && waittingTask.j().a(taskProperty)) {
                    waitWrappers.add(waittingTask);
                }
            }
        }
        return waitWrappers;
    }

    public List<e> e() {
        return a(false, 1, 3);
    }

    public List<e> a(boolean realAll, int... taskPropertys) {
        ArrayList<e> appInfos = new ArrayList();
        for (int taskProperty : taskPropertys) {
            for (e appInfoDownload : l()) {
                if (realAll || appInfoDownload.j().a(taskProperty)) {
                    appInfos.add(appInfoDownload);
                }
            }
        }
        return appInfos;
    }

    public List<e> c(int... taskPropertys) {
        ArrayList<e> appInfos = new ArrayList();
        for (int taskProperty : taskPropertys) {
            for (e appInfoDownload : l()) {
                if (appInfoDownload.j().a(taskProperty) && (appInfoDownload.A() || appInfoDownload.j().f() == 8)) {
                    appInfos.add(appInfoDownload);
                }
            }
        }
        return appInfos;
    }

    public List<e> f() {
        return d(1, 3);
    }

    public List<e> d(int... taskPropertys) {
        ArrayList<e> successTasks = new ArrayList();
        for (int taskProperty : taskPropertys) {
            Iterator i$ = this.l.iterator();
            while (i$.hasNext()) {
                e downloadWrapper = (e) i$.next();
                if (downloadWrapper.f() == f.INSTALL_SUCCESS && downloadWrapper.j().a(taskProperty)) {
                    successTasks.add(downloadWrapper);
                }
            }
        }
        Collections.sort(successTasks, new Comparator<e>(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public /* synthetic */ int compare(Object obj, Object obj2) {
                return a((e) obj, (e) obj2);
            }

            public int a(e lhs, e rhs) {
                if (lhs.v() == rhs.v()) {
                    return 0;
                }
                return lhs.v() < rhs.v() ? 1 : -1;
            }
        });
        return successTasks;
    }

    public List<e> g() {
        return e(1, 3);
    }

    public List<e> e(int... taskPropertys) {
        ArrayList<e> failureTasks = new ArrayList();
        for (int taskProperty : taskPropertys) {
            Iterator i$ = this.k.iterator();
            while (i$.hasNext()) {
                e downloadWrapper = (e) i$.next();
                l anEnum = downloadWrapper.f();
                if ((anEnum == n.FAILURE || anEnum == f.c.TASK_ERROR || anEnum == f.INSTALL_FAILURE || anEnum == j.FAILURE || anEnum == h.PATCHED_FAILURE) && downloadWrapper.j().a(taskProperty)) {
                    failureTasks.add(downloadWrapper);
                }
            }
        }
        return failureTasks;
    }

    public List<e> h() {
        return f(1, 3);
    }

    public List<e> f(int... taskPropertys) {
        ArrayList<e> pauseTask = new ArrayList();
        for (int taskProperty : taskPropertys) {
            Iterator i$ = this.j.iterator();
            while (i$.hasNext()) {
                e downloadWrapper = (e) i$.next();
                if (downloadWrapper.f() == f.c.TASK_PAUSED && downloadWrapper.j().a(taskProperty)) {
                    pauseTask.add(downloadWrapper);
                }
            }
            i$ = this.i.iterator();
            while (i$.hasNext()) {
                downloadWrapper = (e) i$.next();
                if (downloadWrapper.f() == f.c.TASK_PAUSED && downloadWrapper.j().a(taskProperty)) {
                    pauseTask.add(downloadWrapper);
                }
            }
        }
        return pauseTask;
    }

    public boolean f(String packageName) {
        Iterator i$ = g(1, 0, 3).iterator();
        while (i$.hasNext()) {
            if (((e) i$.next()).g().equals(packageName)) {
                return true;
            }
        }
        i$ = i(1).iterator();
        while (i$.hasNext()) {
            if (((e) i$.next()).g().equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    public boolean g(String packageName) {
        Iterator i$ = this.k.iterator();
        while (i$.hasNext()) {
            e wrapper = (e) i$.next();
            if (wrapper.g().equals(packageName) && f.c(wrapper.f())) {
                return true;
            }
        }
        return false;
    }

    public e h(String packageName) {
        for (e wrapper : l()) {
            if (wrapper.g().equals(packageName) && f.c(wrapper.f())) {
                return wrapper;
            }
        }
        return null;
    }

    private void a(e wrapper, DownloadTaskInfo downloadTaskInfo) {
        if (wrapper.z()) {
            b(wrapper, downloadTaskInfo);
        } else if (!wrapper.F()) {
            j(wrapper);
        } else if (x.d(this.c).a(wrapper.g(), wrapper.G()) == com.meizu.cloud.app.core.c.DOWNGRADE) {
            this.f.a(wrapper.g(), this.o);
        } else {
            j(wrapper);
        }
    }

    private void j(e wrapper) {
        if (wrapper.j().b() || com.meizu.cloud.app.settings.a.a(this.c).d()) {
            this.f.a(wrapper, this.o);
        } else {
            e(wrapper);
        }
    }

    private void b(e wrapper, DownloadTaskInfo taskInfo) {
        if (wrapper.z()) {
            wrapper.a(h.PATCHING, taskInfo);
            a(null, wrapper);
            final String oldApkPath = wrapper.B();
            final String patchPath = wrapper.n();
            final String newApkPath = wrapper.n().replace(".patch", "");
            if (!TextUtils.isEmpty(oldApkPath) && !TextUtils.isEmpty(patchPath) && !TextUtils.isEmpty(newApkPath)) {
                final e eVar = wrapper;
                final DownloadTaskInfo downloadTaskInfo = taskInfo;
                new Thread(new Runnable(this) {
                    final /* synthetic */ d f;

                    public void run() {
                        Throwable e;
                        try {
                            if (Bspatch.a(oldApkPath, newApkPath, patchPath) == 0) {
                                eVar.a(h.PATCHED_SUCCESS, downloadTaskInfo);
                                this.f.a(null, eVar);
                                byte[] md5 = e.b(newApkPath);
                                String composedMD5 = md5 != null ? e.a(md5) : "";
                                if (TextUtils.isEmpty(eVar.C()) || TextUtils.isEmpty(composedMD5) || !eVar.C().equals(composedMD5)) {
                                    eVar.a(-1000);
                                    eVar.a(h.PATCHED_FAILURE, downloadTaskInfo);
                                    this.f.a(null, eVar);
                                    return;
                                }
                                eVar.b(newApkPath);
                                this.f.j(eVar);
                                return;
                            }
                            eVar.a(-1000);
                            eVar.a(h.PATCHED_FAILURE, downloadTaskInfo);
                            this.f.a(null, eVar);
                        } catch (Exception e2) {
                            e = e2;
                            e.printStackTrace();
                            this.f.c(eVar, downloadTaskInfo);
                        } catch (NoClassDefFoundError e3) {
                            e = e3;
                            e.printStackTrace();
                            this.f.c(eVar, downloadTaskInfo);
                        }
                    }
                }).start();
            }
        }
    }

    private void c(e wrapper, DownloadTaskInfo taskInfo) {
        wrapper.a(-1000);
        wrapper.a(h.PATCHED_FAILURE, taskInfo);
        a(null, wrapper);
    }

    private void a(e appInfo, boolean force) {
        this.f.a(appInfo.n(), force);
        if (appInfo.n().endsWith(".patch")) {
            this.f.a(appInfo.n().replace(".patch", ""), force);
        } else {
            this.f.a(appInfo.n() + ".patch", force);
        }
    }

    private Set<e> l() {
        Set<e> tempSet = new HashSet();
        tempSet.addAll(this.j);
        tempSet.addAll(this.i);
        tempSet.addAll(this.l);
        tempSet.addAll(this.k);
        return tempSet;
    }

    public synchronized ArrayList<e> g(int... taskPropertys) {
        ArrayList<e> applist;
        applist = new ArrayList();
        for (int taskProperty : taskPropertys) {
            Iterator i$ = this.j.iterator();
            while (i$.hasNext()) {
                e downloadWrapper = (e) i$.next();
                if (downloadWrapper.j().a(taskProperty) && !f.c(downloadWrapper.f())) {
                    applist.add(downloadWrapper);
                }
            }
            i$ = this.i.iterator();
            while (i$.hasNext()) {
                downloadWrapper = (e) i$.next();
                if (downloadWrapper.j().a(taskProperty) && !f.c(downloadWrapper.f())) {
                    applist.add(downloadWrapper);
                }
            }
        }
        return applist;
    }

    public synchronized ArrayList<e> h(int... taskPropertys) {
        ArrayList<e> applist;
        applist = new ArrayList();
        for (int taskProperty : taskPropertys) {
            Iterator i$ = this.j.iterator();
            while (i$.hasNext()) {
                e downloadWrapper = (e) i$.next();
                if (downloadWrapper.j().a(taskProperty) && !f.c(downloadWrapper.f()) && f.d(downloadWrapper.f())) {
                    applist.add(downloadWrapper);
                }
            }
            i$ = this.i.iterator();
            while (i$.hasNext()) {
                downloadWrapper = (e) i$.next();
                if (downloadWrapper.j().a(taskProperty) && !f.c(downloadWrapper.f()) && f.d(downloadWrapper.f())) {
                    applist.add(downloadWrapper);
                }
            }
        }
        return applist;
    }

    public synchronized ArrayList<e> i(int... taskPropertys) {
        ArrayList<e> applist;
        applist = new ArrayList();
        for (int taskProperty : taskPropertys) {
            Iterator i$ = this.i.iterator();
            while (i$.hasNext()) {
                e downloadWrapper = (e) i$.next();
                if (downloadWrapper.j().a(taskProperty) && !f.c(downloadWrapper.f())) {
                    applist.add(downloadWrapper);
                }
            }
        }
        return applist;
    }

    public synchronized ArrayList<e> j(int... taskPropertys) {
        ArrayList<e> applist;
        applist = new ArrayList();
        for (int taskProperty : taskPropertys) {
            Iterator i$ = this.j.iterator();
            while (i$.hasNext()) {
                e downloadWrapper = (e) i$.next();
                if (downloadWrapper.j().a(taskProperty) && !f.d(downloadWrapper.f())) {
                    applist.add(downloadWrapper);
                }
            }
        }
        return applist;
    }

    public synchronized void c(AppStructItem appStructItem, g taskProperty) {
        if (a.b(appStructItem.package_name, appStructItem.version_code)) {
            e downloadWrapper;
            if (taskProperty != null) {
                downloadWrapper = new e(appStructItem, taskProperty);
            } else {
                downloadWrapper = new e(appStructItem);
            }
            downloadWrapper.a(f.INSTALL_START, null);
            a(null, downloadWrapper);
        }
    }

    public void a(final e wrapper) {
        wrapper.a(f.DELETE_START, null);
        a(null, wrapper);
        this.f.a(wrapper.g(), new c(this) {
            final /* synthetic */ d b;

            public void a(e downloadWrapper, int returnCode) {
            }

            public void a(String packageName, int returnCode) {
                if (!wrapper.g().equals(packageName)) {
                    return;
                }
                if (returnCode != 1) {
                    wrapper.a(returnCode);
                    wrapper.a(f.DELETE_FAILURE, null);
                    this.b.a(null, wrapper);
                    return;
                }
                this.b.j(wrapper);
            }
        });
    }

    public synchronized void i(String packageName) {
        this.f.a(packageName, this.o);
    }

    private synchronized void k(e wrapper) {
        if (!wrapper.j().d()) {
            Iterator i$ = this.m.iterator();
            while (i$.hasNext()) {
                Pair<m, g> stateEvent = (Pair) i$.next();
                if ((stateEvent.first instanceof com.meizu.cloud.app.downlad.f.d) && (wrapper.j().a((g) stateEvent.second) || a((g) stateEvent.second, wrapper))) {
                    ((com.meizu.cloud.app.downlad.f.d) stateEvent.first).onFetchStateChange(wrapper);
                }
            }
        }
    }

    private synchronized void l(e wrapper) {
        if (!wrapper.j().d()) {
            Iterator i$ = this.m.iterator();
            while (i$.hasNext()) {
                Pair<m, g> stateEvent = (Pair) i$.next();
                if ((stateEvent.first instanceof f.i) && (wrapper.j().a((g) stateEvent.second) || a((g) stateEvent.second, wrapper))) {
                    ((f.i) stateEvent.first).a(wrapper);
                }
            }
        }
    }

    private synchronized void m(e wrapper) {
        if (!wrapper.j().d()) {
            Iterator i$ = this.m.iterator();
            while (i$.hasNext()) {
                Pair<m, g> stateEvent = (Pair) i$.next();
                if ((stateEvent.first instanceof g) && (wrapper.j().a((g) stateEvent.second) || a((g) stateEvent.second, wrapper))) {
                    ((g) stateEvent.first).b(wrapper);
                }
            }
        }
    }

    private synchronized void n(e wrapper) {
        if (!wrapper.j().d()) {
            Iterator i$ = this.m.iterator();
            while (i$.hasNext()) {
                Pair<m, g> stateEvent = (Pair) i$.next();
                if ((stateEvent.first instanceof f.b) && (wrapper.j().a((g) stateEvent.second) || a((g) stateEvent.second, wrapper))) {
                    ((f.b) stateEvent.first).onDownloadStateChanged(wrapper);
                }
            }
        }
    }

    private synchronized void o(e wrapper) {
        if (!wrapper.j().d()) {
            Iterator i$ = this.m.iterator();
            while (i$.hasNext()) {
                Pair<m, g> stateEvent = (Pair) i$.next();
                if ((stateEvent.first instanceof f.b) && (wrapper.j().a((g) stateEvent.second) || a((g) stateEvent.second, wrapper))) {
                    ((f.b) stateEvent.first).onDownloadProgress(wrapper);
                }
            }
        }
    }

    private synchronized void p(e wrapper) {
        if (!wrapper.j().d() || wrapper.f() == f.INSTALL_SUCCESS) {
            Iterator i$ = this.m.iterator();
            while (i$.hasNext()) {
                Pair<m, g> stateEvent = (Pair) i$.next();
                if ((stateEvent.first instanceof f.e) && (wrapper.j().a((g) stateEvent.second) || a((g) stateEvent.second, wrapper))) {
                    ((f.e) stateEvent.first).onInstallStateChange(wrapper);
                }
            }
        }
    }

    private boolean a(g taskProperty, e wrapper) {
        for (int property : wrapper.j().a()) {
            if (taskProperty.a(property, wrapper.j().f(), wrapper.f())) {
                return true;
            }
        }
        return false;
    }

    public synchronized void i() {
        Iterator i$ = this.j.iterator();
        while (i$.hasNext()) {
            e downloadWrapper = (e) i$.next();
            if (downloadWrapper.P()) {
                this.e.a(downloadWrapper);
            }
        }
        j();
    }

    public synchronized void j(String pkgName) {
        Iterator i$ = this.j.iterator();
        while (i$.hasNext()) {
            e wrapper = (e) i$.next();
            if (wrapper.f() != f.c.TASK_COMPLETED && pkgName.equals(pkgName) && wrapper.q() != -1) {
                this.e.a(wrapper.q());
                break;
            }
        }
    }

    public synchronized void k(int... taskPropertys) {
        for (int taskProperty : taskPropertys) {
            Iterator i$ = this.j.iterator();
            while (i$.hasNext()) {
                e downloadWrapper = (e) i$.next();
                if (downloadWrapper.j().a(taskProperty) && downloadWrapper.f() == f.c.TASK_PAUSED) {
                    this.e.a(downloadWrapper.q());
                }
            }
        }
    }

    public synchronized void k(String pkgName) {
        Iterator i$ = this.j.iterator();
        while (i$.hasNext()) {
            e wrapper = (e) i$.next();
            if (wrapper.g().equals(pkgName)) {
                this.e.b(wrapper.q());
                break;
            }
        }
    }

    public synchronized void a(int taskProperty) {
        this.e.b();
    }

    private void q(final e wrapper) {
        this.p.execute(new Runnable(this) {
            final /* synthetic */ d b;

            public void run() {
                this.b.g.a(wrapper.g(), e.c(wrapper));
            }
        });
    }

    private void r(final e wrapper) {
        this.p.execute(new Runnable(this) {
            final /* synthetic */ d b;

            public void run() {
                this.b.g.a(wrapper.g());
            }
        });
    }

    public void j() {
        this.p.execute(new Runnable(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void run() {
                List<e> items = this.a.g.a(e.class);
                if (items != null) {
                    for (int i = 0; i < items.size(); i++) {
                        e item = (e) items.get(i);
                        item.d(true);
                        if (i.f(this.a.c, item.g()) == (item.F() ? item.G() : item.h())) {
                            this.a.g.a(item.g());
                        } else {
                            if (com.meizu.cloud.app.utils.m.a(this.a.c)) {
                                item.e(true);
                                item.a(t.a(this.a.c, item.g()), null);
                            } else {
                                item.e(false);
                                item.a(f.c.TASK_PAUSED, null);
                            }
                            if (item.A()) {
                                item.e = item.f.getPatchItem(this.a.c);
                            }
                            this.a.a(null, item);
                        }
                    }
                }
            }
        });
    }
}

package com.meizu.cloud.download.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.meizu.cloud.download.c.g.c;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DownloadService extends Service {
    private static f a;
    private static ArrayList<Runnable> b = new ArrayList();
    private static Object d = new Object();
    private static Object e = new Object();
    private c c;
    private com.meizu.cloud.download.service.f.a f = null;

    private class a extends com.meizu.cloud.download.service.f.a {
        final /* synthetic */ DownloadService a;
        private WeakReference<DownloadService> b;

        public a(DownloadService downloadService, DownloadService service) {
            this.a = downloadService;
            this.b = new WeakReference(service);
        }

        public long a(DownloadTaskInfo downloadTaskInfo) throws RemoteException {
            return ((DownloadService) this.b.get()).a(downloadTaskInfo);
        }

        public void a() throws RemoteException {
            ((DownloadService) this.b.get()).f();
        }

        public void a(long taskId) {
            ((DownloadService) this.b.get()).a(taskId);
        }

        public void b() {
            ((DownloadService) this.b.get()).g();
        }

        public void b(long taskId) throws RemoteException {
            ((DownloadService) this.b.get()).b(taskId);
        }

        public void c() throws RemoteException {
            ((DownloadService) this.b.get()).h();
        }

        public void c(long taskId) throws RemoteException {
            ((DownloadService) this.b.get()).c(taskId);
        }

        public void d() throws RemoteException {
            ((DownloadService) this.b.get()).i();
        }

        public void a(long taskId, boolean bDelete) throws RemoteException {
            ((DownloadService) this.b.get()).a(taskId, bDelete);
        }

        public void a(boolean bDelete) throws RemoteException {
            ((DownloadService) this.b.get()).a(bDelete);
        }

        public void a(g listener) throws RemoteException {
            this.a.c.a(listener);
        }

        public void b(g listener) throws RemoteException {
            this.a.c.b(listener);
        }

        public int e() throws RemoteException {
            return ((DownloadService) this.b.get()).j();
        }

        public int f() throws RemoteException {
            return ((DownloadService) this.b.get()).k();
        }

        public List<DownloadTaskInfo> g() throws RemoteException {
            return ((DownloadService) this.b.get()).l();
        }

        public List<DownloadTaskInfo> h() throws RemoteException {
            return ((DownloadService) this.b.get()).m();
        }

        public void a(int limit) {
            ((DownloadService) this.b.get()).a(limit);
        }

        public void b(boolean enable) {
            ((DownloadService) this.b.get()).b(enable);
        }

        public boolean i() {
            return ((DownloadService) this.b.get()).n();
        }
    }

    public static f a(Runnable bindedRunnalbe) {
        f fVar;
        synchronized (d) {
            if (a == null && bindedRunnalbe != null) {
                synchronized (b) {
                    b.add(bindedRunnalbe);
                }
            }
            fVar = a;
        }
        return fVar;
    }

    public static void b(Runnable runnable) {
        synchronized (d) {
            if (a != null) {
                runnable.run();
            } else {
                synchronized (b) {
                    b.add(runnable);
                }
            }
        }
    }

    public static void e() {
        boolean needWait = false;
        synchronized (d) {
            if (a == null) {
                needWait = true;
            }
        }
        if (needWait) {
            synchronized (e) {
                try {
                    e.wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public static void a(Context context, Class<?> clazz) {
        synchronized (d) {
            if (a == null) {
                context.bindService(new Intent(context, clazz), new ServiceConnection() {
                    public void onServiceConnected(ComponentName className, IBinder iservice) {
                        synchronized (DownloadService.d) {
                            DownloadService.a = com.meizu.cloud.download.service.f.a.a(iservice);
                        }
                        synchronized (DownloadService.e) {
                            DownloadService.e.notifyAll();
                        }
                        synchronized (DownloadService.b) {
                            Iterator i$ = DownloadService.b.iterator();
                            while (i$.hasNext()) {
                                ((Runnable) i$.next()).run();
                            }
                            DownloadService.b.clear();
                        }
                    }

                    public void onServiceDisconnected(ComponentName className) {
                        synchronized (DownloadService.d) {
                            DownloadService.a = null;
                        }
                        synchronized (DownloadService.e) {
                            DownloadService.e.notifyAll();
                        }
                    }
                }, 1);
            }
        }
    }

    public long a(DownloadTaskInfo downloadTaskInfo) {
        return this.c.b(downloadTaskInfo);
    }

    public void f() {
        this.c.a();
    }

    public void a(long taskId) {
        this.c.b(taskId);
    }

    public void g() {
        this.c.c();
    }

    public void b(long taskId) {
        this.c.a(taskId);
    }

    public void h() {
        this.c.b();
    }

    public void a(long taskId, boolean bDelelte) {
        this.c.a(taskId, bDelelte);
    }

    public void a(boolean bDelelte) {
        this.c.a(bDelelte);
    }

    public void c(long taskId) {
        this.c.c(taskId);
    }

    public void i() {
        this.c.d();
    }

    public int j() {
        return this.c.e();
    }

    public int k() {
        return this.c.f();
    }

    public List<DownloadTaskInfo> l() {
        return this.c.g();
    }

    public List<DownloadTaskInfo> m() {
        return this.c.h();
    }

    public void a(int limit) {
        this.c.a(limit);
    }

    public void b(boolean enable) {
        this.c.b(enable);
    }

    public boolean n() {
        return this.c.j();
    }

    public IBinder onBind(Intent intent) {
        return this.f;
    }

    public void onCreate() {
        super.onCreate();
        this.c = d();
        this.f = new a(this, this);
    }

    protected c d() {
        return new c(this);
    }

    public void onDestroy() {
        this.c.i();
        this.c = null;
        this.f = null;
        super.onDestroy();
    }

    public String a(c jc, String url, Bundle outArgs) {
        return url;
    }

    public void a(long id, String url, String filePath) {
    }
}

package com.meizu.cloud.app.downlad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.storage.StorageVolume;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.downlad.f.c;
import com.meizu.cloud.app.downlad.f.l;
import com.meizu.cloud.app.utils.e;
import com.meizu.cloud.app.utils.k;
import com.meizu.cloud.app.utils.m;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.download.service.DownloadService;
import com.meizu.cloud.download.service.DownloadTaskInfo;
import com.meizu.cloud.download.service.f;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class a {
    private static final String a = a.class.getSimpleName();
    private static String b;
    private static String c;
    private static int d = 2;
    private Context e;
    private Handler f;
    private f g;
    private ExecutorService h = Executors.newSingleThreadExecutor();
    private boolean i = false;
    private com.meizu.cloud.download.service.g.a j = new com.meizu.cloud.download.service.g.a(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a(DownloadTaskInfo downloadTaskInfo) throws RemoteException {
            e wrapper = d.a(this.a.e).a(downloadTaskInfo.mId);
            if (wrapper != null) {
                l anEnum = null;
                if (downloadTaskInfo.h == 0) {
                    anEnum = c.TASK_CREATED;
                } else if (downloadTaskInfo.h == 1) {
                    anEnum = c.TASK_WAITING;
                } else if (downloadTaskInfo.h == 2) {
                    anEnum = c.TASK_STARTED;
                    wrapper.X();
                    wrapper.f(false);
                } else if (downloadTaskInfo.h == 5) {
                    anEnum = c.TASK_COMPLETED;
                } else if (downloadTaskInfo.h == 3) {
                    anEnum = c.TASK_PAUSED;
                    wrapper.f(true);
                }
                if (anEnum != null && downloadTaskInfo.h != 4 && downloadTaskInfo.h != 6) {
                    wrapper.a(anEnum, downloadTaskInfo);
                    d.a(this.a.e).a(null, wrapper);
                }
            }
        }

        public void b(DownloadTaskInfo downloadTaskInfo) throws RemoteException {
            e wrapper = d.a(this.a.e).a(downloadTaskInfo.mId);
            if (wrapper != null) {
                wrapper.a(c.TASK_STARTED, downloadTaskInfo);
                d.a(this.a.e).a(null, wrapper);
            }
        }

        public void c(DownloadTaskInfo downloadTaskInfo) throws RemoteException {
            e wrapper = d.a(this.a.e).a(downloadTaskInfo.mId);
            if (wrapper != null) {
                k.a(this.a.e, a.a, "onError:" + downloadTaskInfo.i);
                if (wrapper.j().a(1)) {
                    wrapper.J();
                }
                wrapper.a(downloadTaskInfo.i);
                wrapper.a(c.TASK_ERROR, downloadTaskInfo);
                d.a(this.a.e).a(null, wrapper);
                if (wrapper.j().a(0) && wrapper.j().c()) {
                    com.meizu.cloud.app.update.exclude.a.a(this.a.e).a(wrapper.g(), wrapper.h());
                }
            }
        }
    };
    private boolean k = false;
    private Thread l;
    private final BroadcastReceiver m = new BroadcastReceiver(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                this.a.f.post(new Runnable(this) {
                    final /* synthetic */ AnonymousClass5 a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        this.a.a.h();
                    }
                });
            } else if ("android.intent.action.MEDIA_MOUNTED".equals(intent.getAction())) {
                a.c(context);
            } else if ("android.intent.action.MEDIA_UNMOUNTED".equals(intent.getAction()) || "android.intent.action.MEDIA_EJECT".equals(intent.getAction())) {
                a.c(context);
                d.a(this.a.e).c();
            }
        }
    };

    public a(final Context context) {
        this.e = context.getApplicationContext();
        c(context);
        this.f = new Handler(Looper.getMainLooper());
        this.e.registerReceiver(this.m, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.MEDIA_MOUNTED");
        intentFilter2.addAction("android.intent.action.MEDIA_UNMOUNTED");
        intentFilter2.addAction("android.intent.action.MEDIA_EJECT");
        intentFilter2.addDataScheme("file");
        this.e.registerReceiver(this.m, intentFilter2);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addDataScheme("package");
        intentFilter3.addAction("android.intent.action.PACKAGE_REMOVED");
        this.e.registerReceiver(this.m, intentFilter3);
        this.g = DownloadService.a(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                this.b.g = DownloadService.a(null);
                if (this.b.g != null) {
                    this.b.f();
                    d.a(context).i();
                    return;
                }
                Log.w(a.a, "DownloadService is null:  mDownloadService == null");
            }
        });
        f();
        g();
    }

    private static void c(Context context) {
        boolean isAppCenter = x.a(context);
        Pair<String, String> storageDevicePath = d(context);
        String path = "/Download/" + (isAppCenter ? "AppCenter" : "GameCenter") + "/Apk/";
        b = ((String) storageDevicePath.second) + path;
        c = ((String) storageDevicePath.first) + path;
    }

    private static Pair<String, String> d(Context context) {
        StorageVolume[] svList = e.a();
        if (svList != null) {
            for (StorageVolume volume : svList) {
                if (!(Environment.getExternalStorageDirectory().getPath().equals(volume.getPath()) || TextUtils.isEmpty(volume.getPath()) || !"mounted".equals(volume.getState()))) {
                    String name = e.a(context, volume);
                    File file = new File(volume.getPath());
                    if (file.isDirectory() && file.getFreeSpace() > 2147483648L) {
                        return new Pair(name, file.getPath());
                    }
                }
            }
        }
        return new Pair(context.getString(i.sdcard_name), Environment.getExternalStorageDirectory().getPath());
    }

    private void f() {
        if (this.g != null) {
            try {
                this.g.a(d);
                this.g.a(this.j);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static String a(String packageName, int versionCode) {
        return b + packageName + "_" + versionCode + ".apk";
    }

    public static boolean b(String packageName, int versionCode) {
        File file = new File(a(packageName, versionCode));
        if (file.exists() && file.isFile()) {
            return true;
        }
        return false;
    }

    public static String a(Context context) {
        if (TextUtils.isEmpty(c)) {
            c(context);
        }
        return c;
    }

    private void g() {
        new Thread(new Runnable(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void run() {
                String CLEAN_TIME_KEY = "clean_time";
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.a.e);
                if (System.currentTimeMillis() - sharedPreferences.getLong("clean_time", 0) > 604800000) {
                    File file = new File(a.b);
                    if (file.exists() && file.isDirectory() && file.listFiles() != null) {
                        Log.i(a.a, "Clean the temp apks: " + file.listFiles().length);
                        for (File file2 : file.listFiles()) {
                            if (file2.isFile() && System.currentTimeMillis() - file2.lastModified() > 172800000) {
                                if (file2.getAbsolutePath().toLowerCase().endsWith(".dat")) {
                                    file2.delete();
                                } else if (System.currentTimeMillis() - file2.lastModified() > 1209600000 && com.meizu.cloud.app.settings.a.a(this.a.e).e()) {
                                    String name = file2.getName();
                                    int pos;
                                    try {
                                        pos = name.lastIndexOf("_");
                                        int pos2 = name.lastIndexOf(".");
                                        if (pos > 0 && pos2 > 0 && pos2 > pos) {
                                            String packageName = name.substring(0, pos);
                                            if (Integer.valueOf(name.substring(pos + 1, pos2)).intValue() <= com.meizu.cloud.app.core.i.f(this.a.e, packageName) || com.meizu.cloud.app.core.i.a(this.a.e, packageName) == null) {
                                                file2.delete();
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        file2.delete();
                                        pos = name.lastIndexOf("_");
                                        if (pos > 0) {
                                            name.substring(0, pos);
                                        }
                                    }
                                }
                            }
                        }
                        sharedPreferences.edit().putLong("clean_time", System.currentTimeMillis()).apply();
                    }
                }
            }
        }).start();
    }

    private boolean d(long id) throws RemoteException {
        if (id == -1) {
            return false;
        }
        for (DownloadTaskInfo downloadTaskInfo : this.g.h()) {
            if (downloadTaskInfo.mId == id) {
                return true;
            }
        }
        return false;
    }

    private boolean e(long id) throws RemoteException {
        if (id == -1) {
            return false;
        }
        for (DownloadTaskInfo downloadTaskInfo : this.g.h()) {
            if (downloadTaskInfo.mId == id && (downloadTaskInfo.i == 7 || downloadTaskInfo.i == 1 || downloadTaskInfo.i == 2)) {
                return true;
            }
        }
        return false;
    }

    public long a(e wrapper) {
        if (this.g != null) {
            try {
                if (d(wrapper.q())) {
                    k.a(this.e, a, "start:" + wrapper.g() + ",DOWNLOAD_EXIST_ERROR");
                    return -2;
                } else if (e(wrapper.q())) {
                    id = wrapper.q();
                    this.g.a(id);
                    k.a(this.e, a, "resume start:" + wrapper.g());
                    return id;
                } else {
                    DownloadTaskInfo taskInfo = new DownloadTaskInfo();
                    taskInfo.b = wrapper.b();
                    taskInfo.d = wrapper.p();
                    if (wrapper.c() != null) {
                        taskInfo.a(wrapper.c().digest, wrapper.c().verify_mode, wrapper.c().package_name, wrapper.c().size, wrapper.c().version_code, wrapper.c().getAllDownloadUrlEx());
                        if (wrapper.m() != null) {
                            wrapper.m().setCheckInfo(wrapper.c().digest, wrapper.c().verify_mode, wrapper.c().package_name, wrapper.c().size, wrapper.c().version_code, wrapper.c().getAllDownloadUrlEx());
                        }
                    } else {
                        k.a(this.e, a, wrapper.g() + " : CheckInfo is null!");
                    }
                    if (wrapper.z()) {
                        taskInfo.c = a(wrapper.g(), wrapper.h()) + ".patch";
                    } else {
                        taskInfo.c = a(wrapper.g(), wrapper.F() ? wrapper.G() : wrapper.h());
                    }
                    wrapper.b(taskInfo.c);
                    taskInfo.h = 0;
                    id = this.g.a(taskInfo);
                    if (id >= 0) {
                        taskInfo.mId = id;
                        wrapper.a(c.TASK_CREATED, taskInfo);
                        d.a(this.e).a(null, wrapper);
                    }
                    wrapper.b(false);
                    return id;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            wrapper.b(true);
            return -1;
        }
    }

    public synchronized void a(long id) {
        if (!(this.g == null || id == -1)) {
            try {
                this.g.a(id);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void a() {
        if (this.g != null) {
            try {
                this.g.b();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void b(long id) {
        if (!(this.g == null || id == -1)) {
            try {
                this.g.b(id);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void b() {
        if (this.g != null) {
            try {
                this.g.c();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void c(long id) {
        if (!(this.g == null || id == -1)) {
            try {
                this.g.a(id, false);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized List<DownloadTaskInfo> c() {
        List<DownloadTaskInfo> taskInfos;
        taskInfos = new ArrayList();
        if (this.g != null) {
            try {
                taskInfos.addAll(this.g.g());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return taskInfos;
    }

    private void h() {
        if (!m.a(this.e)) {
            b();
            if (m.b(this.e)) {
                for (e downloadWrapper : d.a(this.e).f(0)) {
                    if (!downloadWrapper.j().g()) {
                        a(downloadWrapper.q());
                    }
                }
            }
        } else if (!m.a(this.e)) {
        } else {
            if (this.k) {
                a();
                d.a(this.e).a();
            } else if (this.l == null || !this.l.isAlive()) {
                this.l = new Thread(new Runnable(this) {
                    final /* synthetic */ a a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        boolean isConsistent = true;
                        try {
                            c downloadTaskDbHelper = new c(this.a.e);
                            List<e> downloadWrappers = downloadTaskDbHelper.a(e.class);
                            List<DownloadTaskInfo> taskInfos = this.a.g.h();
                            int compareCount = 0;
                            if (downloadWrappers.size() == taskInfos.size()) {
                                for (e downloadWrapper : downloadWrappers) {
                                    for (DownloadTaskInfo downloadTaskInfo : taskInfos) {
                                        if (downloadWrapper.g().equals(downloadTaskInfo.c())) {
                                            compareCount++;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!(compareCount == downloadWrappers.size() && compareCount == taskInfos.size())) {
                                isConsistent = false;
                            }
                            if (!isConsistent) {
                                downloadTaskDbHelper.b();
                                downloadTaskDbHelper.a();
                            } else if (compareCount != 0) {
                                this.a.a();
                                d.a(this.a.e).a();
                            }
                            this.a.k = true;
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
                this.l.start();
            }
        }
    }
}

package com.meizu.cloud.download.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.meizu.cloud.app.request.structitem.AppStructItem.Columns;
import com.meizu.cloud.download.app.NetworkStatusManager;
import com.meizu.cloud.download.c.d;
import com.meizu.cloud.download.c.e;
import com.meizu.cloud.download.c.g;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class c {
    private static final String b = DownloadTaskInfo.a.a();
    protected DownloadService a;
    private final SQLiteDatabase c;
    private List<DownloadTaskInfo> d;
    private b e;
    private int f = 2;
    private d g;
    private boolean h = false;
    private int i = 0;
    private int j = 0;
    private long[] k = new long[50];
    private long[] l = new long[50];
    private String[] m = new String[1];
    private ContentValues n = new ContentValues();
    private Handler o = new Handler();
    private boolean p = false;
    private Runnable q = new Runnable(this) {
        final /* synthetic */ c a;

        {
            this.a = r1;
        }

        public void run() {
            this.a.p = false;
            if (!NetworkStatusManager.a().a(this.a.h)) {
                this.a.b();
            }
        }
    };
    private NetworkStatusManager.a r = new NetworkStatusManager.a(this) {
        final /* synthetic */ c a;

        {
            this.a = r1;
        }

        public void a(int networkType) {
            this.a.o.removeCallbacks(this.a.q);
            this.a.p = true;
            this.a.o.postDelayed(this.a.q, 6000);
        }
    };
    private com.meizu.cloud.download.service.e.a s = new com.meizu.cloud.download.service.e.a(this) {
        final /* synthetic */ c a;

        {
            this.a = r1;
        }

        public void a(DownloadTaskInfo task, int state) {
            this.a.a(task, state);
        }

        public String a(com.meizu.cloud.download.c.g.c jc, String url, Bundle outArgs) {
            return this.a.a.a(jc, url, outArgs);
        }

        public void a(String msg) {
            this.a.a(msg);
        }

        public void a(long id, String key, String value) {
            this.a.a(id, key, value);
        }

        public void a(long id, String key, long value) {
            this.a.a(id, key, value);
        }

        public boolean a() {
            return this.a.p;
        }

        public void a(DownloadTaskInfo task) {
            this.a.g.b(task);
        }

        public void a(DownloadTaskInfo task, boolean bDelete) {
            this.a.b(task, bDelete);
        }

        public void a(long id, long fileSize) {
            this.a.a(id, fileSize);
        }

        public void b(DownloadTaskInfo downloadTaskInfo) {
            this.a.g.c(downloadTaskInfo);
        }

        public void a(DownloadTaskInfo downloadTaskInfo, b resource) {
            DownloadTaskInfo.a.a(this.a.c, downloadTaskInfo.mId);
            this.a.h().remove(downloadTaskInfo);
            this.a.a.a(downloadTaskInfo.mId, downloadTaskInfo.b, resource.a);
            this.a.a(downloadTaskInfo, 5);
        }
    };

    private static final class a extends SQLiteOpenHelper {
        public a(Context context) {
            super(context, "download_task.db", null, 4);
        }

        public void onCreate(SQLiteDatabase db) {
            DownloadTaskInfo.a.b(db);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion <= 1) {
                db.execSQL("ALTER TABLE " + c.b + " ADD COLUMN " + "downloaded_size" + " INTEGER;");
            }
            if (oldVersion <= 2) {
                db.execSQL("ALTER TABLE " + c.b + " ADD COLUMN " + "temp_file" + " TEXT;");
            }
            if (oldVersion <= 3) {
                db.execSQL("ALTER TABLE " + c.b + " ADD COLUMN " + Columns.CHECK_VERIFY_MODE + " INTEGER;");
                db.execSQL("ALTER TABLE " + c.b + " ADD COLUMN " + Columns.CHECK_PACKAGENAME + " TEXT;");
                db.execSQL("ALTER TABLE " + c.b + " ADD COLUMN " + Columns.CHECK_SIZE + " INTEGER;");
                db.execSQL("ALTER TABLE " + c.b + " ADD COLUMN " + Columns.CHECK_DIGEST + " TEXT;");
                db.execSQL("ALTER TABLE " + c.b + " ADD COLUMN " + Columns.CHECK_VERSIONCODE + " INTEGER;");
                db.execSQL("ALTER TABLE " + c.b + " ADD COLUMN " + Columns.CHECK_URL + " TEXT;");
            }
        }
    }

    public class b implements com.meizu.cloud.download.a.a.a<b> {
        final /* synthetic */ c a;
        private e b;

        public b(c cVar, int limit) {
            this.a = cVar;
            this.b = new e(new g(), limit);
        }

        public com.meizu.cloud.download.c.c<b> a(com.meizu.cloud.download.c.g.b<b> job, d<b> listener) {
            return this.b.a(job, listener);
        }

        public void a(Runnable runnable) {
            runnable.run();
        }
    }

    public c(DownloadService service) {
        this.a = service;
        this.c = new a(service).getWritableDatabase();
        this.g = new d();
        NetworkStatusManager.a().a(this.r);
    }

    public long b(DownloadTaskInfo downloadTaskInfo) {
        if (downloadTaskInfo.b == null || downloadTaskInfo.c == null) {
            return -1;
        }
        DownloadTaskInfo task = a(h(), downloadTaskInfo);
        if (task == null) {
            task = c(downloadTaskInfo);
            h().add(task);
        }
        d(task);
        downloadTaskInfo.mId = task.mId;
        return task.mId;
    }

    public void a() {
        for (DownloadTaskInfo task : h()) {
            d(task);
        }
    }

    public void a(long taskId) {
        DownloadTaskInfo task = a(h(), taskId);
        if (task != null) {
            e(task);
        }
    }

    public void b() {
        g.a().a(new com.meizu.cloud.download.c.g.b<Void>(this) {
            final /* synthetic */ c a;

            {
                this.a = r1;
            }

            public /* synthetic */ Object b(com.meizu.cloud.download.c.g.c cVar) {
                return a(cVar);
            }

            public Void a(com.meizu.cloud.download.c.g.c jc) {
                List<DownloadTaskInfo> tasks = this.a.h();
                if (tasks != null) {
                    for (DownloadTaskInfo task : tasks) {
                        if (task.h != 2) {
                            synchronized (task) {
                                if (task.h == 7) {
                                } else {
                                    if (task.l != null) {
                                        task.l.c();
                                    }
                                    if (!(task.h == 4 || task.h == 5 || task.h == 6)) {
                                        this.a.a(task, 3);
                                    }
                                }
                            }
                        }
                    }
                    for (DownloadTaskInfo task2 : tasks) {
                        if (task2.h == 2) {
                            synchronized (task2) {
                                if (task2.l != null) {
                                    task2.l.c();
                                }
                            }
                            if (!(task2.h == 4 || task2.h == 5 || task2.h == 6)) {
                                this.a.a(task2, 3);
                            }
                        }
                    }
                }
                return null;
            }
        });
    }

    public void b(long taskId) {
        DownloadTaskInfo task = a(h(), taskId);
        if (task != null) {
            d(task);
        }
    }

    public void c() {
        g.a().a(new com.meizu.cloud.download.c.g.b<Void>(this) {
            final /* synthetic */ c a;

            {
                this.a = r1;
            }

            public /* synthetic */ Object b(com.meizu.cloud.download.c.g.c cVar) {
                return a(cVar);
            }

            public Void a(com.meizu.cloud.download.c.g.c jc) {
                List<DownloadTaskInfo> tasks = this.a.h();
                if (tasks != null) {
                    List<DownloadTaskInfo> tasksLeft = new ArrayList();
                    for (DownloadTaskInfo task : tasks) {
                        synchronized (task) {
                            if (task.l == null) {
                                if (this.a.e == null) {
                                    this.a.e = new b(this.a, this.a.f);
                                }
                                task.l = this.a.a(this.a.s, this.a.e, task);
                                this.a.a(task, 1);
                            }
                            if (task.d > 0) {
                                task.l.b();
                            } else {
                                tasksLeft.add(task);
                            }
                        }
                    }
                    for (DownloadTaskInfo task2 : tasksLeft) {
                        synchronized (task2) {
                            if (task2.l == null) {
                                if (this.a.e == null) {
                                    this.a.e = new b(this.a, this.a.f);
                                }
                                task2.l = this.a.a(this.a.s, this.a.e, task2);
                                this.a.a(task2, 1);
                            }
                            task2.l.b();
                        }
                    }
                }
                return null;
            }
        });
    }

    public void c(long taskId) {
        a(taskId, true);
    }

    public void a(long taskId, boolean bDelete) {
        DownloadTaskInfo task = a(h(), taskId);
        if (task != null) {
            a(task, bDelete);
            h().remove(task);
        }
    }

    public void d() {
        a(true);
    }

    public void a(boolean bDelete) {
        for (DownloadTaskInfo task : h()) {
            a(task, bDelete);
        }
        h().clear();
    }

    public void a(g listener) {
        this.g.a(listener);
    }

    public void b(g listener) {
        this.g.b(listener);
    }

    public int e() {
        int i = 0;
        for (DownloadTaskInfo task : h()) {
            if (task.h == 2) {
                i++;
            }
        }
        return i;
    }

    public int f() {
        return h().size();
    }

    public List<DownloadTaskInfo> g() {
        List<DownloadTaskInfo> tasks = h();
        List<DownloadTaskInfo> started = new ArrayList();
        for (DownloadTaskInfo task : tasks) {
            if (task.h == 2) {
                started.add(task);
            }
        }
        return started;
    }

    public List<DownloadTaskInfo> h() {
        if (this.d == null) {
            this.d = DownloadTaskInfo.a.a(this.c, DownloadTaskInfo.o);
            for (DownloadTaskInfo task : this.d) {
                int state = task.h;
                if (!(state == 6 || state == 5 || state == 4)) {
                    task.h = 3;
                }
            }
        }
        return this.d;
    }

    public void a(int limit) {
        if (this.e == null) {
            if (limit < 1) {
                limit = 1;
                Log.w("DownloadServiceImpl", "TaskLimit should not be less than 1");
            } else if (limit > 6) {
                limit = 6;
                Log.w("DownloadServiceImpl", "TaskLimit should not be greater than 6");
            }
            this.f = limit;
            return;
        }
        Log.w("DownloadServiceImpl", "JobExecutor has already been created");
    }

    public void i() {
        this.g.a();
        this.g = null;
        DownloadTaskInfo.a.a(this.c, this.d, true);
        NetworkStatusManager.a().b(this.r);
    }

    public DownloadTaskInfo c(DownloadTaskInfo downloadTaskInfo) {
        com.meizu.cloud.download.c.a task = new DownloadTaskInfo(downloadTaskInfo.b, downloadTaskInfo.c);
        task.h = 0;
        task.j = downloadTaskInfo.j;
        task.d = downloadTaskInfo.d;
        if (!TextUtils.isEmpty(downloadTaskInfo.a())) {
            task.a(downloadTaskInfo);
        }
        DownloadTaskInfo.a.a(this.c, task);
        return task;
    }

    public void d(final DownloadTaskInfo task) {
        g.a().a(new com.meizu.cloud.download.c.g.b<Void>(this) {
            final /* synthetic */ c b;

            public /* synthetic */ Object b(com.meizu.cloud.download.c.g.c cVar) {
                return a(cVar);
            }

            public Void a(com.meizu.cloud.download.c.g.c jc) {
                synchronized (task) {
                    if (task.h == 7) {
                    } else {
                        if (task.l == null) {
                            if (this.b.e == null) {
                                this.b.e = new b(this.b, this.b.f);
                            }
                            task.i = 0;
                            task.l = this.b.a(this.b.s, this.b.e, task);
                            this.b.a(task, 1);
                        }
                        task.l.b();
                    }
                }
                return null;
            }
        });
    }

    public void e(final DownloadTaskInfo task) {
        g.a().a(new com.meizu.cloud.download.c.g.b<Void>(this) {
            final /* synthetic */ c b;

            public /* synthetic */ Object b(com.meizu.cloud.download.c.g.c cVar) {
                return a(cVar);
            }

            public Void a(com.meizu.cloud.download.c.g.c jc) {
                synchronized (task) {
                    if (task.h == 7) {
                    } else {
                        if (task.l != null) {
                            task.l.c();
                        }
                        if (!(task.h == 4 || task.h == 5 || task.h == 6)) {
                            this.b.a(task, 3);
                        }
                    }
                }
                return null;
            }
        });
    }

    public void a(final DownloadTaskInfo task, final boolean bDelete) {
        g.a().a(new com.meizu.cloud.download.c.g.b<Void>(this) {
            final /* synthetic */ c c;

            public /* synthetic */ Object b(com.meizu.cloud.download.c.g.c cVar) {
                return a(cVar);
            }

            public Void a(com.meizu.cloud.download.c.g.c jc) {
                synchronized (task) {
                    task.m = true;
                    task.n = bDelete;
                    if (task.l != null) {
                        task.l.d();
                        task.l = null;
                        this.c.b(task, bDelete);
                    } else {
                        this.c.b(task, bDelete);
                    }
                }
                DownloadTaskInfo.a.a(this.c.c, task.mId);
                return null;
            }
        });
    }

    private void b(DownloadTaskInfo task, boolean bDelete) {
        a(task, 6);
        if (!TextUtils.isEmpty(task.k) && bDelete) {
            File tmpFile = new File(task.k);
            if (tmpFile.exists()) {
                tmpFile.delete();
            }
        }
    }

    public DownloadTaskInfo a(List<DownloadTaskInfo> tasks, long id) {
        for (DownloadTaskInfo task : tasks) {
            if (task.mId == id) {
                return task;
            }
        }
        return null;
    }

    public DownloadTaskInfo a(List<DownloadTaskInfo> tasks, DownloadTaskInfo newTask) {
        for (DownloadTaskInfo task : tasks) {
            if (newTask.b.equals(task.b) && newTask.c.equals(task.c)) {
                return task;
            }
        }
        return null;
    }

    private void a(long id, String key, long value) {
        ContentValues values = new ContentValues(1);
        values.put(key, Long.valueOf(value));
        this.c.update(b, values, "_id = ?", new String[]{String.valueOf(id)});
    }

    private void a(long id, String key, String value) {
        ContentValues values = new ContentValues(1);
        values.put(key, value);
        this.c.update(b, values, "_id = ?", new String[]{String.valueOf(id)});
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(com.meizu.cloud.download.service.DownloadTaskInfo r7, int r8) {
        /*
        r6 = this;
        monitor-enter(r7);
        r0 = r7.h;	 Catch:{ all -> 0x002a }
        if (r0 == r8) goto L_0x0028;
    L_0x0005:
        r0 = r7.h;	 Catch:{ all -> 0x002a }
        r1 = 6;
        if (r0 != r1) goto L_0x000c;
    L_0x000a:
        monitor-exit(r7);	 Catch:{ all -> 0x002a }
    L_0x000b:
        return;
    L_0x000c:
        r7.h = r8;	 Catch:{ all -> 0x002a }
        r0 = 4;
        if (r8 == r0) goto L_0x0014;
    L_0x0011:
        r0 = 3;
        if (r8 != r0) goto L_0x001a;
    L_0x0014:
        r6.l();	 Catch:{ all -> 0x002a }
        r0 = 0;
        r7.f = r0;	 Catch:{ all -> 0x002a }
    L_0x001a:
        r1 = r7.mId;	 Catch:{ all -> 0x002a }
        r3 = "state";
        r4 = (long) r8;	 Catch:{ all -> 0x002a }
        r0 = r6;
        r0.a(r1, r3, r4);	 Catch:{ all -> 0x002a }
        r0 = r6.g;	 Catch:{ all -> 0x002a }
        r0.a(r7);	 Catch:{ all -> 0x002a }
    L_0x0028:
        monitor-exit(r7);	 Catch:{ all -> 0x002a }
        goto L_0x000b;
    L_0x002a:
        r0 = move-exception;
        monitor-exit(r7);	 Catch:{ all -> 0x002a }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.download.service.c.a(com.meizu.cloud.download.service.DownloadTaskInfo, int):void");
    }

    private void a(long id, long fileSize) {
        synchronized (this.n) {
            int index = -1;
            for (int i = 0; i < this.i; i++) {
                if (this.k[i] == id) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                this.l[this.i] = fileSize;
                this.k[this.i] = id;
                this.i++;
            } else {
                this.l[index] = fileSize;
                this.k[index] = id;
            }
            this.j++;
            if (this.j == 80 || this.i == 50) {
                m();
            }
        }
    }

    private void l() {
        synchronized (this.n) {
            m();
        }
    }

    private void m() {
        if (this.i > 0) {
            this.c.beginTransaction();
            int i = 0;
            while (i < this.i) {
                try {
                    this.n.put("downloaded_size", Long.valueOf(this.l[i]));
                    this.m[0] = String.valueOf(this.k[i]);
                    this.c.update(b, this.n, "_id = ?", this.m);
                    i++;
                } finally {
                    this.i = 0;
                    this.j = 0;
                    this.c.endTransaction();
                }
            }
            this.c.setTransactionSuccessful();
        }
    }

    private void a(final String message) {
        this.o.post(new Runnable(this) {
            final /* synthetic */ c b;

            public void run() {
                Toast.makeText(this.b.a, message, 0).show();
            }
        });
    }

    public void b(boolean enable) {
        this.h = enable;
    }

    public boolean j() {
        return this.h;
    }

    protected e a(com.meizu.cloud.download.service.e.a stateListener, com.meizu.cloud.download.a.a.a<b> jobExecutor, DownloadTaskInfo info) {
        return new e(this.a.getApplicationContext(), stateListener, jobExecutor, info);
    }
}

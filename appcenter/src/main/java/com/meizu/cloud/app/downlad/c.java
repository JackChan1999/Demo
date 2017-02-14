package com.meizu.cloud.app.downlad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.google.gson.g;
import com.meizu.cloud.app.utils.k;
import com.meizu.cloud.download.c.b;
import com.meizu.cloud.download.service.DownloadTaskInfo;
import java.util.ArrayList;
import java.util.List;

public class c extends SQLiteOpenHelper {
    private static final String a = c.class.getSimpleName();
    private Context b;

    @com.meizu.cloud.download.c.a.c(a = "download_task")
    static class a extends com.meizu.cloud.download.c.a {
        public static final b a = new b(a.class);
        public static final com.meizu.cloud.download.c.a.b<a> d = new com.meizu.cloud.download.c.a.b<a>() {
            public /* synthetic */ com.meizu.cloud.download.c.a create() {
                return a();
            }

            public a a() {
                return new a();
            }
        };
        @com.meizu.cloud.download.c.a.a(a = "key", e = true)
        public String b;
        @com.meizu.cloud.download.c.a.a(a = "value", e = true)
        public String c;

        a() {
        }
    }

    public c(Context context) {
        super(context, "download_task_info.db", null, 1);
        this.b = context.getApplicationContext();
    }

    public void onCreate(SQLiteDatabase db) {
        a();
        a.a.b(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        k.e(this.b, com.meizu.cloud.app.utils.c.a, "drop download_task_info.db table download_task for upgrade");
        db.execSQL("DROP TABLE IF EXISTS download_task");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        k.e(this.b, com.meizu.cloud.app.utils.c.a, "drop download_task_info.db table download_task for upgrade");
        db.execSQL("DROP TABLE IF EXISTS download_task");
        onCreate(db);
    }

    public void a() {
        SQLiteDatabase serviceDB = this.b.openOrCreateDatabase("download_task.db", 0, null);
        serviceDB.execSQL("DROP TABLE IF EXISTS download_task");
        k.e(this.b, com.meizu.cloud.app.utils.c.a, "drop download_task.db table download_task for create");
        DownloadTaskInfo.a.b(serviceDB);
    }

    public void b() {
        getWritableDatabase().execSQL("DROP TABLE IF EXISTS download_task");
        k.e(this.b, com.meizu.cloud.app.utils.c.a, "drop download_task_info.db table download_task for create");
        a.a.b(getWritableDatabase());
    }

    public <T> List<T> a(Class<T> clazz) {
        List<T> reslut = new ArrayList();
        for (a struct : a.a.a(getReadableDatabase(), a.d)) {
            Log.e("TEST", struct.c);
            reslut.add(new g().a().b().a(struct.c, (Class) clazz));
        }
        return reslut;
    }

    public void a(String key, String value) {
        com.meizu.cloud.download.c.a entry = new a();
        entry.b = key;
        entry.c = value;
        Log.d(a, key + "->add:" + a.a.a(getWritableDatabase(), entry) + "->value:" + value);
    }

    public void a(String key) {
        Log.d(a, key + "->remove:" + getWritableDatabase().delete(a.a.a(), "key=?", new String[]{key}));
    }
}

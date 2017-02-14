package com.meizu.cloud.app.update.exclude;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

public class b {
    public static final String[] a = new String[]{"packageName", "versionCode", "appName", "author", "versionName", "retain_1", "retain_2"};
    private Context b;
    private a c = new a(this.b);

    private static class a extends SQLiteOpenHelper {
        public a(Context context) {
            super(context, "apps_update_exclude.db", null, 3);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table Apps ( packageName text PRIMARY KEY, appName text, versionCode INTEGER, author text, versionName text, retain_1 INTEGER, retain_2 text)");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Apps");
            onCreate(db);
        }
    }

    public b(Context mContext) {
        this.b = mContext.getApplicationContext();
    }

    public synchronized Cursor a(String packageName) {
        Cursor ret;
        String whereWithAlarmId = null;
        synchronized (this) {
            if (!TextUtils.isEmpty(packageName)) {
                whereWithAlarmId = "packageName='" + packageName + "'";
            }
            ret = this.c.getReadableDatabase().query("Apps", a, whereWithAlarmId, null, null, null, null);
        }
        return ret;
    }

    public synchronized boolean b(String packageName) {
        boolean z;
        SQLiteDatabase db = this.c.getReadableDatabase();
        Cursor c = a(packageName);
        if (c != null) {
            if (c.getCount() > 0) {
                c.close();
                z = true;
            } else {
                c.close();
            }
        }
        db.close();
        z = false;
        return z;
    }

    public synchronized long a(ContentValues values) {
        long rowId;
        if (values != null) {
            if (values.containsKey("packageName")) {
                if (b(values.getAsString("packageName"))) {
                    rowId = (long) a(values, values.getAsString("packageName"));
                } else {
                    SQLiteDatabase db = this.c.getWritableDatabase();
                    rowId = db.insert("Apps", "", values);
                    db.close();
                }
                if (rowId < 0) {
                    throw new SQLException("Failed to insert row into Apps");
                }
            }
        }
        throw new IllegalArgumentException("ContentValues is null or not contain packName!");
        return rowId;
    }

    public synchronized int c(String packageName) {
        int delete;
        if (TextUtils.isEmpty(packageName)) {
            delete = this.c.getWritableDatabase().delete("Apps", null, null);
        } else if (b(packageName)) {
            delete = this.c.getWritableDatabase().delete("Apps", "packageName='" + packageName + "'", null);
        } else {
            delete = -1;
        }
        return delete;
    }

    public synchronized int a(ContentValues values, String packageName) {
        int count;
        SQLiteDatabase db = this.c.getWritableDatabase();
        count = db.update("Apps", values, "packageName='" + packageName + "'", null);
        db.close();
        return count;
    }
}

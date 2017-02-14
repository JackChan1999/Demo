package com.meizu.statsapp;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import defpackage.anz;
import java.util.ArrayList;

public class UsageStatsProvider extends ContentProvider {
    private static final UriMatcher a = new UriMatcher(-1);
    private a b;
    private Context c;
    private boolean d;
    private int e;

    static class a extends SQLiteOpenHelper {
        public a(Context context) {
            super(context, "UsageStats.db", null, 3);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS event (_id INTEGER DEFAULT '1' NOT NULL PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,type INTEGER NOT NULL,package TEXT NOT NULL,sessionid TEXT NOT NULL,time LONG,page TEXT,properties TEXT,network TEXT,channel LONG,flyme_version TEXT,package_version TEXT);");
        }

        public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS event");
            onCreate(sQLiteDatabase);
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            if (i <= 1) {
                sQLiteDatabase.beginTransaction();
                sQLiteDatabase.execSQL("ALTER TABLE event RENAME TO temp_event");
                sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS event (_id INTEGER DEFAULT '1' NOT NULL PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,type INTEGER NOT NULL,package TEXT NOT NULL,sessionid TEXT NOT NULL,time LONG,page TEXT,properties TEXT,network TEXT,channel LONG,flyme_version TEXT,package_version TEXT);");
                sQLiteDatabase.execSQL("INSERT INTO event(_id,name,type,package,sessionid,time,page,properties)  SELECT *  FROM temp_event");
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS temp_event");
                sQLiteDatabase.setTransactionSuccessful();
                sQLiteDatabase.endTransaction();
            } else if (i <= 2) {
                sQLiteDatabase.beginTransaction();
                sQLiteDatabase.execSQL("ALTER TABLE event RENAME TO temp_event");
                sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS event (_id INTEGER DEFAULT '1' NOT NULL PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,type INTEGER NOT NULL,package TEXT NOT NULL,sessionid TEXT NOT NULL,time LONG,page TEXT,properties TEXT,network TEXT,channel LONG,flyme_version TEXT,package_version TEXT);");
                sQLiteDatabase.execSQL("INSERT INTO event(_id,name,type,package,sessionid,time,page,properties,network,channel,flyme_version)  SELECT *  FROM temp_event");
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS temp_event");
                sQLiteDatabase.setTransactionSuccessful();
                sQLiteDatabase.endTransaction();
            }
        }
    }

    static {
        a.addURI("com.meizu.usagestats", "event", 1);
        a.addURI("com.meizu.usagestats", "clear_events", 2);
    }

    public UsageStatsProvider() {
        this.e = anz.e;
    }

    public UsageStatsProvider(Context context) {
        this.d = true;
        this.e = anz.d;
        this.c = context;
    }

    public boolean onCreate() {
        if (!this.d) {
            this.c = getContext();
        }
        this.b = new a(this.c);
        return true;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        int match = a.match(uri);
        if (match < 1) {
            return null;
        }
        SQLiteDatabase readableDatabase = this.b.getReadableDatabase();
        if (readableDatabase == null) {
            return null;
        }
        SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
        String queryParameter = uri.getQueryParameter("limit");
        if (uri.getQueryParameter("distinct") != null) {
            sQLiteQueryBuilder.setDistinct(true);
        }
        switch (match) {
            case 1:
                sQLiteQueryBuilder.setTables("event");
                return sQLiteQueryBuilder.query(readableDatabase, strArr, str, strArr2, null, null, str2, queryParameter);
            default:
                throw new UnsupportedOperationException("Invalid URI " + uri);
        }
    }

    public String getType(Uri uri) {
        int match = a.match(uri);
        if (match < 1) {
            return null;
        }
        switch (match) {
            case 1:
                return "vnd.android.cursor.dir/event";
            default:
                throw new IllegalStateException("Unknown URL : " + uri);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Uri insert(Uri r7, ContentValues r8) {
        /*
        r6 = this;
        r0 = 0;
        r1 = a;
        r1 = r1.match(r7);
        r2 = 1;
        if (r1 >= r2) goto L_0x000b;
    L_0x000a:
        return r0;
    L_0x000b:
        r2 = r6.b;
        r2 = r2.getWritableDatabase();
        if (r2 == 0) goto L_0x000a;
    L_0x0013:
        switch(r1) {
            case 1: goto L_0x0024;
            default: goto L_0x0016;
        };
    L_0x0016:
        r1 = r0;
    L_0x0017:
        if (r1 == 0) goto L_0x0022;
    L_0x0019:
        r2 = r6.c;
        r2 = r2.getContentResolver();
        r2.notifyChange(r7, r0);
    L_0x0022:
        r0 = r1;
        goto L_0x000a;
    L_0x0024:
        r1 = "event";
        r2 = r2.insert(r1, r0, r8);
        r4 = 0;
        r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r1 <= 0) goto L_0x0016;
    L_0x0030:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r4 = "content://com.meizu.usagestats/event/";
        r1 = r1.append(r4);
        r2 = java.lang.String.valueOf(r2);
        r1 = r1.append(r2);
        r1 = r1.toString();
        r1 = android.net.Uri.parse(r1);
        goto L_0x0017;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.statsapp.UsageStatsProvider.insert(android.net.Uri, android.content.ContentValues):android.net.Uri");
    }

    public int delete(Uri uri, String str, String[] strArr) {
        int i = 0;
        int match = a.match(uri);
        if (match >= 1) {
            SQLiteDatabase writableDatabase = this.b.getWritableDatabase();
            if (writableDatabase != null) {
                switch (match) {
                    case 1:
                        i = writableDatabase.delete("event", str, strArr);
                        break;
                    case 2:
                        b();
                        break;
                }
                if (i > 0) {
                    this.c.getContentResolver().notifyChange(uri, null);
                }
            }
        }
        return i;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        int i = 0;
        int match = a.match(uri);
        if (match >= 1) {
            SQLiteDatabase writableDatabase = this.b.getWritableDatabase();
            if (writableDatabase != null) {
                switch (match) {
                    case 1:
                        i = writableDatabase.update("event", contentValues, str, strArr);
                        break;
                }
                if (i > 0) {
                    this.c.getContentResolver().notifyChange(uri, null);
                }
            }
        }
        return i;
    }

    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> arrayList) {
        int size = arrayList.size();
        ContentProviderResult[] contentProviderResultArr = new ContentProviderResult[size];
        SQLiteDatabase writableDatabase = this.b.getWritableDatabase();
        if (writableDatabase == null) {
            return null;
        }
        try {
            writableDatabase.beginTransaction();
            for (int i = 0; i < size; i++) {
                contentProviderResultArr[i] = ((ContentProviderOperation) arrayList.get(i)).apply(this, contentProviderResultArr, i);
            }
            writableDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writableDatabase.endTransaction();
        }
        return contentProviderResultArr;
    }

    private int a() {
        Cursor cursor = null;
        int i = 0;
        SQLiteDatabase readableDatabase = this.b.getReadableDatabase();
        if (readableDatabase != null) {
            try {
                cursor = readableDatabase.rawQuery("select count(*) from event", null);
                if (cursor != null && cursor.moveToNext()) {
                    i = cursor.getInt(0);
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return i;
    }

    private void b() {
        SQLiteDatabase writableDatabase = this.b.getWritableDatabase();
        if (writableDatabase != null) {
            if (a() > this.e) {
                writableDatabase.execSQL("delete from event where _id not in ( select _id from event order by time desc limit " + (this.e / 2) + ")");
            }
            writableDatabase.delete("event", "time < ?", new String[]{String.valueOf(System.currentTimeMillis() - 604800000)});
        }
    }
}

package com.meizu.statsapp;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
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

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS event (_id INTEGER DEFAULT '1' NOT NULL PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,type INTEGER NOT NULL,package TEXT NOT NULL,sessionid TEXT NOT NULL,time LONG,page TEXT,properties TEXT,network TEXT,channel LONG,flyme_version TEXT,package_version TEXT);");
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS event");
            onCreate(db);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion <= 1) {
                db.beginTransaction();
                db.execSQL("ALTER TABLE event RENAME TO temp_event");
                db.execSQL("CREATE TABLE IF NOT EXISTS event (_id INTEGER DEFAULT '1' NOT NULL PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,type INTEGER NOT NULL,package TEXT NOT NULL,sessionid TEXT NOT NULL,time LONG,page TEXT,properties TEXT,network TEXT,channel LONG,flyme_version TEXT,package_version TEXT);");
                db.execSQL("INSERT INTO event(_id,name,type,package,sessionid,time,page,properties)  SELECT *  FROM temp_event");
                db.execSQL("DROP TABLE IF EXISTS temp_event");
                db.setTransactionSuccessful();
                db.endTransaction();
            } else if (oldVersion <= 2) {
                db.beginTransaction();
                db.execSQL("ALTER TABLE event RENAME TO temp_event");
                db.execSQL("CREATE TABLE IF NOT EXISTS event (_id INTEGER DEFAULT '1' NOT NULL PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,type INTEGER NOT NULL,package TEXT NOT NULL,sessionid TEXT NOT NULL,time LONG,page TEXT,properties TEXT,network TEXT,channel LONG,flyme_version TEXT,package_version TEXT);");
                db.execSQL("INSERT INTO event(_id,name,type,package,sessionid,time,page,properties,network,channel,flyme_version)  SELECT *  FROM temp_event");
                db.execSQL("DROP TABLE IF EXISTS temp_event");
                db.setTransactionSuccessful();
                db.endTransaction();
            }
        }
    }

    static {
        a.addURI("com.meizu.usagestats", "event", 1);
        a.addURI("com.meizu.usagestats", "clear_events", 2);
    }

    public UsageStatsProvider() {
        this.e = b.e;
    }

    public UsageStatsProvider(Context context) {
        this.d = true;
        this.e = b.d;
        this.c = context;
    }

    public boolean onCreate() {
        if (!this.d) {
            this.c = getContext();
        }
        this.b = new a(this.c);
        return true;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int table = a.match(uri);
        if (table < 1) {
            return null;
        }
        SQLiteDatabase db = this.b.getReadableDatabase();
        if (db == null) {
            return null;
        }
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String limit = uri.getQueryParameter("limit");
        if (uri.getQueryParameter("distinct") != null) {
            qb.setDistinct(true);
        }
        switch (table) {
            case 1:
                qb.setTables("event");
                return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder, limit);
            default:
                throw new UnsupportedOperationException("Invalid URI " + uri);
        }
    }

    public String getType(Uri uri) {
        int table = a.match(uri);
        if (table < 1) {
            return null;
        }
        switch (table) {
            case 1:
                return "vnd.android.cursor.dir/event";
            default:
                throw new IllegalStateException("Unknown URL : " + uri);
        }
    }

    public Uri insert(Uri uri, ContentValues values) {
        int table = a.match(uri);
        if (table < 1) {
            return null;
        }
        SQLiteDatabase db = this.b.getWritableDatabase();
        if (db == null) {
            return null;
        }
        Uri newUri = null;
        switch (table) {
            case 1:
                long rowId = db.insert("event", null, values);
                if (rowId > 0) {
                    newUri = Uri.parse("content://com.meizu.usagestats/event/" + String.valueOf(rowId));
                    break;
                }
                break;
        }
        if (newUri == null) {
            return newUri;
        }
        this.c.getContentResolver().notifyChange(uri, null);
        return newUri;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int i = 0;
        int table = a.match(uri);
        if (table >= 1) {
            SQLiteDatabase db = this.b.getWritableDatabase();
            if (db != null) {
                i = 0;
                switch (table) {
                    case 1:
                        i = db.delete("event", selection, selectionArgs);
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

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int i = 0;
        int table = a.match(uri);
        if (table >= 1) {
            SQLiteDatabase db = this.b.getWritableDatabase();
            if (db != null) {
                i = 0;
                switch (table) {
                    case 1:
                        i = db.update("event", values, selection, selectionArgs);
                        break;
                }
                if (i > 0) {
                    this.c.getContentResolver().notifyChange(uri, null);
                }
            }
        }
        return i;
    }

    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        int numOperations = operations.size();
        ContentProviderResult[] results = new ContentProviderResult[numOperations];
        SQLiteDatabase db = this.b.getWritableDatabase();
        if (db == null) {
            return null;
        }
        try {
            db.beginTransaction();
            for (int i = 0; i < numOperations; i++) {
                results[i] = ((ContentProviderOperation) operations.get(i)).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            return results;
        } finally {
            db.endTransaction();
        }
    }

    private int a() {
        int i = 0;
        SQLiteDatabase db = this.b.getReadableDatabase();
        if (db != null) {
            i = 0;
            Cursor cursor = null;
            try {
                cursor = db.rawQuery("select count(*) from event", null);
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
        SQLiteDatabase db = this.b.getWritableDatabase();
        if (db != null) {
            if (a() > this.e) {
                db.execSQL("delete from event where _id not in ( select _id from event order by time desc limit " + (this.e / 2) + ")");
            }
            db.delete("event", "time < ?", new String[]{String.valueOf(System.currentTimeMillis() - 604800000)});
        }
    }
}

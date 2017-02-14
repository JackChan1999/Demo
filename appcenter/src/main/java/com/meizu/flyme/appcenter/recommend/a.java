package com.meizu.flyme.appcenter.recommend;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.meizu.cloud.app.utils.c;
import com.meizu.cloud.pushsdk.constants.PushConstants;

public class a {
    public static final String[] a = new String[]{"doc_type", "appId", "app_scheme", "name_cn", "name_tw", "name_en", "icon", "url", "local_icon", "backup_1", "backup_2"};
    private Context b = null;
    private SQLiteDatabase c = null;
    private a d = null;

    private static class a extends SQLiteOpenHelper {
        public a(Context context) {
            super(context, "Mstore_Recommend_db", null, 3);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table Mstore_Recommend_Table ( doc_type text,appId text,app_scheme INTEGER,name_cn text,name_tw text,name_en text,icon text,local_icon text,url text,backup_1 text,backup_2 text)");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(c.a, "drop table Mstore_Recommend_Table for upgrade");
            db.execSQL("DROP TABLE IF EXISTS Mstore_Recommend_Table");
            onCreate(db);
        }
    }

    public a(Context context) {
        this.b = context;
    }

    SQLiteDatabase a() {
        if (this.d != null) {
            return this.d.getWritableDatabase();
        }
        return null;
    }

    public void b() throws SQLiteException {
        if (this.d == null) {
            this.d = new a(this.b);
            this.c = this.d.getWritableDatabase();
        }
    }

    public void c() {
        if (this.d != null) {
            this.d.close();
            this.d = null;
            this.c = null;
        }
    }

    public int d() {
        return this.c.delete("Mstore_Recommend_Table", null, null);
    }

    public long a(String type, String key, String[] schemes, String cn, String tw, String en, String icon, String url, String local_url) {
        ContentValues initValues = new ContentValues();
        initValues.put("doc_type", type);
        initValues.put("icon", icon);
        initValues.put("name_cn", cn);
        initValues.put("name_en", en);
        initValues.put("name_tw", tw);
        initValues.put("appId", key);
        initValues.put("url", url);
        initValues.put("local_icon", local_url);
        int schemeValue = 0;
        if (schemes != null) {
            boolean bFile = false;
            boolean bContent = false;
            for (String schemeText : schemes) {
                if ("file".equals(schemeText)) {
                    bFile = true;
                } else if (PushConstants.CONTENT.equals(schemeText)) {
                    bContent = true;
                }
            }
            if (bFile && bContent) {
                schemeValue = 3;
            } else if (bFile) {
                schemeValue = 1;
            } else if (bContent) {
                schemeValue = 2;
            }
        }
        initValues.put("app_scheme", Integer.valueOf(schemeValue));
        initValues.put("backup_1", "");
        initValues.put("backup_2", "");
        return this.c.insert("Mstore_Recommend_Table", null, initValues);
    }
}

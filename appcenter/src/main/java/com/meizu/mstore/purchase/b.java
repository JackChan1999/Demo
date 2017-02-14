package com.meizu.mstore.purchase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.meizu.cloud.app.utils.j;

public class b {
    public static final String[] a = new String[]{"good_id", "good_name", "good_desc", "app_id", "quantity", "amount", "order_num", "order_info", "order_date", "buy_token", "receipt", "license", "state", "tip", "backup_1", "backup_2", "backup_3"};
    private static final String b = b.class.getSimpleName();
    private Context c = null;
    private SQLiteDatabase d = null;
    private a e = null;

    private static class a extends SQLiteOpenHelper {
        public a(Context context) {
            super(context, "Mstore_Payment_db", null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table Mstore_Payment_Order_Table ( good_id text,good_name text,good_desc text,app_id text,quantity INTEGER,amount Double,order_num text primary key,order_info text,order_date text,buy_token text,receipt text,license text,state INTEGER,tip text,backup_1 text,backup_2 text,backup_3 text)");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Mstore_Payment_Order_Table");
            onCreate(db);
        }
    }

    public b(Context context) {
        this.c = context;
        this.e = new a(context);
        SQLiteDatabase checkDb = this.e.getReadableDatabase();
        try {
            checkDb.query("Mstore_Payment_Order_Table", a, null, null, null, null, null).close();
        } catch (Exception e) {
            j.c(b, "db columns, drop it and create!");
            checkDb.execSQL("DROP TABLE IF EXISTS Mstore_Payment_Order_Table");
            checkDb.execSQL("create table Mstore_Payment_Order_Table ( good_id text,good_name text,good_desc text,app_id text,quantity INTEGER,amount Double,order_num text primary key,order_info text,order_date text,buy_token text,receipt text,license text,state INTEGER,tip text,backup_1 text,backup_2 text,backup_3 text)");
        }
        this.e.close();
        this.e = null;
    }
}

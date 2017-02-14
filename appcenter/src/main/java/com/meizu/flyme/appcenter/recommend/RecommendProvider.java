package com.meizu.flyme.appcenter.recommend;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import com.meizu.cloud.app.utils.q;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;

public class RecommendProvider extends ContentProvider {
    private a a = null;

    public boolean onCreate() {
        this.a = new a(getContext());
        this.a.b();
        if (q.a(getContext())) {
            b.a(getContext()).a(false);
        }
        return false;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = this.a.a();
        qb.setTables("Mstore_Recommend_Table");
        if (projection != null) {
            int len = projection.length;
            for (int i = 0; i < len; i++) {
                String item = projection[i];
                if (item.equals("name")) {
                    String languageField;
                    if (Locale.getDefault().getLanguage().toLowerCase().equals("zh") && Locale.getDefault().getCountry().toLowerCase().equals("cn")) {
                        languageField = "name_cn";
                    } else if (Locale.getDefault().getLanguage().toLowerCase().equals("zh") && Locale.getDefault().getCountry().toLowerCase().equals("tw")) {
                        languageField = "name_tw";
                    } else {
                        languageField = "name_en";
                    }
                    projection[i] = languageField + " as " + item;
                } else if (item.equals("scheme")) {
                }
            }
        }
        Log.d(RecommendProvider.class.getSimpleName(), "selection=" + selection);
        Cursor c = qb.query(db, projection, selection, null, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        String path = uri.getPath();
        if (!TextUtils.isEmpty(path) && path.length() > 0) {
            path = path.substring(1);
        }
        Log.d(RecommendProvider.class.getSimpleName(), "read file=" + path);
        return ParcelFileDescriptor.open(new File(path), 268435456);
    }
}

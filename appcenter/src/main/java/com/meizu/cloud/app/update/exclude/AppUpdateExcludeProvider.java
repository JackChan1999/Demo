package com.meizu.cloud.app.update.exclude;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

public class AppUpdateExcludeProvider extends ContentProvider {
    private b a;

    public boolean onCreate() {
        this.a = new b(getContext());
        return false;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return this.a.a(selection);
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        long rowId = this.a.a(values);
        if (rowId < 0) {
            throw new SQLException("Failed to insert row into " + uri);
        }
        Uri newUri = ContentUris.withAppendedId(uri, rowId);
        getContext().getContentResolver().notifyChange(uri, null);
        return newUri;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int res = this.a.c(selection);
        if (res > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return res;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int res = this.a.a(values, selection);
        if (res > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return res;
    }
}

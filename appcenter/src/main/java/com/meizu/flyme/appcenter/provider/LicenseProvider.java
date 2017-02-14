package com.meizu.flyme.appcenter.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Base64;
import java.io.File;
import java.io.FileOutputStream;

public class LicenseProvider extends ContentProvider {
    private UriMatcher a = new UriMatcher(-1);

    public LicenseProvider() {
        this.a.addURI("com.meizu.flyme.appcenter.licenseprovider", "license", 1);
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String getType(Uri uri) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Uri insert(Uri uri, ContentValues values) {
        if (this.a.match(uri) != 1) {
            return ContentUris.withAppendedId(uri, -1);
        }
        if (a(values.getAsString("PACKAGE_NAME"), values.getAsString("LICENSE_DATA"))) {
            return ContentUris.withAppendedId(uri, 1);
        }
        return ContentUris.withAppendedId(uri, 0);
    }

    public boolean onCreate() {
        return false;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private boolean a(String packageName, String licenseData) {
        try {
            File file = new File(getContext().getDir("LicenseFile", 0).getPath() + File.separator + packageName + ".license");
            if (file.isDirectory()) {
                return false;
            }
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            } else {
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file);
            out.write(Base64.decode(licenseData.getBytes(), 0));
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

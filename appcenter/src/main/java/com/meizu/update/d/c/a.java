package com.meizu.update.d.c;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.meizu.update.h.b;
import com.meizu.update.h.c;
import com.meizu.update.h.g;
import org.apache.commons.io.FileUtils;

public class a implements d {
    private Context a;
    private int b;
    private String c;
    private long d;
    private String e;
    private int f;
    private boolean g = true;
    private boolean h = false;

    public a(Context context, int verifyMode, String packageName, long fileLength, String fileMd5, int versionCode) {
        this.a = context;
        this.b = verifyMode;
        this.c = packageName;
        this.d = fileLength;
        this.e = fileMd5;
        this.f = versionCode;
        b("Checker limit:" + toString());
    }

    public String a() {
        if (TextUtils.isEmpty(this.e) || !a(2)) {
            return null;
        }
        return this.e;
    }

    public String b() {
        if (TextUtils.isEmpty(this.e) || !a(4)) {
            return null;
        }
        return this.e;
    }

    public long c() {
        if (this.d <= 0 || !a(1)) {
            return 0;
        }
        return this.d;
    }

    private boolean a(int mask) {
        return (this.b & mask) > 0;
    }

    public c a(long httpRange, long contentLength) {
        if (this.g) {
            this.h = contentLength <= 0;
            if (this.d > 0 && !this.h && a(1)) {
                boolean match;
                if (httpRange + contentLength == this.d) {
                    match = true;
                } else {
                    match = false;
                }
                if (!match) {
                    String errorMsg = "File length not match(" + this.d + "->" + (httpRange + contentLength) + ")";
                    b(errorMsg);
                    return c.a(errorMsg);
                }
            }
        }
        return c.a();
    }

    public c a(String filePath) {
        if (this.g) {
            String errorMsg;
            boolean match;
            boolean z = false;
            if (!TextUtils.isEmpty(this.c) && a(8)) {
                z = true;
                PackageInfo info = g.d(this.a, filePath);
                if (info == null) {
                    errorMsg = "File cant parse to package info(" + this.c + "->" + this.f + ")";
                    b(errorMsg);
                    return c.a(errorMsg);
                } else if (!this.c.equalsIgnoreCase(info.packageName)) {
                    errorMsg = "Package name not match(" + this.c + "->" + info.packageName + ")";
                    b(errorMsg);
                    return c.a(errorMsg);
                } else if (this.f > 0 && a(16)) {
                    if (this.f == info.versionCode) {
                        match = true;
                    } else {
                        match = false;
                    }
                    if (!match) {
                        errorMsg = "Package version code not match(" + this.f + "->" + info.versionCode + ")";
                        b(errorMsg);
                        return c.a(errorMsg);
                    }
                }
            }
            if (!TextUtils.isEmpty(this.e)) {
                if (a(2)) {
                    z = true;
                    String fileMd5 = c.a(filePath);
                    if (!this.e.equalsIgnoreCase(fileMd5)) {
                        errorMsg = "Whole md5 not match(" + this.e + "->" + fileMd5 + ")";
                        b(errorMsg);
                        return c.a(errorMsg);
                    }
                } else if (a(4)) {
                    z = true;
                    String headTailMd5 = c.a(filePath, FileUtils.ONE_MB);
                    if (!this.e.equalsIgnoreCase(headTailMd5)) {
                        errorMsg = "HeadTail md5 not match(" + this.e + "->" + headTailMd5 + ")";
                        b(errorMsg);
                        return c.a(errorMsg);
                    }
                }
            }
            if (!z && this.d > 0 && this.h && a(1)) {
                this.h = false;
                long fileLength = g.a(filePath);
                if (fileLength > 0) {
                    if (fileLength == this.d) {
                        match = true;
                    } else {
                        match = false;
                    }
                    if (!match) {
                        errorMsg = "Download File length not match(" + this.d + "->" + fileLength + ")";
                        b(errorMsg);
                        return c.a(errorMsg);
                    }
                }
            }
        }
        return c.a();
    }

    public void a(boolean enable) {
        this.g = enable;
    }

    public String d() {
        if (this.f > 0) {
            return String.valueOf(this.f);
        }
        return null;
    }

    private void b(String msg) {
        b.d(msg);
    }

    public String toString() {
        String verifyStr = "";
        if (a(1)) {
            verifyStr = verifyStr + "size ";
        }
        if (a(4)) {
            verifyStr = verifyStr + "1mmd5 ";
        }
        if (a(8)) {
            verifyStr = verifyStr + "pkg ";
        }
        if (a(16)) {
            verifyStr = verifyStr + "vcode ";
        }
        if (a(2)) {
            verifyStr = verifyStr + "md5 ";
        }
        if (TextUtils.isEmpty(verifyStr)) {
            verifyStr = "null";
        }
        return "verify_mode=" + verifyStr + ",pk=" + this.c + ",size=" + this.d + ",md5=" + this.e + ",v_code=" + this.f;
    }
}

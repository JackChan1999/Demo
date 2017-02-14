package com.meizu.gslb.b;

import android.net.Uri;
import android.text.TextUtils;
import com.meizu.gslb.g.a;

public class c {
    private final String a;
    private final String b;

    protected c(String str) {
        if (str == null) {
            throw new IllegalArgumentException("original url cant be null");
        }
        this.a = str;
        this.b = Uri.parse(str).getHost();
    }

    public String a() {
        return this.b;
    }

    protected String a(b bVar) {
        String str = null;
        if (bVar != null) {
            str = bVar.a();
        }
        if (TextUtils.isEmpty(str)) {
            return this.a;
        }
        String replaceFirst = this.a.replaceFirst(this.b, str);
        a.a("Domain convert: " + this.b + "->" + str);
        return replaceFirst;
    }
}

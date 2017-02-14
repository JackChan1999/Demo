package com.meizu.update.d.b;

import android.text.TextUtils;
import android.util.Pair;
import com.meizu.update.d.c.d;
import java.util.ArrayList;
import java.util.List;

public class c {
    public String a;
    public List<Pair<String, String>> b;

    public c(String newUrl, List<Pair<String, String>> requestHeaders) {
        this.a = newUrl;
        this.b = requestHeaders;
    }

    public void a(d checker) {
        if (checker != null) {
            String md5 = checker.a();
            String partialMd5 = checker.b();
            long size = checker.c();
            if (!TextUtils.isEmpty(md5) || !TextUtils.isEmpty(partialMd5) || size > 0) {
                if (this.b == null) {
                    this.b = new ArrayList(2);
                }
                if (!TextUtils.isEmpty(md5)) {
                    this.b.add(new Pair("Mz_md5", md5));
                }
                if (!TextUtils.isEmpty(partialMd5)) {
                    this.b.add(new Pair("Mz_partial_md5", partialMd5));
                }
                if (size > 0) {
                    this.b.add(new Pair("Mz_size", String.valueOf(size)));
                }
            }
        }
    }
}

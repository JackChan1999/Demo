package com.meizu.cloud.download.b;

import android.content.Context;
import android.util.Pair;
import java.util.List;

public interface e {

    public static class a {
        public String a;
        public List<Pair<String, String>> b;

        public a(String newUrl, List<Pair<String, String>> requestHeaders) {
            this.a = newUrl;
            this.b = requestHeaders;
        }
    }

    a a(long j, long j2);

    a a(String str);

    a a(Context context, String str);

    String a();

    void a(String str, int i, String str2, String str3);

    void a(String str, String str2, String str3);

    void a(boolean z);

    a b(Context context, String str);

    void b();

    void b(String str, int i, String str2, String str3);

    void b(String str, String str2, String str3);
}

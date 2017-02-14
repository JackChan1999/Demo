package com.meizu.statsapp.a;

import android.util.Pair;
import com.meizu.gslb.d.a;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

public class d extends a {
    private String g;

    public d(String url, List<Pair<String, String>> params, String content) {
        super(url, params);
        this.g = content;
    }

    public d(String url, List<Pair<String, String>> params, List<Pair<String, String>> requestHeaders, String content) {
        super(url, params, requestHeaders);
        this.g = content;
    }

    public String j() {
        return this.g == null ? "" : this.g;
    }

    public boolean i() {
        return true;
    }

    public SSLSocketFactory k() {
        return null;
    }

    public HostnameVerifier l() {
        if (d() && e()) {
            return new com.meizu.gslb.d.a.a.a(h());
        }
        return null;
    }
}

package com.meizu.volley.d;

import android.util.Pair;
import com.android.volley.l;
import com.meizu.gslb.d.a;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

public class d extends a {
    private final l<?> g;

    public d(l<?> request, List<Pair<String, String>> headers) {
        super(request.getUrl(), null, headers);
        this.g = request;
    }

    public boolean i() {
        return true;
    }

    public SSLSocketFactory j() {
        return null;
    }

    public HostnameVerifier k() {
        if (d() && e()) {
            return new com.meizu.gslb.d.a.a.a(h());
        }
        return null;
    }

    public l<?> l() {
        return this.g;
    }
}

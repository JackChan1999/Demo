package com.meizu.gslb.d.a.a;

import com.meizu.gslb.d.c;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class a implements HostnameVerifier {
    private final c a;

    public a(c cVar) {
        this.a = cVar;
    }

    public boolean verify(String str, SSLSession sSLSession) {
        return this.a.a(str, sSLSession);
    }
}

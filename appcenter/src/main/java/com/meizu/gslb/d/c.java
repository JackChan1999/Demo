package com.meizu.gslb.d;

import com.meizu.gslb.g.a;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class c {
    private final String a;

    public c(String str) {
        this.a = str;
    }

    public boolean a(String str, SSLSession sSLSession) {
        a.a("verify:" + this.a + ">" + str);
        HostnameVerifier defaultHostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
        return defaultHostnameVerifier.verify(this.a, sSLSession) ? true : defaultHostnameVerifier.verify(str, sSLSession);
    }
}

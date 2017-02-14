package com.meizu.statsapp.a;

import android.util.Pair;
import com.meizu.gslb.d.e;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class b implements e<e, d> {
    private HttpURLConnection a;

    public e a(d request) throws IOException {
        this.a = (HttpURLConnection) new URL(request.f()).openConnection();
        SSLSocketFactory sf = request.k();
        HostnameVerifier hv = request.l();
        if (this.a instanceof HttpsURLConnection) {
            HttpsURLConnection httpsConnection = this.a;
            if (sf != null) {
                httpsConnection.setSSLSocketFactory(sf);
            }
            if (hv != null) {
                httpsConnection.setHostnameVerifier(hv);
            }
        }
        List<Pair<String, String>> requestHeaders = request.b();
        if (requestHeaders != null && requestHeaders.size() > 0) {
            for (Pair<String, String> header : requestHeaders) {
                this.a.setRequestProperty((String) header.first, (String) header.second);
            }
        }
        List<Pair<String, String>> extraHeaders = request.c();
        if (extraHeaders != null && extraHeaders.size() > 0) {
            for (Pair<String, String> header2 : extraHeaders) {
                this.a.setRequestProperty((String) header2.first, (String) header2.second);
            }
        }
        String content = request.j();
        this.a.setRequestMethod("POST");
        this.a.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(this.a.getOutputStream());
        out.write(content.getBytes("UTF-8"));
        out.close();
        this.a.connect();
        return new e(this.a);
    }

    public void a() {
        if (this.a != null) {
            this.a.disconnect();
        }
    }
}

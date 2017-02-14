package com.meizu.volley.d;

import android.util.Pair;
import com.android.volley.a;
import com.android.volley.l;
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

    public e a(d request) throws IOException, com.meizu.gslb.d.b {
        this.a = (HttpURLConnection) new URL(request.f()).openConnection();
        l<?> realRequest = request.l();
        int timeoutMs = realRequest.getTimeoutMs();
        this.a.setConnectTimeout(timeoutMs);
        this.a.setReadTimeout(timeoutMs);
        this.a.setUseCaches(false);
        this.a.setDoInput(true);
        SSLSocketFactory sf = request.j();
        HostnameVerifier hv = request.k();
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
        try {
            a(this.a, realRequest);
            this.a.connect();
            return new e(this.a);
        } catch (a authFailureError) {
            throw new com.meizu.gslb.d.b(authFailureError);
        }
    }

    public void a() {
        if (this.a != null) {
            this.a.disconnect();
        }
    }

    static void a(HttpURLConnection connection, l<?> request) throws IOException, a {
        switch (request.getMethod()) {
            case -1:
                byte[] postBody = request.getPostBody();
                if (postBody != null) {
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.addRequestProperty("Content-Type", request.getPostBodyContentType());
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.write(postBody);
                    out.close();
                    return;
                }
                return;
            case 0:
                connection.setRequestMethod("GET");
                return;
            case 1:
                connection.setRequestMethod("POST");
                b(connection, request);
                return;
            case 2:
                connection.setRequestMethod("PUT");
                b(connection, request);
                return;
            case 3:
                connection.setRequestMethod("DELETE");
                return;
            case 4:
                connection.setRequestMethod("HEAD");
                return;
            case 5:
                connection.setRequestMethod("OPTIONS");
                return;
            case 6:
                connection.setRequestMethod("TRACE");
                return;
            case 7:
                connection.setRequestMethod("PATCH");
                b(connection, request);
                return;
            default:
                throw new IllegalStateException("Unknown method type.");
        }
    }

    private static void b(HttpURLConnection connection, l<?> request) throws IOException, a {
        byte[] body = request.getBody();
        if (body != null) {
            connection.setDoOutput(true);
            connection.addRequestProperty("Content-Type", request.getBodyContentType());
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(body);
            out.close();
        }
    }
}

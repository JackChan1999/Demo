package com.meizu.cloud.download.service;

import android.content.Context;
import android.net.SSLCertificateSocketFactory;
import android.net.SSLSessionCache;
import android.os.Looper;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.security.KeyStore;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpContext;

public final class h implements HttpClient {
    public static long a = 256;
    private static final HttpRequestInterceptor b = new HttpRequestInterceptor() {
        public void process(HttpRequest request, HttpContext context) {
            if (Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper()) {
                throw new RuntimeException("This thread forbids HTTP requests");
            }
        }
    };
    private final HttpClient c;
    private RuntimeException d = new IllegalStateException("MAndroidHttpClient created and never closed");
    private volatile b e;

    private class a implements HttpRequestInterceptor {
        final /* synthetic */ h a;

        private a(h hVar) {
            this.a = hVar;
        }

        public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
            b configuration = this.a.e;
            if (configuration != null && configuration.a() && (request instanceof HttpUriRequest)) {
                configuration.a(h.b((HttpUriRequest) request, false));
            }
        }
    }

    private static class b {
        private final String a;
        private final int b;

        private boolean a() {
            return Log.isLoggable(this.a, this.b);
        }

        private void a(String message) {
            Log.println(this.b, this.a, message);
        }
    }

    public static h a(String userAgent, Context context, int timeOut, boolean ignoreCertificate, boolean redirecting) {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setStaleCheckingEnabled(params, false);
        HttpConnectionParams.setConnectionTimeout(params, timeOut);
        HttpConnectionParams.setSoTimeout(params, timeOut);
        HttpConnectionParams.setSocketBufferSize(params, 8192);
        HttpClientParams.setRedirecting(params, redirecting);
        HttpProtocolParams.setUserAgent(params, userAgent);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        SocketFactory factory = null;
        if (ignoreCertificate) {
            try {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                SSLSocketFactory sf = new i(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                factory = sf;
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
        }
        if (factory == null) {
            factory = SSLCertificateSocketFactory.getHttpSocketFactory(30000, context == null ? null : new SSLSessionCache(context));
        }
        schemeRegistry.register(new Scheme("https", factory, 443));
        return new h(new ThreadSafeClientConnManager(params, schemeRegistry), params);
    }

    public static h a(String userAgent, int timeOut) {
        return a(userAgent, null, timeOut, false, true);
    }

    public static h b(String userAgent, int timeOut) {
        return a(userAgent, null, timeOut, false, false);
    }

    private h(ClientConnectionManager ccm, HttpParams params) {
        this.c = new DefaultHttpClient(this, ccm, params) {
            final /* synthetic */ h a;

            protected BasicHttpProcessor createHttpProcessor() {
                BasicHttpProcessor processor = super.createHttpProcessor();
                processor.addRequestInterceptor(h.b);
                processor.addRequestInterceptor(new a());
                return processor;
            }

            protected HttpContext createHttpContext() {
                HttpContext context = new BasicHttpContext();
                context.setAttribute("http.authscheme-registry", getAuthSchemes());
                context.setAttribute("http.cookiespec-registry", getCookieSpecs());
                context.setAttribute("http.auth.credentials-provider", getCredentialsProvider());
                return context;
            }
        };
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (this.d != null) {
            Log.e("MAndroidHttpClient", "Leak found", this.d);
            this.d = null;
        }
    }

    public void a() {
        if (this.d != null) {
            getConnectionManager().shutdown();
            this.d = null;
        }
    }

    public HttpParams getParams() {
        return this.c.getParams();
    }

    public ClientConnectionManager getConnectionManager() {
        return this.c.getConnectionManager();
    }

    public HttpResponse execute(HttpUriRequest request) throws IOException {
        return this.c.execute(request);
    }

    public HttpResponse execute(HttpUriRequest request, HttpContext context) throws IOException {
        return this.c.execute(request, context);
    }

    public HttpResponse execute(HttpHost target, HttpRequest request) throws IOException {
        return this.c.execute(target, request);
    }

    public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context) throws IOException {
        return this.c.execute(target, request, context);
    }

    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        return this.c.execute(request, responseHandler);
    }

    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException, ClientProtocolException {
        return this.c.execute(request, responseHandler, context);
    }

    public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        return this.c.execute(target, request, responseHandler);
    }

    public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException, ClientProtocolException {
        return this.c.execute(target, request, responseHandler, context);
    }

    private static String b(HttpUriRequest request, boolean logAuthToken) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("curl ");
        for (Header header : request.getAllHeaders()) {
            if (logAuthToken || !(header.getName().equals("Authorization") || header.getName().equals("Cookie"))) {
                builder.append("--header \"");
                builder.append(header.toString().trim());
                builder.append("\" ");
            }
        }
        URI uri = request.getURI();
        if (request instanceof RequestWrapper) {
            HttpRequest original = ((RequestWrapper) request).getOriginal();
            if (original instanceof HttpUriRequest) {
                uri = ((HttpUriRequest) original).getURI();
            }
        }
        builder.append("\"");
        builder.append(uri);
        builder.append("\"");
        if (request instanceof HttpEntityEnclosingRequest) {
            HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
            if (entity != null && entity.isRepeatable()) {
                if (entity.getContentLength() < 1024) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    entity.writeTo(stream);
                    builder.append(" --data-ascii \"").append(stream.toString()).append("\"");
                } else {
                    builder.append(" [TOO MUCH DATA TO INCLUDE]");
                }
            }
        }
        return builder.toString();
    }
}

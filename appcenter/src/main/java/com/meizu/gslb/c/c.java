package com.meizu.gslb.c;

import android.content.Context;
import android.net.SSLCertificateSocketFactory;
import android.net.SSLSessionCache;
import android.os.Looper;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

public final class c implements HttpClient {
    public static long a = 256;
    private static final HttpRequestInterceptor b = new HttpRequestInterceptor() {
        public void process(HttpRequest httpRequest, HttpContext httpContext) {
            if (Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper()) {
                throw new RuntimeException("This thread forbids HTTP requests");
            }
        }
    };
    private final HttpClient c;
    private RuntimeException d = new IllegalStateException("MAndroidHttpClient created and never closed");
    private volatile b e;

    private class a implements HttpRequestInterceptor {
        final /* synthetic */ c a;

        private a(c cVar) {
            this.a = cVar;
        }

        public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
            b a = this.a.e;
            if (a != null && a.a() && (httpRequest instanceof HttpUriRequest)) {
                a.a(c.b((HttpUriRequest) httpRequest, false));
            }
        }
    }

    private static class b {
        private final String a;
        private final int b;

        private void a(String str) {
            Log.println(this.b, this.a, str);
        }

        private boolean a() {
            return Log.isLoggable(this.a, this.b);
        }
    }

    private c(ClientConnectionManager clientConnectionManager, HttpParams httpParams) {
        this.c = new DefaultHttpClient(this, clientConnectionManager, httpParams) {
            final /* synthetic */ c a;

            protected HttpContext createHttpContext() {
                HttpContext basicHttpContext = new BasicHttpContext();
                basicHttpContext.setAttribute("http.authscheme-registry", getAuthSchemes());
                basicHttpContext.setAttribute("http.cookiespec-registry", getCookieSpecs());
                basicHttpContext.setAttribute("http.auth.credentials-provider", getCredentialsProvider());
                return basicHttpContext;
            }

            protected BasicHttpProcessor createHttpProcessor() {
                BasicHttpProcessor createHttpProcessor = super.createHttpProcessor();
                createHttpProcessor.addRequestInterceptor(c.b);
                createHttpProcessor.addRequestInterceptor(new a());
                return createHttpProcessor;
            }
        };
    }

    public static c a(String str, int i, boolean z) {
        return a(str, null, i, z, null);
    }

    public static c a(String str, Context context, int i, boolean z, SSLSocketFactory sSLSocketFactory) {
        SSLSocketFactory bVar;
        SocketFactory httpSocketFactory;
        SSLSessionCache sSLSessionCache = null;
        HttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setStaleCheckingEnabled(basicHttpParams, false);
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, i);
        HttpConnectionParams.setSoTimeout(basicHttpParams, i);
        HttpConnectionParams.setSocketBufferSize(basicHttpParams, 8192);
        HttpClientParams.setRedirecting(basicHttpParams, true);
        HttpProtocolParams.setUserAgent(basicHttpParams, str);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        if (z) {
            try {
                KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
                instance.load(null, null);
                bVar = new b(instance);
                bVar.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (bVar == null) {
                Object obj = bVar;
            } else if (sSLSocketFactory == null) {
                if (context != null) {
                    sSLSessionCache = new SSLSessionCache(context);
                }
                httpSocketFactory = SSLCertificateSocketFactory.getHttpSocketFactory(30000, sSLSessionCache);
            }
            schemeRegistry.register(new Scheme("https", httpSocketFactory, 443));
            return new c(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
        }
        bVar = null;
        if (bVar == null) {
            Object obj2 = bVar;
        } else if (sSLSocketFactory == null) {
            if (context != null) {
                sSLSessionCache = new SSLSessionCache(context);
            }
            httpSocketFactory = SSLCertificateSocketFactory.getHttpSocketFactory(30000, sSLSessionCache);
        }
        schemeRegistry.register(new Scheme("https", httpSocketFactory, 443));
        return new c(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
    }

    private static String b(HttpUriRequest httpUriRequest, boolean z) throws IOException {
        Object uri;
        HttpEntity entity;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("curl ");
        for (Header header : httpUriRequest.getAllHeaders()) {
            if (z || !(header.getName().equals("Authorization") || header.getName().equals("Cookie"))) {
                stringBuilder.append("--header \"");
                stringBuilder.append(header.toString().trim());
                stringBuilder.append("\" ");
            }
        }
        URI uri2 = httpUriRequest.getURI();
        if (httpUriRequest instanceof RequestWrapper) {
            HttpRequest original = ((RequestWrapper) httpUriRequest).getOriginal();
            if (original instanceof HttpUriRequest) {
                uri = ((HttpUriRequest) original).getURI();
                stringBuilder.append("\"");
                stringBuilder.append(uri);
                stringBuilder.append("\"");
                if (httpUriRequest instanceof HttpEntityEnclosingRequest) {
                    entity = ((HttpEntityEnclosingRequest) httpUriRequest).getEntity();
                    if (entity != null && entity.isRepeatable()) {
                        if (entity.getContentLength() >= 1024) {
                            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            entity.writeTo(byteArrayOutputStream);
                            stringBuilder.append(" --data-ascii \"").append(byteArrayOutputStream.toString()).append("\"");
                        } else {
                            stringBuilder.append(" [TOO MUCH DATA TO INCLUDE]");
                        }
                    }
                }
                return stringBuilder.toString();
            }
        }
        URI uri3 = uri2;
        stringBuilder.append("\"");
        stringBuilder.append(uri);
        stringBuilder.append("\"");
        if (httpUriRequest instanceof HttpEntityEnclosingRequest) {
            entity = ((HttpEntityEnclosingRequest) httpUriRequest).getEntity();
            if (entity.getContentLength() >= 1024) {
                stringBuilder.append(" [TOO MUCH DATA TO INCLUDE]");
            } else {
                OutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                entity.writeTo(byteArrayOutputStream2);
                stringBuilder.append(" --data-ascii \"").append(byteArrayOutputStream2.toString()).append("\"");
            }
        }
        return stringBuilder.toString();
    }

    public void a() {
        if (this.d != null) {
            getConnectionManager().shutdown();
            this.d = null;
        }
    }

    public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        return this.c.execute(httpHost, httpRequest, responseHandler);
    }

    public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws IOException, ClientProtocolException {
        return this.c.execute(httpHost, httpRequest, responseHandler, httpContext);
    }

    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        return this.c.execute(httpUriRequest, responseHandler);
    }

    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws IOException, ClientProtocolException {
        return this.c.execute(httpUriRequest, responseHandler, httpContext);
    }

    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest) throws IOException {
        return this.c.execute(httpHost, httpRequest);
    }

    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws IOException {
        return this.c.execute(httpHost, httpRequest, httpContext);
    }

    public HttpResponse execute(HttpUriRequest httpUriRequest) throws IOException {
        return this.c.execute(httpUriRequest);
    }

    public HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext) throws IOException {
        return this.c.execute(httpUriRequest, httpContext);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (this.d != null) {
            Log.e("MAndroidHttpClient", "Leak found", this.d);
            this.d = null;
        }
    }

    public ClientConnectionManager getConnectionManager() {
        return this.c.getConnectionManager();
    }

    public HttpParams getParams() {
        return this.c.getParams();
    }
}

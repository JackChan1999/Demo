package com.meizu.e;

import android.content.Context;
import android.util.Log;
import com.meizu.c.e;
import com.meizu.c.f;
import com.meizu.cloud.app.request.RequestManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.SocketFactory;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class b {
    private Context a;
    private String b = null;
    private Map<String, String> c = new HashMap();
    private h d = null;
    private i e = null;

    public b(Context context) {
        this.a = context.getApplicationContext();
        d("MEIZU");
    }

    public k a(String url, RequeseParams[] postParameters, Map<String, String> headers, boolean authenticated) throws f, e {
        return a(url, postParameters, (Map) headers, authenticated, 1, 30000);
    }

    private k a(String url, RequeseParams[] postParams, Map<String, String> headers, boolean authenticated, int retryCount, int timeOut) throws f, e {
        k response = null;
        int retriedCount = 0;
        while (retriedCount <= retryCount) {
            boolean lastTry = retriedCount == retryCount;
            if (a() && b(url)) {
                response = a(url, postParams, headers, authenticated, lastTry);
            } else {
                response = a(url, postParams, (Map) headers, authenticated, timeOut, lastTry);
            }
            if (response != null) {
                break;
            }
            retriedCount++;
        }
        return response;
    }

    private boolean b(String url) {
        return url != null && url.startsWith("https");
    }

    private k a(String url, RequeseParams[] postParams, Map<String, String> headers, boolean authenticated, boolean throwResponseError) throws f, e {
        Socket sockWap = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            String helpUrl;
            String host = b();
            int port = c();
            boolean shortUrl = false;
            if (host != null) {
                sockWap = SocketFactory.getDefault().createSocket(host, port);
            } else {
                host = a(url)[0];
                if (b(url)) {
                    sockWap = SSLSocketFactory.getDefault().createSocket(host, 443);
                } else {
                    sockWap = SocketFactory.getDefault().createSocket(host, 80);
                }
                shortUrl = true;
            }
            sockWap.setSoTimeout(50000);
            out = sockWap.getOutputStream();
            in = sockWap.getInputStream();
            String authorizationString = null;
            if (authenticated) {
                authorizationString = a(url, false, postParams);
            }
            if (shortUrl) {
                helpUrl = url.substring(url.indexOf(host) + host.length());
            } else {
                helpUrl = url;
            }
            if (postParams != null) {
                helpUrl = (helpUrl + "?") + a(postParams);
            }
            String outString = (((("GET " + helpUrl) + " HTTP/1.0\r\nHost: ") + host) + "\r\n") + "Connection: Close\r\n";
            for (BasicNameValuePair value : c(url)) {
                outString = outString + value.getName() + ": " + value.getValue() + "\r\n";
            }
            for (String key : this.c.keySet()) {
                outString = outString + key + ": " + ((String) this.c.get(key)) + "\r\n";
            }
            if (headers != null && headers.size() > 0) {
                for (Entry<String, String> keyValue : headers.entrySet()) {
                    outString = outString + ((String) keyValue.getKey()) + ": " + ((String) keyValue.getValue()) + "\r\n";
                }
            }
            if (authorizationString != null) {
                outString = outString + "Authorization: " + authorizationString + "\r\n";
            }
            out.write((outString + "\r\n").getBytes("UTF-8"));
            out.flush();
            k a = a(in);
            if (a.a() != 200) {
                if (a.a() == 401) {
                    Log.w("GameCenterService", "catch Token invalid response code:401");
                    throw new e();
                } else if (a.a() == 403) {
                    Log.w("GameCenterService", "catch Token invalid response code:403");
                    throw new f(a.b(), a.a());
                } else if (throwResponseError) {
                    throw new f(a.b(), a.a());
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignore) {
                    ignore.printStackTrace();
                    return a;
                }
            }
            if (in != null) {
                in.close();
            }
            if (sockWap == null) {
                return a;
            }
            sockWap.close();
            return a;
        } catch (f e) {
            throw e;
        } catch (e e2) {
            throw e2;
        } catch (Exception e3) {
            e3.printStackTrace();
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignore2) {
                    ignore2.printStackTrace();
                    return null;
                }
            }
            if (in != null) {
                in.close();
            }
            if (sockWap != null) {
                sockWap.close();
            }
            return null;
        } catch (Throwable th) {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignore22) {
                    ignore22.printStackTrace();
                }
            }
            if (in != null) {
                in.close();
            }
            if (sockWap != null) {
                sockWap.close();
            }
        }
    }

    private k a(InputStream in) throws IOException {
        int statusCode = 0;
        try {
            String[] statusArray = c(in).split(" ");
            if (statusArray.length == 3) {
                statusCode = Integer.valueOf(statusArray[1]).intValue();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Map<String, String> headers = b(in);
        int contentLength = 0;
        try {
            if (headers.get("Content-Length") != null) {
                contentLength = Integer.valueOf((String) headers.get("Content-Length")).intValue();
            }
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
        }
        byte[] body = a(in, contentLength);
        String charset = (String) headers.get("Content-Type");
        if (charset.matches(".+;charset=.+")) {
            charset = charset.split(";")[1].split("=")[1];
        } else {
            charset = "UTF-8";
        }
        return new k(new String(body, charset), statusCode);
    }

    private byte[] a(InputStream in, int contentLength) throws IOException {
        ByteArrayOutputStream buff = new ByteArrayOutputStream(contentLength);
        byte[] byteBuf = new byte[1024];
        int count = 0;
        int read = 0;
        while (count < contentLength && read != -1) {
            read = in.read(byteBuf);
            buff.write(byteBuf, 0, read);
            count += read;
        }
        return buff.toByteArray();
    }

    private Map<String, String> b(InputStream in) throws IOException {
        Map<String, String> headers = new HashMap();
        while (true) {
            String line = d(in);
            if ("".equals(line)) {
                return headers;
            }
            String[] nv = line.split(":");
            headers.put(nv[0].trim(), nv[1].trim());
        }
    }

    private String c(InputStream in) throws IOException {
        return d(in);
    }

    private String d(InputStream in) throws IOException {
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        int b = in.read();
        while (b != 13 && b != -1) {
            buff.write(b);
            b = in.read();
        }
        in.read();
        return buff.toString("UTF-8");
    }

    private k a(String url, RequeseParams[] postParams, Map<String, String> headers, boolean authenticated, int timeOut, boolean throwResponseError) throws f, e {
        d client = d.a("MEIZU", timeOut, true);
        String converHost = null;
        try {
            String converUrl;
            HttpUriRequest httpRequest;
            if (a()) {
                String[] result = a(url);
                converUrl = result[1];
                converHost = result[0];
            } else {
                converUrl = url;
            }
            if (postParams != null) {
                HttpPost httpPost = new HttpPost(converUrl);
                List<NameValuePair> par = new ArrayList();
                for (int i = 0; i < postParams.length; i++) {
                    par.add(new BasicNameValuePair(postParams[i].name, postParams[i].value));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(par, "UTF-8"));
                httpRequest = httpPost;
            } else {
                Object httpRequest2 = new HttpGet(converUrl);
            }
            if (converHost != null) {
                httpRequest.setHeader("X-Online-Host", converHost);
            }
            if (authenticated) {
                httpRequest.setHeader("Authorization", a(url, postParams != null, postParams));
            }
            for (String key : this.c.keySet()) {
                httpRequest.setHeader(key, (String) this.c.get(key));
            }
            for (BasicNameValuePair value : c(url)) {
                httpRequest.setHeader(value.getName(), value.getValue());
            }
            httpRequest.setHeader("Accept-Encoding", "gzip");
            if (headers != null && headers.size() > 0) {
                for (Entry<String, String> keyValue : headers.entrySet()) {
                    httpRequest.setHeader((String) keyValue.getKey(), (String) keyValue.getValue());
                }
            }
            HttpResponse response = client.execute(httpRequest);
            if (response.getStatusLine().getStatusCode() == 200) {
                String str;
                HttpEntity entity = response.getEntity();
                InputStream stream = entity.getContent();
                int statusCode = response.getStatusLine().getStatusCode();
                if (entity.getContentEncoding() == null) {
                    str = "";
                } else {
                    str = entity.getContentEncoding().getValue();
                }
                k kVar = new k(statusCode, stream, str);
                if (client == null) {
                    return kVar;
                }
                client.a();
                return kVar;
            } else if (response.getStatusLine().getStatusCode() == 401) {
                Log.w("GameCenterService", "catch Token invalid response code:401");
                throw new e();
            } else if (response.getStatusLine().getStatusCode() == 403) {
                Log.w("GameCenterService", "catch Token invalid response code:403");
                throw new f(URLDecoder.decode(EntityUtils.toString(response.getEntity()), "UTF-8"), response.getStatusLine().getStatusCode());
            } else {
                String errMsg = URLDecoder.decode(EntityUtils.toString(response.getEntity()), "UTF-8");
                int responseCode = response.getStatusLine().getStatusCode();
                e("http request error : " + responseCode + "; " + errMsg);
                if (throwResponseError) {
                    throw new f(errMsg, responseCode);
                }
                if (client != null) {
                    client.a();
                }
                return null;
            }
        } catch (SSLHandshakeException e) {
            Log.w("GameCenterService", "catch SSLHandshakeException...");
            throw new f(e, 0, -100);
        } catch (f e2) {
            throw e2;
        } catch (e e3) {
            throw e3;
        } catch (Exception e4) {
            e4.printStackTrace();
            if (client != null) {
                client.a();
            }
        } catch (Throwable th) {
            if (client != null) {
                client.a();
            }
        }
    }

    private BasicNameValuePair[] c(String url) {
        return new BasicNameValuePair[]{new BasicNameValuePair(RequestManager.HEAD_ACCEPT_LANGUAGE, c.a()), new BasicNameValuePair("netType", c.a(this.a))};
    }

    private void d(String ua) {
        a("MEIZU_UA", ua);
    }

    private void a(String name, String value) {
        this.c.put(name, value);
    }

    public static boolean a() {
        return g.a();
    }

    public static String b() {
        return g.b();
    }

    public static int c() {
        return g.c();
    }

    public static final String[] a(String url) {
        try {
            String signFirst = "://";
            int nPosFirst = url.indexOf(signFirst) + signFirst.length();
            int nPosSecond = url.indexOf("/", nPosFirst);
            String host = url.substring(nPosFirst, nPosSecond);
            String strUrl = (url.substring(0, nPosFirst) + b()) + url.substring(nPosSecond);
            return new String[]{host, strUrl};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{url, url};
        }
    }

    public static String a(RequeseParams[] postParams) {
        StringBuffer buf = new StringBuffer();
        for (int j = 0; j < postParams.length; j++) {
            if (j != 0) {
                buf.append("&");
            }
            try {
                buf.append(URLEncoder.encode(postParams[j].name, "UTF-8")).append("=").append(URLEncoder.encode(postParams[j].value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
            }
        }
        return buf.toString();
    }

    public String a(String url, boolean bPost, RequeseParams[] params) {
        if (this.d != null) {
            return this.d.a(bPost ? "POST" : "GET", url, params, this.e);
        } else if (this.b != null) {
            return this.b;
        } else {
            return null;
        }
    }

    private static void e(String message) {
        Log.d("GameCenterService", "[" + new Date() + "] " + message);
    }
}

package com.meizu.statsapp.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class a {
    private static final String a = a.class.getSimpleName();
    private static b b = new b();
    private static a c = new a();
    private static String d = "******";

    static class a implements HostnameVerifier {
        a() {
        }

        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    static class b implements X509TrustManager {
        b() {
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    static {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new X509TrustManager[]{b}, new SecureRandom());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        if (sslContext != null) {
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        }
        HttpsURLConnection.setDefaultHostnameVerifier(c);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static String a(String r16, com.meizu.statsapp.a.a.b[] r17) {
        /*
        r10 = "";
        r3 = new com.meizu.statsapp.a.c;
        r3.<init>();
        r11 = new java.lang.StringBuffer;	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r11.<init>();	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r2 = new java.io.ByteArrayOutputStream;	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r2.<init>();	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r12 = d;	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r12 = r12.getBytes();	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r0 = r17;
        com.meizu.statsapp.a.a.cancel.a(r2, r0, r12);	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r1 = new java.io.ByteArrayInputStream;	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r12 = r2.toByteArray();	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r1.<init>(r12);	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r12 = a(r1);	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r11.append(r12);	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r9 = new java.util.ArrayList;	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r9.<init>();	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r12 = new android.util.Pair;	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r13 = "Content-Type";
        r14 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r14.<init>();	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r15 = "multipart/form-data;boundary=";
        r14 = r14.append(r15);	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r15 = d;	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r14 = r14.append(r15);	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r14 = r14.toString();	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r12.<init>(r13, r14);	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r9.add(r12);	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r8 = new com.meizu.statsapp.a.d;	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r12 = 0;
        r13 = r11.toString();	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r0 = r16;
        r8.<init>(r0, r12, r9, r13);	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r12 = r3.cancel(r8);	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r12 = (com.meizu.statsapp.a.e) r12;	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r4 = r12.cancel();	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r12 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r12.<init>();	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r13 = "response code:";
        r12 = r12.append(r13);	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r13 = r4.getResponseCode();	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r12 = r12.append(r13);	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r12 = r12.toString();	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        com.meizu.gslb.g.a.d(r12);	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r7 = r4.getErrorStream();	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r6 = r4.getInputStream();	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        if (r7 == 0) goto L_0x0095;
    L_0x008a:
        r10 = a(r7);	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r7.close();	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
    L_0x0091:
        r3.a();
    L_0x0094:
        return r10;
    L_0x0095:
        if (r6 == 0) goto L_0x0091;
    L_0x0097:
        r10 = a(r6);	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        r6.close();	 Catch:{ IOException -> 0x009f, cancel -> 0x00a7 }
        goto L_0x0091;
    L_0x009f:
        r5 = move-exception;
        r5.printStackTrace();	 Catch:{ all -> 0x00af }
        r3.a();
        goto L_0x0094;
    L_0x00a7:
        r5 = move-exception;
        r5.printStackTrace();	 Catch:{ all -> 0x00af }
        r3.a();
        goto L_0x0094;
    L_0x00af:
        r12 = move-exception;
        r3.a();
        throw r12;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.statsapp.util.a.a(java.lang.String, com.meizu.statsapp.a.a.cancel[]):java.lang.String");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static String a(String r10, Map<String, String> r11) {
        /*
        r6 = "";
        r0 = new com.meizu.statsapp.a.c;
        r0.<init>();
        r7 = new java.lang.StringBuffer;	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        r7.<init>();	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        r8 = "UTF-8";
        r8 = a(r11, r8);	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        r7.append(r8);	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        r5 = new com.meizu.statsapp.a.d;	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        r8 = 0;
        r9 = r7.toString();	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        r5.<init>(r10, r8, r9);	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        r8 = r0.cancel(r5);	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        r8 = (com.meizu.statsapp.a.e) r8;	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        r1 = r8.cancel();	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        r8 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        r8.<init>();	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        r9 = "response code:";
        r8 = r8.append(r9);	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        r9 = r1.getResponseCode();	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        r8 = r8.append(r9);	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        r8 = r8.toString();	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        com.meizu.gslb.g.a.d(r8);	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        r4 = r1.getErrorStream();	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        r3 = r1.getInputStream();	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        if (r4 == 0) goto L_0x0058;
    L_0x004d:
        r6 = a(r4);	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        r4.close();	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
    L_0x0054:
        r0.a();
    L_0x0057:
        return r6;
    L_0x0058:
        if (r3 == 0) goto L_0x0054;
    L_0x005a:
        r6 = a(r3);	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        r3.close();	 Catch:{ IOException -> 0x0062, cancel -> 0x006a }
        goto L_0x0054;
    L_0x0062:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ all -> 0x0072 }
        r0.a();
        goto L_0x0057;
    L_0x006a:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ all -> 0x0072 }
        r0.a();
        goto L_0x0057;
    L_0x0072:
        r8 = move-exception;
        r0.a();
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.statsapp.util.a.a(java.lang.String, java.util.Map):java.lang.String");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static String a(String r10, org.json.JSONObject r11) {
        /*
        r6 = "";
        r0 = new com.meizu.statsapp.a.c;
        r0.<init>();
        r7 = new java.lang.StringBuffer;	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        r7.<init>();	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        r8 = r11.toString();	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        r7.append(r8);	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        r5 = new com.meizu.statsapp.a.d;	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        r8 = 0;
        r9 = r7.toString();	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        r5.<init>(r10, r8, r9);	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        r8 = r0.cancel(r5);	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        r8 = (com.meizu.statsapp.a.e) r8;	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        r1 = r8.cancel();	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        r8 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        r8.<init>();	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        r9 = "response code:";
        r8 = r8.append(r9);	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        r9 = r1.getResponseCode();	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        r8 = r8.append(r9);	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        r8 = r8.toString();	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        com.meizu.gslb.g.a.d(r8);	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        r4 = r1.getErrorStream();	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        r3 = r1.getInputStream();	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        if (r4 == 0) goto L_0x0056;
    L_0x004b:
        r6 = a(r4);	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        r4.close();	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
    L_0x0052:
        r0.a();
    L_0x0055:
        return r6;
    L_0x0056:
        if (r3 == 0) goto L_0x0052;
    L_0x0058:
        r6 = a(r3);	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        r3.close();	 Catch:{ IOException -> 0x0060, cancel -> 0x0068 }
        goto L_0x0052;
    L_0x0060:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ all -> 0x0070 }
        r0.a();
        goto L_0x0055;
    L_0x0068:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ all -> 0x0070 }
        r0.a();
        goto L_0x0055;
    L_0x0070:
        r8 = move-exception;
        r0.a();
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.statsapp.util.a.a(java.lang.String, org.json.JSONObject):java.lang.String");
    }

    public static String a(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (true) {
            int i = is.read();
            if (i == -1) {
                return baos.toString();
            }
            baos.write(i);
        }
    }

    private static String a(Map<String, String> parameters, String encoding) {
        StringBuilder result = new StringBuilder();
        for (Entry<String, String> entry : parameters.entrySet()) {
            String encodedName = a((String) entry.getKey(), encoding);
            String value = (String) entry.getValue();
            String encodedValue = value != null ? a(value, encoding) : "";
            if (result.length() > 0) {
                result.append("&");
            }
            result.append(encodedName);
            result.append("=");
            result.append(encodedValue);
        }
        return result.toString();
    }

    private static String a(String content, String encoding) {
        if (encoding == null) {
            encoding = "ISO-8859-1";
        }
        try {
            return URLEncoder.encode(content, encoding);
        } catch (UnsupportedEncodingException problem) {
            throw new IllegalArgumentException(problem);
        }
    }
}

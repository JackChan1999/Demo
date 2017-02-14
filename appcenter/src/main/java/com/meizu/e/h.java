package com.meizu.e;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class h implements Serializable {
    private static final RequeseParams a = new RequeseParams("oauth_signature_method", "MD5");
    private static Random d = new Random();
    static final long serialVersionUID = -4368426677157998618L;
    private String b;
    private String c;

    public String a(String method, String url, RequeseParams[] params, String nonce, String timestamp, i otoken) {
        if (params == null) {
            params = new RequeseParams[0];
        }
        List<RequeseParams> oauthHeaderParams = new ArrayList(5);
        oauthHeaderParams.add(new RequeseParams("oauth_consumer_key", this.b));
        oauthHeaderParams.add(a);
        oauthHeaderParams.add(new RequeseParams("oauth_timestamp", timestamp));
        oauthHeaderParams.add(new RequeseParams("oauth_nonce", nonce));
        oauthHeaderParams.add(new RequeseParams("oauth_version", "1.0"));
        if (otoken != null) {
            oauthHeaderParams.add(new RequeseParams("oauth_token", otoken.a()));
        }
        List signatureBaseParams = new ArrayList(oauthHeaderParams.size() + params.length);
        signatureBaseParams.addAll(oauthHeaderParams);
        signatureBaseParams.addAll(a(params));
        a(url, signatureBaseParams);
        StringBuffer base = new StringBuffer(method).append("&").append(a(b(url))).append("&");
        base.append(a(a(signatureBaseParams)));
        String oauthBaseString = base.toString();
        a("OAuth base string:", oauthBaseString);
        String signature = a(oauthBaseString, otoken);
        a("OAuth signature:", signature);
        oauthHeaderParams.add(new RequeseParams("oauth_signature", signature));
        return "OAuth " + a(oauthHeaderParams, ",", true);
    }

    public String a(String method, String url, RequeseParams[] params, i token) {
        long timestamp = System.currentTimeMillis() / 1000;
        return a(method, url, params, String.valueOf(timestamp + ((long) d.nextInt())), String.valueOf(timestamp), token);
    }

    public static List<RequeseParams> a(RequeseParams[] params) {
        List<RequeseParams> paramList = new ArrayList(params.length);
        paramList.addAll(Arrays.asList(params));
        return paramList;
    }

    public static String a(String value) {
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        StringBuffer buf = new StringBuffer(encoded.length());
        int i = 0;
        while (i < encoded.length()) {
            char focus = encoded.charAt(i);
            if (focus == '*') {
                buf.append("%2A");
            } else if (focus == '+') {
                buf.append("%20");
            } else if (focus == '%' && i + 1 < encoded.length() && encoded.charAt(i + 1) == '7' && encoded.charAt(i + 2) == 'E') {
                buf.append('~');
                i += 2;
            } else {
                buf.append(focus);
            }
            i++;
        }
        return buf.toString();
    }

    private void a(String url, List<RequeseParams> signatureBaseParams) {
        int queryStart = url.indexOf("?");
        if (-1 != queryStart) {
            try {
                for (String query : url.substring(queryStart + 1).split("&")) {
                    String[] split = query.split("=");
                    if (split.length == 2) {
                        signatureBaseParams.add(new RequeseParams(URLDecoder.decode(split[0], "UTF-8"), URLDecoder.decode(split[1], "UTF-8")));
                    } else {
                        signatureBaseParams.add(new RequeseParams(URLDecoder.decode(split[0], "UTF-8"), ""));
                    }
                }
            } catch (UnsupportedEncodingException e) {
            } catch (Exception e2) {
            }
        }
    }

    public static String a(List<RequeseParams> params) {
        Collections.sort(params);
        return b((List) params);
    }

    public static String b(List<RequeseParams> postParams) {
        return a(postParams, "&", false);
    }

    String a(String data, i token) {
        if (token == null) {
            data = data + ("&" + a(this.c) + "&");
        } else {
            data = data + ("&" + a(this.c) + "&" + a(token.b()));
        }
        return new a().a(e.b(data.getBytes()));
    }

    public static String a(List<RequeseParams> postParams, String splitter, boolean quot) {
        StringBuffer buf = new StringBuffer();
        for (RequeseParams param : postParams) {
            if (buf.length() != 0) {
                if (quot) {
                    buf.append("\"");
                }
                buf.append(splitter);
            }
            buf.append(a(param.name)).append("=");
            if (quot) {
                buf.append("\"");
            }
            buf.append(a(param.value));
        }
        if (buf.length() != 0 && quot) {
            buf.append("\"");
        }
        return buf.toString();
    }

    public static String b(String url) {
        int index = url.indexOf("?");
        if (-1 != index) {
            url = url.substring(0, index);
        }
        int slashIndex = url.indexOf("/", 8);
        String baseURL = url.substring(0, slashIndex).toLowerCase();
        int colonIndex = baseURL.indexOf(":", 8);
        if (-1 != colonIndex) {
            if (baseURL.startsWith("http://") && baseURL.endsWith(":80")) {
                baseURL = baseURL.substring(0, colonIndex);
            } else if (baseURL.startsWith("https://") && baseURL.endsWith(":443")) {
                baseURL = baseURL.substring(0, colonIndex);
            }
        }
        return baseURL + url.substring(slashIndex);
    }

    private void a(String message, String message2) {
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof h)) {
            return false;
        }
        h oAuth = (h) o;
        if (this.b == null ? oAuth.b != null : !this.b.equals(oAuth.b)) {
            return false;
        }
        if (this.c != null) {
            if (this.c.equals(oAuth.c)) {
                return true;
            }
        } else if (oAuth.c == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int i = 0;
        if (this.b != null) {
            result = this.b.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.c != null) {
            i = this.c.hashCode();
        }
        return i2 + i;
    }

    public String toString() {
        return "OAuth{consumerKey='" + this.b + '\'' + ", consumerSecret='" + this.c + '\'' + '}';
    }
}

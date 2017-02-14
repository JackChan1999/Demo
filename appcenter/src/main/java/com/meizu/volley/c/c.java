package com.meizu.volley.c;

import android.util.Log;
import com.android.volley.i;
import com.android.volley.k;
import com.android.volley.l;
import com.android.volley.n;
import com.android.volley.n.a;
import com.android.volley.n.b;
import com.android.volley.toolbox.d;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class c<T> extends l<T> {
    private b<T> mListener;
    protected Map<String, String> mParamMap;

    protected abstract T parseResponse(String str) throws k;

    public c(String url, b<T> listener, a errorListener) {
        this(0, url, null, listener, errorListener);
    }

    public c(String url, List<com.meizu.volley.b.a> params, b<T> listener, a errorListener) {
        this(1, url, params, listener, errorListener);
    }

    public c(int method, String url, List<com.meizu.volley.b.a> params, b<T> listener, a errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
        generateParamMap(params);
        setShouldCache(false);
    }

    private void generateParamMap(List<com.meizu.volley.b.a> params) {
        if (params != null && params.size() > 0) {
            this.mParamMap = new HashMap();
            for (com.meizu.volley.b.a pair : params) {
                this.mParamMap.put(pair.getName(), pair.getValue());
            }
        }
    }

    public void cancel() {
        this.mListener = null;
        super.cancel();
    }

    protected n<T> parseNetworkResponse(i response) {
        String parsed = null;
        if (response.b != null) {
            try {
                parsed = new String(response.b, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                parsed = new String(response.b);
            }
        }
        try {
            return n.a(parseResponse(parsed), d.a(response));
        } catch (k e2) {
            return n.a(e2);
        }
    }

    protected void deliverResponse(T response) {
        this.mListener.a(response);
    }

    protected Map<String, String> getParams() throws com.android.volley.a {
        return this.mParamMap;
    }

    public String getUrl() {
        if (getMethod() != 0) {
            return super.getUrl();
        }
        String url = super.getUrl();
        try {
            return encodedUrl(super.getUrl(), getParams(), getParamsEncoding());
        } catch (com.android.volley.a e) {
            e.printStackTrace();
            return url;
        } catch (Exception e2) {
            e2.printStackTrace();
            return url;
        }
    }

    public static String encodedUrl(String url, Map<String, String> params, String paramsEncoding) {
        if (url == null || params == null || params.size() <= 0) {
            return url;
        }
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode((String) entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode((String) entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            if (!url.contains("?")) {
                return url + "?" + encodedParams.toString().substring(0, encodedParams.length() - 1);
            }
            Log.w("Encode url", "url \"" + url + "\" has been encode ?");
            return url + "&" + encodedParams.toString().substring(0, encodedParams.length() - 1);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }
}

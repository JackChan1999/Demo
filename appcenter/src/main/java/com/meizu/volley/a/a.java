package com.meizu.volley.a;

import android.content.Context;
import android.text.TextUtils;
import com.android.volley.n.b;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class a<T> extends com.meizu.volley.a<T> implements com.meizu.volley.c.a {
    private Context mContext;
    private String mTokenKey = "access_token";

    public a(Context mContext, int method, String url, List<com.meizu.volley.b.a> params, b<T> listener, com.android.volley.n.a errorListener) {
        super(method, url, params, listener, errorListener);
        setRetryPolicy(new com.meizu.volley.c.b(this));
        if (mContext != null) {
            this.mContext = mContext.getApplicationContext();
        }
    }

    public void cancel() {
        this.mContext = null;
        super.cancel();
    }

    protected Map<String, String> getParams() throws com.android.volley.a {
        if (this.mParamMap == null) {
            this.mParamMap = new HashMap();
        }
        String token = getAccountToken(false);
        if (TextUtils.isEmpty(token)) {
            throw new com.android.volley.a();
        }
        this.mParamMap.put(this.mTokenKey, token);
        return super.getParams();
    }

    public boolean reLoadToken() {
        String token = getAccountToken(true);
        if (token == null) {
            return false;
        }
        resetParam(this.mTokenKey, token);
        return true;
    }

    private String getAccountToken(boolean invalidateToken) {
        if (this.mContext != null) {
            String newToken = com.meizu.cloud.a.b.a(this.mContext, invalidateToken);
            if (!TextUtils.isEmpty(newToken)) {
                return newToken;
            }
        }
        return null;
    }

    protected void resetParam(String key, String value) {
        if (this.mParamMap != null) {
            this.mParamMap.put(key, value);
        }
    }
}

package com.meizu.volley;

import android.text.TextUtils;
import com.android.volley.n.b;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.cloud.app.utils.d;
import com.meizu.volley.c.c;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class a<T> extends c<T> {
    private d mParamProvider;

    protected a(String url, b<T> listener, com.android.volley.n.a errorListener) {
        super(url, listener, errorListener);
    }

    protected a(String url, List<com.meizu.volley.b.a> params, b<T> listener, com.android.volley.n.a errorListener) {
        super(url, params, listener, errorListener);
    }

    protected a(int method, String url, List<com.meizu.volley.b.a> params, b<T> listener, com.android.volley.n.a errorListener) {
        super(method, url, params, listener, errorListener);
        addHeader(RequestManager.HEAD_ACCEPT_LANGUAGE, d.d());
    }

    public void setParamProvider(d provider) {
        this.mParamProvider = provider;
    }

    protected Map<String, String> getParams() throws com.android.volley.a {
        if (this.mParamProvider != null) {
            if (this.mParamMap == null) {
                this.mParamMap = new HashMap();
            }
            List<com.meizu.volley.b.a> params = this.mParamProvider.b();
            if (params != null && params.size() > 0) {
                for (com.meizu.volley.b.a param : params) {
                    this.mParamMap.put(param.getName(), param.getValue());
                }
            }
            if (!this.mParamProvider.g()) {
                this.mParamProvider = null;
            }
        }
        filterNullParams();
        return super.getParams();
    }

    protected void filterNullParams() {
        for (Entry<String, String> entry : this.mParamMap.entrySet()) {
            if (TextUtils.isEmpty((CharSequence) entry.getValue())) {
                entry.setValue("");
            }
        }
    }
}

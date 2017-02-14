package com.meizu.volley.a;

import android.content.Context;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.k;
import com.android.volley.n.a;
import com.android.volley.n.b;
import com.meizu.cloud.app.request.JSONUtils;
import java.util.List;

public class c extends a<String> {
    private TypeReference<String> a;

    protected /* synthetic */ Object parseResponse(String str) throws k {
        return a(str);
    }

    public c(Context context, TypeReference<String> typeReference, String url, List params, b listener, a errorListener) {
        this(context, typeReference, 1, url, params, listener, errorListener);
    }

    public c(Context context, TypeReference<String> typeReference, int method, String url, List params, b listener, a errorListener) {
        super(context, method, url, params, listener, errorListener);
        this.a = typeReference;
    }

    public void cancel() {
        this.a = null;
        super.cancel();
    }

    protected String a(String response) throws k {
        if (this.a != null) {
            return (String) JSONUtils.parseJSONObject(response, this.a);
        }
        return null;
    }
}

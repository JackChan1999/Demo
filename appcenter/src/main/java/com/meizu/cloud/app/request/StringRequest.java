package com.meizu.cloud.app.request;

import com.alibaba.fastjson.TypeReference;
import com.android.volley.k;
import com.android.volley.n;
import com.android.volley.n.b;
import com.meizu.volley.a;
import java.util.List;

public class StringRequest extends a<String> {
    public StringRequest(TypeReference<String> typeReference, String url, b listener, n.a errorListener) {
        this(typeReference, 0, url, null, listener, errorListener);
    }

    public StringRequest(TypeReference<String> typeReference, String url, List params, b listener, n.a errorListener) {
        this(typeReference, 1, url, params, listener, errorListener);
    }

    public StringRequest(TypeReference<String> typeReference, int method, String url, List params, b listener, n.a errorListener) {
        super(method, url, params, listener, errorListener);
    }

    protected String parseResponse(String response) throws k {
        return response;
    }
}

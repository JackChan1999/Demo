package com.meizu.volley;

import com.alibaba.fastjson.TypeReference;
import com.android.volley.k;
import com.android.volley.n.a;
import com.android.volley.n.b;
import java.util.List;

public class e extends a<String> {
    protected /* synthetic */ Object parseResponse(String str) throws k {
        return a(str);
    }

    public e(TypeReference<String> typeReference, int method, String url, List params, b listener, a errorListener) {
        super(method, url, params, listener, errorListener);
    }

    protected String a(String response) throws k {
        return response;
    }
}

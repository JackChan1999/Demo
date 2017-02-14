package com.meizu.cloud.app.request;

import android.content.Context;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.k;
import com.android.volley.n;
import com.android.volley.n.b;
import com.meizu.volley.a.a;
import java.util.List;

public class StringAuthRequest extends a<String> {
    private TypeReference<String> mTypeReference;

    public StringAuthRequest(Context context, TypeReference<String> typeReference, String url, b listener, n.a errorListener) {
        this(context, typeReference, 0, url, null, listener, errorListener);
    }

    public StringAuthRequest(Context context, TypeReference<String> typeReference, String url, List params, b listener, n.a errorListener) {
        this(context, typeReference, 1, url, params, listener, errorListener);
    }

    public StringAuthRequest(Context context, TypeReference<String> typeReference, int method, String url, List params, b listener, n.a errorListener) {
        super(context, method, url, params, listener, errorListener);
        this.mTypeReference = typeReference;
    }

    public void cancel() {
        this.mTypeReference = null;
        super.cancel();
    }

    protected String parseResponse(String response) throws k {
        if (this.mTypeReference != null) {
            return (String) JSONUtils.parseJSONObject(response, this.mTypeReference);
        }
        return null;
    }
}

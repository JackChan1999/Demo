package com.meizu.cloud.app.request;

import com.alibaba.fastjson.TypeReference;
import com.android.volley.k;
import com.android.volley.n;
import com.android.volley.n.b;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.volley.a;
import java.util.List;

public class FastJsonRequest<T> extends a<ResultModel<T>> {
    private TypeReference<ResultModel<T>> mTypeReference;

    public FastJsonRequest(TypeReference<ResultModel<T>> typeReference, String url, b listener, n.a errorListener) {
        this((TypeReference) typeReference, 0, url, null, listener, errorListener);
    }

    public FastJsonRequest(TypeReference<ResultModel<T>> typeReference, String url, int method, List params, b listener, n.a errorListener) {
        this((TypeReference) typeReference, method, url, params, listener, errorListener);
    }

    public FastJsonRequest(TypeReference<ResultModel<T>> typeReference, String url, List params, b listener, n.a errorListener) {
        this((TypeReference) typeReference, 1, url, params, listener, errorListener);
    }

    public FastJsonRequest(TypeReference<ResultModel<T>> typeReference, int method, String url, List params, b listener, n.a errorListener) {
        super(method, url, params, listener, errorListener);
        this.mTypeReference = typeReference;
    }

    public void cancel() {
        this.mTypeReference = null;
        super.cancel();
    }

    protected ResultModel<T> parseResponse(String response) throws k {
        if (this.mTypeReference != null) {
            return JSONUtils.parseResultModel(response, this.mTypeReference);
        }
        return null;
    }
}

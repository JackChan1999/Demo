package com.meizu.volley.a;

import android.content.Context;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.k;
import com.android.volley.n;
import com.meizu.cloud.app.request.JSONUtils;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.volley.b.a;
import java.util.List;

public class b<T> extends a<ResultModel<T>> {
    private TypeReference<ResultModel<T>> a;

    protected /* synthetic */ Object parseResponse(String str) throws k {
        return a(str);
    }

    public b(Context mContext, TypeReference<ResultModel<T>> typeReference, int method, String url, List<a> params, com.android.volley.n.b<ResultModel<T>> listener, n.a errorListener) {
        super(mContext, method, url, params, listener, errorListener);
        this.a = typeReference;
    }

    public b(Context mContext, TypeReference<ResultModel<T>> typeReference, String url, List<a> params, com.android.volley.n.b<ResultModel<T>> listener, n.a errorListener) {
        this(mContext, typeReference, 1, url, params, listener, errorListener);
    }

    public void cancel() {
        this.a = null;
        super.cancel();
    }

    protected ResultModel<T> a(String response) throws k {
        return this.a != null ? JSONUtils.parseResultModel(response, this.a) : null;
    }
}

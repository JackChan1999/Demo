package com.meizu.cloud.app.request;

import android.content.Context;
import com.android.volley.k;
import com.android.volley.n;
import com.android.volley.n.b;
import com.meizu.volley.a.a;
import java.util.List;

public class FastJsonAuthParseRequest<T> extends a<T> {
    private ParseListener<T> mParseListener;

    public FastJsonAuthParseRequest(Context context, String url, ParseListener parseListener, b listener, n.a errorListener) {
        this(context, 0, url, null, parseListener, listener, errorListener);
    }

    public FastJsonAuthParseRequest(Context context, String url, int method, List params, ParseListener parseListener, b listener, n.a errorListener) {
        this(context, method, url, params, parseListener, listener, errorListener);
    }

    public FastJsonAuthParseRequest(Context context, String url, List params, ParseListener parseListener, b listener, n.a errorListener) {
        this(context, 1, url, params, parseListener, listener, errorListener);
    }

    public FastJsonAuthParseRequest(Context context, int method, String url, List params, ParseListener parseListener, b listener, n.a errorListener) {
        super(context, method, url, params, listener, errorListener);
        this.mParseListener = parseListener;
    }

    public void cancel() {
        this.mParseListener = null;
        super.cancel();
    }

    protected T parseResponse(String json) throws k {
        if (this.mParseListener != null) {
            return this.mParseListener.onParseResponse(json);
        }
        return null;
    }
}

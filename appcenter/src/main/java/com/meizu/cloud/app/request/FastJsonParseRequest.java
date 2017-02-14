package com.meizu.cloud.app.request;

import com.android.volley.k;
import com.android.volley.n;
import com.android.volley.n.b;
import com.meizu.volley.a;
import java.util.List;

public class FastJsonParseRequest<T> extends a<T> {
    private ParseListener<T> mParseListener;

    public FastJsonParseRequest(String url, ParseListener parseListener, b listener, n.a errorListener) {
        this(0, url, null, parseListener, listener, errorListener);
    }

    public FastJsonParseRequest(String url, int method, List params, ParseListener parseListener, b listener, n.a errorListener) {
        this(method, url, params, parseListener, listener, errorListener);
    }

    public FastJsonParseRequest(String url, List params, ParseListener parseListener, b listener, n.a errorListener) {
        this(1, url, params, parseListener, listener, errorListener);
    }

    public FastJsonParseRequest(int method, String url, List params, ParseListener parseListener, b listener, n.a errorListener) {
        super(method, url, params, listener, errorListener);
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

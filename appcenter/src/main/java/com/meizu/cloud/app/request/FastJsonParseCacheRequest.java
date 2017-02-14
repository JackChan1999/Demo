package com.meizu.cloud.app.request;

import com.android.volley.i;
import com.android.volley.n;
import com.android.volley.n.a;
import com.android.volley.n.b;
import com.android.volley.toolbox.d;
import java.util.List;

public class FastJsonParseCacheRequest<T> extends FastJsonParseRequest<T> {
    private final int CODE_CACHE = 304;
    private CacheCallback<T> mCacheListener;

    public interface CacheCallback<T> {
        void onCacheDateReceived(String str);

        T onParseCache();
    }

    public FastJsonParseCacheRequest(String url, ParseListener parseListener, b listener, a errorListener) {
        super(url, parseListener, listener, errorListener);
    }

    public FastJsonParseCacheRequest(String url, int method, List params, ParseListener parseListener, b listener, a errorListener) {
        super(url, method, params, parseListener, listener, errorListener);
    }

    public FastJsonParseCacheRequest(String url, List params, ParseListener parseListener, b listener, a errorListener) {
        super(url, params, parseListener, listener, errorListener);
    }

    public FastJsonParseCacheRequest(int method, String url, List params, ParseListener parseListener, b listener, a errorListener) {
        super(method, url, params, parseListener, listener, errorListener);
    }

    public void setCacheListener(CacheCallback mCacheListener) {
        this.mCacheListener = mCacheListener;
    }

    public void cancel() {
        this.mCacheListener = null;
        super.cancel();
    }

    protected n<T> parseNetworkResponse(i response) {
        if (!(response.c == null || !response.c.containsKey(RequestConstants.IF_MODIFIED_SINCE) || this.mCacheListener == null)) {
            this.mCacheListener.onCacheDateReceived((String) response.c.get(RequestConstants.IF_MODIFIED_SINCE));
        }
        if (response.a != 304 || this.mCacheListener == null) {
            return super.parseNetworkResponse(response);
        }
        return n.a(this.mCacheListener.onParseCache(), d.a(response));
    }
}

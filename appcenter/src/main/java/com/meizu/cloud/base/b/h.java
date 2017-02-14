package com.meizu.cloud.base.b;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.meizu.cloud.app.request.FastJsonParseCacheRequest;
import com.meizu.cloud.app.request.FastJsonParseCacheRequest.CacheCallback;
import com.meizu.cloud.app.request.ParseListener;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.utils.m;
import com.meizu.cloud.app.utils.s;
import com.meizu.cloud.app.utils.t;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.base.b.f.b;
import java.util.ArrayList;
import java.util.List;

public abstract class h<T> extends n<a> implements CacheCallback, ParseListener<a> {
    protected a f;
    protected boolean g = false;
    protected String h = "";
    protected int i = 0;
    protected String j = "";

    public static class a<T> {
        public List<T> b;
        public int c = -1;
        public boolean d;
        public String e = "";
    }

    protected abstract a<T> a(String str);

    protected abstract String a();

    protected abstract a<T> b(String str);

    public /* synthetic */ Object onParseCache() {
        return h();
    }

    protected /* synthetic */ Object onParseFirstData(String str) {
        return f(str);
    }

    public /* synthetic */ Object onParseResponse(String str) {
        return g(str);
    }

    protected /* synthetic */ boolean onResponse(Object obj) {
        return a((a) obj);
    }

    protected boolean loadData() {
        boolean bStart = false;
        if (m.b(getActivity())) {
            bStart = super.loadData();
        } else if (!this.mbInitLoad) {
            bStart = loadData(s.a(getActivity(), a() + this.j, 0, 50));
        }
        if (bStart) {
            if (this.g) {
                showFooter();
            } else {
                showProgress();
            }
        }
        return bStart;
    }

    protected a f(String json) {
        return b(json);
    }

    protected void onRequestData() {
        List<com.meizu.volley.b.a> paramPairs = new ArrayList();
        paramPairs.add(new com.meizu.volley.b.a("start", String.valueOf(this.i)));
        paramPairs.add(new com.meizu.volley.b.a("max", String.valueOf(50)));
        String url = a();
        this.mLoadRequest = new FastJsonParseCacheRequest(url, paramPairs, this, new b(this), new f.a(this));
        this.mLoadRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(getActivity()));
        if (this.i == 0) {
            ((FastJsonParseCacheRequest) this.mLoadRequest).setCacheListener(this);
            String ifSinceModified = s.a(getActivity(), url + this.j);
            if (!TextUtils.isEmpty(ifSinceModified)) {
                this.mLoadRequest.addHeader(RequestConstants.IF_MODIFIED_SINCE, ifSinceModified);
            }
        }
        com.meizu.volley.b.a(getActivity()).a().a(this.mLoadRequest);
    }

    public a h() {
        String json = "";
        if (this.mRunning) {
            json = s.a(getActivity(), a() + this.j, 0, 50);
            if (TextUtils.isEmpty(json)) {
                c("");
            }
        }
        return b(json, true);
    }

    public void onCacheDateReceived(String date) {
        c(date);
    }

    private void c(String date) {
        if (this.i == 0 && this.mRunning) {
            Context activity = getActivity();
            String str = a() + this.j;
            if (date == null) {
                date = "";
            }
            s.a(activity, str, date);
        }
    }

    public a g(String json) {
        return b(json, false);
    }

    private a b(String json, boolean isFromCache) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        if (this.g) {
            return a(json);
        }
        a dataResult = b(json);
        if (isFromCache || dataResult == null || dataResult.b == null || dataResult.b.size() <= 0 || !this.mRunning) {
            return dataResult;
        }
        s.a(getActivity(), a() + this.j, json, 0, 50);
        return dataResult;
    }

    protected boolean a(final a resultModel) {
        if (!(resultModel == null || resultModel.b == null || resultModel.b.size() <= 0)) {
            this.mbMore = resultModel.d;
            this.i = (resultModel.c >= 0 ? resultModel.c : resultModel.b.size()) + this.i;
            if (!TextUtils.isEmpty(resultModel.e)) {
                this.h = resultModel.e;
            }
        }
        final boolean loadSuccess = (resultModel == null || resultModel.b == null) ? false : true;
        if (loadSuccess) {
            postOnPagerIdle(new Runnable(this) {
                final /* synthetic */ h c;

                public void run() {
                    if (this.c.getActivity() != null && this.c.mRunning) {
                        this.c.a(loadSuccess);
                        this.c.insertData(resultModel.b);
                    }
                }
            });
        } else {
            c("");
            a(loadSuccess);
        }
        this.f = resultModel;
        return loadSuccess;
    }

    protected void onErrorResponse(com.android.volley.s error) {
        a(false);
    }

    private void a(boolean loadSuccess) {
        if (this.g) {
            hideFooter();
            this.g = false;
            if (!loadSuccess) {
                t.a(getActivity(), getString(i.server_error));
                return;
            }
            return;
        }
        hideProgress();
        if (!loadSuccess) {
            showEmptyView(getEmptyTextString(), null, new OnClickListener(this) {
                final /* synthetic */ h a;

                {
                    this.a = r1;
                }

                public void onClick(View v) {
                    this.a.loadData();
                }
            });
        }
    }

    public void onScrollEnd() {
        if (i() != null && i().d && !this.g) {
            this.g = true;
            loadData();
        }
    }

    protected void onDataConnected() {
        if (!this.mbInitLoad || this.g) {
            loadData();
        }
    }

    protected a<T> i() {
        return this.f;
    }
}

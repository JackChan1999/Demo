package com.meizu.cloud.base.b;

import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import com.android.volley.s;
import com.meizu.cloud.download.app.NetworkStatusManager;
import com.meizu.cloud.thread.c;

public abstract class f<T> extends g {
    protected String mFirstJson = null;
    protected com.meizu.volley.a mLoadRequest;
    protected com.meizu.cloud.download.app.NetworkStatusManager.a mNetworkChangeListener = new com.meizu.cloud.download.app.NetworkStatusManager.a(this) {
        final /* synthetic */ f a;

        {
            this.a = r1;
        }

        public void a(int networkType) {
            if (networkType != 0 && this.a.getView() != null) {
                this.a.onDataConnected();
            }
        }
    };
    protected c mParseTask;
    protected boolean mbInitLoad = false;
    protected boolean mbLoading = false;
    protected boolean mbMore = true;

    public class a implements com.android.volley.n.a {
        final /* synthetic */ f a;

        public a(f fVar) {
            this.a = fVar;
        }

        public void a(s error) {
            this.a.onErrorResponse(error);
            this.a.onLoadFinished();
        }
    }

    public class b implements com.android.volley.n.b<T> {
        final /* synthetic */ f a;

        public b(f fVar) {
            this.a = fVar;
        }

        public void a(T response) {
            if (this.a.onResponse(response)) {
                this.a.mbInitLoad = true;
            }
            this.a.onLoadFinished();
        }
    }

    protected abstract void onErrorResponse(s sVar);

    protected abstract void onRequestData();

    protected abstract boolean onResponse(T t);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkStatusManager.a().a(this.mNetworkChangeListener);
    }

    public void onDestroy() {
        if (this.mNetworkChangeListener != null) {
            NetworkStatusManager.a().b(this.mNetworkChangeListener);
        }
        if (this.mLoadRequest != null) {
            this.mLoadRequest.cancel();
        }
        if (!(this.mParseTask == null || this.mParseTask.a())) {
            this.mParseTask.b();
        }
        super.onDestroy();
    }

    public void onResume() {
        super.onResume();
        resumeLoad();
    }

    protected void resumeLoad() {
        if (!this.mbInitLoad) {
            if (TextUtils.isEmpty(this.mFirstJson)) {
                loadData();
            } else {
                loadData(this.mFirstJson);
            }
        }
    }

    protected boolean loadData() {
        if (this.mbLoading || !this.mbMore) {
            return false;
        }
        this.mbLoading = true;
        onRequestData();
        return true;
    }

    protected void onLoadFinished() {
        this.mbLoading = false;
    }

    protected boolean loadData(String json) {
        if (this.mbLoading || !this.mbMore) {
            return false;
        }
        if (TextUtils.isEmpty(json)) {
            onErrorResponse(null);
            return false;
        }
        this.mbLoading = true;
        parseFirstData(json);
        return true;
    }

    private void parseFirstData(final String json) {
        if (this.mParseTask == null || this.mParseTask.a()) {
            this.mParseTask = asyncExec(new Runnable(this) {
                final /* synthetic */ f b;

                public void run() {
                    try {
                        Process.setThreadPriority(10);
                        final T parseResult = this.b.onParseFirstData(json);
                        if (!Thread.currentThread().isInterrupted()) {
                            this.b.runOnUi(new Runnable(this) {
                                final /* synthetic */ AnonymousClass1 b;

                                public void run() {
                                    this.b.b.onLoadFinished();
                                    if (this.b.b.onResponse(parseResult)) {
                                        this.b.b.mbInitLoad = true;
                                    }
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (!Thread.currentThread().isInterrupted()) {
                            this.b.runOnUi(new Runnable(this) {
                                final /* synthetic */ AnonymousClass1 a;

                                {
                                    this.a = r1;
                                }

                                public void run() {
                                    this.a.b.onLoadFinished();
                                    this.a.b.onErrorResponse(null);
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    protected T onParseFirstData(String json) {
        return null;
    }

    protected void onDataConnected() {
    }
}

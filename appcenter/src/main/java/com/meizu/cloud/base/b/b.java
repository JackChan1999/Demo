package com.meizu.cloud.base.b;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.app.downlad.f.m;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.base.a.b.a;
import com.meizu.cloud.download.app.NetworkStatusManager;
import com.meizu.common.widget.LoadingView;
import java.util.HashMap;
import java.util.Map;

public abstract class b<T> extends e<T> implements a, com.meizu.cloud.base.a.b.b {
    protected com.meizu.cloud.base.a.b<T> e;
    protected View f;
    protected LoadingView g;
    protected TextView h;
    protected boolean i = false;
    protected String j = "";
    protected int k = 0;
    protected int l = 0;

    protected abstract com.meizu.cloud.base.a.b<T> d();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initView(View rootView) {
        super.initView(rootView);
        g().setHeaderDividersEnabled(false);
        g().setFooterDividersEnabled(false);
        this.f = getActivity().getLayoutInflater().inflate(g.list_foot_progress_container, null, false);
        this.g = (LoadingView) this.f.findViewById(f.loadingProgressBar);
        this.h = (TextView) this.f.findViewById(f.loadText);
        e();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (this.e == null) {
            this.e = d();
            this.e.a((a) this);
            this.e.a((com.meizu.cloud.base.a.b.b) this);
            if (this.e instanceof m) {
                d.a(getActivity()).a((m) this.e, new com.meizu.cloud.app.downlad.g());
            }
        }
        a(this.e);
        g().setOnScrollListener(this.e);
    }

    public void onDestroy() {
        if (this.g != null) {
            this.g.setVisibility(8);
            this.g.b();
        }
        if (this.mNetworkChangeListener != null) {
            NetworkStatusManager.a().b(this.mNetworkChangeListener);
        }
        super.onDestroy();
        if (this.e != null && (this.e instanceof m)) {
            d.a(getActivity()).b((m) this.e);
        }
    }

    protected void e() {
        ListView listView = g();
        if (this.f != null) {
            listView.addFooterView(this.f);
            this.f.setVisibility(8);
        }
    }

    public void c() {
        if (this.mbMore && !this.i) {
            a();
        }
    }

    public void b(AbsListView view, int scrollState) {
    }

    protected void a() {
        this.i = true;
        this.f.setVisibility(0);
        g().addFooterView(this.f);
    }

    public int f() {
        return this.e == null ? 0 : this.e.getCount();
    }

    public Map<String, String> b() {
        Map<String, String> wdmParamsMap = new HashMap();
        wdmParamsMap.put("count", String.valueOf(this.l));
        wdmParamsMap.put("sum", String.valueOf(f()));
        return wdmParamsMap;
    }
}

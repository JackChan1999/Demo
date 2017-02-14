package com.meizu.cloud.base.b;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.meizu.cloud.app.utils.d;
import com.meizu.cloud.b.a.e;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import flyme.support.v7.widget.LinearLayoutManager;
import flyme.support.v7.widget.MzRecyclerView;
import flyme.support.v7.widget.RecyclerView;
import flyme.support.v7.widget.RecyclerView.AdapterDataObserver;
import flyme.support.v7.widget.RecyclerView.OnScrollListener;
import java.util.List;

public abstract class k<D extends List> extends j<D> {
    private AdapterDataObserver a = new AdapterDataObserver(this) {
        final /* synthetic */ k a;

        {
            this.a = r1;
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (this.a.e() != null && this.a.e().c().size() > 0) {
                this.a.hideEmptyView();
            }
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (this.a.e() != null && this.a.e().c().size() == 0) {
                Drawable icon = null;
                if (this.a.mRunning) {
                    icon = this.a.getResources().getDrawable(e.ic_error_page, null);
                }
                this.a.showEmptyView(this.a.b(), icon, null);
            }
        }
    };
    protected MzRecyclerView e;
    protected com.meizu.cloud.base.a.e f;
    boolean g;

    public abstract class a extends OnScrollListener {
        private int a = 0;
        int b;
        int c;
        int d;
        final /* synthetic */ k e;
        private boolean f = true;
        private int g = 5;
        private int h = 1;
        private LinearLayoutManager i;

        public abstract void a(int i);

        public a(k kVar, LinearLayoutManager linearLayoutManager) {
            this.e = kVar;
            this.i = linearLayoutManager;
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0) {
                this.c = recyclerView.getChildCount();
                this.d = this.i.getItemCount();
                this.b = this.i.findFirstVisibleItemPosition();
                if (this.f && this.d > this.a) {
                    this.f = false;
                    this.a = this.d;
                }
                if (!this.f && this.d - this.c <= this.b + this.g) {
                    this.h++;
                    a(this.h);
                    this.f = true;
                }
            }
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }

    public abstract com.meizu.cloud.base.a.e a();

    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(g.base_mzrecycler_fragment, container, false);
    }

    protected void initView(View rootView) {
        super.initView(rootView);
        this.e = (MzRecyclerView) rootView.findViewById(f.recyclerView);
        this.e.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        this.e.setLayoutManager(layoutManager);
        this.e.setOnScrollListener(new a(this, layoutManager) {
            final /* synthetic */ k a;

            public void a(int current_page) {
                this.a.f();
            }
        });
        this.e.setClipChildren(false);
        this.e.setClipToPadding(false);
        int extraPaddingTop = d.f(getActivity());
        int extraPaddingBottom = 0;
        if (getArguments() != null) {
            if (getArguments().containsKey("extra_padding_top")) {
                extraPaddingTop += getArguments().getInt("extra_padding_top");
            }
            if (getArguments().containsKey("extra_padding_bottom")) {
                extraPaddingBottom = 0 + getArguments().getInt("extra_padding_bottom");
            }
        }
        this.e.setPadding(this.e.getPaddingLeft(), this.e.getPaddingTop() + extraPaddingTop, this.e.getPaddingRight(), this.e.getPaddingBottom() + extraPaddingBottom);
    }

    public void a(D dataList) {
        if (this.e.getAdapter() == null) {
            this.f = a();
            this.e.setAdapter(this.f);
        }
        this.f.a((List) dataList);
        if (!this.g) {
            this.f.registerAdapterDataObserver(this.a);
            this.g = true;
        }
    }

    public MzRecyclerView d() {
        return this.e;
    }

    public com.meizu.cloud.base.a.e e() {
        return this.f;
    }

    public void f() {
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.f != null) {
            this.f.unregisterAdapterDataObserver(this.a);
        }
        this.g = false;
    }

    protected String b() {
        return "";
    }
}

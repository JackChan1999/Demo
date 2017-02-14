package com.meizu.cloud.base.b;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.base.a.d;
import com.meizu.cloud.base.a.d.b;
import flyme.support.v7.widget.LinearLayoutManager;
import flyme.support.v7.widget.MzItemDecoration;
import flyme.support.v7.widget.MzItemDecoration.DividerPadding;
import flyme.support.v7.widget.MzRecyclerView;
import flyme.support.v7.widget.RecyclerView;
import flyme.support.v7.widget.RecyclerView.OnScrollListener;
import java.util.List;

public abstract class n<T> extends f<T> implements b {
    private d mAdapter;
    protected int mExtraPaddingTop = 0;
    private MzRecyclerView mRecyclerView;
    protected a mScrollToEndListener;

    public abstract class a extends OnScrollListener {
        private int a = 0;
        int b;
        int c;
        int d;
        final /* synthetic */ n e;
        private boolean f = true;
        private int g = 5;
        private int h = 1;
        private LinearLayoutManager i;

        public abstract void a(int i);

        public a(n nVar, LinearLayoutManager linearLayoutManager) {
            this.e = nVar;
            this.i = linearLayoutManager;
        }

        public void a() {
            this.a = 0;
            this.f = true;
            this.g = 5;
            this.b = 0;
            this.c = 0;
            this.d = 0;
            this.h = 1;
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
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
    }

    public abstract d createRecyclerAdapter();

    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(g.base_mzrecycler_fragment, container, false);
    }

    protected void initView(View rootView) {
        super.initView(rootView);
        this.mRecyclerView = (MzRecyclerView) rootView.findViewById(f.recyclerView);
        this.mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        this.mRecyclerView.setLayoutManager(layoutManager);
        this.mScrollToEndListener = new a(this, layoutManager) {
            final /* synthetic */ n a;

            public void a(int current_page) {
                this.a.onScrollEnd();
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                this.a.onScrollDistance(recyclerView, dx, dy);
            }
        };
        this.mRecyclerView.setOnScrollListener(this.mScrollToEndListener);
        this.mRecyclerView.setClipChildren(false);
        this.mRecyclerView.setClipToPadding(false);
        this.mRecyclerView.setMotionEventSplittingEnabled(false);
        int paddingTop = com.meizu.cloud.app.utils.d.f(getActivity()) + this.mExtraPaddingTop;
        int paddingBottom = 0;
        if (getArguments() != null) {
            if (getArguments().containsKey("extra_padding_top")) {
                paddingTop += getArguments().getInt("extra_padding_top");
            }
            if (getArguments().containsKey("extra_padding_bottom")) {
                paddingBottom = 0 + getArguments().getInt("extra_padding_bottom");
            }
        }
        this.mRecyclerView.setPadding(this.mRecyclerView.getPaddingLeft(), this.mRecyclerView.getPaddingTop() + paddingTop, this.mRecyclerView.getPaddingRight(), this.mRecyclerView.getPaddingBottom() + paddingBottom);
        this.mAdapter = createRecyclerAdapter();
        this.mRecyclerView.setAdapter(this.mAdapter);
    }

    protected void setDefaultDivider(DividerPadding dividerPadding) {
        MzItemDecoration itemDecoration = new MzItemDecoration(getActivity());
        itemDecoration.setDividerPadding(dividerPadding);
        getRecyclerView().addItemDecoration(itemDecoration);
    }

    public void swapData(List dataList) {
        this.mAdapter.a(dataList);
    }

    public void insertData(List dataList) {
        this.mAdapter.b(dataList);
    }

    public void hideFooter() {
        if (this.mAdapter != null) {
            this.mAdapter.b();
        }
    }

    public void showFooter() {
        if (this.mAdapter != null) {
            this.mAdapter.f();
        }
    }

    public int getItemCount() {
        if (this.mAdapter != null) {
            return this.mAdapter.getItemCount();
        }
        return 0;
    }

    public MzRecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    public d getRecyclerViewAdapter() {
        return this.mAdapter;
    }

    public void onItemClick(View itemView, int position) {
        Toast.makeText(getActivity(), "click:" + position, 0).show();
    }

    public void onDestroy() {
        if (getRecyclerViewAdapter() != null) {
            getRecyclerViewAdapter().b();
        }
        super.onDestroy();
    }

    public void onScrollEnd() {
    }

    public void onScrollDistance(RecyclerView recyclerView, int dx, int dy) {
    }
}

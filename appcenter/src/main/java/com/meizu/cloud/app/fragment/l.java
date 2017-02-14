package com.meizu.cloud.app.fragment;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.meizu.cloud.app.utils.d;
import com.meizu.cloud.b.a;
import com.meizu.cloud.base.b.g;
import com.meizu.common.widget.f;

public abstract class l extends g {
    protected View a;
    protected ListView b;
    public f c;
    protected DataSetObserver d;

    protected abstract f a();

    protected View a(LayoutInflater inflater) {
        return null;
    }

    protected View b(LayoutInflater inflater) {
        return null;
    }

    public ListView b() {
        return this.b;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected View a(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(a.g.base_list_fragment, null);
    }

    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = a(inflater, container);
        this.a = rootView;
        this.b = (ListView) rootView.findViewById(a.f.backtoplist);
        b().setFooterDividersEnabled(false);
        this.b.setVisibility(8);
        d.b(getActivity(), this.b);
        int paddingLeft = this.b.getPaddingLeft();
        int paddingRight = this.b.getPaddingRight();
        this.b.setPadding(paddingLeft, this.b.getPaddingTop(), paddingRight, this.b.getPaddingBottom());
        this.b.setHeaderDividersEnabled(false);
        View topHeaderView = a(inflater);
        if (topHeaderView != null) {
            this.b.addHeaderView(topHeaderView);
        }
        View bottomFotterView = b(inflater);
        if (bottomFotterView != null) {
            this.b.addFooterView(bottomFotterView);
        }
        this.c = a();
        this.b.setAdapter(this.c);
        this.c.notifyDataSetChanged();
        return rootView;
    }

    public void c() {
        this.b.setVisibility(0);
        hideProgress();
        hideEmptyView();
    }

    public void d() {
        this.b.setVisibility(8);
        showEmptyView(getEmptyTextString(), null, null);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.d != null && this.c != null) {
            this.c.unregisterDataSetObserver(this.d);
        }
    }
}

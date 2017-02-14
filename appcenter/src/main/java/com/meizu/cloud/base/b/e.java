package com.meizu.cloud.base.b;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.utils.d;
import com.meizu.cloud.app.widget.BackTopListview;
import com.meizu.cloud.b.a;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public abstract class e<T> extends f<T> {
    private AbsListView a;
    private Adapter b;
    private boolean c = true;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(g.base_list_fragment, container, false);
    }

    protected void initView(View rootView) {
        super.initView(rootView);
        this.a = (ListView) rootView.findViewById(f.backtoplist);
        if (x.a(getActivity())) {
            d.b(getActivity(), this.a);
        } else {
            this.a.setPadding(this.a.getPaddingLeft(), this.a.getPaddingTop(), this.a.getPaddingRight(), 0);
            d.b(getActivity(), this.a);
        }
        Fragment fragment = getParentFragment();
        if (fragment instanceof m) {
            m basePagerFragment = (m) fragment;
            if (basePagerFragment.getActionBar().a() == 64) {
                if (basePagerFragment.f() > 1) {
                    this.a.setPadding(this.a.getPaddingLeft(), this.a.getPaddingTop() + ((int) getResources().getDimension(a.d.pager_title_container_height)), this.a.getPaddingRight(), this.a.getPaddingBottom());
                } else {
                    this.a.setPadding(this.a.getPaddingLeft(), this.a.getPaddingTop(), this.a.getPaddingRight(), this.a.getPaddingBottom());
                }
            }
        }
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("extra_padding_bottom")) {
            int extraPaddingBottom = bundle.getInt("extra_padding_bottom");
            if (extraPaddingBottom > 0) {
                this.a.setPadding(this.a.getPaddingLeft(), this.a.getPaddingTop(), this.a.getPaddingRight(), this.a.getPaddingBottom() + extraPaddingBottom);
            }
        }
    }

    public ListView g() {
        return (ListView) this.a;
    }

    public void a(ListAdapter adapter) {
        this.b = adapter;
        if (this.a != null && this.a.getAdapter() == null) {
            this.a.setAdapter(adapter);
        }
    }

    public ListAdapter h() {
        return (ListAdapter) this.b;
    }

    public void onDestroyView() {
        this.c = false;
        if (g() instanceof BackTopListview) {
            ((BackTopListview) g()).setBackTopView(null);
        }
        super.onDestroyView();
    }
}

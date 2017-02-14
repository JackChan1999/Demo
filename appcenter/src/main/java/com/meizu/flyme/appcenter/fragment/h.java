package com.meizu.flyme.appcenter.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.l;
import android.support.v4.app.n;
import android.support.v4.view.ad;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.s;
import com.meizu.cloud.app.widget.ScrollableViewPager;
import com.meizu.cloud.base.app.BaseActivity;
import com.meizu.cloud.base.b.m;
import com.meizu.common.util.c;
import com.meizu.mstore.R;

public class h extends m {
    private Drawable a;

    private class a extends n {
        final /* synthetic */ h a;

        public a(h hVar, l fm) {
            this.a = hVar;
            super(fm);
        }

        public Fragment a(int position) {
            Fragment fragment = null;
            Bundle bundle = new Bundle();
            if (this.a.h != null) {
                if (position == 0) {
                    fragment = new l();
                    fragment.setArguments(bundle);
                } else if (position == 1) {
                    fragment = new f();
                    fragment.setArguments(bundle);
                }
            }
            if (fragment != null) {
                bundle.putInt("extra_padding_top", this.a.g());
            }
            return fragment;
        }

        public int b() {
            return this.a.h != null ? this.a.h.length : 0;
        }
    }

    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_scrollable_pager_view, container, false);
    }

    protected ad b() {
        return new a(this, getChildFragmentManager());
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.h = new String[]{getString(R.string.had_install), getString(R.string.uninstall_list)};
    }

    protected boolean loadData() {
        this.mbInitLoad = true;
        this.mbMore = false;
        this.mbLoading = false;
        hideEmptyView();
        hideProgress();
        return false;
    }

    protected void onRequestData() {
    }

    protected boolean onResponse(Object response) {
        return false;
    }

    protected void onErrorResponse(s error) {
    }

    protected void setupActionBar() {
        getActionBar().a(getString(R.string.install_record));
        getActivity().getWindow().setUiOptions(1);
        ActionBar actionBar = ((BaseActivity) getActivity()).g();
        if (actionBar != null) {
            if (this.a == null) {
                this.a = getResources().getDrawable(R.drawable.mz_smartbar_background);
                this.a = c.a(this.a, 0.5f, -1);
            }
            actionBar.b(this.a);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        getActivity().getWindow().setUiOptions(0);
    }

    public void b(boolean isEnable) {
        ((ScrollableViewPager) this.e).setPageScrollEnabled(isEnable);
    }
}

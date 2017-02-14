package com.meizu.flyme.appcenter.fragment;

import a.a.a.c;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.l;
import android.support.v4.app.n;
import android.support.v4.view.ad;
import com.meizu.cloud.app.c.g;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.PageInfo;
import com.meizu.cloud.app.request.model.PageInfo.PageType;

public class j extends com.meizu.cloud.app.fragment.a {

    private class a extends n {
        final /* synthetic */ j a;

        public a(j jVar, l fm) {
            this.a = jVar;
            super(fm);
        }

        public Fragment a(int position) {
            Fragment fragment = null;
            Bundle bundle = new Bundle();
            bundle.putString("url", RequestConstants.APP_CENTER_HOST + ((PageInfo) this.a.a.get(position)).url);
            bundle.putString("pager_name", ((PageInfo) this.a.a.get(position)).name);
            if (position == 0 && this.a.b != null) {
                bundle.putString("json_string", this.a.b);
                bundle.putBoolean("more", this.a.c);
            }
            if (this.a.a != null) {
                if (PageType.FEED.getType().equals(((PageInfo) this.a.a.get(position)).type)) {
                    fragment = new a();
                } else if (PageType.RANK.getType().equals(((PageInfo) this.a.a.get(position)).type)) {
                    if ("rand".equals(((PageInfo) this.a.a.get(position)).page_type)) {
                        fragment = new i();
                    } else if ("new".equals(((PageInfo) this.a.a.get(position)).page_type)) {
                        fragment = new k();
                    }
                } else if (PageType.CATEGORY.getType().equals(((PageInfo) this.a.a.get(position)).type)) {
                    fragment = new b();
                } else if (PageType.SPECIAL.getType().equals(((PageInfo) this.a.a.get(position)).type)) {
                    fragment = new r();
                } else if (PageType.MINE.getType().equals(((PageInfo) this.a.a.get(position)).type)) {
                    fragment = new m();
                }
            }
            if (fragment != null) {
                fragment.setArguments(bundle);
            }
            return fragment;
        }

        public int b() {
            return this.a.a != null ? this.a.a.size() : 0;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mIsFirstClassPage = true;
        c.a().b(this);
    }

    public void onDestroy() {
        super.onDestroy();
        c.a().c(this);
    }

    protected ad b() {
        return new a(this, getChildFragmentManager());
    }

    protected void setupActionBar() {
        super.setupActionBar();
        if (this.h == null || this.h.length == 0) {
            getActionBar().a((CharSequence) "");
            getActionBar().c(true);
            return;
        }
        getActionBar().c(false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActionBar().d(true);
    }

    protected String a() {
        if (this.mRunning) {
            return RequestConstants.getRuntimeDomainUrl(getActivity(), RequestConstants.CENTER_HOME);
        }
        return "";
    }

    public void onEventMainThread(g launchEvent) {
        if (isResumed()) {
            this.e.setCurrentItem(launchEvent.a);
        } else {
            this.i = launchEvent.a;
        }
        c.a().f(launchEvent);
    }
}

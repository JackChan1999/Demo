package com.meizu.cloud.base.b;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.o;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.f;
import android.support.v4.view.ad;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.b;
import android.support.v7.app.ActionBar.c;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.meizu.cloud.app.c.h;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.base.b.d.a;
import java.util.ArrayList;
import java.util.List;

public abstract class m<T> extends f<T> implements f, c {
    private boolean a = true;
    protected ViewPager e;
    protected ad f;
    protected int g;
    protected String[] h;
    protected int i = 0;
    protected List<a> j;

    protected abstract ad b();

    public int f() {
        if (this.h != null) {
            return this.h.length;
        }
        return 0;
    }

    public void a(a onPagerPageChangeListener) {
        if (onPagerPageChangeListener != null) {
            if (this.j == null) {
                this.j = new ArrayList();
            } else {
                for (a listener : this.j) {
                    if (listener == onPagerPageChangeListener) {
                        return;
                    }
                }
            }
            this.j.add(onPagerPageChangeListener);
        }
    }

    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(g.base_pager_view, container, false);
    }

    protected void initView(View rootView) {
        super.initView(rootView);
        this.e = (ViewPager) rootView.findViewById(com.meizu.cloud.b.a.f.base_pager);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.d(false);
        actionBar.b(2);
        actionBar.c();
        if (this.h != null) {
            a(this.h);
        }
    }

    protected int g() {
        return getResources().getDimensionPixelOffset(d.mz_action_bar_stacked_max_height);
    }

    protected void setupActionBar() {
        super.setupActionBar();
    }

    protected void a(String[] tabTitles) {
        if (tabTitles == null || tabTitles.length == 0) {
            showEmptyView(getEmptyTextString(), null, new OnClickListener(this) {
                final /* synthetic */ m a;

                {
                    this.a = r1;
                }

                public void onClick(View v) {
                    this.a.c();
                }
            });
            return;
        }
        hideEmptyView();
        hideProgress();
        this.h = tabTitles;
        if (this.f == null) {
            this.f = b();
            if (!(this.h == null || this.h.length == this.f.b())) {
                throw new IllegalStateException("pager tab titiles size not equel to pager adapter count");
            }
        }
        h();
        this.e.setAdapter(this.f);
        this.e.setOnPageChangeListener(this);
        this.e.setCurrentItem(this.i);
    }

    public void a(b tab, o ft) {
        if (tab.a() < this.f.b()) {
            this.e.setCurrentItem(tab.a(), true);
            return;
        }
        Log.e("AsyncExecuteFragment", "current tag index: " + tab.a());
        Log.e("AsyncExecuteFragment", "tab size is bigger than viewpager size, tab size: " + getActionBar().d() + " viewpager size: " + this.f.b());
    }

    public void b(b tab, o ft) {
    }

    public void c(b tab, o ft) {
    }

    protected void h() {
        ActionBar actionBar = getActionBar();
        int i = 0;
        while (i < this.h.length) {
            actionBar.a(actionBar.b().a(this.h[i]).a((c) this), i == this.i);
            i++;
        }
    }

    public void onPageScrollStateChanged(int state) {
        this.g = state;
        if (this.j != null && this.j.size() > 0) {
            for (a listener : this.j) {
                listener.onPageScrollStateChanged(state);
            }
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (this.a) {
            getActionBar().a(position, positionOffset, this.g);
        }
        if (this.j != null && this.j.size() > 0) {
            for (a listener : this.j) {
                listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }
    }

    public void a(int arg0) {
        if (arg0 < getActionBar().d()) {
            getActionBar().a(getActionBar().c(arg0));
            return;
        }
        Log.e("AsyncExecuteFragment", "current pager index: " + arg0);
        Log.e("AsyncExecuteFragment", "viewpager size is bigger than tab size, viewPager size: " + this.f.b() + " tab size: " + getActionBar().d());
    }

    public void onDestroyView() {
        this.e.setAdapter(null);
        getActionBar().b(0);
        getActionBar().c();
        super.onDestroyView();
    }

    protected void onDataConnected() {
        c();
    }

    protected void a(boolean scrollable) {
        this.a = scrollable;
    }

    protected void c() {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1024 || requestCode == 2048) {
            a.a.a.c.a().d(new h(requestCode, resultCode, data));
        }
    }
}

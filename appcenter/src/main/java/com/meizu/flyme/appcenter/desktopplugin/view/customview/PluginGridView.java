package com.meizu.flyme.appcenter.desktopplugin.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

public class PluginGridView extends GridView implements OnScrollListener, OnItemClickListener, OnItemLongClickListener {
    private a a;
    private b b;

    public interface a {
        void a();
    }

    public interface b {
        void a(View view, int i);

        boolean b(View view, int i);
    }

    public PluginGridView(Context context) {
        this(context, null);
    }

    public PluginGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == 0 && view.getLastVisiblePosition() == view.getCount() - 1) {
            this.a.a();
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    public void setOnLoadMoreListener(a onLoadMoreListener) {
        setOnScrollListener(this);
        this.a = onLoadMoreListener;
    }

    public void setOnLongPressListener(b onLongPressListener) {
        this.b = onLongPressListener;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
        }
        return super.onTouchEvent(ev);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        this.b.a(view, position);
    }

    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        return this.b.b(view, position);
    }
}

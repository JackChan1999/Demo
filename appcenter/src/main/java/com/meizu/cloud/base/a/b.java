package com.meizu.cloud.base.a;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import com.meizu.cloud.app.core.t;
import java.util.List;

public abstract class b<T> extends a<T> implements OnScrollListener {
    private a a;
    private b b;
    protected t f;

    public interface a {
        void b(AbsListView absListView, int i);

        void c();
    }

    public interface b {
        void a(AbsListView absListView, int i);
    }

    public b(Context context, List<T> dataList, t viewController) {
        this.e = context;
        this.d = dataList;
        this.f = viewController;
    }

    public void a(a callback) {
        this.a = callback;
    }

    public void a(b callBack) {
        this.b = callBack;
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (this.a != null) {
            this.a.b(view, scrollState);
        }
        if (this.b != null) {
            this.b.a(view, scrollState);
        }
        if (view.getLastVisiblePosition() == view.getCount() - 1 && this.a != null) {
            this.a.c();
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }
}

package com.meizu.cloud.app.a;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.base.a.d;
import com.meizu.common.widget.LoadingView;

public abstract class f<T> extends d<T> {
    private a a;
    protected Context d;

    public class a extends com.meizu.cloud.base.a.d.a {
        public LoadingView a;
        public TextView b;
        final /* synthetic */ f c;

        public a(f fVar, View itemView) {
            this.c = fVar;
            super(fVar, itemView);
        }
    }

    public f(Context context) {
        this.d = context;
    }

    public com.meizu.cloud.base.a.d.a b(ViewGroup parent) {
        LinearLayout rootView = (LinearLayout) LayoutInflater.from(this.d).inflate(g.list_foot_progress_container, parent, false);
        LoadingView loadingView = (LoadingView) rootView.findViewById(com.meizu.cloud.b.a.f.loadingProgressBar);
        TextView textView = (TextView) rootView.findViewById(com.meizu.cloud.b.a.f.loadText);
        textView.setText(i.loading_text);
        a holder = new a(this, rootView);
        holder.a = loadingView;
        holder.b = textView;
        this.a = holder;
        return holder;
    }

    public void b(com.meizu.cloud.base.a.d.a holder) {
        a loadMoreHolder = (a) holder;
        if (this.i) {
            loadMoreHolder.a.setVisibility(0);
            loadMoreHolder.a.a();
            return;
        }
        loadMoreHolder.a.setVisibility(8);
        loadMoreHolder.a.b();
    }

    public void b() {
        super.b();
        if (this.a != null) {
            this.a.a.b();
        }
    }
}

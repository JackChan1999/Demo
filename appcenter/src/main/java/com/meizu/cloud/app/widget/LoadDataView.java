package com.meizu.cloud.app.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.common.widget.EmptyView;
import com.meizu.common.widget.LoadingView;

public class LoadDataView extends FrameLayout {
    private LinearLayout a;
    private LoadingView b;
    private TextView c;
    private EmptyView d;
    private Handler e;
    private Runnable f = new Runnable(this) {
        final /* synthetic */ LoadDataView a;

        {
            this.a = r1;
        }

        public void run() {
            this.a.a();
        }
    };

    public LoadDataView(Context context) {
        super(context);
        a(context);
    }

    public LoadDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    public LoadDataView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a(context);
    }

    private void a(Context context) {
        View view = LayoutInflater.from(context).inflate(g.load_data_view, this);
        this.a = (LinearLayout) view.findViewById(f.progress_container);
        this.b = (LoadingView) view.findViewById(f.mc_loading_view);
        this.c = (TextView) view.findViewById(f.mc_loading_view_text);
        this.d = (EmptyView) view.findViewById(f.empty_view);
        this.e = new Handler(Looper.getMainLooper());
    }

    public void a() {
        setVisibility(0);
        this.a.setVisibility(0);
        this.b.setVisibility(0);
        this.b.a();
    }

    public void a(String progressText) {
        if (progressText != null) {
            this.c.setText(progressText);
        }
        this.e.removeCallbacks(this.f);
        this.e.postDelayed(this.f, 500);
    }

    public void b() {
        this.e.removeCallbacks(this.f);
        this.a.setVisibility(8);
        this.b.setVisibility(8);
        this.b.b();
    }

    public void c() {
        this.d.setVisibility(8);
    }

    public void a(String title, Drawable iconDrawable, OnClickListener onRetryClickListener) {
        setVisibility(0);
        this.d.setVisibility(0);
        if (title != null) {
            this.d.setTitle(title);
        }
        if (iconDrawable == null) {
            this.d.setImageDrawable(new ColorDrawable(0));
        } else {
            this.d.setImageDrawable(iconDrawable);
        }
        if (onRetryClickListener == null) {
            this.d.setOnClickListener(null);
        } else {
            this.d.setOnClickListener(onRetryClickListener);
        }
        this.d.setAlpha(0.0f);
        this.d.animate().alpha(1.0f).setDuration(100).start();
    }
}

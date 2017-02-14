package com.meizu.cloud.app.widget;

import android.content.Context;
import android.graphics.Movie;
import android.util.AttributeSet;
import com.meizu.cloud.b.a.k;
import com.meizu.update.d.b;

public class UrlGifView extends GifView {
    private Thread a;
    private b b;

    public UrlGifView(Context context) {
        this(context, null);
    }

    public UrlGifView(Context context, AttributeSet attrs) {
        this(context, attrs, k.CustomTheme_gifViewStyle);
    }

    public UrlGifView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.a = null;
        this.b = null;
    }

    public void a() {
        if (this.a != null && this.a.isAlive()) {
            this.a.interrupt();
            if (this.b != null) {
                this.b.a();
            }
        }
    }

    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == 0) {
            setPaused(false);
        } else {
            setPaused(true);
        }
    }

    protected void onDetachedFromWindow() {
        a();
        super.onDetachedFromWindow();
    }

    public void setMoviePost(final Movie movie) {
        if (!Thread.currentThread().isInterrupted()) {
            post(new Runnable(this) {
                final /* synthetic */ UrlGifView b;

                public void run() {
                    this.b.setMovie(movie);
                    this.b.setVisibility(movie != null ? 0 : 8);
                }
            });
        }
    }
}

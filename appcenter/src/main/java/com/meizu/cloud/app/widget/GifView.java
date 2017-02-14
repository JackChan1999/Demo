package com.meizu.cloud.app.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.meizu.cloud.b.a.j;
import com.meizu.cloud.b.a.k;

public class GifView extends View {
    private int a;
    private Movie b;
    private long c;
    private int d;
    private float e;
    private float f;
    private float g;
    private int h;
    private int i;
    private volatile boolean j;
    private boolean k;

    public GifView(Context context) {
        this(context, null);
    }

    public GifView(Context context, AttributeSet attrs) {
        this(context, attrs, k.CustomTheme_gifViewStyle);
    }

    public GifView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.d = 0;
        this.j = false;
        this.k = true;
        a(context, attrs, defStyle);
    }

    @SuppressLint({"NewApi"})
    private void a(Context context, AttributeSet attrs, int defStyle) {
        if (VERSION.SDK_INT >= 11) {
            setLayerType(1, null);
        }
        TypedArray array = context.obtainStyledAttributes(attrs, k.GifView, defStyle, j.Widget_GifView);
        this.a = array.getResourceId(k.GifView_gif, -1);
        this.j = array.getBoolean(k.GifView_paused, false);
        array.recycle();
        if (this.a != -1) {
            this.b = Movie.decodeStream(getResources().openRawResource(this.a));
        }
    }

    public void setMovieResource(int movieResId) {
        this.a = movieResId;
        this.b = Movie.decodeStream(getResources().openRawResource(this.a));
        requestLayout();
    }

    public void setMovie(Movie movie) {
        this.b = movie;
        requestLayout();
    }

    public Movie getMovie() {
        return this.b;
    }

    public void setMovieTime(int time) {
        this.d = time;
        invalidate();
    }

    public void setPaused(boolean paused) {
        this.j = paused;
        if (!paused) {
            this.c = SystemClock.uptimeMillis() - ((long) this.d);
        }
        invalidate();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.b != null) {
            int movieWidth = this.b.width();
            int movieHeight = this.b.height();
            float scaleH = 1.0f;
            if (MeasureSpec.getMode(widthMeasureSpec) != 0) {
                int maximumWidth = MeasureSpec.getSize(widthMeasureSpec);
                if (movieWidth > maximumWidth) {
                    scaleH = ((float) movieWidth) / ((float) maximumWidth);
                }
            }
            float scaleW = 1.0f;
            if (MeasureSpec.getMode(heightMeasureSpec) != 0) {
                int maximumHeight = MeasureSpec.getSize(heightMeasureSpec);
                if (movieHeight > maximumHeight) {
                    scaleW = ((float) movieHeight) / ((float) maximumHeight);
                }
            }
            this.g = 1.0f / Math.max(scaleH, scaleW);
            this.h = (int) (((float) movieWidth) * this.g);
            this.i = (int) (((float) movieHeight) * this.g);
            setMeasuredDimension(this.h, this.i);
            return;
        }
        setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.e = ((float) (getWidth() - this.h)) / 2.0f;
        this.f = ((float) (getHeight() - this.i)) / 2.0f;
        this.k = getVisibility() == 0;
    }

    protected void onDraw(Canvas canvas) {
        if (this.b == null) {
            return;
        }
        if (this.j) {
            a(canvas);
            return;
        }
        b();
        a(canvas);
        a();
    }

    @SuppressLint({"NewApi"})
    private void a() {
        if (!this.k) {
            return;
        }
        if (VERSION.SDK_INT >= 16) {
            postInvalidateOnAnimation();
        } else {
            invalidate();
        }
    }

    private void b() {
        long now = SystemClock.uptimeMillis();
        if (this.c == 0) {
            this.c = now;
        }
        int dur = this.b.duration();
        if (dur == 0) {
            dur = 1000;
        }
        this.d = (int) ((now - this.c) % ((long) dur));
    }

    private void a(Canvas canvas) {
        this.b.setTime(this.d);
        canvas.save(1);
        canvas.scale(this.g, this.g);
        this.b.draw(canvas, this.e / this.g, this.f / this.g);
        canvas.restore();
    }

    @SuppressLint({"NewApi"})
    public void onScreenStateChanged(int screenState) {
        boolean z = true;
        super.onScreenStateChanged(screenState);
        if (screenState != 1) {
            z = false;
        }
        this.k = z;
        a();
    }

    @SuppressLint({"NewApi"})
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        this.k = visibility == 0;
        a();
    }

    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        this.k = visibility == 0;
        a();
    }
}

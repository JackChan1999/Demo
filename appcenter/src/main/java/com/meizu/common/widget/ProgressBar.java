package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.ViewDebug.ExportedProperty;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.RemoteViews.RemoteView;
import com.meizu.common.a.b;
import com.meizu.common.a.j;

@RemoteView
public class ProgressBar extends View {
    private int a;
    private int b;
    private int c;
    private int d;
    int e;
    int f;
    int g;
    int h;
    private int i;
    private boolean j;
    private boolean k;
    private Transformation l;
    private AlphaAnimation m;
    private boolean n;
    private Drawable o;
    private Drawable p;
    private Drawable q;
    private boolean r;
    private Interpolator s;
    private a t;
    private long u;
    private boolean v;
    private boolean w;
    private boolean x;
    private int y;
    private boolean z;

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public /* synthetic */ Object createFromParcel(Parcel x0) {
                return a(x0);
            }

            public /* synthetic */ Object[] newArray(int x0) {
                return a(x0);
            }

            public SavedState a(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] a(int size) {
                return new SavedState[size];
            }
        };
        int a;
        int b;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.a = in.readInt();
            this.b = in.readInt();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.a);
            out.writeInt(this.b);
        }
    }

    private class a implements Runnable {
        final /* synthetic */ ProgressBar a;
        private int b;
        private int c;
        private boolean d;

        a(ProgressBar progressBar, int id, int progress, boolean fromUser) {
            this.a = progressBar;
            this.b = id;
            this.c = progress;
            this.d = fromUser;
        }

        public void run() {
            this.a.a(this.b, this.c, this.d, true);
            this.a.t = this;
        }

        public void a(int id, int progress, boolean fromUser) {
            this.b = id;
            this.c = progress;
            this.d = fromUser;
        }
    }

    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, b.MeizuCommon_ProgressBarStyle);
    }

    public ProgressBar(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, 0);
    }

    public ProgressBar(Context context, AttributeSet attrs, int defStyle, int styleRes) {
        boolean z = false;
        super(context, attrs, defStyle);
        this.y = 0;
        this.z = false;
        this.u = Thread.currentThread().getId();
        a();
        TypedArray a = context.obtainStyledAttributes(attrs, j.ProgressBar, defStyle, styleRes);
        this.r = true;
        Drawable drawable = a.getDrawable(j.ProgressBar_mcProgressDrawable);
        if (drawable != null) {
            setProgressDrawable(a(drawable, false));
        }
        this.i = a.getInt(j.ProgressBar_mcIndeterminateDuration, this.i);
        this.e = a.getDimensionPixelSize(j.ProgressBar_mcMinWidth, this.e);
        this.f = a.getDimensionPixelSize(j.ProgressBar_mcMaxWidth, this.f);
        this.g = a.getDimensionPixelSize(j.ProgressBar_mcMinHeight, this.g);
        this.h = a.getDimensionPixelSize(j.ProgressBar_mcMaxHeight, this.h);
        this.d = a.getInt(j.ProgressBar_mcIndeterminateBehavior, this.d);
        int resID = a.getResourceId(j.ProgressBar_mcInterpolator, 17432587);
        if (resID > 0) {
            setInterpolator(context, resID);
        }
        setMax(a.getInt(j.ProgressBar_mcMax, this.c));
        setProgress(a.getInt(j.ProgressBar_mcProgress, this.a));
        setSecondaryProgress(a.getInt(j.ProgressBar_mcSecondaryProgress, this.b));
        drawable = a.getDrawable(j.ProgressBar_mcIndeterminateDrawable);
        if (drawable != null) {
            setIndeterminateDrawable(a(drawable));
        }
        this.k = a.getBoolean(j.ProgressBar_mcIndeterminateOnly, this.k);
        this.r = false;
        if (this.k || a.getBoolean(j.ProgressBar_mcIndeterminate, this.j)) {
            z = true;
        }
        setIndeterminate(z);
        a.recycle();
    }

    private Drawable a(Drawable drawable, boolean clip) {
        if (!(drawable instanceof LayerDrawable)) {
            return drawable;
        }
        int i;
        LayerDrawable background = (LayerDrawable) drawable;
        int N = background.getNumberOfLayers();
        Drawable[] outDrawables = new Drawable[N];
        for (i = 0; i < N; i++) {
            int id = background.getId(i);
            Drawable drawable2 = background.getDrawable(i);
            boolean z = id == 16908301 || id == 16908303;
            outDrawables[i] = a(drawable2, z);
        }
        Drawable layerDrawable = new LayerDrawable(outDrawables);
        for (i = 0; i < N; i++) {
            layerDrawable.setId(i, background.getId(i));
        }
        return layerDrawable;
    }

    Shape getDrawableShape() {
        return new RoundRectShape(new float[]{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f}, null, null);
    }

    private Drawable a(Drawable drawable) {
        if (!(drawable instanceof AnimationDrawable)) {
            return drawable;
        }
        AnimationDrawable background = (AnimationDrawable) drawable;
        int N = background.getNumberOfFrames();
        Drawable newBg = new AnimationDrawable();
        newBg.setOneShot(background.isOneShot());
        for (int i = 0; i < N; i++) {
            Drawable frame = a(background.getFrame(i), true);
            frame.setLevel(10000);
            newBg.addFrame(frame, background.getDuration(i));
        }
        newBg.setLevel(10000);
        return newBg;
    }

    private void a() {
        this.c = 100;
        this.a = 0;
        this.b = 0;
        this.j = false;
        this.k = false;
        this.i = 4000;
        this.d = 1;
        this.e = 24;
        this.f = 48;
        this.g = 24;
        this.h = 48;
    }

    public synchronized void setIndeterminate(boolean indeterminate) {
        if (!((this.k && this.j) || indeterminate == this.j)) {
            this.j = indeterminate;
            if (indeterminate) {
                this.q = this.o;
                e();
            } else {
                this.q = this.p;
                f();
            }
        }
    }

    public Drawable getIndeterminateDrawable() {
        return this.o;
    }

    public void setIndeterminateDrawable(Drawable d) {
        if (d != null) {
            d.setCallback(this);
        }
        this.o = d;
        if (this.j) {
            this.q = d;
            postInvalidate();
        }
    }

    public Drawable getProgressDrawable() {
        return this.p;
    }

    public void setProgressDrawable(Drawable d) {
        boolean needUpdate;
        if (this.p == null || d == this.p) {
            needUpdate = false;
        } else {
            this.p.setCallback(null);
            needUpdate = true;
        }
        if (d != null) {
            d.setCallback(this);
            int drawableHeight = d.getMinimumHeight();
            if (this.h < drawableHeight) {
                this.h = drawableHeight;
                requestLayout();
            }
        }
        this.p = d;
        if (!this.j) {
            this.q = d;
            postInvalidate();
        }
        if (needUpdate) {
            a(getWidth(), getHeight());
            b();
            a(16908301, this.a, false, false);
            a(16908303, this.b, false, false);
        }
    }

    Drawable getCurrentDrawable() {
        return this.q;
    }

    protected boolean verifyDrawable(Drawable who) {
        return who == this.p || who == this.o || super.verifyDrawable(who);
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.p != null) {
            this.p.jumpToCurrentState();
        }
        if (this.o != null) {
            this.o.jumpToCurrentState();
        }
    }

    public void postInvalidate() {
        if (!this.r) {
            super.postInvalidate();
        }
    }

    private synchronized void a(int id, int progress, boolean fromUser, boolean callBackToApp) {
        float scale = this.c > 0 ? ((float) progress) / ((float) this.c) : 0.0f;
        Drawable d = this.q;
        if (d != null) {
            Drawable progressDrawable = null;
            if (d instanceof LayerDrawable) {
                progressDrawable = ((LayerDrawable) d).findDrawableByLayerId(id);
            }
            int level = (int) (10000.0f * scale);
            if (progressDrawable == null) {
                progressDrawable = d;
            }
            progressDrawable.setLevel(level);
        } else {
            invalidate();
        }
        if (callBackToApp && id == 16908301) {
            a(scale, fromUser);
        }
    }

    void a(float scale, boolean fromUser) {
    }

    private synchronized void a(int id, int progress, boolean fromUser) {
        if (this.u == Thread.currentThread().getId()) {
            a(id, progress, fromUser, true);
        } else {
            a r;
            if (this.t != null) {
                r = this.t;
                this.t = null;
                r.a(id, progress, fromUser);
            } else {
                r = new a(this, id, progress, fromUser);
            }
            post(r);
        }
    }

    public synchronized void setProgress(int progress) {
        a(progress, false);
    }

    synchronized void a(int progress, boolean fromUser) {
        if (!this.j) {
            if (progress < 0) {
                progress = 0;
            }
            if (progress > this.c) {
                progress = this.c;
            }
            if (progress != this.a) {
                this.a = progress;
                a(16908301, this.a, fromUser);
            }
        }
    }

    public synchronized void setSecondaryProgress(int secondaryProgress) {
        if (!this.j) {
            if (secondaryProgress < 0) {
                secondaryProgress = 0;
            }
            if (secondaryProgress > this.c) {
                secondaryProgress = this.c;
            }
            if (secondaryProgress != this.b) {
                this.b = secondaryProgress;
                a(16908303, this.b, false);
            }
        }
    }

    @ExportedProperty(category = "progress")
    public synchronized int getProgress() {
        return this.j ? 0 : this.a;
    }

    @ExportedProperty(category = "progress")
    public synchronized int getSecondaryProgress() {
        return this.j ? 0 : this.b;
    }

    @ExportedProperty(category = "progress")
    public synchronized int getMax() {
        return this.c;
    }

    public synchronized void setMax(int max) {
        if (max < 0) {
            max = 0;
        }
        if (max != this.c) {
            this.c = max;
            postInvalidate();
            if (this.a > max) {
                this.a = max;
            }
            a(16908301, this.a, false);
        }
    }

    public final synchronized void a(int diff) {
        setProgress(this.a + diff);
    }

    void e() {
        if (getVisibility() == 0) {
            if (this.o instanceof Animatable) {
                this.v = true;
                this.m = null;
            } else {
                if (this.s == null) {
                    this.s = new LinearInterpolator();
                }
                this.l = new Transformation();
                this.m = new AlphaAnimation(0.0f, 1.0f);
                this.m.setRepeatMode(this.d);
                this.m.setRepeatCount(-1);
                this.m.setDuration((long) this.i);
                this.m.setInterpolator(this.s);
                this.m.setStartTime(-1);
            }
            postInvalidate();
        }
    }

    void f() {
        this.m = null;
        this.l = null;
        if (this.o instanceof Animatable) {
            ((Animatable) this.o).stop();
            this.v = false;
        }
        postInvalidate();
    }

    public void setInterpolator(Context context, int resID) {
        setInterpolator(AnimationUtils.loadInterpolator(context, resID));
    }

    public void setInterpolator(Interpolator interpolator) {
        this.s = interpolator;
    }

    public Interpolator getInterpolator() {
        return this.s;
    }

    public void setVisibility(int v) {
        if (getVisibility() != v) {
            super.setVisibility(v);
            if (!this.j) {
                return;
            }
            if (v == 8 || v == 4) {
                f();
            } else {
                e();
            }
        }
    }

    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (!this.j) {
            return;
        }
        if (visibility == 8 || visibility == 4) {
            f();
        } else {
            e();
        }
    }

    public void invalidateDrawable(Drawable dr) {
        if (!this.w) {
            if (verifyDrawable(dr)) {
                Rect dirty = dr.getBounds();
                int scrollX = getScrollX() + getPaddingLeft();
                int scrollY = getScrollY() + getPaddingTop();
                invalidate(dirty.left + scrollX, dirty.top + scrollY, dirty.right + scrollX, dirty.bottom + scrollY);
                return;
            }
            super.invalidateDrawable(dr);
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        a(w, h);
    }

    private void a(int w, int h) {
        int right = (w - getPaddingRight()) - getPaddingLeft();
        int bottom = (h - getPaddingBottom()) - getPaddingTop();
        int top = 0;
        int left = 0;
        if (this.o != null) {
            if (this.k && !(this.o instanceof AnimationDrawable)) {
                float intrinsicAspect = ((float) this.o.getIntrinsicWidth()) / ((float) this.o.getIntrinsicHeight());
                float boundAspect = ((float) w) / ((float) h);
                if (intrinsicAspect != boundAspect) {
                    if (boundAspect > intrinsicAspect) {
                        int width = (int) (((float) h) * intrinsicAspect);
                        left = (w - width) / 2;
                        right = left + width;
                    } else {
                        int height = (int) (((float) w) * (1.0f / intrinsicAspect));
                        top = (h - height) / 2;
                        bottom = top + height;
                    }
                }
            }
            this.o.setBounds(left, top, right, bottom);
        }
        if (this.p != null) {
            this.p.setBounds(0, 0, right, bottom);
        }
    }

    public void setBreakPoint(int level) {
        this.y = level;
        invalidate();
    }

    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable d = this.q;
        if (d != null) {
            Rect firstPartRect;
            Rect secondPartRect;
            int availdeDis = this.z ? (getHeight() - getPaddingTop()) - getPaddingBottom() : (getWidth() - getPaddingLeft()) - getPaddingRight();
            int breakLevel = (this.y * availdeDis) / getMax();
            if (this.z) {
                firstPartRect = new Rect(0, 0, getWidth(), availdeDis - breakLevel);
                secondPartRect = new Rect(0, (availdeDis - breakLevel) + 5, getWidth(), availdeDis);
            } else {
                firstPartRect = new Rect(0, 0, breakLevel, getHeight());
                secondPartRect = new Rect(breakLevel + 5, 0, availdeDis, getHeight());
            }
            canvas.save();
            canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
            long time = getDrawingTime();
            if (this.n) {
                this.m.getTransformation(time, this.l);
                float scale = this.l.getAlpha();
                try {
                    this.w = true;
                    d.setLevel((int) (10000.0f * scale));
                    this.w = false;
                    postInvalidate();
                } catch (Throwable th) {
                    this.w = false;
                }
            }
            if (this.y == getMax() || this.y == 0) {
                d.draw(canvas);
                canvas.restore();
            } else {
                canvas.clipRect(firstPartRect);
                d.draw(canvas);
                canvas.restore();
                canvas.save();
                canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
                canvas.clipRect(secondPartRect);
                d.draw(canvas);
                canvas.restore();
            }
            if (this.v && (d instanceof Animatable)) {
                ((Animatable) d).start();
                this.v = false;
            }
        }
    }

    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = this.q;
        int dw = 0;
        int dh = 0;
        if (d != null) {
            dw = Math.max(this.e, Math.min(this.f, d.getIntrinsicWidth()));
            dh = Math.max(this.g, Math.min(this.h, d.getIntrinsicHeight()));
        }
        b();
        setMeasuredDimension(resolveSizeAndState(dw + (getPaddingLeft() + getPaddingRight()), widthMeasureSpec, 0), resolveSizeAndState(dh + (getPaddingTop() + getPaddingBottom()), heightMeasureSpec, 0));
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        b();
    }

    private void b() {
        int[] state = getDrawableState();
        if (this.p != null && this.p.isStateful()) {
            this.p.setState(state);
        }
        if (this.o != null && this.o.isStateful()) {
            this.o.setState(state);
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.a = this.a;
        ss.b = this.b;
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setProgress(ss.a);
        setSecondaryProgress(ss.b);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.j) {
            e();
        }
    }

    protected void onDetachedFromWindow() {
        if (this.j) {
            f();
        }
        if (this.t != null) {
            removeCallbacks(this.t);
        }
        if (this.t != null && this.x) {
            removeCallbacks(this.t);
        }
        super.onDetachedFromWindow();
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(ProgressBar.class.getName());
        event.setItemCount(this.c);
        event.setCurrentItemIndex(this.a);
    }

    public void setProgressDrawableResource(int resId) {
        Drawable drawable = getProgressDrawable();
        Drawable newDrawble = getContext().getResources().getDrawable(resId);
        newDrawble.setBounds(drawable.copyBounds());
        setProgressDrawable(newDrawble);
        if (this.a > 0) {
            a(-1);
            a(1);
        }
    }

    protected void setIsVertical(boolean mIsVertical) {
        this.z = mIsVertical;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(ProgressBar.class.getName());
    }
}

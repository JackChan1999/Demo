package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.meizu.common.a.e;
import com.meizu.common.a.j;

public class EnhanceSeekBar extends View {
    private int a;
    private int b;
    private int c;
    private boolean d;
    private int e;
    private int f;
    private int g;
    private int h;
    private boolean i;
    private boolean j;
    private Drawable k;
    private Drawable l;
    private int m;
    private CharSequence[] n;
    private b o;
    private Paint p;
    private d q;
    private d r;
    private ObjectAnimator s;
    private c t;
    private a u;
    private Interpolator v;
    private int w;
    private boolean x;
    private float y;
    private float z;

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

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.a = in.readInt();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.a);
        }
    }

    private class a {
        final /* synthetic */ EnhanceSeekBar a;
        private Drawable b;

        public a(EnhanceSeekBar enhanceSeekBar) {
            this.a = enhanceSeekBar;
        }

        public void a(Drawable drawable) {
            this.b = drawable;
        }
    }

    public interface b {
        void a(EnhanceSeekBar enhanceSeekBar);

        void a(EnhanceSeekBar enhanceSeekBar, int i);

        void b(EnhanceSeekBar enhanceSeekBar);

        void b(EnhanceSeekBar enhanceSeekBar, int i);
    }

    private class c implements TypeEvaluator {
        final /* synthetic */ EnhanceSeekBar a;

        private c(EnhanceSeekBar enhanceSeekBar) {
            this.a = enhanceSeekBar;
        }

        public Object evaluate(float fraction, Object startValue, Object endValue) {
            d startXY = (d) startValue;
            d endXY = (d) endValue;
            return new d(this.a, startXY.a() + ((endXY.a() - startXY.a()) * fraction), startXY.b() + ((endXY.b() - startXY.b()) * fraction));
        }
    }

    private class d {
        final /* synthetic */ EnhanceSeekBar a;
        private float b;
        private float c;

        public d(EnhanceSeekBar enhanceSeekBar) {
            this.a = enhanceSeekBar;
            this.c = 0.0f;
            this.b = 0.0f;
        }

        public d(EnhanceSeekBar enhanceSeekBar, float x, float y) {
            this.a = enhanceSeekBar;
            this.b = x;
            this.c = y;
        }

        public float a() {
            return this.b;
        }

        public void a(float x, float y) {
            this.b = x;
            this.c = y;
        }

        public float b() {
            return this.c;
        }
    }

    public EnhanceSeekBar(Context context) {
        this(context, null);
    }

    public EnhanceSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, com.meizu.common.a.b.MeizuCommon_EnhanceSeekBarStyle);
    }

    public EnhanceSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.d = false;
        this.e = 0;
        this.f = 0;
        this.g = 0;
        this.q = new d(this);
        this.r = new d(this);
        this.t = new c();
        this.u = new a(this);
        this.x = false;
        this.w = ViewConfiguration.get(context).getScaledTouchSlop();
        TypedArray a = context.obtainStyledAttributes(attrs, j.EnhanceSeekBar, defStyle, 0);
        setItems(a.getTextArray(j.EnhanceSeekBar_mcEItems));
        setProgress(a.getInt(j.EnhanceSeekBar_mcEProgress, 0));
        setItemsCount(a.getInt(j.EnhanceSeekBar_mcEItemsCount, 1));
        Drawable thumb = a.getDrawable(j.EnhanceSeekBar_mcEThumb);
        if (thumb == null) {
            thumb = context.getResources().getDrawable(e.mz_scrubber_control_selector);
        }
        this.l = a.getDrawable(j.EnhanceSeekBar_mcAuraEnhanceThumbDrawble);
        this.m = (int) a.getDimension(j.EnhanceSeekBar_mcAuraEnhanceDistance, 10.0f);
        setThumb(thumb);
        a.recycle();
        this.p = new Paint();
        this.h = getResources().getColor(com.meizu.common.a.c.mc_enhance_seekbar_circle_color);
        this.p.setColor(this.h);
        this.p.setStrokeWidth(4.0f);
        if (VERSION.SDK_INT >= 21) {
            this.v = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        } else {
            this.v = new AccelerateInterpolator();
        }
    }

    public void setOnEnhanceSeekBarChangeListener(b l) {
        this.o = l;
    }

    public void setItems(CharSequence[] items) {
        if (items == null || items.length == 0) {
            this.n = null;
            setMax(0);
            return;
        }
        int length = items.length;
        this.n = new CharSequence[length];
        System.arraycopy(items, 0, this.n, 0, length);
        setMax(length - 1);
    }

    public void setItemsCount(int count) {
        if (this.n != null && this.n.length < count) {
            setMax(this.n.length - 1);
        } else if (count < 1) {
            setMax(0);
        } else {
            setMax(count - 1);
        }
    }

    public void setThumb(Drawable thumb) {
        boolean needUpdate;
        if (thumb == null) {
            thumb = getResources().getDrawable(e.mz_scrubber_control_selector);
        }
        if (this.k == null || thumb == this.k) {
            needUpdate = false;
        } else {
            this.k.setCallback(null);
            int oldThumbWidth = this.k.getIntrinsicWidth();
            needUpdate = true;
        }
        if (thumb != null) {
            if (needUpdate) {
                thumb.setCallback(this);
                this.c = thumb.getIntrinsicWidth() / 2;
            } else {
                thumb.setCallback(this);
                this.c = thumb.getIntrinsicWidth() / 2;
            }
            if (needUpdate && !(thumb.getIntrinsicWidth() == this.k.getIntrinsicWidth() && thumb.getIntrinsicHeight() == this.k.getIntrinsicHeight())) {
                requestLayout();
            }
            this.f = thumb.getIntrinsicWidth() / 2;
            this.g = thumb.getIntrinsicHeight() / 2;
        }
        this.k = thumb;
        invalidate();
        if (needUpdate) {
            a(getWidth(), getHeight());
            if (thumb != null && thumb.isStateful()) {
                thumb.setState(getDrawableState());
            }
        }
    }

    public Drawable getThumb() {
        return this.k;
    }

    private void setThumbOffset(int thumbOffset) {
        this.c = thumbOffset;
        invalidate();
    }

    public synchronized void setProgress(int progress) {
        a(progress, false);
    }

    synchronized void a(int progress, boolean fromFling) {
        if (progress < 0) {
            progress = 0;
        }
        if (progress > this.a) {
            progress = this.a;
        }
        if (!(progress == this.b && this.j)) {
            this.b = progress;
            if (!fromFling) {
                a(this.a > 0 ? ((float) this.b) / ((float) this.a) : 0.0f);
            } else if (this.o != null && this.i) {
                this.o.b(this, getProgress());
            }
        }
    }

    public synchronized int getProgress() {
        return this.b;
    }

    public synchronized int getItemsCount() {
        return this.n != null ? this.n.length : this.a;
    }

    private synchronized int getMax() {
        return this.a;
    }

    private synchronized void setMax(int max) {
        if (max < 0) {
            max = 0;
        }
        if (max != this.a) {
            this.a = max;
            if (this.b > max) {
                this.b = max;
            }
            a(this.a > 0 ? ((float) this.b) / ((float) this.a) : 0.0f);
        }
    }

    private void a(float scale) {
        Drawable thumb = this.k;
        if (thumb != null) {
            a(getWidth(), thumb, scale, Integer.MIN_VALUE);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.k != null && this.k.isStateful()) {
            this.k.setState(getDrawableState());
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        a(w, h);
    }

    private void a(int w, int h) {
        Drawable thumb = this.k;
        int max = getMax();
        float scale = max > 0 ? ((float) getProgress()) / ((float) max) : 0.0f;
        if (thumb != null) {
            a(w, thumb, scale, 0);
        }
    }

    private void a(int w, Drawable thumb, float scale, int gap) {
        int topBound;
        int bottomBound;
        boolean shouldAnimation;
        int available = ((w - getPaddingLeft()) - getPaddingRight()) - (this.m * 2);
        int thumbWidth = thumb.getIntrinsicWidth();
        available -= thumbWidth;
        int thumbPos = available - Math.round((1.0f - scale) * ((float) available));
        if (gap == Integer.MIN_VALUE) {
            Rect oldBounds = thumb.getBounds();
            topBound = oldBounds.top;
            bottomBound = oldBounds.bottom;
            shouldAnimation = true;
        } else {
            topBound = gap;
            bottomBound = topBound + thumb.getIntrinsicHeight();
            shouldAnimation = false;
        }
        if (this.s != null) {
            if (!shouldAnimation) {
                this.s.cancel();
                this.s = null;
            } else if (this.s.isStarted()) {
                shouldAnimation = false;
            }
        }
        if (shouldAnimation) {
            int oldThumbPos = thumb.getBounds().left;
            if (oldThumbPos == thumbPos) {
                this.j = true;
                if (gap == Integer.MIN_VALUE && this.o != null) {
                    this.o.a(this, getProgress());
                    return;
                }
                return;
            }
            this.q.a((float) oldThumbPos, (float) topBound);
            this.r.a((float) thumbPos, (float) topBound);
            this.u.a(thumb);
            this.s = ObjectAnimator.ofObject(this.u, "xY", this.t, new Object[]{this.q, this.r});
            this.s.setDuration(256);
            this.s.setInterpolator(this.v);
            this.s.addListener(new AnimatorListenerAdapter(this) {
                final /* synthetic */ EnhanceSeekBar a;

                {
                    this.a = r1;
                }

                public void onAnimationCancel(Animator animation) {
                    if (this.a.o != null) {
                        this.a.o.a(this.a, this.a.getProgress());
                    }
                }

                public void onAnimationEnd(Animator animation) {
                    if (this.a.o != null) {
                        this.a.o.a(this.a, this.a.getProgress());
                    }
                }
            });
            this.s.start();
        } else {
            this.k.setBounds(thumbPos, topBound, thumbPos + thumbWidth, bottomBound);
            invalidate();
        }
        this.j = true;
    }

    protected synchronized void onDraw(Canvas canvas) {
        float offset = 0.0f;
        synchronized (this) {
            super.onDraw(canvas);
            if (this.k != null) {
                int i;
                canvas.save();
                if (this.n != null) {
                    canvas.translate((float) ((getPaddingLeft() + this.f) + this.m), (float) ((getPaddingTop() + 50) + this.g));
                } else {
                    canvas.translate((float) ((getPaddingLeft() + this.f) + this.m), (float) (getPaddingTop() + this.g));
                }
                float available = (float) ((((getWidth() - getPaddingLeft()) - getPaddingRight()) - (this.f * 2)) - (this.m * 2));
                int max = getMax();
                if (max > 0) {
                    offset = available / ((float) max);
                }
                this.p.setColor(this.h);
                this.p.setAntiAlias(true);
                canvas.drawLine(0.0f, 0.0f, available, 0.0f, this.p);
                for (i = 0; i <= max; i++) {
                    canvas.drawCircle(((float) i) * offset, 0.0f, 6.0f, this.p);
                }
                this.p.setTextSize(40.0f);
                for (i = 0; i <= max; i++) {
                    if (getProgress() == i) {
                        this.p.setColor(-12303292);
                    } else {
                        this.p.setColor(this.h);
                    }
                    if (this.n != null) {
                        String string = this.n[i];
                        canvas.drawText(string, (((float) i) * offset) - (this.p.measureText(string) / 2.0f), (float) (-this.g), this.p);
                    }
                }
                canvas.restore();
                canvas.save();
                if (this.n != null) {
                    canvas.translate((float) (getPaddingLeft() + this.m), (float) (getPaddingTop() + 50));
                } else {
                    canvas.translate((float) (getPaddingLeft() + this.m), (float) getPaddingTop());
                }
                if (this.d && this.l != null) {
                    int X = this.k.getBounds().centerX();
                    int Y = this.k.getBounds().centerY();
                    int radis = this.k.getIntrinsicWidth() / 2;
                    this.l.setBounds((X - radis) - this.m, (Y - radis) - this.m, (X + radis) + this.m, (Y + radis) + this.m);
                    this.l.setAlpha(204);
                    this.l.draw(canvas);
                }
                this.k.draw(canvas);
                canvas.restore();
            }
        }
    }

    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i = 0;
        synchronized (this) {
            int thumbHeight = this.k == null ? 0 : this.k.getIntrinsicHeight();
            int minHeight = 20;
            if (this.n != null) {
                minHeight = 20 + 50;
            }
            int dh = minHeight;
            int dw = (getPaddingLeft() + 64) + getPaddingRight();
            if (thumbHeight != 0) {
                if (this.n != null) {
                    i = 50;
                }
                dh = Math.max(i + thumbHeight, minHeight);
            }
            setMeasuredDimension(resolveSizeAndState(Math.max(dw, MeasureSpec.getSize(widthMeasureSpec)), widthMeasureSpec, 0), resolveSizeAndState(dh + ((getPaddingTop() + getPaddingBottom()) + this.m), heightMeasureSpec, 0));
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled() || getMax() == 0) {
            return false;
        }
        float x = event.getX();
        switch (event.getAction()) {
            case 0:
                this.y = x;
                if (this.k != null) {
                    this.z = (float) this.k.getBounds().left;
                }
                this.e = Math.round((((x - ((float) getPaddingLeft())) - ((float) this.m)) / ((float) ((((getWidth() - getPaddingLeft()) - getPaddingRight()) - (this.f * 2)) - (this.m * 2)))) * ((float) getMax()));
                if (b((int) event.getX(), (int) event.getY())) {
                    setPressed(true);
                    if (this.k != null) {
                        invalidate(this.k.getBounds());
                    }
                    a();
                }
                c();
                this.x = false;
                break;
            case 1:
                if (!this.x) {
                    c(event);
                    break;
                }
                this.d = false;
                if (this.l != null) {
                    invalidate(this.l.getBounds());
                }
                if (!this.i) {
                    a(this.e, false);
                    break;
                }
                b(event);
                b();
                setPressed(false);
                break;
            case 2:
                if (this.i) {
                    this.j = false;
                    a(event);
                    c();
                }
                if (Math.abs(event.getX() - this.y) <= ((float) this.w)) {
                    this.x = false;
                    break;
                }
                this.x = true;
                this.d = true;
                if (this.l != null) {
                    invalidate(this.l.getBounds());
                    break;
                }
                break;
            case 3:
                if (this.i) {
                    b();
                    setPressed(false);
                }
                invalidate();
                break;
        }
        return true;
    }

    private boolean b(int x, int y) {
        return true;
    }

    private void a(MotionEvent event) {
        int width = getWidth();
        int available = (((width - getPaddingLeft()) - getPaddingRight()) - (this.f * 2)) - (this.m * 2);
        int x = (int) event.getX();
        Rect rect = this.k.getBounds();
        int thumbX = (int) ((this.z + ((float) x)) - this.y);
        if (thumbX < 0) {
            thumbX = 0;
        } else if (thumbX > ((width - getPaddingRight()) - (this.f * 2)) - (this.m * 2)) {
            thumbX = ((width - getPaddingRight()) - (this.f * 2)) - (this.m * 2);
        }
        a(Math.round((((float) ((thumbX - getPaddingLeft()) - this.m)) / ((float) available)) * ((float) getMax())), true);
        this.k.setBounds(thumbX, rect.top, this.k.getIntrinsicWidth() + thumbX, rect.bottom);
        invalidate();
    }

    private void b(MotionEvent event) {
        float scale;
        int width = getWidth();
        int available = (((width - getPaddingLeft()) - getPaddingRight()) - (this.f * 2)) - (this.m * 2);
        int thumbX = (int) ((this.z + ((float) ((int) event.getX()))) - this.y);
        if (thumbX < 0) {
            scale = 0.0f;
        } else if (thumbX > (width - getPaddingRight()) - (this.f * 2)) {
            scale = 1.0f;
        } else {
            scale = ((float) ((thumbX - getPaddingLeft()) - this.m)) / ((float) available);
        }
        a(Math.round(0.0f + (((float) getMax()) * scale)), false);
    }

    private void c(MotionEvent event) {
        float process = (float) Math.round((((((float) ((int) event.getX())) - ((float) getPaddingLeft())) - ((float) this.m)) / ((float) ((((getWidth() - getPaddingLeft()) - getPaddingRight()) - (this.f * 2)) - (this.m * 2)))) * ((float) getMax()));
        float scale = process / ((float) this.a);
        a((int) process, true);
        a(getWidth(), this.k, scale, Integer.MIN_VALUE);
    }

    private void c() {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
    }

    void a() {
        this.i = true;
        if (this.o != null) {
            this.o.a(this);
        }
    }

    void b() {
        this.i = false;
        if (this.o != null) {
            this.o.b(this);
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.a = this.b;
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        a(ss.a, true);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(EnhanceSeekBar.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(EnhanceSeekBar.class.getName());
        if (isEnabled()) {
            int progress = getProgress();
            if (progress > 0) {
                info.addAction(8192);
            }
            if (progress < getMax()) {
                info.addAction(4096);
            }
        }
    }

    public boolean performAccessibilityAction(int action, Bundle arguments) {
        if (super.performAccessibilityAction(action, arguments)) {
            return true;
        }
        if (!isEnabled()) {
            return false;
        }
        int progress = getProgress();
        int increment = Math.max(1, Math.round(((float) getMax()) / 5.0f));
        switch (action) {
            case 4096:
                if (progress >= getMax()) {
                    return false;
                }
                a(progress + increment, false);
                return true;
            case 8192:
                if (progress <= 0) {
                    return false;
                }
                a(progress - increment, false);
                return true;
            default:
                return false;
        }
    }

    public void setPaintColor(int color) {
        this.h = color;
    }
}

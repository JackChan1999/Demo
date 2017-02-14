package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.meizu.common.a.j;

public abstract class AbsSeekBar extends ProgressBar {
    float a;
    boolean b = true;
    boolean c = false;
    protected int d = 0;
    private Drawable i;
    private int j;
    private int k = 1;
    private float l;
    private int m;
    private float n;
    private float o;
    private boolean p;
    private int q = 256;
    private int r = 0;
    private float s;
    private float t = 0.0f;
    private boolean u = false;
    private int v = 0;
    private int w = 0;

    public AbsSeekBar(Context context) {
        super(context);
    }

    public AbsSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, j.SeekBar, defStyle, 0);
        setThumb(a.getDrawable(j.SeekBar_mcThumb));
        setThumbOffset(a.getDimensionPixelOffset(j.SeekBar_mcThumbOffset, getThumbOffset()));
        a.recycle();
        this.l = 0.5f;
        this.m = ViewConfiguration.get(context).getScaledTouchSlop();
        this.q = this.m * this.m;
    }

    public void setThumb(Drawable thumb) {
        boolean needUpdate;
        if (this.i == null || thumb == this.i) {
            needUpdate = false;
        } else {
            this.i.setCallback(null);
            needUpdate = true;
        }
        if (thumb != null) {
            thumb.setCallback(this);
            if (this.c) {
                this.j = thumb.getIntrinsicHeight() / 2;
            } else {
                this.j = thumb.getIntrinsicWidth() / 2;
            }
            if (needUpdate && !(thumb.getIntrinsicWidth() == this.i.getIntrinsicWidth() && thumb.getIntrinsicHeight() == this.i.getIntrinsicHeight())) {
                requestLayout();
            }
            this.v = thumb.getIntrinsicWidth() / 2;
            this.w = thumb.getIntrinsicHeight() / 2;
        }
        this.i = thumb;
        invalidate();
        if (needUpdate) {
            a(getWidth(), getHeight());
            if (thumb.isStateful()) {
                thumb.setState(getDrawableState());
            }
        }
    }

    public Drawable getThumb() {
        return this.i;
    }

    public int getThumbOffset() {
        return this.j;
    }

    public void setThumbOffset(int thumbOffset) {
        this.j = thumbOffset;
        invalidate();
    }

    public void setKeyProgressIncrement(int increment) {
        if (increment < 0) {
            increment = -increment;
        }
        this.k = increment;
    }

    public int getKeyProgressIncrement() {
        return this.k;
    }

    public synchronized void setMax(int max) {
        super.setMax(max);
        if (this.k == 0 || getMax() / this.k > 20) {
            setKeyProgressIncrement(Math.max(1, Math.round(((float) getMax()) / 20.0f)));
        }
    }

    protected boolean verifyDrawable(Drawable who) {
        return who == this.i || super.verifyDrawable(who);
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.i != null) {
            this.i.jumpToCurrentState();
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable progressDrawable = getProgressDrawable();
        if (progressDrawable != null) {
            progressDrawable.setAlpha(isEnabled() ? 255 : (int) (255.0f * this.l));
        }
        if (this.i != null && this.i.isStateful()) {
            this.i.setState(getDrawableState());
        }
    }

    void a(float scale, boolean fromUser) {
        super.a(scale, fromUser);
        Drawable thumb = this.i;
        if (thumb != null) {
            a(getWidth(), getHeight(), thumb, scale, Integer.MIN_VALUE);
            invalidate();
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        a(w, h);
    }

    private void a(int w, int h) {
        float scale = 0.0f;
        Drawable d = getCurrentDrawable();
        Drawable thumb = this.i;
        int max;
        int gapForCenteringTrack;
        int gap;
        if (this.c) {
            int thumbWidth = thumb == null ? 0 : thumb.getIntrinsicWidth();
            int trackWidth = Math.min(this.f, (w - getPaddingLeft()) - getPaddingRight());
            max = getMax();
            if (max > 0) {
                scale = ((float) getProgress()) / ((float) max);
            }
            if (thumbWidth > trackWidth) {
                if (thumb != null) {
                    a(w, h, thumb, scale, 0);
                }
                gapForCenteringTrack = (thumbWidth - trackWidth) / 2;
                if (d != null) {
                    d.setBounds(gapForCenteringTrack, 0, ((w - getPaddingRight()) - gapForCenteringTrack) - getPaddingLeft(), (h - getPaddingBottom()) - getPaddingTop());
                    return;
                }
                return;
            }
            if (d != null) {
                d.setBounds(0, 0, (w - getPaddingRight()) - getPaddingLeft(), (h - getPaddingBottom()) - getPaddingTop());
            }
            gap = (trackWidth - thumbWidth) / 2;
            if (thumb != null) {
                a(w, h, thumb, scale, gap);
                return;
            }
            return;
        }
        int thumbHeight = thumb == null ? 0 : thumb.getIntrinsicHeight();
        int trackHeight = Math.min(this.h, (h - getPaddingTop()) - getPaddingBottom());
        max = getMax();
        if (max > 0) {
            scale = ((float) getProgress()) / ((float) max);
        }
        if (thumbHeight > trackHeight) {
            if (thumb != null) {
                a(w, h, thumb, scale, 0);
            }
            gapForCenteringTrack = (thumbHeight - trackHeight) / 2;
            if (d != null) {
                d.setBounds(0, gapForCenteringTrack, (w - getPaddingRight()) - getPaddingLeft(), ((h - getPaddingBottom()) - gapForCenteringTrack) - getPaddingTop());
                return;
            }
            return;
        }
        if (d != null) {
            d.setBounds(0, 0, (w - getPaddingRight()) - getPaddingLeft(), (h - getPaddingBottom()) - getPaddingTop());
        }
        gap = (trackHeight - thumbHeight) / 2;
        if (thumb != null) {
            a(w, h, thumb, scale, gap);
        }
    }

    private void a(int w, int h, Drawable thumb, float scale, int gap) {
        int available;
        int thumbWidth = thumb.getIntrinsicWidth();
        int thumbHeight = thumb.getIntrinsicHeight();
        if (this.c) {
            available = ((h - getPaddingTop()) - getPaddingBottom()) - thumbHeight;
        } else {
            available = ((w - getPaddingLeft()) - getPaddingRight()) - thumbWidth;
        }
        available += this.j * 2;
        if (this.c) {
            int leftBound;
            int rightBound;
            int thumbPos = (int) ((1.0f - scale) * ((float) available));
            if (gap == Integer.MIN_VALUE) {
                Rect oldBounds = thumb.getBounds();
                leftBound = oldBounds.left;
                rightBound = oldBounds.right;
            } else {
                leftBound = gap;
                rightBound = gap + thumbWidth;
            }
            thumb.setBounds(leftBound, thumbPos, rightBound, thumbPos + thumbHeight);
            return;
        }
        int topBound;
        int bottomBound;
        thumbPos = (int) (((float) available) * scale);
        if (gap == Integer.MIN_VALUE) {
            oldBounds = thumb.getBounds();
            topBound = oldBounds.top;
            bottomBound = oldBounds.bottom;
        } else {
            topBound = gap;
            bottomBound = gap + thumbHeight;
        }
        thumb.setBounds(thumbPos, topBound, thumbPos + thumbWidth, bottomBound);
    }

    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.i != null) {
            canvas.save();
            if (this.c) {
                canvas.translate((float) getPaddingLeft(), (float) (getPaddingTop() - this.j));
                this.i.draw(canvas);
                canvas.restore();
            } else {
                canvas.translate((float) (getPaddingLeft() - this.j), (float) getPaddingTop());
                this.i.draw(canvas);
                canvas.restore();
            }
        }
    }

    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int thumbHeight = 0;
        synchronized (this) {
            Drawable d = getCurrentDrawable();
            if (this.i != null) {
                thumbHeight = this.i.getIntrinsicHeight();
            }
            int dw = 0;
            int dh = 0;
            if (d != null) {
                dw = Math.max(this.e, Math.min(this.f, d.getIntrinsicWidth()));
                dh = Math.max(thumbHeight, Math.max(this.g, Math.min(this.h, d.getIntrinsicHeight())));
            }
            setMeasuredDimension(resolveSizeAndState(dw + (getPaddingLeft() + getPaddingRight()), widthMeasureSpec, 0), resolveSizeAndState(dh + (getPaddingTop() + getPaddingBottom()), heightMeasureSpec, 0));
            if (getMeasuredHeight() > getMeasuredWidth()) {
                this.c = true;
            }
        }
    }

    public boolean a() {
        ViewParent p = getParent();
        while (p != null && (p instanceof ViewGroup)) {
            if (((ViewGroup) p).shouldDelayChildPressedState()) {
                return true;
            }
            p = p.getParent();
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.b || !isEnabled()) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        int available;
        float deltaX;
        float deltaY;
        float scale;
        switch (event.getAction()) {
            case 0:
                if (!a()) {
                    setPressed(true);
                    if (this.i != null) {
                        invalidate(this.i.getBounds());
                    }
                    b();
                    this.r = 0;
                    if (this.d == 1) {
                        if (this.c) {
                            this.s = x;
                            this.t = event.getY();
                        } else {
                            this.s = event.getX();
                            this.t = y;
                        }
                        this.u = false;
                        this.r = getProgress();
                    } else {
                        a(event);
                    }
                    g();
                    break;
                }
                this.n = x;
                this.o = y;
                if (this.d == 1) {
                    b();
                    this.s = x;
                    this.t = y;
                    this.u = false;
                    this.r = getProgress();
                    g();
                    break;
                }
                break;
            case 1:
                if (this.p) {
                    if (this.c) {
                        available = (getHeight() - getPaddingTop()) - getPaddingBottom();
                    } else {
                        available = (getWidth() - getPaddingLeft()) - getPaddingRight();
                    }
                    if (this.d == 1 && !this.u) {
                        float point;
                        if (this.c) {
                            point = y;
                        } else {
                            point = x;
                        }
                        if ((((float) available) - point) + ((float) getPaddingBottom()) < ((float) (c(this.r) - this.v)) || (((float) available) - point) + ((float) getPaddingBottom()) > ((float) (c(this.r) + this.v))) {
                            if (b((int) point) >= this.r + this.k) {
                                a(this.r + this.k, true);
                            } else if (b((int) point) < this.r + this.k) {
                                a(this.r - this.k, true);
                            }
                        }
                    } else if (this.d != 1 || !this.u) {
                        a(event);
                    } else if (available != 0) {
                        deltaX = x - this.s;
                        deltaY = this.t - y;
                        if (this.c) {
                            scale = deltaY / ((float) available);
                        } else {
                            scale = deltaX / ((float) available);
                        }
                        a(this.r + a(((float) getMax()) * scale), true);
                    }
                    c();
                    setPressed(false);
                } else {
                    b();
                    a(event);
                    c();
                }
                invalidate();
                this.u = false;
                break;
            case 2:
                if (this.p) {
                    if (this.d != 1) {
                        a(event);
                        break;
                    }
                    if (this.c) {
                        deltaX = Math.abs(event.getX() - this.s);
                        deltaY = Math.abs(y - this.t);
                    } else {
                        deltaX = Math.abs(x - this.s);
                        deltaY = Math.abs(event.getY() - this.t);
                    }
                    if ((deltaX * deltaX) + (deltaY * deltaY) > ((float) this.q) && !this.u) {
                        this.s = x;
                        this.u = true;
                    }
                    if (this.u) {
                        if (this.c) {
                            available = (getHeight() - getPaddingTop()) - getPaddingBottom();
                        } else {
                            available = (getWidth() - getPaddingLeft()) - getPaddingRight();
                        }
                        if (available != 0) {
                            if (this.c) {
                                scale = (this.t - y) / ((float) available);
                            } else {
                                scale = (x - this.s) / ((float) available);
                            }
                            a(this.r + a(((float) getMax()) * scale), true);
                            break;
                        }
                    }
                }
                float length;
                if (this.c) {
                    length = Math.abs(y - this.o);
                } else {
                    length = Math.abs(x - this.n);
                }
                if (length > ((float) this.m)) {
                    setPressed(true);
                    if (this.i != null) {
                        invalidate(this.i.getBounds());
                    }
                    b();
                    a(event);
                    g();
                    break;
                }
                break;
            case 3:
                if (this.p) {
                    c();
                    setPressed(false);
                }
                invalidate();
                break;
        }
        return true;
    }

    private void a(MotionEvent event) {
        float progress = 0.0f;
        int available;
        float scale;
        if (this.c) {
            int height = getHeight();
            available = (height - getPaddingTop()) - getPaddingBottom();
            int y = (int) event.getY();
            if (y < getPaddingTop()) {
                scale = 1.0f;
            } else if (y > height - getPaddingBottom()) {
                scale = 0.0f;
            } else {
                scale = 1.0f - (((float) (y - getPaddingTop())) / ((float) available));
                progress = this.a;
            }
            progress += ((float) getMax()) * scale;
        } else {
            int width = getWidth();
            available = (width - getPaddingLeft()) - getPaddingRight();
            int x = (int) event.getX();
            if (x < getPaddingLeft()) {
                scale = 0.0f;
            } else if (x > width - getPaddingRight()) {
                scale = 1.0f;
            } else {
                scale = ((float) (x - getPaddingLeft())) / ((float) available);
                progress = this.a;
            }
            progress += ((float) getMax()) * scale;
        }
        a((int) progress, true);
    }

    private void g() {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
    }

    void b() {
        this.p = true;
    }

    void c() {
        this.p = false;
    }

    void d() {
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isEnabled()) {
            int progress = getProgress();
            if ((keyCode != 21 || this.c) && !(keyCode == 20 && this.c)) {
                if (((keyCode == 22 && !this.c) || (keyCode == 19 && this.c)) && progress < getMax()) {
                    a(this.k + progress, true);
                    d();
                    return true;
                }
            } else if (progress > 0) {
                a(progress - this.k, true);
                d();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void setTouchMode(int mode) {
        this.d = mode;
        if (this.d > 1) {
            this.d = 0;
        }
    }

    private int a(float num) {
        return Math.round(num);
    }

    private int b(int position) {
        int available;
        if (this.c) {
            available = (getHeight() - getPaddingTop()) - getPaddingBottom();
            position = available - position;
        } else {
            available = (getWidth() - getPaddingLeft()) - getPaddingRight();
        }
        return (int) (((float) ((getMax() * position) / available)) - this.a);
    }

    private int c(int progress) {
        int available;
        if (this.c) {
            available = (getHeight() - getPaddingTop()) - getPaddingBottom();
        } else {
            available = (getWidth() - getPaddingLeft()) - getPaddingRight();
        }
        int x = getPaddingLeft();
        int y = getPaddingBottom();
        float prog = ((float) progress) - this.a;
        if (prog < 0.0f) {
            if (this.c) {
                return y;
            }
            return x;
        } else if (prog > ((float) getMax())) {
            if (this.c) {
                return getHeight() - getPaddingBottom();
            }
            return getWidth() - getPaddingRight();
        } else if (getMax() > 0) {
            x += (int) (((float) available) * (prog / ((float) getMax())));
            y += (int) (((float) available) * (prog / ((float) getMax())));
            if (this.c) {
                return y;
            }
            return x;
        } else if (this.c) {
            return y;
        } else {
            return x;
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(AbsSeekBar.class.getName());
    }
}

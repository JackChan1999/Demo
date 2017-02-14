package com.meizu.common.widget;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.CompoundButton;
import com.meizu.common.a.b;
import com.meizu.common.a.e;
import com.meizu.common.a.j;
import com.meizu.common.b.a;

public class Switch extends CompoundButton {
    private static final int[] F = new int[]{16842912};
    private static int[] m;
    private int A;
    private TextPaint B;
    private ValueAnimator C;
    private Interpolator D;
    private final Rect E;
    public CharSequence a;
    public CharSequence b;
    private Drawable c;
    private Drawable d;
    private Drawable e;
    private Drawable f;
    private Drawable g;
    private Drawable h;
    private boolean i;
    private int j;
    private int k;
    private boolean l;
    private int n;
    private int o;
    private float p;
    private float q;
    private VelocityTracker r;
    private int s;
    private float t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    private int z;

    public Switch(Context context) {
        this(context, null);
    }

    public Switch(Context context, AttributeSet attrs) {
        this(context, attrs, b.MeizuCommon_Switch);
    }

    public Switch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = false;
        this.r = VelocityTracker.obtain();
        this.E = new Rect();
        this.B = new TextPaint(1);
        Resources res = getResources();
        this.B.density = res.getDisplayMetrics().density;
        TypedArray a = context.obtainStyledAttributes(attrs, j.Switch, defStyleAttr, 0);
        this.c = a.getDrawable(j.Switch_mcThumbOff);
        if (this.c == null) {
            this.c = res.getDrawable(e.mc_switch_anim_thumb_off_selector);
        }
        if (this.c != null) {
            this.c.setCallback(this);
        }
        this.d = a.getDrawable(j.Switch_mcThumbOn);
        if (this.d == null) {
            this.d = res.getDrawable(e.mc_switch_anim_thumb_on_selector);
        }
        if (this.d != null) {
            this.d.setCallback(this);
        }
        this.e = a.getDrawable(j.Switch_mcTrack);
        if (this.e == null) {
            this.e = res.getDrawable(e.mc_switch_bg_default);
        }
        if (this.e != null) {
            this.e.setCallback(this);
        }
        this.j = a.getDimensionPixelSize(j.Switch_mcSwitchMinWidth, 0);
        this.k = a.getDimensionPixelSize(j.Switch_mcSwitchPadding, 0);
        this.l = false;
        a.recycle();
        m = new int[]{16843044, 16843045};
        TypedArray b = context.obtainStyledAttributes(attrs, m, 16843839, 0);
        this.a = b.getText(0);
        this.b = b.getText(1);
        b.recycle();
        ViewConfiguration config = ViewConfiguration.get(context);
        this.o = config.getScaledTouchSlop();
        this.s = config.getScaledMinimumFlingVelocity();
        refreshDrawableState();
        setChecked(isChecked());
    }

    public void setSwitchPadding(int pixels) {
        this.k = pixels;
        requestLayout();
    }

    public int getSwitchPadding() {
        return this.k;
    }

    public void setSwitchMinWidth(int pixels) {
        this.j = pixels;
        requestLayout();
    }

    public int getSwitchMinWidth() {
        return this.j;
    }

    public void setTrackDrawable(Drawable track) {
        if (this.e != null) {
            this.e.setCallback(null);
        }
        this.e = track;
        if (track != null) {
            track.setCallback(this);
        }
        requestLayout();
    }

    public void setTrackResource(int resId) {
        setTrackDrawable(getContext().getResources().getDrawable(resId));
    }

    public Drawable getTrackDrawable() {
        return this.e;
    }

    public void setThumbDrawable(Drawable thumb) {
        if (this.d != null) {
            this.d.setCallback(null);
        }
        this.d = thumb;
        if (thumb != null) {
            thumb.setCallback(this);
        }
        requestLayout();
    }

    public void setThumbResource(int resId) {
        setThumbDrawable(getContext().getResources().getDrawable(resId));
    }

    public Drawable getThumbDrawable() {
        return this.d;
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int thumbWidth;
        int thumbHeight;
        int trackHeight;
        Rect padding = this.E;
        if (this.d != null) {
            this.d.getPadding(padding);
            thumbWidth = (this.d.getIntrinsicWidth() - padding.left) - padding.right;
            thumbHeight = this.d.getIntrinsicHeight();
        } else {
            thumbWidth = 0;
            thumbHeight = 0;
        }
        this.w = thumbWidth;
        if (this.e != null) {
            this.e.getPadding(padding);
            trackHeight = this.e.getIntrinsicHeight();
        } else {
            padding.setEmpty();
            trackHeight = 0;
        }
        int paddingLeft = padding.left;
        int paddingRight = padding.right;
        if (this.c != null) {
            d inset = d.a;
            paddingLeft = Math.max(paddingLeft, inset.b);
            paddingRight = Math.max(paddingRight, inset.d);
        }
        int switchWidth = Math.max(this.j, ((this.w * 2) + paddingLeft) + paddingRight);
        int switchHeight = Math.max(trackHeight, thumbHeight);
        this.u = switchWidth;
        this.v = switchHeight;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredHeight() < switchHeight) {
            setMeasuredDimension(getMeasuredWidthAndState(), switchHeight);
        }
    }

    public void onPopulateAccessibilityEvent(AccessibilityEvent event) {
        super.onPopulateAccessibilityEvent(event);
    }

    private boolean a(float x, float y) {
        return x > ((float) this.x) && x < ((float) this.z) && y > ((float) this.y) && y < ((float) this.A);
    }

    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        this.r.addMovement(ev);
        float x;
        float y;
        switch (ev.getActionMasked()) {
            case 0:
                x = ev.getX();
                y = ev.getY();
                if (isEnabled() && a(x, y)) {
                    this.n = 1;
                    this.p = x;
                    this.q = y;
                    break;
                }
            case 1:
            case 3:
                if (this.n != 2) {
                    this.n = 0;
                    this.r.clear();
                    break;
                }
                b(ev);
                super.onTouchEvent(ev);
                return true;
            case 2:
                switch (this.n) {
                    case 0:
                        break;
                    case 1:
                        x = ev.getX();
                        y = ev.getY();
                        if (Math.abs(x - this.p) > ((float) this.o) || Math.abs(y - this.q) > ((float) this.o)) {
                            this.n = 2;
                            getParent().requestDisallowInterceptTouchEvent(true);
                            this.p = x;
                            this.q = y;
                            return true;
                        }
                    case 2:
                        float dPos;
                        x = ev.getX();
                        int thumbScrollRange = getThumbScrollRange();
                        float thumbScrollOffset = x - this.p;
                        if (thumbScrollRange != 0) {
                            dPos = thumbScrollOffset / ((float) thumbScrollRange);
                        } else {
                            dPos = thumbScrollOffset > 0.0f ? 1.0f : -1.0f;
                        }
                        if (a()) {
                            dPos = -dPos;
                        }
                        float newPos = e.a(this.t + dPos, 0.0f, 1.0f);
                        if (newPos != this.t) {
                            this.p = x;
                            setThumbPosition(newPos);
                        }
                        return true;
                    default:
                        break;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void a(MotionEvent ev) {
        MotionEvent cancel = MotionEvent.obtain(ev);
        cancel.setAction(3);
        super.onTouchEvent(cancel);
        cancel.recycle();
    }

    private void b(MotionEvent ev) {
        boolean commitChange;
        boolean newState = true;
        this.n = 0;
        if (ev.getAction() == 1 && isEnabled()) {
            commitChange = true;
        } else {
            commitChange = false;
        }
        if (commitChange) {
            this.r.computeCurrentVelocity(1000);
            float xvel = this.r.getXVelocity();
            if (Math.abs(xvel) <= ((float) this.s)) {
                newState = getTargetCheckedState();
            } else if (a()) {
                if (xvel >= 0.0f) {
                    newState = false;
                }
            } else if (xvel <= 0.0f) {
                newState = false;
            }
        } else {
            newState = isChecked();
        }
        setChecked(newState);
        a(ev);
    }

    private void a(boolean newCheckedState) {
        float targetPosition;
        if (newCheckedState) {
            targetPosition = 1.0f;
        } else {
            targetPosition = 0.0f;
        }
        if (this.C != null) {
            this.C.removeAllUpdateListeners();
        }
        this.C = ValueAnimator.ofFloat(new float[]{this.t, targetPosition});
        if (this.D == null) {
            if (VERSION.SDK_INT >= 21) {
                this.D = new PathInterpolator(0.33f, 0.0f, 0.33f, 1.0f);
            } else {
                this.D = new a(0.33f, 0.0f, 0.33f, 1.0f);
            }
        }
        this.C.setInterpolator(this.D);
        this.C.setDuration(250);
        this.C.start();
        this.C.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ Switch a;

            {
                this.a = r1;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                this.a.setThumbPosition(((Float) animation.getAnimatedValue()).floatValue());
            }
        });
    }

    private void b() {
        if (this.C != null) {
            this.C.cancel();
        }
    }

    private boolean getTargetCheckedState() {
        return this.t > 0.5f;
    }

    private void setThumbPosition(float position) {
        this.t = position;
        invalidate();
    }

    public void toggle() {
        setChecked(!isChecked());
    }

    public void setChecked(boolean checked) {
        setChecked(checked, true);
    }

    public void setChecked(boolean checked, boolean useAnim) {
        super.setChecked(checked);
        checked = isChecked();
        if (useAnim && VERSION.SDK_INT >= 19 && isAttachedToWindow() && isLaidOut()) {
            a(checked);
            return;
        }
        b();
        setThumbPosition(checked ? 1.0f : 0.0f);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int switchLeft;
        int switchRight;
        int switchTop;
        int switchBottom;
        super.onLayout(changed, left, top, right, bottom);
        int opticalInsetLeft = 0;
        int opticalInsetRight = 0;
        if (this.c != null) {
            Rect trackPadding = this.E;
            if (this.e != null) {
                this.e.getPadding(trackPadding);
            } else {
                trackPadding.setEmpty();
            }
            d insets = d.a;
            opticalInsetLeft = Math.max(0, insets.b - trackPadding.left);
            opticalInsetRight = Math.max(0, insets.d - trackPadding.right);
        }
        if (a()) {
            switchLeft = getPaddingLeft() + opticalInsetLeft;
            switchRight = ((this.u + switchLeft) - opticalInsetLeft) - opticalInsetRight;
        } else {
            switchRight = (getWidth() - getPaddingRight()) - opticalInsetRight;
            switchLeft = ((switchRight - this.u) + opticalInsetLeft) + opticalInsetRight;
        }
        switch (getGravity() & 112) {
            case 16:
                switchTop = (((getPaddingTop() + getHeight()) - getPaddingBottom()) / 2) - (this.v / 2);
                switchBottom = switchTop + this.v;
                break;
            case 80:
                switchBottom = getHeight() - getPaddingBottom();
                switchTop = switchBottom - this.v;
                break;
            default:
                switchTop = getPaddingTop();
                switchBottom = switchTop + this.v;
                break;
        }
        this.x = switchLeft;
        this.y = switchTop;
        this.A = switchBottom;
        this.z = switchRight;
    }

    public void draw(Canvas c) {
        d thumbInsets;
        Rect padding = this.E;
        int switchLeft = this.x;
        int switchTop = this.y;
        int switchRight = this.z;
        int switchBottom = this.A;
        int thumbInitialLeft = switchLeft + getThumbOffset();
        if (this.c != null) {
            thumbInsets = d.a;
        } else {
            thumbInsets = d.a;
        }
        if (this.e != null) {
            this.e.getPadding(padding);
            thumbInitialLeft += padding.left;
            int trackLeft = switchLeft;
            int trackTop = switchTop;
            int trackRight = switchRight;
            int trackBottom = switchBottom;
            if (thumbInsets != d.a) {
                if (thumbInsets.b > padding.left) {
                    trackLeft += thumbInsets.b - padding.left;
                }
                if (thumbInsets.c > padding.top) {
                    trackTop += thumbInsets.c - padding.top;
                }
                if (thumbInsets.d > padding.right) {
                    trackRight -= thumbInsets.d - padding.right;
                }
                if (thumbInsets.e > padding.bottom) {
                    trackBottom -= thumbInsets.e - padding.bottom;
                }
            }
            this.e.setBounds(trackLeft, trackTop, trackRight, trackBottom);
        }
        int thumbRight;
        int thumbLeft;
        if (this.c != null && !c()) {
            this.c.getPadding(padding);
            if (a()) {
                thumbRight = switchRight - getThumbOffset();
                thumbLeft = (thumbRight - this.c.getIntrinsicWidth()) - ((int) (((float) (this.d.getIntrinsicWidth() - this.c.getIntrinsicWidth())) * (1.0f - getThumbValue())));
            } else {
                thumbLeft = thumbInitialLeft;
                thumbRight = (this.c.getIntrinsicWidth() + thumbLeft) + ((int) (((float) (this.d.getIntrinsicWidth() - this.c.getIntrinsicWidth())) * (1.0f - getThumbValue())));
            }
            this.c.setBounds(thumbLeft, switchTop, thumbRight, switchBottom);
        } else if (this.d != null && c()) {
            this.d.getPadding(padding);
            if (a()) {
                thumbLeft = switchLeft - padding.left;
                thumbRight = (this.w + thumbLeft) + padding.right;
            } else {
                thumbRight = switchRight - padding.right;
                thumbLeft = (thumbRight - this.w) - padding.left;
            }
            int xx = (int) ((((float) (thumbRight - thumbLeft)) * (1.0f - getThumbValue())) * 0.7f);
            this.d.setBounds(thumbLeft + xx, switchTop + xx, thumbRight - xx, switchBottom - xx);
        }
        super.draw(c);
    }

    protected void onDraw(Canvas canvas) {
        int saveCount;
        super.onDraw(canvas);
        Rect padding = this.E;
        Drawable trackDrawable = this.e;
        if (trackDrawable != null) {
            trackDrawable.getPadding(padding);
        } else {
            padding.setEmpty();
        }
        Drawable thumbOffDrawable = this.c;
        if (trackDrawable != null) {
            if (!this.l || thumbOffDrawable == null) {
                trackDrawable.draw(canvas);
            } else {
                d insets = d.a;
                thumbOffDrawable.copyBounds(padding);
                padding.left += insets.b;
                padding.right -= insets.d;
                saveCount = canvas.save();
                canvas.clipRect(padding, Op.DIFFERENCE);
                trackDrawable.draw(canvas);
                canvas.restoreToCount(saveCount);
            }
        }
        saveCount = canvas.save();
        Drawable thumbOnDrawable = this.d;
        if (thumbOffDrawable == null || c()) {
            thumbOnDrawable.draw(canvas);
        } else {
            thumbOffDrawable.mutate();
            thumbOffDrawable.draw(canvas);
        }
        canvas.restoreToCount(saveCount);
    }

    public int getCompoundPaddingLeft() {
        if (!a()) {
            return super.getCompoundPaddingLeft();
        }
        int padding = super.getCompoundPaddingLeft() + this.u;
        if (TextUtils.isEmpty(getText())) {
            return padding;
        }
        return padding + this.k;
    }

    public int getCompoundPaddingRight() {
        if (a()) {
            return super.getCompoundPaddingRight();
        }
        int padding = super.getCompoundPaddingRight() + this.u;
        if (TextUtils.isEmpty(getText())) {
            return padding;
        }
        return padding + this.k;
    }

    public boolean a() {
        if (VERSION.SDK_INT < 17) {
            return false;
        }
        if (getLayoutDirection() == 1) {
            return true;
        }
        return false;
    }

    private int getThumbOffset() {
        if (c()) {
            return getThumbScrollRange();
        }
        return (int) ((getThumbValue() * ((float) getThumbScrollRange())) + 0.5f);
    }

    private float getThumbValue() {
        if (c()) {
            return (this.t - 0.8f) / 0.19999999f;
        }
        return this.t / 0.8f;
    }

    private boolean c() {
        return this.t > 0.8f;
    }

    private int getThumbScrollRange() {
        if (this.e == null) {
            return 0;
        }
        d insets;
        Rect padding = this.E;
        this.e.getPadding(padding);
        if (this.c != null) {
            insets = d.a;
        } else {
            insets = d.a;
        }
        return ((((this.u - this.w) - padding.left) - padding.right) - insets.b) - insets.d;
    }

    protected int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, F);
        }
        return drawableState;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] myDrawableState = getDrawableState();
        if (this.c != null) {
            this.c.setState(myDrawableState);
        }
        if (this.d != null) {
            this.d.setState(myDrawableState);
        }
        if (this.e != null) {
            this.e.setState(myDrawableState);
        }
        invalidate();
    }

    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == this.c || who == this.e;
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.c != null) {
            this.c.jumpToCurrentState();
        }
        if (this.e != null) {
            this.e.jumpToCurrentState();
        }
        if (this.C != null && this.C.isRunning()) {
            this.C.end();
            this.C = null;
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(Switch.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(Switch.class.getName());
        CharSequence switchText = isChecked() ? this.a : this.b;
        if (!TextUtils.isEmpty(switchText)) {
            CharSequence oldText = info.getText();
            if (TextUtils.isEmpty(oldText)) {
                info.setText(switchText);
                return;
            }
            StringBuilder newText = new StringBuilder();
            newText.append(oldText).append(' ').append(switchText);
            info.setText(newText);
        }
    }

    public void setStyleWhite() {
        Resources res = getResources();
        this.g = this.d;
        this.f = this.c;
        this.h = this.e;
        this.d = res.getDrawable(e.mc_switch_anim_thumb_on_selector_color_white);
        this.c = res.getDrawable(e.mc_switch_anim_thumb_off_selector_color_white);
        this.e = res.getDrawable(e.mc_switch_anim_track_color_white);
        if (this.d == null || this.c == null || this.e == null) {
            setStyleDefault();
            return;
        }
        this.d.setCallback(this);
        this.c.setCallback(this);
        this.e.setCallback(this);
        invalidate();
        this.i = true;
    }

    public void setStyleDefault() {
        if (this.i) {
            this.d = this.g;
            this.c = this.f;
            this.e = this.h;
            this.d.setCallback(this);
            this.c.setCallback(this);
            this.e.setCallback(this);
            invalidate();
        }
    }
}

package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.View.BaseSavedState;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.TextView;
import com.meizu.common.a.d;
import com.meizu.common.a.e;
import com.meizu.common.a.j;
import com.meizu.common.app.SlideNotice;

public class CircularProgressButton extends Button {
    private Drawable A;
    private boolean B;
    private boolean C = true;
    private a D;
    private ColorStateList E;
    private ColorStateList F;
    private ColorStateList G;
    private ColorStateList H;
    private ColorStateList I;
    private ColorStateList J;
    private boolean K = false;
    private boolean L = false;
    private int M;
    private int N = 0;
    private boolean O;
    private b P = new b(this) {
        final /* synthetic */ CircularProgressButton a;

        {
            this.a = r1;
        }

        public void a() {
            this.a.O = false;
            this.a.setClickable(true);
            this.a.setText(null);
            this.a.requestLayout();
        }
    };
    private b Q = new b(this) {
        final /* synthetic */ CircularProgressButton a;

        {
            this.a = r1;
        }

        public void a() {
            if (this.a.t != 0) {
                this.a.setText(null);
                this.a.setIcon(this.a.t);
            } else {
                this.a.setText(this.a.n);
            }
            this.a.O = false;
            this.a.setClickable(true);
            this.a.setTextColor(this.a.F);
        }
    };
    private b R = new b(this) {
        final /* synthetic */ CircularProgressButton a;

        {
            this.a = r1;
        }

        public void a() {
            this.a.a();
            this.a.setText(this.a.m);
            this.a.O = false;
            this.a.setClickable(true);
            this.a.setTextColor(this.a.E);
        }
    };
    private b S = new b(this) {
        final /* synthetic */ CircularProgressButton a;

        {
            this.a = r1;
        }

        public void a() {
            if (this.a.u != 0) {
                this.a.setText(null);
                this.a.setIcon(this.a.u);
            } else {
                this.a.setText(this.a.o);
            }
            this.a.O = false;
            this.a.setClickable(true);
            this.a.setTextColor(this.a.G);
        }
    };
    private d a;
    private com.meizu.common.a.b b;
    private com.meizu.common.a.c c;
    private ColorStateList d;
    private ColorStateList e;
    private ColorStateList f;
    private StateListDrawable g;
    private StateListDrawable h;
    private StateListDrawable i;
    private StateListDrawable j;
    private StateListDrawable k;
    private c l;
    private String m;
    private String n;
    private String o;
    private String p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private float x;
    private boolean y;
    private boolean z;

    interface b {
        void a();
    }

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
        private boolean a;
        private boolean b;
        private int c;

        public SavedState(Parcelable parcel) {
            super(parcel);
        }

        private SavedState(Parcel in) {
            boolean z;
            boolean z2 = true;
            super(in);
            this.c = in.readInt();
            if (in.readInt() == 1) {
                z = true;
            } else {
                z = false;
            }
            this.a = z;
            if (in.readInt() != 1) {
                z2 = false;
            }
            this.b = z2;
        }

        public void writeToParcel(Parcel out, int flags) {
            int i;
            int i2 = 1;
            super.writeToParcel(out, flags);
            out.writeInt(this.c);
            if (this.a) {
                i = 1;
            } else {
                i = 0;
            }
            out.writeInt(i);
            if (!this.b) {
                i2 = 0;
            }
            out.writeInt(i2);
        }
    }

    class a {
        final /* synthetic */ CircularProgressButton a;
        private b b;
        private int c;
        private int d;
        private int e;
        private int f;
        private int g;
        private int h;
        private int i;
        private float j;
        private float k;
        private float l;
        private TextView m;
        private d n;
        private AnimatorSet o;

        public a(CircularProgressButton circularProgressButton, TextView viewGroup, d drawable) {
            this.a = circularProgressButton;
            this.m = viewGroup;
            this.n = drawable;
        }

        public void a(int duration) {
            this.c = duration;
        }

        public void a(b listener) {
            this.b = listener;
        }

        public void b(int fromWidth) {
            this.d = fromWidth;
        }

        public void c(int toWidth) {
            this.e = toWidth;
        }

        public void d(int fromColor) {
            this.f = fromColor;
        }

        public void e(int toColor) {
            this.g = toColor;
        }

        public void f(int fromStrokeColor) {
            this.h = fromStrokeColor;
        }

        public void g(int toStrokeColor) {
            this.i = toStrokeColor;
        }

        public void a(float fromCornerRadius) {
            this.j = fromCornerRadius;
        }

        public void b(float toCornerRadius) {
            this.k = toCornerRadius;
        }

        public void c(float padding) {
            this.l = padding;
        }

        public void a() {
            ValueAnimator widthAnimation = ValueAnimator.ofInt(new int[]{this.d, this.e});
            final GradientDrawable gradientDrawable = this.n.c();
            widthAnimation.addUpdateListener(new AnimatorUpdateListener(this) {
                final /* synthetic */ a b;

                public void onAnimationUpdate(ValueAnimator animation) {
                    int leftOffset;
                    int rightOffset;
                    int padding;
                    Integer value = (Integer) animation.getAnimatedValue();
                    if (this.b.d > this.b.e) {
                        leftOffset = (this.b.d - value.intValue()) / 2;
                        rightOffset = this.b.d - leftOffset;
                        padding = (int) (this.b.l * animation.getAnimatedFraction());
                    } else {
                        leftOffset = (this.b.e - value.intValue()) / 2;
                        rightOffset = this.b.e - leftOffset;
                        padding = (int) (this.b.l - (this.b.l * animation.getAnimatedFraction()));
                    }
                    gradientDrawable.setBounds(leftOffset + padding, padding, rightOffset - padding, this.b.m.getHeight() - padding);
                    this.b.a.invalidate();
                }
            });
            ObjectAnimator.ofInt(gradientDrawable, "color", new int[]{this.f, this.g}).setEvaluator(new ArgbEvaluator());
            ObjectAnimator.ofInt(this.n, "strokeColor", new int[]{this.h, this.i}).setEvaluator(new ArgbEvaluator());
            ObjectAnimator cornerAnimation = ObjectAnimator.ofFloat(gradientDrawable, "cornerRadius", new float[]{this.j, this.k});
            this.o = new AnimatorSet();
            this.o.setInterpolator(this.a.getInterpolator());
            this.o.setDuration((long) this.c);
            this.o.playTogether(new Animator[]{widthAnimation, bgColorAnimation, strokeColorAnimation, cornerAnimation});
            this.o.addListener(new AnimatorListener(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void onAnimationStart(Animator animation) {
                }

                public void onAnimationEnd(Animator animation) {
                    if (this.a.b != null) {
                        this.a.b.a();
                    }
                }

                public void onAnimationCancel(Animator animation) {
                }

                public void onAnimationRepeat(Animator animation) {
                }
            });
            this.o.start();
        }

        public void b() {
            this.o.end();
            this.o.removeAllListeners();
        }
    }

    public enum c {
        PROGRESS,
        IDLE,
        COMPLETE,
        ERROR
    }

    public CircularProgressButton(Context context) {
        super(context);
        a(context, null);
    }

    public CircularProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context, attrs);
    }

    public CircularProgressButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        a(context, attrs);
    }

    private void a(Context context, AttributeSet attributeSet) {
        b(context, attributeSet);
        this.M = 100;
        this.l = c.IDLE;
        setText(this.m);
        e();
        d();
        f();
        c();
        this.k = this.g;
        setBackgroundCompat(null);
    }

    private void c() {
        d drawablePressed = a(b(this.f), b(this.J));
        if (this.i == null) {
            this.i = new StateListDrawable();
            this.i.setCallback(this);
        }
        this.i.addState(new int[]{16842919}, drawablePressed.c());
        this.i.addState(StateSet.WILD_CARD, this.a.c());
        this.i.setBounds(0, 0, getRight() - getLeft(), getBottom() - getTop());
    }

    private void d() {
        d drawablePressed = a(b(this.e), b(this.I));
        if (this.h == null) {
            this.h = new StateListDrawable();
            this.h.setCallback(this);
        }
        this.h.addState(new int[]{16842919}, drawablePressed.c());
        this.h.addState(StateSet.WILD_CARD, this.a.c());
        this.h.setBounds(0, 0, getRight() - getLeft(), getBottom() - getTop());
    }

    private void e() {
        int colorNormal = a(this.d);
        int colorPressed = b(this.d);
        int colorFocused = c(this.d);
        int colorDisabled = d(this.d);
        int strokeColorNormal = a(this.H);
        int strokeColorPressed = b(this.H);
        int strokeColorFocused = c(this.H);
        int strokeColorDisabled = d(this.H);
        if (this.a == null) {
            this.a = a(colorNormal, strokeColorNormal);
        }
        d drawableDisabled = a(colorDisabled, strokeColorDisabled);
        d drawableFocused = a(colorFocused, strokeColorFocused);
        d drawablePressed = a(colorPressed, strokeColorPressed);
        if (this.g == null) {
            this.g = new StateListDrawable();
            this.g.setCallback(this);
        }
        this.g.addState(new int[]{16842919}, drawablePressed.c());
        this.g.addState(new int[]{16842908}, drawableFocused.c());
        this.g.addState(new int[]{-16842910}, drawableDisabled.c());
        this.g.addState(StateSet.WILD_CARD, this.a.c());
        this.g.setBounds(0, 0, getRight() - getLeft(), getBottom() - getTop());
    }

    private void f() {
        if (this.j == null) {
            this.j = new StateListDrawable();
            this.j.setCallback(this);
        }
        this.j.addState(StateSet.WILD_CARD, this.a.c());
        int left = (Math.abs(getWidth() - getHeight()) / 2) + this.w;
        this.j.setBounds(left, this.w, (getWidth() - left) - this.w, getHeight() - this.w);
    }

    private int a(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{16842910}, 0);
    }

    private int b(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{16842919}, 0);
    }

    private int c(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{16842908}, 0);
    }

    private int d(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{-16842910}, 0);
    }

    private d a(int color, int strokeColor) {
        GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(e.mc_cir_pro_btn_background).mutate();
        drawable.setColor(color);
        drawable.setCornerRadius(this.x);
        d strokeGradientDrawable = new d(drawable);
        strokeGradientDrawable.b(strokeColor);
        strokeGradientDrawable.a(this.v);
        return strokeGradientDrawable;
    }

    protected void drawableStateChanged() {
        Rect r = g();
        a(this.g, getDrawableState());
        a(this.h, getDrawableState());
        a(this.i, getDrawableState());
        a(this.j, getDrawableState());
        a(r);
        super.drawableStateChanged();
    }

    private Rect g() {
        if (!this.O) {
            return null;
        }
        Rect r = new Rect();
        r.set(this.a.c().getBounds());
        return r;
    }

    private void a(Rect r) {
        if (this.O && r != null) {
            this.a.c().setBounds(r);
        }
    }

    private void a(Drawable d, int[] state) {
        if (d != null) {
            d.setState(state);
        }
    }

    public void setPressed(boolean pressed) {
        if (!pressed || !this.O) {
            super.setPressed(pressed);
        }
    }

    private void b(Context context, AttributeSet attributeSet) {
        TypedArray attr = a(context, attributeSet, j.CircularProgressButton);
        if (attr != null) {
            this.v = attr.getDimensionPixelSize(j.CircularProgressButton_mcCirButtonStrokeWidth, (int) getContext().getResources().getDimension(com.meizu.common.a.d.mc_cir_progress_button_stroke_width));
            this.m = attr.getString(j.CircularProgressButton_mcCirButtonTextIdle);
            this.n = attr.getString(j.CircularProgressButton_mcCirButtonTextComplete);
            this.o = attr.getString(j.CircularProgressButton_mcCirButtonTextError);
            this.p = attr.getString(j.CircularProgressButton_mcCirButtonTextProgress);
            this.t = attr.getResourceId(j.CircularProgressButton_mcCirButtonIconComplete, 0);
            this.u = attr.getResourceId(j.CircularProgressButton_mcCirButtonIconError, 0);
            this.x = attr.getDimension(j.CircularProgressButton_mcCirButtonCornerRadius, 0.0f);
            this.w = attr.getDimensionPixelSize(j.CircularProgressButton_mcCirButtonPaddingProgress, 0);
            int blue = a(com.meizu.common.a.c.mc_cir_progress_button_blue);
            int white = a(com.meizu.common.a.c.mc_cir_progress_button_white);
            int grey = a(com.meizu.common.a.c.mc_cir_progress_button_grey);
            int idleStateSelector = attr.getResourceId(j.CircularProgressButton_mcCirButtonSelectorIdle, com.meizu.common.a.c.mc_cir_progress_button_blue);
            this.d = getResources().getColorStateList(idleStateSelector);
            this.H = getResources().getColorStateList(attr.getResourceId(j.CircularProgressButton_mcCirButtonStrokeColorIdle, idleStateSelector));
            int completeStateSelector = attr.getResourceId(j.CircularProgressButton_mcCirButtonSelectorComplete, com.meizu.common.a.c.mc_cir_progress_button_green);
            this.e = getResources().getColorStateList(completeStateSelector);
            this.I = getResources().getColorStateList(attr.getResourceId(j.CircularProgressButton_mcCirButtonStrokeColorComplete, completeStateSelector));
            int errorStateSelector = attr.getResourceId(j.CircularProgressButton_mcCirButtonSelectorError, com.meizu.common.a.c.mc_cir_progress_button_red);
            this.f = getResources().getColorStateList(errorStateSelector);
            this.J = getResources().getColorStateList(attr.getResourceId(j.CircularProgressButton_mcCirButtonStrokeColorError, errorStateSelector));
            this.q = attr.getColor(j.CircularProgressButton_mcCirButtonColorProgress, white);
            this.r = attr.getColor(j.CircularProgressButton_mcCirButtonColorIndicator, blue);
            this.s = attr.getColor(j.CircularProgressButton_mcCirButtonColorIndicatorBackground, grey);
            this.G = attr.getColorStateList(j.CircularProgressButton_mcCirButtonTextColorError);
            if (this.G == null) {
                this.G = getTextColors();
            }
            this.E = attr.getColorStateList(j.CircularProgressButton_mcCirButtonTextColorIdle);
            if (this.E == null) {
                this.E = getTextColors();
            }
            this.F = attr.getColorStateList(j.CircularProgressButton_mcCirButtonTextColorComplete);
            if (this.F == null) {
                this.F = getTextColors();
            }
            attr.recycle();
        }
    }

    protected int a(int id) {
        return getResources().getColor(id);
    }

    protected TypedArray a(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.l != c.PROGRESS || this.O) {
            if (this.b != null) {
                this.b.a(false);
            }
        } else if (this.y) {
            a(canvas);
        } else {
            b(canvas);
        }
    }

    private void a(Canvas canvas) {
        if (this.b == null) {
            int offset = (getWidth() - getHeight()) / 2;
            this.b = new com.meizu.common.a.b(this.r, (float) this.v);
            int right = (getWidth() - offset) - this.w;
            int bottom = getHeight() - this.w;
            this.b.setBounds(offset + this.w, this.w, right, bottom);
            this.b.setCallback(this);
            this.b.start();
            return;
        }
        this.b.a(true);
        this.b.draw(canvas);
    }

    private void b(Canvas canvas) {
        if (this.c == null) {
            int offset = (getWidth() - getHeight()) / 2;
            this.c = new com.meizu.common.a.c(getHeight() - (this.w * 2), this.v, this.r);
            int left = offset + this.w;
            this.c.setBounds(left, this.w, left, this.w);
        }
        if (this.B) {
            this.B = false;
            this.c.a(this.A);
            if (this.A == null) {
                this.c.a(this.K);
            }
        }
        this.c.a((360.0f / ((float) this.M)) * ((float) this.N));
        this.c.draw(canvas);
    }

    public void setIndeterminateProgressMode(boolean indeterminateProgressMode) {
        this.y = indeterminateProgressMode;
    }

    protected boolean verifyDrawable(Drawable who) {
        return who == this.b || who == this.j || who == this.g || who == this.i || who == this.h || super.verifyDrawable(who);
    }

    private a h() {
        this.O = true;
        setClickable(false);
        this.D = new a(this, this, this.a);
        this.D.a(this.x);
        this.D.b(this.x);
        this.D.b(getWidth());
        this.D.c(getWidth());
        if (this.z || !this.C) {
            this.D.a(1);
        } else {
            this.D.a((int) SlideNotice.SHOW_ANIMATION_DURATION);
        }
        this.z = false;
        return this.D;
    }

    private a a(float fromCorner, float toCorner, int fromWidth, int toWidth) {
        this.O = true;
        setClickable(false);
        this.D = new a(this, this, this.a);
        this.D.a(fromCorner);
        this.D.b(toCorner);
        this.D.c((float) this.w);
        this.D.b(fromWidth);
        this.D.c(toWidth);
        if (this.z || !this.C) {
            this.D.a(1);
        } else {
            this.D.a((int) SlideNotice.SHOW_ANIMATION_DURATION);
        }
        this.z = false;
        return this.D;
    }

    private void i() {
        setWidth(getWidth());
        setText(this.p);
        a animation = a(this.x, (float) getHeight(), getWidth(), getHeight());
        animation.d(a(this.d));
        animation.e(this.q);
        animation.f(a(this.H));
        animation.g(this.s);
        animation.a(this.P);
        setState(c.PROGRESS);
        this.k = this.j;
        animation.a();
    }

    private void j() {
        a animation = a((float) getHeight(), this.x, getHeight(), getWidth());
        animation.d(this.q);
        animation.f(this.r);
        animation.g(a(this.I));
        animation.e(a(this.e));
        animation.a(this.Q);
        setState(c.COMPLETE);
        this.k = this.h;
        animation.a();
    }

    private void k() {
        a animation = h();
        animation.d(a(this.d));
        animation.f(a(this.H));
        animation.e(a(this.e));
        animation.g(a(this.I));
        animation.a(this.Q);
        setState(c.COMPLETE);
        this.k = this.h;
        animation.a();
    }

    private void l() {
        a animation = h();
        animation.d(a(this.e));
        animation.e(a(this.d));
        animation.f(a(this.I));
        animation.g(a(this.H));
        animation.a(this.R);
        setState(c.IDLE);
        this.k = this.g;
        animation.a();
    }

    private void m() {
        a animation = h();
        animation.d(a(this.f));
        animation.e(a(this.d));
        animation.f(a(this.J));
        animation.g(a(this.H));
        animation.a(this.R);
        setState(c.IDLE);
        this.k = this.g;
        animation.a();
    }

    private void n() {
        a animation = h();
        animation.d(a(this.d));
        animation.e(a(this.f));
        animation.f(a(this.H));
        animation.g(a(this.J));
        animation.a(this.S);
        setState(c.ERROR);
        this.k = this.i;
        animation.a();
    }

    private void o() {
        a animation = a((float) getHeight(), this.x, getHeight(), getWidth());
        animation.d(this.q);
        animation.e(a(this.f));
        animation.f(this.r);
        animation.g(a(this.J));
        animation.a(this.S);
        setState(c.ERROR);
        this.k = this.i;
        animation.a();
    }

    private void p() {
        a animation = a((float) getHeight(), this.x, getHeight(), getWidth());
        animation.d(this.q);
        animation.e(a(this.d));
        animation.f(this.r);
        animation.g(a(this.H));
        animation.a(new b(this) {
            final /* synthetic */ CircularProgressButton a;

            {
                this.a = r1;
            }

            public void a() {
                this.a.a();
                this.a.setText(this.a.m);
                this.a.O = false;
                this.a.setClickable(true);
            }
        });
        setState(c.IDLE);
        this.k = this.g;
        animation.a();
    }

    private void setIcon(int icon) {
        Drawable drawable = getResources().getDrawable(icon);
        if (drawable != null) {
            int padding = (getWidth() / 2) - (drawable.getIntrinsicWidth() / 2);
            setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
            setPadding(padding, 0, 0, 0);
        }
    }

    protected void a() {
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        setPadding(0, 0, 0, 0);
    }

    @SuppressLint({"NewApi"})
    public void setBackgroundCompat(Drawable drawable) {
        if (VERSION.SDK_INT >= 16) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    public void setProgress(int progress, boolean useAnim) {
        this.N = progress;
        this.C = useAnim;
        if (!this.O && getWidth() != 0) {
            if (this.N >= this.M) {
                if (this.l == c.PROGRESS) {
                    j();
                } else if (this.l == c.IDLE) {
                    k();
                }
            } else if (this.N > 0) {
                if (this.l == c.IDLE || this.l == c.ERROR) {
                    i();
                } else if (this.l == c.PROGRESS) {
                    invalidate();
                }
            } else if (this.N == -1) {
                if (this.l == c.PROGRESS) {
                    o();
                } else if (this.l == c.IDLE) {
                    n();
                }
            } else if (this.N != 0) {
            } else {
                if (this.l == c.COMPLETE) {
                    l();
                } else if (this.l == c.PROGRESS) {
                    p();
                } else if (this.l == c.ERROR) {
                    m();
                }
            }
        }
    }

    public void setProgress(int progress) {
        setProgress(progress, true);
    }

    public int getProgress() {
        return this.N;
    }

    public void setBackgroundColor(int color) {
        this.a.c().setColor(color);
    }

    public void setStrokeColor(int color) {
        this.a.b(color);
    }

    public String getIdleText() {
        return this.m;
    }

    public String getCompleteText() {
        return this.n;
    }

    public String getErrorText() {
        return this.o;
    }

    public void setIdleText(String text) {
        this.m = text;
    }

    public void setCompleteText(String text) {
        this.n = text;
    }

    public void setErrorText(String text) {
        this.o = text;
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            setState(this.l, false, false);
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.c = this.N;
        savedState.a = this.y;
        savedState.b = true;
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            SavedState savedState = (SavedState) state;
            this.N = savedState.c;
            this.y = savedState.a;
            this.z = savedState.b;
            super.onRestoreInstanceState(savedState.getSuperState());
            setProgress(this.N);
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public void setProgressCenterIcon(Drawable centerIcon) {
        this.A = centerIcon;
        this.B = true;
    }

    public void setShowCenterIcon(boolean showCenterIcon) {
        this.K = showCenterIcon;
        this.B = true;
    }

    private void q() {
        a(c.IDLE, this.g);
        a(c.COMPLETE, this.h);
        a(c.ERROR, this.i);
        a(this.l, this.a.c());
    }

    private void a(c state, Drawable d) {
        if (d != null) {
            if (state == c.PROGRESS) {
                int left = (Math.abs(getWidth() - getHeight()) / 2) + this.w;
                d.setBounds(left, this.w, (getWidth() - left) - this.w, getHeight() - this.w);
                return;
            }
            d.setBounds(0, 0, getRight() - getLeft(), getBottom() - getTop());
        }
    }

    public void setState(c state, boolean useAnim, boolean fromUser) {
        if (state != this.l) {
            this.C = useAnim;
            if (!useAnim) {
                a(state, false);
            } else if (!this.O && getWidth() != 0) {
                switch (state) {
                    case COMPLETE:
                        switch (this.l) {
                            case PROGRESS:
                                j();
                                return;
                            case IDLE:
                                k();
                                return;
                            default:
                                return;
                        }
                    case ERROR:
                        switch (this.l) {
                            case PROGRESS:
                                o();
                                return;
                            case IDLE:
                                n();
                                return;
                            default:
                                return;
                        }
                    case PROGRESS:
                        if (this.l != c.PROGRESS) {
                            i();
                            return;
                        }
                        return;
                    case IDLE:
                        switch (this.l) {
                            case COMPLETE:
                                l();
                                return;
                            case ERROR:
                                m();
                                return;
                            case PROGRESS:
                                p();
                                return;
                            default:
                                return;
                        }
                    default:
                        return;
                }
            }
        }
    }

    private void a(c state, boolean forceUpdate) {
        if (forceUpdate || state != this.l) {
            b();
            String st = "";
            int backgroundColor = a(this.d);
            int strokeColor = a(this.d);
            ColorStateList textColor = getTextColors();
            switch (state) {
                case COMPLETE:
                    backgroundColor = a(this.e);
                    strokeColor = a(this.I);
                    st = this.n;
                    setState(c.COMPLETE);
                    textColor = this.F;
                    this.k = this.h;
                    break;
                case ERROR:
                    backgroundColor = a(this.f);
                    strokeColor = a(this.J);
                    st = this.o;
                    setState(c.ERROR);
                    textColor = this.G;
                    this.k = this.i;
                    break;
                case PROGRESS:
                    backgroundColor = this.q;
                    strokeColor = this.s;
                    setState(c.PROGRESS);
                    this.k = this.j;
                    break;
                case IDLE:
                    backgroundColor = a(this.d);
                    strokeColor = a(this.H);
                    st = this.m;
                    setState(c.IDLE);
                    textColor = this.E;
                    this.k = this.g;
                    break;
            }
            GradientDrawable d = this.a.c();
            if (state == c.PROGRESS) {
                int left = (Math.abs(getWidth() - getHeight()) / 2) + this.w;
                d.setBounds(left, this.w, (getWidth() - left) - this.w, getHeight() - this.w);
            } else {
                d.setBounds(0, 0, getRight() - getLeft(), getBottom() - getTop());
            }
            d.setColor(backgroundColor);
            this.a.a(this.v);
            this.a.b(strokeColor);
            setText(st);
            setTextColor(textColor);
            invalidate();
        }
    }

    public void setProgressForState(int progress) {
        if (this.l == c.PROGRESS) {
            this.N = progress;
            invalidate();
        }
    }

    public c getState() {
        return this.l;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        b();
    }

    public void b() {
        if (this.D != null) {
            this.D.b();
        }
    }

    public void setStateColorSelector(c state, ColorStateList backgroundColorStateList, ColorStateList strokeColorStateList) {
        if (backgroundColorStateList != null && strokeColorStateList != null) {
            switch (state) {
                case COMPLETE:
                    this.e = backgroundColorStateList;
                    this.I = strokeColorStateList;
                    break;
                case ERROR:
                    this.f = backgroundColorStateList;
                    this.J = strokeColorStateList;
                    break;
                case IDLE:
                    this.d = backgroundColorStateList;
                    this.H = strokeColorStateList;
                    break;
            }
            this.a = null;
            this.g = null;
            this.j = null;
            this.h = null;
            this.i = null;
            e();
            f();
            c();
            d();
            if (this.l == state) {
                setBackgroundFromState(state);
            }
            a(this.l, true);
            drawableStateChanged();
        }
    }

    private void setBackgroundFromState(c state) {
        switch (state) {
            case COMPLETE:
                this.k = this.h;
                return;
            case ERROR:
                this.k = this.i;
                return;
            case PROGRESS:
                this.k = this.j;
                return;
            case IDLE:
                this.k = this.g;
                return;
            default:
                return;
        }
    }

    private void setState(c state) {
        if (this.l != state) {
            this.l = state;
        }
    }

    public void setStateTextColor(c state, ColorStateList colorStateList) {
        switch (state) {
            case COMPLETE:
                this.F = colorStateList;
                break;
            case ERROR:
                this.G = colorStateList;
                break;
            case IDLE:
                this.E = colorStateList;
                break;
        }
        if (this.l == state) {
            invalidate();
        }
    }

    public void draw(Canvas canvas) {
        if (this.L || !this.O) {
            this.L = false;
            q();
        }
        if (this.O && isPressed()) {
            super.draw(canvas);
            return;
        }
        if (this.k != null) {
            if ((getScrollX() | getScrollY()) == 0) {
                switch (this.l) {
                    case COMPLETE:
                        a(this.h, canvas);
                        break;
                    case ERROR:
                        a(this.i, canvas);
                        break;
                    case PROGRESS:
                        a(this.j, canvas);
                        break;
                    case IDLE:
                        a(this.g, canvas);
                        break;
                }
            }
            canvas.translate((float) getScrollX(), (float) getScrollY());
            this.k.draw(canvas);
            canvas.translate((float) (-getScrollX()), (float) (-getScrollY()));
        }
        super.draw(canvas);
    }

    private void a(Drawable d, Canvas canvas) {
        if (d != null) {
            d.draw(canvas);
        }
    }

    public void setStateText(c state, String text) {
        switch (state) {
            case COMPLETE:
                this.n = text;
                break;
            case ERROR:
                this.o = text;
                break;
            case IDLE:
                this.m = text;
                break;
        }
        if (this.l == state && !this.O) {
            setTextForState(state);
        }
    }

    private void setTextForState(c state) {
        switch (state) {
            case COMPLETE:
                setText(this.n);
                return;
            case ERROR:
                setText(this.o);
                return;
            case IDLE:
                setText(this.m);
                return;
            default:
                return;
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.b = null;
        this.c = null;
        this.B = true;
        this.L = true;
    }

    private Interpolator getInterpolator() {
        if (VERSION.SDK_INT >= 21) {
            return new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        }
        return new com.meizu.common.b.a(0.33f, 0.0f, 0.1f, 1.0f);
    }

    public void setProgressIndicatorColor(int color) {
        this.r = color;
        this.b = null;
        this.c = null;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(CircularProgressButton.class.getName());
    }
}

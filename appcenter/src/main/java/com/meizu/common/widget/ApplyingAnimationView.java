package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.PathInterpolator;
import com.meizu.common.a.j;

public class ApplyingAnimationView extends View {
    private boolean A;
    private float B;
    private float C;
    private float D;
    private float E;
    private AnimatorSet F;
    private boolean G;
    private boolean H;
    private float I;
    private Paint a;
    private Paint b;
    private Paint c;
    private Paint d;
    private int e;
    private int f;
    private int g;
    private int h;
    private float i;
    private float j;
    private float k;
    private float l;
    private float m;
    private float n;
    private float o;
    private float p;
    private float q;
    private float r;
    private float s;
    private float t;
    private final String[] u;
    private final String[] v;
    private final String[] w;
    private boolean x;
    private boolean y;
    private boolean z;

    public ApplyingAnimationView(Context context) {
        this(context, null, 0);
    }

    public ApplyingAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ApplyingAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.e = 255;
        this.f = 255;
        this.g = 255;
        this.h = 255;
        this.u = new String[]{"crPosition", "cbPosition", "cgPosition", "coPosition"};
        this.v = new String[]{"rAlpha", "bAlpha", "gAlpha", "oAlpha"};
        this.w = new String[]{"crRadius", "cbRadius", "cgRadius", "coRadius"};
        this.x = false;
        this.y = false;
        this.z = false;
        this.A = false;
        this.G = false;
        this.H = false;
        this.I = 1.0f;
        TypedArray a = context.obtainStyledAttributes(attrs, j.ApplyingAnimationView);
        this.I = a.getFloat(j.ApplyingAnimationView_mcApplyingAnimationScale, this.I);
        a.recycle();
        b(context);
    }

    private void b(Context context) {
        c(context);
        this.a = a();
        this.a.setColor(-1357238);
        this.b = a();
        this.b.setColor(-16737828);
        this.c = a();
        this.c.setColor(110475);
        this.d = a();
        this.d.setColor(-620493);
    }

    private void c(Context context) {
        float ratio = a(context) * this.I;
        this.i = 6.0f * ratio;
        this.j = this.i * 0.5f;
        this.B = 0.0f;
        this.C = 12.3f * ratio;
        this.D = 24.0f * ratio;
        this.E = 11.0f * ratio;
        this.k = (getX() + this.j) + (this.I * 2.0f);
        this.l = getY();
    }

    protected void onDraw(Canvas canvas) {
        if (this.m < this.j) {
            canvas.drawCircle(this.k + this.q, this.l + this.i, this.m, this.a);
            this.x = true;
        }
        if (this.n < this.j) {
            canvas.drawCircle(this.k + this.r, this.l + this.i, this.n, this.b);
            this.y = true;
        }
        if (this.o < this.j) {
            canvas.drawCircle(this.k + this.s, this.l + this.i, this.o, this.c);
            this.z = true;
        }
        if (this.p < this.j) {
            canvas.drawCircle(this.k + this.t, this.l + this.i, this.p, this.d);
            this.A = true;
        }
        if (!this.x) {
            canvas.drawCircle(this.k + this.q, this.l + this.i, this.m, this.a);
        }
        if (!this.y) {
            canvas.drawCircle(this.k + this.r, this.l + this.i, this.n, this.b);
        }
        if (!this.z) {
            canvas.drawCircle(this.k + this.s, this.l + this.i, this.o, this.c);
        }
        if (!this.A) {
            canvas.drawCircle(this.k + this.t, this.l + this.i, this.p, this.d);
        }
        this.x = false;
        this.y = false;
        this.z = false;
        this.A = false;
    }

    private Paint a() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL);
        return paint;
    }

    private void b() {
        if (!this.H) {
            Animator rPosition = a(0);
            Animator bPosition = a(1);
            Animator gPosition = a(2);
            Animator oPosition = a(3);
            new AnimatorSet().playTogether(new Animator[]{rPosition, bPosition, gPosition, oPosition});
            Animator rRadiusAnim = b(0);
            Animator bRadiusAnim = b(1);
            Animator gRadiusAnim = b(2);
            Animator oRadiusAnim = b(3);
            new AnimatorSet().playTogether(new Animator[]{rRadiusAnim, bRadiusAnim, gRadiusAnim, oRadiusAnim});
            Animator rAlphaAnim = c(0);
            Animator bAlphaAnim = c(1);
            Animator gAlphaAnim = c(2);
            Animator oAlphaAnim = c(3);
            new AnimatorSet().playTogether(new Animator[]{rAlphaAnim, bAlphaAnim, gAlphaAnim, oAlphaAnim});
            this.F = new AnimatorSet();
            this.F.playTogether(new Animator[]{positionSet, radiusSet, alphaSet});
            this.F.addListener(new AnimatorListenerAdapter(this) {
                final /* synthetic */ ApplyingAnimationView a;

                {
                    this.a = r1;
                }

                public void onAnimationEnd(Animator animation) {
                    if (!this.a.G && this.a.F != null) {
                        this.a.F.start();
                    } else if (this.a.F != null) {
                        this.a.G = false;
                        this.a.H = false;
                    }
                }
            });
            this.H = true;
            this.F.start();
        }
    }

    private Animator a(int begin) {
        array = new ObjectAnimator[4];
        array[0] = ObjectAnimator.ofFloat(this, this.u[begin], new float[]{0.0f, this.C});
        array[0].setDuration(704);
        array[0].setInterpolator(new PathInterpolator(0.21f, 0.0f, 0.35f, 0.471f));
        array[1] = ObjectAnimator.ofFloat(this, this.u[begin], new float[]{this.C, this.D});
        array[1].setDuration(704);
        array[1].setInterpolator(new PathInterpolator(0.24f, 0.341f, 0.41f, 1.0f));
        array[2] = ObjectAnimator.ofFloat(this, this.u[begin], new float[]{this.D, this.E});
        array[2].setDuration(672);
        array[2].setInterpolator(new PathInterpolator(0.26f, 0.0f, 0.87f, 0.758f));
        array[3] = ObjectAnimator.ofFloat(this, this.u[begin], new float[]{this.E, this.B});
        array[3].setDuration(736);
        array[3].setInterpolator(new PathInterpolator(0.18f, 0.434f, 0.59f, 1.0f));
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(new Animator[]{array[begin % 4], array[(begin + 1) % 4], array[(begin + 2) % 4], array[(begin + 3) % 4]});
        return set;
    }

    private Animator b(int begin) {
        array = new ObjectAnimator[4];
        array[0] = ObjectAnimator.ofFloat(this, this.w[begin], new float[]{this.j, this.i});
        array[0].setInterpolator(new PathInterpolator(0.24f, 0.209f, 0.25f, 1.0f));
        array[0].setDuration(720);
        array[1] = ObjectAnimator.ofFloat(this, this.w[begin], new float[]{this.i, this.j});
        array[1].setInterpolator(new PathInterpolator(0.29f, 0.0f, 0.32f, 0.631f));
        array[1].setDuration(704);
        array[2] = ObjectAnimator.ofFloat(this, this.w[begin], new float[]{this.j, this.i * 0.25f});
        array[2].setInterpolator(new PathInterpolator(0.2f, 0.337f, 0.17f, 1.0f));
        array[2].setDuration(704);
        array[3] = ObjectAnimator.ofFloat(this, this.w[begin], new float[]{this.i * 0.25f, this.j});
        array[3].setInterpolator(new PathInterpolator(0.19f, 0.0f, 0.37f, 0.31f));
        array[3].setDuration(688);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(new Animator[]{array[begin % 4], array[(begin + 1) % 4], array[(begin + 2) % 4], array[(begin + 3) % 4]});
        return set;
    }

    private Animator c(int begin) {
        ObjectAnimator[] array = new ObjectAnimator[4];
        array[0] = ObjectAnimator.ofInt(this, this.v[begin], new int[]{255, 255});
        array[0].setDuration(720);
        array[1] = ObjectAnimator.ofInt(this, this.v[begin], new int[]{255, 255});
        array[1].setDuration(704);
        array[2] = ObjectAnimator.ofInt(this, this.v[begin], new int[]{255, 0, 0, 0});
        array[2].setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.4f, 1.0f));
        array[2].setDuration(704);
        array[3] = ObjectAnimator.ofInt(this, this.v[begin], new int[]{0, 255, 255});
        array[3].setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.4f, 1.0f));
        array[3].setDuration(688);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(new Animator[]{array[begin % 4], array[(begin + 1) % 4], array[(begin + 2) % 4], array[(begin + 3) % 4]});
        return set;
    }

    private void setRAlpha(int rAlpha) {
        this.e = Math.round((float) rAlpha);
        this.a.setAlpha(this.e);
    }

    private void setBAlpha(int bAlpha) {
        this.f = Math.round((float) bAlpha);
        this.b.setAlpha(this.f);
    }

    private void setGAlpha(int gAlpha) {
        this.g = Math.round((float) gAlpha);
        this.c.setAlpha(this.g);
    }

    private void setOAlpha(int oAlpha) {
        this.h = Math.round((float) oAlpha);
        this.d.setAlpha(this.h);
    }

    private void setCrRadius(float crRadius) {
        this.m = crRadius;
    }

    private void setCbRadius(float cbRadius) {
        this.n = cbRadius;
    }

    private void setCgRadius(float cgRadius) {
        this.o = cgRadius;
    }

    private void setCoRadius(float coRadius) {
        this.p = coRadius;
    }

    private void setCrPosition(float crPosition) {
        this.q = crPosition;
    }

    private void setCbPosition(float cbPosition) {
        this.r = cbPosition;
    }

    private void setCgPosition(float cgPosition) {
        this.s = cgPosition;
    }

    private void setCoPosition(float coPosition) {
        this.t = coPosition;
        invalidate();
    }

    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility != 0) {
            c();
        } else if (isShown()) {
            b();
            this.G = false;
        }
    }

    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility != 0) {
            c();
        } else if (isShown()) {
            b();
            this.G = false;
        }
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == 0) {
            b();
            this.G = false;
        } else if (visibility == 4 || visibility == 8) {
            c();
        }
    }

    private void c() {
        if (this.F != null) {
            this.F.cancel();
            this.G = true;
            this.H = false;
            this.F = null;
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int dw = Math.round((((this.D - this.B) + this.i) + (this.I * 4.0f)) + 0.5f);
        setMeasuredDimension(resolveSizeAndState(dw + (getPaddingLeft() + getPaddingRight()), widthMeasureSpec, 0), resolveSizeAndState(Math.round((this.i * 2.0f) + 0.5f) + (getPaddingTop() + getPaddingBottom()), heightMeasureSpec, 0));
    }

    public float a(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(ApplyingAnimationView.class.getName());
    }
}

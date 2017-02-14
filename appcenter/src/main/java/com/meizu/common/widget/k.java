package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import com.meizu.cloud.app.drawable.CircularAnimatedDrawable;
import com.meizu.common.a.a;
import com.meizu.common.a.d;
import com.meizu.common.a.h;

public class k {
    private static int A = -1;
    private static int B = 0;
    private static int C = 1;
    private int D = -1;
    private int E = A;
    private int F = C;
    private float G;
    private float H;
    private float I;
    private int J;
    private String K;
    private String L;
    private String M;
    private TypedArray N;
    private TypedArray O;
    private int P = 1500;
    private int Q = 1000;
    private float R = 0.0f;
    private float S = 0.0f;
    private String T;
    private String[] U = new String[]{"chocolate", "coral", "dodgerBlue", "fireBrick", "forestGreeen", "LimeGreen", "peru", "tomato"};
    private final int V = 637534208;
    private int W;
    private int X;
    private int Y;
    private int Z;
    private View a = null;
    private int aa;
    private Animator ab;
    private float ac;
    private float ad;
    private final long ae = 1760;
    private final long af = 1120;
    private float ag = 0.0f;
    private float ah = 0.0f;
    private Handler ai = new Handler();
    private Runnable aj = new Runnable(this) {
        final /* synthetic */ k a;

        {
            this.a = r1;
        }

        public void run() {
            if (this.a.r || this.a.s) {
                if (this.a.s) {
                    this.a.a.postInvalidate(0, this.a.v, this.a.a.getWidth(), ((int) this.a.y) + this.a.v);
                    this.a.ai.postDelayed(this, (long) (this.a.P / this.a.J));
                } else if (this.a.r) {
                    this.a.a.postInvalidate((int) (((((float) (this.a.a.getWidth() / 2)) - this.a.p) - this.a.q) - 10.0f), (int) (((this.a.b.top - 5.0f) + ((float) this.a.a.getScrollY())) + this.a.R), (int) (((((float) (this.a.a.getWidth() / 2)) + this.a.p) + this.a.q) + 10.0f), (int) (((this.a.b.bottom + 5.0f) + ((float) this.a.a.getScrollY())) + this.a.R));
                    this.a.ai.postDelayed(this, (long) (this.a.Q / this.a.J));
                }
            }
            if (!this.a.r && this.a.ab != null) {
                this.a.ab.cancel();
                this.a.ab = null;
            }
        }
    };
    private RectF b = null;
    private RectF c = null;
    private Paint d = null;
    private Paint e = null;
    private Paint f = null;
    private Paint g = null;
    private Paint h = null;
    private int i = 0;
    private int j;
    private int k;
    private int l;
    private int m;
    private float n;
    private float o;
    private float p = 30.0f;
    private float q = 5.0f;
    private boolean r = false;
    private boolean s = false;
    private boolean t = false;
    private long u;
    private int v;
    private int w = 40;
    private int x = 30;
    private float y = 0.0f;
    private float z = 3.0f;

    public k(Context context, int colorValue, int drawType, View view) {
        this.j = context.getResources().getDimensionPixelOffset(d.mc_pullRefresh_holdheight);
        this.k = context.getResources().getDimensionPixelOffset(d.mc_pullRefresh_overscrollheight);
        this.l = context.getResources().getDimensionPixelOffset(d.mc_pullRefresh_showarcheight);
        this.p = context.getResources().getDimension(d.mc_pullRefresh_radius);
        this.q = context.getResources().getDimension(d.mc_pullRefresh_ringwidth);
        this.G = context.getResources().getDimension(d.mc_pullRefresh_maxheight);
        this.H = context.getResources().getDimension(d.mc_pullRefresh_minheight);
        this.I = context.getResources().getDimension(d.mc_pullRefresh_animheight);
        this.w = context.getResources().getDimensionPixelOffset(d.mc_pullRefresh_textsize);
        this.x = context.getResources().getDimensionPixelOffset(d.mc_pullRefresh_textmargintop);
        this.N = context.getResources().obtainTypedArray(a.mc_pullline_move_start);
        this.O = context.getResources().obtainTypedArray(a.mc_pullline_move_end);
        this.J = this.O.length();
        this.z = context.getResources().getDimension(d.mc_pullRefresh_paintoffset);
        this.F = drawType;
        this.E = colorValue;
        String string = context.getResources().getString(h.mc_pull_refresh);
        this.K = string;
        this.T = string;
        this.L = context.getResources().getString(h.mc_is_Refreshing);
        this.M = context.getResources().getString(h.mc_go_Refreshing);
        this.m = 0;
        this.d = new Paint(1);
        this.d.setAntiAlias(true);
        this.d.setColor(this.D);
        this.d.setAntiAlias(true);
        this.d.setTextAlign(Align.CENTER);
        this.d.setTextSize((float) this.w);
        this.e = new Paint(1);
        this.e.setAntiAlias(true);
        this.e.setColor(this.E);
        this.e.setStyle(Style.STROKE);
        this.e.setStrokeCap(Cap.ROUND);
        this.e.setStrokeWidth(this.q);
        this.f = new Paint(1);
        this.f.setAntiAlias(true);
        this.f.setColor(637534208);
        this.f.setStyle(Style.STROKE);
        this.f.setStrokeWidth(this.q);
        this.g = new Paint(1);
        this.g.setAntiAlias(true);
        this.g.setColor(this.E);
        this.g.setStyle(Style.FILL);
        this.h = new Paint(1);
        this.h.setAntiAlias(true);
        this.h.setColor(-1);
        this.h.setStyle(Style.FILL);
        this.h.setAlpha(102);
        this.W = this.E;
        this.Y = Color.alpha(this.W);
        this.X = 637534208;
        this.Z = Color.alpha(this.X);
        this.aa = Color.alpha(this.D);
        this.ag = -this.d.getFontMetrics().ascent;
        this.ah = (((float) (this.l + this.x)) + this.d.getTextSize()) / 2.0f;
        a(view);
    }

    public void a(View view) {
        this.a = view;
        this.b = new RectF();
        this.c = new RectF(0.0f, 0.0f, (float) this.a.getWidth(), 100.0f);
        this.n = this.a.getX() + ((float) (this.a.getWidth() / 2));
        this.o = (((this.a.getY() - this.p) - this.q) - ((float) this.x)) - this.d.getTextSize();
        this.b.left = (this.n - this.p) - (this.q / 2.0f);
        this.b.top = (this.o - this.p) - (this.q / 2.0f);
        this.b.right = (this.n + this.p) + (this.q / 2.0f);
        this.b.bottom = (this.o + this.p) + (this.q / 2.0f);
    }

    @SuppressLint({"NewApi"})
    private void a(Canvas canvas) {
        long nowTime = AnimationUtils.currentAnimationTimeMillis();
        int restoreCount = canvas.save();
        if (((float) this.i) <= this.G / 4.0f) {
            this.y = (float) this.i;
        } else if (((float) this.i) <= this.G / 4.0f || this.i > this.j) {
            this.y = this.G;
        } else {
            this.y = (this.G / 4.0f) + ((((this.G * 3.0f) / 4.0f) * (((float) this.i) - (this.G / 4.0f))) / (((float) this.j) - (this.G / 4.0f)));
        }
        canvas.translate(0.0f, (float) this.a.getScrollY());
        Canvas canvas2 = canvas;
        canvas2.drawRect(0.0f, (float) this.v, (float) this.a.getWidth(), this.y + ((float) this.v), this.g);
        if (this.s) {
            float endX;
            int index = a((nowTime - this.u) % ((long) this.P));
            float startX = 0.0f;
            if (index >= this.N.length() || index >= this.O.length()) {
                endX = 0.0f;
            } else {
                startX = this.N.getFloat(index, 0.0f) * ((float) this.a.getWidth());
                endX = this.O.getFloat(index, 0.0f) * ((float) this.a.getWidth());
            }
            canvas.drawRect(endX, (float) this.v, startX, (((float) this.v) + this.y) - this.z, this.h);
        }
        canvas.restoreToCount(restoreCount);
    }

    private void b(Canvas canvas) {
        this.e.setAlpha(this.Y);
        this.f.setAlpha(this.Z);
        this.d.setAlpha(this.aa);
        if (this.i < this.j) {
            this.R = ((float) this.v) + (((float) this.i) - this.ah);
        } else {
            this.R = ((float) this.v) + (((float) this.j) - this.ah);
        }
        int restoreCount = canvas.save();
        if (this.i >= this.l) {
            canvas.translate((float) (this.a.getWidth() / 2), this.R + ((float) this.a.getScrollY()));
            float distance = 0.0f;
            if (this.i <= this.j && this.i >= this.l) {
                distance = (float) (((this.i - this.l) * 360) / (this.j - this.l));
            } else if (this.i < this.l) {
                distance = 0.0f;
            } else if (this.i > this.j) {
                distance = 360.0f;
            }
            float baseX = this.n;
            this.S = (((float) this.x) + ((this.o + this.p) + this.q)) + this.ag;
            float fraction = distance / 360.0f;
            this.f.setAlpha((int) (((float) this.Z) * fraction));
            canvas.drawArc(this.b, -90.0f, 360.0f, false, this.f);
            if (this.r) {
                canvas.drawArc(this.b, this.ac, this.ad, false, this.e);
                canvas.drawText(this.L, baseX, this.S, this.d);
            } else if (this.i >= this.j && !this.t) {
                canvas.drawText(this.M, baseX, this.S, this.d);
                canvas.drawArc(this.b, -90.0f, distance, false, this.e);
            } else if (this.t) {
                this.e.setAlpha((int) (((float) this.Y) * fraction));
                this.d.setAlpha((int) (((float) this.aa) * fraction));
                canvas.drawArc(this.b, this.ac, this.ad, false, this.e);
                canvas.drawText(this.L, baseX, this.S, this.d);
            } else {
                this.e.setAlpha((int) (((float) this.Y) * fraction));
                this.d.setAlpha((int) (((float) this.aa) * fraction));
                canvas.drawText(this.K, baseX, this.S, this.d);
                canvas.drawArc(this.b, -90.0f, distance, false, this.e);
            }
            canvas.restoreToCount(restoreCount);
        }
    }

    private int a(long ctime) {
        return (int) ((((long) this.J) * ctime) / ((long) this.P));
    }

    public void a(int currentOverScrollDistance, Canvas canvas) {
        if (currentOverScrollDistance > 0) {
            this.i = Math.abs(currentOverScrollDistance);
        } else {
            this.i = Math.abs(currentOverScrollDistance);
        }
        if (C == this.F) {
            if (this.i > this.l && this.i < this.j) {
                this.r = false;
            } else if (this.i < this.j) {
                this.r = false;
            }
            b(canvas);
        } else if (B == this.F) {
            if (((float) this.i) < (this.G / 4.0f) - 1.0f) {
                this.s = false;
            }
            a(canvas);
        }
    }

    public void a(boolean isSpringBack) {
        this.t = isSpringBack;
    }

    public void a() {
        this.s = false;
    }

    public void b() {
        if (!this.r && !this.s) {
            if (this.F == B) {
                this.s = true;
                this.r = false;
            } else if (this.F == C) {
                this.s = false;
                this.r = true;
            }
            this.u = AnimationUtils.currentAnimationTimeMillis();
            if (this.r && this.ab == null) {
                this.ab = g();
                this.ab.start();
            }
            if (this.s || this.r) {
                this.ai.postDelayed(this.aj, 1);
            }
        }
    }

    public void a(int offset) {
        this.v = offset;
    }

    public void b(int distance) {
        this.k = distance;
    }

    public void c(int colorValue) {
        this.D = colorValue;
        this.aa = Color.alpha(this.D);
        if (this.d != null) {
            this.d.setColor(this.D);
        }
    }

    public int c() {
        return this.D;
    }

    public void a(String lastRefreshTime) {
        this.K = lastRefreshTime;
    }

    public void d() {
        if (this.ai != null) {
            this.ai.removeCallbacksAndMessages(null);
        }
        if (this.ab != null) {
            this.ab.end();
            this.ab = null;
        }
    }

    public void d(int color) {
        if (this.e != null) {
            this.e.setColor(color);
            this.W = color;
            this.Y = Color.alpha(this.W);
        }
    }

    public int e() {
        return this.W;
    }

    public void e(int color) {
        if (this.f != null) {
            this.f.setColor(color);
            this.X = color;
            this.Z = Color.alpha(this.X);
        }
    }

    public int f() {
        return this.X;
    }

    private Animator g() {
        PropertyValuesHolder pvhStart = PropertyValuesHolder.ofFloat(CircularAnimatedDrawable.START_ANGLE_PROPERTY, new float[]{-90.0f, 270.0f});
        PropertyValuesHolder pvhSweep = PropertyValuesHolder.ofFloat(CircularAnimatedDrawable.SWEEP_ANGLE_PROPERTY, new float[]{-360.0f, 0.0f});
        ObjectAnimator beforeLoadingAnim = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{pvhStart, pvhSweep});
        beforeLoadingAnim.setDuration(1120);
        beforeLoadingAnim.setInterpolator(new LinearInterpolator());
        Animator loadingAnim = h();
        AnimatorSet setList = new AnimatorSet();
        setList.play(beforeLoadingAnim).before(loadingAnim);
        return setList;
    }

    private Animator h() {
        Keyframe key1 = Keyframe.ofFloat(0.0f, -90.0f);
        Keyframe key2 = Keyframe.ofFloat(0.5f, 330.0f);
        Keyframe key3 = Keyframe.ofFloat(1.0f, 630.0f);
        PropertyValuesHolder pvhStart = PropertyValuesHolder.ofKeyframe(CircularAnimatedDrawable.START_ANGLE_PROPERTY, new Keyframe[]{key1, key2, key3});
        PropertyValuesHolder pvhSweep = PropertyValuesHolder.ofFloat(CircularAnimatedDrawable.SWEEP_ANGLE_PROPERTY, new float[]{0.2f, -150.0f, 0.0f});
        ObjectAnimator loadingAnim = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{pvhStart, pvhSweep});
        loadingAnim.setDuration(1760);
        loadingAnim.setInterpolator(new LinearInterpolator());
        loadingAnim.setRepeatCount(-1);
        return loadingAnim;
    }
}

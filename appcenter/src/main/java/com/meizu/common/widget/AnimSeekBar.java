package com.meizu.common.widget;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.meizu.common.a.b;
import com.meizu.common.a.j;
import org.apache.commons.io.FileUtils;

public class AnimSeekBar extends SkposSeekBar implements OnGestureListener {
    private int A;
    private int B;
    private int C;
    private Rect D;
    private Paint E;
    private Resources F;
    private GestureDetector G;
    private ValueAnimator H;
    private ValueAnimator I;
    private ValueAnimator J;
    private float a;
    private Interpolator b;
    private Interpolator c;
    private Interpolator d;
    private Interpolator e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private float k;
    private float l;
    private float m;
    private int n;
    private boolean o;
    private boolean p;
    private boolean q;
    private boolean r;
    private float s;
    private float t;
    private Drawable u;
    private Drawable v;
    private int w;
    private int x;
    private int y;
    private String z;

    public AnimSeekBar(Context context) {
        this(context, null);
    }

    public AnimSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, b.MeizuCommon_AnimSeekBarStyle);
    }

    public AnimSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.a = 0.0f;
        this.h = 0;
        this.k = 0.0f;
        this.m = 0.0f;
        this.o = false;
        this.p = false;
        this.q = false;
        this.r = false;
        this.A = 500;
        this.D = new Rect();
        this.G = null;
        a(context, attrs, defStyle);
    }

    private void a(Context context, AttributeSet attrs, int defStyle) {
        this.F = context.getResources();
        this.G = new GestureDetector(context, this);
        TypedArray seek = context.obtainStyledAttributes(attrs, j.AnimSeekBar, defStyle, 0);
        this.u = seek.getDrawable(j.AnimSeekBar_mcLargeCircleDrawble);
        this.w = (int) seek.getDimension(j.AnimSeekBar_mcLargeCircleRadis, 15.0f);
        this.y = seek.getColor(j.AnimSeekBar_mcTextNumberColor, -1);
        this.x = (int) seek.getDimension(j.AnimSeekBar_mcDistanceToCircle, 40.0f);
        this.C = (int) seek.getDimension(j.AnimSeekBar_mcTextNumberSize, 15.0f);
        this.i = (int) TypedValue.applyDimension(1, 24.0f, this.F.getDisplayMetrics());
        this.B = (int) TypedValue.applyDimension(1, 65.0f, this.F.getDisplayMetrics());
        this.E = new Paint();
        this.E.setColor(this.y);
        this.E.setAntiAlias(true);
        this.E.setTextSize((float) this.C);
        seek.recycle();
        this.f = VERSION.SDK_INT;
        if (this.f >= 21) {
            this.b = new PathInterpolator(0.0f, 0.0f, 0.1f, 1.0f);
            this.c = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
            this.d = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
            this.e = new PathInterpolator(0.66f, 0.0f, 0.67f, 1.0f);
            return;
        }
        this.b = new AccelerateInterpolator();
        this.c = new AccelerateInterpolator();
        this.d = new AccelerateInterpolator();
        this.e = new AccelerateInterpolator();
    }

    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        this.n = (int) (((float) ((((this.w + this.x) + this.i) + getPaddingTop()) + getPaddingBottom())) + this.l);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (measureWidthMode == Integer.MIN_VALUE) {
            width = measureWidth;
        } else if (measureWidthMode == FileUtils.ONE_GB) {
            width = measureWidth;
        } else {
            width = this.A;
        }
        if (measureHeightMode == Integer.MIN_VALUE) {
            height = this.n;
        } else if (measureHeightMode != FileUtils.ONE_GB) {
            height = this.n;
        } else if (measureHeight < this.B) {
            height = this.B;
            this.w = (int) TypedValue.applyDimension(1, 15.0f, this.F.getDisplayMetrics());
            this.x = (int) TypedValue.applyDimension(1, 10.0f, this.F.getDisplayMetrics());
        } else {
            height = this.n;
        }
        setMeasuredDimension(width, height);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.v = getProgressDrawable();
        Drawable thumbDrable = getThumb();
        if (thumbDrable != null && this.v != null) {
            this.g = thumbDrable.getBounds().width() / 2;
            this.k = (float) this.g;
            this.m = (float) (this.g / 2);
            this.l = (float) thumbDrable.getBounds().height();
            this.a = (float) this.g;
            setmY((float) this.v.getBounds().centerY());
            if (this.f <= 21) {
                getThumb().setBounds(thumbDrable.getBounds().centerX() - this.g, this.v.getBounds().centerY() - this.g, thumbDrable.getBounds().centerX() + this.g, this.v.getBounds().centerY() + this.g);
            }
        }
    }

    protected synchronized void onDraw(Canvas canvas) {
        canvas.translate(0.0f, (((float) (this.n / 2)) - this.l) - ((float) getPaddingBottom()));
        canvas.save();
        if (!(getThumb() == null || this.u == null)) {
            this.t = (float) ((getThumb().getBounds().centerX() + getPaddingLeft()) - (getThumb().getIntrinsicWidth() / 2));
            float top = (float) ((int) getThumb().getBounds().exactCenterY());
            getThumb().setBounds((int) (((float) getThumb().getBounds().centerX()) - this.a), (int) (top - this.a), (int) (this.a + ((float) getThumb().getBounds().centerX())), (int) (this.a + ((float) ((int) getThumb().getBounds().exactCenterY()))));
            this.D.set((int) (this.t - ((float) this.w)), (int) (((this.s - ((float) this.w)) - ((float) this.j)) - ((float) this.x)), (int) (this.t + ((float) this.w)), (int) (((this.s + ((float) this.w)) - ((float) this.j)) - ((float) this.x)));
            this.u.setBounds(this.D);
            this.u.setAlpha(this.h);
            this.u.draw(canvas);
            if (this.h > 100) {
                this.z = Integer.toString(getProgress());
            } else {
                this.z = "";
            }
            if (this.z.length() > 4) {
                this.z = this.z.substring(0, 4);
            }
            this.E.getTextBounds(this.z, 0, this.z.length(), this.D);
            this.E.setTextAlign(Align.CENTER);
            FontMetricsInt fontMetrics = this.E.getFontMetricsInt();
            canvas.drawText(this.z, this.t, (float) ((int) ((((this.s - ((float) this.j)) - ((float) this.x)) - ((float) ((fontMetrics.bottom - fontMetrics.top) / 2))) - ((float) fontMetrics.top))), this.E);
        }
        super.onDraw(canvas);
    }

    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (!isEnabled()) {
            return false;
        }
        this.G.onTouchEvent(event);
        switch (event.getAction()) {
            case 1:
            case 3:
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                if (!this.p) {
                    a(event.getX(), event.getY());
                    break;
                }
                break;
            case 2:
                if (this.q && getThumb() != null) {
                    float thumbCenterX = (float) getThumb().getBounds().centerX();
                    float thumbCenterY = (float) getThumb().getBounds().centerY();
                    getThumb().setBounds((int) (thumbCenterX - ((float) (this.g / 2))), (int) (thumbCenterY - ((float) (this.g / 2))), (int) (((float) (this.g / 2)) + thumbCenterX), (int) (((float) (this.g / 2)) + thumbCenterY));
                }
                invalidate();
                break;
        }
        return true;
    }

    private void a(float f, float g) {
        if (this.o) {
            this.o = false;
        }
        b();
        this.q = false;
    }

    public boolean onDown(MotionEvent e) {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return false;
    }

    public void onShowPress(MotionEvent e) {
        if (!this.o) {
            a();
            this.o = true;
        }
    }

    public boolean onSingleTapUp(MotionEvent e) {
        if (this.a != this.k || this.o) {
            this.p = false;
        } else {
            this.p = true;
        }
        return true;
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        this.p = false;
        this.q = true;
        if (getThumb() != null) {
            float thumbCenterX = (float) getThumb().getBounds().centerX();
            float thumbCenterY = (float) getThumb().getBounds().centerY();
            getThumb().setBounds((int) (thumbCenterX - ((float) (this.g / 2))), (int) (thumbCenterY - ((float) (this.g / 2))), (int) (((float) (this.g / 2)) + thumbCenterX), (int) (((float) (this.g / 2)) + thumbCenterY));
        }
        if (this.o) {
            this.H.end();
            this.a = this.m;
        } else {
            this.o = true;
            a();
        }
        invalidate();
        return true;
    }

    public void onLongPress(MotionEvent e) {
        this.p = false;
        this.q = true;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (getThumb() != null) {
            b();
            invalidate();
        }
        this.q = false;
        return true;
    }

    private void a() {
        this.H = ValueAnimator.ofFloat(new float[]{(float) this.g, (float) (this.g / 2)});
        this.H.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ AnimSeekBar a;

            {
                this.a = r1;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                this.a.setSize(((Float) animation.getAnimatedValue()).floatValue());
                this.a.invalidate();
            }
        });
        this.H.setInterpolator(this.b);
        this.H.setDuration(166);
        this.H.start();
        this.I = ValueAnimator.ofInt(new int[]{0, 255});
        this.I.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ AnimSeekBar a;

            {
                this.a = r1;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                this.a.setFadeValue(((Integer) animation.getAnimatedValue()).intValue());
                this.a.invalidate();
            }
        });
        this.I.setInterpolator(this.c);
        this.I.setDuration(166);
        this.I.start();
        this.J = ValueAnimator.ofInt(new int[]{0, this.i});
        this.J.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ AnimSeekBar a;

            {
                this.a = r1;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                this.a.setMoveValue(((Integer) animation.getAnimatedValue()).intValue());
                this.a.invalidate();
            }
        });
        this.J.setDuration(166);
        this.J.setInterpolator(this.d);
        this.J.start();
    }

    private void b() {
        this.H = ValueAnimator.ofFloat(new float[]{(float) (this.g / 2), (float) this.g});
        this.H.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ AnimSeekBar a;

            {
                this.a = r1;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                this.a.setSize(((Float) animation.getAnimatedValue()).floatValue());
                this.a.invalidate();
            }
        });
        this.H.setInterpolator(this.b);
        this.H.setDuration(166);
        this.H.start();
        this.I = ValueAnimator.ofInt(new int[]{255, 0});
        this.I.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ AnimSeekBar a;

            {
                this.a = r1;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                this.a.setFadeValue(((Integer) animation.getAnimatedValue()).intValue());
                this.a.invalidate();
            }
        });
        this.I.setInterpolator(this.c);
        this.I.setDuration(166);
        this.I.start();
        this.J = ValueAnimator.ofInt(new int[]{this.i, 0});
        this.J.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ AnimSeekBar a;

            {
                this.a = r1;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                this.a.setMoveValue(((Integer) animation.getAnimatedValue()).intValue());
                this.a.invalidate();
            }
        });
        this.J.setDuration(166);
        this.J.setInterpolator(this.e);
        this.J.start();
    }

    public void setTextNumberColor(int numberColor) {
        if (this.y != numberColor) {
            this.y = numberColor;
            this.E.setColor(this.y);
            postInvalidate();
        }
    }

    public void setTextNumberSize(int numberSize) {
        Context c = getContext();
        if (this.F == null) {
            this.F = Resources.getSystem();
        } else {
            this.F = c.getResources();
        }
        if (numberSize != this.C) {
            this.C = (int) TypedValue.applyDimension(2, (float) numberSize, this.F.getDisplayMetrics());
            this.E.setTextSize((float) this.C);
            requestLayout();
        }
        invalidate();
    }

    public void setLargeCircleDrawble(Drawable drawble) {
        if (this.u != drawble) {
            int oldBoundsWidth = this.u.getBounds().width();
            int oldBoundsHeight = this.u.getBounds().height();
            int newBoundsWith = -1;
            int newBoundsHeiht = -1;
            this.u = drawble;
            if (drawble != null) {
                newBoundsWith = drawble.getBounds().width();
                newBoundsHeiht = drawble.getBounds().height();
            }
            if (!(oldBoundsWidth == newBoundsWith && oldBoundsHeight == newBoundsHeiht)) {
                requestLayout();
            }
            invalidate();
        }
    }

    public void setLargeCircleRadis(int largeCircleRadis) {
        Context c = getContext();
        if (this.F == null) {
            this.F = Resources.getSystem();
        } else {
            this.F = c.getResources();
        }
        int larCircleRadis = (int) TypedValue.applyDimension(1, (float) largeCircleRadis, this.F.getDisplayMetrics());
        if (this.w != largeCircleRadis) {
            this.w = larCircleRadis;
            requestLayout();
        }
        invalidate();
    }

    public void setDistanceToCircle(int distanceBew) {
        Context c = getContext();
        if (this.F == null) {
            this.F = Resources.getSystem();
        } else {
            this.F = c.getResources();
        }
        int distance = (int) TypedValue.applyDimension(1, (float) distanceBew, this.F.getDisplayMetrics());
        if (distanceBew != this.x) {
            this.x = distance;
            this.r = true;
            requestLayout();
        }
        invalidate();
    }

    public Drawable getLargeCircleDrawble() {
        if (this.u != null) {
            return this.u;
        }
        return null;
    }

    public int getTextNumberColor() {
        return this.y;
    }

    public int getTextNumberSize() {
        return this.C;
    }

    public int getDistanceToCircle() {
        return this.x;
    }

    public int getLargeCircleRadius() {
        return this.w;
    }

    private void setmY(float y) {
        this.s = y;
    }

    private void setFadeValue(int fadevalue) {
        this.h = fadevalue;
    }

    private void setMoveValue(int moveUpValue) {
        this.j = moveUpValue;
    }

    private void setSize(float thumbRadius) {
        this.a = (float) ((int) thumbRadius);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(AnimSeekBar.class.getName());
    }
}

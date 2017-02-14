package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Scroller;
import com.meizu.common.a.j;
import java.util.List;

public class HorizontalWheelView extends View {
    private Paint A;
    private a B;
    private int C;
    private Paint D;
    private Path E;
    private float F;
    private int G;
    private float H;
    private float I;
    private boolean J;
    private float K;
    private boolean L;
    private int M;
    private int N;
    private boolean O;
    private float P;
    private float Q;
    private int R;
    private boolean S;
    private boolean T;
    private boolean U;
    private float a;
    private float b;
    private float c;
    private int d;
    private int e;
    private Paint f;
    private float g;
    private int h;
    private List<String> i;
    private boolean j;
    private boolean k;
    private boolean l;
    private float m;
    private float n;
    private float o;
    private int p;
    private float q;
    private int r;
    private int s;
    private float t;
    private float u;
    private int v;
    private Scroller w;
    private VelocityTracker x;
    private int y;
    private int z;

    public interface a {
        void a(float f);
    }

    public HorizontalWheelView(Context context) {
        this(context, null);
    }

    public HorizontalWheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.c = 18.0f;
        this.d = -65536;
        this.e = -16777216;
        this.g = 18.0f;
        this.h = 100;
        this.k = true;
        this.m = 2.0f;
        this.n = 1.0f;
        this.p = 0;
        this.q = 20.0f;
        this.t = 10.0f;
        this.u = 10.0f;
        this.y = -16777216;
        this.z = -16777216;
        this.C = 5;
        this.F = 0.0f;
        this.H = 5.0f;
        this.I = 0.0f;
        this.J = true;
        this.R = -1;
        this.S = false;
        this.T = false;
        this.U = false;
        a();
        a(context, attrs);
        this.f = new TextPaint(1);
        this.f.setTextSize(this.g);
        this.f.setColor(this.e);
        Rect textBound = new Rect();
        this.f.getTextBounds("0", 0, 1, textBound);
        this.b = (((float) getPaddingTop()) + this.t) + ((float) textBound.height());
        this.a = this.b + this.c;
        this.A = new Paint(1);
        this.A.setColor(this.y);
        if (this.L) {
            this.A.setStrokeCap(Cap.ROUND);
        }
        this.u *= this.o;
        this.E = new Path();
        this.D = new Paint(1);
        this.D.setStyle(Style.FILL);
        this.D.setColor(this.d);
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        if (accessibilityManager != null) {
            this.S = accessibilityManager.isEnabled();
        }
        if (this.S) {
            setFocusable(true);
        }
        f();
    }

    private void a() {
        this.w = new Scroller(getContext());
        this.o = (float) ((int) getContext().getResources().getDisplayMetrics().density);
        this.g *= this.o;
        this.q *= this.o;
        this.v = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
        this.c *= this.o;
        this.n *= this.o;
        this.m *= this.o;
        this.t *= this.o;
        this.H *= this.o;
        this.K = ((float) this.h) * this.q;
        this.P = 3.0f * this.o;
        this.Q = 15.0f * this.o;
    }

    private void a(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, j.HorizontalWheelView, 0, 0);
        int length = a.getIndexCount();
        for (int i = 0; i < length; i++) {
            int index = a.getIndex(i);
            if (index == j.HorizontalWheelView_mcScaleDistance) {
                this.q = (float) ((int) a.getDimension(index, this.q));
            } else if (index == j.HorizontalWheelView_mcTextColor) {
                this.e = a.getColor(index, -16777216);
            } else if (index == j.HorizontalWheelView_mcTextSize) {
                this.g = (float) ((int) a.getDimension(index, this.g));
                this.K = ((float) this.h) * this.q;
            } else if (index == j.HorizontalWheelView_mcSelectedColor) {
                this.d = a.getColor(index, -65536);
            } else if (index == j.HorizontalWheelView_mcLineColor) {
                this.y = a.getColor(index, -16777216);
            } else if (index == j.HorizontalWheelView_mcLineWidth) {
                this.m = a.getDimension(index, this.m);
            } else if (index == j.HorizontalWheelView_mcLineHeight) {
                this.c = a.getDimension(index, this.c);
            } else if (index == j.HorizontalWheelView_mcLittleLineWidth) {
                this.n = a.getDimension(index, this.n);
            } else if (index == j.HorizontalWheelView_mcLittleLineColor) {
                this.z = a.getColor(index, -16777216);
            } else if (index == j.HorizontalWheelView_mcTriangleSideLength) {
                this.u = a.getDimension(index, this.u);
            } else if (index == j.HorizontalWheelView_mcShowNumber) {
                this.C = a.getInt(index, this.C);
            } else if (index == j.HorizontalWheelView_mcTextMarginBottom) {
                this.t = a.getDimension(index, this.t);
            } else if (index == j.HorizontalWheelView_mcLineMarginBottom) {
                this.H = a.getDimension(index, this.H);
            } else if (index == j.HorizontalWheelView_mcDamping) {
                this.I = a.getFloat(index, this.I);
                if (this.I > 1.0f) {
                    this.I = 1.0f;
                } else if (this.I < 0.0f) {
                    this.I = 0.0f;
                }
            } else if (index == j.HorizontalWheelView_mcPaintRound) {
                this.L = a.getBoolean(index, false);
            }
        }
        a.recycle();
    }

    public float getSelected() {
        return (float) this.p;
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        this.s = getWidth();
        if (this.s != 0 && this.k) {
            this.F = ((float) this.p) * this.q;
            b();
            this.G = (((int) (((float) this.s) / this.q)) / 2) + 1;
            this.k = false;
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    private void b() {
        this.N = this.s / 2;
        float top = this.a + this.H;
        float bottom = top + ((float) ((int) (Math.sin(1.0471975511965976d) * ((double) this.u))));
        this.E.moveTo((float) this.N, top);
        this.E.lineTo(((float) this.N) - (this.u / 2.0f), bottom);
        this.E.lineTo(((float) this.N) + (this.u / 2.0f), bottom);
        this.E.close();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        b(canvas);
        a(canvas);
    }

    private void a(Canvas canvas) {
        canvas.drawPath(this.E, this.D);
    }

    private void b(Canvas canvas) {
        canvas.save();
        float offset = this.F % this.q;
        int middleItem = (int) (this.F / this.q);
        if (middleItem != this.p) {
            this.p = middleItem;
            d();
            if (this.O && this.p == this.R) {
                f();
            }
        }
        float middle = ((float) (this.s / 2)) - offset;
        int i = 0;
        while (i < this.G) {
            float xPosition = middle + (((float) i) * this.q);
            if (((float) getPaddingRight()) + xPosition < ((float) this.s) && middleItem + i <= this.h) {
                if ((middleItem + i) % this.C == 0) {
                    String text = a(middleItem + i);
                    PointF textPoint = a(text, this.f, xPosition);
                    canvas.drawText(text, textPoint.x, textPoint.y, this.f);
                    a(this.y, this.m, xPosition);
                    canvas.drawLine(xPosition, this.b, xPosition, this.a, this.A);
                } else {
                    a(this.z, this.n, xPosition);
                    Canvas canvas2 = canvas;
                    float f = xPosition;
                    canvas2.drawLine(xPosition, (this.c / 4.0f) + this.b, f, this.a - (this.c / 4.0f), this.A);
                }
            }
            xPosition = middle - (((float) i) * this.q);
            if (xPosition > ((float) getPaddingLeft()) && middleItem - i >= 0) {
                if ((middleItem - i) % this.C == 0) {
                    text = a(middleItem - i);
                    textPoint = a(text, this.f, xPosition);
                    canvas.drawText(text, textPoint.x, textPoint.y, this.f);
                    a(this.y, this.m, xPosition);
                    canvas.drawLine(xPosition, this.b, xPosition, this.a, this.A);
                } else {
                    a(this.z, this.n, xPosition);
                    this.A.setStrokeWidth(this.n);
                    canvas2 = canvas;
                    f = xPosition;
                    canvas2.drawLine(xPosition, (this.c / 4.0f) + this.b, f, this.a - (this.c / 4.0f), this.A);
                }
            }
            i++;
        }
        canvas.restore();
    }

    private String a(int index) {
        if (this.i == null || this.i.size() <= 0 || index >= this.i.size() || index < 0) {
            return String.valueOf(index);
        }
        return (String) this.i.get(index);
    }

    private void a(int color, float lineWidth, float xPosition) {
        this.A.setStrokeWidth(lineWidth);
        if (Math.abs(xPosition - ((float) (this.s / 2))) < this.q) {
            a(color, Math.abs(xPosition - ((float) (this.s / 2))) / this.q);
        } else {
            this.A.setColor(color);
        }
    }

    private PointF a(String text, Paint paint, float cx) {
        Rect textBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBound);
        PointF textPoint = new PointF();
        textPoint.set(cx - ((float) (textBound.width() / 2)), (float) (getPaddingTop() + textBound.height()));
        return textPoint;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.J || this.l) {
            return super.onTouchEvent(event);
        }
        int action = event.getAction();
        int xPosition = (int) event.getX();
        if (this.x == null) {
            this.x = VelocityTracker.obtain();
        }
        this.x.addMovement(event);
        this.j = false;
        switch (action) {
            case 0:
                this.w.forceFinished(true);
                this.r = xPosition;
                this.M = xPosition;
                this.T = true;
                this.O = false;
                break;
            case 1:
            case 3:
                this.T = false;
                this.O = false;
                if (Math.abs(this.M - this.r) < 5) {
                    float temp = this.F + ((float) (this.M - this.N));
                    if (temp >= (-this.P) && temp <= this.K + this.P) {
                        int i = Math.round(temp / (this.q * ((float) this.C)));
                        if (Math.abs(temp - ((((float) i) * this.q) * ((float) this.C))) < this.Q && this.p != this.C * i) {
                            this.O = true;
                            this.R = this.C * i;
                            a(this.R, 500);
                            this.l = true;
                        }
                    }
                }
                if (!this.O) {
                    this.r = 0;
                    invalidate();
                    c();
                    return true;
                }
                break;
            case 2:
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                float move = (float) (this.r - xPosition);
                if (move >= 0.0f && this.F >= this.K) {
                    return true;
                }
                if (move > 0.0f || this.F > 0.0f) {
                    this.F += a(move);
                    invalidate();
                    break;
                }
                return true;
        }
        this.r = xPosition;
        return true;
    }

    private void c() {
        this.x.computeCurrentVelocity(1000);
        float xVelocity = this.x.getXVelocity();
        if (Math.abs(xVelocity) > ((float) this.v)) {
            xVelocity *= 1.0f - this.I;
            this.j = true;
            this.w.fling(0, 0, (int) xVelocity, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
            return;
        }
        e();
    }

    private void d() {
        if (this.B != null) {
            post(new Runnable(this) {
                final /* synthetic */ HorizontalWheelView a;

                {
                    this.a = r1;
                }

                public void run() {
                    this.a.B.a((float) this.a.p);
                }
            });
        }
    }

    public void computeScroll() {
        super.computeScroll();
        if (this.w.computeScrollOffset()) {
            int xPosition = this.w.getCurrX();
            if (this.j) {
                float move = (float) (this.r - xPosition);
                this.r = xPosition;
                if ((move < 0.0f || this.F < this.K) && (move > 0.0f || this.F > 0.0f)) {
                    this.F += a(move);
                } else {
                    this.w.forceFinished(true);
                    f();
                    return;
                }
            }
            this.F = (float) xPosition;
            postInvalidate();
        } else if (this.j) {
            e();
        } else {
            this.R = -1;
            this.l = false;
            if (!(this.T || this.O || this.U)) {
                f();
            }
            if (this.U) {
                this.U = false;
            }
        }
    }

    private float a(float move) {
        if (this.F + move < 0.0f) {
            return -this.F;
        }
        if (this.F + move > this.K) {
            return this.K - this.F;
        }
        return move;
    }

    private void e() {
        this.j = false;
        this.J = true;
        this.w.forceFinished(true);
        this.F = (float) Math.round(this.F);
        this.w.startScroll((int) this.F, 0, (int) ((float) Math.round(b(this.F % this.q))), 0, 1000);
        postInvalidate();
    }

    private float b(float offset) {
        if (offset <= this.q / 2.0f) {
            return -offset;
        }
        return this.q - offset;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.x != null) {
            this.x.recycle();
        }
    }

    private void a(int normalColor, float scale) {
        int selectColor = this.d;
        int selectAlpha = Color.alpha(selectColor);
        int selectRed = Color.red(selectColor);
        int selectGreen = Color.green(selectColor);
        int selectBlue = Color.blue(selectColor);
        this.A.setColor(Color.argb((int) ((((float) selectAlpha) * (1.0f - scale)) + (((float) Color.alpha(normalColor)) * scale)), (int) ((((float) selectRed) * (1.0f - scale)) + (((float) Color.red(normalColor)) * scale)), (int) ((((float) selectGreen) * (1.0f - scale)) + (((float) Color.green(normalColor)) * scale)), (int) ((((float) selectBlue) * (1.0f - scale)) + (((float) Color.blue(normalColor)) * scale))));
    }

    public void a(int value, int duration) {
        this.j = false;
        this.w.forceFinished(true);
        this.w.startScroll((int) this.F, 0, (int) ((((float) value) * this.q) - this.F), 0, duration);
        invalidate();
    }

    public void setData(List<String> data, int defaultSelected) {
        this.w.forceFinished(true);
        this.i = data;
        this.h = data.size();
        this.K = ((float) this.h) * this.q;
        setSelectNotDraw(defaultSelected);
        invalidate();
    }

    public void setSelect(int select) {
        this.w.forceFinished(true);
        setSelectNotDraw(select);
        invalidate();
    }

    private void setSelectNotDraw(int select) {
        if (select > this.h) {
            this.p = this.h;
        } else if (select < 0) {
            this.p = 0;
        } else {
            this.p = select;
        }
        this.F = ((float) this.p) * this.q;
    }

    public void setTotalMove(float totalMove) {
        this.w.forceFinished(true);
        this.U = true;
        boolean invalidate = true;
        this.O = false;
        if (totalMove < 0.0f && this.F != 0.0f) {
            this.F = 0.0f;
        } else if (totalMove > this.K && this.F != this.K) {
            this.F = this.K;
        } else if (totalMove != this.F) {
            this.F = totalMove;
        } else {
            invalidate = false;
        }
        if (invalidate) {
            invalidate();
        }
    }

    public void setOnValueChangeListener(a onValueChangeListener) {
        this.B = onValueChangeListener;
    }

    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        if (this.R != -1) {
            bundle.putInt("selected", this.R);
        } else {
            bundle.putInt("selected", this.p);
        }
        return bundle;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            setSelect(bundle.getInt("selected"));
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public void setAllowScroll(boolean allowScroll) {
        this.J = allowScroll;
    }

    public void setScaleDistance(float scaleDistance) {
        this.q = scaleDistance;
        invalidate();
    }

    public float getScaleDistance() {
        return this.q;
    }

    public void setSelectedColor(int selectedColor) {
        this.d = selectedColor;
        invalidate();
    }

    public void setTextColor(int textColor) {
        this.e = textColor;
    }

    public float getTotalMove() {
        return this.F;
    }

    private void f() {
        if (this.S) {
            setContentDescription(String.valueOf(this.p));
            sendAccessibilityEvent(4);
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(HorizontalWheelView.class.getName());
    }
}

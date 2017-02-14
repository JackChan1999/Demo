package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import com.meizu.common.a.h;
import com.meizu.common.a.j;
import com.meizu.common.app.SlideNotice;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PullRefreshLayout extends RelativeLayout {
    private int A;
    private float B;
    private boolean C;
    private Date D;
    private SimpleDateFormat E;
    private String F;
    private String G;
    private String H;
    private String I;
    private String J;
    private int K;
    private boolean L;
    private int M;
    private float N;
    private float O;
    private float P;
    private float Q;
    private int R;
    private boolean S;
    private int T;
    private String U;
    private float a;
    private int b;
    private float c;
    private float d;
    private View e;
    private TimeInterpolator f;
    private a g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private k o;
    private Context p;
    private b q;
    private c r;
    private d s;
    private e t;
    private boolean u;
    private boolean v;
    private boolean w;
    private boolean x;
    private boolean y;
    private boolean z;

    private class a implements AnimatorListener, AnimatorUpdateListener {
        final /* synthetic */ PullRefreshLayout a;
        private ValueAnimator b;
        private int c;
        private boolean d;

        private a(PullRefreshLayout pullRefreshLayout) {
            this.a = pullRefreshLayout;
        }

        public void a(int offset) {
            a();
            this.d = false;
            int duration = Math.abs((offset * 400) / this.a.j);
            this.b = new ValueAnimator();
            this.b.setIntValues(new int[]{0, offset});
            this.c = 0;
            this.b.setDuration((long) duration);
            this.b.setRepeatCount(0);
            if (this.a.f == null) {
                if (VERSION.SDK_INT < 21) {
                    this.a.f = new DecelerateInterpolator();
                } else {
                    this.a.f = new PathInterpolator(0.33f, 0.0f, 0.33f, 1.0f);
                }
            }
            this.b.setInterpolator(this.a.f);
            this.b.addListener(this);
            this.b.addUpdateListener(this);
            this.b.start();
            this.a.w = true;
        }

        public void a() {
            if (this.b != null && this.b.isRunning()) {
                this.b.cancel();
                this.a.w = false;
            }
            this.b = null;
        }

        public void onAnimationUpdate(ValueAnimator va) {
            int currentOffset = ((Integer) va.getAnimatedValue()).intValue();
            this.a.a(this.c - currentOffset);
            this.c = currentOffset;
            this.a.l = this.a.e.getTop();
        }

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
            this.b = null;
            if (!this.d) {
                if (this.a.s == d.STATE_LINE_END) {
                    this.a.o.b();
                    this.a.u = true;
                    if (this.a.q != null) {
                        this.a.q.a();
                    }
                } else if (this.a.s == d.STATE_ARC_END) {
                    this.a.o.b();
                    this.a.u = true;
                    if (this.a.q != null) {
                        this.a.q.a();
                    }
                }
                this.a.w = false;
            }
        }

        public void onAnimationCancel(Animator animation) {
            this.d = true;
        }

        public void onAnimationRepeat(Animator animation) {
        }
    }

    public interface b {
        void a();
    }

    public interface c {
        void a(int i);
    }

    private enum d {
        STATE_DEFAULT,
        STATE_LINE_MOVE,
        STATE_LINE_END,
        STATE_ARC_MOVE,
        STATE_ARC_END
    }

    private enum e {
        VIEW_NEED_OFFSET_DOWN,
        VIEW_NEED_OFFSET_UP,
        VIEW_NO_OFFSET
    }

    public PullRefreshLayout(Context context) {
        this(context, null);
    }

    public PullRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.g = new a();
        this.h = 0;
        this.i = 20;
        this.j = SlideNotice.SHOW_ANIMATION_DURATION;
        this.k = 120;
        this.n = 0;
        this.q = null;
        this.r = null;
        this.s = d.STATE_DEFAULT;
        this.t = e.VIEW_NO_OFFSET;
        this.u = false;
        this.v = false;
        this.w = false;
        this.x = false;
        this.y = true;
        this.z = false;
        this.B = 0.0f;
        this.C = false;
        this.E = new SimpleDateFormat("yyyy/MM/dd");
        this.L = true;
        this.P = 1.5f;
        this.Q = 1.2f;
        this.R = -1;
        this.S = false;
        this.T = 60;
        this.U = null;
        this.p = context;
        this.i = context.getResources().getDimensionPixelOffset(com.meizu.common.a.d.mc_pullRefresh_animheight);
        this.k = context.getResources().getDimensionPixelOffset(com.meizu.common.a.d.mc_pullRefresh_holdheight);
        this.j = context.getResources().getDimensionPixelOffset(com.meizu.common.a.d.mc_pullRefresh_overscrollheight);
        TypedArray b = this.p.obtainStyledAttributes(j.MZTheme);
        this.m = b.getColor(j.MZTheme_mzThemeColor, -16711936);
        b.recycle();
        TypedArray a = this.p.obtainStyledAttributes(attrs, j.PullRefreshLayout);
        this.h = a.getInt(j.PullRefreshLayout_mcPullRefreshAnimType, 0);
        this.y = a.getBoolean(j.PullRefreshLayout_mcPullRefreshDrawOnTop, true);
        this.A = a.getColor(j.PullRefreshLayout_mcPullRefreshAnimationColor, this.m);
        this.R = a.getColor(j.PullRefreshLayout_mcPullRefreshTextColor, this.R);
        a.recycle();
        b();
        this.M = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setOffset(int offset) {
        this.n = offset;
        this.o.a(offset);
    }

    public int getOffset() {
        return this.n;
    }

    public void setPullGetDataListener(b listener) {
        if (listener != null) {
            this.q = listener;
        }
    }

    public void setPullRefreshLayoutListener(c listener) {
        if (listener != null) {
            this.r = listener;
        }
    }

    public boolean getRefreshState() {
        return this.u;
    }

    public void setPromptTextColor(int colorValue) {
        this.o.c(colorValue);
    }

    public int getPromptTextColor() {
        return this.o.c();
    }

    public void setOverScrollDistance(int overScrollDistance) {
        this.j = overScrollDistance;
        this.o.b(overScrollDistance);
    }

    public int getOverScrollDistance() {
        return this.j;
    }

    private void b() {
        this.o = new k(this.p, this.A, this.h, this);
        setPromptTextColor(this.R);
        this.C = a();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.e = getChildAt(0);
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (!this.L) {
            return super.dispatchTouchEvent(event);
        }
        if (this.e == null && !a(event, this.e)) {
            return super.dispatchTouchEvent(event);
        }
        int action = event.getAction();
        this.x = a(event);
        super.dispatchTouchEvent(event);
        if (action == 3 || action == 1) {
            this.a = 0.0f;
            this.b = 0;
            this.v = false;
            return true;
        }
        if (action == 0) {
            this.g.a();
        }
        this.a = event.getY();
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.L) {
            return this.x;
        }
        return super.onInterceptTouchEvent(ev);
    }

    protected void dispatchDraw(Canvas canvas) {
        if (!this.L) {
            super.dispatchDraw(canvas);
        } else if (this.y) {
            super.dispatchDraw(canvas);
            if (this.o != null) {
                this.o.a(this.l, canvas);
            }
        } else {
            if (this.o != null) {
                this.o.a(this.l, canvas);
            }
            super.dispatchDraw(canvas);
        }
    }

    private void a(int offset) {
        int tempDistance = offset + this.e.getTop();
        if (tempDistance <= 0) {
            offset = -this.e.getTop();
        } else if (tempDistance > this.j) {
            offset = this.j - this.e.getTop();
        }
        this.e.offsetTopAndBottom(offset);
        this.l = this.e.getTop();
        if (this.r != null) {
            this.r.a(this.l);
        }
        if (getParent() != null && this.l > 0) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        postInvalidateDelayed(15);
    }

    private boolean a(MotionEvent event) {
        boolean ret = false;
        int action = event.getAction();
        this.K = action;
        int contentTop = a(this.e);
        int offset;
        if (action == 3 || action == 1) {
            if (contentTop > 0) {
                offset = this.e.getTop();
                if (this.h == 0) {
                    if (offset >= this.i) {
                        this.g.a(offset - this.i);
                        this.s = d.STATE_LINE_END;
                    } else {
                        this.g.a(offset);
                        this.s = d.STATE_DEFAULT;
                    }
                } else if (offset >= this.k) {
                    this.g.a(offset - this.k);
                    this.s = d.STATE_ARC_END;
                    if (!TextUtils.isEmpty(this.J)) {
                        this.D = new Date();
                    }
                } else {
                    this.g.a(offset);
                    this.s = d.STATE_DEFAULT;
                }
            }
            this.z = false;
            this.B = 0.0f;
        } else if (action == 0) {
            this.a = event.getY();
            this.c = event.getX();
            this.d = event.getY();
            if (!(this.D == null || TextUtils.isEmpty(this.J))) {
                if (this.S) {
                    this.o.a(getLastTimeOptional());
                } else {
                    this.o.a(getLastTime());
                }
            }
        } else if (action == 2) {
            float tmpOffset = event.getY() - this.a;
            this.N = Math.abs(event.getX() - this.c);
            this.O = Math.abs(event.getY() - this.d);
            this.o.a(false);
            if (Math.abs(tmpOffset) < 1.0f) {
                this.B += tmpOffset;
                if (Math.abs(this.B) <= 1.0f) {
                    return false;
                }
                tmpOffset = this.B;
                this.B = 0.0f;
            }
            if (this.O < ((float) this.M)) {
                return false;
            }
            if (this.N > ((float) this.M) && this.N > this.O && this.l == 0) {
                return false;
            }
            if ((this.u && this.h != 1) || this.v) {
                return false;
            }
            if (this.l >= this.j && tmpOffset > 0.0f) {
                return false;
            }
            boolean isDown;
            offset = (int) tmpOffset;
            if (this.h == 0) {
                this.s = d.STATE_LINE_MOVE;
                this.o.a();
            } else {
                this.s = d.STATE_ARC_MOVE;
            }
            if (offset <= 0) {
                isDown = false;
            } else {
                isDown = true;
            }
            if (contentTop >= 0) {
                this.t = a(a(this.e, event), isDown);
                if (e.VIEW_NO_OFFSET == this.t) {
                    return false;
                }
                ret = true;
                this.z = true;
                a(Math.round(((float) offset) / (((this.Q * ((float) this.l)) / ((float) this.j)) + this.P)));
            }
            return ret;
        }
        return false;
    }

    private e a(View view, boolean isDown) {
        e viewTopState = e.VIEW_NO_OFFSET;
        if (view == null || view.getScrollY() > 0) {
            return viewTopState;
        }
        if (this.e.getScrollY() < 0 && isDown) {
            viewTopState = e.VIEW_NEED_OFFSET_DOWN;
        }
        if (AbsListView.class.isAssignableFrom(view.getClass())) {
            if (((AbsListView) view).getFirstVisiblePosition() == 0) {
                if (((AbsListView) view).getChildCount() <= 0) {
                    return e.VIEW_NO_OFFSET;
                }
                if (a(((AbsListView) view).getChildAt(0)) >= view.getPaddingTop() && isDown) {
                    viewTopState = e.VIEW_NEED_OFFSET_DOWN;
                } else if (a(this.e) > 0 && !isDown) {
                    viewTopState = e.VIEW_NEED_OFFSET_UP;
                }
            } else if (a(this.e) > 0) {
                viewTopState = e.VIEW_NEED_OFFSET_UP;
            }
        } else if (ScrollView.class.isAssignableFrom(view.getClass())) {
            if (view.getScrollY() <= 0 && isDown) {
                viewTopState = e.VIEW_NEED_OFFSET_DOWN;
            } else if (view.getTop() > 0 && this.O > ((float) this.M) && !isDown) {
                viewTopState = e.VIEW_NEED_OFFSET_UP;
            }
        } else if (VERSION.SDK_INT > 14) {
            if (this.e == null) {
                return viewTopState;
            }
            if (this.e.canScrollVertically(-1) || (this.l == 0 && !isDown)) {
                viewTopState = e.VIEW_NO_OFFSET;
            } else if (!this.e.canScrollVertically(-1) && isDown) {
                viewTopState = e.VIEW_NEED_OFFSET_DOWN;
            } else if (!(this.e.canScrollVertically(-1) || isDown)) {
                viewTopState = e.VIEW_NEED_OFFSET_UP;
            }
            return viewTopState;
        } else if (isDown) {
            if (view.getScrollY() <= 0) {
                viewTopState = e.VIEW_NEED_OFFSET_DOWN;
            } else {
                viewTopState = e.VIEW_NO_OFFSET;
            }
        } else if (view.getTop() > 0 && this.O > ((float) this.M)) {
            viewTopState = e.VIEW_NEED_OFFSET_UP;
        }
        return viewTopState;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (!this.L) {
            super.onLayout(changed, left, top, right, bottom);
        } else if (this.e != null) {
            int contentTop = this.e.getTop();
            this.e.layout(0, contentTop, right, contentTop + getMeasuredHeight());
        }
    }

    private boolean a(MotionEvent event, View view) {
        if (event == null || view == null) {
            return false;
        }
        int eventX = (int) event.getRawX();
        int eventY = (int) event.getRawY();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int width = view.getWidth();
        int height = view.getHeight();
        int left = location[0];
        int top = location[1];
        return new Rect(left, top, left + width, top + height).contains(eventX, eventY);
    }

    private View a(View target, MotionEvent event) {
        if (target == null) {
            return null;
        }
        if (!a(event, target)) {
            return null;
        }
        if (!(target instanceof ViewGroup)) {
            return target;
        }
        if (AbsListView.class.isAssignableFrom(target.getClass()) || ScrollView.class.isAssignableFrom(target.getClass())) {
            if (a(event, target)) {
                View view = target;
                return target;
            }
        } else if (target instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) target;
            int index = parent.getChildCount() - 1;
            while (index >= 0) {
                View child = parent.getChildAt(index);
                if (!a(event, child)) {
                    index--;
                } else if (child instanceof ViewGroup) {
                    return a(child, event);
                } else {
                    return child;
                }
            }
        }
        return target;
    }

    private int a(View view) {
        return view.getTop();
    }

    boolean a() {
        boolean result = false;
        try {
            Field field = Class.forName("android.os.BuildExt").getField("IS_MX2");
            field.setAccessible(true);
            result = ((Boolean) field.get(null)).booleanValue();
        } catch (Exception e) {
        }
        return result;
    }

    public String getLastTime() {
        if (this.D == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.F);
        sb.append(" ");
        long d = new Date().getTime() - this.D.getTime();
        if (d > 0) {
            if (d < 60000) {
                sb.append(d / 1000);
                sb.append(this.I);
            } else if (d < 3600000) {
                sb.append(d / 60000);
                sb.append(this.H);
            } else if (d < 86400000) {
                sb.append(d / 3600000);
                sb.append(this.G);
            } else {
                sb.append(this.E.format(this.D));
            }
        }
        return sb.toString();
    }

    public void setOptionalLastTimeDisplay(int seconds, String displayText) {
        this.S = true;
        if (seconds < 60) {
            seconds = 60;
        }
        this.T = seconds;
        if (displayText == null) {
            this.U = getResources().getString(h.mc_last_refresh_just_now);
        } else {
            this.U = displayText;
        }
    }

    private String getLastTimeOptional() {
        if (this.D == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.F);
        sb.append(" ");
        long d = new Date().getTime() - this.D.getTime();
        if (d >= 0) {
            if (d < ((long) (this.T * 1000))) {
                sb.append(this.U);
            } else if (d < 3600000) {
                sb.append(d / 60000);
                sb.append(this.H);
            } else if (d < 86400000) {
                sb.append(d / 3600000);
                sb.append(this.G);
            } else {
                sb.append(this.E.format(this.D));
            }
        }
        return sb.toString();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!TextUtils.isEmpty(this.J)) {
            long time = this.p.getSharedPreferences("pull_to_refresh", 0).getLong(this.J, 0);
            if (time != 0) {
                this.D = new Date(time);
            }
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!(TextUtils.isEmpty(this.J) || this.D == null)) {
            Editor editor = this.p.getSharedPreferences("pull_to_refresh", 0).edit();
            editor.putLong(this.J, this.D.getTime());
            editor.commit();
        }
        this.o.d();
    }

    public void setLastRefreshTimeKey(String key) {
        if (!TextUtils.isEmpty(key)) {
            this.J = key;
            if (TextUtils.isEmpty(this.F)) {
                this.F = this.p.getString(h.mc_last_refresh);
                this.G = this.p.getString(h.mc_last_refresh_hour);
                this.H = this.p.getString(h.mc_last_refresh_minute);
                this.I = this.p.getString(h.mc_last_refresh_second);
            }
        }
    }

    public void setEnablePull(boolean enablePull) {
        this.L = enablePull;
    }

    public void setRingColor(int color) {
        if (this.o != null) {
            this.o.d(color);
        }
    }

    public int getRingColor() {
        return this.o != null ? this.o.e() : 0;
    }

    public void setRingBackgroundColor(int color) {
        if (this.o != null) {
            this.o.e(color);
        }
    }

    public int getRingBackgroundColor() {
        return this.o != null ? this.o.f() : 0;
    }

    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        if (this.e != null && AbsListView.class.isAssignableFrom(this.e.getClass()) && getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    public void setResistance(float resistance) {
        this.P = resistance;
    }

    public void setAppendResistance(float resistance) {
        this.Q = resistance;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        info.setClassName(PullRefreshLayout.class.getName());
    }
}

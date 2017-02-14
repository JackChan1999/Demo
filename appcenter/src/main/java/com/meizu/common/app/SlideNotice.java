package com.meizu.common.app;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.meizu.common.a.f;
import com.meizu.common.a.g;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public class SlideNotice {
    public static final int LENGTH_LONG = 1;
    public static final int LENGTH_SHORT = 0;
    private static final int NOTICE_CLICK = 0;
    public static final int NOTICE_TYPE_FAILURE = 0;
    public static final int NOTICE_TYPE_SUCCESS = 1;
    public static final int SHOW_ANIMATION_DURATION = 300;
    private static final String TAG = "SlideNotice";
    private static a mService;
    private static Field sMeizuFlag;
    private Context mContext;
    private int mDuration;
    private d mSlideViewController;
    private Toast mToast;

    public interface b {
        void a(SlideNotice slideNotice);
    }

    private static final class a extends Handler {
        private WeakReference<SlideNotice> a;

        public a(SlideNotice notice) {
            this.a = new WeakReference(notice);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    ((b) msg.obj).a((SlideNotice) this.a.get());
                    return;
                default:
                    return;
            }
        }
    }

    private static final class c extends FrameLayout {
        public c(Context context) {
            super(context);
        }
    }

    private class d implements com.meizu.common.app.a.a {
        private LayoutParams A = new LayoutParams();
        private View B;
        private WeakReference<View> C;
        private int D;
        private OnScrollChangedListener E = new OnScrollChangedListener(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void onScrollChanged() {
                View anchor = this.a.C != null ? (View) this.a.C.get() : null;
                if (anchor != null && anchor.getParent() != null && this.a.e != null) {
                    LayoutParams p = new LayoutParams();
                    this.a.a(anchor, p);
                    this.a.a(p.x, p.y);
                }
            }
        };
        private int[] F = new int[2];
        private boolean G;
        private Message H;
        private Handler I;
        private final OnClickListener J = new OnClickListener(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void onClick(View v) {
                Message msg;
                if (this.a.H != null) {
                    msg = Message.obtain(this.a.H);
                } else {
                    msg = null;
                }
                if (msg != null) {
                    msg.sendToTarget();
                }
            }
        };
        private boolean K;
        final Handler a = new Handler();
        final Runnable b = new Runnable(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.c();
            }
        };
        final Runnable c = new Runnable(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.d();
            }
        };
        final /* synthetic */ SlideNotice d;
        private View e;
        private LinearLayout f;
        private TextView g;
        private View h;
        private FrameLayout i;
        private int j;
        private int k = -1;
        private int l;
        private int m;
        private int n;
        private int o;
        private int p;
        private int q;
        private boolean r;
        private int s;
        private int t;
        private boolean u;
        private a v;
        private boolean w = false;
        private CharSequence x;
        private b y;
        private WindowManager z;

        private class a {
            final /* synthetic */ d a;
            private b b;
            private ValueAnimator c;

            public a(d dVar) {
                this.a = dVar;
                this.b = new b();
            }

            public void a() {
                if (this.c != null && this.c.isRunning()) {
                    this.c.cancel();
                }
            }

            public void a(int fromY, int toY) {
                if (this.c != null && this.c.isRunning()) {
                    this.c.cancel();
                }
                this.c = ObjectAnimator.ofInt(this.a.d.mSlideViewController, "height", new int[]{fromY, toY});
                this.c.setDuration(300);
                this.c.addListener(this.b);
                this.c.setInterpolator(new DecelerateInterpolator());
                this.c.start();
            }

            public boolean b() {
                return this.c == null ? false : this.c.isRunning();
            }
        }

        private class b implements AnimatorListener {
            final /* synthetic */ d a;

            private b(d dVar) {
                this.a = dVar;
            }

            public void onAnimationStart(Animator animation) {
                this.a.d.mSlideViewController.u = true;
            }

            public void onAnimationEnd(Animator animation) {
                if (this.a.D != 0 && this.a.k <= 0) {
                    this.a.k = -1;
                    this.a.d.mSlideViewController.j();
                }
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        }

        public d(SlideNotice slideNotice, SlideNotice notice) {
            this.d = slideNotice;
            this.I = new a(notice);
            i();
        }

        public void a(int gravity) {
            this.f.setGravity(gravity);
        }

        public void a(View anchor) {
            if (anchor != null) {
                h();
                this.C = new WeakReference(anchor);
                ViewTreeObserver vto = anchor.getViewTreeObserver();
                if (vto != null) {
                    vto.addOnScrollChangedListener(this.E);
                }
            }
        }

        private void h() {
            WeakReference<View> anchorRef = this.C;
            View anchor = null;
            if (anchorRef != null) {
                anchor = (View) anchorRef.get();
            }
            if (anchor != null) {
                anchor.getViewTreeObserver().removeOnScrollChangedListener(this.E);
            }
            this.C = null;
        }

        public void a(b l) {
            this.y = l;
        }

        public boolean a() {
            return this.u;
        }

        public void a(boolean belowDefaultActionBar) {
            this.r = belowDefaultActionBar;
            if (this.r) {
                this.l = this.o + this.n;
            } else {
                this.l = 0;
            }
        }

        public void a(CharSequence s) {
            this.x = s;
            if (this.g != null) {
                this.g.setText(s);
            }
        }

        public CharSequence b() {
            return this.x;
        }

        public void b(int color) {
            this.s = color;
        }

        public void c(int color) {
            this.t = color;
        }

        public void b(boolean b) {
            if (this.G) {
                this.w = b;
                this.K = true;
            }
        }

        public void d(int height) {
            this.p = height;
        }

        public void e(int width) {
            this.q = width;
        }

        private void i() throws RuntimeException {
            this.e = LayoutInflater.from(this.d.mContext).inflate(g.mc_slide_notice_content, null);
            this.g = (TextView) this.e.findViewById(f.noticeView);
            this.f = (LinearLayout) this.e.findViewById(f.noticePanel);
            this.i = (FrameLayout) this.e.findViewById(f.custom);
            this.e.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
            this.n = com.meizu.common.util.c.b(this.d.mContext);
            this.o = a(this.d.mContext);
            this.v = new a(this);
            Context context = this.d.mContext.getApplicationContext();
            if (context != null) {
                this.z = (WindowManager) context.getSystemService("window");
            } else {
                this.z = (WindowManager) this.d.mContext.getSystemService("window");
            }
            this.A.height = -2;
            this.A.width = -1;
            this.A.gravity = 51;
            this.A.format = -3;
            this.A.setTitle("SlideNotice:" + Integer.toHexString(hashCode()));
            this.A.flags = 40;
            this.G = a(this.A, true);
            if (!this.G) {
                this.n = 0;
            }
            if (!(this.d.mContext instanceof Activity)) {
                this.o = this.d.mContext.getResources().getDimensionPixelSize(com.meizu.common.a.d.mz_action_bar_default_height);
            }
        }

        private void j() {
            if (!(this.B == null || this.B.getParent() == null)) {
                this.z.removeView(this.B);
            }
            h();
            this.E = null;
            this.u = false;
        }

        public boolean c() {
            if (this.u) {
                return false;
            }
            a(this.A);
            n();
            k();
            this.A.x = this.m;
            this.A.y = this.l;
            this.e.setVisibility(0);
            b(this.A);
            this.e.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener(this) {
                final /* synthetic */ d a;

                {
                    this.a = r1;
                }

                public boolean onPreDraw() {
                    this.a.e.getViewTreeObserver().removeOnPreDrawListener(this);
                    this.a.j = this.a.B.getMeasuredHeight();
                    this.a.v.a(0, this.a.j);
                    this.a.D = 0;
                    return false;
                }
            });
            this.u = true;
            o();
            return true;
        }

        private void a(LayoutParams p) {
            if (this.B == null) {
                FrameLayout slideContainer = new c(this.d.mContext);
                slideContainer.addView(this.e);
                this.B = slideContainer;
            }
            if (this.d.mContext instanceof Activity) {
                IBinder token = ((Activity) this.d.mContext).getWindow().getDecorView().getWindowToken();
                if (token != null) {
                    p.token = token;
                    p.type = 1000;
                } else {
                    p.type = 2005;
                }
            } else {
                p.type = 2005;
            }
            if (this.q > 0) {
                p.width = this.q;
            }
        }

        private void b(LayoutParams p) {
            if (this.d.mContext != null) {
                p.packageName = this.d.mContext.getPackageName();
            }
            this.z.addView(this.B, p);
        }

        private void k() {
            if (m()) {
                this.f.setVisibility(8);
            } else {
                l();
            }
            if (this.y != null) {
                this.e.setOnClickListener(this.J);
                this.H = this.I.obtainMessage(0, this.y);
            }
        }

        private void l() {
            this.g.setVisibility(8);
            if (this.l < this.n && !this.K) {
                this.w = true;
            }
            if (this.w) {
                this.g = (TextView) this.e.findViewById(f.noticeView_no_title_bar);
                ((LinearLayout.LayoutParams) this.g.getLayoutParams()).topMargin = this.d.mContext.getResources().getDimensionPixelSize(com.meizu.common.a.d.mc_slide_notice_textview_margin_top);
            } else {
                ((LinearLayout.LayoutParams) this.g.getLayoutParams()).topMargin = 0;
                this.g = (TextView) this.e.findViewById(f.noticeView);
            }
            if (this.p > 0) {
                this.f.getLayoutParams().height = this.p;
            }
            this.g.setText(this.x);
            this.g.setVisibility(0);
            this.f.setBackgroundColor(this.s);
            this.f.setVisibility(0);
        }

        private boolean m() {
            if (this.h != null) {
                if (this.h.getParent() == this.i) {
                    this.i.removeView(this.h);
                }
                this.i.addView(this.h);
                ViewGroup.LayoutParams lp = this.h.getLayoutParams();
                lp.width = -1;
                lp.height = -2;
                this.i.setVisibility(0);
                return true;
            }
            this.i.setVisibility(8);
            return false;
        }

        private void n() {
            if (this.C != null) {
                View anchor = this.C != null ? (View) this.C.get() : null;
                if (anchor != null && anchor.getParent() != null) {
                    LayoutParams p = new LayoutParams();
                    a(anchor, p);
                    this.l = p.y;
                }
            }
        }

        private void o() {
            AccessibilityManager accessibilityManager = (AccessibilityManager) this.d.mContext.getSystemService("accessibility");
            if (accessibilityManager.isEnabled()) {
                AccessibilityEvent event = AccessibilityEvent.obtain(64);
                event.setClassName(getClass().getName());
                event.setPackageName(this.B.getContext().getPackageName());
                this.B.dispatchPopulateAccessibilityEvent(event);
                accessibilityManager.sendAccessibilityEvent(event);
            }
        }

        public void d() {
            if (!this.u) {
                return;
            }
            if (this.k < 0) {
                if (this.v.b()) {
                    this.v.a();
                }
                j();
                return;
            }
            this.v.a(this.k, 0);
            this.D = 1;
        }

        public void f(int left) {
            this.m = left;
        }

        public void g(int top) {
            this.l = top;
        }

        public void e() {
            if (this.r) {
                this.l = this.o + this.n;
            } else {
                this.l = 0;
            }
        }

        private int a(Context context) {
            TypedValue tv = new TypedValue();
            if (context.getTheme().resolveAttribute(16843499, tv, true)) {
                return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
            }
            return 144;
        }

        private void a(View anchor, LayoutParams p) {
            int anchorHeight = anchor.getHeight();
            anchor.getLocationInWindow(this.F);
            p.x = this.F[0];
            p.y = this.F[1] + anchorHeight;
        }

        private void a(int x, int y) {
            if (this.u && this.B != null && this.B.getParent() != null) {
                this.l = y;
                this.A.y = this.l;
                this.z.updateViewLayout(this.B, this.A);
            }
        }

        public void b(View customView) {
            this.h = customView;
        }

        public void f() {
            try {
                this.a.post(this.c);
            } catch (Exception e) {
                d();
            }
        }

        public void g() {
            try {
                this.a.post(this.b);
            } catch (Exception e) {
                c();
            }
        }

        private boolean a(LayoutParams attributes, boolean trans) {
            try {
                if (VERSION.SDK_INT < 19) {
                    int trans_status = 0;
                    if (SlideNotice.sMeizuFlag == null) {
                        trans_status = 64;
                        SlideNotice.sMeizuFlag = attributes.getClass().getDeclaredField("meizuFlags");
                        SlideNotice.sMeizuFlag.setAccessible(true);
                    }
                    int value = SlideNotice.sMeizuFlag.getInt(attributes);
                    if (trans) {
                        value |= trans_status;
                    } else {
                        value &= trans_status ^ -1;
                    }
                    SlideNotice.sMeizuFlag.setInt(attributes, value);
                    return true;
                } else if (trans) {
                    attributes.flags |= 67108864;
                    return true;
                } else {
                    attributes.flags &= -67108865;
                    return true;
                }
            } catch (Exception e) {
                Log.e(SlideNotice.TAG, "Can't set the status bar to be transparent, Caused by:" + e.getMessage());
                return false;
            }
        }
    }

    private static a getService() {
        if (mService != null) {
            return mService;
        }
        mService = new a();
        return mService;
    }

    public SlideNotice(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context can not be null!");
        }
        this.mContext = context;
        this.mSlideViewController = new d(this, this);
    }

    public SlideNotice(Context context, CharSequence text, int duration) {
        this(context);
        this.mToast = Toast.makeText(context, text, duration);
    }

    public void setText(CharSequence s) {
        this.mSlideViewController.a(s);
        if (this.mToast != null) {
            this.mToast.setText(s);
        }
    }

    public void setBeginColor(int color) {
        this.mSlideViewController.b(color);
    }

    public void setEndColor(int color) {
        this.mSlideViewController.c(color);
    }

    public void setNoticeType(int type) {
        if (!this.mSlideViewController.a()) {
            switch (type) {
                case 0:
                    setBeginColor(this.mContext.getResources().getColor(com.meizu.common.a.c.mc_slide_notice_failure_begin_color));
                    setEndColor(this.mContext.getResources().getColor(com.meizu.common.a.c.mc_slide_notice_failure_end_color));
                    return;
                default:
                    setBeginColor(this.mContext.getResources().getColor(com.meizu.common.a.c.mc_slide_notice_success_begin_color));
                    setEndColor(this.mContext.getResources().getColor(com.meizu.common.a.c.mc_slide_notice_success_end_color));
                    return;
            }
        }
    }

    public void setTop(int top) {
        this.mSlideViewController.g(top);
    }

    public void setLeft(int left) {
        this.mSlideViewController.f(left);
    }

    public void resetSlideFrom() {
        this.mSlideViewController.e();
    }

    public void setActionBarToTop(boolean toTop) {
        this.mSlideViewController.a(toTop);
    }

    public void setBelowDefaultActionBar(boolean belowDefaultActionBar) {
        this.mSlideViewController.a(belowDefaultActionBar);
    }

    public void setHeight(int height) {
        this.mSlideViewController.d(height);
    }

    public void setWidth(int width) {
        this.mSlideViewController.e(width);
    }

    public boolean isShowing() {
        return this.mSlideViewController.a();
    }

    public void setNoTitleBarStyle(boolean b) {
        this.mSlideViewController.b(b);
    }

    public void setIsOverlaidByStatusBar(boolean b) {
        this.mSlideViewController.b(b);
    }

    public void setOnClickNoticeListener(b l) {
        this.mSlideViewController.a(l);
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
        if (this.mToast != null) {
            this.mToast.setDuration(duration);
        }
    }

    public int getDuration() {
        return this.mDuration;
    }

    public void setAnchorView(View anchor) {
        this.mSlideViewController.a(anchor);
    }

    public void show() {
        if (this.mToast != null) {
            this.mToast.show();
        }
    }

    public void show(boolean isKeeped) {
    }

    public void slideToShow() {
        getService().a(this.mSlideViewController.b(), this.mSlideViewController, this.mDuration);
    }

    public void slideToShow(boolean isKeeped) {
        if (isKeeped) {
            this.mSlideViewController.g();
        } else {
            show();
        }
    }

    public void finish() {
        cancel();
    }

    public void cancel() {
        if (this.mToast != null) {
            this.mToast.cancel();
        }
    }

    public void slideToCancel() {
        this.mSlideViewController.f();
        getService().a(this.mSlideViewController);
    }

    public void cancelWithoutAnim() {
        this.mSlideViewController.j();
    }

    public void showAndFinish(long keepTime) {
    }

    public static SlideNotice makeNotice(Context context, CharSequence text) {
        return makeNotice(context, text, 1, 0);
    }

    public static SlideNotice makeNotice(Context context, CharSequence text, int type) {
        return makeNotice(context, text, 0, 0);
    }

    public static SlideNotice makeNotice(Context context, CharSequence text, int type, int duration) {
        return new SlideNotice(context, text, duration);
    }

    public void setGravity(int gravity) {
        this.mSlideViewController.a(gravity);
    }

    public static SlideNotice makeSlideNotice(Context context, CharSequence text) {
        return makeSlideNotice(context, text, 1, 0);
    }

    public static SlideNotice makeSlideNotice(Context context, CharSequence text, int type) {
        SlideNotice notice = new SlideNotice(context);
        notice.setText(text);
        notice.setNoticeType(type);
        return notice;
    }

    public static SlideNotice makeSlideNotice(Context context, CharSequence text, int type, int duration) {
        SlideNotice notice = new SlideNotice(context);
        notice.setText(text);
        notice.setNoticeType(type);
        notice.mDuration = duration;
        return notice;
    }

    public void setCustomView(View customView) {
        this.mSlideViewController.b(customView);
    }
}

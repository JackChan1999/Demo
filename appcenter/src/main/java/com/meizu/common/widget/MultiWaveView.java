package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.DecelerateInterpolator;
import com.alibaba.fastjson.asm.Opcodes;
import com.meizu.common.a.j;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class MultiWaveView extends View {
    private static final TimeInterpolator K = new DecelerateInterpolator();
    private AnimatorListener A;
    private AnimatorUpdateListener B;
    private boolean C;
    private int D;
    private int E;
    private int F;
    private int G;
    private int H;
    private int I;
    private boolean J;
    private int L;
    private int M;
    private int N;
    private int O;
    private float P;
    private float Q;
    private float R;
    private float S;
    private float T;
    private float U;
    private float V;
    private float W;
    private ArrayList<f> a;
    private int aA;
    private int aB;
    private ArgbEvaluator aC;
    private Paint aD;
    private Paint aE;
    private float aF;
    private Paint aG;
    private int aH;
    private int aI;
    private float aJ;
    private float aK;
    private float aL;
    private AnimatorSet aM;
    private int aN;
    private AnimatorListener aO;
    private float aa;
    private float ab;
    private float ac;
    private float ad;
    private float ae;
    private float af;
    private float ag;
    private float ah;
    private float ai;
    private float aj;
    private float ak;
    private float al;
    private Paint am;
    private Paint an;
    private ObjectAnimator ao;
    private ObjectAnimator ap;
    private h aq;
    private h ar;
    private boolean as;
    private boolean at;
    private boolean au;
    private boolean av;
    private boolean aw;
    private int ax;
    private int ay;
    private int az;
    private a b;
    private a c;
    private ArrayList<String> d;
    private ArrayList<String> e;
    private e f;
    private float g;
    private float h;
    private f i;
    private f j;
    private Vibrator k;
    private d l;
    private boolean m;
    private c n;
    private int o;
    private int p;
    private int q;
    private float r;
    private float s;
    private float t;
    private int u;
    private int v;
    private float w;
    private boolean x;
    private int y;
    private AnimatorListener z;

    private class a extends ArrayList<g> {
        private static final long serialVersionUID = -6319262269245852568L;
        final /* synthetic */ MultiWaveView a;
        private boolean b;

        private a(MultiWaveView multiWaveView) {
            this.a = multiWaveView;
        }

        public void a() {
            if (!this.b) {
                int count = size();
                for (int i = 0; i < count; i++) {
                    ((g) get(i)).a.start();
                }
            }
        }

        public void b() {
            int count = size();
            for (int i = 0; i < count; i++) {
                ((g) get(i)).a.cancel();
            }
            clear();
        }

        public void c() {
            int count = size();
            for (int i = 0; i < count; i++) {
                ((g) get(i)).a.end();
            }
            clear();
        }
    }

    static class b {

        static class a {
            public static final TimeInterpolator a = new TimeInterpolator() {
                public float getInterpolation(float input) {
                    input /= 1.0f;
                    return ((((1.0f * input) * input) * input) * input) + 0.0f;
                }
            };
            public static final TimeInterpolator b = new TimeInterpolator() {
                public float getInterpolation(float input) {
                    input = (input / 1.0f) - 1.0f;
                    return (-1.0f * ((((input * input) * input) * input) - 1.0f)) + 0.0f;
                }
            };
            public static final TimeInterpolator c = new TimeInterpolator() {
                public float getInterpolation(float input) {
                    input /= 0.5f;
                    if (input < 1.0f) {
                        return ((((0.5f * input) * input) * input) * input) + 0.0f;
                    }
                    input -= 2.0f;
                    return (-0.5f * ((((input * input) * input) * input) - 2.0f)) + 0.0f;
                }
            };
        }
    }

    private class c implements AnimatorUpdateListener {
        public boolean a;
        final /* synthetic */ MultiWaveView b;

        private c(MultiWaveView multiWaveView) {
            this.b = multiWaveView;
            this.a = true;
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            this.b.a(null, this.a);
            this.b.invalidate();
        }
    }

    private static class d {
        public float a;
        public float b;
    }

    public interface e {
        void a();

        void a(View view, int i);

        void a(View view, int i, boolean z);

        void b(View view, int i);

        void c(View view, int i);
    }

    static class f {
        public static final int[] a = new int[]{16842910, 16842914};
        public static final int[] b = new int[]{16842910, -16842914};
        public static final int[] c = new int[]{16842910, -16842914, 16842908};
        private static final Xfermode q = new PorterDuffXfermode(Mode.XOR);
        private float d;
        private float e;
        private float f;
        private float g;
        private float h;
        private float i;
        private float j;
        private float k;
        private Drawable l;
        private boolean m;
        private final int n;
        private Bitmap o;
        private Paint p;
        private boolean r;
        private int s;
        private float t;

        public f(Resources res, int resId) {
            this.d = 0.0f;
            this.e = 0.0f;
            this.f = 0.0f;
            this.g = 0.0f;
            this.h = 1.0f;
            this.i = 1.0f;
            this.j = 1.0f;
            this.k = 0.0f;
            this.m = true;
            this.r = false;
            this.n = resId;
            a(res, resId);
            this.p = new Paint();
            this.p.setFilterBitmap(true);
            this.p.setAntiAlias(true);
            this.r = false;
        }

        public void a(Resources res, int resId) {
            Drawable drawable = null;
            Drawable drawable2 = resId == 0 ? null : res.getDrawable(resId);
            if (drawable2 != null) {
                drawable = drawable2.mutate();
            }
            this.l = drawable;
            j();
            a(b);
        }

        public f(int color, float radius) {
            this.d = 0.0f;
            this.e = 0.0f;
            this.f = 0.0f;
            this.g = 0.0f;
            this.h = 1.0f;
            this.i = 1.0f;
            this.j = 1.0f;
            this.k = 0.0f;
            this.m = true;
            this.r = false;
            this.n = -1;
            this.l = null;
            this.o = null;
            this.r = true;
            this.s = color;
            this.t = radius;
            this.p = new Paint();
            this.p.setFilterBitmap(true);
            this.p.setAntiAlias(true);
        }

        public void a(int[] state) {
            if (this.l instanceof StateListDrawable) {
                this.l.setState(state);
            }
        }

        public boolean a() {
            return this.m && !(!this.r && this.o == null && this.l == null);
        }

        private void j() {
            if (this.l instanceof StateListDrawable) {
                int i;
                StateListDrawable d = this.l;
                int maxWidth = 0;
                int maxHeight = 0;
                for (i = 0; i < a(d); i++) {
                    Drawable childDrawable = a(d, i);
                    maxWidth = Math.max(maxWidth, childDrawable.getIntrinsicWidth());
                    maxHeight = Math.max(maxHeight, childDrawable.getIntrinsicHeight());
                }
                d.setBounds(0, 0, maxWidth, maxHeight);
                for (i = 0; i < a(d); i++) {
                    a(d, i).setBounds(0, 0, maxWidth, maxHeight);
                }
            } else if (this.l != null) {
                this.l.setBounds(0, 0, this.l.getIntrinsicWidth(), this.l.getIntrinsicHeight());
            }
            if (this.l != null) {
                this.o = a(this.l);
            }
        }

        public void a(float x) {
            this.d = x;
        }

        public void b(float y) {
            this.e = y;
        }

        public void c(float rotate) {
            this.k = rotate;
        }

        public float b() {
            return this.d;
        }

        public float c() {
            return this.e;
        }

        public float d() {
            return this.j;
        }

        public void d(float x) {
            this.f = x;
        }

        public void e(float y) {
            this.g = y;
        }

        public float e() {
            return this.f;
        }

        public float f() {
            return this.g;
        }

        public int g() {
            if (this.l != null) {
                return this.l.getIntrinsicWidth();
            }
            if (this.o != null) {
                return this.o.getWidth();
            }
            if (this.r) {
                return (int) (this.t * 2.0f);
            }
            return 0;
        }

        public int h() {
            if (this.l != null) {
                return this.l.getIntrinsicHeight();
            }
            if (this.o != null) {
                return this.o.getHeight();
            }
            if (this.r) {
                return (int) (this.t * 2.0f);
            }
            return 0;
        }

        public void a(Canvas canvas, boolean Xfermode) {
            if (this.m) {
                int halfWidth = g() / 2;
                int halfHeight = h() / 2;
                canvas.save();
                canvas.translate(this.d + this.f, this.e + this.g);
                canvas.translate((float) (-halfWidth), (float) (-halfHeight));
                Matrix matrix = new Matrix();
                matrix.preScale(this.h, this.i, (float) halfWidth, (float) halfHeight);
                matrix.postRotate(this.k, (float) halfWidth, (float) halfHeight);
                if (this.o != null) {
                    canvas.drawBitmap(this.o, matrix, this.p);
                } else if (this.r) {
                    this.p.setColor(this.s);
                    canvas.drawCircle((float) halfWidth, (float) halfHeight, this.t * this.h, this.p);
                }
                canvas.restore();
            }
        }

        public void a(boolean enabled) {
            this.m = enabled;
        }

        public int i() {
            return this.n;
        }

        private int a(StateListDrawable d) {
            try {
                return ((Integer) d.getClass().getMethod("getStateCount", new Class[0]).invoke(d, new Object[0])).intValue();
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        private Drawable a(StateListDrawable d, int index) {
            try {
                return (Drawable) d.getClass().getMethod("getStateDrawable", new Class[]{Integer.TYPE}).invoke(d, new Object[]{Integer.valueOf(index)});
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private Bitmap a(Drawable drawable) {
            int w = drawable.getIntrinsicWidth();
            int h = drawable.getIntrinsicHeight();
            try {
                Bitmap bitmap = Bitmap.createBitmap(w, h, drawable.getOpacity() != -1 ? Config.ARGB_8888 : Config.RGB_565);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, w, h);
                drawable.draw(canvas);
                return bitmap;
            } catch (OutOfMemoryError e) {
                Log.w("TargetDrawable", e.toString() + "");
                return null;
            } catch (IllegalArgumentException e2) {
                Log.w("TargetDrawable", e2.toString() + "");
                return null;
            }
        }
    }

    static class g {
        private static HashMap<Object, g> b = new HashMap();
        private static AnimatorListener c = new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                g.b(animation);
            }

            public void onAnimationCancel(Animator animation) {
                g.b(animation);
            }
        };
        ObjectAnimator a;

        public g(ObjectAnimator anim) {
            this.a = anim;
        }

        private static void b(Animator animator) {
            Iterator<Entry<Object, g>> iter = b.entrySet().iterator();
            while (iter.hasNext()) {
                if (((g) ((Entry) iter.next()).getValue()).a == animator) {
                    iter.remove();
                    return;
                }
            }
        }

        public static g a(Object object, long duration, Object... vars) {
            ObjectAnimator anim;
            long delay = 0;
            AnimatorUpdateListener updateListener = null;
            AnimatorListener listener = null;
            TimeInterpolator interpolator = null;
            ArrayList<PropertyValuesHolder> props = new ArrayList(vars.length / 2);
            int i = 0;
            while (i < vars.length) {
                if (vars[i] instanceof String) {
                    String key = vars[i];
                    TimeInterpolator value = vars[i + 1];
                    if (!"simultaneousTween".equals(key)) {
                        if ("ease".equals(key)) {
                            interpolator = value;
                        } else if ("onUpdate".equals(key) || "onUpdateListener".equals(key)) {
                            updateListener = (AnimatorUpdateListener) value;
                        } else if ("onComplete".equals(key) || "onCompleteListener".equals(key)) {
                            listener = (AnimatorListener) value;
                        } else if ("delay".equals(key)) {
                            delay = ((Number) value).longValue();
                        } else if ("syncWith".equals(key)) {
                            continue;
                        } else if (value instanceof float[]) {
                            props.add(PropertyValuesHolder.ofFloat(key, new float[]{((float[]) value)[0], ((float[]) value)[1]}));
                        } else if (value instanceof int[]) {
                            props.add(PropertyValuesHolder.ofInt(key, new int[]{((int[]) value)[0], ((int[]) value)[1]}));
                        } else if (value instanceof Number) {
                            props.add(PropertyValuesHolder.ofFloat(key, new float[]{((Number) value).floatValue()}));
                        } else {
                            throw new IllegalArgumentException("Bad argument for key \"" + key + "\" with value " + value.getClass());
                        }
                    }
                    i += 2;
                } else {
                    throw new IllegalArgumentException("Key must be a string: " + vars[i]);
                }
            }
            g tween = (g) b.get(object);
            if (tween == null) {
                anim = ObjectAnimator.ofPropertyValuesHolder(object, (PropertyValuesHolder[]) props.toArray(new PropertyValuesHolder[props.size()]));
                tween = new g(anim);
                b.put(object, tween);
            } else {
                anim = ((g) b.get(object)).a;
                a(props, object);
            }
            if (interpolator != null) {
                anim.setInterpolator(interpolator);
            }
            anim.setStartDelay(delay);
            anim.setDuration(duration);
            if (updateListener != null) {
                anim.removeAllUpdateListeners();
                anim.addUpdateListener(updateListener);
            }
            if (listener != null) {
                anim.removeAllListeners();
                anim.addListener(listener);
            }
            anim.addListener(c);
            return tween;
        }

        public static void a() {
            b.clear();
        }

        private static void a(ArrayList<PropertyValuesHolder> props, Object... args) {
            for (Object killobject : args) {
                g tween = (g) b.get(killobject);
                if (tween != null) {
                    tween.a.cancel();
                    if (props != null) {
                        tween.a.setValues((PropertyValuesHolder[]) props.toArray(new PropertyValuesHolder[props.size()]));
                    } else {
                        b.remove(tween);
                    }
                }
            }
        }
    }

    private class h {
        final /* synthetic */ MultiWaveView a;
        private float b;

        private h(MultiWaveView multiWaveView) {
            this.a = multiWaveView;
        }

        public float a() {
            return this.b;
        }
    }

    public MultiWaveView(Context context) {
        this(context, null);
    }

    public MultiWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, com.meizu.common.a.b.MeizuCommon_MultiWaveView);
    }

    public MultiWaveView(Context context, AttributeSet attrs, int defStyle) {
        boolean z = true;
        super(context, attrs, defStyle);
        this.a = new ArrayList();
        this.b = new a();
        this.c = new a();
        this.m = false;
        this.n = new c();
        this.o = 0;
        this.q = -1;
        this.w = 0.0f;
        this.z = new AnimatorListenerAdapter(this) {
            final /* synthetic */ MultiWaveView a;

            {
                this.a = r1;
            }

            public void onAnimationEnd(Animator animator) {
                this.a.a(0, this.a.s, this.a.t);
                this.a.e();
                this.a.aw = false;
            }
        };
        this.A = new AnimatorListenerAdapter(this) {
            final /* synthetic */ MultiWaveView a;

            {
                this.a = r1;
            }

            public void onAnimationEnd(Animator animator) {
                this.a.a(0, this.a.s, this.a.t);
                this.a.e();
                this.a.aw = false;
            }
        };
        this.B = new AnimatorUpdateListener(this) {
            final /* synthetic */ MultiWaveView a;

            {
                this.a = r1;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                this.a.q = this.a.a(Math.atan2((double) (-this.a.i.c()), (double) this.a.i.b()));
                float tx = this.a.i.b();
                if (tx < 0.0f && tx > -117.0f * this.a.aL) {
                    this.a.ay = ((Integer) this.a.aC.evaluate((-tx) / (this.a.aL * 117.0f), Integer.valueOf(this.a.ax), Integer.valueOf(this.a.aA))).intValue();
                } else if (tx > 0.0f && tx < this.a.aL * 117.0f) {
                    this.a.ay = ((Integer) this.a.aC.evaluate(tx / (this.a.aL * 117.0f), Integer.valueOf(this.a.ax), Integer.valueOf(this.a.az))).intValue();
                }
                this.a.invalidate();
            }
        };
        this.I = 1;
        this.J = true;
        this.L = 0;
        this.T = 1.4765486f;
        this.U = 0.0f;
        this.aa = 0.0f;
        this.ad = 0.0f;
        this.ae = 0.0f;
        this.ah = 0.0f;
        this.ai = 0.0f;
        this.as = false;
        this.at = true;
        this.au = false;
        this.av = true;
        this.aw = false;
        this.ax = 234881023;
        this.ay = this.O;
        this.az = -12339861;
        this.aA = -626650;
        this.aB = this.ax;
        this.aC = new ArgbEvaluator();
        this.aF = 0.0f;
        this.aH = 0;
        this.aI = 0;
        this.aL = getResources().getDisplayMetrics().density;
        this.aM = null;
        this.aN = -1;
        this.aO = new AnimatorListenerAdapter(this) {
            final /* synthetic */ MultiWaveView a;

            {
                this.a = r1;
            }

            public void onAnimationEnd(Animator animator) {
                this.a.au = false;
                this.a.a(5, this.a.s, this.a.t);
            }
        };
        Resources res = context.getResources();
        TypedArray a = context.obtainStyledAttributes(attrs, j.MultiWaveView, defStyle, 0);
        this.aj = a.getDimension(j.MultiWaveView_mcTargetMinRadius, this.aj);
        this.ak = a.getDimension(j.MultiWaveView_mcTargetMaxRadius, this.ak);
        this.w = a.getDimension(j.MultiWaveView_mcOuterRadius, this.w);
        this.o = a.getInt(j.MultiWaveView_mcVibrationDuration, this.o);
        this.i = new f(res, a.peekValue(j.MultiWaveView_mcHandleDrawable).resourceId);
        this.r = (float) (this.i.g() / 2);
        this.j = new f(res, a.peekValue(j.MultiWaveView_mcWaveDrawable).resourceId);
        this.g = this.i.b();
        this.h = this.i.c();
        TypedValue outValue = new TypedValue();
        if (a.getValue(j.MultiWaveView_mcTargetDrawables, outValue)) {
            c(outValue.resourceId);
        }
        if (this.a == null || this.a.size() == 0) {
            throw new IllegalStateException("Must specify at least one target drawable");
        }
        int resourceId;
        if (a.getValue(j.MultiWaveView_mcTargetDescriptions, outValue)) {
            resourceId = outValue.resourceId;
            if (resourceId == 0) {
                throw new IllegalStateException("Must specify target descriptions");
            }
            setTargetDescriptionsResourceId(resourceId);
        }
        if (a.getValue(j.MultiWaveView_mcDirectionDescriptions, outValue)) {
            resourceId = outValue.resourceId;
            if (resourceId == 0) {
                throw new IllegalStateException("Must specify direction descriptions");
            }
            setDirectionDescriptionsResourceId(resourceId);
        }
        this.P = a.getDimension(j.MultiWaveView_mcMaxPointCircleRadius, this.P);
        this.Q = this.P * 0.12f;
        this.R = a.getDimension(j.MultiWaveView_mcMaxPointRadius, this.R);
        this.S = 0.0f;
        this.ad = a.getDimension(j.MultiWaveView_mcOuterRadiusHeight, this.ad);
        this.ae = (a.getDimension(j.MultiWaveView_mcTargetToHandle, this.ae) + this.r) + this.aj;
        this.af = a.getDimension(j.MultiWaveView_mcTargetSnapRadius, this.af);
        this.ag = this.ak / this.aj;
        this.al = 135.0f / this.ae;
        this.N = a.getColor(j.MultiWaveView_mcPointColor, -16777216);
        this.O = a.getColor(j.MultiWaveView_mcHandleCircleColor, 687865855);
        a.recycle();
        this.am = new Paint();
        this.am.setAntiAlias(true);
        this.am.setColor(this.N);
        this.aG = new Paint();
        this.aG.setColor(this.aB);
        this.aG.setStyle(Style.FILL_AND_STROKE);
        this.aG.setStrokeWidth(getResources().getDimension(com.meizu.common.a.d.mc_multiwaveview_handle_circle_width));
        this.aE = new Paint();
        this.aE.setAntiAlias(true);
        this.aE.setColor(this.ax);
        this.aE.setStyle(Style.STROKE);
        this.aE.setStrokeWidth(5.0f);
        this.an = new Paint();
        this.an.setAntiAlias(true);
        this.an.setColor(this.O);
        this.an.setStyle(Style.FILL_AND_STROKE);
        this.an.setStrokeWidth(getResources().getDimension(com.meizu.common.a.d.mc_multiwaveview_handle_circle_width));
        this.aD = new Paint();
        this.aD.setAntiAlias(true);
        this.aD.setColor(this.O);
        this.aD.setStyle(Style.FILL_AND_STROKE);
        this.aD.setStrokeWidth(getResources().getDimension(com.meizu.common.a.d.mc_multiwaveview_handle_circle_width));
        this.M = ViewConfiguration.get(context).getScaledTouchSlop();
        this.aq = new h();
        c();
        this.ar = new h();
        if (this.o <= 0) {
            z = false;
        }
        setVibrateEnabled(z);
        h();
    }

    protected int getSuggestedMinimumWidth() {
        return (int) ((Math.max((float) this.j.g(), this.w * 2.0f) + ((float) (this.v * 2))) + (this.r * 2.0f));
    }

    protected int getSuggestedMinimumHeight() {
        return (int) (this.ad + ((float) this.u));
    }

    private int a(int measureSpec, int desired) {
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {
            case Integer.MIN_VALUE:
                return Math.min(specSize, desired);
            case 0:
                return desired;
            default:
                return specSize;
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minimumWidth = getSuggestedMinimumWidth();
        int minimumHeight = getSuggestedMinimumHeight();
        int computedWidth = a(widthMeasureSpec, minimumWidth);
        int computedHeight = a(heightMeasureSpec, minimumHeight);
        b(computedWidth - minimumWidth, computedHeight - minimumHeight);
        setMeasuredDimension(computedWidth, computedHeight);
    }

    private void a(int state, float x, float y) {
        switch (state) {
            case 0:
                this.i.a(f.b);
                return;
            case 2:
                setGrabbedState(1);
                if (a(getContext()).isEnabled()) {
                    i();
                    return;
                }
                return;
            case 5:
                f();
                return;
            default:
                return;
        }
    }

    private void a(int duration, int delay, TimeInterpolator interpolator, float finalAlpha, float finalX, float finalY, float finalAngle, AnimatorListener finishListener) {
        this.b.b();
        this.b.add(g.a(this.i, (long) duration, "ease", interpolator, "delay", Integer.valueOf(delay), "alpha", Float.valueOf(finalAlpha), "x", Float.valueOf(finalX), "y", Float.valueOf(finalY), "rotation", Float.valueOf(finalAngle), "onUpdate", this.B, "onComplete", finishListener));
        this.b.a();
    }

    private void b(int duration, int delay, TimeInterpolator interpolator, float finalAlpha, float finalX, float finalY, float finalAngle, AnimatorListener finishListener) {
        a animationBundle = new a();
        animationBundle.add(g.a(this.i, (long) duration, "ease", interpolator, "delay", Integer.valueOf(delay), "alpha", Float.valueOf(finalAlpha), "x", Float.valueOf(finalX), "y", Float.valueOf(finalY), "rotation", Float.valueOf(finalAngle), "onUpdate", this.B, "onComplete", finishListener));
        animationBundle.a();
    }

    void a(f drawable) {
        int width = drawable.g();
        int height = drawable.h();
        RectF childBounds = new RectF(0.0f, 0.0f, (float) width, (float) height);
        childBounds.offset(drawable.b() - ((float) (width / 2)), drawable.c() - ((float) (height / 2)));
        View view = this;
        while (view.getParent() != null && (view.getParent() instanceof View)) {
            view = (View) view.getParent();
            view.getMatrix().mapRect(childBounds);
            view.invalidate((int) Math.floor((double) childBounds.left), (int) Math.floor((double) childBounds.top), (int) Math.ceil((double) childBounds.right), (int) Math.ceil((double) childBounds.bottom));
        }
    }

    private void a(int whichTarget) {
        g();
        if (this.f != null) {
            Log.i("MultiWaveView", "onTrigger whichTarget = " + whichTarget + " mIsBluetoothAns = " + this.m);
            this.f.a(this, whichTarget, this.m);
            this.m = false;
        }
    }

    private void e() {
        if (this.f != null) {
            this.f.a();
        }
    }

    public void a() {
        if (this.c != null) {
            this.c.b();
        }
    }

    private void f() {
        int activeTarget = this.q;
        if (activeTarget != -1) {
            AnimatorListener finish = new AnimatorListenerAdapter(this) {
                final /* synthetic */ MultiWaveView a;

                {
                    this.a = r1;
                }

                public void onAnimationCancel(Animator animation) {
                    g.a();
                    this.a.b(330, 1200, a.b, 1.0f, 0.0f, 0.0f, 0.0f, this.a.z);
                }

                public void onAnimationEnd(Animator animator) {
                    g.a();
                    this.a.b(330, 1200, a.b, 1.0f, 0.0f, 0.0f, 0.0f, this.a.z);
                }
            };
            a(activeTarget);
            if (this.x) {
                switch (activeTarget) {
                    case 0:
                        a(100, 0, K, this.i.d(), this.ae, 0.0f, 0.0f, finish);
                        break;
                    case 1:
                        a(100, 0, K, this.i.d(), -this.ae, 0.0f, 135.0f, finish);
                        break;
                }
                if (this.ao != null) {
                    this.ao.cancel();
                    c();
                    invalidate();
                }
            }
        } else {
            a(330, 200, a.b, 1.0f, 0.0f, 0.0f, 0.0f, this.A);
        }
        setGrabbedState(0);
        this.x = false;
    }

    private void g() {
        if (this.k != null) {
            this.k.vibrate((long) this.o);
        }
    }

    private ArrayList<f> b(int resourceId) {
        Resources res = getContext().getResources();
        TypedArray array = res.obtainTypedArray(resourceId);
        int count = array.length();
        ArrayList<f> drawables = new ArrayList(count);
        for (int i = 0; i < count; i++) {
            f target;
            TypedValue value = array.peekValue(i);
            if (value == null || value.type != 28) {
                target = new f(res, value != null ? value.resourceId : 0);
            } else {
                target = new f(res.getColor(value.resourceId), this.aj);
            }
            drawables.add(target);
        }
        array.recycle();
        return drawables;
    }

    private void c(int resourceId) {
        this.a = b(resourceId);
        this.D = resourceId;
        int count = this.a.size();
        int maxWidth = this.i.g();
        int maxHeight = this.i.h();
        for (int i = 0; i < count; i++) {
            f target = (f) this.a.get(i);
            maxWidth = Math.max(maxWidth, target.g());
            maxHeight = Math.max(maxHeight, target.h());
        }
        if (this.v == maxWidth && this.u == maxHeight) {
            b(this.s, this.t);
            return;
        }
        this.v = maxWidth;
        this.u = maxHeight;
        requestLayout();
    }

    public void setTargetResources(int resourceId) {
        if (this.C) {
            this.y = resourceId;
        } else {
            c(resourceId);
        }
    }

    public int getTargetResourceId() {
        return this.D;
    }

    public void setTargetDescriptionsResourceId(int resourceId) {
        this.E = resourceId;
        if (this.d != null) {
            this.d.clear();
        }
    }

    public int getTargetDescriptionsResourceId() {
        return this.E;
    }

    public void setDirectionDescriptionsResourceId(int resourceId) {
        this.F = resourceId;
        if (this.e != null) {
            this.e.clear();
        }
    }

    public int getDirectionDescriptionsResourceId() {
        return this.F;
    }

    public void setVibrateEnabled(boolean enabled) {
        if (enabled && this.k == null) {
            this.k = (Vibrator) getContext().getSystemService("vibrator");
        } else if (!enabled) {
            this.k = null;
        }
    }

    public void b() {
        if (!(this.q == -1 || this.x || this.au)) {
            a(330, 200, a.b, 1.0f, 0.0f, 0.0f, 0.0f, this.A);
            this.q = -1;
        }
        if (this.ao == null || !this.ao.isStarted()) {
            c();
            a(1600, K, 0.0f);
        }
        this.aw = false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case 0:
                this.ay = this.O;
                a(event);
                if (this.aw && this.ao != null) {
                    this.ao.cancel();
                    break;
                }
            case 1:
            case 6:
                d(event);
                if (event != null) {
                    Log.i("MultiWaveView", "Action Up x = " + event.getX() + " y = " + event.getY());
                }
                b(event);
                break;
            case 2:
                d(event);
                break;
            case 3:
                d(event);
                c(event);
                break;
            default:
                return super.onTouchEvent(event);
        }
        invalidate();
        return true;
    }

    private void a(float x, float y, boolean animate) {
        this.i.a(x);
        this.i.b(y);
    }

    private void a(MotionEvent event) {
        float x;
        float y;
        if (event != null) {
            int actionIndex = event.getActionIndex();
            this.aN = event.getPointerId(actionIndex);
            x = event.getX(actionIndex);
            y = event.getY(actionIndex);
            a();
        } else if (this.aN == -1) {
            x = this.l.a;
            y = this.l.b;
        } else {
            return;
        }
        this.ah = x;
        this.ai = y;
        a(1, x, y);
        if (!a(x, y)) {
            this.x = false;
            this.au = false;
            this.q = -1;
            b();
        }
    }

    private void b(MotionEvent event) {
        int actionIndex;
        float x;
        float y;
        if (this.x) {
            if (event == null) {
                actionIndex = event.getActionIndex();
                if (event.getPointerId(actionIndex) == this.aN) {
                    x = event.getX(actionIndex);
                    y = event.getY(actionIndex);
                    this.aN = -1;
                } else {
                    return;
                }
            } else if (this.aN != -1) {
                x = this.l.a;
                y = this.l.b;
            } else {
                return;
            }
            if (!this.au) {
                a(5, x, y);
            }
            if (this.aw) {
                this.at = false;
            }
        }
        if (event == null) {
            actionIndex = event.getActionIndex();
            if (event.getPointerId(actionIndex) == this.aN) {
                x = event.getX(actionIndex);
                y = event.getY(actionIndex);
                this.aN = -1;
            } else {
                return;
            }
        } else if (this.aN != -1) {
            x = this.l.a;
            y = this.l.b;
        } else {
            return;
        }
        if (this.au) {
            a(5, x, y);
        }
        if (this.aw) {
            this.at = false;
        }
    }

    private void c(MotionEvent event) {
        int actionIndex;
        float x;
        float y;
        if (this.x) {
            if (event == null) {
                actionIndex = event.getActionIndex();
                if (event.getPointerId(actionIndex) == this.aN) {
                    x = event.getX(actionIndex);
                    y = event.getY(actionIndex);
                    this.aN = -1;
                } else {
                    return;
                }
            } else if (this.aN != -1) {
                x = this.l.a;
                y = this.l.b;
            } else {
                return;
            }
            if (!this.au) {
                a(5, x, y);
            }
        }
        if (event == null) {
            actionIndex = event.getActionIndex();
            if (event.getPointerId(actionIndex) == this.aN) {
                x = event.getX(actionIndex);
                y = event.getY(actionIndex);
                this.aN = -1;
            } else {
                return;
            }
        } else if (this.aN != -1) {
            x = this.l.a;
            y = this.l.b;
        } else {
            return;
        }
        if (!this.au) {
            a(5, x, y);
        }
    }

    private void d(MotionEvent event) {
        a(event, false);
    }

    private void a(MotionEvent event, boolean ignorSnap) {
        int pointerIndex = 0;
        if (event == null) {
            if (this.aN != -1) {
                return;
            }
        } else if (this.aN != -1) {
            pointerIndex = event.findPointerIndex(this.aN);
        } else {
            return;
        }
        int activeTarget = -1;
        int historySize = event == null ? 0 : event.getHistorySize();
        float x = 0.0f;
        float y = 0.0f;
        float eventX = event.getX();
        float eventY = event.getY();
        int k = 0;
        while (k < historySize + 1) {
            if (event == null) {
                eventX = this.l.a;
                eventY = this.l.b;
            } else {
                eventX = k < historySize ? event.getHistoricalX(pointerIndex, k) : event.getX(pointerIndex);
                eventY = k < historySize ? event.getHistoricalY(pointerIndex, k) : event.getY(pointerIndex);
            }
            float tx = eventX - this.s;
            float ty = eventY - this.t;
            float touchRadius = (float) Math.sqrt((double) c(tx, ty));
            float scale = touchRadius > this.ae ? this.ae / touchRadius : 1.0f;
            float limitX = tx * scale;
            float limitY = ty * scale;
            double angleRad = Math.atan2((double) (-ty), (double) tx);
            if (!(this.x || this.au)) {
                a(eventX, eventY);
            }
            if (!this.au) {
                if (this.x) {
                    activeTarget = a(angleRad);
                }
                x = limitX;
                y = limitY;
                if (this.i != null && this.x) {
                    if (tx < 0.0f && tx > -117.0f * this.aL) {
                        this.i.c(135.0f * ((-tx) / (117.0f * this.aL)));
                        this.ay = ((Integer) this.aC.evaluate((-tx) / (117.0f * this.aL), Integer.valueOf(this.O), Integer.valueOf(this.aA))).intValue();
                    } else if (tx > 0.0f && tx < 117.0f * this.aL) {
                        this.ay = ((Integer) this.aC.evaluate(tx / (117.0f * this.aL), Integer.valueOf(this.O), Integer.valueOf(this.az))).intValue();
                        this.i.c(0.0f);
                    }
                }
                k++;
            } else {
                return;
            }
        }
        if (this.x) {
            if (activeTarget != -1) {
                a(4, x, y);
                a(x, y, false);
            } else {
                a(3, x, y);
                a(x, y, false);
            }
            float deltaX = Math.abs(this.ah - eventX);
            float deltaY = Math.abs(this.ai - eventY);
            if (this.at && !((deltaY <= ((float) this.M) && deltaX <= ((float) this.M)) || this.as || this.ao == null || this.ao.isStarted())) {
                this.at = false;
                invalidate();
            }
            a(this.i);
            this.q = activeTarget;
        }
    }

    public boolean onHoverEvent(MotionEvent event) {
        if (a(getContext()).isTouchExplorationEnabled()) {
            int action = event.getAction();
            switch (action) {
                case 7:
                    event.setAction(2);
                    break;
                case 9:
                    event.setAction(0);
                    break;
                case 10:
                    event.setAction(1);
                    break;
            }
            onTouchEvent(event);
            event.setAction(action);
        }
        return super.onHoverEvent(event);
    }

    private void setGrabbedState(int newState) {
        if (newState != this.p) {
            if (newState != 0) {
                g();
            }
            this.p = newState;
            if (this.f != null) {
                if (newState == 0) {
                    this.f.b(this, 1);
                } else {
                    this.f.a(this, 1);
                }
                this.f.c(this, newState);
            }
        }
    }

    private boolean a(float x, float y) {
        float tx = x - this.s;
        float ty = y - this.t;
        if (c(tx, ty) <= getScaledTapRadiusSquared()) {
            a(2, x, y);
            a(tx, ty, false);
            d(x, y);
            this.x = true;
            return true;
        } else if (this.av || Math.sqrt((double) c(tx, ty)) < ((double) (this.ae - this.af)) || Math.abs(ty) > this.r) {
            return false;
        } else {
            this.q = a(Math.atan2((double) (-ty), (double) tx));
            if (this.i.b() != this.g || this.i.c() != this.h) {
                return false;
            }
            if (tx < 0.0f) {
                a(330, 0, K, this.i.d(), -this.ae, 0.0f, 135.0f, this.aO);
            } else if (tx > 0.0f) {
                a(330, 0, K, this.i.d(), this.ae, 0.0f, 0.0f, this.aO);
            }
            this.au = true;
            if (this.ao != null) {
                this.ao.cancel();
                c();
                invalidate();
            }
            return true;
        }
    }

    private void h() {
        if (this.w == 0.0f) {
            this.w = ((float) Math.max(this.j.g(), this.j.h())) / 2.0f;
        }
    }

    private void b(int dx, int dy) {
        int absoluteGravity = Gravity.getAbsoluteGravity(this.I, getLayoutDirection());
        switch (absoluteGravity & 7) {
            case 3:
                this.G = 0;
                break;
            case 5:
                this.G = dx;
                break;
            default:
                this.G = dx / 2;
                break;
        }
        switch (absoluteGravity & 112) {
            case 48:
                this.H = 0;
                return;
            case 80:
                this.H = dy;
                return;
            default:
                this.H = dy / 2;
                return;
        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int height = bottom - top;
        float newWaveCenterX = ((float) this.G) + (Math.max((float) (right - left), (((float) (this.v * 2)) + Math.max((float) this.j.g(), 2.0f * this.w)) + (this.r * 2.0f)) / 2.0f);
        float newWaveCenterY = ((float) this.H) + (Math.max((float) height, ((float) this.u) + this.ad) / 2.0f);
        if (this.J) {
            a(0.0f, 0.0f, false);
            this.J = false;
        }
        this.j.d(newWaveCenterX);
        this.j.e(newWaveCenterY);
        this.i.d(newWaveCenterX);
        this.i.e(newWaveCenterY);
        b(newWaveCenterX, newWaveCenterY);
        this.s = newWaveCenterX;
        this.t = newWaveCenterY;
        this.V = this.s;
        this.W = this.t;
    }

    private void b(float centerX, float centerY) {
        ArrayList<f> targets = this.a;
        int size = targets.size();
        float alpha = (float) (-6.283185307179586d / ((double) size));
        for (int i = 0; i < size; i++) {
            f targetIcon = (f) targets.get(i);
            float angle = alpha * ((float) i);
            targetIcon.d(centerX);
            targetIcon.e(centerY);
            targetIcon.a(this.ae * ((float) Math.cos((double) angle)));
            targetIcon.b(this.ae * ((float) Math.sin((double) angle)));
        }
    }

    protected void onDraw(Canvas canvas) {
        if (this.at) {
            canvas.save();
            canvas.translate(this.V, this.W);
            this.aG.setColor(this.aB);
            canvas.drawCircle(0.0f, 0.0f, 43.0f * this.aL, this.aG);
            this.aE.setStrokeWidth(this.aK);
            this.aE.setAlpha(this.aI);
            float ratio = ((61.0f * this.aL) - this.ab) / (18.0f * this.aL);
            if (this.ab > 43.0f * this.aL && this.ab <= 61.0f * this.aL) {
                this.aD.setAlpha((int) (20.0f * ratio));
                this.aE.setStrokeWidth(5.0f * ratio);
            }
            if (this.ab >= 61.0f * this.aL) {
                this.aE.setAlpha(0);
                this.aE.setStrokeWidth(0.0f);
            }
            canvas.drawCircle(0.0f, 0.0f, this.ab, this.aE);
            this.aE.setAlpha(this.aI);
            this.aE.setStrokeWidth(this.aK);
            if (this.ab > 53.0f * this.aL) {
                this.aE.setStrokeWidth(5.0f * (((77.0f * this.aL) - this.ab) / (24.0f * this.aL)));
                canvas.drawCircle(0.0f, 0.0f, this.ab - (9.0f * this.aL), this.aE);
            }
            this.aD.setColor(this.az);
            this.aD.setAlpha(this.aH);
            ratio = ((this.aL * 133.0f) - this.aJ) / (33.0f * this.aL);
            if (this.aJ >= 100.0f * this.aL && this.aJ <= this.aL * 133.0f) {
                this.aD.setAlpha((int) (255.0f * ratio));
            }
            if (this.aJ >= this.aL * 133.0f) {
                this.aD.setAlpha(0);
            }
            canvas.drawCircle(this.aJ, 0.0f, (5.0f * this.aL) + 1.0f, this.aD);
            this.aD.setAlpha(this.aH);
            if (this.aJ > 75.0f * this.aL) {
                ratio = ((this.aL * 155.0f) - this.aJ) / (33.0f * this.aL);
                if (this.aJ >= 122.0f * this.aL && this.aJ <= this.aL * 155.0f) {
                    this.aD.setAlpha((int) (255.0f * ratio));
                }
                if (this.aJ >= this.aL * 155.0f) {
                    this.aD.setAlpha(0);
                }
                canvas.drawCircle(this.aJ - (20.0f * this.aL), 0.0f, 3.0f * this.aL, this.aD);
            }
            this.aD.setAlpha(this.aH);
            if (this.aJ > 91.0f * this.aL) {
                if (this.aJ > 171.0f * this.aL) {
                    this.aD.setAlpha(0);
                }
                canvas.drawCircle(this.aJ - (36.0f * this.aL), 0.0f, this.aL + 1.0f, this.aD);
            }
            this.aD.setColor(this.aA);
            this.aD.setAlpha(this.aH);
            ratio = ((this.aL * 133.0f) - this.aJ) / (33.0f * this.aL);
            if (this.aJ >= 100.0f * this.aL && this.aJ <= this.aL * 133.0f) {
                this.aD.setAlpha((int) (255.0f * ratio));
            }
            if (this.aJ >= this.aL * 133.0f) {
                this.aD.setAlpha(0);
            }
            canvas.drawCircle(-this.aJ, 0.0f, (5.0f * this.aL) + 1.0f, this.aD);
            this.aD.setAlpha(this.aH);
            if (this.aJ > 75.0f * this.aL) {
                ratio = ((this.aL * 155.0f) - this.aJ) / (33.0f * this.aL);
                if (this.aJ >= 122.0f * this.aL && this.aJ <= this.aL * 155.0f) {
                    this.aD.setAlpha((int) (255.0f * ratio));
                }
                if (this.aJ >= this.aL * 155.0f) {
                    this.aD.setAlpha(0);
                }
                canvas.drawCircle((-this.aJ) + (20.0f * this.aL), 0.0f, 3.0f * this.aL, this.aD);
            }
            this.aD.setAlpha(this.aH);
            if (this.aJ > 91.0f * this.aL) {
                if (this.aJ > 171.0f * this.aL) {
                    this.aD.setAlpha(0);
                }
                canvas.drawCircle((-this.aJ) + (36.0f * this.aL), 0.0f, this.aL + 1.0f, this.aD);
            }
            this.i.c(this.aF);
            canvas.restore();
        }
        if (this.aw && this.ar != null) {
            this.at = false;
            canvas.save();
            this.an.setColor(this.ay);
            canvas.drawCircle(this.i.b() + this.i.e(), this.i.c() + this.i.f(), this.ar.a(), this.an);
            canvas.restore();
        }
        int ntargets = this.a.size();
        if (!this.at) {
            for (int i = 0; i < ntargets; i++) {
                f target = (f) this.a.get(i);
                if (target != null) {
                    target.a(canvas, false);
                }
            }
        }
        this.i.a(canvas, true);
    }

    public void setOnTriggerListener(e listener) {
        this.f = listener;
    }

    private float a(float d) {
        return d * d;
    }

    private float c(float dx, float dy) {
        return (dx * dx) + (dy * dy);
    }

    private float getScaledTapRadiusSquared() {
        float scaledTapRadius;
        if (a(getContext()).isEnabled()) {
            scaledTapRadius = (1.3f * this.r) * 2.0f;
        } else {
            scaledTapRadius = this.r * 2.0f;
        }
        return a(scaledTapRadius);
    }

    private void i() {
        StringBuilder utterance = new StringBuilder();
        int targetCount = this.a.size();
        for (int i = 0; i < targetCount; i++) {
            String targetDescription = d(i);
            String directionDescription = e(i);
            if (!(TextUtils.isEmpty(targetDescription) || TextUtils.isEmpty(directionDescription))) {
                utterance.append(String.format(directionDescription, new Object[]{targetDescription}));
            }
            if (utterance.length() > 0) {
                a(utterance.toString());
            }
        }
    }

    private void a(String text) {
        setContentDescription(text);
        sendAccessibilityEvent(8);
        setContentDescription(null);
    }

    private String d(int index) {
        if (this.d == null || this.d.isEmpty()) {
            this.d = f(this.E);
            if (this.a.size() != this.d.size()) {
                Log.w("MultiWaveView", "The number of target drawables must be euqal to the number of target descriptions.");
                return null;
            }
        }
        return (String) this.d.get(index);
    }

    private String e(int index) {
        if (this.e == null || this.e.isEmpty()) {
            this.e = f(this.F);
            if (this.a.size() != this.e.size()) {
                Log.w("MultiWaveView", "The number of target drawables must be euqal to the number of direction descriptions.");
                return null;
            }
        }
        return (String) this.e.get(index);
    }

    private ArrayList<String> f(int resourceId) {
        TypedArray array = getContext().getResources().obtainTypedArray(resourceId);
        int count = array.length();
        ArrayList<String> targetContentDescriptions = new ArrayList(count);
        for (int i = 0; i < count; i++) {
            targetContentDescriptions.add(array.getString(i));
        }
        array.recycle();
        return targetContentDescriptions;
    }

    public void setEnableTarget(int resourceId, boolean enabled) {
        for (int i = 0; i < this.a.size(); i++) {
            f target = (f) this.a.get(i);
            if (target.i() == resourceId) {
                target.a(enabled);
                return;
            }
        }
    }

    private AccessibilityManager a(Context context) {
        try {
            return (AccessibilityManager) Class.forName("android.view.accessibility.AccessibilityManager").getMethod("getInstance", new Class[]{Context.class}).invoke(null, new Object[]{context});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setGravity(int gravity) {
        this.I = gravity;
    }

    public void c() {
        this.ab = this.Q;
        this.ac = this.S;
        this.L = 0;
        this.aa = 0.0f;
        this.as = false;
        this.V = this.s;
        this.W = this.t;
        this.at = true;
        this.aH = 0;
        this.aB = 0;
        this.aF = 0.0f;
        this.aI = 0;
    }

    private void a(int duration, TimeInterpolator interpolator, float targetRadius) {
        PropertyValuesHolder pvRadius = PropertyValuesHolder.ofFloat("radius", new float[]{45.0f * this.aL, 73.0f * this.aL});
        PropertyValuesHolder pvAngel = PropertyValuesHolder.ofFloat("angel", new float[]{this.U, this.T});
        float[] fArr = new float[2];
        PropertyValuesHolder pvStrokeWidth = PropertyValuesHolder.ofFloat("strokewidth", new float[]{5.0f, 0.0f});
        PropertyValuesHolder pvTranslateCircleX = PropertyValuesHolder.ofFloat("translateX", new float[]{53.0f * this.aL, 171.0f * this.aL});
        int[] iArr = new int[2];
        PropertyValuesHolder pvBigCircleAlpha = PropertyValuesHolder.ofInt("bigcirclealpha", new int[]{30, 0});
        Keyframe alphaKey0 = Keyframe.ofInt(0.0f, 0);
        Keyframe alphaKey1 = Keyframe.ofInt(0.4f, 255);
        Keyframe alphaKey2 = Keyframe.ofInt(0.78f, 255);
        Keyframe alphaKey3 = Keyframe.ofInt(1.0f, 0);
        PropertyValuesHolder pvAlpha = PropertyValuesHolder.ofKeyframe("ballalpha", new Keyframe[]{alphaKey0, alphaKey1, alphaKey2, alphaKey3});
        Keyframe pointRadiusKey0 = Keyframe.ofFloat(0.0f, this.S);
        Keyframe pointRadiusKey1 = Keyframe.ofFloat(0.4f, this.R);
        Keyframe pointRadiusKey2 = Keyframe.ofFloat(0.78f, this.R);
        Keyframe pointRadiusKey3 = Keyframe.ofFloat(1.0f, this.S);
        PropertyValuesHolder pvPointRadius = PropertyValuesHolder.ofKeyframe("pointRadius", new Keyframe[]{pointRadiusKey0, pointRadiusKey1, pointRadiusKey2, pointRadiusKey3});
        Keyframe ballalpha0 = Keyframe.ofInt(0.0f, 0);
        Keyframe ballalpha1 = Keyframe.ofInt(0.4f, Opcodes.IF_ICMPNE);
        Keyframe ballalpha2 = Keyframe.ofInt(0.75f, Opcodes.IF_ICMPNE);
        Keyframe ballalpha3 = Keyframe.ofInt(1.0f, 0);
        PropertyValuesHolder pvBallAlpha = PropertyValuesHolder.ofKeyframe("alpha", new Keyframe[]{ballalpha0, ballalpha1, ballalpha2, ballalpha3});
        Keyframe shakekey0 = Keyframe.ofFloat(0.0f, 0.0f);
        Keyframe shakekey1 = Keyframe.ofFloat(0.25f, 9.0f);
        Keyframe shakekey2 = Keyframe.ofFloat(0.5f, -9.0f);
        Keyframe shakekey3 = Keyframe.ofFloat(0.75f, 9.0f);
        Keyframe shakekey4 = Keyframe.ofFloat(1.0f, 0.0f);
        PropertyValuesHolder pvIconShake = PropertyValuesHolder.ofKeyframe("rotation", new Keyframe[]{shakekey0, shakekey1, shakekey2, shakekey3, shakekey4});
        ObjectAnimator shakeAnimator = ObjectAnimator.ofPropertyValuesHolder(this.aq, new PropertyValuesHolder[]{pvIconShake});
        shakeAnimator.setDuration(500);
        shakeAnimator.setInterpolator(new DecelerateInterpolator());
        shakeAnimator.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ MultiWaveView a;

            {
                this.a = r1;
            }

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.a.aF = ((Float) valueAnimator.getAnimatedValue("rotation")).floatValue();
                this.a.invalidate();
            }
        });
        final ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(this.aq, new PropertyValuesHolder[]{pvRadius, pvAngel, pvAlpha, pvPointRadius, pvStrokeWidth, pvTranslateCircleX, pvBallAlpha, pvBigCircleAlpha});
        objectAnimator.setDuration((long) duration);
        objectAnimator.setInterpolator(interpolator);
        final float f = targetRadius;
        objectAnimator.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ MultiWaveView c;

            public void onAnimationUpdate(ValueAnimator animation) {
                this.c.aH = ((Integer) animation.getAnimatedValue("ballalpha")).intValue();
                this.c.aJ = ((Float) animation.getAnimatedValue("translateX")).floatValue();
                this.c.aK = ((Float) animation.getAnimatedValue("strokewidth")).floatValue();
                this.c.ab = ((Float) animation.getAnimatedValue("radius")).floatValue();
                this.c.aa = ((Float) animation.getAnimatedValue("angel")).floatValue();
                this.c.aI = ((Integer) animation.getAnimatedValue("bigcirclealpha")).intValue();
                this.c.aB = this.c.ax;
                this.c.ac = ((Float) animation.getAnimatedValue("pointRadius")).floatValue();
                if (!this.c.as || Math.abs(this.c.ab - f) >= 10.0f) {
                    this.c.invalidate();
                    return;
                }
                this.c.invalidate();
                this.c.as = false;
                objectAnimator.cancel();
            }
        });
        this.aM = new AnimatorSet();
        this.aM.playSequentially(new Animator[]{objectAnimator, shakeAnimator});
        this.aM.addListener(new AnimatorListener(this) {
            final /* synthetic */ MultiWaveView a;

            {
                this.a = r1;
            }

            public void onAnimationStart(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                if (this.a.at) {
                    this.a.aM.start();
                }
            }

            public void onAnimationCancel(Animator animator) {
                this.a.c();
            }

            public void onAnimationRepeat(Animator animator) {
            }
        });
        this.aM.start();
        this.ao = objectAnimator;
    }

    private void d(float x, float y) {
        if (this.ao != null) {
            this.ao.cancel();
            c();
            invalidate();
        }
        this.aw = true;
        a((int) Opcodes.GETFIELD, new DecelerateInterpolator(3.0f), 43.0f * this.aL, this.ak - 5.0f);
    }

    private void a(int duration, TimeInterpolator interpolator, float startRadius, float finalRadius) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this.ar, "radius", new float[]{startRadius, finalRadius});
        objectAnimator.setDuration((long) duration);
        objectAnimator.setInterpolator(interpolator);
        objectAnimator.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ MultiWaveView a;

            {
                this.a = r1;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                this.a.invalidate();
            }
        });
        objectAnimator.start();
        this.ap = objectAnimator;
    }

    private int a(double angleRad) {
        ArrayList<f> targets = this.a;
        int activeTarget = -1;
        int ntargets = targets.size();
        for (int i = 0; i < ntargets; i++) {
            f target = (f) targets.get(i);
            double targetMinRad = (((((double) i) - 0.5d) * 2.0d) * 3.141592653589793d) / ((double) ntargets);
            double targetMaxRad = (((((double) i) + 0.5d) * 2.0d) * 3.141592653589793d) / ((double) ntargets);
            float distance = (float) Math.sqrt((double) c((target.e() + target.b()) - (this.i.e() + this.i.b()), (target.f() + target.c()) - (this.i.f() + this.i.c())));
            if (target.a()) {
                boolean angleMatches = (angleRad > targetMinRad && angleRad <= targetMaxRad) || (6.283185307179586d + angleRad > targetMinRad && 6.283185307179586d + angleRad <= targetMaxRad);
                if (angleMatches && distance <= this.af) {
                    activeTarget = i;
                    float f = (((this.ag - 1.0f) / this.af) * (this.af - distance)) + 1.0f;
                }
            }
        }
        switch (activeTarget) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                return activeTarget;
        }
    }

    public void d() {
        if (this.ao != null) {
            this.ao.end();
        }
        if (this.b != null) {
            this.b.c();
        }
        if (this.ap != null) {
            this.ap.end();
        }
        if (this.aM != null) {
            this.aM.cancel();
        }
        invalidate();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        d();
    }
}

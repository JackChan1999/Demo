package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.meizu.common.a.f;
import com.meizu.common.a.g;
import com.meizu.common.a.j;

public class StretchSearchView extends RelativeLayout {
    private float A;
    private float B;
    private float C;
    private float D;
    private float E;
    private float F;
    private RelativeLayout G;
    private View H;
    private ImageView I;
    private ImageView J;
    private ImageView K;
    private SearchEditText L;
    private RelativeLayout M;
    private TextView N;
    private int O;
    private String P;
    private String Q;
    private b R;
    private a S;
    private Context a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private boolean j;
    private int k;
    private boolean l;
    private boolean m;
    private boolean n;
    private boolean o;
    private boolean p;
    private boolean q;
    private boolean r;
    private int s;
    private boolean t;
    private boolean u;
    private int v;
    private int w;
    private float x;
    private float y;
    private float z;

    public interface a {
    }

    public interface b {
        void a(View view);

        void a(View view, float f);

        void b(View view);
    }

    public StretchSearchView(Context context) {
        this(context, null);
    }

    public StretchSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, com.meizu.common.a.b.MeizuCommon_StretchSearchViewStyle);
    }

    public StretchSearchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = 0;
        this.g = 0;
        this.h = 0;
        this.i = 0;
        this.j = false;
        this.l = false;
        this.m = false;
        this.n = true;
        this.o = true;
        this.p = true;
        this.q = true;
        this.r = true;
        this.s = 1;
        this.t = false;
        this.u = false;
        this.v = 320;
        this.w = this.v;
        this.x = 0.0f;
        this.y = 1.0f;
        this.z = 0.0f;
        this.A = 1.0f;
        this.B = 0.0f;
        this.C = 1.0f;
        this.D = 1.0f;
        this.E = 0.9f;
        this.F = 0.0f;
        this.O = -1;
        this.P = "搜索";
        this.R = null;
        this.S = null;
        this.k = -1;
        this.a = context;
        TypedArray array = this.a.obtainStyledAttributes(attrs, j.StretchSearchView, defStyle, 0);
        this.s = array.getInteger(j.StretchSearchView_mcStretchTpye, this.s);
        this.m = array.getBoolean(j.StretchSearchView_mcHasVoiceIcon, this.m);
        this.j = array.getBoolean(j.StretchSearchView_mcPlayStretchOnPreDraw, this.j);
        this.u = array.getBoolean(j.StretchSearchView_mcAlignRightWhenAnim, this.u);
        this.t = array.getBoolean(j.StretchSearchView_mcUseSysInterpolater, this.t);
        this.v = array.getInteger(j.StretchSearchView_mcStretchDuration, this.v);
        this.w = array.getInteger(j.StretchSearchView_mcShortenDuration, this.w);
        this.P = array.getString(j.StretchSearchView_mcSearchTextHint);
        this.Q = array.getString(j.StretchSearchView_mcTextViewContent);
        this.F = array.getFloat(j.StretchSearchView_mcSearchLayoutInitAlpha, this.F);
        this.O = array.getColor(j.StretchSearchView_mcTextViewColor, -1);
        this.f = (int) array.getDimension(j.StretchSearchView_mcLayoutMarginLeftAdjust, (float) this.f);
        this.i = (int) array.getDimension(j.StretchSearchView_mcLayoutMarginRightAdjust, (float) this.i);
        this.g = (int) array.getDimension(j.StretchSearchView_mcLayoutPaddingLeft, (float) this.g);
        this.h = (int) array.getDimension(j.StretchSearchView_mcLayoutPaddingRight, (float) this.h);
        this.d = (int) array.getDimension(j.StretchSearchView_mcStretchWidthFrom, 0.0f);
        this.e = (int) array.getDimension(j.StretchSearchView_mcStretchWidthTo, 0.0f);
        this.b = (int) array.getDimension(j.StretchSearchView_mcStretchXfrom, 0.0f);
        this.c = (int) array.getDimension(j.StretchSearchView_mcStretchXto, 0.0f);
        array.recycle();
        a();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    protected void a(Context context) {
        String errMsg = "";
        this.H = null;
        if (this.s == 0) {
            this.H = View.inflate(context, g.mc_stretch_search_layout_ext, this);
            errMsg = "R.layout.mc_move_search_layout";
        } else if (3 == this.s) {
            this.H = View.inflate(context, g.mc_move_search_layout, this);
            errMsg = "R.layout.mc_move_search_layout";
        } else if (2 == this.s) {
            this.H = View.inflate(context, g.mc_stretch_search_layout_ext, this);
            errMsg = "R.layout.mc_stretch_search_layout_ext";
        } else {
            this.H = View.inflate(context, g.mc_stretch_search_layout, this);
            errMsg = "R.layout.mc_stretch_search_layout";
        }
        if (this.H == null) {
            throw new IllegalArgumentException("StretchSearchView cannot inflate " + errMsg + "!");
        }
        this.G = (RelativeLayout) this.H.findViewById(f.mc_stretch_search_layout);
        this.M = (RelativeLayout) this.H.findViewById(f.mc_search_layout);
        this.K = (ImageView) this.H.findViewById(f.mc_voice_icon);
        this.I = (ImageView) this.H.findViewById(f.mc_search_icon);
        this.J = (ImageView) this.H.findViewById(f.mc_search_icon_input_clear);
        this.L = (SearchEditText) this.H.findViewById(f.mc_search_edit);
        this.L.setAlpha(this.F);
        this.L.setHint(this.P);
        if (this.l) {
            this.N = (TextView) this.H.findViewById(f.mc_search_textview);
            this.N.setTextColor(this.O);
            this.N.setText(this.Q);
            this.N.setAlpha(0.0f);
        }
        LayoutParams layoutParams = new LayoutParams(-2, -1);
        layoutParams.rightMargin = this.i;
        this.M.setLayoutParams(layoutParams);
        this.G.setPadding(this.g, this.G.getTop(), this.h, this.G.getBottom());
        this.G.requestLayout();
    }

    protected void a() {
        b();
        a(this.a);
        h();
        c();
    }

    protected void b() {
        boolean z;
        boolean z2 = false;
        if (2 == this.s || 4 == this.s || this.s == 0) {
            z = true;
        } else {
            z = false;
        }
        this.l = z;
        if (true != this.u) {
            z2 = true;
        }
        this.o = z2;
    }

    protected void c() {
        this.I.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ StretchSearchView a;

            {
                this.a = r1;
            }

            public void onClick(View v) {
                this.a.k();
            }
        });
        this.J.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ StretchSearchView a;

            {
                this.a = r1;
            }

            public void onClick(View v) {
                this.a.L.setText("");
            }
        });
        this.L.addTextChangedListener(new TextWatcher(this) {
            final /* synthetic */ StretchSearchView a;

            {
                this.a = r1;
            }

            public void afterTextChanged(Editable s) {
                String txt = this.a.L.getText().toString();
                if (txt == null || txt.isEmpty()) {
                    this.a.J.setVisibility(8);
                    if (this.a.k == 2 && this.a.m) {
                        this.a.K.setVisibility(0);
                    }
                    this.a.a(true);
                    return;
                }
                if (this.a.m) {
                    this.a.K.setVisibility(8);
                }
                this.a.J.setVisibility(0);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        if (4 == this.s || 3 == this.s) {
            this.L.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ StretchSearchView a;

                {
                    this.a = r1;
                }

                public void onClick(View v) {
                    this.a.k();
                }
            });
        }
        l();
    }

    private void l() {
        this.G.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener(this) {
            final /* synthetic */ StretchSearchView a;

            {
                this.a = r1;
            }

            public boolean onPreDraw() {
                this.a.G.getViewTreeObserver().removeOnPreDrawListener(this);
                this.a.d();
                if (this.a.j) {
                    this.a.k();
                }
                return true;
            }
        });
    }

    protected void d() {
        if (this.s != 0) {
            f();
            e();
        }
        if (3 == this.s || 4 == this.s) {
            g();
        }
        Log.i("StretchSearchView", "Stretch width from " + this.d + " to " + this.e + ", move X from " + this.b + " to " + this.c + " !");
    }

    protected void e() {
        this.d = this.M.getMeasuredWidth();
        this.e = getMaxStretchWidth();
    }

    protected void f() {
        this.b = (int) this.M.getX();
        this.c = getMinMoveX();
    }

    protected void g() {
        int tempX = this.b;
        int halfImgTextWidth = (((int) this.L.getPaint().measureText(this.L.getHint().toString())) / 2) + this.I.getMeasuredWidth();
        this.d = (getMaxStretchWidth() / 2) + halfImgTextWidth;
        this.b = (this.G.getMeasuredWidth() / 2) - halfImgTextWidth;
        this.c = getMinMoveX();
        this.M.setX((float) this.b);
        Log.i("StretchSearchView", "Reset stretch layout, search icon location X to layout right:  " + this.d + ",search icon location X: " + this.b + " !");
        ImageView layout_1 = (ImageView) this.G.findViewById(f.mc_stretch_search_layout_1);
        if (layout_1 != null) {
            layout_1.setX((float) (this.c - this.I.getPaddingLeft()));
        }
    }

    protected int getMaxStretchWidth() {
        int swidth = this.G.getMeasuredWidth();
        int layoutPaddingLeft = this.G.getPaddingLeft();
        int layoutPaddingRight = this.G.getPaddingRight();
        if (this.l) {
            return (swidth - this.N.getLayoutParams().width) - layoutPaddingLeft;
        }
        return (swidth - layoutPaddingLeft) - layoutPaddingRight;
    }

    protected int getMinMoveX() {
        return (((int) this.G.getX()) + this.G.getPaddingLeft()) + this.f;
    }

    protected void h() {
        if (3 == this.s || 4 == this.s) {
            this.H.setVisibility(0);
            this.L.setVisibility(0);
            this.L.setBackground(null);
            this.z = 0.8f;
        } else {
            this.H.setVisibility(0);
            this.L.setVisibility(8);
        }
        this.k = 0;
    }

    protected void i() {
        this.H.requestLayout();
        this.H.setVisibility(0);
        this.L.setVisibility(0);
        this.L.setText("");
        if (this.l) {
            this.N.setVisibility(0);
            this.N.setAlpha(0.0f);
        }
    }

    protected void j() {
        this.H.requestLayout();
        this.L.a(true);
        if (this.m) {
            this.K.setVisibility(0);
        }
    }

    public void k() {
        m();
    }

    private void m() {
        if (this.k == 0 || this.k == 4) {
            this.k = 1;
            i();
            AnimatorSet aniSet = new AnimatorSet();
            aniSet.setDuration((long) this.v);
            ObjectAnimator layoutX = ObjectAnimator.ofFloat(this.M, "x", new float[]{(float) this.c});
            layoutX.setDuration((long) this.v);
            ObjectAnimator layoutWidth = ObjectAnimator.ofFloat(this.M, "width", new float[]{(float) this.e});
            layoutWidth.setDuration((long) this.v);
            layoutWidth.addUpdateListener(new AnimatorUpdateListener(this) {
                final /* synthetic */ StretchSearchView a;

                {
                    this.a = r1;
                }

                public void onAnimationUpdate(ValueAnimator animation) {
                    this.a.M.setLayoutParams(new LayoutParams((int) ((Float) animation.getAnimatedValue()).floatValue(), this.a.M.getHeight()));
                }
            });
            ObjectAnimator custAnim = ObjectAnimator.ofFloat(this.M, "StretchSearchViewAnimValue", new float[]{this.B, this.C});
            custAnim.setDuration((long) this.v);
            custAnim.addUpdateListener(new AnimatorUpdateListener(this) {
                final /* synthetic */ StretchSearchView a;

                {
                    this.a = r1;
                }

                public void onAnimationUpdate(ValueAnimator animation) {
                    float cal = ((Float) animation.getAnimatedValue()).floatValue();
                    if (this.a.R != null) {
                        this.a.R.a(this.a.H, cal);
                    }
                }
            });
            aniSet.addListener(new AnimatorListener(this) {
                final /* synthetic */ StretchSearchView a;

                {
                    this.a = r1;
                }

                public void onAnimationStart(Animator animation) {
                    if (this.a.R != null) {
                        this.a.R.a(this.a.H);
                    }
                }

                public void onAnimationEnd(Animator animation) {
                    this.a.j();
                    this.a.k = 2;
                    if (this.a.R != null) {
                        this.a.R.b(this.a.H);
                    }
                }

                public void onAnimationCancel(Animator animation) {
                }

                public void onAnimationRepeat(Animator animation) {
                }
            });
            ObjectAnimator imgClearAlpha = ObjectAnimator.ofFloat(this.J, "alpha", new float[]{this.x, this.y});
            imgClearAlpha.setDuration((long) this.v);
            ObjectAnimator editAlpha = ObjectAnimator.ofFloat(this.L, "alpha", new float[]{this.z, this.A});
            editAlpha.setDuration((long) this.v);
            ObjectAnimator imgScaleX = ObjectAnimator.ofFloat(this.I, "scaleX", new float[]{this.D, this.E});
            ObjectAnimator imgScaleY = ObjectAnimator.ofFloat(this.I, "scaleY", new float[]{this.D, this.E});
            imgScaleX.setDuration((long) this.v);
            imgScaleY.setDuration((long) this.v);
            if (this.t) {
                layoutX.setInterpolator(getMovingInterpolater());
                layoutWidth.setInterpolator(getStretchInterpolater());
                imgScaleX.setInterpolator(getScaleInterpolater());
                imgScaleY.setInterpolator(getScaleInterpolater());
            }
            aniSet.play(custAnim);
            if (this.o) {
                aniSet.play(custAnim).with(layoutX);
            }
            if (this.q) {
                aniSet.play(custAnim).with(imgClearAlpha);
            }
            if (this.r) {
                aniSet.play(custAnim).with(editAlpha);
            }
            if (this.n) {
                aniSet.play(custAnim).with(layoutWidth);
            }
            if (this.p) {
                aniSet.play(custAnim).with(imgScaleX).with(imgScaleY);
            }
            if (this.l) {
                ObjectAnimator btnAlpha = ObjectAnimator.ofFloat(this.N, "alpha", new float[]{0.0f, 1.0f});
                btnAlpha.setDuration((long) ((this.v * 2) / 3));
                AnimatorSet aniSetBtn = new AnimatorSet();
                aniSetBtn.setDuration((long) ((this.v * 2) / 3));
                aniSetBtn.play(btnAlpha).after((long) (this.v / 3));
                aniSetBtn.start();
            }
            aniSet.start();
        }
    }

    public void a(boolean isShow) {
        this.L.a(isShow);
    }

    public void setAutoPlayStretchOnPreDraw(boolean preDraw) {
        this.j = preDraw;
    }

    public void setHaveVoiceIcon(boolean hasVoiceIcon) {
        this.m = hasVoiceIcon;
    }

    public void setPlayStretchWidthAnim(boolean mPlayStretchWidthAnim) {
        this.n = mPlayStretchWidthAnim;
    }

    public void setPlayMoveXAnim(boolean mPlayMoveXAnim) {
        this.o = mPlayMoveXAnim;
    }

    public void setPlaySearchImgScaleAnim(boolean mPlaySearchImgScaleAnim) {
        this.p = mPlaySearchImgScaleAnim;
    }

    public void setPlaySearchclearAlphaAnim(boolean mPlaySearchclearAlphaAnim) {
        this.q = mPlaySearchclearAlphaAnim;
    }

    public void setPlayInputTextAlhpaAnim(boolean mPlayInputTextAlhpaAnim) {
        this.r = mPlayInputTextAlhpaAnim;
    }

    public void setCustomAnimValueFrom(float from) {
        this.B = from;
    }

    public float getCustomAnimValueFrom() {
        return this.B;
    }

    public void setCustomAnimValueTo(float to) {
        this.C = to;
    }

    public float getCustomAnimValueTo() {
        return this.y;
    }

    public void setInputTextAlphaFrom(float from) {
        this.z = from;
    }

    public float getInputTextAlphaFrom() {
        return this.z;
    }

    public void setInputTextAlphaTo(float to) {
        this.A = to;
    }

    public float getInputTextAlphaTo() {
        return this.y;
    }

    public void setInputClearAlphaFrom(float from) {
        this.z = from;
    }

    public float getInputClearAlphaFrom() {
        return this.z;
    }

    public void setInputClearAlphaTo(float to) {
        this.A = to;
    }

    public int getInputClearAlphaTo() {
        return this.e;
    }

    public void setVoiceIconListener(OnClickListener listener) {
        if (this.m) {
            this.K.setOnClickListener(listener);
        }
    }

    public void setBtnListener(OnClickListener listener) {
        if (this.N != null) {
            this.N.setOnClickListener(listener);
        }
    }

    public void setEditTextListener(OnClickListener listener) {
        this.L.setOnClickListener(listener);
    }

    public int getStretchWidthFrom() {
        return this.d;
    }

    public void setStretchWidthFrom(int fromWidth) {
        this.d = fromWidth;
    }

    public int getStretchWidthTo() {
        return this.e;
    }

    public void setStretchWidthTo(int toWidth) {
        this.e = toWidth;
    }

    public int getStretchXfrom() {
        return this.b;
    }

    public void setStretchXfrom(int fromX) {
        this.b = fromX;
    }

    public int getStretchXto() {
        return this.c;
    }

    public void setStretchXto(int toX) {
        this.c = toX;
    }

    public float getScaleFrom() {
        return this.D;
    }

    public void setScaleFrom(float scaleFrom) {
        this.D = scaleFrom;
    }

    public float getScaleTo() {
        return this.E;
    }

    public void setScaleTo(float scaleTo) {
        this.E = scaleTo;
    }

    public int getLayoutMarginLeftAdjust() {
        return this.f;
    }

    public void setLayoutMarginLeftAdjust(int adjustSize) {
        this.f = adjustSize;
    }

    public int getLayoutMarginRightAdjust() {
        return this.i;
    }

    public void setLayoutMarginRightAdjust(int adjustSize) {
        this.i = adjustSize;
    }

    public String getSearchText() {
        return this.L.getText().toString();
    }

    public void setSearchText(String str) {
        this.L.setText(str);
    }

    public String getBtnText() {
        if (this.l) {
            return this.N.getText().toString();
        }
        return null;
    }

    public void setBtnText(String text) {
        if (this.l) {
            this.N.setText(text);
        }
    }

    public boolean getUseInterpolater() {
        return this.t;
    }

    public void setUseInterpolater(boolean isOn) {
        this.t = isOn;
    }

    public boolean getIsAlignRight() {
        return this.u;
    }

    public void setIsAlignRigh(boolean isAlignRigh) {
        this.u = isAlignRigh;
    }

    private Interpolator getMovingInterpolater() {
        Interpolator polator = new DecelerateInterpolator();
        if (VERSION.SDK_INT >= 21) {
            return new PathInterpolator(0.3333f, 0.0f, 0.1f, 1.0f);
        }
        return polator;
    }

    private Interpolator getScaleInterpolater() {
        Interpolator polator = new DecelerateInterpolator();
        if (VERSION.SDK_INT >= 21) {
            return new PathInterpolator(0.3333f, 0.0f, 0.0f, 1.0f);
        }
        return polator;
    }

    private Interpolator getStretchInterpolater() {
        Interpolator polator = new DecelerateInterpolator();
        if (VERSION.SDK_INT >= 21) {
            return new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        }
        return polator;
    }

    public void setStretchAnimDuration(int duration) {
        this.v = duration;
    }

    public int getStretchAnimDuration() {
        return this.v;
    }

    public void setShortenAnimDuration(int duration) {
        this.w = duration;
    }

    public int getShortenAnimDuration() {
        return this.w;
    }

    public int getAnimationState() {
        return this.k;
    }

    public void setOnStretchAnimationListener(b listener) {
        this.R = listener;
    }

    public void setOnShortenAnimationListener(a listener) {
        this.S = listener;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(StretchSearchView.class.getName());
    }
}

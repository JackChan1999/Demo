package com.meizu.cloud.detail.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.FrameLayout;

import java.util.Date;

public class PullToZoomGroup extends FrameLayout {
    private Date mDate;
    private long B;
    private b C;
    private ValueAnimator mAnimator;
    private PathInterpolator mInterpolator;
    private boolean F;
    private boolean G = true;
    private AnimatorListenerAdapter mAdapter;
    private int a = 73;
    private int b = 17;
    private int c = 186;
    private int d = 255;
    private int e = 338;
    private int f = 200;
    private int g = ((this.a + this.e) + this.b);
    private int h = 640;
    private int i;
    private PullToZoomListView j;
    private LayoutParams k;
    private FrameLayout l;
    private LayoutParams m;
    private FrameLayout n;
    private LayoutParams o;
    private GalleryFlow p;
    private a q;
    private LayoutParams r;
    private int s;
    private int t;
    private float u;
    private float v;
    private float w;
    private float x;
    private int y;
    private Date z;

    public PullToZoomGroup(Context context) {
        super(context);
        a(context);
    }

    public PullToZoomGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    public PullToZoomGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a(context);
    }

    public void a(Context context) {
        this.c = (int) TypedValue.applyDimension(1, (float) this.c, getContext().getResources().getDisplayMetrics());
        this.d = (int) TypedValue.applyDimension(1, (float) this.d, getContext().getResources().getDisplayMetrics());
        this.e = (int) TypedValue.applyDimension(1, (float) this.e, getContext().getResources().getDisplayMetrics());
        this.f = (int) TypedValue.applyDimension(1, (float) this.f, getContext().getResources().getDisplayMetrics());
        this.g = (int) TypedValue.applyDimension(1, (float) this.g, getContext().getResources().getDisplayMetrics());
        this.h = (int) TypedValue.applyDimension(1, (float) this.h, getContext().getResources().getDisplayMetrics());
        this.a = (int) TypedValue.applyDimension(1, (float) this.a, getContext().getResources().getDisplayMetrics());
        this.b = (int) TypedValue.applyDimension(1, (float) this.b, getContext().getResources().getDisplayMetrics());
        this.y = getResources().getDimensionPixelOffset(d.app_info_bottom_height);
        this.j = new PullToZoomListView(context);
        this.k = new LayoutParams(-1, -1);
        this.k.setMargins(0, this.a, 0, 0);
        this.j.setLayoutParams(this.k);
        this.j.setTranslationY((float) (this.g - this.a));
        this.j.setTag("pullgroup_listview");
        this.j.setDivider(null);
        this.l = new FrameLayout(context);
        this.m = new LayoutParams(-1, this.g);
        this.l.setLayoutParams(this.m);
        this.o = new LayoutParams(-1, -1);
        this.n = new FrameLayout(context);
        this.n.setLayoutParams(this.o);
        this.l.addView(this.n);
        this.p = new GalleryFlow(context);
        this.r = new LayoutParams(-1, -1);
        this.r.setMargins(0, this.a, 0, this.b);
        this.p.setLayoutParams(this.r);
        this.l.addView(this.p);
        this.p.setOnItemSelectedListener(new OnItemSelectedListener(this) {
            final /* synthetic */ PullToZoomGroup a;

            {
                this.a = r1;
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (this.a.p.getChildCount() > 0) {
                    if (this.a.q == null) {
                        this.a.q = (a) this.a.p.getAdapter();
                    }
                    this.a.q.a(position, this.a.p.getHeight());
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.p.setSpacing(0);
        this.p.setTag("pullgroup_tag");
        addView(this.l);
        addView(this.j);
        this.mInterpolator = new PathInterpolator(0.1f, 0.7f, 0.1f, 1.0f);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0) {
            this.z = new Date(System.currentTimeMillis());
            this.x = this.j.getTranslationY();
            this.u = ev.getY();
            if (this.x == 0.0f && this.j.getChildCount() > 0 && this.j.getChildAt(0).getTop() < 0) {
                setGroupState(-1);
            } else if ((ev.getY() >= ((float) this.l.getHeight()) || this.l.getHeight() != this.g) && this.l.getHeight() != this.h) {
                setGroupState(0);
            } else {
                setGroupState(2);
            }
        }
        if (ev.getAction() == 2 && this.j.getTranslationY() > 0.0f && Math.abs(ev.getY() - this.u) > 1.0f && this.j.getCount() > 0 && this.i != 2) {
            if (this.j.getChildAt(0) != null) {
                this.j.getChildAt(0).setTop(0);
            }
            setGroupState(1);
        }
        if (this.i == 1) {
            requestDisallowInterceptTouchEvent(false);
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.i == 1) {
            return true;
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.G || this.F) {
            return true;
        }
        this.v = event.getY();
        this.w = (this.v - this.u) / 1.3f;
        switch (this.i) {
            case -1:
                return false;
            default:
                switch (event.getAction()) {
                    case 0:
                        return true;
                    case 1:
                    case 3:
                        if (this.j.getTranslationY() == 0.0f) {
                            this.m.height = this.a;
                            this.l.setLayoutParams(this.m);
                            return false;
                        }
                        this.mDate = new Date(System.currentTimeMillis());
                        this.B = this.mDate.getTime() - this.z.getTime();
                        if (this.B < 100 && this.w > 80.0f && this.x + ((float) this.j.getTop()) != ((float) this.g) && this.x + ((float) this.j.getTop()) != ((float) this.h)) {
                            a((float) this.l.getHeight(), (float) this.g);
                            return false;
                        } else if (this.B >= 130 || this.w >= -80.0f || this.x + ((float) this.j.getTop()) == ((float) this.a)) {
                            if (this.B >= 200 || this.w <= 40.0f) {
                                if (this.B < 200 && this.w < -40.0f) {
                                    if (this.x + ((float) this.j.getTop()) <= ((float) this.h) && this.x + ((float) this.j.getTop()) > ((float) this.g)) {
                                        a((float) this.l.getHeight(), (float) this.g);
                                        return false;
                                    } else if (this.x + ((float) this.j.getTop()) <= ((float) this.g) && this.x + ((float) this.j.getTop()) > ((float) this.f)) {
                                        a((float) this.l.getHeight(), (float) this.a);
                                        return false;
                                    } else if (this.x + ((float) this.j.getTop()) <= ((float) this.f)) {
                                        a((float) this.l.getHeight(), (float) this.a);
                                        return false;
                                    }
                                }
                            } else if (this.x + ((float) this.j.getTop()) >= ((float) this.a) && this.x + ((float) this.j.getTop()) < ((float) this.f)) {
                                a((float) this.l.getHeight(), (float) this.g);
                                return false;
                            } else if (this.x + ((float) this.j.getTop()) >= ((float) this.f) && this.x + ((float) this.j.getTop()) < ((float) this.g)) {
                                a((float) this.l.getHeight(), (float) this.g);
                                return false;
                            } else if (this.x + ((float) this.j.getTop()) >= ((float) this.g)) {
                                a((float) this.l.getHeight(), (float) this.h);
                                return false;
                            }
                            if (this.l.getHeight() >= (this.h + this.g) / 2) {
                                a((float) this.l.getHeight(), (float) this.h);
                                return true;
                            } else if (this.l.getHeight() < (this.h + this.g) / 2 && this.l.getHeight() >= this.g) {
                                a((float) this.l.getHeight(), (float) this.g);
                                return true;
                            } else if (this.w > 200.0f) {
                                a((float) this.l.getHeight(), (float) this.g);
                                return true;
                            } else if (this.w < -200.0f) {
                                a((float) this.l.getHeight(), (float) this.a);
                                return true;
                            } else if (this.w <= 200.0f && this.w > 0.0f) {
                                a((float) this.l.getHeight(), (float) this.a);
                                return true;
                            } else if (this.w < -200.0f || this.w >= 0.0f) {
                                return this.w == 0.0f ? true : true;
                            } else {
                                a((float) this.l.getHeight(), (float) this.g);
                                return true;
                            }
                        } else {
                            a((float) this.l.getHeight(), (float) this.a);
                            return false;
                        }
                    case 2:
                        if (this.x + this.w <= 0.0f && this.i != 2) {
                            this.j.setTranslationY(0.0f);
                            return true;
                        } else if (this.i == 2) {
                            return true;
                        } else {
                            setGroupState(1);
                            if (this.l.getHeight() < this.h || this.w <= 0.0f) {
                                this.j.setTranslationY(this.x + this.w);
                                this.m.height = (int) ((((float) this.j.getTop()) + this.x) + this.w);
                                this.l.setLayoutParams(this.m);
                                b((((float) this.j.getTop()) + this.x) + this.w);
                                return true;
                            }
                            this.u = event.getY();
                            this.w = 0.0f;
                            this.x = this.j.getTranslationY();
                            return true;
                        }
                    default:
                        return true;
                }
        }
    }

    private void a(float start, float to) {
        long j = 80;
        if (!this.F) {
            this.F = true;
            this.mAnimator = ValueAnimator.ofFloat(new float[]{start, to});
            this.mAnimator.addUpdateListener(new AnimatorUpdateListener(this) {
                final /* synthetic */ PullToZoomGroup a;

                {
                    this.a = r1;
                }

                public void onAnimationUpdate(ValueAnimator animation) {
                    float val = ((Float) animation.getAnimatedValue()).floatValue();
                    this.a.j.setTranslationY(val - ((float) this.a.j.getTop()));
                    this.a.a(val);
                    this.a.b(val);
                }
            });
            this.mAnimator.addListener(new AnimatorListenerAdapter(this) {
                final /* synthetic */ PullToZoomGroup a;

                {
                    this.a = r1;
                }

                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (this.a.mAdapter != null) {
                        this.a.mAdapter.onAnimationEnd(animation);
                    }
                    this.a.F = false;
                }
            });
            this.mAnimator.setInterpolator(this.mInterpolator);
            long duration;
            ValueAnimator valueAnimator;
            if ((start <= ((float) this.g) && to == ((float) this.a)) || (start >= ((float) this.a) && to == ((float) this.g))) {
                duration = (long) (Math.abs(start - to) * (350.0f / Math.abs(start - to)));
                valueAnimator = this.mAnimator;
                if (duration > 80) {
                    j = duration;
                }
                valueAnimator.setDuration(j);
            } else if ((start <= ((float) this.g) || start > ((float) this.h) || to != ((float) this.a)) && (start >= ((float) this.g) || start < ((float) this.a) || to != ((float) this.h))) {
                duration = (long) (Math.abs(start - to) * (200.0f / Math.abs(start - to)));
                valueAnimator = this.mAnimator;
                if (duration > 80) {
                    j = duration;
                }
                valueAnimator.setDuration(j);
            } else {
                duration = (long) (Math.abs(start - to) * (400.0f / Math.abs(start - to)));
                valueAnimator = this.mAnimator;
                if (duration > 80) {
                    j = duration;
                }
                valueAnimator.setDuration(j);
            }
            this.mAnimator.start();
        }
    }

    private void b(float start, float to) {
        if (!this.F) {
            this.F = true;
            this.mAnimator = ValueAnimator.ofFloat(new float[]{start, to});
            this.mAnimator.addUpdateListener(new AnimatorUpdateListener(this) {
                final /* synthetic */ PullToZoomGroup a;

                {
                    this.a = r1;
                }

                public void onAnimationUpdate(ValueAnimator animation) {
                    float val = ((Float) animation.getAnimatedValue()).floatValue();
                    this.a.j.setTranslationY(val - ((float) this.a.j.getTop()));
                    this.a.a(val);
                    this.a.b(val);
                }
            });
            this.mAnimator.addListener(new AnimatorListenerAdapter(this) {
                final /* synthetic */ PullToZoomGroup a;

                {
                    this.a = r1;
                }

                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    this.a.F = false;
                }
            });
            this.mAnimator.setInterpolator(this.mInterpolator);
            this.mAnimator.setDuration(300);
            this.mAnimator.start();
        }
    }

    private void a(float val) {
        this.m.height = (int) val;
        this.l.setLayoutParams(this.m);
    }

    private void b(float galleryFrameLayoutHeight) {
        if (galleryFrameLayoutHeight > ((float) this.g)) {
            this.r.height = -1;
            this.s = (int) (((float) this.a) - ((((float) this.a) * (galleryFrameLayoutHeight - ((float) this.g))) / ((float) (this.h - this.g))));
            this.t = (int) (((float) this.b) - ((((float) this.b) * (galleryFrameLayoutHeight - ((float) this.g))) / ((float) (this.h - this.g))));
            this.r.setMargins(0, this.s, 0, this.t);
            if (galleryFrameLayoutHeight - ((float) this.g) >= ((float) this.y)) {
                setPadding(0, 0, 0, 0);
            } else {
                setPadding(0, 0, 0, (int) ((((float) this.y) - galleryFrameLayoutHeight) + ((float) this.g)));
            }
            this.o.height = -1;
            this.n.setLayoutParams(this.o);
        } else if (galleryFrameLayoutHeight <= ((float) this.g) && galleryFrameLayoutHeight >= ((float) this.f)) {
            this.r.setMargins(0, this.a, 0, this.b);
            this.r.height = (int) (((float) this.e) - (((((float) this.g) - galleryFrameLayoutHeight) / ((float) (this.g - this.f))) * ((float) (this.e - this.d))));
            setPadding(0, 0, 0, this.y);
            this.o.height = this.g;
            this.n.setLayoutParams(this.o);
        } else if (galleryFrameLayoutHeight < ((float) this.f)) {
            this.r.setMargins(0, this.a, 0, this.b);
            this.r.height = (int) (((float) this.d) - (((((float) this.f) - galleryFrameLayoutHeight) / ((float) this.f)) * ((float) (this.d - this.c))));
            setPadding(0, 0, 0, this.y);
            this.o.height = this.g;
            this.n.setLayoutParams(this.o);
        }
        if (this.p.getChildCount() > 0) {
            if (this.q == null) {
                this.q = (a) this.p.getAdapter();
            }
            this.q.b(this.r.height);
        }
        this.p.setLayoutParams(this.r);
        if (this.C != null) {
            if (((float) this.j.getTop()) + this.j.getTranslationY() > ((float) this.g) && ((float) this.j.getTop()) + this.j.getTranslationY() < ((float) (this.h - this.y))) {
                this.C.a(1.0f - (((((float) this.j.getTop()) + this.j.getTranslationY()) - ((float) this.g)) / ((float) ((this.h - this.g) - this.y))));
            } else if (((float) this.j.getTop()) + this.j.getTranslationY() <= ((float) this.g)) {
                this.C.a(1.0f);
            } else if (((float) this.j.getTop()) + this.j.getTranslationY() >= ((float) this.h)) {
                this.C.a(0.0f);
            }
        }
    }

    public void a() {
        if (this.l.getHeight() == this.g) {
            b((float) this.g, (float) this.h);
        } else if (this.l.getHeight() == this.h) {
            b((float) this.h, (float) this.g);
        }
    }

    public void setAnimatorListenerAdapter(AnimatorListenerAdapter listenerAdapter) {
        this.mAdapter = listenerAdapter;
    }

    public PullToZoomListView getListView() {
        return this.j;
    }

    public FrameLayout getGalleryLayout() {
        return this.n;
    }

    public GalleryFlow getGalleryFlow() {
        return this.p;
    }

    public void setPullGroupListener(b listener) {
        this.C = listener;
    }

    public void setGroupState(int mGroupState) {
        this.i = mGroupState;
    }

    public int getGroupState() {
        return this.i;
    }

    public int getSTATUS_GALLERY0() {
        return this.c;
    }

    public int getSTATUS_GALLERY1() {
        return this.d;
    }

    public int getSTATUS_GALLERY2() {
        return this.e;
    }

    public int getSTATUS3() {
        return this.h;
    }

    public void setCanScroll(boolean canScroll) {
        this.G = canScroll;
    }

    public int getViewState() {
        if (this.l.getHeight() == 0) {
            return 0;
        }
        if (this.l.getHeight() <= this.f) {
            return 1;
        }
        if (this.l.getHeight() <= this.g) {
            return 2;
        }
        if (this.l.getHeight() <= this.h) {
            return 3;
        }
        return 4;
    }
}

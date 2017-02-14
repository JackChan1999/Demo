package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.CheckBox;

public class AnimCheckBox extends CheckBox {
    int a;
    private a b;
    private b c;
    private boolean d;

    private static class a {
        private boolean a = false;
        private ObjectAnimator b;
        private ObjectAnimator c;
        private ValueAnimator d;
        private AnimatorSet e;
        private boolean f;
        private boolean g;
        private boolean h = true;
        private boolean i = false;
        private TimeInterpolator j;
        private TimeInterpolator k;
        private TimeInterpolator l;
        private TimeInterpolator m;
        private AnimCheckBox n;

        public a(AnimCheckBox checkBox) {
            this.n = checkBox;
            a();
            this.i = true;
        }

        private void a() {
            if (VERSION.SDK_INT >= 21) {
                this.j = new PathInterpolator(0.33f, 0.0f, 1.0f, 1.0f);
                this.k = new PathInterpolator(0.0f, 0.0f, 0.01f, 1.0f);
                this.l = new PathInterpolator(0.4f, 0.0f, 0.01f, 1.0f);
                this.m = new PathInterpolator(0.0f, 0.0f, 0.1f, 1.0f);
            } else {
                TimeInterpolator decelerateInterpolator = new DecelerateInterpolator();
                this.m = decelerateInterpolator;
                this.l = decelerateInterpolator;
                this.k = decelerateInterpolator;
                this.j = decelerateInterpolator;
            }
            PropertyValuesHolder scaleYPVH = PropertyValuesHolder.ofFloat("scaleY", new float[]{1.0f, 0.0f});
            this.b = ObjectAnimator.ofPropertyValuesHolder(this.n, new PropertyValuesHolder[]{scaleYPVH});
            this.b.setInterpolator(this.j);
            this.b.addListener(new AnimatorListenerAdapter(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void onAnimationEnd(Animator animation) {
                    this.a.n.a(this.a.f);
                    this.a.n.b(this.a.g);
                    if (this.a.n.a != 0) {
                        if (this.a.f) {
                            this.a.n.setVisibility(0);
                        } else {
                            this.a.n.setVisibility(this.a.n.a);
                        }
                    }
                    this.a.c.start();
                }
            });
            PropertyValuesHolder scaleYPVH2 = PropertyValuesHolder.ofFloat("scaleY", new float[]{0.0f, 1.0f});
            this.c = ObjectAnimator.ofPropertyValuesHolder(this.n, new PropertyValuesHolder[]{scaleYPVH2});
            this.c.setInterpolator(this.k);
            this.d = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            this.d.setInterpolator(this.l);
            this.d.addUpdateListener(new AnimatorUpdateListener(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void onAnimationUpdate(ValueAnimator animation) {
                    if (this.a.n.c != null) {
                        float ff = ((Float) animation.getAnimatedValue()).floatValue();
                        if (!this.a.f) {
                            ff = 1.0f - ff;
                        }
                        this.a.n.c.a(ff);
                    }
                }
            });
            this.d.addListener(new AnimatorListenerAdapter(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void onAnimationEnd(Animator animation) {
                    if (this.a.c.isRunning()) {
                        this.a.c.end();
                    }
                }
            });
            this.e = new AnimatorSet();
            this.e.playTogether(new Animator[]{this.b, this.d});
        }

        public void a(boolean checked) {
            if (this.i && this.h) {
                if (this.a) {
                    Log.i("xx", "setChecked checked = " + checked + " targetChecekedState = " + this.f + " " + " " + this.e.isRunning() + " " + this.c.isRunning());
                }
                if (checked != this.f) {
                    this.f = checked;
                    if (checked) {
                        if (this.e.isRunning() || this.c.isRunning()) {
                            this.e.end();
                            this.c.end();
                            this.f = false;
                            a(checked);
                            return;
                        }
                        this.b.setDuration(150);
                        this.c.setDuration(230);
                        this.d.setDuration(380);
                        this.e.start();
                        return;
                    } else if (this.e.isRunning() || this.c.isRunning()) {
                        this.n.a(checked);
                        this.e.end();
                        this.c.end();
                        return;
                    } else {
                        this.b.setDuration(0);
                        this.c.setDuration(476);
                        this.d.setDuration(476);
                        this.e.start();
                        return;
                    }
                }
                return;
            }
            this.n.a(checked);
            this.f = checked;
        }

        public void b(boolean activated) {
            this.g = activated;
            if (this.i && this.h) {
                if (this.a) {
                    Log.i("xx", "setActivated activated = " + activated + " " + this.n.isActivated() + " " + this.g + " targetChecekedState = " + this.f + " " + this.n.isChecked() + " " + this.e.isRunning() + " " + this.c.isRunning());
                }
                if (activated == this.n.isActivated()) {
                    return;
                }
                if (!activated && !this.f && this.n.isChecked()) {
                    return;
                }
                if (this.n.isChecked() && this.f) {
                    this.n.b(activated);
                    if (!this.e.isRunning() && !this.c.isRunning()) {
                        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, new float[]{0.0f, 1.0f});
                        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, new float[]{0.0f, 1.0f});
                        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this.n, new PropertyValuesHolder[]{scaleX, scaleY});
                        animator.setDuration(40).setInterpolator(this.m);
                        animator.start();
                        return;
                    }
                    return;
                } else if (!activated) {
                    this.e.end();
                    this.c.end();
                    this.n.b(activated);
                    return;
                } else {
                    return;
                }
            }
            this.n.b(activated);
        }

        public void c(boolean isAnimation) {
            this.h = isAnimation;
        }
    }

    public interface b {
        void a(float f);
    }

    public AnimCheckBox(Context context) {
        this(context, null);
    }

    public AnimCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.a = getVisibility();
        setIsAnimation(true);
    }

    public AnimCheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.a = getVisibility();
        setIsAnimation(true);
    }

    public void setInitVisible(int visible) {
        if (visible == 0 || visible == 4 || visible == 8) {
            this.a = visible;
        }
    }

    public void setChecked(boolean checked) {
        if (this.b == null) {
            super.setChecked(checked);
        } else {
            this.b.a(checked);
        }
    }

    public void setActivated(boolean activated) {
        if (this.d != activated) {
            this.d = activated;
            sendAccessibilityEvent(32768);
        }
        if (this.b == null) {
            super.setActivated(activated);
        } else {
            this.b.b(activated);
        }
    }

    public void setIsAnimation(boolean isAnimation) {
        if (this.b == null) {
            this.b = new a(this);
        }
        this.b.c(isAnimation);
    }

    public void a(boolean checked) {
        super.setChecked(checked);
    }

    public void b(boolean activated) {
        super.setActivated(activated);
    }

    public void setUpdateListner(b listener) {
        this.c = listener;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setChecked(this.d);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(AnimCheckBox.class.getName());
        info.setChecked(this.d);
    }
}

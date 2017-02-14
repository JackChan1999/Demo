package com.meizu.cloud.app.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class BackTopView extends FrameLayout {
    float a;
    FrameLayout b;
    float c;
    float d;
    float e;
    float f;
    ImageView g;
    float h;
    float i;
    ImageView j;
    float k;
    ImageView l;
    float m;
    ImageView n;
    float o;
    ImageView p;
    a q;
    private b r;

    public interface b {
        void a(View view);
    }

    public enum a {
        OPEN,
        COMMON
    }

    public BackTopView(Context context) {
        this(context, null);
        LayoutInflater.from(context).inflate(g.game_bottom_backtop, this, true);
        this.b = (FrameLayout) findViewById(f.frame);
        this.g = (ImageView) findViewById(f.topPic);
        this.p = (ImageView) findViewById(f.bottompic);
        this.l = (ImageView) findViewById(f.leftcloud);
        this.j = (ImageView) findViewById(f.leftstar);
        this.n = (ImageView) findViewById(f.rightcloud);
        this.g.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ BackTopView a;

            {
                this.a = r1;
            }

            public void onClick(View v) {
                if (this.a.r != null) {
                    this.a.r.a(v);
                }
            }
        });
    }

    public BackTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.f = 500.0f;
        this.q = a.COMMON;
    }

    public BackTopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.f = 500.0f;
        this.q = a.COMMON;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.c == 0.0f) {
            this.c = (float) this.b.getMeasuredHeight();
            this.d = (float) this.b.getMeasuredWidth();
            this.h = (float) ((LayoutParams) this.g.getLayoutParams()).bottomMargin;
        }
    }

    public void setOnTopPicListener(b listener) {
        this.r = listener;
    }

    public void setFrameAnimHeigth(float distance) {
        this.a = 0.0f;
        if (distance < 0.0f) {
            this.q = a.COMMON;
            this.e = this.c;
            LayoutParams layoutParams = (LayoutParams) this.b.getLayoutParams();
            layoutParams.height = (int) this.e;
            this.b.setLayoutParams(layoutParams);
            return;
        }
        this.q = a.OPEN;
        float compentDistance = ((float) Math.pow((double) distance, 0.5d)) * 3.0f;
        this.e = this.c + compentDistance < this.f ? this.c + compentDistance : this.f;
        layoutParams = (LayoutParams) this.b.getLayoutParams();
        layoutParams.height = (int) this.e;
        this.b.setLayoutParams(layoutParams);
        if (this.c + compentDistance >= this.f) {
            compentDistance = this.f;
        }
        this.a = compentDistance;
        LayoutParams layoutParams1 = (LayoutParams) this.g.getLayoutParams();
        layoutParams1.bottomMargin = (int) (-this.a);
        this.i = (float) ((int) (-this.a));
        this.g.setLayoutParams(layoutParams1);
        LayoutParams layoutParams2 = (LayoutParams) this.j.getLayoutParams();
        layoutParams2.leftMargin = (int) (-this.a);
        this.k = (float) ((int) (-this.a));
        this.j.setLayoutParams(layoutParams2);
        LayoutParams layoutParams3 = (LayoutParams) this.l.getLayoutParams();
        layoutParams3.leftMargin = (int) (-this.a);
        this.m = (float) ((int) (-this.a));
        this.l.setLayoutParams(layoutParams3);
        LayoutParams layoutParams4 = (LayoutParams) this.n.getLayoutParams();
        layoutParams4.rightMargin = (int) (-this.a);
        this.o = (float) ((int) (-this.a));
        this.n.setLayoutParams(layoutParams4);
    }

    public void setFrameAnimOrigin() {
        this.q = a.COMMON;
        final ValueAnimator animator1 = ValueAnimator.ofFloat(new float[]{this.e, this.c});
        animator1.setDuration(100);
        animator1.setInterpolator(new AccelerateInterpolator());
        animator1.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ BackTopView b;

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.b.b.setLayoutParams(new LayoutParams((int) this.b.d, (int) ((Float) animator1.getAnimatedValue()).floatValue()));
            }
        });
        final ValueAnimator animator2 = ValueAnimator.ofFloat(new float[]{this.i, this.h});
        animator2.setDuration(100);
        animator2.setInterpolator(new AccelerateInterpolator());
        animator2.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ BackTopView b;

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LayoutParams layoutParams1 = (LayoutParams) this.b.g.getLayoutParams();
                layoutParams1.bottomMargin = (int) ((Float) animator2.getAnimatedValue()).floatValue();
                this.b.g.setLayoutParams(layoutParams1);
            }
        });
        final ValueAnimator animator3 = ValueAnimator.ofFloat(new float[]{this.k, 0.0f});
        animator3.setDuration(100);
        animator3.setInterpolator(new AccelerateInterpolator());
        animator3.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ BackTopView b;

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LayoutParams layoutParams2 = (LayoutParams) this.b.j.getLayoutParams();
                layoutParams2.leftMargin = (int) ((Float) animator3.getAnimatedValue()).floatValue();
                this.b.j.setLayoutParams(layoutParams2);
            }
        });
        final ValueAnimator animator4 = ValueAnimator.ofFloat(new float[]{this.m, 0.0f});
        animator4.setDuration(100);
        animator4.setInterpolator(new AccelerateInterpolator());
        animator4.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ BackTopView b;

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LayoutParams layoutParams3 = (LayoutParams) this.b.l.getLayoutParams();
                layoutParams3.leftMargin = (int) ((Float) animator4.getAnimatedValue()).floatValue();
                this.b.l.setLayoutParams(layoutParams3);
            }
        });
        final ValueAnimator animator5 = ValueAnimator.ofFloat(new float[]{this.o, 0.0f});
        animator5.setDuration(100);
        animator5.setInterpolator(new AccelerateInterpolator());
        animator5.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ BackTopView b;

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LayoutParams layoutParams4 = (LayoutParams) this.b.n.getLayoutParams();
                layoutParams4.rightMargin = (int) ((Float) animator5.getAnimatedValue()).floatValue();
                this.b.n.setLayoutParams(layoutParams4);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{animator1, animator2, animator3, animator4, animator5});
        animatorSet.start();
    }

    public float getmMaxHeight() {
        return this.f;
    }

    public void setmMaxHeight(float mMaxHeight) {
        this.f = mMaxHeight;
    }
}

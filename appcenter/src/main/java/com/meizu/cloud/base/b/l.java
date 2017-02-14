package com.meizu.cloud.base.b;

import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.meizu.cloud.app.widget.FlowLayout;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.e;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.base.app.BaseCommonActivity;
import com.meizu.cloud.base.app.BaseCommonActivity.a;

public abstract class l<T> extends m<T> implements a {
    private final int a = 200;
    private ViewGroup b;
    private ImageView c;
    private ViewGroup d;
    private FlowLayout k;
    private View l;

    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(g.base_pager_pop_view, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof BaseCommonActivity) {
            ((BaseCommonActivity) getActivity()).a((a) this);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity() instanceof BaseCommonActivity) {
            ((BaseCommonActivity) getActivity()).a(null);
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        a(false);
    }

    protected void setupActionBar() {
        super.setupActionBar();
        ActionBar actionBar = getActionBar();
        actionBar.k(true);
        actionBar.a(new OnClickListener(this) {
            final /* synthetic */ l a;

            {
                this.a = r1;
            }

            public void onClick(View v) {
                this.a.a();
            }
        });
    }

    private void a() {
        a(getView());
        e();
    }

    private TextView a(final String title) {
        TextView btn = new TextView(getActivity());
        btn.setText(title);
        btn.setGravity(17);
        int innerSpace = getResources().getDimensionPixelSize(d.multi_tab_button_inner_margin);
        btn.setPadding(innerSpace, 0, innerSpace, 0);
        btn.setTextSize(((float) getResources().getDimensionPixelSize(d.multi_tab_text_size)) / getResources().getDisplayMetrics().density);
        btn.setSingleLine();
        btn.setBackgroundResource(e.multi_btn_flow_selector);
        btn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ l b;

            public void onClick(View v) {
                if (this.b.h != null) {
                    for (int i = 0; i < this.b.h.length; i++) {
                        if (this.b.h[i].equals(title)) {
                            this.b.b(i);
                            this.b.i();
                            return;
                        }
                    }
                }
            }
        });
        return btn;
    }

    private void b(int position) {
        getActionBar().a(getActionBar().c(position));
        if (this.k != null) {
            int i = 0;
            while (i < this.k.getChildCount()) {
                TextView btn = (TextView) this.k.getChildAt(i);
                btn.setSelected(this.e.getCurrentItem() == i);
                btn.setTextColor(this.e.getCurrentItem() == i ? -1 : getResources().getColor(c.desc_color));
                i++;
            }
        }
    }

    private void a(View rootView) {
        if (this.d == null) {
            int barHeight = com.meizu.cloud.app.utils.d.f(getActivity());
            int tabHeight = getActionBar().l().getHeight();
            this.b = (ViewGroup) rootView.findViewById(f.layout_bar);
            LayoutParams titleParams = (LayoutParams) this.b.getLayoutParams();
            titleParams.topMargin = barHeight;
            titleParams.height = tabHeight;
            this.b.setLayoutParams(titleParams);
            this.c = (ImageView) rootView.findViewById(f.btn_folder);
            this.c.setColorFilter(c.desc_color, Mode.MULTIPLY);
            ((TextView) rootView.findViewById(f.title)).setText(i.smartbar_cat);
            this.l = rootView.findViewById(f.cover_view);
            this.d = (ViewGroup) rootView.findViewById(f.layout_content);
            LayoutParams popParams = (LayoutParams) this.d.getLayoutParams();
            popParams.topMargin = barHeight + tabHeight;
            this.d.setLayoutParams(popParams);
            this.k = (FlowLayout) this.d.findViewById(f.flow_layout);
            FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(-2, getResources().getDimensionPixelSize(d.tab_pop_btn_height));
            layoutParams.setMargins(0, 0, getResources().getDimensionPixelSize(d.tab_pop_flow_btn_item_margin), getResources().getDimensionPixelSize(d.tab_pop_flow_btn_line_margin));
            if (this.h != null) {
                int i = 0;
                while (i < this.h.length) {
                    boolean z;
                    TextView btn = a(this.h[i]);
                    if (this.e.getCurrentItem() == i) {
                        z = true;
                    } else {
                        z = false;
                    }
                    btn.setSelected(z);
                    btn.setTextColor(this.e.getCurrentItem() == i ? -1 : getResources().getColor(c.desc_color));
                    this.k.addView(btn, layoutParams);
                    i++;
                }
            }
        }
    }

    private void e() {
        getActionBar().l().setVisibility(8);
        this.b.setVisibility(0);
        this.d.setVisibility(0);
        this.l.setVisibility(0);
        RotateAnimation rotateAnimation = new RotateAnimation(-180.0f, 0.0f, 1, 0.5f, 1, 0.5f);
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(200);
        animationSet.setInterpolator(new DecelerateInterpolator());
        this.c.setAnimation(animationSet);
        animationSet.start();
        TranslateAnimation animation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f);
        animation.setDuration(200);
        animation.setInterpolator(new DecelerateInterpolator());
        this.d.setAnimation(animation);
        animation.start();
        this.l.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ l a;

            {
                this.a = r1;
            }

            public boolean onTouch(View v, MotionEvent event) {
                v.setOnTouchListener(null);
                this.a.i();
                return true;
            }
        });
    }

    private void i() {
        this.l.setVisibility(8);
        j();
        k();
    }

    private void j() {
        this.b.clearAnimation();
        AlphaAnimation layoutAnm = new AlphaAnimation(1.0f, 0.0f);
        layoutAnm.setDuration(200);
        layoutAnm.setInterpolator(new AccelerateInterpolator());
        layoutAnm.setAnimationListener(new AnimationListener(this) {
            final /* synthetic */ l a;

            {
                this.a = r1;
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                this.a.getActionBar().l().setVisibility(0);
                this.a.b.setVisibility(8);
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.b.setAnimation(layoutAnm);
        layoutAnm.start();
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, -180.0f, 1, 0.5f, 1, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(200);
        animationSet.setInterpolator(new AccelerateInterpolator());
        this.c.setAnimation(animationSet);
        animationSet.start();
    }

    private void k() {
        this.d.clearAnimation();
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, -1.0f);
        translateAnimation.setDuration(200);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        translateAnimation.setAnimationListener(new AnimationListener(this) {
            final /* synthetic */ l a;

            {
                this.a = r1;
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                this.a.d.setVisibility(8);
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.d.setAnimation(translateAnimation);
        translateAnimation.start();
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        a(true);
    }

    public boolean d() {
        if (this.d == null || this.d.getVisibility() != 0) {
            return false;
        }
        i();
        return true;
    }
}

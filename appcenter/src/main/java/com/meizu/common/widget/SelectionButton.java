package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.common.a.b;
import com.meizu.common.a.d;
import com.meizu.common.a.g;
import com.meizu.common.a.h;
import com.meizu.common.a.j;

public class SelectionButton extends LinearLayout {
    private static final float[] g = new float[]{0.0f, 0.215313f, 0.513045f, 0.675783f, 0.777778f, 0.848013f, 0.898385f, 0.934953f, 0.96126f, 0.979572f, 0.991439f, 0.997972f, 1.0f, 1.0f};
    private static final float[] h = new float[]{0.0f, 0.002028f, 0.008561f, 0.020428f, 0.03874f, 0.065047f, 0.101615f, 0.151987f, 0.222222f, 0.324217f, 0.486955f, 0.784687f, 1.0f, 1.0f};
    Context a;
    Drawable b;
    private boolean c;
    private ObjectAnimator d;
    private int e;
    private ColorStateList f;
    private TextView i;
    private int j;
    private int k;
    private boolean l;

    public SelectionButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.b = null;
        this.c = false;
        this.j = 0;
        this.k = 0;
        this.l = false;
        this.a = context;
        TypedArray a = context.obtainStyledAttributes(attrs, j.SelectionButton, defStyle, 0);
        this.b = a.getDrawable(j.SelectionButton_mcBackground);
        this.f = a.getColorStateList(j.SelectionButton_mcSelectTextColor);
        a.recycle();
        c();
        e();
    }

    public SelectionButton(Context context, AttributeSet attrs) {
        this(context, attrs, b.MeizuCommon_SelectionButtonStyle);
    }

    public SelectionButton(Context context) {
        this(context, null);
    }

    private void c() {
        if (LayoutInflater.from(this.a).inflate(g.mc_selection_button, this) == null) {
            Log.w("SelectionButton", "can not inflate the view");
            return;
        }
        setClickable(true);
        setGravity(17);
        setMinimumWidth(getContext().getResources().getDimensionPixelSize(d.mz_action_button_min_width));
        this.i = (TextView) findViewById(16908308);
        d();
        this.i.setActivated(false);
        if (this.b != null) {
            this.i.setBackgroundDrawable(this.b);
        }
        if (this.f != null) {
            this.i.setTextColor(this.f);
        }
    }

    public void setTotalCount(int count) {
        if (this.j != count) {
            if (count < 0) {
                count = 0;
            }
            this.j = count;
            d();
        }
    }

    public int getTotalCount() {
        return this.j;
    }

    public void setCurrentCount(int count) {
        if (this.k != count) {
            if (count < 0) {
                count = 0;
            }
            this.k = count;
            d();
        }
    }

    public int getCurrentCount() {
        return this.k;
    }

    public void setAllSelected(boolean select) {
        if (select) {
            this.k = this.j;
        } else {
            this.k = 0;
        }
        d();
    }

    private void d() {
        if (this.k > this.j) {
            this.k = this.j;
        }
        if (this.j <= 0 || this.k != this.j) {
            this.i.setText(String.valueOf(this.k));
            this.l = false;
            this.i.setActivated(false);
            return;
        }
        this.l = true;
        this.i.setText(getContext().getResources().getString(h.mc_selectionbutton_all));
        this.i.setActivated(true);
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (this.i != null) {
            this.i.setEnabled(enabled);
        }
    }

    public void setVisibility(int visibility) {
        if (!this.c) {
            super.setVisibility(visibility);
        } else if (this.e != visibility) {
            this.e = visibility;
            if (visibility == 0) {
                super.setVisibility(visibility);
                this.d.start();
                return;
            }
            this.d.reverse();
        }
    }

    public void setVisibility(int visibility, boolean isAnimation) {
        if (isAnimation) {
            setVisibility(visibility);
            return;
        }
        super.setVisibility(visibility);
        this.e = getVisibility();
    }

    private void e() {
        if (this.d == null && this.i != null) {
            this.c = true;
            this.e = getVisibility();
            f();
        }
    }

    private void f() {
        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", new float[]{0.0f, 1.0f});
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", new float[]{0.0f, 1.0f});
        this.d = ObjectAnimator.ofPropertyValuesHolder(this.i, new PropertyValuesHolder[]{pvhScaleX, pvhScaleY}).setDuration(200);
        this.d.setInterpolator(new TimeInterpolator(this) {
            final /* synthetic */ SelectionButton a;

            {
                this.a = r1;
            }

            public float getInterpolation(float input) {
                float[] interpolations;
                int index = Math.round(12.0f * input);
                if (this.a.e == 0) {
                    interpolations = SelectionButton.g;
                } else {
                    interpolations = SelectionButton.h;
                }
                return interpolations[index];
            }
        });
        this.d.addListener(new AnimatorListenerAdapter(this) {
            final /* synthetic */ SelectionButton a;

            {
                this.a = r1;
            }

            public void onAnimationEnd(Animator animation) {
                View view = (View) ((ObjectAnimator) animation).getTarget();
                view.setScaleX(1.0f);
                view.setScaleY(1.0f);
                this.a.setVisibility(this.a.e, false);
                this.a.setClickable(true);
            }

            public void onAnimationStart(Animator animation) {
                this.a.setClickable(false);
            }
        });
    }

    public void setIsAnimation(boolean isAnimation) {
        this.c = isAnimation;
    }

    public void setSelectBackground(Drawable drawable) {
        if (drawable != null) {
            this.b = drawable;
            this.i.setBackgroundDrawable(this.b);
        }
    }

    public void setSelectTextColor(int color) {
        this.i.setTextColor(color);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(SelectionButton.class.getName());
    }
}

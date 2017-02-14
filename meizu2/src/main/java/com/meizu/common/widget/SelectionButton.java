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

import com.meizu.common.R;

public class SelectionButton extends LinearLayout {
    private static final int FRAME_COUNT = 12;
    private static final float[] interpolationsEnter = new float[]{0.0f, 0.215313f, 0.513045f, 0
            .675783f, 0.777778f, 0.848013f, 0.898385f, 0.934953f, 0.96126f, 0.979572f, 0.991439f,
            0.997972f, 1.0f, 1.0f};
    private static final float[] interpolationsOut = new float[]{0.0f, 0.002028f, 0.008561f, 0
            .020428f, 0.03874f, 0.065047f, 0.101615f, 0.151987f, 0.222222f, 0.324217f, 0.486955f,
            0.784687f, 1.0f, 1.0f};
    private ObjectAnimator mAnimator;
    Context mContext;
    private int mCurrentCount;
    Drawable mDrawable;
    private boolean mIsAllSelected;
    private boolean mIsAnimation;
    private ColorStateList mSelectTextColor;
    private TextView mText;
    private int mTotalCount;
    private int targetVisibility;

    public SelectionButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDrawable = null;
        this.mIsAnimation = false;
        this.mTotalCount = 0;
        this.mCurrentCount = 0;
        this.mIsAllSelected = false;
        this.mContext = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R
                .styleable.SelectionButton, i, 0);
        this.mDrawable = obtainStyledAttributes.getDrawable(R.styleable
                .SelectionButton_mcBackground);
        this.mSelectTextColor = obtainStyledAttributes.getColorStateList(R.styleable
                .SelectionButton_mcSelectTextColor);
        obtainStyledAttributes.recycle();
        initView();
        initAnimation();
    }

    public SelectionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.MeizuCommon_SelectionButtonStyle);
    }

    public SelectionButton(Context context) {
        this(context, null);
    }

    private void initView() {
        if (LayoutInflater.from(this.mContext).inflate(R.layout.mc_selection_button, this) ==
                null) {
            Log.w("SelectionButton", "can not inflate the view");
            return;
        }
        setClickable(true);
        setGravity(17);
        setMinimumWidth(getContext().getResources().getDimensionPixelSize(R.dimen
                .mz_action_button_min_width));
        this.mText = (TextView) findViewById(16908308);
        update();
        this.mText.setActivated(false);
        if (this.mDrawable != null) {
            this.mText.setBackgroundDrawable(this.mDrawable);
        }
        if (this.mSelectTextColor != null) {
            this.mText.setTextColor(this.mSelectTextColor);
        }
    }

    public void setTotalCount(int i) {
        if (this.mTotalCount != i) {
            if (i < 0) {
                i = 0;
            }
            this.mTotalCount = i;
            update();
        }
    }

    public int getTotalCount() {
        return this.mTotalCount;
    }

    public void setCurrentCount(int i) {
        if (this.mCurrentCount != i) {
            if (i < 0) {
                i = 0;
            }
            this.mCurrentCount = i;
            update();
        }
    }

    public int getCurrentCount() {
        return this.mCurrentCount;
    }

    public void setAllSelected(boolean z) {
        if (z) {
            this.mCurrentCount = this.mTotalCount;
        } else {
            this.mCurrentCount = 0;
        }
        update();
    }

    public boolean isAllSelected() {
        return this.mIsAllSelected;
    }

    private void update() {
        if (this.mCurrentCount > this.mTotalCount) {
            this.mCurrentCount = this.mTotalCount;
        }
        if (this.mTotalCount <= 0 || this.mCurrentCount != this.mTotalCount) {
            this.mText.setText(String.valueOf(this.mCurrentCount));
            this.mIsAllSelected = false;
            this.mText.setActivated(false);
            return;
        }
        this.mIsAllSelected = true;
        this.mText.setText(getContext().getResources().getString(R.string.mc_selectionbutton_all));
        this.mText.setActivated(true);
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        if (this.mText != null) {
            this.mText.setEnabled(z);
        }
    }

    public void setVisibility(int i) {
        if (!this.mIsAnimation) {
            super.setVisibility(i);
        } else if (this.targetVisibility != i) {
            this.targetVisibility = i;
            if (i == 0) {
                super.setVisibility(i);
                this.mAnimator.start();
                return;
            }
            this.mAnimator.reverse();
        }
    }

    public void setVisibility(int i, boolean z) {
        if (z) {
            setVisibility(i);
            return;
        }
        super.setVisibility(i);
        this.targetVisibility = getVisibility();
    }

    private void initAnimation() {
        if (this.mAnimator == null && this.mText != null) {
            this.mIsAnimation = true;
            this.targetVisibility = getVisibility();
            setupAnimation();
        }
    }

    private void setupAnimation() {
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("scaleX", new float[]{0.0f, 1
                .0f});
        PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat("scaleY", new float[]{0.0f,
                1.0f});
        this.mAnimator = ObjectAnimator.ofPropertyValuesHolder(this.mText, new
                PropertyValuesHolder[]{ofFloat, ofFloat2}).setDuration(200);
        this.mAnimator.setInterpolator(new TimeInterpolator() {
            public float getInterpolation(float f) {
                float[] access$100;
                int round = Math.round(12.0f * f);
                if (SelectionButton.this.targetVisibility == 0) {
                    access$100 = SelectionButton.interpolationsEnter;
                } else {
                    access$100 = SelectionButton.interpolationsOut;
                }
                return access$100[round];
            }
        });
        this.mAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                View view = (View) ((ObjectAnimator) animator).getTarget();
                view.setScaleX(1.0f);
                view.setScaleY(1.0f);
                SelectionButton.this.setVisibility(SelectionButton.this.targetVisibility, false);
                SelectionButton.this.setClickable(true);
            }

            public void onAnimationStart(Animator animator) {
                SelectionButton.this.setClickable(false);
            }
        });
    }

    public void setIsAnimation(boolean z) {
        this.mIsAnimation = z;
    }

    public void setSelectBackground(Drawable drawable) {
        if (drawable != null) {
            this.mDrawable = drawable;
            this.mText.setBackgroundDrawable(this.mDrawable);
        }
    }

    public void setSelectTextColor(int i) {
        this.mText.setTextColor(i);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(SelectionButton.class.getName());
    }
}

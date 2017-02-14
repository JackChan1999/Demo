package com.meizu.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AbsListView.SelectionBoundsAdjuster;
import android.widget.FrameLayout;
import com.meizu.common.a.e;

public class PartitionItemLayout extends FrameLayout implements SelectionBoundsAdjuster {
    protected Drawable a;
    private Rect b;

    public PartitionItemLayout(Context context) {
        super(context);
    }

    public PartitionItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartitionItemLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Drawable cbg = getResources().getDrawable(e.mz_list_new_item_bg_light_activated);
        if (cbg != null) {
            setContentBackground(cbg);
        }
        if (this.b == null) {
            this.b = new Rect();
        }
    }

    public void adjustListItemSelectionBounds(Rect bounds) {
        bounds.left += this.b.left;
        bounds.top += this.b.top;
        bounds.right -= this.b.right;
        bounds.bottom -= this.b.bottom;
    }

    public View getDragView() {
        return getChildAt(0);
    }

    public void setBackground(Drawable background) {
        setBackgroundDrawable(background);
    }

    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        if (this.b == null) {
            this.b = new Rect();
        }
        if (background != null) {
            background.getPadding(this.b);
        } else {
            this.b.setEmpty();
        }
    }

    public void setContentBackground(Drawable contentBackground) {
        if (this.a != contentBackground) {
            if (this.a != null) {
                this.a.setCallback(null);
                unscheduleDrawable(this.a);
            }
            this.a = contentBackground;
            if (contentBackground != null) {
                setWillNotDraw(false);
                contentBackground.setCallback(this);
                if (contentBackground.isStateful()) {
                    contentBackground.setState(getDrawableState());
                }
            } else {
                setWillNotDraw(true);
            }
            requestLayout();
            invalidate();
        }
    }

    public Drawable getContentBackground() {
        return this.a;
    }

    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == this.a;
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.a != null) {
            this.a.jumpToCurrentState();
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.a != null && this.a.isStateful()) {
            this.a.setState(getDrawableState());
        }
    }

    protected void onDraw(Canvas canvas) {
        this.a.setBounds(this.b.left, this.b.top, getMeasuredWidth() - this.b.right, getMeasuredHeight() - this.b.bottom);
        this.a.draw(canvas);
        super.onDraw(canvas);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(PartitionItemLayout.class.getName());
    }
}

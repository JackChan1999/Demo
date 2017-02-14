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
import com.meizu.common.R;
import com.meizu.widget.ListDragShadowItem;

public class PartitionItemLayout extends FrameLayout implements SelectionBoundsAdjuster, ListDragShadowItem {
    protected Drawable mContentBackground;
    private Rect mItemShadeRect;

    public PartitionItemLayout(Context context) {
        super(context);
    }

    public PartitionItemLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PartitionItemLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Drawable drawable = getResources().getDrawable(R.drawable.mz_list_new_item_bg_light_activated);
        if (drawable != null) {
            setContentBackground(drawable);
        }
        if (this.mItemShadeRect == null) {
            this.mItemShadeRect = new Rect();
        }
    }

    public void adjustListItemSelectionBounds(Rect rect) {
        rect.left += this.mItemShadeRect.left;
        rect.top += this.mItemShadeRect.top;
        rect.right -= this.mItemShadeRect.right;
        rect.bottom -= this.mItemShadeRect.bottom;
    }

    public View getDragView() {
        return getChildAt(0);
    }

    public boolean needBackground() {
        return true;
    }

    public void setBackground(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (this.mItemShadeRect == null) {
            this.mItemShadeRect = new Rect();
        }
        if (drawable != null) {
            drawable.getPadding(this.mItemShadeRect);
        } else {
            this.mItemShadeRect.setEmpty();
        }
    }

    public void setContentBackground(Drawable drawable) {
        if (this.mContentBackground != drawable) {
            if (this.mContentBackground != null) {
                this.mContentBackground.setCallback(null);
                unscheduleDrawable(this.mContentBackground);
            }
            this.mContentBackground = drawable;
            if (drawable != null) {
                setWillNotDraw(false);
                drawable.setCallback(this);
                if (drawable.isStateful()) {
                    drawable.setState(getDrawableState());
                }
            } else {
                setWillNotDraw(true);
            }
            requestLayout();
            invalidate();
        }
    }

    public Drawable getContentBackground() {
        return this.mContentBackground;
    }

    protected boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mContentBackground;
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mContentBackground != null) {
            this.mContentBackground.jumpToCurrentState();
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mContentBackground != null && this.mContentBackground.isStateful()) {
            this.mContentBackground.setState(getDrawableState());
        }
    }

    protected void onDraw(Canvas canvas) {
        this.mContentBackground.setBounds(this.mItemShadeRect.left, this.mItemShadeRect.top, getMeasuredWidth() - this.mItemShadeRect.right, getMeasuredHeight() - this.mItemShadeRect.bottom);
        this.mContentBackground.draw(canvas);
        super.onDraw(canvas);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(PartitionItemLayout.class.getName());
    }
}

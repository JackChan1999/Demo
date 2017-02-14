package com.meizu.cloud.app.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import com.meizu.cloud.b.a.k;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

@TargetApi(14)
public class FlowLayout extends ViewGroup {
    private int a;
    private final List<List<View>> b;
    private final List<Integer> c;
    private final List<Integer> d;

    public static class LayoutParams extends MarginLayoutParams {
        public int a = -1;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, k.FlowLayout_Layout);
            try {
                this.a = a.getInt(k.FlowLayout_Layout_android_layout_gravity, -1);
            } finally {
                a.recycle();
            }
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    protected /* synthetic */ ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return a();
    }

    public /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return a(attributeSet);
    }

    protected /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return a(layoutParams);
    }

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.a = (b() ? 8388611 : 3) | 48;
        this.b = new ArrayList();
        this.c = new ArrayList();
        this.d = new ArrayList();
        TypedArray a = context.obtainStyledAttributes(attrs, k.FlowLayout, defStyle, 0);
        try {
            int index = a.getInt(k.FlowLayout_android_gravity, -1);
            if (index > 0) {
                setGravity(index);
            }
            a.recycle();
        } catch (Throwable th) {
            a.recycle();
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = (MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft()) - getPaddingRight();
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int width = 0;
        int height = getPaddingTop() + getPaddingBottom();
        int lineWidth = 0;
        int lineHeight = 0;
        int childCount = getChildCount();
        int i = 0;
        while (i < childCount) {
            View child = getChildAt(i);
            boolean lastChild = i == childCount + -1;
            if (child.getVisibility() != 8) {
                measureChildWithMargins(child, widthMeasureSpec, lineWidth, heightMeasureSpec, height);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int childWidthMode = Integer.MIN_VALUE;
                int childWidthSize = sizeWidth;
                int childHeightMode = Integer.MIN_VALUE;
                int childHeightSize = sizeHeight;
                if (lp.width == -1) {
                    childWidthMode = FileUtils.ONE_GB;
                    childWidthSize -= lp.leftMargin + lp.rightMargin;
                } else if (lp.width >= 0) {
                    childWidthMode = FileUtils.ONE_GB;
                    childWidthSize = lp.width;
                }
                if (lp.height >= 0) {
                    childHeightMode = FileUtils.ONE_GB;
                    childHeightSize = lp.height;
                } else if (modeHeight == 0) {
                    childHeightMode = 0;
                    childHeightSize = 0;
                }
                child.measure(MeasureSpec.makeMeasureSpec(childWidthSize, childWidthMode), MeasureSpec.makeMeasureSpec(childHeightSize, childHeightMode));
                int childWidth = (child.getMeasuredWidth() + lp.leftMargin) + lp.rightMargin;
                if (lineWidth + childWidth > sizeWidth) {
                    width = Math.max(width, lineWidth);
                    lineWidth = childWidth;
                    height += lineHeight;
                    lineHeight = (child.getMeasuredHeight() + lp.topMargin) + lp.bottomMargin;
                } else {
                    lineWidth += childWidth;
                    lineHeight = Math.max(lineHeight, (child.getMeasuredHeight() + lp.topMargin) + lp.bottomMargin);
                }
                if (lastChild) {
                    width = Math.max(width, lineWidth);
                    height += lineHeight;
                }
            } else if (lastChild) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
            i++;
        }
        width += getPaddingLeft() + getPaddingRight();
        if (modeWidth != 1073741824) {
            sizeWidth = width;
        }
        if (modeHeight != 1073741824) {
            sizeHeight = height;
        }
        setMeasuredDimension(sizeWidth, sizeHeight);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        float horizontalGravityFactor;
        int i;
        int childWidth;
        this.b.clear();
        this.c.clear();
        this.d.clear();
        int width = getWidth();
        int height = getHeight();
        int linesSum = getPaddingTop();
        int lineWidth = 0;
        int lineHeight = 0;
        List<View> lineViews = new ArrayList();
        switch (this.a & 7) {
            case 1:
                horizontalGravityFactor = 0.5f;
                break;
            case 5:
                horizontalGravityFactor = 1.0f;
                break;
            default:
                horizontalGravityFactor = 0.0f;
                break;
        }
        for (i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                childWidth = (child.getMeasuredWidth() + lp.leftMargin) + lp.rightMargin;
                int childHeight = (child.getMeasuredHeight() + lp.bottomMargin) + lp.topMargin;
                if (lineWidth + childWidth > width) {
                    this.c.add(Integer.valueOf(lineHeight));
                    this.b.add(lineViews);
                    this.d.add(Integer.valueOf(((int) (((float) (width - lineWidth)) * horizontalGravityFactor)) + getPaddingLeft()));
                    linesSum += lineHeight;
                    lineHeight = 0;
                    lineWidth = 0;
                    lineViews = new ArrayList();
                }
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
                lineViews.add(child);
            }
        }
        this.c.add(Integer.valueOf(lineHeight));
        this.b.add(lineViews);
        this.d.add(Integer.valueOf(((int) (((float) (width - lineWidth)) * horizontalGravityFactor)) + getPaddingLeft()));
        linesSum += lineHeight;
        int verticalGravityMargin = 0;
        switch (this.a & 112) {
            case 16:
                verticalGravityMargin = (height - linesSum) / 2;
                break;
            case 80:
                verticalGravityMargin = height - linesSum;
                break;
        }
        int numLines = this.b.size();
        int top = getPaddingTop();
        for (i = 0; i < numLines; i++) {
            lineHeight = ((Integer) this.c.get(i)).intValue();
            lineViews = (List) this.b.get(i);
            int left = ((Integer) this.d.get(i)).intValue();
            int children = lineViews.size();
            for (int j = 0; j < children; j++) {
                child = (View) lineViews.get(j);
                if (child.getVisibility() != 8) {
                    lp = (LayoutParams) child.getLayoutParams();
                    if (lp.height == -1) {
                        int childWidthMode = Integer.MIN_VALUE;
                        int childWidthSize = lineWidth;
                        if (lp.width == -1) {
                            childWidthMode = FileUtils.ONE_GB;
                        } else if (lp.width >= 0) {
                            childWidthMode = FileUtils.ONE_GB;
                            childWidthSize = lp.width;
                        }
                        child.measure(MeasureSpec.makeMeasureSpec(childWidthSize, childWidthMode), MeasureSpec.makeMeasureSpec((lineHeight - lp.topMargin) - lp.bottomMargin, FileUtils.ONE_GB));
                    }
                    childWidth = child.getMeasuredWidth();
                    childHeight = child.getMeasuredHeight();
                    int gravityMargin = 0;
                    if (Gravity.isVertical(lp.a)) {
                        switch (lp.a) {
                            case 16:
                            case 17:
                                gravityMargin = (((lineHeight - childHeight) - lp.topMargin) - lp.bottomMargin) / 2;
                                break;
                            case 80:
                                gravityMargin = ((lineHeight - childHeight) - lp.topMargin) - lp.bottomMargin;
                                break;
                        }
                    }
                    child.layout(lp.leftMargin + left, ((lp.topMargin + top) + gravityMargin) + verticalGravityMargin, (left + childWidth) + lp.leftMargin, (((top + childHeight) + lp.topMargin) + gravityMargin) + verticalGravityMargin);
                    left += (lp.leftMargin + childWidth) + lp.rightMargin;
                }
            }
            top += lineHeight;
        }
    }

    protected LayoutParams a(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    public LayoutParams a(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    protected LayoutParams a() {
        return new LayoutParams(-1, -1);
    }

    @TargetApi(14)
    public void setGravity(int gravity) {
        if (this.a != gravity) {
            if ((8388615 & gravity) == 0) {
                gravity |= b() ? 8388611 : 3;
            }
            if ((gravity & 112) == 0) {
                gravity |= 48;
            }
            this.a = gravity;
            requestLayout();
        }
    }

    public int getGravity() {
        return this.a;
    }

    private static boolean b() {
        return VERSION.SDK_INT >= 14;
    }
}

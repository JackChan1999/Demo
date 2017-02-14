package com.qq.demo.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/30 09:03
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class FlowLayout extends ViewGroup {

    private int mWidth;
    private int horizontalSpace;
    private int verticalSpace;
    private List<Line> mLines;
    private Line currentLine;
    private int usedWidth;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLines = new ArrayList<>();
    }

    public void setHorizontalSpace(int horizontalSpace){
        this.horizontalSpace = horizontalSpace;
    }

    public void setVerticalSpace(int verticalSpace){
        this.verticalSpace = verticalSpace;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //重置
        mLines.clear();
        usedWidth = 0;
        currentLine = null;
        //获取flowlayout的size和mode
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingBottom() - getPaddingTop();

        //make子view的measureSpec
        int childWidthMode = widthMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : widthMode;
        int childHeightMode = heightMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : heightMode;
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(mWidth, childWidthMode);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, childHeightMode);

        currentLine = new Line();
        //遍历子view，测量每一个子view的宽高
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            usedWidth += child.getMeasuredWidth();
            if (usedWidth <= mWidth) {
                currentLine.addChild(child);
                usedWidth += horizontalSpace;
                if (usedWidth > mWidth) {
                    newLine();//换行
                }
            } else {
                if (currentLine.getChildCount() < 1) {
                    currentLine.addChild(child);
                }
                newLine();
            }
        }
        //添加最后一行
        if (!mLines.contains(currentLine)) {
            mLines.add(currentLine);
        }

        int totalHeighjt = verticalSpace * (mLines.size() - 1) + getPaddingBottom() +
                getPaddingTop();
        for (Line line : mLines) {
            totalHeighjt += line.getHeight();
        }

        //设置自己的宽高
        setMeasuredDimension(mWidth + getPaddingLeft() + getPaddingRight(), resolveSize
                (totalHeighjt, heightMeasureSpec));
    }

    private void newLine() {
        mLines.add(currentLine);
        currentLine = new Line();
        usedWidth = 0;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        l += getPaddingLeft();
        t += getPaddingTop();
        for (Line line : mLines) {
            line.layout(l, t);
            t += line.getHeight() + verticalSpace;
        }
    }

    private class Line {

        private int height = 0;
        private int lineWidth = 0;
        private List<View> children;

        public Line(){
            children = new ArrayList<>();
        }

        public int getHeight() {
            return height;
        }

        public int getChildCount() {
            return children.size();
        }

        public void addChild(View child) {
            children.add(child);
            if (child.getMeasuredHeight() > height) {
                height = child.getMeasuredHeight();
            }
            lineWidth += child.getMeasuredWidth();
        }

        public void layout(int left, int top) {
            int surplusWidth = mWidth - lineWidth - horizontalSpace * (children.size() - 1);
            int surplusChild = 0;
            if (surplusWidth > 0) {
                surplusChild = surplusWidth / children.size();
            }
            for (View child : children) {
                child.layout(left, top, left + child.getMeasuredWidth() + surplusChild, top +
                        child.getMeasuredHeight());
                left += child.getMeasuredWidth() + surplusChild;
                left += horizontalSpace;
            }
        }
    }
}

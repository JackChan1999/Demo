package com.google.demo3;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/18 20:59
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class SweepView extends ViewGroup {

    private ViewDragHelper mHelper;

    public SweepView(Context context) {
        super(context);
        mHelper = ViewDragHelper.create(this,new MyCallBack());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mHelper.processTouchEvent(event);
        return true;
    }

    class MyCallBack extends ViewDragHelper.Callback{

        /**
         * 是否分析view的touch事件
         * @param child 触摸的view
         * @param pointerId touch的id
         * @return
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return false;
        }

        /**
         * 当touch移动后的回调
         * @param child 谁移动了
         * @param left child的左侧的边距
         * @param dx 增量的x
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left; //确定要移动多少
        }

        /**
         * 当控件的位置移动时的回调
         * @param changedView 那个view移动了
         * @param left，top view坐上角的坐标
         * @param dx，dy 移动的增量
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        /**
         * up时的回调
         * @param releasedChild 松开了那个view
         * @param xvel 水平方向速率
         * @param yvel 垂直方向速率
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
        }
    }
}

package com.google.slidingmenu;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/18 15:39
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class ScrollAnimation extends Animation {

    private View view;
    private int targetScrollX;
    private int startScrollX;
    private int totalValue;

    public ScrollAnimation(View view, int targetScrollX){
        this.view = view;
        this.targetScrollX = targetScrollX;
        startScrollX = view.getScrollX();
        totalValue = this.targetScrollX - startScrollX;
        setDuration(Math.abs(totalValue));
    }

    /**
     *
     * @param interpolatedTime 进度或百分比
     * @param t
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        int currentScrollX = (int) (startScrollX + totalValue*interpolatedTime);
        view.scrollTo(currentScrollX,0);
    }
}

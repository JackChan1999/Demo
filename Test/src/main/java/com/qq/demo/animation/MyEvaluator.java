package com.qq.demo.animation;

import android.animation.TypeEvaluator;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/7/21 18:13
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class MyEvaluator implements TypeEvaluator<Integer> {
    @Override
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int)(200+startInt + fraction * (endValue - startInt));
    }
}

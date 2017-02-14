package com.qq.demo.animation;

import android.animation.TimeInterpolator;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/7/21 18:05
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class MyInterploator implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        return 1-input;
    }
}

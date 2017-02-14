package com.qq.demo.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;

import com.qq.demo.base.BaseApplication;


/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/3/20 17:27
 * 描 述 ：和ui相关的工具类
 * 修订历史 ：
 * ============================================================
 **/
public class UIUtils {

    public static Context getContext() {
        return BaseApplication.getContext();
    }

    public static Resources getResource() {
        return getContext().getResources();
    }

    public static String getString(int resId) {
        return getResource().getString(resId);
    }

    public static String[] getStringArr(int resId) {
        return getResource().getStringArray(resId);
    }

    public static int getColor(int colorId) {
        return getResource().getColor(colorId);
    }

    public static String getPackageName() {
        return getContext().getPackageName();
    }

    public static long getMainThreadid() {
        return BaseApplication.getMainTreadId();
    }

    public static Handler getMainThreadHandler() {
        return BaseApplication.getHandler();
    }

    public static void postTaskSafely(Runnable task) {
        int curThreadId = android.os.Process.myTid();

        if (curThreadId == getMainThreadid()) {// 如果当前线程是主线程
            task.run();
        } else {// 如果当前线程不是主线程
            getMainThreadHandler().post(task);
        }

    }

    public static Drawable getDrawable(int drawableId) {
        return getResources().getDrawable(drawableId);
    }

    public static ColorStateList getColorStateList(int mTabTextColorResId) {
        return getResources().getColorStateList(mTabTextColorResId);
    }

    public static View inflate(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * @param runnable 保证方法在主线程中运行
     */
   /* public static void runOnMainThread(Runnable runnable) {
        if (android.os.Process.myTid() == getMainThreadId()) {
            //在主线程中的方法直接运行
            runnable.run();
        } else {
            //不在主线程中放入主线程中运行
            getHandler().post(runnable);
        }
    }*/

    public static int dip2Px(int dip) {
        // px/dip = density;
        float density = getResource().getDisplayMetrics().density;
        int px = (int) (dip * density + .5f);
        return px;
    }

    public static int px2Dip(int px) {
        // px/dip = density;
        float density = getResource().getDisplayMetrics().density;
        int dip = (int) (px / density + .5f);
        return dip;
    }
}

package com.qq.demo.utils;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class ViewUtils {
    public static void showView(View view) {
        if (view != null && view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hideView(View view) {
        if (view != null && view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static Rect getViewBoundRect(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return new Rect(iArr[0], iArr[1], iArr[0] + view.getWidth(), iArr[1] + view.getHeight());
    }

    public static int getViewHeight(View view) {
        if (view == null) {
            return 0;
        }
        int measuredHeight = view.getMeasuredHeight();
        if (measuredHeight != 0) {
            return measuredHeight;
        }
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null && layoutParams.height > 0) {
            return layoutParams.height;
        }
        view.measure(0, 0);
        return view.getMeasuredHeight();
    }

    public static int getViewWidth(View view) {
        if (view == null) {
            return 0;
        }
        int measuredWidth = view.getMeasuredWidth();
        if (measuredWidth != 0) {
            return measuredWidth;
        }
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null && layoutParams.width > 0) {
            return layoutParams.width;
        }
        view.measure(0, 0);
        return view.getMeasuredWidth();
    }
}

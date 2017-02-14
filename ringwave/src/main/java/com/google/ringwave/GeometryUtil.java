package com.google.ringwave;

import android.graphics.PointF;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/23 19:46
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class GeometryUtil {

    public static PointF getMiddlePoint(PointF p1, PointF p2){
        return new PointF((p1.x + p2.x)/2.0f,(p1.y + p2.y)/2.0f);
    }

    public static PointF[] getPoints(PointF point, float radius, Double linek){
        PointF[] points = new PointF[2];
        float aran, xOffset = 0, yOffset = 0;
        if (linek != null){
            aran = (float) Math.atan(linek);
            xOffset = (float) (Math.cos(aran)*radius);
            yOffset = (float) (Math.sin(aran)*radius);
        }else {
            yOffset = 0;
            xOffset = radius;
        }
        points[0] = new PointF((point.x + xOffset), (point.y - yOffset));
        points[1] = new PointF((point.x - xOffset), (point.y + yOffset));
        return points;
    }

    public static float getDistanceBetween2Points(PointF p1, PointF p2){
        return (float) Math.sqrt(Math.pow(p1.x - p2.x,2) + Math.pow(p1.y - p2.y, 2));
    }

    public static PointF getPointByPercent(PointF p1, PointF p2, float percent){
        return new PointF(evaluate(percent,p1.x,p2.x),evaluate(percent,p1.y,p2.y));
    }

    public static  Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }

    public static float evaluateValue(float fraction, Number start, Number end){
        float startfloat = start.floatValue();
        return startfloat + fraction*(end.floatValue() - startfloat);
    }
}

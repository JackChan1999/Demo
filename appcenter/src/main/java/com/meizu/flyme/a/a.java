package com.meizu.flyme.a;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.c.d;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class a {
    private boolean a = true;
    private final b b = new b();

    public class a {
        public int a;
        public int b;
        public int c;
        public float d;
        public float e;
        public float f;
        public int g;
        public int h;
        public int i;
        public boolean j;
        final /* synthetic */ a k;

        public a(a aVar, int red, int green, int blue, float h, float s, float v, int rgba, int count) {
            int i;
            this.k = aVar;
            this.a = red;
            this.b = green;
            this.c = blue;
            this.d = h;
            this.e = s;
            this.f = v;
            this.g = rgba;
            this.h = count;
            if (v > 0.5f) {
                i = 1;
            } else {
                i = 0;
            }
            this.i = i;
            this.j = false;
        }
    }

    private class b implements Comparator<a> {
        final /* synthetic */ a a;

        private b(a aVar) {
            this.a = aVar;
        }

        public /* synthetic */ int compare(Object x0, Object x1) {
            return a((a) x0, (a) x1);
        }

        public int a(a a, a b) {
            return b.h - a.h;
        }
    }

    public int a(Bitmap bitmap) {
        int primaryColor = -16777216;
        long startTime = 0;
        if (bitmap != null) {
            int i;
            List<android.support.v7.c.d.a> swatches = d.a(bitmap, 38).a();
            if (this.a) {
                startTime = System.currentTimeMillis();
            }
            float[] hsv = new float[3];
            List<a> initColorsSets = new ArrayList();
            List<a> colorSets = new ArrayList();
            for (android.support.v7.c.d.a swatch : swatches) {
                int rgba = swatch.a();
                int r = Color.red(rgba);
                int g = Color.green(rgba);
                int b = Color.blue(rgba);
                Color.RGBToHSV(r, g, b, hsv);
                if ((hsv[1] >= 0.1f || hsv[2] <= 0.9f) && hsv[2] >= 0.25f) {
                    initColorsSets.add(new a(this, r, g, b, hsv[0], hsv[1], hsv[2], rgba, swatch.c()));
                }
            }
            Collections.sort(initColorsSets, this.b);
            for (i = 0; i < initColorsSets.size(); i++) {
                a tempColorI = (a) initColorsSets.get(i);
                if (!tempColorI.j) {
                    tempColorI.j = true;
                    for (int j = i + 1; j < initColorsSets.size(); j++) {
                        a tempColorJ = (a) initColorsSets.get(j);
                        if (!tempColorJ.j && Math.abs(tempColorI.d - tempColorJ.d) < 20.0f) {
                            tempColorJ.j = true;
                            tempColorI.h += tempColorJ.h;
                        }
                    }
                    colorSets.add(tempColorI);
                }
            }
            if (colorSets.size() > 0) {
                a bestColor = (a) colorSets.get(0);
                if (bestColor.i == 1) {
                    for (i = 1; i < colorSets.size(); i++) {
                        if (bestColor.h < ((a) colorSets.get(i)).h * 2) {
                            bestColor = (a) colorSets.get(i);
                            break;
                        }
                    }
                }
                if (bestColor.i == 1) {
                    bestColor.f -= 0.16f;
                    hsv[0] = bestColor.d;
                    hsv[1] = a(bestColor.e, 0.0f, 0.8f);
                    hsv[2] = a(bestColor.f, 0.3f, 0.85f);
                    primaryColor = Color.HSVToColor(hsv);
                } else {
                    primaryColor = bestColor.g;
                }
            }
            initColorsSets.clear();
            colorSets.clear();
            if (this.a) {
                Log.e("PrimaryColor", "spend time:" + (System.currentTimeMillis() - startTime) + " ms");
            }
        }
        return primaryColor;
    }

    private static float a(float value, float min, float max) {
        if (value < min) {
            return min;
        }
        return value > max ? max : value;
    }
}

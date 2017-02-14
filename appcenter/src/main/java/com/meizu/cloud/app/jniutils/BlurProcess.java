package com.meizu.cloud.app.jniutils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import java.util.ArrayList;
import java.util.concurrent.Callable;

class BlurProcess {

    private static class a implements Callable<Void> {
        private final Bitmap a;
        private final int b;
        private final int c;
        private final int d;
        private final int e;

        public /* synthetic */ Object call() throws Exception {
            return a();
        }

        public a(Bitmap bitmapOut, int radius, int totalCores, int coreIndex, int round) {
            this.a = bitmapOut;
            this.b = radius;
            this.c = totalCores;
            this.d = coreIndex;
            this.e = round;
        }

        public Void a() throws Exception {
            BlurProcess.functionToBlur(this.a, this.b, this.c, this.d, this.e);
            return null;
        }
    }

    private static native void functionToBlur(Bitmap bitmap, int i, int i2, int i3, int i4);

    BlurProcess() {
    }

    static {
        try {
            System.loadLibrary("blur");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap a(Bitmap original, float radius) {
        Bitmap bitmapOut = original.copy(Config.ARGB_8888, true);
        int cores = a.a;
        ArrayList<a> horizontal = new ArrayList(cores);
        ArrayList<a> vertical = new ArrayList(cores);
        for (int i = 0; i < cores; i++) {
            horizontal.add(new a(bitmapOut, (int) radius, cores, i, 1));
            vertical.add(new a(bitmapOut, (int) radius, cores, i, 2));
        }
        try {
            a.b.invokeAll(horizontal);
            try {
                a.b.invokeAll(vertical);
            } catch (InterruptedException e) {
            }
        } catch (InterruptedException e2) {
        }
        return bitmapOut;
    }
}

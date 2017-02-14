package com.meizu.cloud.app.jniutils;

import android.graphics.Bitmap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class a {
    static final int a = Runtime.getRuntime().availableProcessors();
    static final ExecutorService b = Executors.newFixedThreadPool(a);
    private final Bitmap c;
    private Bitmap d;
    private final BlurProcess e = new BlurProcess();

    public a(Bitmap image) {
        this.c = image;
    }

    public Bitmap a(int radius) {
        this.d = this.e.a(this.c, (float) radius);
        return this.d;
    }
}

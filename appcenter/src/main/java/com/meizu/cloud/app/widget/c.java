package com.meizu.cloud.app.widget;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import com.squareup.picasso.Transformation;

public class c implements Transformation {
    private int a;
    private int b;

    public c(int radius, int margin) {
        this.a = radius;
        this.b = margin;
    }

    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, TileMode.CLAMP, TileMode.CLAMP));
        canvas.drawRoundRect(new RectF((float) this.b, (float) this.b, (float) (width - this.b), (float) (height - this.b)), (float) this.a, (float) this.a, paint);
        source.recycle();
        return bitmap;
    }

    public String key() {
        return "RoundedTransformation(radius=" + this.a + ", margin=" + this.b + ")";
    }
}

package com.meizu.common.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.PictureDrawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.LruCache;

public class StrokeDrawableUtils {
    private static final int DEF_STROKE_RADIUS = 1;
    private static final int EFFECTIVE_COLOR = -16777216;
    private static final int MAX_LENGTH = 1000;
    private static final int STROKE_ALPHA_VALUE = 78;
    private static final int STROKE_RECT_ALPHA_VALUE = 26;
    private static final Object syncStroke = new Object();
    private static final Object syncStrokeRect = new Object();

    static class StrokeLruCache {
        private static BlurMaskFilter mBlurMaskFilter;
        private static final int mCacheSize = (mMaxMemory / 8);
        private static final int mMaxMemory = ((int) (Runtime.getRuntime().maxMemory() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID));
        private static LruCache<String, Bitmap> mMemoryCache = new AnonymousClass1(mCacheSize);
        private static Canvas mStrokeCanvas;
        private static Paint mStrokePaint;

        final class AnonymousClass1 extends LruCache<String, Bitmap> {
            AnonymousClass1(int i) {
                super(i);
            }

            protected int sizeOf(String str, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        }

        private StrokeLruCache() {
        }

        public static Bitmap getExtraAlphaBitmap(int i, int i2, int i3, Bitmap bitmap) {
            String str = String.valueOf(i3) + String.valueOf(i) + String.valueOf(i2);
            Bitmap bitmap2 = (Bitmap) mMemoryCache.get(str);
            if (bitmap2 != null) {
                return bitmap2;
            }
            MaskFilter obtainBlurMaskFilter = obtainBlurMaskFilter();
            Paint obtainStokePaint = obtainStokePaint();
            obtainStokePaint.setMaskFilter(obtainBlurMaskFilter);
            bitmap2 = bitmap.extractAlpha(obtainStokePaint, new int[2]);
            mMemoryCache.put(str, bitmap2);
            return bitmap2;
        }

        public static Paint obtainStokePaint() {
            if (mStrokePaint == null) {
                mStrokePaint = new Paint();
            }
            return mStrokePaint;
        }

        public static Canvas obtainStrokeCanvas() {
            if (mStrokeCanvas == null) {
                mStrokeCanvas = new Canvas();
            }
            return mStrokeCanvas;
        }

        public static BlurMaskFilter obtainBlurMaskFilter() {
            if (mBlurMaskFilter == null) {
                mBlurMaskFilter = new BlurMaskFilter(1.0f, Blur.OUTER);
            }
            return mBlurMaskFilter;
        }
    }

    public static Drawable createStrokeDrawable(Drawable drawable, Resources resources, Boolean bool) {
        Drawable drawable2 = null;
        synchronized (syncStroke) {
            Bitmap drawable2Bitmap = drawable2Bitmap(drawable);
            if (drawable2Bitmap != null) {
                int intrinsicHeight = drawable.getIntrinsicHeight() + 2;
                int intrinsicWidth = drawable.getIntrinsicWidth() + 2;
                Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Config.ARGB_8888);
                if (createBitmap != null) {
                    int hashCode = drawable.hashCode();
                    createBitmap.eraseColor(0);
                    Canvas obtainStrokeCanvas = StrokeLruCache.obtainStrokeCanvas();
                    obtainStrokeCanvas.setBitmap(createBitmap);
                    Paint obtainStokePaint = StrokeLruCache.obtainStokePaint();
                    Bitmap extraAlphaBitmap = StrokeLruCache.getExtraAlphaBitmap(intrinsicWidth, intrinsicHeight, hashCode, drawable2Bitmap);
                    obtainStokePaint.reset();
                    obtainStokePaint.setAlpha(78);
                    obtainStrokeCanvas.drawBitmap(extraAlphaBitmap, (float) ((intrinsicWidth - extraAlphaBitmap.getWidth()) >> 1), (float) ((intrinsicHeight - extraAlphaBitmap.getHeight()) >> 1), obtainStokePaint);
                    obtainStrokeCanvas.drawBitmap(drawable2Bitmap, (float) ((intrinsicWidth - drawable2Bitmap.getWidth()) >> 1), (float) ((intrinsicHeight - drawable2Bitmap.getHeight()) >> 1), null);
                    drawable2 = new BitmapDrawable(resources, createBitmap);
                    drawable2.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
                }
            }
            if (drawable2 != null) {
                drawable = drawable2;
            }
        }
        return drawable;
    }

    @Deprecated
    public static Drawable createStrokeDrawable(Drawable drawable, Resources resources) {
        return createStrokeDrawable(drawable, resources, Boolean.valueOf(true));
    }

    private static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable == null || drawable.getIntrinsicHeight() > MAX_LENGTH || drawable.getIntrinsicWidth() > MAX_LENGTH) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        if ((!(drawable instanceof NinePatchDrawable) && !(drawable instanceof StateListDrawable) && !(drawable instanceof GradientDrawable) && !(drawable instanceof InsetDrawable) && !(drawable instanceof LayerDrawable) && !(drawable instanceof LevelListDrawable) && !(drawable instanceof PaintDrawable) && !(drawable instanceof PictureDrawable) && !(drawable instanceof RotateDrawable) && !(drawable instanceof ScaleDrawable) && !(drawable instanceof ShapeDrawable) && !(drawable instanceof ClipDrawable)) || drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
        if (createBitmap == null) {
            return null;
        }
        Canvas obtainStrokeCanvas = StrokeLruCache.obtainStrokeCanvas();
        obtainStrokeCanvas.setBitmap(createBitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(obtainStrokeCanvas);
        return createBitmap;
    }

    public static Drawable createRectStrokeDrawable(Drawable drawable, Resources resources) {
        synchronized (syncStrokeRect) {
            Drawable drawable2 = null;
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Config.ARGB_8888);
            if (createBitmap != null) {
                createBitmap.eraseColor(0);
                Canvas obtainStrokeCanvas = StrokeLruCache.obtainStrokeCanvas();
                obtainStrokeCanvas.setBitmap(createBitmap);
                drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
                drawable.draw(obtainStrokeCanvas);
                obtainStrokeCanvas.save();
                Paint obtainStokePaint = StrokeLruCache.obtainStokePaint();
                obtainStokePaint.setStrokeWidth(1.0f);
                obtainStokePaint.setColor(-16777216);
                obtainStokePaint.setAlpha(26);
                obtainStokePaint.setStyle(Style.STROKE);
                obtainStrokeCanvas.drawRect(1.0f, 1.0f, (float) (intrinsicWidth - 1), (float) (intrinsicHeight - 1), obtainStokePaint);
                drawable2 = new BitmapDrawable(resources, createBitmap);
            }
            if (drawable2 != null) {
                drawable = drawable2;
            }
        }
        return drawable;
    }
}

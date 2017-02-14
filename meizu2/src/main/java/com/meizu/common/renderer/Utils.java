package com.meizu.common.renderer;

import android.graphics.Bitmap;
import android.graphics.Rect;
import com.meizu.common.renderer.effect.GLRenderManager;
import com.meizu.common.renderer.effect.parameters.FastBlurParameters;
import java.lang.reflect.Field;

public class Utils {
    private static native void native_glFinish();

    private static native void native_glReadPixels(Object obj, int i, int i2);

    public static void assertTrue(boolean z) {
        if (!z) {
            throw new AssertionError();
        }
    }

    public static float getInterpolatorLinear(float f, float f2, float f3) {
        return ((1.0f - f) * f2) + (f3 * f);
    }

    public static float clip(float f, float f2, float f3) {
        if (f > f3) {
            return f3;
        }
        return f < f2 ? f2 : f;
    }

    public static int clip(int i, int i2, int i3) {
        if (i > i3) {
            return i3;
        }
        return i < i2 ? i2 : i;
    }

    public static int nextPowerOf2(int i) {
        if (i <= 0 || i > 1073741824) {
            throw new IllegalArgumentException();
        }
        int i2 = i - 1;
        i2 |= i2 >> 16;
        i2 |= i2 >> 8;
        i2 |= i2 >> 4;
        i2 |= i2 >> 2;
        return (i2 | (i2 >> 1)) + 1;
    }

    public static int nextMultipleN(int i, int i2) {
        return (((i + i2) - 1) / i2) * i2;
    }

    public static int nextMultipleN(float f, int i) {
        return (((int) ((((float) i) + f) - 1.0f)) / i) * i;
    }

    public static int prevPowerOf2(int i) {
        if (i > 0) {
            return Integer.highestOneBit(i);
        }
        throw new IllegalArgumentException();
    }

    public static long getBitmapNativeId(Bitmap bitmap) {
        long j = 0;
        if (bitmap != null) {
            Field field = null;
            try {
                field = Bitmap.class.getDeclaredField("mNativeBitmap");
                field.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            if (field != null) {
                try {
                    j = field.getLong(bitmap);
                } catch (IllegalArgumentException e2) {
                    e2.printStackTrace();
                } catch (IllegalAccessException e3) {
                    e3.printStackTrace();
                }
            }
        }
        return j;
    }

    public static byte[] getBitmapPixels(Bitmap bitmap) {
        NoSuchFieldException e;
        if (bitmap == null) {
            return null;
        }
        Field declaredField;
        try {
            declaredField = Bitmap.class.getDeclaredField("mBuffer");
            try {
                declaredField.setAccessible(true);
            } catch (NoSuchFieldException e2) {
                e = e2;
                e.printStackTrace();
                if (declaredField != null) {
                    return null;
                }
                try {
                    return (byte[]) declaredField.get(bitmap);
                } catch (IllegalArgumentException e3) {
                    e3.printStackTrace();
                    return null;
                } catch (IllegalAccessException e4) {
                    e4.printStackTrace();
                    return null;
                }
            }
        } catch (NoSuchFieldException e5) {
            e = e5;
            declaredField = null;
            e.printStackTrace();
            if (declaredField != null) {
                return null;
            }
            return (byte[]) declaredField.get(bitmap);
        }
        if (declaredField != null) {
            return null;
        }
        return (byte[]) declaredField.get(bitmap);
    }

    public static void view2Window(float[] fArr, Rect rect, Rect rect2) {
        if (rect2 != null) {
            rect2.set((int) ((((float) rect.left) + fArr[12]) + FastBlurParameters.DEFAULT_LEVEL), (int) ((((float) rect.top) + fArr[13]) + FastBlurParameters.DEFAULT_LEVEL), (int) ((((float) rect.right) + fArr[12]) + FastBlurParameters.DEFAULT_LEVEL), (int) ((((float) rect.bottom) + fArr[13]) + FastBlurParameters.DEFAULT_LEVEL));
        }
    }

    public static void window2View(float[] fArr, float f, float f2, float f3, float f4, Rect rect) {
        if (rect != null) {
            rect.set((int) ((f - fArr[12]) + FastBlurParameters.DEFAULT_LEVEL), (int) ((f2 - fArr[13]) + FastBlurParameters.DEFAULT_LEVEL), (int) ((f3 - fArr[12]) + FastBlurParameters.DEFAULT_LEVEL), (int) ((f4 - fArr[13]) + FastBlurParameters.DEFAULT_LEVEL));
        }
    }

    public static void glFinish() {
        GLRenderManager.getInstance().loadLibraryIfNeeded();
        native_glFinish();
    }

    public static void glFillBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            if (bitmap.isMutable()) {
                GLRenderManager.getInstance().loadLibraryIfNeeded();
                native_glReadPixels(bitmap, bitmap.getWidth(), bitmap.getHeight());
                return;
            }
            throw new IllegalArgumentException("Bitmap is not mutable.");
        }
    }
}

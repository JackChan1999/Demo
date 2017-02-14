package com.meizu.common.renderer.effect;

import android.util.Log;
import java.lang.reflect.Method;

public class GLES31Utils {
    public static final int GL_COMPUTE_SHADER = 37305;
    public static final int GL_READ_ONLY = 35000;
    public static final int GL_RGBA16F = 34842;
    public static final int GL_RGBA32F = 34836;
    public static final int GL_SHADER_IMAGE_ACCESS_BARRIER_BIT = 32;
    public static final int GL_TEXTURE_UPDATE_BARRIER_BIT = 256;
    public static final int GL_WRITE_ONLY = 35001;
    public static Method sMethod_glBindImageTexture;
    public static Method sMethod_glDispatchCompute;
    public static Method sMethod_glMemoryBarrier;
    public static Method sMethod_glTexStorage2D;

    static {
        try {
            Class cls = Class.forName("android.opengl.GLES31");
            sMethod_glBindImageTexture = cls.getMethod("glBindImageTexture", new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE});
            sMethod_glDispatchCompute = cls.getMethod("glDispatchCompute", new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE});
            sMethod_glMemoryBarrier = cls.getMethod("glMemoryBarrier", new Class[]{Integer.TYPE});
            sMethod_glTexStorage2D = cls.getMethod("glTexStorage2D", new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE});
        } catch (Exception e) {
            Log.e(GLRenderManager.TAG, "Exception: " + e.getMessage());
        }
    }

    public static void glBindImageTexture(int i, int i2, int i3, boolean z, int i4, int i5, int i6) {
        try {
            sMethod_glBindImageTexture.invoke(null, new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Boolean.valueOf(z), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6)});
        } catch (Exception e) {
            Log.e(GLRenderManager.TAG, "Exception glBindImageTexture: " + e.getMessage());
        }
    }

    public static void glDispatchCompute(int i, int i2, int i3) {
        try {
            sMethod_glDispatchCompute.invoke(null, new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)});
        } catch (Exception e) {
            Log.e(GLRenderManager.TAG, "Exception glDispatchCompute: " + e.getMessage());
        }
    }

    public static void glMemoryBarrier(int i) {
        try {
            sMethod_glMemoryBarrier.invoke(null, new Object[]{Integer.valueOf(i)});
        } catch (Exception e) {
            Log.e(GLRenderManager.TAG, "Exception glMemoryBarrier: " + e.getMessage());
        }
    }

    public static void glTexStorage2D(int i, int i2, int i3, int i4, int i5) {
        try {
            sMethod_glTexStorage2D.invoke(null, new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5)});
        } catch (Exception e) {
            Log.e(GLRenderManager.TAG, "Exception glTexStorage2D: " + e.getMessage());
        }
    }
}

package com.meizu.common.renderer.effect;

import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.util.Log;
import com.meizu.common.renderer.Utils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class GraphicBuffer {
    private static Constructor<?> sConstructor;
    private static Method sDestory;
    private static Method sLockCanvas;
    private static Method sUnlockCanvasAndPost;
    private final int mFormat;
    private Object mGraphicBufferObject = null;
    private final int mHeight;
    private final int mWidth;

    static {
        init();
    }

    public GraphicBuffer(EGLBitmap eGLBitmap) {
        Utils.assertTrue(eGLBitmap.isValid());
        this.mWidth = eGLBitmap.getWidth();
        this.mHeight = eGLBitmap.getHeight();
        this.mFormat = eGLBitmap.getFormat();
        try {
            if (VERSION.SDK_INT >= 21) {
                this.mGraphicBufferObject = sConstructor.newInstance(new Object[]{Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight), Integer.valueOf(this.mFormat), Integer.valueOf(0), Long.valueOf(eGLBitmap.getGraphicBuffer())});
                return;
            }
            this.mGraphicBufferObject = sConstructor.newInstance(new Object[]{Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight), Integer.valueOf(this.mFormat), Integer.valueOf(0), Integer.valueOf((int) eGLBitmap.getGraphicBuffer())});
        } catch (Exception e) {
            Log.e(GLRenderManager.TAG, "GraphicBufferWappper() : " + e.toString());
        }
    }

    public Canvas lockCanvas() {
        try {
            return (Canvas) sLockCanvas.invoke(this.mGraphicBufferObject, new Object[0]);
        } catch (Exception e) {
            Log.e(GLRenderManager.TAG, "GraphicBufferWappper lockCanvas : " + e.toString());
            return null;
        }
    }

    public void unlockCanvasAndPost(Canvas canvas) {
        try {
            sUnlockCanvasAndPost.invoke(this.mGraphicBufferObject, new Object[]{canvas});
        } catch (Exception e) {
            Log.e(GLRenderManager.TAG, "GraphicBufferWappper unlockCanvasAndPost : " + e.toString());
        }
    }

    public void destroy() {
        try {
            sDestory.invoke(this.mGraphicBufferObject, new Object[0]);
        } catch (Exception e) {
            Log.e(GLRenderManager.TAG, "GraphicBufferWappper destroy : " + e.toString());
        }
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getFormat() {
        return this.mFormat;
    }

    public int getUsage() {
        return 0;
    }

    private static void init() {
        try {
            Class cls = Class.forName("android.view.GraphicBuffer");
            if (VERSION.SDK_INT >= 21) {
                sConstructor = cls.getDeclaredConstructor(new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Long.TYPE});
            } else {
                sConstructor = cls.getDeclaredConstructor(new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE});
            }
            sConstructor.setAccessible(true);
            sLockCanvas = cls.getDeclaredMethod("lockCanvas", new Class[0]);
            sUnlockCanvasAndPost = cls.getDeclaredMethod("unlockCanvasAndPost", new Class[]{Canvas.class});
            sDestory = cls.getDeclaredMethod("destroy", new Class[0]);
        } catch (Exception e) {
            Log.e(GLRenderManager.TAG, "GraphicBufferWappper init : " + e.toString());
        }
    }
}

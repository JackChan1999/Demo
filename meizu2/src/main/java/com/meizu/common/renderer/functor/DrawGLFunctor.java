package com.meizu.common.renderer.functor;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.opengl.Matrix;
import android.os.Build.VERSION;
import android.util.Log;
import android.util.LongSparseArray;
import com.meizu.common.renderer.Utils;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.GLRenderManager;
import com.meizu.common.renderer.effect.MemoryTrimmer;
import com.meizu.common.renderer.effect.render.Render;
import com.meizu.common.renderer.effect.texture.Texture;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

public class DrawGLFunctor implements MemoryTrimmer {
    protected static final String TAG = "glrenderer";
    public static final int kModeDraw = 0;
    public static final int kModeProcess = 1;
    public static final int kModeProcessNoContext = 2;
    public static final int kModeSync = 3;
    private static LongSparseArray<WeakReference<DrawGLFunctor>> sDrawGLFunctors = new LongSparseArray();
    public static boolean sIsInitedLib = false;
    protected static Method sMethod_callDrawGLFunction;
    protected static Method sMethod_invokeFunctor;
    protected int mAlpha = 255;
    protected String mEffectKey = Render.NONE;
    protected long mNativeFunctor;
    protected Rect mSourceBounds = new Rect();

    public static class GLInfo {
        public int clipBottom;
        public int clipLeft;
        public int clipRight;
        public int clipTop;
        public boolean isLayer;
        public float[] transform;
        public int viewportHeight;
        public int viewportWidth;

        public GLInfo() {
            this.transform = new float[16];
            Matrix.setIdentityM(this.transform, 0);
        }

        public GLInfo(int i, int i2) {
            this.viewportWidth = i;
            this.viewportHeight = i2;
        }
    }

    private native long native_create(Object obj);

    private native void native_destory(long j);

    private static native void native_init();

    public DrawGLFunctor() {
        initLibrary();
        this.mNativeFunctor = native_create(new WeakReference(this));
        Utils.assertTrue(this.mNativeFunctor != 0);
        synchronized (sDrawGLFunctors) {
            sDrawGLFunctors.put(this.mNativeFunctor, new WeakReference(this));
        }
    }

    public void draw(Canvas canvas) {
        if (canvas.isHardwareAccelerated()) {
            this.mSourceBounds.set(0, 0, canvas.getWidth(), canvas.getHeight());
            drawFunctorInternal(canvas);
            return;
        }
        Log.e("glrenderer", "DrawGLFunctor only can use in hardware accelerated");
    }

    public void draw(Canvas canvas, int i, int i2, int i3, int i4) {
        if (canvas.isHardwareAccelerated()) {
            this.mSourceBounds.set(i, i2, i3, i4);
            drawFunctorInternal(canvas);
            return;
        }
        Log.e("glrenderer", "DrawGLFunctor only can use in hardware accelerated");
    }

    public void setEffect(String str) {
        if (str != null) {
            this.mEffectKey = str;
        }
    }

    public String getEffect() {
        return this.mEffectKey;
    }

    public Render getRender(GLCanvas gLCanvas) {
        return gLCanvas.getRender(this.mEffectKey);
    }

    public void setAlpha(int i) {
        this.mAlpha = i;
    }

    public int getAlpha() {
        return this.mAlpha;
    }

    public boolean isBlend(Texture texture) {
        return (this.mAlpha == 255 && (texture == null || texture.isOpaque())) ? false : true;
    }

    protected boolean drawFunctorInternal(Canvas canvas) {
        if (!(this.mNativeFunctor == 0 || sMethod_callDrawGLFunction == null)) {
            try {
                if (VERSION.SDK_INT < 21) {
                    sMethod_callDrawGLFunction.invoke(canvas, new Object[]{Integer.valueOf((int) this.mNativeFunctor)});
                    return true;
                }
                sMethod_callDrawGLFunction.invoke(canvas, new Object[]{Long.valueOf(this.mNativeFunctor)});
                return true;
            } catch (Exception e) {
                Log.e("glrenderer", "callDrawGLFunction2 method doesn't exist" + e.getMessage());
            }
        }
        return false;
    }

    public static void scheduleTrimMemory(int i) {
        int size = sDrawGLFunctors.size();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < size; i2++) {
            WeakReference weakReference = (WeakReference) sDrawGLFunctors.get(sDrawGLFunctors.keyAt(i2));
            if (weakReference == null || weakReference.get() == null) {
                arrayList.add(Long.valueOf(sDrawGLFunctors.keyAt(i2)));
            } else {
                ((DrawGLFunctor) weakReference.get()).onTrimMemory(i);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            sDrawGLFunctors.remove(((Long) it.next()).longValue());
        }
    }

    private static void postEventFromNative(WeakReference<DrawGLFunctor> weakReference, GLInfo gLInfo, int i) {
        if (weakReference != null && weakReference.get() != null) {
            DrawGLFunctor drawGLFunctor = (DrawGLFunctor) weakReference.get();
            if (gLInfo != null) {
                drawGLFunctor.onDraw(gLInfo);
            } else {
                drawGLFunctor.onInvoke(i);
            }
        }
    }

    public void onTrimMemory(int i) {
    }

    protected void onDraw(GLInfo gLInfo) {
        if (GLRenderManager.DEBUG) {
            Log.i("glrenderer", String.format("viewport: [%d, %d]", new Object[]{Integer.valueOf(gLInfo.viewportWidth), Integer.valueOf(gLInfo.viewportHeight)}));
            Log.i("glrenderer", String.format("source:[%d, %d, %d, %d]", new Object[]{Integer.valueOf(this.mSourceBounds.left), Integer.valueOf(this.mSourceBounds.top), Integer.valueOf(this.mSourceBounds.right), Integer.valueOf(this.mSourceBounds.bottom)}));
            Log.i("glrenderer", String.format("clip:[%d, %d, %d, %d]", new Object[]{Integer.valueOf(gLInfo.clipLeft), Integer.valueOf(gLInfo.clipTop), Integer.valueOf(gLInfo.clipRight), Integer.valueOf(gLInfo.clipBottom)}));
            Log.i("glrenderer", "transform: ");
            for (int i = 0; i < 4; i++) {
                Log.i("glrenderer", String.format("[%.1f, %.1f, %.1f, %.1f]", new Object[]{Float.valueOf(gLInfo.transform[i + 0]), Float.valueOf(gLInfo.transform[i + 4]), Float.valueOf(gLInfo.transform[i + 8]), Float.valueOf(gLInfo.transform[i + 12])}));
            }
        }
    }

    public void onInvoke(int i) {
    }

    public static void initLibrary() {
        if (!sIsInitedLib) {
            GLRenderManager.getInstance().loadLibraryIfNeeded();
            init();
            sIsInitedLib = true;
        }
    }

    private static void init() {
        native_init();
        try {
            if (VERSION.SDK_INT < 23) {
                Class cls = Class.forName("android.view.HardwareCanvas");
                if (VERSION.SDK_INT < 21) {
                    sMethod_callDrawGLFunction = cls.getMethod("callDrawGLFunction", new Class[]{Integer.TYPE});
                } else if (VERSION.SDK_INT == 21) {
                    sMethod_callDrawGLFunction = cls.getMethod("callDrawGLFunction", new Class[]{Long.TYPE});
                } else {
                    sMethod_callDrawGLFunction = cls.getMethod("callDrawGLFunction2", new Class[]{Long.TYPE});
                }
            } else {
                sMethod_callDrawGLFunction = Class.forName("android.view.DisplayListCanvas").getMethod("callDrawGLFunction2", new Class[]{Long.TYPE});
            }
        } catch (Throwable e) {
            Log.e("glrenderer", "callDrawGLFunction method doesn't exist", e);
        }
        try {
            if (VERSION.SDK_INT >= 21) {
                sMethod_invokeFunctor = Class.forName("android.view.ThreadedRenderer").getDeclaredMethod("invokeFunctor", new Class[]{Long.TYPE, Boolean.TYPE});
                sMethod_invokeFunctor.setAccessible(true);
            }
        } catch (Throwable e2) {
            Log.e("glrenderer", "invokeFunctor method doesn't exist", e2);
        }
    }

    protected void finalize() {
        try {
            if (this.mNativeFunctor != 0) {
                onTrimMemory(79);
                native_destory(this.mNativeFunctor);
                this.mNativeFunctor = 0;
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }
}

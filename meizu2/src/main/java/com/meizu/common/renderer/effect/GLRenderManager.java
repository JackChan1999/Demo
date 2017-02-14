package com.meizu.common.renderer.effect;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import com.meizu.common.renderer.SystemProperty;
import com.meizu.common.renderer.Utils;
import com.meizu.common.renderer.functor.InvokeFunctor;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;

public class GLRenderManager {
    public static final boolean DEBUG = SystemProperty.getBoolean("debug.perf.glrenderer", false);
    private static final int MSG_RECYCLE_RES = 0;
    public static final String TAG = "glrenderer";
    public static final int TRIM_MEMORY_BACKGROUND = 39;
    public static final int TRIM_MEMORY_COMPLETE = 79;
    public static final int TRIM_MEMORY_UI_HIDDEN = 19;
    public static int sDefaultFrameBufferCacheSize = 25165824;
    public static int sDefaultTextureCacheSize = ViewCompat.MEASURED_STATE_TOO_SMALL;
    private static final boolean sDisableBlur = SystemProperty.getBoolean("persist.sys.disable_blur_effect", false);
    private static final boolean sDisableViewBlur = SystemProperty.getBoolean("persist.sys.disable_blur_view", false);
    private static GLRenderManager sInstance;
    private static boolean sSupportedEGLBitmap = (VERSION.SDK_INT >= 17);
    private static boolean sUploadEGLBitmapWithGPU = false;
    private static final boolean sWindowStaticBlur = SystemProperty.getBoolean("persist.perf.wm_static_blur", false);
    private Context mAppContext;
    private EGLContext mEGLContext;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    if (GLRenderManager.this.mRecycleCallback == null) {
                        GLRenderManager.this.mRecycleCallback = new RecycleResourcesCallback();
                    }
                    Runtime.getRuntime().gc();
                    GLRenderManager.this.mRecycleCallback.invoke();
                    return;
                default:
                    return;
            }
        }
    };
    private boolean mIsLoadedLib = false;
    private String mLibraryName = "jni_glrenderer";
    private RecycleResourcesCallback mRecycleCallback;
    private GLCanvasImpl mRenderCanvas;
    private TrimMemoryCallback mTrimBackground;
    private TrimMemoryCallback mTrimComplete;
    private TrimMemoryCallback mTrimHideUI;

    class RecycleResourcesCallback extends InvokeFunctor {
        private RecycleResourcesCallback() {
        }

        protected void onInvoke(int i) {
            synchronized (GLRenderManager.this) {
                if (i == 1) {
                    if (GLRenderManager.this.mRenderCanvas != null) {
                        if (GLRenderManager.DEBUG) {
                            Log.d(GLRenderManager.TAG, "recycledResources");
                        }
                        GLRenderManager.this.mRenderCanvas.recycledResources();
                    }
                }
            }
        }
    }

    class TrimMemoryCallback extends InvokeFunctor {
        private int mLevel;

        public TrimMemoryCallback(int i) {
            this.mLevel = i;
        }

        protected void onInvoke(int i) {
            synchronized (GLRenderManager.this) {
                if (GLRenderManager.DEBUG) {
                    Log.i(GLRenderManager.TAG, "TrimMemoryCallback level = " + (this.mLevel + 1));
                }
                GLCanvasImpl canvas = GLRenderManager.this.getCanvas();
                if (canvas != null) {
                    canvas.onTrimMemory(this.mLevel + 1);
                }
                if (this.mLevel >= 79) {
                    GLRenderManager.this.mRenderCanvas = null;
                    GLRenderManager.this.mEGLContext = null;
                }
                if (this.mLevel >= 19) {
                    GLRenderManager.this.recycleGLResources();
                }
            }
        }
    }

    public void initialize(Application application) {
        this.mAppContext = application;
    }

    public void loadLibraryIfNeeded() {
        synchronized (this) {
            if (!this.mIsLoadedLib) {
                loadLibrary();
                this.mIsLoadedLib = true;
            }
        }
    }

    public static GLRenderManager getInstance() {
        GLRenderManager gLRenderManager;
        synchronized (GLRenderManager.class) {
            if (sInstance == null) {
                sInstance = new GLRenderManager();
            }
            gLRenderManager = sInstance;
        }
        return gLRenderManager;
    }

    public void invokeFunctor(InvokeFunctor invokeFunctor) {
        if (invokeFunctor != null) {
            invokeFunctor.invoke();
        }
    }

    public void trimMemory(int i) {
        if (!this.mIsLoadedLib) {
            return;
        }
        if (i >= 79) {
            if (this.mTrimComplete == null) {
                this.mTrimComplete = new TrimMemoryCallback(79);
            }
            this.mTrimComplete.invoke();
        } else if (i >= 39) {
            if (this.mTrimBackground == null) {
                this.mTrimBackground = new TrimMemoryCallback(39);
            }
            this.mTrimBackground.invoke();
        } else if (i >= 19) {
            if (this.mTrimHideUI == null) {
                this.mTrimHideUI = new TrimMemoryCallback(19);
            }
            this.mTrimHideUI.invoke();
        }
    }

    public GLCanvasImpl getCanvas() {
        synchronized (this) {
            if (this.mIsLoadedLib) {
                EGLContext eglGetCurrentContext = ((EGL10) EGLContext.getEGL()).eglGetCurrentContext();
                if (EGL10.EGL_NO_CONTEXT.equals(eglGetCurrentContext)) {
                    Log.e(TAG, "This thread is no EGLContext.");
                    return null;
                }
                if (this.mEGLContext != null) {
                    Utils.assertTrue(eglGetCurrentContext.equals(this.mEGLContext));
                }
                if (this.mRenderCanvas == null) {
                    this.mEGLContext = eglGetCurrentContext;
                    this.mRenderCanvas = new GLCanvasImpl();
                }
                this.mHandler.removeMessages(0);
                GLCanvasImpl gLCanvasImpl = this.mRenderCanvas;
                return gLCanvasImpl;
            }
            return null;
        }
    }

    public Resources getAppResources() {
        if (this.mAppContext != null) {
            return this.mAppContext.getResources();
        }
        return null;
    }

    public static boolean isUploadEGLBitmapWithGPU() {
        return sUploadEGLBitmapWithGPU;
    }

    public static void setUploadEGLBitmapWithGPU(boolean z) {
        sUploadEGLBitmapWithGPU = z;
    }

    public static boolean isSupprotedEGLBitmap() {
        return sSupportedEGLBitmap;
    }

    public static boolean isDisableViewBlur() {
        return sDisableViewBlur;
    }

    public static boolean isDisableBlur() {
        return sDisableBlur;
    }

    public static boolean isWindowStaticBlur() {
        return sWindowStaticBlur;
    }

    public void setLibraryName(String str) {
        this.mLibraryName = str;
    }

    public static void setDefaultTexureCacheSize(int i) {
        boolean z = i > 0 && i <= 96;
        Utils.assertTrue(z);
        sDefaultTextureCacheSize = (i * 1024) * 1024;
    }

    public static void setDefaultFrameBufferCacheSize(int i) {
        boolean z = i > 0 && i <= 96;
        Utils.assertTrue(z);
        sDefaultFrameBufferCacheSize = (i * 1024) * 1024;
    }

    private void loadLibrary() {
        try {
            System.loadLibrary(this.mLibraryName);
        } catch (Throwable th) {
            Log.e(TAG, "Load " + this.mLibraryName + ".so failed.", th);
        }
    }

    public Context getAppContext() {
        return this.mAppContext;
    }

    private void recycleGLResources() {
        this.mHandler.removeMessages(0);
        this.mHandler.sendEmptyMessageDelayed(0, 5000);
    }
}

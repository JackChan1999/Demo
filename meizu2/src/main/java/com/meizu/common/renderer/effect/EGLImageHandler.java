package com.meizu.common.renderer.effect;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.meizu.common.renderer.Utils;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

public class EGLImageHandler implements GLResource {
    private static final int MSG_FREERESOURCE = 2;
    private static final int MSG_INIT = 0;
    private static final int MSG_RELEASE = 1;
    private static final int MSG_UPLOAD = 3;
    private static EGLImageHandler sEGLUploader;
    private EGLConfig mEglConfig;
    private EGLHandler mEglHandler;
    private HandlerThread mEglThread = new HandlerThread(GLRenderManager.TAG);
    private ConditionVariable mEglThreadBlock = new ConditionVariable();

    class EGLHandler extends Handler {
        private static final int EGL_CONTEXT_CLIENT_VERSION = 12440;
        private static final int EGL_OPENGL_ES2_BIT = 4;
        private EGL10 mEgl;
        private EGLContext mEglContext;
        private EGLDisplay mEglDisplay;
        private EGLSurface mEglSurface;

        public EGLHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    initEGL();
                    Log.i(GLRenderManager.TAG, "init EGL success.");
                    EGLImageHandler.this.mEglThreadBlock.open();
                    return;
                case 1:
                    release();
                    EGLImageHandler.this.mEglThreadBlock.open();
                    return;
                case 3:
                    UploadItem uploadItem = (UploadItem) message.obj;
                    int[] iArr = new int[1];
                    GLES20.glGenTextures(1, iArr, 0);
                    uploadItem.eglBitmap.bindTexture(iArr[0]);
                    GLUtils.texSubImage2D(3553, 0, 0, 0, uploadItem.bitmap);
                    GLES20.glDeleteTextures(1, iArr, 0);
                    Utils.glFinish();
                    EGLImageHandler.this.mEglThreadBlock.open();
                    return;
                default:
                    return;
            }
        }

        private void initEGL() {
            this.mEgl = (EGL10) EGLContext.getEGL();
            this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            if (this.mEglDisplay == EGL10.EGL_NO_DISPLAY) {
                throw new RuntimeException("eglGetDisplay failed");
            }
            int[] iArr = new int[2];
            if (this.mEgl.eglInitialize(this.mEglDisplay, iArr)) {
                Log.v(GLRenderManager.TAG, "EGL version: " + iArr[0] + '.' + iArr[1]);
                EGLImageHandler.this.mEglConfig = chooseConfig(this.mEgl, this.mEglDisplay, new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 0, 12325, 8, 12326, 0, 12344});
                this.mEglContext = this.mEgl.eglCreateContext(this.mEglDisplay, EGLImageHandler.this.mEglConfig, EGL10.EGL_NO_CONTEXT, new int[]{EGL_CONTEXT_CLIENT_VERSION, 2, 12344});
                if (this.mEglContext == null || this.mEglContext == EGL10.EGL_NO_CONTEXT) {
                    throw new RuntimeException("failed to createContext");
                }
                this.mEglSurface = this.mEgl.eglCreatePbufferSurface(this.mEglDisplay, EGLImageHandler.this.mEglConfig, new int[]{12375, 1, 12374, 1, 12344});
                if (this.mEglSurface == null || this.mEglSurface == EGL10.EGL_NO_SURFACE) {
                    throw new RuntimeException("failed to createWindowSurface");
                } else if (!this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext)) {
                    throw new RuntimeException("failed to eglMakeCurrent");
                } else {
                    return;
                }
            }
            throw new RuntimeException("eglInitialize failed");
        }

        private void release() {
            this.mEgl.eglDestroySurface(this.mEglDisplay, this.mEglSurface);
            this.mEgl.eglDestroyContext(this.mEglDisplay, this.mEglContext);
            this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            this.mEgl.eglTerminate(this.mEglDisplay);
            this.mEglSurface = null;
            this.mEglContext = null;
            this.mEglDisplay = null;
        }

        private EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, int[] iArr) {
            int[] iArr2 = new int[1];
            if (egl10.eglChooseConfig(eGLDisplay, iArr, null, 0, iArr2)) {
                int i = iArr2[0];
                if (i <= 0) {
                    throw new IllegalArgumentException("No configs match configSpec");
                }
                EGLConfig[] eGLConfigArr = new EGLConfig[i];
                if (egl10.eglChooseConfig(eGLDisplay, iArr, eGLConfigArr, i, iArr2)) {
                    return eGLConfigArr[0];
                }
                throw new IllegalArgumentException("eglChooseConfig#2 failed");
            }
            throw new IllegalArgumentException("eglChooseConfig failed");
        }
    }

    class UploadItem {
        public Bitmap bitmap;
        public EGLBitmap eglBitmap;

        public UploadItem(EGLBitmap eGLBitmap, Bitmap bitmap) {
            this.eglBitmap = eGLBitmap;
            this.bitmap = bitmap;
        }
    }

    private EGLImageHandler() {
        this.mEglThread.start();
        this.mEglHandler = new EGLHandler(this.mEglThread.getLooper());
        this.mEglThreadBlock.close();
        this.mEglHandler.sendEmptyMessage(0);
        this.mEglThreadBlock.block();
    }

    public static EGLImageHandler getInstance(boolean z) {
        if (z && sEGLUploader == null) {
            sEGLUploader = new EGLImageHandler();
        }
        return sEGLUploader;
    }

    public static EGLImageHandler getInstance() {
        return getInstance(true);
    }

    public static void releaseInstance() {
        if (sEGLUploader != null) {
            sEGLUploader.mEglThreadBlock.close();
            sEGLUploader.mEglHandler.sendEmptyMessage(1);
            sEGLUploader.mEglThreadBlock.block();
            sEGLUploader.mEglThread.quit();
            sEGLUploader = null;
        }
    }

    public void freeGLResource() {
        this.mEglThreadBlock.close();
        this.mEglHandler.obtainMessage(2);
        this.mEglThreadBlock.block();
    }

    public void setEGLBitmap(EGLBitmap eGLBitmap, Bitmap bitmap) {
        this.mEglThreadBlock.close();
        this.mEglHandler.obtainMessage(3, new UploadItem(eGLBitmap, bitmap)).sendToTarget();
        this.mEglThreadBlock.block();
    }
}

package com.meizu.common.renderer.functor;

import android.os.Build.VERSION;
import android.util.Log;
import com.meizu.common.renderer.effect.GLRenderManager;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;

public class InvokeFunctor extends DrawGLFunctor {
    public void invoke() {
        invokeFunctorInternal(true);
    }

    public void invoke(boolean z) {
        invokeFunctorInternal(z);
    }

    protected boolean invokeFunctorInternal(boolean z) {
        if (this.mNativeFunctor != 0) {
            if (VERSION.SDK_INT < 21) {
                if (EGL10.EGL_NO_CONTEXT.equals(((EGL10) EGLContext.getEGL()).eglGetCurrentContext())) {
                    Log.e(GLRenderManager.TAG, "invokeFunctor fail,sdk version = " + VERSION.SDK_INT);
                    return false;
                }
                onInvoke(1);
                return true;
            } else if (sMethod_invokeFunctor != null) {
                try {
                    sMethod_invokeFunctor.invoke(null, new Object[]{Long.valueOf(this.mNativeFunctor), Boolean.valueOf(z)});
                } catch (Exception e) {
                    Log.e(GLRenderManager.TAG, "invokeFunctor method doesn't exist" + e.getMessage());
                }
                return true;
            }
        }
        return false;
    }
}

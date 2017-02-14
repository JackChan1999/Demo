package com.meizu.common.renderer.effect;

import android.opengl.GLES20;
import java.nio.FloatBuffer;

public class ArrayBuffer implements GLResource {
    protected int[] mBufferID = new int[1];
    protected GLCanvas mGLCanvas;

    public ArrayBuffer(GLCanvas gLCanvas, FloatBuffer floatBuffer) {
        GLES20.glGenBuffers(1, this.mBufferID, 0);
        GLES20.glBindBuffer(34962, this.mBufferID[0]);
        GLES20.glBufferData(34962, floatBuffer.capacity() * 4, floatBuffer, 35044);
        this.mGLCanvas = gLCanvas;
    }

    public int getId() {
        return this.mBufferID[0];
    }

    public void freeGLResource() {
        if (this.mGLCanvas != null) {
            this.mGLCanvas.deleteBuffer(getId());
            this.mBufferID[0] = 0;
            this.mGLCanvas = null;
        }
    }

    public void bindSelf() {
        GLES20.glBindBuffer(34962, this.mBufferID[0]);
    }

    protected void finalize() {
        freeGLResource();
    }
}

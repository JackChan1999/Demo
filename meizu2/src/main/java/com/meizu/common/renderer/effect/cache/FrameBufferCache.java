package com.meizu.common.renderer.effect.cache;

import com.meizu.common.renderer.effect.EGLFrameBuffer;
import com.meizu.common.renderer.effect.FrameBuffer;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.GLRenderManager;
import com.meizu.common.renderer.effect.GLResource;
import java.util.Vector;

public class FrameBufferCache implements GLResource {
    private Vector<FrameBuffer> mCache;
    private GLCanvas mCanvas;
    private int mMaxSize;
    private int mSize;

    public FrameBufferCache(GLCanvas gLCanvas) {
        this(gLCanvas, GLRenderManager.sDefaultFrameBufferCacheSize);
    }

    public FrameBufferCache(GLCanvas gLCanvas, int i) {
        this.mCache = new Vector();
        this.mCanvas = gLCanvas;
        this.mMaxSize = i;
        this.mSize = 0;
    }

    public FrameBuffer get(int i, int i2, boolean z, boolean z2) {
        boolean isSupprotedEGLBitmap = z & GLRenderManager.isSupprotedEGLBitmap();
        int size = this.mCache.size() - 1;
        while (size >= 0) {
            if (((FrameBuffer) this.mCache.get(size)).getWidth() == i && ((FrameBuffer) this.mCache.get(size)).getHeight() == i2 && ((FrameBuffer) this.mCache.get(size)).isEGL() == isSupprotedEGLBitmap && ((FrameBuffer) this.mCache.get(size)).getDepth() == z2) {
                break;
            }
            size--;
        }
        size = -1;
        if (size == -1) {
            return isSupprotedEGLBitmap ? new EGLFrameBuffer(this.mCanvas, i, i2, z2) : new FrameBuffer(this.mCanvas, i, i2, z2);
        } else {
            FrameBuffer frameBuffer = (FrameBuffer) this.mCache.get(size);
            removeLocation(size);
            return frameBuffer;
        }
    }

    public FrameBuffer get(int i, int i2, boolean z) {
        return get(i, i2, false, z);
    }

    public FrameBuffer get(int i, int i2) {
        return get(i, i2, false);
    }

    public void put(FrameBuffer frameBuffer) {
        if (frameBuffer != null) {
            frameBuffer.resetTextureBounds();
            int size = this.mCache.size() - 1;
            while (size >= 0) {
                if (this.mCache.get(size) != frameBuffer) {
                    size--;
                } else {
                    return;
                }
            }
            addNew(frameBuffer);
            while (this.mSize > this.mMaxSize) {
                removeOldest();
            }
        }
    }

    private void addNew(FrameBuffer frameBuffer) {
        this.mCache.add(frameBuffer);
        this.mSize += frameBuffer.getSize();
    }

    private void removeLocation(int i) {
        this.mSize -= ((FrameBuffer) this.mCache.remove(i)).getSize();
    }

    private void removeOldest() {
        FrameBuffer frameBuffer = (FrameBuffer) this.mCache.remove(0);
        this.mSize -= frameBuffer.getSize();
        frameBuffer.freeGLResource();
    }

    public void freeGLResource() {
        for (int i = 0; i < this.mCache.size(); i++) {
            ((FrameBuffer) this.mCache.get(i)).freeGLResource();
        }
        this.mCache.clear();
        this.mSize = 0;
    }
}

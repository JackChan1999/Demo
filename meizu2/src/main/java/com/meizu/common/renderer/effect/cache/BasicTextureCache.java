package com.meizu.common.renderer.effect.cache;

import com.meizu.common.renderer.effect.GLES31Utils;
import com.meizu.common.renderer.effect.GLRenderManager;
import com.meizu.common.renderer.effect.GLResource;
import com.meizu.common.renderer.effect.texture.BasicTexture;
import com.meizu.common.renderer.effect.texture.RawTexture;
import com.meizu.common.renderer.effect.texture.StorageTexture;
import java.util.Vector;

public class BasicTextureCache implements GLResource {
    public static BasicTextureCache sIntance;
    private Vector<BasicTexture> mCache;
    private int mMaxSize;
    private int mSize;

    public static BasicTextureCache getInstance() {
        BasicTextureCache basicTextureCache;
        synchronized (BasicTextureCache.class) {
            if (sIntance == null) {
                sIntance = new BasicTextureCache();
            }
            basicTextureCache = sIntance;
        }
        return basicTextureCache;
    }

    public BasicTextureCache() {
        this(GLRenderManager.sDefaultTextureCacheSize);
    }

    public BasicTextureCache(int i) {
        this.mCache = new Vector();
        this.mMaxSize = i;
        this.mSize = 0;
    }

    public BasicTexture get(int i, int i2, int i3) {
        int size = this.mCache.size() - 1;
        while (size >= 0) {
            if (((BasicTexture) this.mCache.get(size)).getWidth() == i && ((BasicTexture) this.mCache.get(size)).getHeight() == i2 && ((BasicTexture) this.mCache.get(size)).getFormat() == i3) {
                break;
            }
            size--;
        }
        size = -1;
        if (size != -1) {
            BasicTexture basicTexture = (BasicTexture) this.mCache.get(size);
            removeLocation(size);
            return basicTexture;
        } else if (i3 == GLES31Utils.GL_RGBA16F || i3 == GLES31Utils.GL_RGBA32F) {
            return new StorageTexture(i, i2, i3);
        } else {
            return new RawTexture(i, i2, i3 == 6407);
        }
    }

    public BasicTexture get(int i, int i2) {
        return get(i, i2, 6408);
    }

    public BasicTexture get(int i, int i2, boolean z) {
        return get(i, i2, z ? 6407 : 6408);
    }

    public void put(BasicTexture basicTexture) {
        if (basicTexture != null) {
            basicTexture.resetBounds();
            int size = this.mCache.size() - 1;
            while (size >= 0) {
                if (this.mCache.get(size) != basicTexture) {
                    size--;
                } else {
                    return;
                }
            }
            addNew(basicTexture);
            while (this.mSize > this.mMaxSize) {
                removeOldest();
            }
        }
    }

    private void addNew(BasicTexture basicTexture) {
        this.mCache.add(basicTexture);
        this.mSize += basicTexture.getBytes();
    }

    private void removeLocation(int i) {
        this.mSize -= ((BasicTexture) this.mCache.remove(i)).getBytes();
    }

    private void removeOldest() {
        BasicTexture basicTexture = (BasicTexture) this.mCache.remove(0);
        this.mSize -= basicTexture.getBytes();
        basicTexture.freeGLResource();
    }

    public void freeGLResource() {
        for (int i = 0; i < this.mCache.size(); i++) {
            ((BasicTexture) this.mCache.get(i)).freeGLResource();
        }
        this.mCache.clear();
        this.mSize = 0;
    }
}

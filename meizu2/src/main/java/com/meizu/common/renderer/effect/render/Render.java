package com.meizu.common.renderer.effect.render;

import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.GLResource;
import com.meizu.common.renderer.effect.RenderInfo;

public abstract class Render implements GLResource {
    public static final String BLUR = "__blur";
    public static final String FISHEYE = "__fisheye";
    public static final String FOG = "__fog";
    public static final String GRAY = "__gray";
    public static final String MOSAIC = "__mosaic";
    public static final String NONE = "__none";
    public static final String SEVENTY = "__seventy";
    public static final String SKETCH = "__sketch";
    public static final String VIVID = "__vivid";
    public static final String WATER = "__water";
    public static final String YESTERDAY = "__yesterday";
    protected GLCanvas mGLCanvas;
    public String mKey = NONE;

    public abstract boolean draw(RenderInfo renderInfo);

    public Render(GLCanvas gLCanvas) {
        this.mGLCanvas = gLCanvas;
    }

    public String getKey() {
        return this.mKey;
    }

    protected void finalize() {
        try {
            freeGLResource();
        } finally {
            super.finalize();
        }
    }

    public void freeGLResource() {
    }
}

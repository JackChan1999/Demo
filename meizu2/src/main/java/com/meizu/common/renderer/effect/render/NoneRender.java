package com.meizu.common.renderer.effect.render;

import com.meizu.common.renderer.effect.GLCanvas;

public class NoneRender extends PixelsRender {
    public NoneRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mKey = Render.NONE;
    }
}

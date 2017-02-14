package com.meizu.common.renderer.effect;

import com.meizu.common.renderer.effect.element.Element;
import com.meizu.common.renderer.effect.render.Render;

public class RenderInfo {
    public int alpha;
    public boolean blend;
    public boolean clearFbo;
    public boolean cullFace;
    public boolean depthTest;
    public String effectKey;
    public Element element;
    public boolean flipProjV;
    public boolean flipTextureH;
    public boolean flipTextureV;
    public int viewportHeight;
    public int viewportWidth;

    public RenderInfo() {
        reset();
    }

    public void reset() {
        this.flipProjV = false;
        this.flipTextureV = false;
        this.flipTextureH = false;
        this.blend = false;
        this.depthTest = false;
        this.cullFace = false;
        this.clearFbo = false;
        this.alpha = 255;
        this.viewportWidth = 0;
        this.viewportHeight = 0;
        this.element = null;
        this.effectKey = Render.NONE;
    }
}

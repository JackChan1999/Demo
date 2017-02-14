package com.meizu.common.renderer.effect.render;

import com.meizu.common.renderer.effect.FrameBuffer;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.RenderInfo;
import com.meizu.common.renderer.effect.element.TextureElement;

public class ScaleProxyRender extends Render {
    private RenderInfo mEffectInfo;
    private float mScale;
    private TextureElement mTextureElement;

    public ScaleProxyRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mTextureElement = new TextureElement();
        this.mEffectInfo = new RenderInfo();
        this.mScale = 1.0f;
        this.mKey = Render.NONE;
    }

    public ScaleProxyRender setPrameter(String str, float f) {
        this.mKey = str;
        this.mScale = f;
        return this;
    }

    public ScaleProxyRender setPrameter(String str) {
        this.mKey = str;
        this.mScale = 1.0f;
        return this;
    }

    public boolean draw(RenderInfo renderInfo) {
        switch (renderInfo.element.getId()) {
            case 1:
                drawTexure(renderInfo);
                return true;
            default:
                return false;
        }
    }

    private void drawTexure(RenderInfo renderInfo) {
        if (this.mScale != 1.0f) {
            TextureElement textureElement = (TextureElement) renderInfo.element;
            int max = (int) Math.max(1.0f, ((float) textureElement.mWidth) * this.mScale);
            int max2 = (int) Math.max(1.0f, ((float) textureElement.mHeight) * this.mScale);
            this.mEffectInfo.flipProjV = false;
            this.mEffectInfo.viewportWidth = max;
            this.mEffectInfo.viewportHeight = max2;
            this.mEffectInfo.element = this.mTextureElement;
            FrameBuffer frameBuffer = this.mGLCanvas.getFrameBufferCache().get(max, max2);
            this.mGLCanvas.getState().push();
            this.mGLCanvas.getState().indentityModelM();
            this.mGLCanvas.getState().indentityTexM();
            this.mTextureElement.init(textureElement.mTexture, 0, 0, max, max2);
            this.mGLCanvas.getState().setFrameBufferId(frameBuffer.getId());
            this.mGLCanvas.getRender(this.mKey).draw(this.mEffectInfo);
            this.mGLCanvas.getState().pop();
            textureElement.mTexture = frameBuffer.getTexture();
            this.mGLCanvas.getRender(Render.NONE).draw(renderInfo);
            this.mGLCanvas.getFrameBufferCache().put(frameBuffer);
            textureElement.mTexture = null;
            this.mTextureElement.mTexture = null;
            return;
        }
        this.mGLCanvas.getRender(this.mKey).draw(renderInfo);
    }
}

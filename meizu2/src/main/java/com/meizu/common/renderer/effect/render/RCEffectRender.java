package com.meizu.common.renderer.effect.render;

import android.opengl.GLES20;
import com.meizu.common.renderer.effect.FrameBuffer;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.RenderInfo;
import com.meizu.common.renderer.effect.element.TextureElement;

public class RCEffectRender extends Render {
    public static final String ROUND_CORNER = "__round_corner";
    private RenderInfo mEffectInfo;
    private RCRender mRCRender;
    private TextureElement mTextureElement;

    static class RCRender extends ConvolutionRender {
        private static final String FRAG = "precision highp float;   \nvarying vec2 vTexCoord;   \nuniform float uAlpha;\nuniform sampler2D sTexture; \nuniform vec2 uStep; \nuniform float uRadius;\nbool isInRoundRect(float x, float y, float width, float height, float radius) { \n    if (x < radius && y < radius) { \n        return sqrt((x-radius)*(x-radius) + (y-radius)*(y-radius)) <= radius;\n    } else if ( x > (width - radius) && y < radius) { \n        return sqrt((x-(width - radius))*(x-(width - radius)) + (y-radius)*(y-radius)) <= radius;\n    } else if (x < radius && y > (height - radius)) { \n        return sqrt((x-radius)*(x-radius) + (y-(height - radius))*(y-(height - radius))) <= radius; \n    } else if (x > (width - radius) && y > (height - radius)) { \n        return sqrt((x-(width - radius))*(x-(width - radius)) + (y-(height - radius))*(y-(height - radius))) <= radius; \n    } else {\n        return true; \n    }\n    return true;\n} \nvoid main()  \n{   \n    if (!isInRoundRect(vTexCoord.x*uStep.x, vTexCoord.y*uStep.y, uStep.x, uStep.y, uRadius)) {        discard; \n        return; \n    }\n    gl_FragColor = texture2D(sTexture, vTexCoord); \n    gl_FragColor.a *= uAlpha;\n} \n";
        private float mRadius;
        protected int mUniformRadius;

        public RCRender(GLCanvas gLCanvas) {
            super(gLCanvas);
            this.mRadius = 20.0f;
            this.mKey = Render.NONE;
        }

        public String getFragmentShader() {
            return FRAG;
        }

        protected void initProgram() {
            super.initProgram();
            this.mUniformRadius = GLES20.glGetUniformLocation(this.mProgram, "uRadius");
        }

        public void setRadius(float f) {
            this.mRadius = f;
        }

        protected void initShader(RenderInfo renderInfo) {
            super.initShader(renderInfo);
            GLES20.glUniform2f(this.mUniformStepH, (float) renderInfo.element.mWidth, (float) renderInfo.element.mHeight);
            GLES20.glUniform1f(this.mUniformRadius, this.mRadius);
        }
    }

    public RCEffectRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mTextureElement = new TextureElement();
        this.mEffectInfo = new RenderInfo();
        this.mKey = ROUND_CORNER;
        this.mRCRender = new RCRender(gLCanvas);
    }

    public void setRadius(float f) {
        this.mRCRender.setRadius(f);
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
        if (renderInfo.effectKey.equals(Render.NONE)) {
            this.mRCRender.draw(renderInfo);
            return;
        }
        TextureElement textureElement = (TextureElement) renderInfo.element;
        this.mEffectInfo.viewportWidth = textureElement.mWidth;
        this.mEffectInfo.viewportHeight = textureElement.mHeight;
        this.mEffectInfo.element = this.mTextureElement;
        FrameBuffer frameBuffer = this.mGLCanvas.getFrameBufferCache().get(textureElement.mWidth, textureElement.mHeight);
        this.mGLCanvas.getState().push();
        this.mGLCanvas.getState().indentityModelM();
        this.mGLCanvas.getState().indentityTexM();
        this.mGLCanvas.getState().setFrameBufferId(frameBuffer.getId());
        this.mTextureElement.init(textureElement.mTexture, 0, 0, textureElement.mWidth, textureElement.mHeight);
        this.mGLCanvas.getRender(renderInfo.effectKey).draw(this.mEffectInfo);
        this.mGLCanvas.getState().pop();
        textureElement.mTexture = frameBuffer.getTexture();
        this.mRCRender.draw(renderInfo);
        this.mGLCanvas.getFrameBufferCache().put(frameBuffer);
        this.mTextureElement.mTexture = null;
        this.mEffectInfo.reset();
    }

    public void freeGLResource() {
        super.freeGLResource();
        this.mRCRender.freeGLResource();
    }
}

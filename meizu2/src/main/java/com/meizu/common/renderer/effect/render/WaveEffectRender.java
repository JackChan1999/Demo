package com.meizu.common.renderer.effect.render;

import android.opengl.GLES20;
import com.meizu.common.renderer.effect.FrameBuffer;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.RenderInfo;
import com.meizu.common.renderer.effect.element.TextureElement;

public class WaveEffectRender extends Render {
    public static final String WAVE = "__wave";
    private int mCols;
    private RenderInfo mEffectInfo;
    private int mProgress;
    private int mRows;
    private TextureElement mTextureElement;
    private WaveRender mWaveRender;

    class WaveRender extends MeshRender {
        private static final String VERTEX = "precision mediump float;   \nuniform mat4 uMVPMatrix;   \nuniform mat4 uSTMatrix;    \nuniform float uAngle;      \nattribute vec3 aPosition;  \nattribute vec2 aTexCoord;  \nvarying vec2 vTexCoord;    \nvoid main() {              \n    if ((abs(abs(aPosition.x) - 1.0) < 0.01 && abs(abs(aPosition.y) - 1.0) > 0.01) ||  \n        (abs(abs(aPosition.y) - 1.0) < 0.01 && abs(abs(aPosition.x) - 1.0) > 0.01) || \n        (abs(abs(aPosition.y) - 1.0) < 0.01 && abs(abs(aPosition.x) - 1.0) < 0.01)) { \n        gl_Position = uMVPMatrix * vec4(aPosition, 1);          \n    } else {  \n        float angleSpanH = 2.0*3.14159265;                 \n        float currAngle = uAngle + (aPosition.x + 1.0)*angleSpanH; \n        float tz = sin(currAngle)*0.1;      \n        gl_Position = uMVPMatrix * vec4(aPosition.x, aPosition.y, tz, 1); \n    }  \n    vTexCoord = (uSTMatrix * vec4(aTexCoord, 0, 1)).st;           \n} \n";
        private float mAngle = 0.0f;
        private int mUniformAngleH;

        public WaveRender(GLCanvas gLCanvas) {
            super(gLCanvas);
        }

        protected void initProgram() {
            super.initProgram();
            this.mUniformAngleH = GLES20.glGetUniformLocation(this.mProgram, "uAngle");
        }

        public void setProgress(int i) {
            this.mAngle = (((float) i) * 3.1415927f) / 16.0f;
        }

        protected void initShader(RenderInfo renderInfo) {
            super.initShader(renderInfo);
            GLES20.glUniform1f(this.mUniformAngleH, this.mAngle);
        }

        protected String getVertexShader() {
            return VERTEX;
        }
    }

    public WaveEffectRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mCols = 12;
        this.mRows = 12;
        this.mTextureElement = new TextureElement();
        this.mEffectInfo = new RenderInfo();
        this.mKey = WAVE;
        this.mWaveRender = new WaveRender(gLCanvas);
    }

    public void setProgress(int i) {
        this.mProgress = i;
    }

    public void setGrid(int i, int i2) {
        this.mCols = i;
        this.mRows = i2;
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
        TextureElement textureElement = (TextureElement) renderInfo.element;
        int i = textureElement.mWidth;
        int i2 = textureElement.mHeight;
        FrameBuffer frameBuffer = this.mGLCanvas.getFrameBufferCache().get(i, i2, true);
        this.mTextureElement.init(textureElement.mTexture, 0, 0, i, i2);
        this.mEffectInfo.clearFbo = true;
        this.mEffectInfo.depthTest = true;
        this.mEffectInfo.viewportWidth = i;
        this.mEffectInfo.viewportHeight = i2;
        this.mEffectInfo.element = this.mTextureElement;
        this.mGLCanvas.getState().push();
        this.mGLCanvas.getState().indentityModelM();
        this.mGLCanvas.getState().indentityTexM();
        this.mGLCanvas.getState().setFrameBufferId(frameBuffer.getId());
        this.mWaveRender.setGrid(this.mCols, this.mRows);
        this.mWaveRender.setProgress(this.mProgress);
        this.mWaveRender.draw(this.mEffectInfo);
        this.mGLCanvas.getState().pop();
        this.mProgress = 0;
        this.mTextureElement.mTexture = null;
        this.mEffectInfo.reset();
        textureElement.mTexture = frameBuffer.getTexture();
        this.mGLCanvas.getRender(renderInfo.effectKey).draw(renderInfo);
        this.mGLCanvas.getFrameBufferCache().put(frameBuffer);
    }

    public void freeGLResource() {
        this.mWaveRender.freeGLResource();
    }
}

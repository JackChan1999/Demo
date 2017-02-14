package com.meizu.common.renderer.effect.render;

import android.opengl.GLES20;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.RenderInfo;
import com.meizu.common.renderer.effect.ShaderUtil;
import com.meizu.common.renderer.effect.element.TextureElement;
import java.nio.FloatBuffer;

public abstract class PixelsRender extends ShaderRender {
    private static final float[] TEXTURES = new float[]{0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};
    private static FloatBuffer TEXTURES_BUFFER;
    private static final float[] VERTICES = new float[]{0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f};
    private static FloatBuffer VERTICES_BUFFER;

    static {
        init();
    }

    public PixelsRender(GLCanvas gLCanvas) {
        super(gLCanvas);
    }

    public void initProgram() {
        this.mProgram = ShaderUtil.createProgram(getVertexShader(), getFragmentShader());
        if (this.mProgram != 0) {
            GLES20.glUseProgram(this.mProgram);
            this.mUniformMVPMatrixH = GLES20.glGetUniformLocation(this.mProgram, "uMVPMatrix");
            this.mUniformSTMatrixH = GLES20.glGetUniformLocation(this.mProgram, "uSTMatrix");
            this.mUniformAlphaH = GLES20.glGetUniformLocation(this.mProgram, "uAlpha");
            this.mUniformTextureH = GLES20.glGetUniformLocation(this.mProgram, "sTexture");
            this.mAttributePositionH = GLES20.glGetAttribLocation(this.mProgram, "aPosition");
            this.mAttributeTexCoorH = GLES20.glGetAttribLocation(this.mProgram, "aTexCoord");
            return;
        }
        throw new IllegalArgumentException(getClass() + ": mProgram = 0");
    }

    public boolean draw(RenderInfo renderInfo) {
        switch (renderInfo.element.getId()) {
            case 1:
                drawBasicTexture(renderInfo, (TextureElement) renderInfo.element);
                return true;
            default:
                return false;
        }
    }

    public void initShader(RenderInfo renderInfo) {
        GLES20.glVertexAttribPointer(this.mAttributePositionH, 3, 5126, false, 12, getVertexBuffer());
        GLES20.glVertexAttribPointer(this.mAttributeTexCoorH, 2, 5126, false, 8, getTextureBuffer());
        GLES20.glUniformMatrix4fv(this.mUniformMVPMatrixH, 1, false, this.mGLCanvas.getState().getFinalMatrix(), 0);
        GLES20.glUniformMatrix4fv(this.mUniformSTMatrixH, 1, false, this.mGLCanvas.getState().getTexMaxtrix(), 0);
        GLES20.glUniform1i(this.mUniformTextureH, 0);
        GLES20.glUniform1f(this.mUniformAlphaH, ((float) renderInfo.alpha) / 255.0f);
        GLES20.glEnableVertexAttribArray(this.mAttributePositionH);
        GLES20.glEnableVertexAttribArray(this.mAttributeTexCoorH);
    }

    protected void drawBasicTexture(RenderInfo renderInfo, TextureElement textureElement) {
        GLES20.glUseProgram(this.mProgram);
        if (textureElement.mTexture.onBind(this.mGLCanvas)) {
            ShaderRender.bindTexture(textureElement.mTexture, 33984);
            onPreDraw(renderInfo);
            textureElement.mTexture.updateTransformMatrix(this.mGLCanvas, renderInfo.flipTextureH, renderInfo.flipTextureV);
            if (!(textureElement.mX == 0 && textureElement.mY == 0)) {
                this.mGLCanvas.getState().translate((float) textureElement.mX, (float) textureElement.mY, 0.0f);
            }
            this.mGLCanvas.getState().scale((float) textureElement.mWidth, (float) textureElement.mHeight, 1.0f);
            initShader(renderInfo);
            GLES20.glDrawArrays(5, 0, 4);
            onPostDraw(renderInfo);
        }
    }

    protected FloatBuffer getVertexBuffer() {
        return VERTICES_BUFFER;
    }

    protected FloatBuffer getTextureBuffer() {
        return TEXTURES_BUFFER;
    }

    private static void init() {
        VERTICES_BUFFER = ShaderRender.allocateByteBuffer((VERTICES.length * 32) / 8).asFloatBuffer();
        VERTICES_BUFFER.put(VERTICES);
        VERTICES_BUFFER.position(0);
        TEXTURES_BUFFER = ShaderRender.allocateByteBuffer((TEXTURES.length * 32) / 8).asFloatBuffer();
        TEXTURES_BUFFER.put(TEXTURES);
        TEXTURES_BUFFER.position(0);
    }
}

package com.meizu.common.renderer.effect.render;

import android.opengl.GLES20;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.GroundOverlayOptions;
import com.meizu.common.renderer.effect.FrameBuffer;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.RenderInfo;
import com.meizu.common.renderer.effect.element.TextureElement;
import java.nio.FloatBuffer;

public class CubeEffectRender extends Render {
    public static final String CUBE = "__cube";
    private int mAngleX;
    private int mAngleY;
    private int mAngleZ;
    private CubeRender mCubeRender;
    private RenderInfo mEffectInfo;
    private TextureElement mTextureElement;

    class CubeRender extends PixelsRender {
        private static final float UNIT_SIZE = 2.0f;
        private boolean mDrawTopBottom = true;
        private FloatBuffer mTextureBuffer;
        private FloatBuffer mVertexBuffer;

        public CubeRender(GLCanvas gLCanvas) {
            super(gLCanvas);
            initMesh();
        }

        public void drawTopottom(boolean z) {
            this.mDrawTopBottom = z;
        }

        public boolean draw(RenderInfo renderInfo) {
            switch (renderInfo.element.getId()) {
                case 1:
                    TextureElement textureElement = (TextureElement) renderInfo.element;
                    onPreDraw(renderInfo);
                    if (!textureElement.mTexture.onBind(this.mGLCanvas)) {
                        return false;
                    }
                    ShaderRender.bindTexture(textureElement.mTexture, 33984);
                    textureElement.mTexture.updateTransformMatrix(this.mGLCanvas, renderInfo.flipTextureH, renderInfo.flipTextureV);
                    this.mGLCanvas.getState().push();
                    this.mGLCanvas.getState().translate(0.0f, 0.0f, 0.99f);
                    drawSelf(renderInfo);
                    this.mGLCanvas.getState().pop();
                    this.mGLCanvas.getState().push();
                    this.mGLCanvas.getState().translate(0.0f, 0.0f, -0.99f);
                    this.mGLCanvas.getState().rotate(BitmapDescriptorFactory.HUE_CYAN, 0.0f, 1.0f, 0.0f);
                    drawSelf(renderInfo);
                    this.mGLCanvas.getState().pop();
                    this.mGLCanvas.getState().push();
                    this.mGLCanvas.getState().translate(-0.99f, 0.0f, 0.0f);
                    this.mGLCanvas.getState().rotate(-90.0f, 0.0f, 1.0f, 0.0f);
                    drawSelf(renderInfo);
                    this.mGLCanvas.getState().pop();
                    this.mGLCanvas.getState().push();
                    this.mGLCanvas.getState().translate(0.99f, 0.0f, 0.0f);
                    this.mGLCanvas.getState().rotate(90.0f, 0.0f, 1.0f, 0.0f);
                    drawSelf(renderInfo);
                    this.mGLCanvas.getState().pop();
                    if (this.mDrawTopBottom) {
                        this.mGLCanvas.getState().push();
                        this.mGLCanvas.getState().translate(0.0f, 0.99f, 0.0f);
                        this.mGLCanvas.getState().rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                        drawSelf(renderInfo);
                        this.mGLCanvas.getState().pop();
                        this.mGLCanvas.getState().push();
                        this.mGLCanvas.getState().translate(0.0f, -0.99f, 0.0f);
                        this.mGLCanvas.getState().rotate(90.0f, 1.0f, 0.0f, 0.0f);
                        drawSelf(renderInfo);
                        this.mGLCanvas.getState().pop();
                    }
                    onPostDraw(renderInfo);
                    return true;
                default:
                    return false;
            }
        }

        protected FloatBuffer getVertexBuffer() {
            return this.mVertexBuffer;
        }

        protected FloatBuffer getTextureBuffer() {
            return this.mTextureBuffer;
        }

        protected void drawSelf(RenderInfo renderInfo) {
            GLES20.glUseProgram(this.mProgram);
            initShader(renderInfo);
            GLES20.glDrawArrays(4, 0, 6);
        }

        protected void updateViewport(RenderInfo renderInfo) {
            GLES20.glViewport(0, 0, renderInfo.viewportWidth, renderInfo.viewportHeight);
            this.mGLCanvas.getState().frustumM(GroundOverlayOptions.NO_DIMENSION / 4.0f, 1.0f / 4.0f, GroundOverlayOptions.NO_DIMENSION / 4.0f, 1.0f / 4.0f, 1.0f, 100.0f);
            this.mGLCanvas.getState().setLookAt(0.0f, 0.0f, 1.0f + 4.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        }

        private void initMesh() {
            float[] fArr = new float[]{GroundOverlayOptions.NO_DIMENSION, GroundOverlayOptions.NO_DIMENSION, 0.0f, GroundOverlayOptions.NO_DIMENSION + 2.0f, GroundOverlayOptions.NO_DIMENSION, 0.0f, GroundOverlayOptions.NO_DIMENSION, GroundOverlayOptions.NO_DIMENSION + 2.0f, 0.0f, GroundOverlayOptions.NO_DIMENSION, GroundOverlayOptions.NO_DIMENSION + 2.0f, 0.0f, GroundOverlayOptions.NO_DIMENSION + 2.0f, GroundOverlayOptions.NO_DIMENSION, 0.0f, GroundOverlayOptions.NO_DIMENSION + 2.0f, GroundOverlayOptions.NO_DIMENSION + 2.0f, 0.0f};
            float[] fArr2 = new float[]{0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};
            this.mVertexBuffer = ShaderRender.allocateByteBuffer(fArr.length * 4).asFloatBuffer();
            this.mVertexBuffer.put(fArr);
            this.mVertexBuffer.position(0);
            this.mTextureBuffer = ShaderRender.allocateByteBuffer(fArr2.length * 4).asFloatBuffer();
            this.mTextureBuffer.put(fArr2);
            this.mTextureBuffer.position(0);
        }
    }

    public CubeEffectRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mTextureElement = new TextureElement();
        this.mEffectInfo = new RenderInfo();
        this.mKey = CUBE;
        this.mCubeRender = new CubeRender(gLCanvas);
    }

    public void drawTopottom(boolean z) {
        this.mCubeRender.drawTopottom(z);
    }

    public void setAngleX(int i) {
        this.mAngleX = i;
    }

    public void setAngleY(int i) {
        this.mAngleY = i;
    }

    public void setAngleZ(int i) {
        this.mAngleZ = i;
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
        this.mEffectInfo.cullFace = true;
        this.mEffectInfo.depthTest = true;
        this.mEffectInfo.viewportWidth = i;
        this.mEffectInfo.viewportHeight = i2;
        this.mEffectInfo.element = this.mTextureElement;
        this.mGLCanvas.getState().push();
        this.mGLCanvas.getState().indentityModelM();
        this.mGLCanvas.getState().indentityTexM();
        if (this.mAngleX != 0) {
            this.mGLCanvas.getState().rotate((float) this.mAngleX, 1.0f, 0.0f, 0.0f);
        }
        if (this.mAngleY != 0) {
            this.mGLCanvas.getState().rotate((float) this.mAngleY, 0.0f, 1.0f, 0.0f);
        }
        if (this.mAngleZ != 0) {
            this.mGLCanvas.getState().rotate((float) this.mAngleZ, 0.0f, 0.0f, 1.0f);
        }
        this.mGLCanvas.getState().setFrameBufferId(frameBuffer.getId());
        this.mCubeRender.draw(this.mEffectInfo);
        this.mGLCanvas.getState().pop();
        this.mTextureElement.mTexture = null;
        this.mEffectInfo.reset();
        textureElement.mTexture = frameBuffer.getTexture();
        this.mGLCanvas.getRender(renderInfo.effectKey).draw(renderInfo);
        this.mGLCanvas.getFrameBufferCache().put(frameBuffer);
    }

    public void freeGLResource() {
        this.mCubeRender.freeGLResource();
    }
}

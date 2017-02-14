package com.meizu.common.renderer.effect.render;

import android.opengl.GLES20;
import com.amap.api.maps2d.model.GroundOverlayOptions;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.RenderInfo;
import com.meizu.common.renderer.effect.element.TextureElement;
import com.meizu.common.widget.CircleProgressBar;
import java.nio.FloatBuffer;

public abstract class MeshRender extends PixelsRender {
    private final float UNIT_SIZE = CircleProgressBar.BAR_WIDTH_DEF_DIP;
    private int mCols = 12;
    private int mRows = 12;
    private FloatBuffer mTextureBuffer;
    private FloatBuffer mVertexBuffer;
    private int mVertexCount;

    public MeshRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        initMesh();
    }

    public void setGrid(int i, int i2) {
        if (this.mCols != i || this.mRows != i2) {
            this.mCols = i;
            this.mRows = i2;
            initMesh();
        }
    }

    protected void drawBasicTexture(RenderInfo renderInfo, TextureElement textureElement) {
        GLES20.glUseProgram(this.mProgram);
        if (textureElement.mTexture.onBind(this.mGLCanvas)) {
            ShaderRender.bindTexture(textureElement.mTexture, 33984);
            onPreDraw(renderInfo);
            textureElement.mTexture.updateTransformMatrix(this.mGLCanvas, renderInfo.flipTextureH, renderInfo.flipTextureV);
            initShader(renderInfo);
            GLES20.glDrawArrays(4, 0, this.mVertexCount);
            onPostDraw(renderInfo);
        }
    }

    protected void updateViewport(RenderInfo renderInfo) {
        GLES20.glViewport(0, 0, renderInfo.viewportWidth, renderInfo.viewportHeight);
        if (renderInfo.flipProjV) {
            this.mGLCanvas.getState().frustumM(GroundOverlayOptions.NO_DIMENSION / 4.0f, 1.0f / 4.0f, 1.0f / 4.0f, GroundOverlayOptions.NO_DIMENSION / 4.0f, 1.0f, 100.0f);
        } else {
            this.mGLCanvas.getState().frustumM(GroundOverlayOptions.NO_DIMENSION / 4.0f, 1.0f / 4.0f, GroundOverlayOptions.NO_DIMENSION / 4.0f, 1.0f / 4.0f, 1.0f, 100.0f);
        }
        this.mGLCanvas.getState().setLookAt(0.0f, 0.0f, 4.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    }

    protected FloatBuffer getVertexBuffer() {
        return this.mVertexBuffer;
    }

    protected FloatBuffer getTextureBuffer() {
        return this.mTextureBuffer;
    }

    private void initMesh() {
        int i;
        float f;
        float f2 = CircleProgressBar.BAR_WIDTH_DEF_DIP / ((float) this.mCols);
        float f3 = CircleProgressBar.BAR_WIDTH_DEF_DIP / ((float) this.mRows);
        this.mVertexCount = (this.mCols * this.mRows) * 6;
        float[] fArr = new float[(this.mVertexCount * 3)];
        int i2 = 0;
        for (i = 0; i < this.mRows; i++) {
            int i3 = 0;
            while (i3 < this.mCols) {
                float f4 = (((float) i3) * f2) + GroundOverlayOptions.NO_DIMENSION;
                f = (((float) i) * f3) + GroundOverlayOptions.NO_DIMENSION;
                int i4 = i2 + 1;
                fArr[i2] = f4;
                i2 = i4 + 1;
                fArr[i4] = f;
                i4 = i2 + 1;
                fArr[i2] = 0.0f;
                i2 = i4 + 1;
                fArr[i4] = f4;
                i4 = i2 + 1;
                fArr[i2] = f + f3;
                i2 = i4 + 1;
                fArr[i4] = 0.0f;
                i4 = i2 + 1;
                fArr[i2] = f4 + f2;
                i2 = i4 + 1;
                fArr[i4] = f;
                i4 = i2 + 1;
                fArr[i2] = 0.0f;
                i2 = i4 + 1;
                fArr[i4] = f4 + f2;
                i4 = i2 + 1;
                fArr[i2] = f;
                i2 = i4 + 1;
                fArr[i4] = 0.0f;
                i4 = i2 + 1;
                fArr[i2] = f4;
                i2 = i4 + 1;
                fArr[i4] = f + f3;
                i4 = i2 + 1;
                fArr[i2] = 0.0f;
                i2 = i4 + 1;
                fArr[i4] = f4 + f2;
                i4 = i2 + 1;
                fArr[i2] = f + f3;
                int i5 = i4 + 1;
                fArr[i4] = 0.0f;
                i3++;
                i2 = i5;
            }
        }
        float[] fArr2 = new float[(this.mVertexCount * 2)];
        f3 = 1.0f / ((float) this.mCols);
        f = 1.0f / ((float) this.mRows);
        i2 = 0;
        for (i = 0; i < this.mRows; i++) {
            i3 = 0;
            while (i3 < this.mCols) {
                f4 = ((float) i3) * f3;
                float f5 = ((float) i) * f;
                i4 = i2 + 1;
                fArr2[i2] = f4;
                i2 = i4 + 1;
                fArr2[i4] = f5;
                i4 = i2 + 1;
                fArr2[i2] = f4;
                i2 = i4 + 1;
                fArr2[i4] = f5 + f;
                i4 = i2 + 1;
                fArr2[i2] = f4 + f3;
                i2 = i4 + 1;
                fArr2[i4] = f5;
                i4 = i2 + 1;
                fArr2[i2] = f4 + f3;
                i2 = i4 + 1;
                fArr2[i4] = f5;
                i4 = i2 + 1;
                fArr2[i2] = f4;
                i2 = i4 + 1;
                fArr2[i4] = f5 + f;
                i4 = i2 + 1;
                fArr2[i2] = f4 + f3;
                i5 = i4 + 1;
                fArr2[i4] = f5 + f;
                i3++;
                i2 = i5;
            }
        }
        this.mVertexBuffer = ShaderRender.allocateByteBuffer(fArr.length * 4).asFloatBuffer();
        this.mVertexBuffer.put(fArr);
        this.mVertexBuffer.position(0);
        this.mTextureBuffer = ShaderRender.allocateByteBuffer(fArr2.length * 4).asFloatBuffer();
        this.mTextureBuffer.put(fArr2);
        this.mTextureBuffer.position(0);
    }
}

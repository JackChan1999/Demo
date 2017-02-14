package com.meizu.common.renderer.effect.render;

import android.opengl.GLES20;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.RenderInfo;
import com.meizu.common.renderer.effect.texture.Texture;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class ShaderRender extends Render {
    private static final String FRAG = "precision mediump float;\nuniform sampler2D sTexture;\nuniform float uAlpha;\nvarying vec2 vTexCoord;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTexCoord);\n    gl_FragColor.a *= uAlpha; \n}\n";
    protected static final float OPAQUE_ALPHA = 0.95f;
    private static final String VERTEX = "uniform mat4 uMVPMatrix; \nuniform mat4 uSTMatrix;\nattribute vec3 aPosition;\nattribute vec2 aTexCoord;\nvarying vec2 vTexCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * vec4(aPosition,1);\n    vTexCoord = (uSTMatrix * vec4(aTexCoord,0,1)).st;\n}";
    protected int mAttributePositionH;
    protected int mAttributeTexCoorH;
    protected int mCurrentFbo;
    protected boolean mIsBlend;
    protected boolean mIsCullFace;
    protected boolean mIsDepthTest;
    protected boolean mIsScissor;
    public int mProgram = 0;
    protected int mUniformAlphaH;
    protected int mUniformMVPMatrixH;
    protected int mUniformSTMatrixH;
    protected int mUniformTextureH;

    public abstract void initProgram();

    public abstract void initShader(RenderInfo renderInfo);

    public ShaderRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        initProgram();
    }

    public static boolean isOpaque(int i) {
        return (i >>> 24) == 255;
    }

    public static void bindTexture(Texture texture, int i) {
        GLES20.glActiveTexture(i);
        GLES20.glBindTexture(texture.getTarget(), texture.getId());
    }

    public static void bindTexture(Texture texture) {
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(texture.getTarget(), texture.getId());
    }

    public static void bindTexture(int i, int i2) {
        GLES20.glActiveTexture(i2);
        GLES20.glBindTexture(3553, i);
    }

    public static void bindTexture(int i) {
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, i);
    }

    protected void onPreDraw(RenderInfo renderInfo) {
        this.mGLCanvas.getState().push();
        updateViewport(renderInfo);
        this.mCurrentFbo = this.mGLCanvas.getState().getFrameBufferId();
        GLES20.glBindFramebuffer(36160, this.mCurrentFbo);
        this.mIsBlend = GLES20.glIsEnabled(3042);
        this.mIsCullFace = GLES20.glIsEnabled(2884);
        this.mIsDepthTest = GLES20.glIsEnabled(2929);
        this.mIsScissor = GLES20.glIsEnabled(3089);
        if (renderInfo.blend) {
            GLES20.glEnable(3042);
        } else {
            GLES20.glDisable(3042);
        }
        if (renderInfo.cullFace) {
            GLES20.glEnable(2884);
        } else {
            GLES20.glDisable(2884);
        }
        if (renderInfo.depthTest) {
            GLES20.glEnable(2929);
        } else {
            GLES20.glDisable(2929);
        }
        if (this.mCurrentFbo != this.mGLCanvas.getTargetFrameBufferId()) {
            GLES20.glDisable(3089);
            if (renderInfo.clearFbo) {
                GLES20.glClear(16640);
            }
        }
    }

    protected void onPostDraw(RenderInfo renderInfo) {
        if (this.mIsCullFace) {
            GLES20.glEnable(2884);
        } else {
            GLES20.glDisable(2884);
        }
        if (this.mIsDepthTest) {
            GLES20.glEnable(2929);
        } else {
            GLES20.glDisable(2929);
        }
        if (this.mIsScissor) {
            GLES20.glEnable(3089);
        } else {
            GLES20.glDisable(3089);
        }
        this.mGLCanvas.getState().pop();
    }

    protected void updateViewport(RenderInfo renderInfo) {
        int i = renderInfo.viewportWidth;
        int i2 = renderInfo.viewportHeight;
        GLES20.glViewport(0, 0, i, i2);
        this.mGLCanvas.getState().indentityViewM();
        if (renderInfo.flipProjV) {
            this.mGLCanvas.getState().orthoM(0.0f, (float) i, (float) i2, 0.0f);
        } else {
            this.mGLCanvas.getState().orthoM(0.0f, (float) i, 0.0f, (float) i2);
        }
    }

    public void freeGLResource() {
        if (!(this.mProgram == 0 || this.mGLCanvas == null)) {
            this.mGLCanvas.deleteProgram(this.mProgram);
            this.mProgram = 0;
            this.mKey = null;
            this.mGLCanvas = null;
        }
        super.freeGLResource();
    }

    protected String getVertexShader() {
        return VERTEX;
    }

    public String getFragmentShader() {
        return FRAG;
    }

    public static ByteBuffer allocateByteBuffer(int i) {
        return ByteBuffer.allocateDirect(i).order(ByteOrder.nativeOrder());
    }
}

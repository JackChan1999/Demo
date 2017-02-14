package com.meizu.common.renderer.effect.render;

import android.opengl.GLES20;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.RenderInfo;
import com.meizu.common.renderer.effect.texture.Texture;

public class ETC1Render extends PixelsRender {
    public static final String ETC1 = "__etc1";
    private static final String FRAG = "precision mediump float; \nuniform int uHasAlpha; \nuniform float uAlpha;\nuniform sampler2D sTexture; \nuniform sampler2D sAlphaTexture; \nvarying vec2 vTexCoord; \nvoid main() { \n    gl_FragColor = texture2D(sTexture, vTexCoord); \n    gl_FragColor.a = uAlpha; \n    if (uHasAlpha == 1) gl_FragColor.a *= texture2D(sAlphaTexture, vTexCoord).r;}";
    private Texture mAlphaTexture;
    protected int mUniformHasAlphaH;
    protected int mUniformTextureH2;

    public ETC1Render(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mKey = ETC1;
    }

    public void setAlphaTexture(Texture texture) {
        this.mAlphaTexture = texture;
    }

    protected void initProgram() {
        super.initProgram();
        this.mUniformTextureH2 = GLES20.glGetUniformLocation(this.mProgram, "sAlphaTexture");
        this.mUniformHasAlphaH = GLES20.glGetUniformLocation(this.mProgram, "uHasAlpha");
    }

    protected void initShader(RenderInfo renderInfo) {
        super.initShader(renderInfo);
        if (this.mAlphaTexture != null) {
            GLES20.glUniform1i(this.mUniformTextureH2, 1);
            GLES20.glUniform1i(this.mUniformHasAlphaH, 1);
            ShaderRender.bindTexture(this.mAlphaTexture, 33985);
            return;
        }
        GLES20.glUniform1i(this.mUniformHasAlphaH, 0);
    }

    public String getFragmentShader() {
        return FRAG;
    }
}

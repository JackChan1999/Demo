package com.meizu.common.renderer.effect.render;

import android.opengl.GLES20;
import com.meizu.common.renderer.effect.GLCanvas;

public abstract class ConvolutionRender extends PixelsRender {
    protected float mStepX;
    protected float mStepY;
    public int mUniformStepH;

    public ConvolutionRender(GLCanvas gLCanvas) {
        super(gLCanvas);
    }

    public void initProgram() {
        super.initProgram();
        this.mUniformStepH = GLES20.glGetUniformLocation(this.mProgram, "uStep");
    }
}

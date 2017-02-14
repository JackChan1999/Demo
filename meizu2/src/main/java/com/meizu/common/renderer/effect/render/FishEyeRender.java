package com.meizu.common.renderer.effect.render;

import android.opengl.GLES20;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.RenderInfo;
import com.meizu.common.widget.CircleProgressBar;

public class FishEyeRender extends ConvolutionRender {
    private static final String FRAG = "precision highp float; \nuniform sampler2D sTexture; \nuniform float uAlpha;\nvarying vec2 vTexCoord; \nuniform vec2 uStep; \nuniform float uInvMaxDist; \nuniform float uF; \nvec3 fisheye() { \n    const float slope = 20.0;               // vignette slope  \n    const float shade = 0.85;               // vignette shading  \n    const float range = 0.6;               // 0.6 - 1.3 \n    const float zoom = 0.3;               // smaller zoom means bigger image \n    vec2 coord = (vTexCoord - 0.5) / uStep; // convert to world coordinate  \n    float dist = length(coord); // distance to the center \n    float lumen = shade / (1.0 + exp((dist * uInvMaxDist - range) * slope)) + (1.0 - shade); \n    float t = zoom*dist/uF; \n    float theta = asin(t)*2.0; \n    float r = uF * tan(theta); \n    float angle = atan(coord.y, coord.x); \n    vec2 newCoord = vec2(cos(angle), sin(angle))*uStep*r+0.5; \n    return texture2D(sTexture, newCoord).rgb;  \n   // return texture2D(sTexture, newCoord).rgb * lumen; \n} \nvoid main() { \n    gl_FragColor.rgb = fisheye(); \n    gl_FragColor.a = texture2D(sTexture,vTexCoord).a*uAlpha; \n}";
    private float mF;
    private float mInvMaxDist;
    private int mTexH;
    private int mTexW;
    private int mUniformFH;
    private int mUniformInvMaxDistH;

    public FishEyeRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mKey = Render.FISHEYE;
    }

    public String getFragmentShader() {
        return FRAG;
    }

    protected void initProgram() {
        super.initProgram();
        this.mUniformFH = GLES20.glGetUniformLocation(this.mProgram, "uF");
        this.mUniformInvMaxDistH = GLES20.glGetUniformLocation(this.mProgram, "uInvMaxDist");
    }

    private void update(int i, int i2) {
        if (this.mTexW != i || this.mTexH != i2) {
            this.mTexW = i;
            this.mTexH = i2;
            if (i <= i2) {
                i2 = i;
            }
            float sqrt = (float) Math.sqrt((double) ((this.mTexW * this.mTexW) + (this.mTexH * this.mTexH)));
            if (i2 > 1080) {
                this.mStepX = 2.5f / ((float) this.mTexW);
                this.mStepY = 2.5f / ((float) this.mTexH);
                this.mF = (6.0f * sqrt) / 35.0f;
            } else if (i2 > 720) {
                this.mStepX = 1.5f / ((float) this.mTexW);
                this.mStepY = 1.5f / ((float) this.mTexH);
                this.mF = (7.0f * sqrt) / 35.0f;
            } else {
                this.mStepX = 1.0f / ((float) this.mTexW);
                this.mStepY = 1.0f / ((float) this.mTexH);
                this.mF = (10.0f * sqrt) / 35.0f;
            }
            this.mInvMaxDist = CircleProgressBar.BAR_WIDTH_DEF_DIP / sqrt;
        }
    }

    protected void initShader(RenderInfo renderInfo) {
        super.initShader(renderInfo);
        update(renderInfo.element.mWidth, renderInfo.element.mHeight);
        GLES20.glUniform1f(this.mUniformFH, this.mF);
        GLES20.glUniform1f(this.mUniformInvMaxDistH, this.mInvMaxDist);
        GLES20.glUniform2f(this.mUniformStepH, this.mStepX, this.mStepY);
    }
}

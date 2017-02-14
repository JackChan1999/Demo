package com.meizu.common.renderer.effect.render;

import android.opengl.GLES20;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.RenderInfo;

public class MosaicRender extends ConvolutionRender {
    private static final String FRAG = "precision highp float; \nuniform sampler2D sTexture; \nuniform float uAlpha;\nuniform vec2 uStep; \nvarying vec2 vTexCoord; \nvec3 mosaic() { \n    vec2 step = uStep; \n    vec2 st0 = (step.x < step.y) ? \n                vec2(0.02, 0.02 * step.y/step.x) : \n                vec2(0.02*step.x/step.y, 0.02); \n    vec2 st = floor(vTexCoord/st0) * st0; \n    vec2 st1 = st + st0*0.5; \n    return 0.25 * (texture2D(sTexture, st).rgb + \n             texture2D(sTexture, st1).rgb + \n             texture2D(sTexture, vec2(st.s,st1.t)).rgb + \n             texture2D(sTexture, vec2(st1.s,st.t)).rgb); \n} \nvoid main() \n{             \n  gl_FragColor.rgb = mosaic(); \n  gl_FragColor.a = uAlpha;\n}";

    public MosaicRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mKey = Render.MOSAIC;
    }

    public String getFragmentShader() {
        return FRAG;
    }

    protected void initShader(RenderInfo renderInfo) {
        super.initShader(renderInfo);
        GLES20.glUniform2f(this.mUniformStepH, 1.0f / ((float) renderInfo.element.mWidth), 1.0f / ((float) renderInfo.element.mHeight));
    }
}

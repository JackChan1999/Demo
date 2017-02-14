package com.meizu.common.renderer.effect.render;

import android.graphics.Color;
import android.opengl.GLES20;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.RenderInfo;

public class BlurFilterRender extends PixelsRender {
    private static final String FRAG = "precision mediump float;\nuniform sampler2D sTexture;\nuniform float uAlpha;\nuniform float uIntensity;\nuniform vec4 uFilterColor;\nvarying vec2 vTexCoord; \nvoid main() { \n    vec4 color = texture2D(sTexture,  vTexCoord)*uIntensity;\n    gl_FragColor.rgb = uFilterColor.rgb*uFilterColor.a + color.rgb*(1.0-uFilterColor.a);\n    gl_FragColor.a = uAlpha;\n}\n";
    public static final String KEY = "blur_filter";
    private int mFilterColor;
    protected float mIntensity;
    protected int mUniformFilterColor;
    protected int mUniformIntensityH;

    public static BlurFilterRender getInstace(GLCanvas gLCanvas) {
        Render render = gLCanvas.getRender(KEY);
        if (render == null) {
            render = new BlurFilterRender(gLCanvas);
            gLCanvas.addRender(render);
        }
        return (BlurFilterRender) render;
    }

    public BlurFilterRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mIntensity = 1.0f;
        this.mFilterColor = 0;
        this.mKey = KEY;
    }

    protected void initProgram() {
        super.initProgram();
        this.mUniformFilterColor = GLES20.glGetUniformLocation(this.mProgram, "uFilterColor");
        this.mUniformIntensityH = GLES20.glGetUniformLocation(this.mProgram, "uIntensity");
    }

    public void setFilterColor(int i) {
        this.mFilterColor = i;
    }

    public void setIntensity(float f) {
        this.mIntensity = f;
    }

    protected void initShader(RenderInfo renderInfo) {
        super.initShader(renderInfo);
        GLES20.glUniform4f(this.mUniformFilterColor, ((float) Color.red(this.mFilterColor)) / 255.0f, ((float) Color.green(this.mFilterColor)) / 255.0f, ((float) Color.blue(this.mFilterColor)) / 255.0f, ((float) Color.alpha(this.mFilterColor)) / 255.0f);
        GLES20.glUniform1f(this.mUniformIntensityH, this.mIntensity);
    }

    protected String getFragmentShader() {
        return FRAG;
    }
}

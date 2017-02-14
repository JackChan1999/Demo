package com.meizu.common.renderer.effect.render;

import android.opengl.GLES20;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.RenderInfo;
import com.meizu.common.renderer.effect.element.TextureElement;

public class GaussianRender extends ConvolutionRender {
    private static final String FRAG = "precision mediump float; \nuniform vec2 uStep; \nuniform sampler2D sTexture; \nvarying vec2 vTexCoord; \nuniform int uVertical; \nuniform int uRadius; \nuniform float uWeight; \nvec4 gassian(vec2 step) { \n    if (uRadius == 0) return texture2D(sTexture, vTexCoord); \n    vec3 sum; \n    float j=0.0;  \n    for (int i=0; i<=uRadius; ++i) {  \n        if (i == 0) { \n           sum = texture2D(sTexture, vTexCoord).rgb * uWeight; \n        } else {  \n           sum += texture2D(sTexture,uVertical==1 ? vec2(vTexCoord.x,vTexCoord.y-j*step.y) : vec2(vTexCoord.x-j*step.x,vTexCoord.y)).rgb * uWeight;\n           sum += texture2D(sTexture,uVertical==1 ? vec2(vTexCoord.x,vTexCoord.y+j*step.y) : vec2(vTexCoord.x+j*step.x,vTexCoord.y)).rgb * uWeight;\n        }\n        j += 1.0;\n    }\n    return vec4(sum, 1.0); \n} \nvec4 gassian2(vec2 step) { \n    vec3 sum; \n\t float a[6]; \n    a[0] = 0.001; a[1] = 0.01; a[2] = 0.044; a[3] = 0.116; a[4] = 0.205; a[5] = 0.246; \n    //a[0] = 0.0355; a[1] = 0.0585; a[2] = 0.0863; a[3] = 0.1139; a[4] = 0.1346; a[5] = 0.1423; \n    //a[0] = 0.0549; a[1] = 0.0727; a[2] = 0.0905; a[3] = 0.1058; a[4] = 0.1162; a[5] = 0.1199; \n    sum  = texture2D(sTexture, vTexCoord - 5.0 * step).rgb * a[0]; \n    sum += texture2D(sTexture, vTexCoord - 4.0 * step).rgb * a[1]; \n    sum += texture2D(sTexture, vTexCoord - 3.0 * step).rgb * a[2]; \n    sum += texture2D(sTexture, vTexCoord - 2.0 * step).rgb * a[3]; \n    sum += texture2D(sTexture, vTexCoord - step).rgb * a[4]; \n    sum += texture2D(sTexture, vTexCoord).rgb * a[5]; \n    sum += texture2D(sTexture, vTexCoord + step).rgb * a[4]; \n    sum += texture2D(sTexture, vTexCoord + 2.0 * step).rgb * a[3]; \n    sum += texture2D(sTexture, vTexCoord + 3.0 * step).rgb * a[2]; \n    sum += texture2D(sTexture, vTexCoord + 4.0 * step).rgb * a[1]; \n    sum += texture2D(sTexture, vTexCoord + 5.0 * step).rgb * a[0]; \n    return vec4(sum, 1.0); \n} \nvoid main() { \n    if (uRadius <= 100) { \n        gl_FragColor = gassian(uStep); \n    } else { \n        gl_FragColor = gassian2(uStep);\n    } \n} \n";
    public static final String KEY = "__gaussian";
    public static final int MAGIC_RADIUS = 123;
    private int mRadius;
    protected int mUniformRadius;
    protected int mUniformVertical;
    protected int mUniformWeight;
    private boolean mVertical;
    private float mWeight;

    public static GaussianRender getInstace(GLCanvas gLCanvas) {
        Render render = gLCanvas.getRender(KEY);
        if (render == null) {
            render = new GaussianRender(gLCanvas);
            gLCanvas.addRender(render);
        }
        return (GaussianRender) render;
    }

    public GaussianRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mKey = KEY;
        this.mStepX = 1.0f;
        this.mStepY = 1.0f;
        setRadius(4);
    }

    protected void initProgram() {
        super.initProgram();
        this.mUniformVertical = GLES20.glGetUniformLocation(this.mProgram, "uVertical");
        this.mUniformRadius = GLES20.glGetUniformLocation(this.mProgram, "uRadius");
        this.mUniformWeight = GLES20.glGetUniformLocation(this.mProgram, "uWeight");
    }

    public void setDirection(boolean z) {
        this.mVertical = z;
    }

    public String getFragmentShader() {
        return FRAG;
    }

    public void setRadius(int i) {
        this.mRadius = i;
        this.mWeight = 1.0f / ((float) ((i * 2) + 1));
    }

    protected void initShader(RenderInfo renderInfo) {
        super.initShader(renderInfo);
        TextureElement textureElement = (TextureElement) renderInfo.element;
        this.mStepX = 1.0f / ((float) textureElement.mWidth);
        this.mStepY = 1.0f / ((float) textureElement.mHeight);
        if (this.mVertical) {
            GLES20.glUniform2f(this.mUniformStepH, 0.0f, this.mStepY);
            GLES20.glUniform1i(this.mUniformVertical, 1);
        } else {
            GLES20.glUniform2f(this.mUniformStepH, this.mStepX, 0.0f);
            GLES20.glUniform1i(this.mUniformVertical, 0);
        }
        GLES20.glUniform1f(this.mUniformWeight, this.mWeight);
        GLES20.glUniform1i(this.mUniformRadius, this.mRadius);
    }
}

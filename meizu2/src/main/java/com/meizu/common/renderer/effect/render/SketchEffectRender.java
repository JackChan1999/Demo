package com.meizu.common.renderer.effect.render;

import android.opengl.GLES20;
import com.meizu.common.renderer.effect.FrameBuffer;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.RenderInfo;
import com.meizu.common.renderer.effect.element.TextureElement;

public class SketchEffectRender extends RenderGroup {
    private RenderInfo mEffectInfo;
    private Gaussian2DRender mGaussian2DRender;
    private SketchRender mSketchRender;
    private TextureElement mTextureElement;

    static class Gaussian2DRender extends ConvolutionRender {
        private static final String FRAG = "precision mediump float; \nuniform vec2 uStep; \nuniform sampler2D sTexture; \nvarying vec2 vTexCoord; \nvoid main() { \n    vec2 step = uStep; \n    vec3 a = vec3(0.0113,0.0838,0.6193); \n    vec3 sum; \n    sum = texture2D(sTexture,  vTexCoord - step).rgb * a.x; \n    sum += texture2D(sTexture, vTexCoord + vec2(0.0, -step.y)).rgb * a.y; \n    sum += texture2D(sTexture, vTexCoord + vec2(step.x, -step.y)).rgb * a.x; \n    sum += texture2D(sTexture, vTexCoord + vec2(step.x, 0.0)).rgb * a.y; \n    sum += texture2D(sTexture, vTexCoord).rgb * a.z; \n    sum += texture2D(sTexture, vTexCoord + vec2(-step.x, 0.0)).rgb * a.y; \n    sum += texture2D(sTexture, vTexCoord + vec2(-step.x, step.y)).rgb * a.x; \n    sum += texture2D(sTexture, vTexCoord + vec2(0.0, step.y)).rgb * a.y; \n    sum += texture2D(sTexture, vTexCoord + step).rgb * a.x; \n    gl_FragColor.rgb = sum; \n    gl_FragColor.a = 1.0; \n } ";

        public Gaussian2DRender(GLCanvas gLCanvas) {
            super(gLCanvas);
        }

        public String getFragmentShader() {
            return FRAG;
        }

        protected void initShader(RenderInfo renderInfo) {
            super.initShader(renderInfo);
            GLES20.glUniform2f(this.mUniformStepH, 1.0f / ((float) renderInfo.element.mWidth), 1.0f / ((float) renderInfo.element.mHeight));
        }
    }

    static class SketchRender extends ConvolutionRender {
        private static final String FRAG = "precision mediump float; \nuniform vec2 uStep; \nuniform float uAlpha;\nuniform sampler2D sTexture; \nvarying vec2 vTexCoord; \nfloat rgb2gray(vec4 color) { \n    return dot(color, vec4(0.299, 0.587, 0.114, 0.0)); \n} \nvoid main() \n{ \n    vec4 bigStep = vec4(uStep, uStep); \n    float sample = 0.0; \n    sample  = 0.0448 * rgb2gray(texture2D(sTexture, vTexCoord - bigStep.pq)); \n    sample += 0.0468 * rgb2gray(texture2D(sTexture, vTexCoord - bigStep.sq)); \n    sample += 0.0564 * rgb2gray(texture2D(sTexture, vTexCoord - vec2(0.0, bigStep.q))); \n    sample += 0.0468 * rgb2gray(texture2D(sTexture, vTexCoord + vec2(bigStep.s, -bigStep.q))); \n    sample += 0.0448 * rgb2gray(texture2D(sTexture, vTexCoord + vec2(bigStep.p, -bigStep.q))); \n    sample += 0.0468 * rgb2gray(texture2D(sTexture, vTexCoord - bigStep.pt)); \n    sample += 0.3167 * rgb2gray(texture2D(sTexture, vTexCoord - bigStep.st)); \n    sample += 0.7146 * rgb2gray(texture2D(sTexture, vTexCoord - vec2(0.0, bigStep.t))); \n    sample += 0.3167 * rgb2gray(texture2D(sTexture, vTexCoord + vec2(bigStep.s, -bigStep.t))); \n    sample += 0.0468 * rgb2gray(texture2D(sTexture, vTexCoord + vec2(bigStep.p, -bigStep.t))); \n    sample += 0.0564 * rgb2gray(texture2D(sTexture, vTexCoord - vec2(bigStep.p, 0.0))); \n    sample += 0.7146 * rgb2gray(texture2D(sTexture, vTexCoord - vec2(bigStep.s, 0.0))); \n    sample -= 4.9048 * rgb2gray(texture2D(sTexture, vTexCoord)); \n    sample += 0.7146 * rgb2gray(texture2D(sTexture, vTexCoord + vec2(bigStep.s, 0.0))); \n    sample += 0.0564 * rgb2gray(texture2D(sTexture, vTexCoord + vec2(bigStep.p, 0.0))); \n    sample += 0.0468 * rgb2gray(texture2D(sTexture, vTexCoord + vec2(-bigStep.p, bigStep.t))); \n    sample += 0.3167 * rgb2gray(texture2D(sTexture, vTexCoord + vec2(-bigStep.s, bigStep.t))); \n    sample += 0.7146 * rgb2gray(texture2D(sTexture, vTexCoord + vec2(0.0, bigStep.t))); \n    sample += 0.3167 * rgb2gray(texture2D(sTexture, vTexCoord + bigStep.st)); \n    sample += 0.0468 * rgb2gray(texture2D(sTexture, vTexCoord + bigStep.pt)); \n    sample += 0.0448 * rgb2gray(texture2D(sTexture, vTexCoord + vec2(-bigStep.p, bigStep.q))); \n    sample += 0.0468 * rgb2gray(texture2D(sTexture, vTexCoord + vec2(-bigStep.s, bigStep.q))); \n    sample += 0.0564 * rgb2gray(texture2D(sTexture, vTexCoord + vec2(0.0, bigStep.q))); \n    sample += 0.0468 * rgb2gray(texture2D(sTexture, vTexCoord + bigStep.sq)); \n    sample += 0.0448 * rgb2gray(texture2D(sTexture, vTexCoord + bigStep.pq)); \n    sample = 1.0 - 3.0 * sample; \n    sample = clamp(sample, 0.0, 1.0); \n    gl_FragColor.rgb = vec3(sample); \n    gl_FragColor.a = uAlpha; \n}";

        public SketchRender(GLCanvas gLCanvas) {
            super(gLCanvas);
        }

        public String getFragmentShader() {
            return FRAG;
        }

        protected void initShader(RenderInfo renderInfo) {
            super.initShader(renderInfo);
            GLES20.glUniform2f(this.mUniformStepH, 1.0f / ((float) renderInfo.element.mWidth), 1.0f / ((float) renderInfo.element.mHeight));
        }
    }

    public SketchEffectRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mTextureElement = new TextureElement();
        this.mEffectInfo = new RenderInfo();
        this.mKey = Render.SKETCH;
        this.mSketchRender = new SketchRender(gLCanvas);
        this.mGaussian2DRender = new Gaussian2DRender(gLCanvas);
        this.mRenders.add(this.mGaussian2DRender);
        this.mRenders.add(this.mSketchRender);
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
        this.mEffectInfo.viewportWidth = textureElement.mWidth;
        this.mEffectInfo.viewportHeight = textureElement.mHeight;
        this.mEffectInfo.element = this.mTextureElement;
        FrameBuffer frameBuffer = this.mGLCanvas.getFrameBufferCache().get(textureElement.mWidth, textureElement.mHeight);
        this.mGLCanvas.getState().push();
        this.mGLCanvas.getState().indentityModelM();
        this.mGLCanvas.getState().indentityTexM();
        this.mGLCanvas.getState().setFrameBufferId(frameBuffer.getId());
        this.mTextureElement.init(textureElement.mTexture, 0, 0, textureElement.mWidth, textureElement.mHeight);
        this.mGaussian2DRender.draw(this.mEffectInfo);
        this.mGLCanvas.getState().pop();
        textureElement.mTexture = frameBuffer.getTexture();
        this.mSketchRender.draw(renderInfo);
        this.mGLCanvas.getFrameBufferCache().put(frameBuffer);
        this.mTextureElement.mTexture = null;
        this.mEffectInfo.reset();
    }
}

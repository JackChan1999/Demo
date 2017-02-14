package com.meizu.common.renderer.effect.render;

import android.util.Log;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.meizu.common.renderer.effect.FrameBuffer;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.GLRenderManager;
import com.meizu.common.renderer.effect.RenderInfo;
import com.meizu.common.renderer.effect.element.TextureElement;
import com.meizu.common.renderer.effect.parameters.BlurParameters;
import com.meizu.common.renderer.effect.parameters.FastBlurParameters;

public class BlurEffectRender extends Render {
    private final BlurParameters mDrawingParameters;
    private GaussianRender mEffectRender;
    private RenderInfo mRenderInfo = new RenderInfo();
    private int[] mSize = new int[3];
    private TextureElement mTextureElement = new TextureElement();

    public BlurEffectRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mEffectRender = GaussianRender.getInstace(gLCanvas);
        this.mDrawingParameters = new BlurParameters();
        this.mKey = Render.BLUR;
    }

    public void setParameters(BlurParameters blurParameters) {
        this.mDrawingParameters.copyFrom(blurParameters);
    }

    public BlurParameters getParameters() {
        return this.mDrawingParameters;
    }

    public boolean draw(RenderInfo renderInfo) {
        TextureElement textureElement = (TextureElement) renderInfo.element;
        FrameBuffer blur = blur(textureElement);
        if (blur != null) {
            textureElement.mTexture = blur.getTexture();
        }
        drawBlur(renderInfo);
        this.mGLCanvas.getFrameBufferCache().put(blur);
        return true;
    }

    public FrameBuffer blur(TextureElement textureElement) {
        if (skipBlur()) {
            return null;
        }
        float f;
        int i;
        int i2;
        float f2;
        float scale = this.mDrawingParameters.getScale();
        boolean progressBlur = this.mDrawingParameters.getProgressBlur();
        int radius = this.mDrawingParameters.getRadius();
        int passCount = this.mDrawingParameters.getPassCount();
        float f3 = 1.0f;
        if (progressBlur) {
            radius = (int) ((BitmapDescriptorFactory.HUE_ORANGE * this.mDrawingParameters.getLevel()) + FastBlurParameters.DEFAULT_LEVEL);
            passCount = radius / 10;
            if (passCount == 1) {
                f3 = 0.75f;
            } else if (passCount == 2) {
                f3 = FastBlurParameters.DEFAULT_LEVEL;
            } else if (passCount >= 3) {
                f3 = 0.25f;
            }
            scale *= f3;
            boxesForGauss(((float) radius) * f3, 3);
            f = f3;
            i = 3;
            i2 = radius;
            f2 = scale;
        } else {
            f = 1.0f;
            i = passCount;
            i2 = radius;
            f2 = scale;
        }
        int max = (int) Math.max(((float) textureElement.mWidth) * f2, 1.0f);
        int max2 = (int) Math.max(((float) textureElement.mHeight) * f2, 1.0f);
        FrameBuffer frameBuffer = this.mGLCanvas.getFrameBufferCache().get(max, max2);
        FrameBuffer frameBuffer2 = this.mGLCanvas.getFrameBufferCache().get(max, max2);
        this.mRenderInfo.viewportWidth = max;
        this.mRenderInfo.viewportHeight = max2;
        this.mRenderInfo.element = this.mTextureElement;
        this.mGLCanvas.getState().push();
        this.mGLCanvas.getState().indentityModelM();
        this.mGLCanvas.getState().indentityTexM();
        int i3 = 0;
        while (i3 < i) {
            this.mEffectRender.setRadius(progressBlur ? (this.mSize[i3] - 1) / 2 : i2);
            this.mTextureElement.init(i3 == 0 ? textureElement.mTexture : frameBuffer2.getTexture(), 0, 0, max, max2);
            this.mGLCanvas.getState().setFrameBufferId(frameBuffer.getId());
            this.mEffectRender.setDirection(false);
            this.mEffectRender.draw(this.mRenderInfo);
            this.mTextureElement.init(frameBuffer.getTexture(), 0, 0, max, max2);
            this.mGLCanvas.getState().setFrameBufferId(frameBuffer2.getId());
            this.mEffectRender.setDirection(true);
            this.mEffectRender.draw(this.mRenderInfo);
            i3++;
        }
        this.mGLCanvas.getState().pop();
        this.mGLCanvas.getFrameBufferCache().put(frameBuffer);
        this.mRenderInfo.reset();
        this.mTextureElement.mTexture = null;
        if (GLRenderManager.DEBUG) {
            String str = GLRenderManager.TAG;
            StringBuilder append = new StringBuilder().append("scaleFactor = ").append(f).append(" scale = ").append(f2).append(" level = ").append(this.mDrawingParameters.getLevel()).append(" width = ").append(max).append(" height = ").append(max2).append(" radius= ");
            if (progressBlur) {
                i2 = (this.mSize[2] - 1) / 2;
            }
            Log.e(str, append.append(i2).toString());
        }
        return frameBuffer2;
    }

    public void drawBlur(RenderInfo renderInfo) {
        BlurFilterRender instace = BlurFilterRender.getInstace(this.mGLCanvas);
        instace.setFilterColor(this.mDrawingParameters.getFilterColor());
        instace.setIntensity(this.mDrawingParameters.getIntensity());
        instace.draw(renderInfo);
    }

    public boolean skipBlur() {
        return this.mDrawingParameters.getLevel() < 0.01f;
    }

    private void boxesForGauss(float f, int i) {
        float f2 = f / 2.57f;
        int floor = (int) Math.floor((double) ((float) Math.sqrt((((12.0d * ((double) f2)) * ((double) f2)) / ((double) i)) + 1.0d)));
        if (floor % 2 == 0) {
            floor--;
        }
        int i2 = floor + 2;
        int round = Math.round(((((f2 * (12.0f * f2)) - ((float) ((i * floor) * floor))) - ((float) ((i * 4) * floor))) - ((float) (i * 3))) / ((float) ((floor * -4) - 4)));
        int i3 = 0;
        while (i3 < i) {
            this.mSize[i3] = i3 < round ? floor : i2;
            i3++;
        }
    }
}

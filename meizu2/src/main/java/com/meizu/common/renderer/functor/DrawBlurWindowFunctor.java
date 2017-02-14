package com.meizu.common.renderer.functor;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.meizu.common.renderer.Utils;
import com.meizu.common.renderer.effect.EGLBitmap;
import com.meizu.common.renderer.effect.FrameBuffer;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.GLRenderManager;
import com.meizu.common.renderer.effect.GraphicBuffer;
import com.meizu.common.renderer.effect.RenderInfo;
import com.meizu.common.renderer.effect.element.TextureElement;
import com.meizu.common.renderer.effect.parameters.FastBlurParameters;
import com.meizu.common.renderer.effect.render.ConvolutionRender;
import com.meizu.common.renderer.effect.render.GaussianRender;
import com.meizu.common.renderer.effect.render.Render;
import com.meizu.common.renderer.effect.render.ShaderRender;
import com.meizu.common.renderer.effect.texture.BasicTexture;
import com.meizu.common.renderer.effect.texture.EGLBitmapTexture;
import com.meizu.common.renderer.functor.DrawGLFunctor.GLInfo;
import java.lang.reflect.Method;

public class DrawBlurWindowFunctor extends DrawGLFunctor {
    private static final int BLUR_HEIGHT = 64;
    private static final int BLUR_WIDTH = 36;
    protected static Method sMethod_screenshot;
    private EGLBitmap mContentBitmap;
    private boolean mContentChanged;
    private Display mDisplay;
    private int mFormat;
    private FrameBuffer mFrameBuffer;
    private float mIntensity;
    private int mOrientation;
    protected RenderInfo mRenderInfo;
    private int mScreenShotHeight;
    private int mScreenShotWidth;
    private Rect mTexCropRect;
    private EGLBitmapTexture mTexture;
    protected TextureElement mTextureElement;
    private boolean mVisible;

    static class SmothRender extends ConvolutionRender {
        private static final String FRAG = "precision mediump float; \nuniform vec2 uStep; \nuniform sampler2D sTexture; \nuniform sampler2D sTexture2; \nuniform float uAlpha;\nuniform float uIntensity;\nvarying vec2 vTexCoord; \nvoid main() { \n    gl_FragColor.rgb = texture2D(sTexture,  vTexCoord).rgb*uAlpha*uIntensity +\n                       texture2D(sTexture2, vTexCoord).rgb*(1.0-uAlpha); \n    gl_FragColor.a = 1.0; \n } ";
        public static final String KEY = "__smoth";
        protected float mIntensity;
        protected BasicTexture mTexture;
        protected int mUniformIntensityH;
        protected int mUniformTextureH2;

        public SmothRender(GLCanvas gLCanvas) {
            super(gLCanvas);
            this.mKey = KEY;
        }

        public void setIntensity(float f) {
            this.mIntensity = f;
        }

        public void setTexture(BasicTexture basicTexture) {
            this.mTexture = basicTexture;
        }

        protected void initProgram() {
            super.initProgram();
            this.mUniformTextureH2 = GLES20.glGetUniformLocation(this.mProgram, "sTexture2");
            this.mUniformIntensityH = GLES20.glGetUniformLocation(this.mProgram, "uIntensity");
        }

        public String getFragmentShader() {
            return FRAG;
        }

        protected void initShader(RenderInfo renderInfo) {
            super.initShader(renderInfo);
            GLES20.glUniform2f(this.mUniformStepH, 1.0f / ((float) renderInfo.element.mWidth), 1.0f / ((float) renderInfo.element.mHeight));
            GLES20.glUniform1f(this.mUniformIntensityH, this.mIntensity);
            GLES20.glUniform1i(this.mUniformTextureH2, 1);
            ShaderRender.bindTexture(this.mTexture, 33985);
        }
    }

    public DrawBlurWindowFunctor() {
        this(4);
    }

    public DrawBlurWindowFunctor(int i) {
        this.mTextureElement = new TextureElement();
        this.mRenderInfo = new RenderInfo();
        this.mTexCropRect = new Rect();
        this.mDisplay = ((WindowManager) GLRenderManager.getInstance().getAppContext().getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mDisplay.getMetrics(displayMetrics);
        this.mScreenShotWidth = displayMetrics.widthPixels / 2;
        this.mScreenShotHeight = displayMetrics.heightPixels / 2;
        this.mIntensity = 0.34509805f * ((float) Math.pow(1.05d, 8.0d));
        this.mFormat = i;
        this.mEffectKey = Render.BLUR;
    }

    public GaussianRender getRender(GLCanvas gLCanvas) {
        return GaussianRender.getInstace(gLCanvas);
    }

    public SmothRender getNoneRender(GLCanvas gLCanvas) {
        SmothRender smothRender = (SmothRender) gLCanvas.getRender(SmothRender.KEY);
        if (smothRender != null) {
            return smothRender;
        }
        Render smothRender2 = new SmothRender(gLCanvas);
        gLCanvas.addRender(smothRender2);
        return smothRender2;
    }

    protected void onDraw(GLInfo gLInfo) {
        synchronized (this) {
            if (this.mVisible) {
                super.onDraw(gLInfo);
                Object canvas = GLRenderManager.getInstance().getCanvas();
                canvas.onRenderPreDraw(gLInfo);
                blurScreenShot(canvas);
                drawSceenShot(canvas, gLInfo);
                canvas.onRenderPostDraw();
                return;
            }
        }
    }

    private void blurScreenShot(GLCanvas gLCanvas) {
        if (this.mContentChanged && this.mContentBitmap != null) {
            if (GLRenderManager.DEBUG) {
                Log.i(GLRenderManager.TAG, "mContentChanged = true");
            }
            if (this.mTexture == null) {
                this.mTexture = new EGLBitmapTexture(this.mContentBitmap);
            }
            if (this.mFrameBuffer == null) {
                this.mFrameBuffer = new FrameBuffer(gLCanvas, 36, 64);
            }
            FrameBuffer frameBuffer = gLCanvas.getFrameBufferCache().get(36, 64);
            this.mRenderInfo.viewportWidth = 36;
            this.mRenderInfo.viewportHeight = 64;
            this.mRenderInfo.element = this.mTextureElement;
            gLCanvas.getState().push();
            gLCanvas.getState().indentityModelM();
            gLCanvas.getState().indentityTexM();
            getRender(gLCanvas).setRadius(123);
            for (int i = 0; i < 2; i++) {
                if (i == 0) {
                    this.mTextureElement.init(this.mTexture, 0, 0, 36, 64);
                } else {
                    this.mTextureElement.init(this.mFrameBuffer.getTexture(), 0, 0, 36, 64);
                }
                gLCanvas.getState().setFrameBufferId(frameBuffer.getId());
                getRender(gLCanvas).setDirection(false);
                getRender(gLCanvas).draw(this.mRenderInfo);
                this.mTextureElement.init(frameBuffer.getTexture(), 0, 0, 36, 64);
                gLCanvas.getState().setFrameBufferId(this.mFrameBuffer.getId());
                getRender(gLCanvas).setDirection(true);
                getRender(gLCanvas).draw(this.mRenderInfo);
            }
            gLCanvas.getState().pop();
            this.mRenderInfo.reset();
            gLCanvas.getFrameBufferCache().put(frameBuffer);
            this.mContentChanged = false;
        }
    }

    private void drawSceenShot(GLCanvas gLCanvas, GLInfo gLInfo) {
        if (this.mFrameBuffer != null && this.mTexture != null) {
            BasicTexture texture = this.mFrameBuffer.getTexture();
            this.mTextureElement.init(texture, this.mSourceBounds.left, this.mSourceBounds.top, this.mSourceBounds.width(), this.mSourceBounds.height());
            Utils.view2Window(gLInfo.transform, this.mSourceBounds, this.mTexCropRect);
            texture.setBounds(((float) this.mTexCropRect.left) / ((float) gLInfo.viewportWidth), ((float) this.mTexCropRect.top) / ((float) gLInfo.viewportHeight), ((float) this.mTexCropRect.width()) / ((float) gLInfo.viewportWidth), ((float) this.mTexCropRect.height()) / ((float) gLInfo.viewportHeight));
            if (this.mOrientation != 0) {
                Matrix.translateM(gLCanvas.getState().getTexMaxtrix(), 0, FastBlurParameters.DEFAULT_LEVEL, FastBlurParameters.DEFAULT_LEVEL, 0.0f);
                Matrix.rotateM(gLCanvas.getState().getTexMaxtrix(), 0, (float) this.mOrientation, 0.0f, 0.0f, 1.0f);
                Matrix.translateM(gLCanvas.getState().getTexMaxtrix(), 0, -0.5f, -0.5f, 0.0f);
            }
            this.mRenderInfo.flipProjV = true;
            this.mRenderInfo.blend = false;
            this.mRenderInfo.alpha = this.mAlpha;
            this.mRenderInfo.viewportWidth = gLInfo.viewportWidth;
            this.mRenderInfo.viewportHeight = gLInfo.viewportHeight;
            this.mRenderInfo.element = this.mTextureElement;
            SmothRender noneRender = getNoneRender(gLCanvas);
            noneRender.setIntensity(this.mIntensity);
            noneRender.setTexture(this.mTexture);
            noneRender.draw(this.mRenderInfo);
            noneRender.setIntensity(this.mIntensity);
            noneRender.setTexture(null);
            this.mRenderInfo.reset();
            texture.resetBounds();
        }
    }

    public void setIntensity(float f) {
        synchronized (this) {
            this.mIntensity = f;
        }
    }

    public float getIntensity() {
        return this.mIntensity;
    }

    public int getFormat() {
        return this.mFormat;
    }

    public void setVisible(boolean z) {
        synchronized (this) {
            this.mVisible = z;
        }
    }

    public boolean getVisible() {
        return this.mVisible;
    }

    public void screenshot(int i, int i2) {
        screenshot(this.mScreenShotWidth, this.mScreenShotHeight, i, i2);
    }

    public void screenshot(int i, int i2, int i3, int i4) {
        synchronized (this) {
            try {
                this.mScreenShotWidth = i;
                this.mScreenShotHeight = i2;
                this.mOrientation = getDisplayRotation();
                Bitmap bitmap = (Bitmap) sMethod_screenshot.invoke(null, new Object[]{new Rect(), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Boolean.valueOf(false), Integer.valueOf(0)});
                if (bitmap == null) {
                    Bitmap.createBitmap(i, i2, Config.RGB_565).eraseColor(-872415232);
                    this.mOrientation = 0;
                    return;
                }
                if (this.mContentBitmap == null || !this.mContentBitmap.isValid()) {
                    this.mContentBitmap = new EGLBitmap(i, i2, this.mFormat);
                }
                GraphicBuffer graphicBuffer = new GraphicBuffer(this.mContentBitmap);
                Canvas lockCanvas = graphicBuffer.lockCanvas();
                lockCanvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
                graphicBuffer.unlockCanvasAndPost(lockCanvas);
                graphicBuffer.destroy();
                bitmap.recycle();
                this.mContentChanged = true;
            } catch (Exception e) {
                Log.e(GLRenderManager.TAG, "screenshot error : " + e.getMessage());
            }
        }
    }

    private int getDisplayRotation() {
        switch (this.mDisplay.getRotation()) {
            case 1:
                return 90;
            case 2:
                return 180;
            case 3:
                return 270;
            default:
                return 0;
        }
    }

    public void reclaimMemory() {
        synchronized (this) {
            if (!(this.mContentBitmap == null || this.mVisible)) {
                this.mContentBitmap.freeGLResource();
                this.mContentBitmap = null;
            }
            if (this.mFrameBuffer != null) {
                this.mFrameBuffer.freeGLResource();
                this.mFrameBuffer = null;
            }
            if (this.mTexture != null) {
                this.mTexture.freeGLResource();
                this.mTexture = null;
            }
            this.mContentChanged = true;
        }
    }

    public void onTrimMemory(int i) {
        synchronized (this) {
            super.onTrimMemory(i);
            if (i >= 39) {
                reclaimMemory();
            }
        }
    }

    static {
        try {
            sMethod_screenshot = Class.forName("android.view.SurfaceControl").getDeclaredMethod("screenshot", new Class[]{Rect.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Integer.TYPE});
        } catch (Exception e) {
            Log.e(GLRenderManager.TAG, "DrawBlurWindowFunctor : " + e.toString());
        }
    }
}

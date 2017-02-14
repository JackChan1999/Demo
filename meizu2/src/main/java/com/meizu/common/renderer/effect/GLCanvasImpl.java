package com.meizu.common.renderer.effect;

import android.opengl.GLES20;
import android.util.Log;
import com.meizu.common.renderer.effect.cache.BasicTextureCache;
import com.meizu.common.renderer.effect.cache.BitmapTextureCache;
import com.meizu.common.renderer.effect.cache.EGLBitmapCache;
import com.meizu.common.renderer.effect.cache.FrameBufferCache;
import com.meizu.common.renderer.effect.render.BlurEffectRender;
import com.meizu.common.renderer.effect.render.FishEyeRender;
import com.meizu.common.renderer.effect.render.FogRender;
import com.meizu.common.renderer.effect.render.GrayRender;
import com.meizu.common.renderer.effect.render.MosaicRender;
import com.meizu.common.renderer.effect.render.NoneRender;
import com.meizu.common.renderer.effect.render.Render;
import com.meizu.common.renderer.effect.render.RenderGroup;
import com.meizu.common.renderer.effect.render.SeventyRender;
import com.meizu.common.renderer.effect.render.SketchEffectRender;
import com.meizu.common.renderer.effect.render.VividRender;
import com.meizu.common.renderer.effect.render.WaterRender;
import com.meizu.common.renderer.effect.render.YesteryearRender;
import com.meizu.common.renderer.functor.DrawGLFunctor;
import com.meizu.common.renderer.functor.DrawGLFunctor.GLInfo;
import java.util.ArrayList;

public class GLCanvasImpl implements GLCanvas, MemoryTrimmer {
    private final IntArray mDeleteBuffers = new IntArray();
    private final IntArray mDeleteFrameBuffers = new IntArray();
    private final ArrayList<Integer> mDeletePrograms = new ArrayList();
    private final IntArray mDeleteRenderBuffers = new IntArray();
    private final IntArray mDeleteTextures = new IntArray();
    private FrameBufferCache mFrameBufferCache = new FrameBufferCache(this);
    private RenderGroup mRenderGroup = new RenderGroup(this);
    private StateMachine mSnapshot = new StateMachine();
    private int mTargetFrameBufferId = 0;

    public StateMachine getState() {
        return this.mSnapshot;
    }

    public int getTargetFrameBufferId() {
        return this.mTargetFrameBufferId;
    }

    public void draw(RenderInfo renderInfo) {
        getRender(renderInfo.effectKey).draw(renderInfo);
    }

    public Render getRender(String str) {
        Render render = this.mRenderGroup.getRender(str);
        if (render == null) {
            render = createRender(str);
            if (render != null) {
                this.mRenderGroup.addRender(render);
            }
        }
        return render;
    }

    public void addRender(Render render) {
        if (render == null) {
            return;
        }
        if (render.getKey().equals(Render.NONE)) {
            Log.e(GLRenderManager.TAG, "Add render fail ,key = " + render.getKey());
        } else {
            this.mRenderGroup.addRender(render);
        }
    }

    private Render createRender(String str) {
        if (Render.NONE.equals(str)) {
            return new NoneRender(this);
        }
        if (Render.BLUR.equals(str)) {
            return new BlurEffectRender(this);
        }
        if (Render.GRAY.equals(str)) {
            return new GrayRender(this);
        }
        if (Render.FOG.equals(str)) {
            return new FogRender(this);
        }
        if (Render.WATER.equals(str)) {
            return new WaterRender(this);
        }
        if (Render.YESTERDAY.equals(str)) {
            return new YesteryearRender(this);
        }
        if (Render.VIVID.equals(str)) {
            return new VividRender(this);
        }
        if (Render.SEVENTY.equals(str)) {
            return new SeventyRender(this);
        }
        if (Render.FISHEYE.equals(str)) {
            return new FishEyeRender(this);
        }
        if (Render.MOSAIC.equals(str)) {
            return new MosaicRender(this);
        }
        if (Render.SKETCH.equals(str)) {
            return new SketchEffectRender(this);
        }
        return null;
    }

    public void onRenderPreDraw(GLInfo gLInfo) {
        this.mSnapshot.reset();
        this.mSnapshot.setMatrix(gLInfo.transform, 0);
        int[] iArr = new int[1];
        GLES20.glGetIntegerv(36006, iArr, 0);
        this.mSnapshot.setFrameBufferId(iArr[0]);
        this.mTargetFrameBufferId = iArr[0];
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glBlendFunc(770, 771);
        recycledResources();
    }

    public void onRenderPostDraw() {
        GLES20.glBindFramebuffer(36160, this.mTargetFrameBufferId);
        recycledResources();
    }

    public FrameBufferCache getFrameBufferCache() {
        return this.mFrameBufferCache;
    }

    public boolean deleteTexture(int i) {
        synchronized (this.mDeleteTextures) {
            this.mDeleteTextures.add(i);
        }
        return true;
    }

    public void deleteBuffer(int i) {
        synchronized (this.mDeleteBuffers) {
            this.mDeleteBuffers.add(i);
        }
    }

    public void deleteRenderBuffer(int i) {
        synchronized (this.mDeleteRenderBuffers) {
            this.mDeleteRenderBuffers.add(i);
        }
    }

    public void deleteFrameBuffer(int i) {
        synchronized (this.mDeleteFrameBuffers) {
            this.mDeleteFrameBuffers.add(i);
        }
    }

    public void deleteProgram(int i) {
        synchronized (this.mDeletePrograms) {
            this.mDeletePrograms.add(Integer.valueOf(i));
        }
    }

    public void onTrimMemory(int i) {
        DrawGLFunctor.scheduleTrimMemory(i);
        if (i >= 19) {
            BasicTextureCache.getInstance().freeGLResource();
            EGLBitmapCache.freeResource();
            BitmapTextureCache.freeGLResource();
            this.mFrameBufferCache.freeGLResource();
            this.mSnapshot.onTrimMemory(i);
        }
        if (i >= 39) {
            EGLImageHandler.releaseInstance();
            this.mRenderGroup.freeGLResource();
        }
        recycledResources();
    }

    public void recycledResources() {
        synchronized (this.mDeleteTextures) {
            IntArray intArray = this.mDeleteTextures;
            if (intArray.size() > 0) {
                GLES20.glDeleteTextures(intArray.size(), intArray.getInternalArray(), 0);
                intArray.clear();
            }
            intArray = this.mDeleteBuffers;
            if (intArray.size() > 0) {
                GLES20.glDeleteBuffers(intArray.size(), intArray.getInternalArray(), 0);
                intArray.clear();
            }
            intArray = this.mDeleteRenderBuffers;
            if (intArray.size() > 0) {
                GLES20.glDeleteRenderbuffers(intArray.size(), intArray.getInternalArray(), 0);
                intArray.clear();
            }
            intArray = this.mDeleteFrameBuffers;
            if (intArray.size() > 0) {
                GLES20.glDeleteFramebuffers(intArray.size(), intArray.getInternalArray(), 0);
                intArray.clear();
            }
            while (this.mDeletePrograms.size() > 0) {
                GLES20.glDeleteProgram(((Integer) this.mDeletePrograms.remove(0)).intValue());
            }
        }
    }
}

package com.meizu.common.renderer.effect;

import com.meizu.common.renderer.effect.cache.FrameBufferCache;
import com.meizu.common.renderer.effect.render.Render;

public interface GLCanvas {
    void addRender(Render render);

    void deleteBuffer(int i);

    void deleteFrameBuffer(int i);

    void deleteProgram(int i);

    void deleteRenderBuffer(int i);

    boolean deleteTexture(int i);

    void draw(RenderInfo renderInfo);

    FrameBufferCache getFrameBufferCache();

    Render getRender(String str);

    StateMachine getState();

    int getTargetFrameBufferId();

    void recycledResources();
}

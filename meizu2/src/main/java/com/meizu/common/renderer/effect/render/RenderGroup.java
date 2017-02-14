package com.meizu.common.renderer.effect.render;

import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.RenderInfo;
import java.util.ArrayList;

public class RenderGroup extends Render {
    protected ArrayList<Render> mRenders = new ArrayList();

    public RenderGroup(GLCanvas gLCanvas) {
        super(gLCanvas);
    }

    public void addRender(Render render) {
        int i = 0;
        while (i < this.mRenders.size()) {
            if (!render.getKey().equals(((Render) this.mRenders.get(i)).getKey())) {
                i++;
            } else {
                return;
            }
        }
        this.mRenders.add(render);
    }

    public Render getRender(String str) {
        for (int i = 0; i < this.mRenders.size(); i++) {
            if (str.equals(((Render) this.mRenders.get(i)).getKey())) {
                return (Render) this.mRenders.get(i);
            }
        }
        return null;
    }

    public boolean draw(RenderInfo renderInfo) {
        return false;
    }

    public void freeGLResource() {
        for (int i = 0; i < this.mRenders.size(); i++) {
            ((Render) this.mRenders.get(i)).freeGLResource();
        }
        this.mRenders.clear();
    }
}

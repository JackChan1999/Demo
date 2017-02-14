package com.meizu.common.renderer;

import android.app.Application;
import com.meizu.common.renderer.effect.GLRenderManager;

public class ApplicationImpl extends Application {
    public void onCreate() {
        super.onCreate();
        GLRenderManager.getInstance().initialize(this);
    }

    public void onTrimMemory(int i) {
        super.onTrimMemory(i);
        GLRenderManager.getInstance().trimMemory(i);
    }
}

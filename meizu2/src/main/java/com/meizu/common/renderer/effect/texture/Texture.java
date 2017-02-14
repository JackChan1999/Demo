package com.meizu.common.renderer.effect.texture;

import android.graphics.RectF;
import com.meizu.common.renderer.effect.GLCanvas;

public interface Texture {
    RectF getBounds();

    int getFormat();

    int getHeight();

    int getId();

    int getTarget();

    int getWidth();

    boolean isOpaque();

    boolean onBind(GLCanvas gLCanvas);

    void resetBounds();

    void setBounds(float f, float f2, float f3, float f4);

    void updateTransformMatrix(GLCanvas gLCanvas, boolean z, boolean z2);
}

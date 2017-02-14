package com.meizu.common.renderer.effect.render;

import com.meizu.common.renderer.effect.GLCanvas;

public class ExtRender extends PixelsRender {
    public static final String EXT = "external";
    private static final String FRAG = "#extension GL_OES_EGL_image_external : require  \nuniform samplerExternalOES sTexture; \nprecision mediump float; \nuniform float uAlpha; \nvarying vec2 vTexCoord; \nvoid main() \n{ \n    gl_FragColor.rgb = texture2D(sTexture, vTexCoord).rgb; \n    gl_FragColor.a = uAlpha;\n}";

    public ExtRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mKey = EXT;
    }

    public String getFragmentShader() {
        return FRAG;
    }
}

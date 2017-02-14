package com.meizu.common.renderer.effect.render;

import com.meizu.common.renderer.effect.GLCanvas;

public class GrayRender extends PixelsRender {
    private static final String FRAG = "precision mediump float; \nuniform sampler2D sTexture; \nuniform float uAlpha;\nvarying vec2 vTexCoord; \nvoid main() { \n    float alpha = texture2D(sTexture, vTexCoord).a*uAlpha; \n    vec3 factor = vec3(0.299, 0.587, 0.114); \n    vec3 color = texture2D(sTexture, vTexCoord).rgb; \n    float gray = 0.0; \n    gray = dot(color,factor); \n    color = vec3(gray, gray, gray); \n    gl_FragColor = vec4(color, alpha); \n}";

    public GrayRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mKey = Render.GRAY;
    }

    public String getFragmentShader() {
        return FRAG;
    }
}

package com.meizu.common.renderer.effect.render;

import com.meizu.common.renderer.effect.GLCanvas;

public class FogRender extends PixelsRender {
    private static final String FRAG = "precision mediump float; \nuniform sampler2D sTexture; \nuniform float uAlpha;\nvarying vec2 vTexCoord; \nvoid main() \n{ \n    vec4 color = texture2D(sTexture, vTexCoord).rgba; \n    vec3 fog = mix(vec3(0.5, 0.8, 0.5), color.rgb, 0.7); \n    gl_FragColor = vec4(fog, color.a*uAlpha); \n} \n";

    public FogRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mKey = Render.FOG;
    }

    public String getFragmentShader() {
        return FRAG;
    }
}

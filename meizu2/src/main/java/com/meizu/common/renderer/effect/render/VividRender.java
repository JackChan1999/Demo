package com.meizu.common.renderer.effect.render;

import com.meizu.common.renderer.effect.GLCanvas;

public class VividRender extends PixelsRender {
    private static final String FRAG = "precision mediump float; \nuniform sampler2D sTexture; \nuniform float uAlpha;\nvarying vec2 vTexCoord; \nfloat BlendOverlayf(float base, float blend) \n{ \n  return (base < 0.5 ? (2.0 * base * blend) : (1.0 - 2.0 * (1.0 - base) * (1.0 - blend))); \n} \nvec3 BlendOverlay(vec3 base, vec3 blend) \n{ \n  return vec3(BlendOverlayf(base.r, blend.r), BlendOverlayf(base.g, blend.g), BlendOverlayf(base.b, blend.b)); \n} \nvec3 HardLight(vec3 base, vec3 blend) \n{ \n  return BlendOverlay(blend, base); \n} \nvoid main() \n{ \n   vec4 color = texture2D(sTexture, vTexCoord); \n   vec3 tmpColor = HardLight(color.rgb, color.rgb); \n   gl_FragColor = vec4(mix(color.rgb, tmpColor, 0.7), color.a*uAlpha); \n} \n";

    public VividRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mKey = Render.VIVID;
    }

    public String getFragmentShader() {
        return FRAG;
    }
}

package com.meizu.common.renderer.effect.element;

import com.meizu.common.renderer.effect.texture.Texture;

public class TextureElement extends Element {
    public Texture mTexture;

    public TextureElement(Texture texture, int i, int i2, int i3, int i4) {
        init(texture, i, i2, i3, i4);
    }

    public TextureElement init(Texture texture, int i, int i2, int i3, int i4) {
        this.mTexture = texture;
        this.mX = i;
        this.mY = i2;
        this.mWidth = i3;
        this.mHeight = i4;
        return this;
    }

    public int getId() {
        return 1;
    }
}

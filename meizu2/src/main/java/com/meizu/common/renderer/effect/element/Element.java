package com.meizu.common.renderer.effect.element;

public abstract class Element {
    public static final int NONE = -1;
    public static final int TEXTURE = 1;
    public int mHeight;
    protected int mId = -1;
    public int mWidth;
    public int mX;
    public int mY;

    public int getId() {
        return -1;
    }
}

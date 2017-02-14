package com.meizu.common.renderer.effect;

public class IntArray {
    private static final int INIT_CAPACITY = 8;
    private int[] mData = new int[8];
    private int mSize = 0;

    public void add(int i) {
        if (this.mData.length == this.mSize) {
            Object obj = new int[(this.mSize + this.mSize)];
            System.arraycopy(this.mData, 0, obj, 0, this.mSize);
            this.mData = obj;
        }
        int[] iArr = this.mData;
        int i2 = this.mSize;
        this.mSize = i2 + 1;
        iArr[i2] = i;
    }

    public int size() {
        return this.mSize;
    }

    public int[] toArray(int[] iArr) {
        if (iArr == null || iArr.length < this.mSize) {
            iArr = new int[this.mSize];
        }
        System.arraycopy(this.mData, 0, iArr, 0, this.mSize);
        return iArr;
    }

    public int[] getInternalArray() {
        return this.mData;
    }

    public void clear() {
        this.mSize = 0;
        if (this.mData.length != 8) {
            this.mData = new int[8];
        }
    }
}

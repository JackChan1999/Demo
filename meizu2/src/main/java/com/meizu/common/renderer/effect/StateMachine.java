package com.meizu.common.renderer.effect;

import android.opengl.Matrix;
import java.util.Stack;
import java.util.Vector;

public class StateMachine implements MemoryTrimmer {
    private int mFrameBufferId = 0;
    private final float[] mIdentityMatrix = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    private final float[] mMVPMatrix = new float[16];
    private final float[] mModelMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private Stack<State> mStack = new Stack();
    private StateCache mStateCaches = new StateCache(25);
    private final float[] mTexMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    static class State {
        int mFrameBufferId = 0;
        final float[] mModelMatrix = new float[16];
        final float[] mTexMatrix = new float[16];

        public State init(float[] fArr, float[] fArr2, int i) {
            System.arraycopy(fArr, 0, this.mModelMatrix, 0, 16);
            System.arraycopy(fArr2, 0, this.mTexMatrix, 0, 16);
            this.mFrameBufferId = i;
            return this;
        }
    }

    static class StateCache {
        private Vector<State> mCache = new Vector();
        private int mSize = 25;

        public StateCache(int i) {
            this.mSize = i;
        }

        public State get() {
            if (this.mCache.size() > 0) {
                return (State) this.mCache.remove(this.mCache.size() - 1);
            }
            return new State();
        }

        public void put(State state) {
            if (state != null) {
                int size = this.mCache.size() - 1;
                while (size >= 0) {
                    if (this.mCache.get(size) != state) {
                        size--;
                    } else {
                        return;
                    }
                }
                if (this.mCache.size() < this.mSize) {
                    this.mCache.add(state);
                }
            }
        }

        public void clear() {
            this.mCache.clear();
        }
    }

    public StateMachine() {
        reset();
    }

    public void indentityAllM() {
        Matrix.setIdentityM(this.mModelMatrix, 0);
        Matrix.setIdentityM(this.mTexMatrix, 0);
        Matrix.setIdentityM(this.mViewMatrix, 0);
        Matrix.setIdentityM(this.mProjectionMatrix, 0);
    }

    public void indentityModelM() {
        Matrix.setIdentityM(this.mModelMatrix, 0);
    }

    public void indentityTexM() {
        Matrix.setIdentityM(this.mTexMatrix, 0);
    }

    public void indentityViewM() {
        Matrix.setIdentityM(this.mViewMatrix, 0);
    }

    public void indentityProjectionM() {
        Matrix.setIdentityM(this.mModelMatrix, 0);
    }

    public void push() {
        this.mStack.push(this.mStateCaches.get().init(this.mModelMatrix, this.mTexMatrix, this.mFrameBufferId));
    }

    public void pop() {
        State state = (State) this.mStack.pop();
        if (state == null) {
            throw new IllegalStateException("Wrong to invoke pop.");
        }
        System.arraycopy(state.mModelMatrix, 0, this.mModelMatrix, 0, 16);
        System.arraycopy(state.mTexMatrix, 0, this.mTexMatrix, 0, 16);
        this.mFrameBufferId = state.mFrameBufferId;
        this.mStateCaches.put(state);
    }

    public void translate(float f, float f2, float f3) {
        Matrix.translateM(this.mModelMatrix, 0, f, f2, f3);
    }

    public void translate(float f, float f2) {
        Matrix.translateM(this.mModelMatrix, 0, f, f2, 0.0f);
    }

    public void rotate(float f, float f2, float f3, float f4) {
        if (f != 0.0f) {
            Matrix.rotateM(this.mModelMatrix, 0, f, f2, f3, f4);
        }
    }

    public void scale(float f, float f2, float f3) {
        Matrix.scaleM(this.mModelMatrix, 0, f, f2, f3);
    }

    public int getTranslateX() {
        return (int) this.mModelMatrix[12];
    }

    public int getTranslateY() {
        return (int) this.mModelMatrix[13];
    }

    public void posScale(float f, float f2, float f3) {
        Object obj = new float[16];
        Matrix.multiplyMM(obj, 0, new float[]{f, 0.0f, 0.0f, 0.0f, 0.0f, f2, 0.0f, 0.0f, 0.0f, 0.0f, f3, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f}, 0, this.mModelMatrix, 0);
        System.arraycopy(obj, 0, this.mModelMatrix, 0, 16);
    }

    public void multiplyMatrix(float[] fArr, int i) {
        Object obj = new float[16];
        Matrix.multiplyMM(obj, 0, this.mModelMatrix, 0, fArr, i);
        System.arraycopy(obj, 0, this.mModelMatrix, 0, 16);
    }

    public void setMatrix(float[] fArr, int i) {
        System.arraycopy(fArr, i, this.mModelMatrix, 0, 16);
    }

    public void setTexMatrix(float[] fArr, int i) {
        System.arraycopy(fArr, i, this.mTexMatrix, 0, 16);
    }

    public void setLookAt(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        Matrix.setLookAtM(this.mViewMatrix, 0, f, f2, f3, f4, f5, f6, f7, f8, f9);
    }

    public void frustumM(float f, float f2, float f3, float f4, float f5, float f6) {
        Matrix.frustumM(this.mProjectionMatrix, 0, f, f2, f3, f4, f5, f6);
    }

    public void orthoM(float f, float f2, float f3, float f4) {
        Matrix.orthoM(this.mProjectionMatrix, 0, f, f2, f3, f4, -100.0f, 100.0f);
    }

    public void setIdentity() {
        Matrix.setIdentityM(this.mModelMatrix, 0);
    }

    public void setFrameBufferId(int i) {
        this.mFrameBufferId = i;
    }

    public int getFrameBufferId() {
        return this.mFrameBufferId;
    }

    public float[] getFinalMatrix() {
        Matrix.multiplyMM(this.mMVPMatrix, 0, this.mViewMatrix, 0, this.mModelMatrix, 0);
        Matrix.multiplyMM(this.mMVPMatrix, 0, this.mProjectionMatrix, 0, this.mMVPMatrix, 0);
        return this.mMVPMatrix;
    }

    public float[] getModelMatrix() {
        return this.mModelMatrix;
    }

    public float[] getIdentityMatrix() {
        return this.mIdentityMatrix;
    }

    public float[] getTexMaxtrix() {
        return this.mTexMatrix;
    }

    public void reset() {
        Matrix.setIdentityM(this.mModelMatrix, 0);
        Matrix.setIdentityM(this.mViewMatrix, 0);
        Matrix.setIdentityM(this.mProjectionMatrix, 0);
        Matrix.setIdentityM(this.mTexMatrix, 0);
        this.mFrameBufferId = 0;
    }

    public static boolean isIndentity(float[] fArr) {
        for (int i = 0; i < 16; i++) {
            if (i % 5 == 0) {
                if (fArr[i] != 1.0f) {
                    return false;
                }
            } else if (fArr[i] != 0.0f) {
                return false;
            }
        }
        return true;
    }

    public void onTrimMemory(int i) {
        this.mStateCaches.clear();
    }
}

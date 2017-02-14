package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnDrawListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BlurRectView extends FrameLayout implements OnDrawListener, OnGlobalLayoutListener {
    private static final String TAG = "BlurRectView";
    private boolean mBlurAsBackground = false;
    private Method mBlurMethod;
    private View mBlurView;
    private Field mDirtyField;
    private int mDownscale = 6;
    private boolean mFindBlurRect = false;
    private Object mMzImageProcessing;
    private int mRadius = 7;
    private Bitmap mRawBitmap;
    private Object mViewRootImpl;
    private ViewTreeObserver mViewTreeObserver;
    private Rect mVisibleRect = new Rect();
    private Drawable mWindowBackground;

    public BlurRectView(Context context) {
        super(context);
    }

    public BlurRectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public BlurRectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void onGlobalLayout() {
        if (!this.mFindBlurRect) {
            this.mFindBlurRect = findNextView();
        }
        getGlobalVisibleRect(this.mVisibleRect);
    }

    public void onDraw() {
        Rect rect;
        if (this.mDirtyField != null) {
            try {
                rect = (Rect) this.mDirtyField.get(this.mViewRootImpl);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                rect = null;
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            }
            boolean intersects = rect != null ? false : Rect.intersects(this.mVisibleRect, rect);
            if (this.mFindBlurRect && intersects) {
                applyBlur();
                return;
            }
        }
        rect = null;
        if (rect != null) {
        }
        if (this.mFindBlurRect) {
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mViewTreeObserver == null) {
            this.mViewTreeObserver = getViewTreeObserver();
        }
        this.mViewTreeObserver.addOnGlobalLayoutListener(this);
        this.mViewTreeObserver.addOnDrawListener(this);
        getWindowBackgroundDrawable();
        try {
            this.mViewRootImpl = View.class.getDeclaredMethod("getViewRootImpl", new Class[0]).invoke(this, new Object[0]);
            this.mDirtyField = this.mViewRootImpl.getClass().getDeclaredField("mDirty");
            this.mDirtyField.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
        } catch (NoSuchFieldException e5) {
            e5.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.graphics.MZImageProcessing");
            this.mBlurMethod = cls.getDeclaredMethod("stackBlurMultiThreadProcessedByNative", new Class[]{Bitmap.class, Integer.TYPE});
            this.mMzImageProcessing = cls.getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (ClassNotFoundException e6) {
            e6.printStackTrace();
        } catch (NoSuchMethodException e7) {
            e7.printStackTrace();
        } catch (IllegalAccessException e22) {
            e22.printStackTrace();
        } catch (IllegalArgumentException e32) {
            e32.printStackTrace();
        } catch (InvocationTargetException e42) {
            e42.printStackTrace();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mViewTreeObserver != null) {
            this.mViewTreeObserver.removeOnGlobalLayoutListener(this);
            this.mViewTreeObserver.removeOnDrawListener(this);
        }
    }

    public void draw(Canvas canvas) {
        if (!(this.mBlurAsBackground || this.mRawBitmap == null || this.mRawBitmap.isRecycled())) {
            canvas.drawBitmap(this.mRawBitmap, 0.0f, 0.0f, null);
            canvas.drawBitmap(this.mRawBitmap, new Rect(0, 0, this.mRawBitmap.getWidth(), this.mRawBitmap.getHeight()), new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), null);
        }
        super.draw(canvas);
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.mRawBitmap != null && !this.mRawBitmap.isRecycled()) {
            this.mRawBitmap.recycle();
            this.mRawBitmap = null;
            if (this.mBlurAsBackground) {
                setBackground(null);
            }
        }
    }

    private void getWindowBackgroundDrawable() {
        int[] iArr = new int[]{16842836};
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(16842836, typedValue, true);
        TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(typedValue.resourceId, iArr);
        this.mWindowBackground = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
    }

    private boolean findNextView() {
        ViewParent parent = getParent();
        if (parent instanceof FrameLayout) {
            FrameLayout frameLayout = (FrameLayout) parent;
            boolean z = false;
            for (int childCount = frameLayout.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = frameLayout.getChildAt(childCount);
                if (z && childAt.getVisibility() == 0) {
                    if (new Rect(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom()).intersect(getLeft(), getTop(), getRight(), getBottom())) {
                        this.mBlurView = childAt;
                        return true;
                    }
                } else if (childAt == this) {
                    z = true;
                }
            }
        }
        return false;
    }

    private void applyBlur() {
        boolean z;
        int i = this.mDownscale;
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        float f = 1.0f / ((float) i);
        if (this.mRawBitmap == null) {
            this.mRawBitmap = Bitmap.createBitmap(measuredWidth / i, measuredHeight / i, Config.ARGB_8888);
            z = true;
        } else {
            this.mRawBitmap.eraseColor(0);
            z = false;
        }
        Canvas canvas = new Canvas(this.mRawBitmap);
        this.mWindowBackground.setBounds(new Rect(0, 0, measuredWidth, measuredHeight));
        this.mWindowBackground.draw(canvas);
        canvas.scale(f, f);
        drawBlurView(canvas);
        try {
            this.mBlurMethod.invoke(this.mMzImageProcessing, new Object[]{this.mRawBitmap, Integer.valueOf(this.mRadius)});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
        if (z && getBackground() == null) {
            this.mBlurAsBackground = true;
            setBackground(new BitmapDrawable(getContext().getResources(), this.mRawBitmap));
        }
    }

    private void drawBlurView(Canvas canvas) {
        int left = (this.mBlurView.getLeft() - getLeft()) - this.mBlurView.getScrollX();
        int top = (this.mBlurView.getTop() - getTop()) - this.mBlurView.getScrollY();
        canvas.translate((float) left, (float) top);
        this.mBlurView.draw(canvas);
        canvas.translate((float) (-left), (float) (-top));
    }

    public void setBlurRadius(int i) {
        this.mRadius = Math.abs(i);
    }

    public void setBlurDownScale(int i) {
        this.mDownscale = Math.abs(i);
        this.mDownscale = Math.max(1, this.mDownscale);
    }

    public void setBlurRaidusAndDownScale(int i, int i2) {
        setBlurRadius(i);
        setBlurDownScale(i2);
    }

    public void saveToSdCard(Bitmap bitmap, String str) {
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, 40, byteArrayOutputStream);
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "DCIM" + File.separator + str);
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap softwareBlur(Bitmap bitmap) {
        int i = this.mRadius;
        if (i < 1) {
            return null;
        }
        int i2;
        int i3;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[(width * height)];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        int i4 = width - 1;
        int i5 = height - 1;
        int i6 = width * height;
        int i7 = (i + i) + 1;
        int[] iArr2 = new int[i6];
        int[] iArr3 = new int[i6];
        int[] iArr4 = new int[i6];
        int[] iArr5 = new int[Math.max(width, height)];
        i6 = (i7 + 1) >> 1;
        int i8 = i6 * i6;
        int[] iArr6 = new int[(i8 * 256)];
        for (i6 = 0; i6 < i8 * 256; i6++) {
            iArr6[i6] = i6 / i8;
        }
        int[][] iArr7 = (int[][]) Array.newInstance(Integer.TYPE, new int[]{i7, 3});
        int i9 = i + 1;
        int i10 = 0;
        int i11 = 0;
        for (i2 = 0; i2 < height; i2++) {
            int i12;
            i8 = 0;
            int i13 = 0;
            int i14 = 0;
            int i15 = 0;
            int i16 = 0;
            int i17 = 0;
            int i18 = 0;
            int i19 = 0;
            int i20 = 0;
            for (i3 = -i; i3 <= i; i3++) {
                i12 = iArr[Math.min(i4, Math.max(i3, 0)) + i11];
                int[] iArr8 = iArr7[i3 + i];
                iArr8[0] = (16711680 & i12) >> 16;
                iArr8[1] = (MotionEventCompat.ACTION_POINTER_INDEX_MASK & i12) >> 8;
                iArr8[2] = i12 & 255;
                i12 = i9 - Math.abs(i3);
                i19 += iArr8[0] * i12;
                i18 += iArr8[1] * i12;
                i17 += i12 * iArr8[2];
                if (i3 > 0) {
                    i13 += iArr8[0];
                    i20 += iArr8[1];
                    i8 += iArr8[2];
                } else {
                    i16 += iArr8[0];
                    i15 += iArr8[1];
                    i14 += iArr8[2];
                }
            }
            i12 = i19;
            i19 = i18;
            i18 = i17;
            i3 = i11;
            i11 = i;
            for (i17 = 0; i17 < width; i17++) {
                iArr2[i3] = iArr6[i12];
                iArr3[i3] = iArr6[i19];
                iArr4[i3] = iArr6[i18];
                i12 -= i16;
                i19 -= i15;
                i18 -= i14;
                iArr8 = iArr7[((i11 - i) + i7) % i7];
                i16 -= iArr8[0];
                i15 -= iArr8[1];
                i14 -= iArr8[2];
                if (i2 == 0) {
                    iArr5[i17] = Math.min((i17 + i) + 1, i4);
                }
                int i21 = iArr[iArr5[i17] + i10];
                iArr8[0] = (16711680 & i21) >> 16;
                iArr8[1] = (MotionEventCompat.ACTION_POINTER_INDEX_MASK & i21) >> 8;
                iArr8[2] = i21 & 255;
                i13 += iArr8[0];
                i20 += iArr8[1];
                i8 += iArr8[2];
                i12 += i13;
                i19 += i20;
                i18 += i8;
                i11 = (i11 + 1) % i7;
                iArr8 = iArr7[i11 % i7];
                i16 += iArr8[0];
                i15 += iArr8[1];
                i14 += iArr8[2];
                i13 -= iArr8[0];
                i20 -= iArr8[1];
                i8 -= iArr8[2];
                i3++;
            }
            i10 += width;
            i11 = i3;
        }
        for (i17 = 0; i17 < width; i17++) {
            i20 = 0;
            i8 = (-i) * width;
            i14 = 0;
            i15 = 0;
            i16 = 0;
            i11 = 0;
            i12 = -i;
            i3 = 0;
            i18 = 0;
            i19 = 0;
            i13 = 0;
            while (i12 <= i) {
                i2 = Math.max(0, i8) + i17;
                int[] iArr9 = iArr7[i12 + i];
                iArr9[0] = iArr2[i2];
                iArr9[1] = iArr3[i2];
                iArr9[2] = iArr4[i2];
                int abs = i9 - Math.abs(i12);
                i10 = (iArr2[i2] * abs) + i19;
                i19 = (iArr3[i2] * abs) + i18;
                i18 = (iArr4[i2] * abs) + i3;
                if (i12 > 0) {
                    i14 += iArr9[0];
                    i13 += iArr9[1];
                    i20 += iArr9[2];
                } else {
                    i11 += iArr9[0];
                    i16 += iArr9[1];
                    i15 += iArr9[2];
                }
                if (i12 < i5) {
                    i8 += width;
                }
                i12++;
                i3 = i18;
                i18 = i19;
                i19 = i10;
            }
            i12 = i18;
            i10 = i19;
            i19 = i3;
            i3 = i17;
            i8 = i20;
            i20 = i13;
            i13 = i14;
            i14 = i15;
            i15 = i16;
            i16 = i11;
            i11 = i;
            for (i18 = 0; i18 < height; i18++) {
                iArr[i3] = (((ViewCompat.MEASURED_STATE_MASK & iArr[i3]) | (iArr6[i10] << 16)) | (iArr6[i12] << 8)) | iArr6[i19];
                i10 -= i16;
                i12 -= i15;
                i19 -= i14;
                int[] iArr10 = iArr7[((i11 - i) + i7) % i7];
                i16 -= iArr10[0];
                i15 -= iArr10[1];
                i14 -= iArr10[2];
                if (i17 == 0) {
                    iArr5[i18] = Math.min(i18 + i9, i5) * width;
                }
                i4 = iArr5[i18] + i17;
                iArr10[0] = iArr2[i4];
                iArr10[1] = iArr3[i4];
                iArr10[2] = iArr4[i4];
                i13 += iArr10[0];
                i20 += iArr10[1];
                i8 += iArr10[2];
                i10 += i13;
                i12 += i20;
                i19 += i8;
                i11 = (i11 + 1) % i7;
                iArr10 = iArr7[i11];
                i16 += iArr10[0];
                i15 += iArr10[1];
                i14 += iArr10[2];
                i13 -= iArr10[0];
                i20 -= iArr10[1];
                i8 -= iArr10[2];
                i3 += width;
            }
        }
        bitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(BlurRectView.class.getName());
    }
}

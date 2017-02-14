package com.meizu.cloud.base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.f;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

public class TabScrollIndicator extends View implements a {
    private final Paint a;
    private ViewPager b;
    private f c;
    private int d;
    private int e;
    private int f;
    private float g;
    private int h;
    private boolean i;
    private ViewGroup j;
    private HorizontalScrollView k;

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return a(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return a(i);
            }

            public SavedState a(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] a(int size) {
                return new SavedState[size];
            }
        };
        int a;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.a = in.readInt();
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.a);
        }
    }

    public TabScrollIndicator(Context context) {
        this(context, null);
    }

    public TabScrollIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabScrollIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.a = new Paint(1);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.b != null) {
            int count = this.b.getAdapter().b();
            if (count == 0) {
                return;
            }
            if (this.e >= count) {
                setCurrentItem(count - 1);
            } else if (this.j != null) {
                a(canvas);
            } else {
                a(canvas, count);
            }
        }
    }

    private void a(Canvas canvas, int count) {
        int paddingLeft = getPaddingLeft();
        float pageWidth = ((float) ((getWidth() - paddingLeft) - getPaddingRight())) / (1.0f * ((float) count));
        float left = ((float) paddingLeft) + ((((float) this.e) + this.g) * pageWidth);
        Canvas canvas2 = canvas;
        canvas2.drawRect(left, (float) getPaddingTop(), left + pageWidth, (float) (getHeight() - getPaddingBottom()), this.a);
    }

    private void a(Canvas canvas) {
        if (this.j != null && this.e < this.j.getChildCount()) {
            View currentView = this.j.getChildAt(this.e);
            float width = (float) currentView.getWidth();
            float top = (float) getPaddingTop();
            float bottom = (float) (getHeight() - getPaddingBottom());
            float left = ((float) currentView.getLeft()) + (this.g * width);
            float right = left + width;
            float deltaX = left - ((float) this.j.getChildAt(this.f).getLeft());
            if (deltaX < 0.0f) {
                right += this.g * (((float) this.j.getChildAt(this.f).getWidth()) - width);
            } else if (deltaX > 0.0f && this.e + 1 < this.j.getChildCount()) {
                right += this.g * (((float) this.j.getChildAt(this.e + 1).getWidth()) - width);
            }
            canvas.drawRect(left, top, right, bottom, this.a);
        }
    }

    public void setViewPager(ViewPager viewPager) {
        if (this.b != viewPager) {
            if (this.b != null) {
                this.b.setOnPageChangeListener(null);
            }
            if (viewPager.getAdapter() == null) {
                throw new IllegalStateException("ViewPager does not have adapter instance.");
            }
            this.b = viewPager;
            this.b.setOnPageChangeListener(this);
            invalidate();
        }
    }

    public void setViewPager(ViewPager view, int initialPosition) {
        Log.e("scroll", "setViewPager:" + initialPosition);
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    public void setCurrentItem(int item) {
        if (this.b == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        this.b.setCurrentItem(item);
        this.e = item;
        invalidate();
    }

    public void onPageScrollStateChanged(int state) {
        this.d = state;
        if (state == 0) {
            this.f = this.e;
            this.i = false;
            invalidate();
        }
        if (state == 1) {
            this.h = 0;
            this.i = true;
        }
        if (this.c != null) {
            this.c.onPageScrollStateChanged(state);
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position != this.e) {
            this.h = 0;
        }
        this.e = position;
        this.g = positionOffset;
        invalidate();
        if (this.i) {
            a();
        }
        if (this.c != null) {
            this.c.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    public void a(int position) {
        if (this.d == 0) {
            this.e = position;
            this.g = 0.0f;
            invalidate();
        }
        if (2 == this.d && !this.i) {
            b(position);
        }
        if (this.c != null) {
            this.c.a(position);
        }
    }

    private void a() {
        if (this.j != null && this.k != null) {
            int offset = 0;
            if (this.e < this.f) {
                if (this.e >= 0 && this.e < this.j.getChildCount()) {
                    offset = (int) (((float) this.j.getChildAt(this.e).getWidth()) * this.g);
                }
            } else if (this.e + 1 < this.j.getChildCount()) {
                offset = (int) (((float) this.j.getChildAt(this.e + 1).getWidth()) * this.g);
            }
            if (this.h != 0) {
                this.k.scrollBy(offset - this.h, 0);
            }
            this.h = offset;
        }
    }

    private void b(int position) {
        if (this.j != null && this.k != null) {
            int offset = 0;
            if (position < this.f) {
                offset = -(this.j.getChildAt(this.f).getLeft() - this.j.getChildAt(position).getLeft());
            } else if (position > this.f) {
                offset = this.j.getChildAt(position).getRight() - this.j.getChildAt(this.f).getRight();
            }
            this.k.smoothScrollBy(offset, 0);
        }
    }

    public void setOnPageChangeListener(f listener) {
        this.c = listener;
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.e = savedState.a;
        requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.a = this.e;
        return savedState;
    }

    public void setSelectedColor(int selectedColor) {
        this.a.setColor(selectedColor);
        invalidate();
    }

    public void setTabLayout(ViewGroup viewGroup) {
        this.j = viewGroup;
    }

    public void setScrollView(HorizontalScrollView scrollView) {
        this.k = scrollView;
    }
}

package com.meizu.cloud.app.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.BaseSavedState;
import android.widget.Button;
import com.meizu.cloud.app.utils.u;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.e;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.b.a.k;

public class RoundCornerProgressButton extends Button {
    private Context a;
    private int b;
    private int c;
    private int d;
    private Bitmap e;
    private Bitmap f;
    private StateListDrawable g;
    private StateListDrawable h;
    private Paint i;
    private Paint j;
    private LinearGradient k;
    private CharSequence l;
    private CharSequence m;
    private CharSequence n;
    private int o;
    private boolean p;

    public static class SavedState extends BaseSavedState {
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
        private int a;

        private SavedState(Parcel in) {
            super(in);
            this.a = in.readInt();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.a);
        }
    }

    public RoundCornerProgressButton(Context context) {
        this(context, null);
    }

    public RoundCornerProgressButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundCornerProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.b = -1;
        this.l = "";
        this.m = "";
        this.n = "";
        this.a = context;
        a(context, attrs);
    }

    private void a(Context context, AttributeSet attrs) {
        this.d = 0;
        this.c = 100;
        setGravity(17);
        this.g = new StateListDrawable();
        this.h = new StateListDrawable();
        this.g.addState(new int[0], a(e.rcpb_normal_bg_normal));
        this.h.addState(new int[0], a(e.rcpb_progress_bg_normal));
        this.i = new Paint();
        this.i.setXfermode(new PorterDuffXfermode(Mode.SRC_ATOP));
        this.j = new Paint(1);
        this.i.setAntiAlias(true);
        this.j.setTextSize(getResources().getDimension(d.rcpb_text_size));
        this.j.setColor(getResources().getColor(c.rcpb_normal_bg_color));
        if (attrs != null) {
            b(context, attrs);
        }
    }

    public void setRoundBtnColor(int color) {
        if (this.h != null && color != 0) {
            this.h = new StateListDrawable();
            this.h.addState(new int[0], u.a(this.a, color));
            this.o = color;
            this.p = true;
            invalidate();
        }
    }

    public void setCurrentText(CharSequence charSequence) {
        this.n = charSequence;
        this.b = -1;
        invalidate();
    }

    public void setProgressText(CharSequence charSequence, int progress) {
        if (progress < this.d) {
            this.b = this.d;
        } else if (progress > this.c) {
            this.b = this.c;
        } else {
            this.b = progress;
        }
        this.n = charSequence + getResources().getString(i.rcpb_downloaded, new Object[]{Integer.valueOf(this.b)});
        invalidate();
    }

    private void b(Context context, AttributeSet attrs) {
        TypedArray attr = a(context, attrs, k.RoundCornerProgressButton);
        if (attr != null) {
            try {
                this.m = attr.getString(k.RoundCornerProgressButton_textNormal);
                this.l = attr.getString(k.RoundCornerProgressButton_textComplete);
            } finally {
                attr.recycle();
            }
        }
    }

    private TypedArray a(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        }
        if (progress > this.c) {
            progress = this.c;
        }
        if (progress != this.b) {
            this.b = progress;
        }
        invalidate();
    }

    public int getProgress() {
        return this.b;
    }

    public int getMaxProgress() {
        return this.c;
    }

    public int getMinProgress() {
        return this.d;
    }

    public void setMaxProgress(int maxProgress) {
        this.c = maxProgress;
    }

    public void setMinProgress(int minProgress) {
        this.d = minProgress;
    }

    public void setCompleteText(CharSequence completeText) {
        this.l = completeText;
    }

    public void setCompleteText(int textResId) {
        this.l = getContext().getString(textResId);
    }

    public void setNormalText(CharSequence normalText) {
        this.m = normalText;
    }

    public void setNormalText(int normalTextResId) {
        this.m = getContext().getString(normalTextResId);
    }

    public CharSequence getNormalText() {
        return this.m;
    }

    public CharSequence getCompleteText() {
        return this.l;
    }

    protected Drawable a(int id) {
        return getResources().getDrawable(id);
    }

    private Bitmap a(Drawable drawable, int width, int height) {
        Bitmap bm = Bitmap.createBitmap(width, height, drawable.getOpacity() != -1 ? Config.ARGB_8888 : Config.RGB_565);
        Canvas canvas = new Canvas(bm);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bm;
    }

    @SuppressLint({"NewApi"})
    public void setBackgroundCompat(Drawable drawable) {
        int pL = getPaddingLeft();
        int pT = getPaddingTop();
        int pR = getPaddingRight();
        int pB = getPaddingBottom();
        if (VERSION.SDK_INT >= 16) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
        setPadding(pL, pT, pR, pB);
    }

    protected void onDraw(Canvas canvas) {
        if (this.e == null) {
            this.e = a(this.g, getMeasuredWidth(), getMeasuredHeight());
        }
        if (this.f == null || this.p) {
            this.f = a(this.h, getMeasuredWidth(), getMeasuredHeight());
            this.p = false;
        }
        b(canvas);
        if (this.b < this.d || this.b > this.c) {
            a(canvas, true);
        } else {
            a(canvas, false);
        }
        a(canvas);
    }

    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility != 0) {
            a();
        }
    }

    private void a() {
        if (this.e != null) {
            this.e.recycle();
            this.e = null;
        }
        if (this.f != null) {
            this.f.recycle();
            this.f = null;
            this.p = false;
        }
    }

    private void a(Canvas canvas) {
        float indicatorWidth = ((float) getMeasuredWidth()) * (((float) getProgress()) / ((float) getMaxProgress()));
        float y = ((float) (canvas.getHeight() / 2)) - ((this.j.descent() / 2.0f) + (this.j.ascent() / 2.0f));
        CharSequence text = "";
        if (this.b == this.d) {
            text = this.n;
        } else if (this.b == this.c) {
            text = this.n;
        } else {
            text = this.n;
        }
        int pateleColor = getResources().getColor(c.rcpb_normal_bg_color);
        if (this.o != 0) {
            pateleColor = this.o;
        }
        float textWidth = this.j.measureText(text.toString());
        if (indicatorWidth < 0.0f) {
            this.j.setColor(getResources().getColor(17170443));
        } else if (indicatorWidth <= (((float) getMeasuredWidth()) - textWidth) / 2.0f) {
            this.j.setColor(pateleColor);
            this.j.setShader(null);
        } else if (indicatorWidth <= (((float) getMeasuredWidth()) - textWidth) / 2.0f || indicatorWidth >= (((float) getMeasuredWidth()) + textWidth) / 2.0f) {
            this.j.setColor(getResources().getColor(17170443));
            this.j.setShader(null);
        } else {
            float coveredLength = (indicatorWidth - ((((float) getMeasuredWidth()) - textWidth) / 2.0f)) / textWidth;
            this.k = new LinearGradient((((float) getMeasuredWidth()) - textWidth) / 2.0f, 0.0f, (((float) getMeasuredWidth()) + textWidth) / 2.0f, 0.0f, new int[]{getResources().getColor(c.rcpb_normal_text_color), pateleColor}, new float[]{coveredLength, 0.001f + coveredLength}, TileMode.CLAMP);
            this.j.setShader(this.k);
        }
        canvas.drawText(text.toString(), (((float) getMeasuredWidth()) - textWidth) / 2.0f, y, this.j);
    }

    private void b(Canvas canvas) {
        canvas.saveLayer(0.0f, 0.0f, (float) getMeasuredWidth(), (float) getMeasuredHeight(), null, 31);
        canvas.drawBitmap(this.e, 0.0f, 0.0f, null);
    }

    private void a(Canvas canvas, boolean fromUser) {
        float indicatorWidth;
        if (this.b >= this.d && this.b <= this.c) {
            int progress = this.b;
        }
        if (fromUser) {
            indicatorWidth = (float) getMeasuredWidth();
        } else {
            indicatorWidth = ((float) getMeasuredWidth()) * (((float) getProgress()) / ((float) getMaxProgress()));
        }
        canvas.drawBitmap(this.f, indicatorWidth - ((float) getMeasuredWidth()), 0.0f, this.i);
        canvas.restore();
    }
}

package com.meizu.cloud.app.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View.BaseSavedState;
import android.view.animation.PathInterpolator;
import android.widget.TextView;
import com.alibaba.fastjson.asm.Opcodes;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.b.a.k;

public class AnimDownloadProgressButton extends TextView {
    private AnimatorSet A;
    private ValueAnimator B;
    private int C;
    private a a;
    private Paint b;
    private Paint c;
    private volatile Paint d;
    private Paint e;
    private Paint f;
    private Paint g;
    private float h;
    private int i;
    private int j;
    private float k;
    private float l;
    private int m;
    private int n;
    private float o;
    private float p;
    private float q;
    private float r;
    private CharSequence s;
    private CharSequence t;
    private CharSequence u;
    private CharSequence v;
    private float w;
    private RectF x;
    private LinearGradient y;
    private LinearGradient z;

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
        private int b;

        private SavedState(Parcel in) {
            super(in);
            this.a = in.readInt();
            this.b = in.readInt();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.a);
            out.writeInt(this.b);
        }
    }

    public interface a {
    }

    public AnimDownloadProgressButton(Context context) {
        this(context, null);
    }

    public AnimDownloadProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.k = 0.0f;
        this.l = 0.0f;
        setWillNotDraw(false);
        a(context, attrs);
        b();
        c();
    }

    private void a(Context context, AttributeSet attrs) {
        TypedArray attr = a(context, attrs, k.RoundCornerProgressButton);
        if (attr != null) {
            try {
                this.s = attr.getString(k.RoundCornerProgressButton_textNormal);
                this.t = attr.getString(k.RoundCornerProgressButton_textComplete);
            } finally {
                attr.recycle();
            }
        }
    }

    private void b() {
        setGravity(17);
        this.m = 100;
        this.n = 0;
        this.i = getResources().getColor(c.rcpb_normal_bg_color);
        this.j = getResources().getColor(c.rcpb_normal_bg_color);
        this.b = new Paint();
        this.b.setAntiAlias(true);
        this.b.setColor(this.i);
        this.b.setStyle(Style.FILL);
        this.c = new Paint();
        this.c.setAntiAlias(true);
        this.c.setColor(this.i);
        this.c.setStyle(Style.FILL);
        this.d = new Paint();
        this.d.setAntiAlias(true);
        this.d.setColor(getResources().getColor(c.white));
        this.d.setTextSize(getResources().getDimension(d.rcpb_text_size));
        this.e = new Paint();
        this.e.setAntiAlias(true);
        this.e.setColor(getResources().getColor(c.white));
        this.e.setTextSize(getResources().getDimension(d.rcpb_text_size));
        this.f = new Paint();
        this.f.setAntiAlias(true);
        this.f.setColor(getResources().getColor(c.white));
        this.f.setTextSize(getResources().getDimension(d.rcpb_text_size));
        this.g = new Paint();
        this.g.setAntiAlias(true);
        this.g.setColor(getResources().getColor(c.white));
        this.g.setTextSize(getResources().getDimension(d.rcpb_text_size));
        if (VERSION.SDK_INT >= 11) {
            setLayerType(1, this.d);
        }
        this.C = 0;
        invalidate();
    }

    private TypedArray a(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    private void c() {
        ValueAnimator dotMoveAnimation = ValueAnimator.ofFloat(new float[]{0.0f, 20.0f});
        dotMoveAnimation.setInterpolator(new PathInterpolator(0.11f, 0.0f, 0.12f, 1.0f));
        dotMoveAnimation.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ AnimDownloadProgressButton a;

            {
                this.a = r1;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                float transX = ((Float) animation.getAnimatedValue()).floatValue();
                this.a.q = transX;
                this.a.r = transX;
                this.a.invalidate();
            }
        });
        dotMoveAnimation.setDuration(1243);
        dotMoveAnimation.setRepeatMode(1);
        dotMoveAnimation.setRepeatCount(-1);
        final ValueAnimator dotalphaAnim = ValueAnimator.ofInt(new int[]{0, 1243}).setDuration(1243);
        dotalphaAnim.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ AnimDownloadProgressButton b;

            public void onAnimationUpdate(ValueAnimator animation) {
                int time = ((Integer) dotalphaAnim.getAnimatedValue()).intValue();
                int dot1alpha = this.b.b(time);
                int dot2alpha = this.b.a(time);
                this.b.f.setAlpha(dot1alpha);
                this.b.g.setAlpha(dot2alpha);
            }
        });
        dotalphaAnim.addListener(new AnimatorListener(this) {
            final /* synthetic */ AnimDownloadProgressButton a;

            {
                this.a = r1;
            }

            public void onAnimationStart(Animator animation) {
                this.a.f.setAlpha(0);
                this.a.g.setAlpha(0);
            }

            public void onAnimationEnd(Animator animation) {
                this.a.f.setAlpha(0);
                this.a.g.setAlpha(0);
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        dotalphaAnim.setRepeatMode(1);
        dotalphaAnim.setRepeatCount(-1);
        this.A = new AnimatorSet();
        this.A.play(dotMoveAnimation).with(dotalphaAnim);
        this.B = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f}).setDuration(500);
        this.B.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ AnimDownloadProgressButton a;

            {
                this.a = r1;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                this.a.k = ((this.a.l - this.a.k) * ((Float) animation.getAnimatedValue()).floatValue()) + this.a.k;
                this.a.invalidate();
            }
        });
    }

    private int a(int time) {
        if (time >= 0 && time <= 83) {
            return (int) (3.072289156626506d * ((double) time));
        }
        if (83 < time && time <= 1000) {
            return 255;
        }
        if (1000 < time && time <= 1083) {
            return (int) (-3.072289156626506d * ((double) (time - 1083)));
        }
        if (1083 >= time || time > 1243) {
            return 255;
        }
        return 0;
    }

    private int b(int time) {
        if (time >= 0 && time <= Opcodes.IF_ICMPNE) {
            return 0;
        }
        if (Opcodes.IF_ICMPNE < time && time <= 243) {
            return (int) (3.072289156626506d * ((double) (time - 160)));
        }
        if (243 < time && time <= 1160) {
            return 255;
        }
        if (1160 >= time || time > 1243) {
            return 255;
        }
        return (int) (-3.072289156626506d * ((double) (time - 1243)));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        a(canvas);
    }

    private void a(Canvas canvas) {
        b(canvas);
        c(canvas);
    }

    private void b(Canvas canvas) {
        this.x = new RectF();
        this.p = (float) (getMeasuredHeight() / 2);
        this.x.left = 2.0f;
        this.x.top = 2.0f;
        this.x.right = (float) (getMeasuredWidth() - 2);
        this.x.bottom = (float) (getMeasuredHeight() - 2);
        int pateleColor = getResources().getColor(c.rcpb_normal_bg_color);
        if (this.j != 0) {
            pateleColor = this.j;
        }
        switch (this.C) {
            case 0:
                this.b.setShader(null);
                this.b.setColor(pateleColor);
                canvas.drawRoundRect(this.x, this.p, this.p, this.b);
                return;
            case 1:
                this.o = this.k / (((float) this.m) + 0.0f);
                float f = 0.0f;
                float f2 = 0.0f;
                this.y = new LinearGradient(0.0f, f, (float) getMeasuredWidth(), f2, new int[]{pateleColor, getResources().getColor(c.block_divider_viewbg_color)}, new float[]{this.o, this.o + 0.001f}, TileMode.CLAMP);
                this.b.setShader(this.y);
                this.b.setColor(pateleColor);
                canvas.drawRoundRect(this.x, this.p, this.p, this.b);
                return;
            case 2:
                this.b.setShader(null);
                this.b.setColor(pateleColor);
                canvas.drawRoundRect(this.x, this.p, this.p, this.b);
                return;
            case 3:
                canvas.drawRoundRect(this.x, this.p, this.p, this.b);
                return;
            default:
                return;
        }
    }

    private void c(Canvas canvas) {
        float y = ((float) (canvas.getHeight() / 2)) - ((this.d.descent() / 2.0f) + (this.d.ascent() / 2.0f));
        if (this.u == null) {
            this.u = "";
        }
        float textWidth = this.d.measureText(this.u.toString());
        if (this.e == null || this.v == null) {
            this.w = textWidth;
        } else {
            this.w = this.e.measureText(this.v.toString());
        }
        int pateleColor = getResources().getColor(c.rcpb_normal_bg_color);
        if (this.j != 0) {
            pateleColor = this.j;
        }
        switch (this.C) {
            case 0:
                this.d.setShader(null);
                this.d.setColor(getResources().getColor(c.rcpb_normal_text_color));
                canvas.drawText(this.u.toString(), (((float) getMeasuredWidth()) - textWidth) / 2.0f, y, this.d);
                return;
            case 1:
                if (this.u.toString().contains(getResources().getString(i.roundbtn_update_downloaded)) && this.h == 0.0f) {
                    this.h = textWidth;
                }
                float coverlength = ((float) getMeasuredWidth()) * this.o;
                float indicator1 = ((float) (getMeasuredWidth() / 2)) - (textWidth / 2.0f);
                float indicator2 = ((float) (getMeasuredWidth() / 2)) + (textWidth / 2.0f);
                float textProgress = (((textWidth / 2.0f) - ((float) (getMeasuredWidth() / 2))) + coverlength) / textWidth;
                if (coverlength <= indicator1) {
                    this.d.setShader(null);
                    this.d.setColor(pateleColor);
                } else if (indicator1 >= coverlength || coverlength > indicator2) {
                    this.d.setShader(null);
                    this.d.setColor(getResources().getColor(c.rcpb_normal_text_color));
                } else {
                    this.z = new LinearGradient((((float) getMeasuredWidth()) - textWidth) / 2.0f, 0.0f, (((float) getMeasuredWidth()) + textWidth) / 2.0f, 0.0f, new int[]{getResources().getColor(c.rcpb_normal_text_color), pateleColor}, new float[]{textProgress, 0.001f + textProgress}, TileMode.CLAMP);
                    this.d.setColor(getResources().getColor(c.rcpb_normal_text_color));
                    this.d.setShader(this.z);
                }
                if (this.u.toString().contains(getResources().getString(i.roundbtn_update_downloaded))) {
                    canvas.drawText(this.u.toString(), (((float) getMeasuredWidth()) - this.h) / 2.0f, y, this.d);
                    return;
                }
                canvas.drawText(this.u.toString(), (((float) getMeasuredWidth()) - textWidth) / 2.0f, y, this.d);
                return;
            case 2:
                this.d.setShader(null);
                this.d.setColor(getResources().getColor(c.rcpb_normal_text_color));
                canvas.drawText(this.u.toString(), (((float) getMeasuredWidth()) - textWidth) / 2.0f, y, this.d);
                canvas.drawCircle((((((float) getMeasuredWidth()) + textWidth) / 2.0f) + 4.0f) + this.q, y, 4.0f, this.f);
                canvas.drawCircle((((((float) getMeasuredWidth()) + textWidth) / 2.0f) + 24.0f) + this.r, y, 4.0f, this.g);
                return;
            case 3:
                if (this.v != null) {
                    canvas.drawText(this.v.toString(), (((float) getMeasuredWidth()) - this.w) / 2.0f, y, this.e);
                }
                this.d.setColor(this.j);
                canvas.drawText(this.u.toString(), (((float) getMeasuredWidth()) - textWidth) / 2.0f, y, this.d);
                return;
            default:
                return;
        }
    }

    public int getState() {
        return this.C;
    }

    public void setState(int mState) {
        if (this.C != mState) {
            if (mState == 1 && this.C == 0) {
                this.C = mState;
            } else {
                this.C = mState;
                invalidate();
                Log.w("tan", "把状态改为" + mState);
            }
            if (mState == 2) {
                this.A.start();
                this.B.removeAllUpdateListeners();
            } else if (mState == 0) {
                this.A.cancel();
                this.B.removeAllUpdateListeners();
            } else if (mState == 1) {
                this.A.cancel();
            }
        }
    }

    public void setCurrentText(CharSequence charSequence) {
        this.v = this.u;
        this.u = charSequence;
        invalidate();
    }

    public void setProgressText(CharSequence charSequence, float progress) {
        this.v = this.u;
        if (progress >= ((float) this.n) && progress <= ((float) this.m)) {
            this.u = charSequence + getResources().getString(i.rcpb_downloaded, new Object[]{Integer.valueOf((int) progress)});
            this.l = progress;
            if (this.B.isRunning()) {
                this.B.resume();
                this.B.start();
                return;
            }
            this.B.start();
        } else if (progress < ((float) this.n)) {
            this.k = 0.0f;
        } else if (progress > ((float) this.m)) {
            this.k = 100.0f;
        }
    }

    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        }
        if (progress > this.m) {
            progress = this.m;
        }
        if (((float) progress) != this.k) {
            this.l = (float) progress;
        }
    }

    public void a() {
        this.B.cancel();
        this.B.removeAllUpdateListeners();
        this.A.cancel();
        this.A.removeAllListeners();
    }

    public float getProgress() {
        return this.k;
    }

    public int getMaxProgress() {
        return this.m;
    }

    public int getMinProgress() {
        return this.n;
    }

    public void setRoundBtnColor(int color) {
        this.j = color;
        invalidate();
    }

    public void setAnimationButtonListener(a animationButtonListener) {
        if (animationButtonListener != null) {
            this.a = animationButtonListener;
        }
    }
}

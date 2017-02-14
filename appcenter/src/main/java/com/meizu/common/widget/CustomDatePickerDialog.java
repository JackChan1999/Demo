package com.meizu.common.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import com.meizu.common.a.e;
import com.meizu.common.widget.DatePicker.b;

public class CustomDatePickerDialog extends AlertDialog implements OnClickListener, b {
    private final DatePicker a;
    private final a b;

    public static class FlipView extends View {
        float[] a;
        float[] b;
        private Rect c;
        private Matrix[] d;
        private Bitmap e;
        private Bitmap f;
        private int g;
        private int h;
        private float i;
        private boolean j;
        private Paint k;

        public FlipView(Context context) {
            super(context);
            this.i = 0.0f;
            this.j = false;
            this.a = new float[8];
            this.b = new float[8];
            a();
        }

        public FlipView(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.i = 0.0f;
            this.j = false;
            this.a = new float[8];
            this.b = new float[8];
            a();
        }

        private void a() {
            this.c = new Rect(0, 0, this.g, this.h);
            this.d = new Matrix[2];
            this.f = BitmapFactory.decodeResource(getResources(), e.mc_ic_popup_calendar_gregorian);
            this.e = BitmapFactory.decodeResource(getResources(), e.mc_ic_popup_calendar_lunar);
            this.g = this.e.getWidth();
            this.h = this.e.getHeight();
            for (int i = 0; i < 2; i++) {
                this.d[i] = new Matrix();
            }
            this.k = new Paint();
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0.8f);
            this.k.setColorFilter(new ColorMatrixColorFilter(cm));
        }

        public void onDraw(Canvas canvas) {
            b();
            if (this.i == 0.0f) {
                canvas.drawBitmap(this.j ? this.e : this.f, 0.0f, 0.0f, null);
            } else if (this.i == 1.0f) {
                canvas.drawBitmap(this.j ? this.f : this.e, 0.0f, 0.0f, null);
            } else {
                int i;
                Bitmap flipBitmap;
                this.c.set(0, 0, this.g, this.h);
                canvas.drawBitmap(this.e, this.c, this.c, this.k);
                this.c.set(0, 0, this.g / 2, this.h);
                canvas.drawBitmap(this.f, this.c, this.c, null);
                canvas.save();
                if (this.j) {
                    if (this.i < 0.5f) {
                        i = 0;
                        this.c.set(0, 0, this.g / 2, this.h);
                        flipBitmap = this.e;
                    } else {
                        i = 1;
                        this.c.set(this.g / 2, 0, this.g, this.h);
                        flipBitmap = this.f;
                    }
                } else if (this.i < 0.5f) {
                    i = 1;
                    this.c.set(this.g / 2, 0, this.g, this.h);
                    flipBitmap = this.f;
                } else {
                    i = 0;
                    this.c.set(0, 0, this.g / 2, this.h);
                    flipBitmap = this.e;
                }
                canvas.concat(this.d[i]);
                canvas.clipRect(0, 0, this.c.right - this.c.left, this.c.bottom - this.c.top);
                canvas.translate((float) (-this.c.left), 0.0f);
                canvas.drawBitmap(flipBitmap, 0.0f, 0.0f, null);
                canvas.restore();
            }
        }

        private void b() {
            float scale;
            int k;
            float translateWidthPerFold = (float) Math.round((((float) this.g) * (1.0f - this.i)) / 2.0f);
            float scaleHeight = ((float) this.h) * ((1500.0f + ((float) Math.sqrt((double) (((float) ((this.g / 2) * (this.g / 2))) - (translateWidthPerFold * translateWidthPerFold))))) / 1500.0f);
            this.a[0] = 0.0f;
            this.a[1] = 0.0f;
            this.a[2] = 0.0f;
            this.a[3] = (float) this.h;
            this.a[4] = (float) (this.g / 2);
            this.a[5] = 0.0f;
            this.a[6] = (float) (this.g / 2);
            this.a[7] = (float) this.h;
            if (((double) this.i) < 0.5d) {
                scale = this.i;
            } else {
                scale = 1.0f - this.i;
            }
            this.d[0].reset();
            this.b[0] = ((float) this.g) * scale;
            this.b[1] = (((float) this.h) - scaleHeight) / 2.0f;
            this.b[2] = this.b[0];
            this.b[3] = ((float) this.h) + ((scaleHeight - ((float) this.h)) / 2.0f);
            this.b[4] = (float) (this.g / 2);
            this.b[5] = 0.0f;
            this.b[6] = this.b[4];
            this.b[7] = (float) this.h;
            for (k = 0; k < 8; k++) {
                this.b[k] = (float) Math.round(this.b[k]);
            }
            this.d[0].setPolyToPoly(this.a, 0, this.b, 0, 4);
            this.d[1].reset();
            this.b[0] = (float) (this.g / 2);
            this.b[1] = 0.0f;
            this.b[2] = this.b[0];
            this.b[3] = (float) this.h;
            this.b[4] = ((float) this.g) - (((float) this.g) * scale);
            this.b[5] = (((float) this.h) - scaleHeight) / 2.0f;
            this.b[6] = this.b[4];
            this.b[7] = ((float) this.h) + ((scaleHeight - ((float) this.h)) / 2.0f);
            for (k = 0; k < 8; k++) {
                this.b[k] = (float) Math.round(this.b[k]);
            }
            this.d[1].setPolyToPoly(this.a, 0, this.b, 0, 4);
        }

        public void setFoldFactor(float pFoldFactor) {
            this.i = pFoldFactor;
            postInvalidate();
        }

        public void setFilpViewPrefer(boolean isLunar) {
            this.j = isLunar;
        }
    }

    public interface a {
        void a(DatePicker datePicker, int i, int i2, int i3);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onClick(DialogInterface dialog, int which) {
        if (this.b != null) {
            this.a.clearFocus();
            this.b.a(this.a, this.a.getYear(), this.a.getMonth(), this.a.getDayOfMonth());
        }
    }

    public void a(DatePicker view, int year, int month, int day) {
        this.a.a(year, month, day, null, true);
    }

    public Bundle onSaveInstanceState() {
        Bundle state = super.onSaveInstanceState();
        state.putInt("year", this.a.getYear());
        state.putInt("month", this.a.getMonth());
        state.putInt("day", this.a.getDayOfMonth());
        return state;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.a.a(savedInstanceState.getInt("year"), savedInstanceState.getInt("month"), savedInstanceState.getInt("day"), (b) this, false);
    }
}

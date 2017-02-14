package com.meizu.cloud.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.meizu.cloud.b.a.b;
import com.meizu.cloud.b.a.e;

public class CircleImageView extends ImageView {
    private static final ScaleType a = ScaleType.CENTER_CROP;
    private static final Config b = Config.ARGB_8888;
    private static int c = -12339861;
    private int A;
    private BitmapShader B;
    private Matrix C;
    private a[] D;
    private RectF d;
    private RectF e;
    private Paint f;
    private Paint g;
    private Paint h;
    private Paint i;
    private Paint j;
    private int k;
    private int l;
    private int m;
    private int n;
    private float o;
    private float p;
    private ColorFilter q;
    private boolean r;
    private boolean s;
    private boolean t;
    private Drawable u;
    private float v;
    private int w;
    private String x;
    private float y;
    private Bitmap z;

    private class a {
        public Bitmap a = null;
        public BitmapShader b = null;
        public Matrix c = null;
        public int d = CircleImageView.c;
        public boolean e = false;
        final /* synthetic */ CircleImageView f;

        public a(CircleImageView circleImageView) {
            this.f = circleImageView;
        }
    }

    public CircleImageView(Context context) {
        super(context);
        this.d = new RectF();
        this.e = new RectF();
        this.f = new Paint();
        this.g = new Paint();
        this.h = new Paint();
        this.i = new Paint();
        this.j = new Paint();
        this.k = -328966;
        this.l = 0;
        this.t = false;
        this.u = null;
        this.v = 1.0f;
        this.w = 1;
        this.x = "";
        this.y = 0.0f;
        this.A = c;
        this.B = null;
        this.C = new Matrix();
        this.D = new a[2];
        b();
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.d = new RectF();
        this.e = new RectF();
        this.f = new Paint();
        this.g = new Paint();
        this.h = new Paint();
        this.i = new Paint();
        this.j = new Paint();
        this.k = -328966;
        this.l = 0;
        this.t = false;
        this.u = null;
        this.v = 1.0f;
        this.w = 1;
        this.x = "";
        this.y = 0.0f;
        this.A = c;
        this.B = null;
        this.C = new Matrix();
        this.D = new a[2];
        b();
    }

    private void b() {
        super.setScaleType(a);
        if (getDrawable() == null) {
            setImageDrawable(getDefaultDrawable());
        }
        for (int i = 0; i < 2; i++) {
            this.D[i] = new a(this);
        }
        this.i.setStyle(Style.FILL_AND_STROKE);
        this.i.setAntiAlias(true);
        this.i.setColor(c);
        this.j.setAntiAlias(true);
        this.j.setTextAlign(Align.CENTER);
        this.j.setColor(-1);
        this.g.setStyle(Style.STROKE);
        this.g.setAntiAlias(true);
        this.g.setColor(this.k);
        this.g.setStrokeWidth((float) this.l);
        this.h.setStyle(Style.STROKE);
        this.h.setAntiAlias(true);
        this.h.setColor(419430400);
        this.h.setStrokeWidth(1.0f);
        this.v = getResources().getDisplayMetrics().density;
        this.r = true;
        this.l = ((int) this.v) - 2;
        if (this.s) {
            c();
            this.s = false;
        }
    }

    public ScaleType getScaleType() {
        return a;
    }

    public void setScaleType(ScaleType scaleType) {
        if (scaleType != a) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", new Object[]{scaleType}));
        }
    }

    public void setAdjustViewBounds(boolean adjustViewBounds) {
        if (adjustViewBounds) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(d(), 0.0f, 0.0f, null);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        c();
    }

    public void setBorderColor(int borderColor) {
        if (borderColor != this.k) {
            this.k = borderColor;
            this.g.setColor(this.k);
            invalidate();
        }
    }

    public void setImageDrawable(Drawable drawable) {
        if (getDrawable() == drawable) {
            this.x = "";
            invalidate();
            return;
        }
        if (drawable == null) {
            drawable = getDefaultDrawable();
            this.A = c;
        }
        super.setImageDrawable(drawable);
        this.z = a(drawable);
        this.x = "";
        c();
    }

    public void setColorFilter(ColorFilter cf) {
        if (cf != this.q) {
            this.q = cf;
            this.f.setColorFilter(this.q);
            invalidate();
        }
    }

    private Drawable getDefaultDrawable() {
        if (this.u == null) {
            this.u = getResources().getDrawable(e.mc_contact_list_picture);
        }
        return this.u;
    }

    private Bitmap a(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            if (drawable instanceof ColorDrawable) {
                return Bitmap.createBitmap(2, 2, b);
            }
            return Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), b);
        } catch (OutOfMemoryError e) {
            Log.e("CircleImage ", "getBitmapFromDrawable  OutOfMemoryError !");
            return null;
        }
    }

    private void c() {
        if (!this.r) {
            this.s = true;
        } else if (this.z != null) {
            float scale;
            this.n = this.z.getHeight();
            this.m = this.z.getWidth();
            this.e.set(0.0f, 0.0f, (float) ((getWidth() * 3) / (this.w + 2)), (float) ((getHeight() * 3) / (this.w + 2)));
            this.p = Math.min(((this.e.height() - ((float) this.l)) - 1.0f) / 2.0f, ((this.e.width() - ((float) this.l)) - 1.0f) / 2.0f);
            this.d.set(this.e);
            if (!this.t) {
                this.d.inset((float) this.l, (float) this.l);
            }
            this.o = Math.min(this.d.height() / 2.0f, this.d.width() / 2.0f);
            float[] itemScale = new float[2];
            if (this.w == 3) {
                this.y = (float) (((getWidth() * 2) / 15) + this.l);
            }
            if (this.w == 2) {
                this.y = (float) ((getWidth() / 8) - this.l);
            }
            if (this.w == 1) {
                this.y = 0.0f;
            }
            this.B = new BitmapShader(this.z, TileMode.CLAMP, TileMode.CLAMP);
            if (this.D[0].a != null) {
                this.D[0].b = new BitmapShader(this.D[0].a, TileMode.CLAMP, TileMode.CLAMP);
            }
            if (this.D[1].a != null) {
                this.D[1].b = new BitmapShader(this.D[1].a, TileMode.CLAMP, TileMode.CLAMP);
            }
            if (((float) this.m) * this.d.height() > this.d.width() * ((float) this.n)) {
                scale = this.d.height() / ((float) this.n);
            } else {
                scale = this.d.width() / ((float) this.m);
            }
            this.C.setScale(scale, scale);
            this.C.postTranslate(((((float) getWidth()) - this.d.width()) / 2.0f) - this.y, (((float) getHeight()) - this.d.height()) / 2.0f);
            for (int i = 0; i < this.w - 1; i++) {
                if (this.D[i].a != null) {
                    if (((float) this.D[i].a.getWidth()) * this.d.height() > this.d.width() * ((float) this.D[i].a.getHeight())) {
                        itemScale[i] = this.d.height() / ((float) this.D[i].a.getHeight());
                    } else {
                        itemScale[i] = this.d.width() / ((float) this.D[i].a.getWidth());
                    }
                    this.D[i].c = new Matrix();
                    this.D[i].c.setScale(itemScale[i], itemScale[i]);
                    if (i == 1) {
                        this.D[i].c.postTranslate((((float) getWidth()) - this.d.width()) / 2.0f, (((float) getHeight()) - this.d.height()) / 2.0f);
                    } else {
                        this.D[i].c.postTranslate(((((float) getWidth()) - this.d.width()) / 2.0f) + this.y, (((float) getHeight()) - this.d.height()) / 2.0f);
                    }
                    this.D[i].b.setLocalMatrix(this.D[i].c);
                }
            }
            this.B.setLocalMatrix(this.C);
            this.f.setAntiAlias(true);
            this.f.setShader(this.B);
            invalidate();
        }
    }

    public void setIconCount(int num) {
        if (num >= 1) {
            if (num > 3) {
                num = 3;
            }
            this.w = num;
            c();
        }
    }

    public int getmIconCount() {
        return this.w;
    }

    public void setBadgeText(String badgeText) {
        this.x = b(badgeText);
        this.A = a(this.x);
        invalidate();
    }

    private int a(String colorText) {
        TypedArray colorArray = getResources().obtainTypedArray(b.mc_colorful_round);
        int result = c;
        int index = 0;
        if (!TextUtils.isEmpty(colorText)) {
            index = Math.abs(colorText.hashCode()) % colorArray.length();
        }
        if (index < colorArray.length()) {
            result = colorArray.getColor(index, c);
        }
        colorArray.recycle();
        return result;
    }

    private String b(String checkText) {
        String result = "";
        if (TextUtils.isEmpty(checkText)) {
            return "";
        }
        String text = checkText.trim();
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        String firstLetter = text.substring(0, 1);
        char c = firstLetter.charAt(0);
        if ('a' <= c && c <= 'z') {
            firstLetter = firstLetter.toUpperCase();
        }
        return firstLetter;
    }

    public void setMultiImageDrawable(Drawable[] drawable) {
        int len = 3;
        super.setImageDrawable(drawable[0]);
        if (drawable.length <= 3) {
            len = drawable.length;
        }
        this.w = Math.max(this.w, len);
        for (int i = 0; i < len; i++) {
            if (i == 0) {
                if (drawable[i] != null) {
                    this.z = a(drawable[i]);
                } else {
                    this.z = a(getDefaultDrawable());
                }
                this.x = "";
            } else {
                this.D[(len - i) - 1].a = a(drawable[i]);
                this.D[(len - i) - 1].e = true;
            }
        }
        c();
    }

    public void setMultiBadgeText(String[] badgeText) {
        int len = 3;
        if (badgeText.length <= 3) {
            len = badgeText.length;
        }
        this.w = Math.max(this.w, len);
        for (int i = 0; i < len; i++) {
            if (i == 0) {
                this.x = b(badgeText[i]);
                this.A = a(badgeText[i]);
            } else {
                this.D[(len - i) - 1].d = a(badgeText[i]);
                this.D[(len - i) - 1].e = false;
            }
        }
        c();
    }

    public String getBadgeText() {
        return this.x;
    }

    public void setItemByIndex(int index, Object obj) {
        if (index == -1 && (obj instanceof String)) {
            this.A = a(b((String) obj));
            setImageDrawable(this.u);
        }
        if (index >= 0 && index <= this.w - 1) {
            if (index == 0) {
                if (obj instanceof String) {
                    this.x = b((String) obj);
                    this.A = a(this.x);
                    invalidate();
                } else if (obj instanceof Drawable) {
                    setImageDrawable((Drawable) obj);
                }
            } else if (obj instanceof String) {
                this.D[(this.w - index) - 1].d = a((String) obj);
                this.D[(this.w - index) - 1].e = false;
                c();
            } else if (obj instanceof Drawable) {
                this.D[(this.w - index) - 1].a = a((Drawable) obj);
                this.D[(this.w - index) - 1].e = true;
                c();
            }
        }
    }

    private Bitmap d() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        for (int i = 1; i <= this.w; i++) {
            if (i == this.w) {
                this.i.setColor(this.A);
                if (TextUtils.isEmpty(this.x)) {
                    if (this.z != null && this.z.equals(((BitmapDrawable) getDefaultDrawable()).getBitmap())) {
                        canvas.drawCircle(((float) (getWidth() / 2)) - this.y, (float) (getHeight() / 2), this.o, this.i);
                    }
                    canvas.drawCircle(((float) (getWidth() / 2)) - this.y, (float) (getHeight() / 2), this.o, this.f);
                } else {
                    canvas.drawCircle(((float) (getWidth() / 2)) - this.y, (float) (getHeight() / 2), this.o, this.i);
                    this.j.setTextSize(this.o);
                    Rect bounds = new Rect();
                    this.j.getTextBounds(this.x, 0, this.x.length(), bounds);
                    canvas.drawText(this.x, ((float) (getWidth() / 2)) - this.y, ((float) ((getHeight() + bounds.height()) / 2)) - this.v, this.j);
                }
                if (this.l != 0) {
                    canvas.drawCircle(((float) (getWidth() / 2)) - this.y, (float) (getHeight() / 2), this.p, this.g);
                    canvas.drawCircle(((float) (getWidth() / 2)) - this.y, (float) (getHeight() / 2), this.o, this.h);
                }
            } else if (i == 1) {
                canvas.save();
                if (this.l != 0) {
                    canvas.drawCircle(((float) (getWidth() / 2)) + this.y, (float) (getHeight() / 2), this.p, this.g);
                    canvas.drawCircle(((float) (getWidth() / 2)) + this.y, (float) (getHeight() / 2), this.o, this.h);
                }
                if (!this.D[0].e || this.D[0].a == null) {
                    this.i.setColor(this.D[0].d);
                    canvas.drawCircle(((float) (getWidth() / 2)) + this.y, (float) (getHeight() / 2), this.o, this.i);
                } else {
                    this.f.setShader(this.D[0].b);
                    canvas.drawCircle(((float) (getWidth() / 2)) + this.y, (float) (getHeight() / 2), this.o, this.f);
                    this.f.setShader(this.B);
                }
                canvas.restore();
            } else if (i == 2) {
                canvas.save();
                if (this.l != 0) {
                    canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), this.p, this.g);
                    canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), this.o, this.h);
                }
                if (!this.D[1].e || this.D[1].a == null) {
                    this.i.setColor(this.D[1].d);
                    canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), this.o, this.i);
                } else {
                    this.f.setShader(this.D[1].b);
                    canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), this.o, this.f);
                    this.f.setShader(this.B);
                }
                canvas.restore();
            }
        }
        return bitmap;
    }

    public void setBorderWidth(int width) {
        this.l = width;
    }
}

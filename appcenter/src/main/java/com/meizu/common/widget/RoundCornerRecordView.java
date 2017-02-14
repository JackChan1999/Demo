package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.meizu.common.a.c;
import com.meizu.common.a.d;
import com.meizu.common.a.e;
import com.meizu.common.a.j;
import org.apache.commons.io.FileUtils;

public class RoundCornerRecordView extends ImageView {
    private static boolean P = false;
    private static final Object Q = new Object();
    private static final a[] R = new a[]{a.BORDER_NULL, a.BORDER_LIST_CONTACT, a.BORDER_EDIT_CONTACT, a.BORDER_VIEW_CONTACT, a.BORDER_SMS_CONTACT, a.BORDER_SMALL_CONTACT};
    private static final b[] S = new b[]{b.IC_NULL, b.IC_CALL_LOG_CALLOUT, b.IC_CALL_LOG_CALLIN, b.IC_CALL_LOG_MISSED, b.IC_CALL_LOG_REFUSED, b.IC_CALL_LOG_RINGONCE, b.IC_CALL_LOG_RECORD, b.IC_CALL_LOG_RECORD_FAIL, b.IC_CALL_LOG_VOICEMAIL, b.IC_SMS_HAS_UNREAD, b.IC_SMS_HAS_NOTDELIVERED};
    private Paint A;
    private int B;
    private CharSequence C;
    private CharSequence D;
    private Drawable E;
    private String F;
    private int G;
    private int H;
    private int I;
    private Paint J;
    private int K;
    private int L;
    private boolean M;
    private boolean N;
    private boolean O;
    private a a;
    private b b;
    private Rect c;
    private Bitmap d;
    private Drawable e;
    private Drawable f;
    private int g;
    private int h;
    private int i;
    private int j;
    private boolean k;
    private String l;
    private boolean m;
    private boolean n;
    private boolean o;
    private CharSequence p;
    private long q;
    private Bundle r;
    private String s;
    private long t;
    private Bundle u;
    private Drawable v;
    private Drawable w;
    private Drawable x;
    private Drawable y;
    private Drawable z;

    public enum a {
        BORDER_NULL(0),
        BORDER_LIST_CONTACT(1),
        BORDER_EDIT_CONTACT(2),
        BORDER_VIEW_CONTACT(3),
        BORDER_SMS_CONTACT(4),
        BORDER_SMALL_CONTACT(5);
        
        final int g;

        private a(int value) {
            this.g = value;
        }
    }

    public enum b {
        IC_NULL(0),
        IC_CALL_LOG_CALLOUT(1),
        IC_CALL_LOG_CALLIN(2),
        IC_CALL_LOG_MISSED(3),
        IC_CALL_LOG_REFUSED(4),
        IC_CALL_LOG_RINGONCE(5),
        IC_CALL_LOG_RECORD(6),
        IC_CALL_LOG_RECORD_FAIL(7),
        IC_CALL_LOG_VOICEMAIL(8),
        IC_SMS_HAS_UNREAD(9),
        IC_SMS_HAS_NOTDELIVERED(10);
        
        final int l;

        private b(int value) {
            this.l = value;
        }
    }

    public RoundCornerRecordView(Context context) {
        this(context, null);
    }

    public RoundCornerRecordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundCornerRecordView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.a = null;
        this.b = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = 0;
        this.h = 0;
        this.i = 0;
        this.j = 0;
        this.k = false;
        this.m = false;
        this.n = false;
        this.o = false;
        this.K = -1;
        this.M = true;
        this.O = true;
        TypedArray a = context.obtainStyledAttributes(attrs, j.RoundCornerContactBadge, defStyle, 0);
        int borderTypeIndex = a.getInt(j.RoundCornerContactBadge_mcBorderType, a.BORDER_NULL.g);
        int iconTypeIndex = a.getInt(j.RoundCornerContactBadge_mcIconType, b.IC_CALL_LOG_RECORD.l);
        this.L = getResources().getColor(c.mc_round_colorfulbg_color);
        a.recycle();
        setBorderType(R[borderTypeIndex]);
        setIconType(S[iconTypeIndex]);
        a();
    }

    private void a() {
        super.setScaleType(ScaleType.FIT_CENTER);
        setDuplicateParentStateEnabled(false);
        this.c = new Rect();
        this.y = getResources().getDrawable(e.mc_contact_list_picture_shadow);
    }

    public void setBackgroundColorId(String colorId) {
        TypedArray colorArray = getResources().obtainTypedArray(com.meizu.common.a.a.mc_colorful_round);
        int index = Math.abs(colorId.hashCode()) % colorArray.length();
        if (index < colorArray.length()) {
            this.K = colorArray.getColor(index, this.L);
        }
        colorArray.recycle();
    }

    public void setImageResource(int resId) {
        if (resId == 0) {
            setImageDrawable(null);
            return;
        }
        Drawable oldDrawable = getDrawable();
        super.setImageResource(resId);
        Bitmap bmp = null;
        Drawable d = getDrawable();
        if (d != null && (d instanceof BitmapDrawable)) {
            bmp = ((BitmapDrawable) d).getBitmap();
        }
        if (!(this.d == null || this.d == bmp)) {
            this.d.recycle();
            this.d = null;
        }
        if (this.N && (oldDrawable instanceof BitmapDrawable)) {
            ((BitmapDrawable) oldDrawable).getBitmap().recycle();
        }
        this.N = false;
    }

    public void setImageURI(Uri uri) {
        if (uri == null) {
            setImageDrawable(null);
            return;
        }
        Drawable oldDrawable = getDrawable();
        super.setImageURI(uri);
        Bitmap bmp = null;
        Drawable d = getDrawable();
        if (d != null && (d instanceof BitmapDrawable)) {
            bmp = ((BitmapDrawable) d).getBitmap();
        }
        if (!(this.d == null || this.d == bmp)) {
            this.d.recycle();
            this.d = null;
        }
        if (this.N && (oldDrawable instanceof BitmapDrawable)) {
            ((BitmapDrawable) oldDrawable).getBitmap().recycle();
        }
        this.N = false;
    }

    public void setImageBitmap(Bitmap bm, boolean recycle) {
        if (bm == null) {
            setImageDrawable(null);
            return;
        }
        super.setImageBitmap(bm);
        this.N = recycle;
    }

    public void setImageBitmap(Bitmap bm) {
        if (bm == null) {
            setImageDrawable(null);
        } else {
            super.setImageBitmap(bm);
        }
    }

    public void setImageDrawable(Drawable drawable) {
        Drawable oldDrawable = getDrawable();
        if (drawable == null) {
            drawable = this.z;
        }
        super.setImageDrawable(drawable);
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) drawable).getBitmap();
        }
        if (!(this.d == null || this.d == bmp)) {
            this.d.recycle();
            this.d = null;
        }
        if (this.N && (oldDrawable instanceof BitmapDrawable)) {
            ((BitmapDrawable) oldDrawable).getBitmap().recycle();
        }
        this.N = false;
    }

    private Drawable getDefaultDrawable() {
        switch (this.a) {
            case BORDER_SMALL_CONTACT:
                return getResources().getDrawable(e.mc_contact_small_picture);
            default:
                return getResources().getDrawable(e.mc_contact_list_picture);
        }
    }

    private boolean a(Drawable drawable) {
        if (((BitmapDrawable) drawable).getBitmap().equals(((BitmapDrawable) this.z).getBitmap())) {
            return true;
        }
        return false;
    }

    public void setScaleType(ScaleType scaleType) {
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.k) {
            int width = this.g;
            int height = this.h;
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, FileUtils.ONE_GB);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, FileUtils.ONE_GB);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.c.set(0, 0, super.getMeasuredWidth(), super.getMeasuredHeight());
    }

    public void setIconText(CharSequence text) {
        if (!TextUtils.equals(this.p, text)) {
            this.p = text;
            invalidate();
        }
    }

    public void setIconType(b iconType) {
        if (iconType == null) {
            throw new NullPointerException();
        } else if (this.b != iconType) {
            this.b = iconType;
            switch (this.b) {
                case IC_CALL_LOG_CALLOUT:
                    this.e = getResources().getDrawable(e.mc_sym_call_list_outgoing);
                    break;
                case IC_CALL_LOG_CALLIN:
                    this.e = getResources().getDrawable(e.mc_sym_call_list_incoming);
                    break;
                case IC_CALL_LOG_MISSED:
                    this.e = getResources().getDrawable(e.mc_sym_call_list_missed);
                    break;
                case IC_CALL_LOG_REFUSED:
                    this.e = getResources().getDrawable(e.mc_sym_call_list_reject);
                    break;
                case IC_CALL_LOG_RINGONCE:
                    this.e = getResources().getDrawable(e.mc_sym_call_list_ringing);
                    break;
                case IC_CALL_LOG_RECORD:
                    this.e = getResources().getDrawable(e.mc_sym_call_list_record);
                    break;
                case IC_CALL_LOG_RECORD_FAIL:
                    this.e = getResources().getDrawable(e.mc_sym_call_list_record_fail);
                    break;
                case IC_CALL_LOG_VOICEMAIL:
                    this.e = getResources().getDrawable(e.mc_sym_call_list_voicemail);
                    break;
                default:
                    this.e = null;
                    break;
            }
            invalidate();
        }
    }

    public b getIconType() {
        return this.b;
    }

    public void setBorderType(a borderType) {
        if (borderType == null) {
            throw new NullPointerException();
        } else if (this.a != borderType) {
            this.k = true;
            this.a = borderType;
            Drawable oldDefDrawable = this.z;
            this.z = getDefaultDrawable();
            if (getDrawable() == oldDefDrawable) {
                setImageDrawable(this.z);
            }
            this.f = getResources().getDrawable(e.mc_contact_list_picture_box);
            this.H = getResources().getDimensionPixelSize(d.mc_badge_text_shadow_radius);
            this.I = getResources().getColor(c.mc_badge_text_shadow_color);
            switch (this.a) {
                case BORDER_SMALL_CONTACT:
                    this.g = getResources().getDimensionPixelSize(d.mc_badge_border_small_width);
                    this.h = getResources().getDimensionPixelSize(d.mc_badge_border_small_height);
                    this.i = getResources().getDimensionPixelSize(d.mc_badge_contact_small_picture_width);
                    this.j = getResources().getDimensionPixelSize(d.mc_badge_contact_small_picture_height);
                    this.w = getResources().getDrawable(e.mc_contact_list_call);
                    this.v = getResources().getDrawable(e.mc_contact_list_picture_call_pressed);
                    this.G = getResources().getDimensionPixelSize(d.mc_badge_small_textsize);
                    return;
                case BORDER_LIST_CONTACT:
                    this.g = getResources().getDimensionPixelSize(d.mc_badge_border_list_width);
                    this.h = getResources().getDimensionPixelSize(d.mc_badge_border_list_height);
                    this.i = getResources().getDimensionPixelSize(d.mc_badge_contact_list_picture_width);
                    this.j = getResources().getDimensionPixelSize(d.mc_badge_contact_list_picture_height);
                    this.w = getResources().getDrawable(e.mc_contact_list_call);
                    this.v = getResources().getDrawable(e.mc_contact_list_picture_call_pressed);
                    this.G = getResources().getDimensionPixelSize(d.mc_badge_list_textsize);
                    return;
                case BORDER_SMS_CONTACT:
                    this.g = getResources().getDimensionPixelSize(d.mc_badge_border_sms_width);
                    this.h = getResources().getDimensionPixelSize(d.mc_badge_border_sms_height);
                    this.i = getResources().getDimensionPixelSize(d.mc_badge_contact_list_picture_width);
                    this.j = getResources().getDimensionPixelSize(d.mc_badge_contact_list_picture_height);
                    this.w = getResources().getDrawable(e.mc_contact_list_call);
                    this.v = getResources().getDrawable(e.mc_contact_list_picture_call_pressed);
                    this.G = getResources().getDimensionPixelSize(d.mc_badge_list_textsize);
                    return;
                case BORDER_EDIT_CONTACT:
                case BORDER_VIEW_CONTACT:
                    this.g = getResources().getDimensionPixelSize(d.mc_badge_border_contact_width);
                    this.h = getResources().getDimensionPixelSize(d.mc_badge_border_contact_height);
                    this.i = getResources().getDimensionPixelSize(d.mc_badge_contact_picture_width);
                    this.j = getResources().getDimensionPixelSize(d.mc_badge_contact_picture_height);
                    this.w = null;
                    this.v = null;
                    this.G = getResources().getDimensionPixelSize(d.mc_badge_list_textsize);
                    return;
                default:
                    this.w = null;
                    this.v = null;
                    this.k = false;
                    this.G = getResources().getDimensionPixelSize(d.mc_badge_small_textsize);
                    return;
            }
        }
    }

    public a getBorderType() {
        return this.a;
    }

    public void setClickToCall(boolean clickToCall) {
        if (this.m != clickToCall) {
            this.m = clickToCall;
            invalidate();
        }
    }

    public void setTilte(CharSequence title) {
        this.C = title;
    }

    public void setSubtitle(CharSequence subtitle) {
        this.D = subtitle;
    }

    public void setHasShadow(boolean hasShadow) {
        if (this.M != hasShadow) {
            this.M = hasShadow;
            invalidate();
        }
    }

    public void setContactBadgeText(String badgeText) {
        if (TextUtils.isEmpty(badgeText)) {
            this.F = "";
        } else {
            String text = badgeText.trim();
            if (TextUtils.isEmpty(text)) {
                this.F = "";
            } else {
                String firstLetter = text.substring(0, 1);
                char c = firstLetter.charAt(0);
                if ('a' <= c && c <= 'z') {
                    firstLetter = firstLetter.toUpperCase();
                }
                this.F = firstLetter;
            }
        }
        invalidate();
    }

    private void b() {
        Drawable drawable = getDrawable();
        if ((drawable instanceof BitmapDrawable) && !a(drawable)) {
            int dstWidth = this.c.width();
            int dstHeight = this.c.height();
            if (this.k) {
                dstWidth = this.i;
                dstHeight = this.j;
            }
            Bitmap contactBmp = ((BitmapDrawable) drawable).getBitmap();
            int width = contactBmp.getWidth();
            int height = contactBmp.getHeight();
            int cropLeft = 0;
            int cropTop = 0;
            if (width != height) {
                if (height > width) {
                    cropTop = (height - width) / 2;
                    height = width;
                } else {
                    cropLeft = (width - height) / 2;
                    width = height;
                }
            }
            float scaleWidth = ((float) dstWidth) / ((float) width);
            float scaleHeight = ((float) dstHeight) / ((float) height);
            Bitmap bitmap = contactBmp;
            if (!(scaleWidth == 1.0f && scaleHeight == 1.0f && cropLeft == 0 && cropTop == 0)) {
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                bitmap = Bitmap.createBitmap(contactBmp, cropLeft, cropTop, width, height, matrix, true);
            }
            this.d = a(bitmap);
            super.setImageDrawable(new BitmapDrawable(getResources(), this.d));
            if (bitmap != contactBmp) {
                bitmap.recycle();
            }
            if (this.N) {
                contactBmp.recycle();
                this.N = false;
            }
        }
    }

    private Bitmap a(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        if (this.x == null) {
            this.x = getResources().getDrawable(e.mc_contact_list_picture_cover);
            if (this.x instanceof NinePatchDrawable) {
                ((NinePatchDrawable) this.x).getPaint().setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
            }
        }
        if (this.A == null) {
            this.A = new Paint();
        }
        this.A.setXfermode(null);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.A);
        this.x.setBounds(rect);
        this.x.draw(canvas);
        return output;
    }

    private void a(Canvas canvas, Rect rect) {
        if (this.E == null) {
            this.E = getResources().getDrawable(e.mc_contact_list_picture_default);
        }
        this.E.setBounds(rect);
        this.E.draw(canvas);
        if (this.J == null) {
            this.J = new Paint();
            this.J.setAntiAlias(true);
            this.J.setTextAlign(Align.CENTER);
            this.J.setColor(-1);
            this.J.setShadowLayer((float) this.H, 0.0f, 0.0f, this.I);
        }
        this.J.setTextSize((float) this.G);
        float baseX = (float) ((rect.left + rect.right) / 2);
        float baseY = (float) ((rect.top + rect.bottom) / 2);
        FontMetrics fontMetrics = this.J.getFontMetrics();
        canvas.drawText(this.F, baseX, (baseY + (((fontMetrics.bottom - fontMetrics.top) / 2.0f) - fontMetrics.bottom)) - 2.0f, this.J);
    }

    protected void onDraw(Canvas canvas) {
        if (!((getDrawable() instanceof BitmapDrawable) && this.d == ((BitmapDrawable) getDrawable()).getBitmap())) {
            Bitmap oldDstContactBmp = this.d;
            this.d = null;
            b();
            if (oldDstContactBmp != null) {
                oldDstContactBmp.recycle();
            }
        }
        Paint tmpPaint = new Paint();
        tmpPaint.setColor(this.K);
        Rect drawRect = new Rect();
        if (this.a == a.BORDER_LIST_CONTACT) {
            drawRect.set(this.c.left + this.B, this.c.top + this.B, this.c.right - this.B, this.c.bottom - this.B);
        } else {
            drawRect.set(this.c);
        }
        canvas.drawRect(drawRect, tmpPaint);
        int saveCount = canvas.save();
        if (!this.n || this.v == null) {
            if (this.d != null || TextUtils.isEmpty(this.F)) {
                Drawable contact = getDrawable();
                contact.setBounds(drawRect);
                contact.draw(canvas);
            } else {
                a(canvas, drawRect);
            }
            if (this.M) {
                this.y.setBounds(drawRect);
                this.y.draw(canvas);
            }
            if (this.m && this.w != null) {
                this.w.setBounds(drawRect.left, drawRect.bottom - this.w.getIntrinsicHeight(), drawRect.right, drawRect.bottom);
                this.w.draw(canvas);
            }
            if (this.f != null) {
                this.f.setBounds(drawRect);
                this.f.draw(canvas);
            }
        } else {
            this.v.setBounds(drawRect);
            this.v.draw(canvas);
        }
        canvas.restoreToCount(saveCount);
        this.n = false;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] states = getDrawableState();
        Drawable d = this.f;
        if (d != null && d.isStateful()) {
            d.setState(states);
            invalidate();
        }
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (hasWindowFocus) {
            synchronized (Q) {
                P = false;
            }
        }
        super.onWindowFocusChanged(hasWindowFocus);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.O) {
            setImageDrawable(null);
        } else {
            this.O = true;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.m) {
            return super.onTouchEvent(event);
        }
        if (!hasWindowFocus() || P) {
            return true;
        }
        this.s = this.l;
        this.t = this.q;
        this.u = this.r;
        return super.onTouchEvent(event);
    }

    public void setPressed(boolean pressed) {
        if (!(getParent() instanceof View) || !((View) getParent()).isPressed()) {
            super.setPressed(pressed);
        }
    }

    public void setRecordClickListener(OnClickListener listener) {
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(RoundCornerRecordView.class.getName());
    }
}

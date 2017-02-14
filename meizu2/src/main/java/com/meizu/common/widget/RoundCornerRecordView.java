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
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.meizu.common.R;

public class RoundCornerRecordView extends ImageView {
    static final int ID_COLUMN_INDEX = 0;
    static final int LOOKUPKEY_COLUMN_INDEX = 1;
    static final int PHONE_COLUMN_INDEX = 2;
    public static final String TAG = "RoundCornerRecordView";
    private static final BorderType[] sBorderTypeArray = new BorderType[]{BorderType.BORDER_NULL, BorderType.BORDER_LIST_CONTACT, BorderType.BORDER_EDIT_CONTACT, BorderType.BORDER_VIEW_CONTACT, BorderType.BORDER_SMS_CONTACT, BorderType.BORDER_SMALL_CONTACT};
    private static final IconType[] sIconTypeArray = new IconType[]{IconType.IC_NULL, IconType.IC_CALL_LOG_CALLOUT, IconType.IC_CALL_LOG_CALLIN, IconType.IC_CALL_LOG_MISSED, IconType.IC_CALL_LOG_REFUSED, IconType.IC_CALL_LOG_RINGONCE, IconType.IC_CALL_LOG_RECORD, IconType.IC_CALL_LOG_RECORD_FAIL, IconType.IC_CALL_LOG_VOICEMAIL, IconType.IC_SMS_HAS_UNREAD, IconType.IC_SMS_HAS_NOTDELIVERED};
    private static boolean sStartActivity = false;
    private static final Object sSyncKeyLock = new Object();
    private String mBadgeText;
    private Drawable mBadgeTextDrawable;
    private Paint mBadgeTextPaint;
    private int mBadgeTextShadowColor;
    private int mBadgeTextShadowRadius;
    private int mBadgeTextSize;
    private int mBgColor;
    private Drawable mBorder;
    private BorderType mBorderType;
    private Drawable mCallIcon;
    private long mContactId;
    private String mContactPhone;
    private Drawable mCoverDrawable;
    private int mDefaultColor;
    private Drawable mDefaultDrawable;
    private Bitmap mDstContactBmp;
    private Bundle mExtras;
    private boolean mHasShadow;
    private CharSequence mIconText;
    private IconType mIconType;
    private boolean mIsClickToCall;
    private boolean mIsUseStyle;
    private Drawable mListCallIcon;
    private boolean mLongClick;
    private int mOffsetBottom;
    private int mOffsetRight;
    private long mOldContactId;
    private Bundle mOldExtras;
    private String mOldPhone;
    private Paint mPaint;
    private int mPictureHeight;
    private int mPictureWidth;
    private Rect mRectView;
    private boolean mRecycle;
    private boolean mRecycleOnDetached;
    private Drawable mShadowDrawable;
    private Drawable mSmallIcon;
    private CharSequence mSubtitle;
    private CharSequence mTitle;
    private boolean mUseCallIcon;
    private int mViewHeight;
    private int mViewWidth;

    public enum BorderType {
        BORDER_NULL(0),
        BORDER_LIST_CONTACT(1),
        BORDER_EDIT_CONTACT(2),
        BORDER_VIEW_CONTACT(3),
        BORDER_SMS_CONTACT(4),
        BORDER_SMALL_CONTACT(5);
        
        final int borderTypeInt;

        private BorderType(int i) {
            this.borderTypeInt = i;
        }
    }

    public enum IconType {
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
        
        final int iconTypeInt;

        private IconType(int i) {
            this.iconTypeInt = i;
        }
    }

    public RoundCornerRecordView(Context context) {
        this(context, null);
    }

    public RoundCornerRecordView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoundCornerRecordView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBorderType = null;
        this.mIconType = null;
        this.mDstContactBmp = null;
        this.mSmallIcon = null;
        this.mBorder = null;
        this.mViewWidth = 0;
        this.mViewHeight = 0;
        this.mPictureWidth = 0;
        this.mPictureHeight = 0;
        this.mIsUseStyle = false;
        this.mIsClickToCall = false;
        this.mUseCallIcon = false;
        this.mLongClick = false;
        this.mBgColor = -1;
        this.mHasShadow = true;
        this.mRecycleOnDetached = true;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RoundCornerContactBadge, i, 0);
        int i2 = obtainStyledAttributes.getInt(R.styleable.RoundCornerContactBadge_mcBorderType, BorderType.BORDER_NULL.borderTypeInt);
        int i3 = obtainStyledAttributes.getInt(R.styleable.RoundCornerContactBadge_mcIconType, IconType.IC_CALL_LOG_RECORD.iconTypeInt);
        this.mDefaultColor = getResources().getColor(R.color.mc_round_colorfulbg_color);
        obtainStyledAttributes.recycle();
        setBorderType(sBorderTypeArray[i2]);
        setIconType(sIconTypeArray[i3]);
        init();
    }

    private void init() {
        super.setScaleType(ScaleType.FIT_CENTER);
        setDuplicateParentStateEnabled(false);
        this.mRectView = new Rect();
        this.mShadowDrawable = getResources().getDrawable(R.drawable.mc_contact_list_picture_shadow);
    }

    public void setBackgroundColorId(String str) {
        TypedArray obtainTypedArray = getResources().obtainTypedArray(R.array.mc_colorful_round);
        int abs = Math.abs(str.hashCode()) % obtainTypedArray.length();
        if (abs < obtainTypedArray.length()) {
            this.mBgColor = obtainTypedArray.getColor(abs, this.mDefaultColor);
        }
        obtainTypedArray.recycle();
    }

    public void setImageResource(int i) {
        if (i == 0) {
            setImageDrawable(null);
            return;
        }
        Drawable drawable = getDrawable();
        super.setImageResource(i);
        Drawable drawable2 = getDrawable();
        Bitmap bitmap;
        if (drawable2 == null || !(drawable2 instanceof BitmapDrawable)) {
            bitmap = null;
        } else {
            bitmap = ((BitmapDrawable) drawable2).getBitmap();
        }
        if (!(this.mDstContactBmp == null || this.mDstContactBmp == r0)) {
            this.mDstContactBmp.recycle();
            this.mDstContactBmp = null;
        }
        if (this.mRecycle && (drawable instanceof BitmapDrawable)) {
            ((BitmapDrawable) drawable).getBitmap().recycle();
        }
        this.mRecycle = false;
    }

    public void setImageURI(Uri uri) {
        if (uri == null) {
            setImageDrawable(null);
            return;
        }
        Drawable drawable = getDrawable();
        super.setImageURI(uri);
        Drawable drawable2 = getDrawable();
        Bitmap bitmap;
        if (drawable2 == null || !(drawable2 instanceof BitmapDrawable)) {
            bitmap = null;
        } else {
            bitmap = ((BitmapDrawable) drawable2).getBitmap();
        }
        if (!(this.mDstContactBmp == null || this.mDstContactBmp == r0)) {
            this.mDstContactBmp.recycle();
            this.mDstContactBmp = null;
        }
        if (this.mRecycle && (drawable instanceof BitmapDrawable)) {
            ((BitmapDrawable) drawable).getBitmap().recycle();
        }
        this.mRecycle = false;
    }

    public void setImageBitmap(Bitmap bitmap, boolean z) {
        if (bitmap == null) {
            setImageDrawable(null);
            return;
        }
        super.setImageBitmap(bitmap);
        this.mRecycle = z;
    }

    public void setImageBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            setImageDrawable(null);
        } else {
            super.setImageBitmap(bitmap);
        }
    }

    public void setImageDrawable(Drawable drawable) {
        Drawable drawable2;
        Drawable drawable3 = getDrawable();
        if (drawable == null) {
            drawable2 = this.mDefaultDrawable;
        } else {
            drawable2 = drawable;
        }
        super.setImageDrawable(drawable2);
        Bitmap bitmap;
        if (drawable2 instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable2).getBitmap();
        } else {
            bitmap = null;
        }
        if (!(this.mDstContactBmp == null || this.mDstContactBmp == r0)) {
            this.mDstContactBmp.recycle();
            this.mDstContactBmp = null;
        }
        if (this.mRecycle && (drawable3 instanceof BitmapDrawable)) {
            ((BitmapDrawable) drawable3).getBitmap().recycle();
        }
        this.mRecycle = false;
    }

    private Drawable getDefaultDrawable() {
        switch (this.mBorderType) {
            case BORDER_SMALL_CONTACT:
                return getResources().getDrawable(R.drawable.mc_contact_small_picture);
            default:
                return getResources().getDrawable(R.drawable.mc_contact_list_picture);
        }
    }

    private boolean isDefaultDrawable(Drawable drawable) {
        if (((BitmapDrawable) drawable).getBitmap().equals(((BitmapDrawable) this.mDefaultDrawable).getBitmap())) {
            return true;
        }
        return false;
    }

    public void setScaleType(ScaleType scaleType) {
    }

    protected void onMeasure(int i, int i2) {
        if (this.mIsUseStyle) {
            int i3 = this.mViewWidth;
            int i4 = this.mViewHeight;
            i = MeasureSpec.makeMeasureSpec(i3, 1073741824);
            i2 = MeasureSpec.makeMeasureSpec(i4, 1073741824);
        }
        super.onMeasure(i, i2);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mRectView.set(0, 0, super.getMeasuredWidth(), super.getMeasuredHeight());
    }

    public void setIconText(CharSequence charSequence) {
        if (!TextUtils.equals(this.mIconText, charSequence)) {
            this.mIconText = charSequence;
            invalidate();
        }
    }

    public void setIconType(IconType iconType) {
        if (iconType == null) {
            throw new NullPointerException();
        } else if (this.mIconType != iconType) {
            this.mIconType = iconType;
            switch (this.mIconType) {
                case IC_CALL_LOG_CALLOUT:
                    this.mSmallIcon = getResources().getDrawable(R.drawable.mc_sym_call_list_outgoing);
                    break;
                case IC_CALL_LOG_CALLIN:
                    this.mSmallIcon = getResources().getDrawable(R.drawable.mc_sym_call_list_incoming);
                    break;
                case IC_CALL_LOG_MISSED:
                    this.mSmallIcon = getResources().getDrawable(R.drawable.mc_sym_call_list_missed);
                    break;
                case IC_CALL_LOG_REFUSED:
                    this.mSmallIcon = getResources().getDrawable(R.drawable.mc_sym_call_list_reject);
                    break;
                case IC_CALL_LOG_RINGONCE:
                    this.mSmallIcon = getResources().getDrawable(R.drawable.mc_sym_call_list_ringing);
                    break;
                case IC_CALL_LOG_RECORD:
                    this.mSmallIcon = getResources().getDrawable(R.drawable.mc_sym_call_list_record);
                    break;
                case IC_CALL_LOG_RECORD_FAIL:
                    this.mSmallIcon = getResources().getDrawable(R.drawable.mc_sym_call_list_record_fail);
                    break;
                case IC_CALL_LOG_VOICEMAIL:
                    this.mSmallIcon = getResources().getDrawable(R.drawable.mc_sym_call_list_voicemail);
                    break;
                default:
                    this.mSmallIcon = null;
                    break;
            }
            invalidate();
        }
    }

    public IconType getIconType() {
        return this.mIconType;
    }

    public void setBorderType(BorderType borderType) {
        if (borderType == null) {
            throw new NullPointerException();
        } else if (this.mBorderType != borderType) {
            this.mIsUseStyle = true;
            this.mBorderType = borderType;
            Drawable drawable = this.mDefaultDrawable;
            this.mDefaultDrawable = getDefaultDrawable();
            if (getDrawable() == drawable) {
                setImageDrawable(this.mDefaultDrawable);
            }
            this.mBorder = getResources().getDrawable(R.drawable.mc_contact_list_picture_box);
            this.mBadgeTextShadowRadius = getResources().getDimensionPixelSize(R.dimen.mc_badge_text_shadow_radius);
            this.mBadgeTextShadowColor = getResources().getColor(R.color.mc_badge_text_shadow_color);
            switch (this.mBorderType) {
                case BORDER_SMALL_CONTACT:
                    this.mViewWidth = getResources().getDimensionPixelSize(R.dimen.mc_badge_border_small_width);
                    this.mViewHeight = getResources().getDimensionPixelSize(R.dimen.mc_badge_border_small_height);
                    this.mPictureWidth = getResources().getDimensionPixelSize(R.dimen.mc_badge_contact_small_picture_width);
                    this.mPictureHeight = getResources().getDimensionPixelSize(R.dimen.mc_badge_contact_small_picture_height);
                    this.mListCallIcon = getResources().getDrawable(R.drawable.mc_contact_list_call);
                    this.mCallIcon = getResources().getDrawable(R.drawable.mc_contact_list_picture_call_pressed);
                    this.mBadgeTextSize = getResources().getDimensionPixelSize(R.dimen.mc_badge_small_textsize);
                    return;
                case BORDER_LIST_CONTACT:
                    this.mViewWidth = getResources().getDimensionPixelSize(R.dimen.mc_badge_border_list_width);
                    this.mViewHeight = getResources().getDimensionPixelSize(R.dimen.mc_badge_border_list_height);
                    this.mPictureWidth = getResources().getDimensionPixelSize(R.dimen.mc_badge_contact_list_picture_width);
                    this.mPictureHeight = getResources().getDimensionPixelSize(R.dimen.mc_badge_contact_list_picture_height);
                    this.mListCallIcon = getResources().getDrawable(R.drawable.mc_contact_list_call);
                    this.mCallIcon = getResources().getDrawable(R.drawable.mc_contact_list_picture_call_pressed);
                    this.mBadgeTextSize = getResources().getDimensionPixelSize(R.dimen.mc_badge_list_textsize);
                    return;
                case BORDER_SMS_CONTACT:
                    this.mViewWidth = getResources().getDimensionPixelSize(R.dimen.mc_badge_border_sms_width);
                    this.mViewHeight = getResources().getDimensionPixelSize(R.dimen.mc_badge_border_sms_height);
                    this.mPictureWidth = getResources().getDimensionPixelSize(R.dimen.mc_badge_contact_list_picture_width);
                    this.mPictureHeight = getResources().getDimensionPixelSize(R.dimen.mc_badge_contact_list_picture_height);
                    this.mListCallIcon = getResources().getDrawable(R.drawable.mc_contact_list_call);
                    this.mCallIcon = getResources().getDrawable(R.drawable.mc_contact_list_picture_call_pressed);
                    this.mBadgeTextSize = getResources().getDimensionPixelSize(R.dimen.mc_badge_list_textsize);
                    return;
                case BORDER_EDIT_CONTACT:
                case BORDER_VIEW_CONTACT:
                    this.mViewWidth = getResources().getDimensionPixelSize(R.dimen.mc_badge_border_contact_width);
                    this.mViewHeight = getResources().getDimensionPixelSize(R.dimen.mc_badge_border_contact_height);
                    this.mPictureWidth = getResources().getDimensionPixelSize(R.dimen.mc_badge_contact_picture_width);
                    this.mPictureHeight = getResources().getDimensionPixelSize(R.dimen.mc_badge_contact_picture_height);
                    this.mListCallIcon = null;
                    this.mCallIcon = null;
                    this.mBadgeTextSize = getResources().getDimensionPixelSize(R.dimen.mc_badge_list_textsize);
                    return;
                default:
                    this.mListCallIcon = null;
                    this.mCallIcon = null;
                    this.mIsUseStyle = false;
                    this.mBadgeTextSize = getResources().getDimensionPixelSize(R.dimen.mc_badge_small_textsize);
                    return;
            }
        }
    }

    public BorderType getBorderType() {
        return this.mBorderType;
    }

    public void setClickToCall(boolean z) {
        if (this.mIsClickToCall != z) {
            this.mIsClickToCall = z;
            invalidate();
        }
    }

    public void setTilte(CharSequence charSequence) {
        this.mTitle = charSequence;
    }

    public void setSubtitle(CharSequence charSequence) {
        this.mSubtitle = charSequence;
    }

    public void setHasShadow(boolean z) {
        if (this.mHasShadow != z) {
            this.mHasShadow = z;
            invalidate();
        }
    }

    public void setContactBadgeText(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mBadgeText = "";
        } else {
            Object trim = str.trim();
            if (TextUtils.isEmpty(trim)) {
                this.mBadgeText = "";
            } else {
                String substring = trim.substring(0, 1);
                char charAt = substring.charAt(0);
                if ('a' <= charAt && charAt <= 'z') {
                    substring = substring.toUpperCase();
                }
                this.mBadgeText = substring;
            }
        }
        invalidate();
    }

    private void drawContactDrawable() {
        Drawable drawable = getDrawable();
        if ((drawable instanceof BitmapDrawable) && !isDefaultDrawable(drawable)) {
            int i;
            int i2;
            int i3;
            Bitmap bitmap;
            int width = this.mRectView.width();
            int height = this.mRectView.height();
            if (this.mIsUseStyle) {
                width = this.mPictureWidth;
                i = this.mPictureHeight;
                i2 = width;
            } else {
                i = height;
                i2 = width;
            }
            Bitmap bitmap2 = ((BitmapDrawable) drawable).getBitmap();
            int width2 = bitmap2.getWidth();
            width = bitmap2.getHeight();
            if (width2 == width) {
                height = 0;
                i3 = width2;
                width2 = width;
                width = 0;
            } else if (width > width2) {
                width = (width - width2) / 2;
                height = 0;
                i3 = width2;
            } else {
                height = (width2 - width) / 2;
                width2 = width;
                i3 = width;
                width = 0;
            }
            float f = ((float) i2) / ((float) i3);
            float f2 = ((float) i) / ((float) width2);
            if (f == 1.0f && f2 == 1.0f && height == 0 && width == 0) {
                bitmap = bitmap2;
            } else {
                Matrix matrix = new Matrix();
                matrix.postScale(f, f2);
                bitmap = Bitmap.createBitmap(bitmap2, height, width, i3, width2, matrix, true);
            }
            this.mDstContactBmp = getRoundCornerBitmap(bitmap);
            super.setImageDrawable(new BitmapDrawable(getResources(), this.mDstContactBmp));
            if (bitmap != bitmap2) {
                bitmap.recycle();
            }
            if (this.mRecycle) {
                bitmap2.recycle();
                this.mRecycle = false;
            }
        }
    }

    private Bitmap getRoundCornerBitmap(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        if (this.mCoverDrawable == null) {
            this.mCoverDrawable = getResources().getDrawable(R.drawable.mc_contact_list_picture_cover);
            if (this.mCoverDrawable instanceof NinePatchDrawable) {
                ((NinePatchDrawable) this.mCoverDrawable).getPaint().setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
            }
        }
        if (this.mPaint == null) {
            this.mPaint = new Paint();
        }
        this.mPaint.setXfermode(null);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.mPaint);
        this.mCoverDrawable.setBounds(rect);
        this.mCoverDrawable.draw(canvas);
        return createBitmap;
    }

    private void drawSmallIcon(Canvas canvas) {
        if (this.mBorderType == BorderType.BORDER_LIST_CONTACT && this.mSmallIcon != null) {
            int i = this.mOffsetRight - this.mOffsetBottom;
            this.mSmallIcon.setBounds(new Rect(this.mRectView.right - this.mSmallIcon.getIntrinsicWidth(), (this.mRectView.bottom - this.mSmallIcon.getIntrinsicHeight()) - i, this.mRectView.right, this.mRectView.bottom - i));
            this.mSmallIcon.draw(canvas);
            if (!TextUtils.isEmpty(this.mIconText)) {
                if (this.mIconType == IconType.IC_SMS_HAS_NOTDELIVERED || this.mIconType == IconType.IC_SMS_HAS_UNREAD) {
                    TextPaint textPaint = new TextPaint(1);
                    textPaint.density = getResources().getDisplayMetrics().density;
                    textPaint.setTextSize(20.0f);
                    textPaint.setColor(getResources().getColor(R.color.white));
                    StaticLayout staticLayout = new StaticLayout(this.mIconText, textPaint, this.mSmallIcon.getIntrinsicWidth(), Alignment.ALIGN_CENTER, 1.0f, 0.0f, true);
                    canvas.save();
                    canvas.translate((float) (this.mRectView.right - this.mSmallIcon.getIntrinsicWidth()), (float) ((this.mRectView.bottom - this.mSmallIcon.getIntrinsicHeight()) - i));
                    canvas.clipRect(0, 0, this.mSmallIcon.getIntrinsicWidth(), this.mSmallIcon.getIntrinsicHeight());
                    staticLayout.draw(canvas);
                    canvas.restore();
                }
            }
        }
    }

    private void drawBadgeText(Canvas canvas, Rect rect) {
        if (this.mBadgeTextDrawable == null) {
            this.mBadgeTextDrawable = getResources().getDrawable(R.drawable.mc_contact_list_picture_default);
        }
        this.mBadgeTextDrawable.setBounds(rect);
        this.mBadgeTextDrawable.draw(canvas);
        if (this.mBadgeTextPaint == null) {
            this.mBadgeTextPaint = new Paint();
            this.mBadgeTextPaint.setAntiAlias(true);
            this.mBadgeTextPaint.setTextAlign(Align.CENTER);
            this.mBadgeTextPaint.setColor(-1);
            this.mBadgeTextPaint.setShadowLayer((float) this.mBadgeTextShadowRadius, 0.0f, 0.0f, this.mBadgeTextShadowColor);
        }
        this.mBadgeTextPaint.setTextSize((float) this.mBadgeTextSize);
        float f = (float) ((rect.left + rect.right) / 2);
        float f2 = (float) ((rect.top + rect.bottom) / 2);
        FontMetrics fontMetrics = this.mBadgeTextPaint.getFontMetrics();
        canvas.drawText(this.mBadgeText, f, (f2 + (((fontMetrics.bottom - fontMetrics.top) / CircleProgressBar.BAR_WIDTH_DEF_DIP) - fontMetrics.bottom)) - CircleProgressBar.BAR_WIDTH_DEF_DIP, this.mBadgeTextPaint);
    }

    protected void onDraw(Canvas canvas) {
        if (!((getDrawable() instanceof BitmapDrawable) && this.mDstContactBmp == ((BitmapDrawable) getDrawable()).getBitmap())) {
            Bitmap bitmap = this.mDstContactBmp;
            this.mDstContactBmp = null;
            drawContactDrawable();
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
        Paint paint = new Paint();
        paint.setColor(this.mBgColor);
        Rect rect = new Rect();
        if (this.mBorderType == BorderType.BORDER_LIST_CONTACT) {
            rect.set(this.mRectView.left + this.mOffsetRight, this.mRectView.top + this.mOffsetRight, this.mRectView.right - this.mOffsetRight, this.mRectView.bottom - this.mOffsetRight);
        } else {
            rect.set(this.mRectView);
        }
        canvas.drawRect(rect, paint);
        int save = canvas.save();
        if (!this.mUseCallIcon || this.mCallIcon == null) {
            if (this.mDstContactBmp != null || TextUtils.isEmpty(this.mBadgeText)) {
                Drawable drawable = getDrawable();
                drawable.setBounds(rect);
                drawable.draw(canvas);
            } else {
                drawBadgeText(canvas, rect);
            }
            if (this.mHasShadow) {
                this.mShadowDrawable.setBounds(rect);
                this.mShadowDrawable.draw(canvas);
            }
            if (this.mIsClickToCall && this.mListCallIcon != null) {
                this.mListCallIcon.setBounds(rect.left, rect.bottom - this.mListCallIcon.getIntrinsicHeight(), rect.right, rect.bottom);
                this.mListCallIcon.draw(canvas);
            }
            if (this.mBorder != null) {
                this.mBorder.setBounds(rect);
                this.mBorder.draw(canvas);
            }
        } else {
            this.mCallIcon.setBounds(rect);
            this.mCallIcon.draw(canvas);
        }
        canvas.restoreToCount(save);
        this.mUseCallIcon = false;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.mBorder;
        if (drawable != null && drawable.isStateful()) {
            drawable.setState(drawableState);
            invalidate();
        }
    }

    public void onWindowFocusChanged(boolean z) {
        if (z) {
            synchronized (sSyncKeyLock) {
                sStartActivity = false;
            }
        }
        super.onWindowFocusChanged(z);
    }

    public void recycleOnDetached(boolean z) {
        this.mRecycleOnDetached = z;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mRecycleOnDetached) {
            setImageDrawable(null);
        } else {
            this.mRecycleOnDetached = true;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mIsClickToCall) {
            return super.onTouchEvent(motionEvent);
        }
        if (!hasWindowFocus() || sStartActivity) {
            return true;
        }
        this.mOldPhone = this.mContactPhone;
        this.mOldContactId = this.mContactId;
        this.mOldExtras = this.mExtras;
        return super.onTouchEvent(motionEvent);
    }

    public void setPressed(boolean z) {
        if (!(getParent() instanceof View) || !((View) getParent()).isPressed()) {
            super.setPressed(z);
        }
    }

    public void setRecordClickListener(OnClickListener onClickListener) {
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(RoundCornerRecordView.class.getName());
    }
}

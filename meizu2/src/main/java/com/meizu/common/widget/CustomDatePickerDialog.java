package com.meizu.common.widget;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.meizu.common.R;
import com.meizu.common.renderer.effect.parameters.FastBlurParameters;
import com.meizu.common.util.LunarCalendar;
import com.meizu.common.widget.DatePicker.OnDateChangedListener;
import java.util.Calendar;

public class CustomDatePickerDialog extends AlertDialog implements OnClickListener, OnDateChangedListener {
    private static final String DAY = "day";
    private static final String MONTH = "month";
    private static final String TAG = "CustomDatePickerDialog";
    private static final String YEAR = "year";
    final int gregorianColor;
    final int lunarColor;
    private final OnDateSetListener mCallBack;
    private final DatePicker mDatePicker;
    private long mDuration;
    private TextView mGregorianTab;
    private View mIndicator;
    private boolean mIsLayoutRtl;
    private TextView mLunarTab;
    final int tabTextSelectColor;
    final int unSlectColor;

    public static class FlipView extends View {
        private static final int DEPTH_CONSTANT = 1500;
        private static final int POLY_POINTS = 8;
        private Bitmap mBitmapGregorian;
        private int mBitmapHeight;
        private Bitmap mBitmapLunar;
        private int mBitmapWidth;
        float[] mDstPoly;
        private float mFoldFactor;
        private boolean mIslunar;
        private Matrix[] mMatrices;
        private Paint mPaintShadow;
        private Rect mRect;
        float[] mSrcPoly;

        public FlipView(Context context) {
            super(context);
            this.mFoldFactor = 0.0f;
            this.mIslunar = false;
            this.mSrcPoly = new float[8];
            this.mDstPoly = new float[8];
            init();
        }

        public FlipView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mFoldFactor = 0.0f;
            this.mIslunar = false;
            this.mSrcPoly = new float[8];
            this.mDstPoly = new float[8];
            init();
        }

        private void init() {
            int i = 0;
            this.mRect = new Rect(0, 0, this.mBitmapWidth, this.mBitmapHeight);
            this.mMatrices = new Matrix[2];
            this.mBitmapGregorian = BitmapFactory.decodeResource(getResources(), R.drawable.mc_ic_popup_calendar_gregorian);
            this.mBitmapLunar = BitmapFactory.decodeResource(getResources(), R.drawable.mc_ic_popup_calendar_lunar);
            this.mBitmapWidth = this.mBitmapLunar.getWidth();
            this.mBitmapHeight = this.mBitmapLunar.getHeight();
            while (i < 2) {
                this.mMatrices[i] = new Matrix();
                i++;
            }
            this.mPaintShadow = new Paint();
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0.8f);
            this.mPaintShadow.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        }

        public void onDraw(Canvas canvas) {
            int i = 1;
            calculateMatrix();
            if (this.mFoldFactor == 0.0f) {
                canvas.drawBitmap(this.mIslunar ? this.mBitmapLunar : this.mBitmapGregorian, 0.0f, 0.0f, null);
            } else if (this.mFoldFactor == 1.0f) {
                canvas.drawBitmap(this.mIslunar ? this.mBitmapGregorian : this.mBitmapLunar, 0.0f, 0.0f, null);
            } else {
                Bitmap bitmap;
                this.mRect.set(0, 0, this.mBitmapWidth, this.mBitmapHeight);
                canvas.drawBitmap(this.mBitmapLunar, this.mRect, this.mRect, this.mPaintShadow);
                this.mRect.set(0, 0, this.mBitmapWidth / 2, this.mBitmapHeight);
                canvas.drawBitmap(this.mBitmapGregorian, this.mRect, this.mRect, null);
                canvas.save();
                if (this.mIslunar) {
                    if (this.mFoldFactor < FastBlurParameters.DEFAULT_LEVEL) {
                        this.mRect.set(0, 0, this.mBitmapWidth / 2, this.mBitmapHeight);
                        bitmap = this.mBitmapLunar;
                        i = 0;
                    } else {
                        this.mRect.set(this.mBitmapWidth / 2, 0, this.mBitmapWidth, this.mBitmapHeight);
                        bitmap = this.mBitmapGregorian;
                    }
                } else if (this.mFoldFactor < FastBlurParameters.DEFAULT_LEVEL) {
                    this.mRect.set(this.mBitmapWidth / 2, 0, this.mBitmapWidth, this.mBitmapHeight);
                    bitmap = this.mBitmapGregorian;
                } else {
                    this.mRect.set(0, 0, this.mBitmapWidth / 2, this.mBitmapHeight);
                    bitmap = this.mBitmapLunar;
                    i = 0;
                }
                canvas.concat(this.mMatrices[i]);
                canvas.clipRect(0, 0, this.mRect.right - this.mRect.left, this.mRect.bottom - this.mRect.top);
                canvas.translate((float) (-this.mRect.left), 0.0f);
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
                canvas.restore();
            }
        }

        private void calculateMatrix() {
            float f;
            int i;
            float round = (float) Math.round(((1.0f - this.mFoldFactor) * ((float) this.mBitmapWidth)) / CircleProgressBar.BAR_WIDTH_DEF_DIP);
            float sqrt = ((float) this.mBitmapHeight) * ((((float) Math.sqrt((double) (((float) ((this.mBitmapWidth / 2) * (this.mBitmapWidth / 2))) - (round * round)))) + 1500.0f) / 1500.0f);
            this.mSrcPoly[0] = 0.0f;
            this.mSrcPoly[1] = 0.0f;
            this.mSrcPoly[2] = 0.0f;
            this.mSrcPoly[3] = (float) this.mBitmapHeight;
            this.mSrcPoly[4] = (float) (this.mBitmapWidth / 2);
            this.mSrcPoly[5] = 0.0f;
            this.mSrcPoly[6] = (float) (this.mBitmapWidth / 2);
            this.mSrcPoly[7] = (float) this.mBitmapHeight;
            if (((double) this.mFoldFactor) < 0.5d) {
                f = this.mFoldFactor;
            } else {
                f = 1.0f - this.mFoldFactor;
            }
            this.mMatrices[0].reset();
            this.mDstPoly[0] = ((float) this.mBitmapWidth) * f;
            this.mDstPoly[1] = (((float) this.mBitmapHeight) - sqrt) / CircleProgressBar.BAR_WIDTH_DEF_DIP;
            this.mDstPoly[2] = this.mDstPoly[0];
            this.mDstPoly[3] = ((float) this.mBitmapHeight) + ((sqrt - ((float) this.mBitmapHeight)) / CircleProgressBar.BAR_WIDTH_DEF_DIP);
            this.mDstPoly[4] = (float) (this.mBitmapWidth / 2);
            this.mDstPoly[5] = 0.0f;
            this.mDstPoly[6] = this.mDstPoly[4];
            this.mDstPoly[7] = (float) this.mBitmapHeight;
            for (i = 0; i < 8; i++) {
                this.mDstPoly[i] = (float) Math.round(this.mDstPoly[i]);
            }
            this.mMatrices[0].setPolyToPoly(this.mSrcPoly, 0, this.mDstPoly, 0, 4);
            this.mMatrices[1].reset();
            this.mDstPoly[0] = (float) (this.mBitmapWidth / 2);
            this.mDstPoly[1] = 0.0f;
            this.mDstPoly[2] = this.mDstPoly[0];
            this.mDstPoly[3] = (float) this.mBitmapHeight;
            this.mDstPoly[4] = ((float) this.mBitmapWidth) - (((float) this.mBitmapWidth) * f);
            this.mDstPoly[5] = (((float) this.mBitmapHeight) - sqrt) / CircleProgressBar.BAR_WIDTH_DEF_DIP;
            this.mDstPoly[6] = this.mDstPoly[4];
            this.mDstPoly[7] = ((float) this.mBitmapHeight) + ((sqrt - ((float) this.mBitmapHeight)) / CircleProgressBar.BAR_WIDTH_DEF_DIP);
            for (i = 0; i < 8; i++) {
                this.mDstPoly[i] = (float) Math.round(this.mDstPoly[i]);
            }
            this.mMatrices[1].setPolyToPoly(this.mSrcPoly, 0, this.mDstPoly, 0, 4);
        }

        public void setFoldFactor(float f) {
            this.mFoldFactor = f;
            postInvalidate();
        }

        public void setFilpViewPrefer(boolean z) {
            this.mIslunar = z;
        }
    }

    public interface OnDateSetListener {
        void onDateSet(DatePicker datePicker, int i, int i2, int i3);
    }

    public CustomDatePickerDialog(Context context, OnDateSetListener onDateSetListener, int i, int i2, int i3) {
        this(context, 0, onDateSetListener, i, i2, i3);
    }

    public CustomDatePickerDialog(Context context, int i, OnDateSetListener onDateSetListener, int i2, int i3, int i4) {
        super(context, i);
        this.mDuration = 200;
        this.mIsLayoutRtl = false;
        this.mCallBack = onDateSetListener;
        setButton(-1, context.getText(R.string.mc_yes), this);
        setButton(-2, context.getText(17039360), (OnClickListener) null);
        View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.mc_custom_date_picker_dialog, null);
        setView(inflate);
        this.mDatePicker = (DatePicker) inflate.findViewById(R.id.datePicker);
        this.mDatePicker.init(i2, i3, i4, this, false);
        this.mDatePicker.setIsDrawLine(true);
        this.mDatePicker.setLineHeight(context.getResources().getDimensionPixelSize(R.dimen.mc_custom_time_picker_line_one_height), context.getResources().getDimensionPixelSize(R.dimen.mc_custom_time_picker_line_two_height));
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.styleable.MZTheme);
        this.lunarColor = obtainStyledAttributes.getInt(R.styleable.MZTheme_mzThemeColor, context.getResources().getColor(R.color.mc_custom_date_picker_selected_lunar_color));
        this.gregorianColor = obtainStyledAttributes.getInt(R.styleable.MZTheme_mzThemeColor, context.getResources().getColor(R.color.mc_custom_date_picker_selected_gregorian_color));
        obtainStyledAttributes.recycle();
        this.unSlectColor = context.getResources().getColor(R.color.mc_custom_date_picker_unselected_color);
        this.tabTextSelectColor = context.getResources().getColor(R.color.mc_custom_date_picker_selected_tab_color);
        initTabView(context, inflate);
        setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface dialogInterface) {
                if (CustomDatePickerDialog.this.mDatePicker == null || !CustomDatePickerDialog.this.mDatePicker.isLunar()) {
                    CustomDatePickerDialog.this.getButton(-1).setTextColor(CustomDatePickerDialog.this.gregorianColor);
                } else {
                    CustomDatePickerDialog.this.getButton(-1).setTextColor(CustomDatePickerDialog.this.lunarColor);
                }
            }
        });
        this.mIndicator.post(new Runnable() {
            public void run() {
                if (CustomDatePickerDialog.this.mIndicator == null || VERSION.SDK_INT < 17 || CustomDatePickerDialog.this.mIndicator.getLayoutDirection() != 1) {
                    CustomDatePickerDialog.this.mIsLayoutRtl = false;
                } else {
                    CustomDatePickerDialog.this.mIsLayoutRtl = true;
                }
                if (CustomDatePickerDialog.this.mIsLayoutRtl) {
                    LayoutParams layoutParams = (LayoutParams) CustomDatePickerDialog.this.mIndicator.getLayoutParams();
                    layoutParams.leftMargin = CustomDatePickerDialog.this.getContext().getResources().getDimensionPixelSize(R.dimen.mc_custom_picker_dicator_margin_left_rtl);
                    CustomDatePickerDialog.this.mIndicator.setLayoutParams(layoutParams);
                }
            }
        });
    }

    private void setTabColor(int i, final boolean z) {
        float f;
        float f2 = -1.5333f;
        final int color = getContext().getResources().getColor(R.color.mc_custom_date_picker_unselected_tab_color);
        if (this.mIsLayoutRtl) {
            if (z) {
                f = 0.0f;
            } else {
                f = -1.5333f;
                f2 = 0.0f;
            }
        } else if (z) {
            f2 = 1.5333f;
            f = 0.0f;
        } else {
            f = 1.5333f;
            f2 = 0.0f;
        }
        Animation translateAnimation = new TranslateAnimation(1, f, 1, f2, 1, 0.0f, 1, 0.0f);
        translateAnimation.setDuration(this.mDuration);
        translateAnimation.setFillAfter(true);
        this.mIndicator.startAnimation(translateAnimation);
        String str = "RgbRed";
        str = "RgbGreen";
        str = "RgbBlue";
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int red2 = Color.red(this.tabTextSelectColor);
        int red3 = Color.red(this.tabTextSelectColor);
        int red4 = Color.red(this.tabTextSelectColor);
        PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt("RgbRed", new int[]{red, red2});
        PropertyValuesHolder ofInt2 = PropertyValuesHolder.ofInt("RgbGreen", new int[]{green, red3});
        PropertyValuesHolder ofInt3 = PropertyValuesHolder.ofInt("RgbBlue", new int[]{blue, red4});
        final ValueAnimator ofPropertyValuesHolder = ValueAnimator.ofPropertyValuesHolder(new PropertyValuesHolder[]{ofInt, ofInt2, ofInt3});
        if (ofPropertyValuesHolder != null && (ofPropertyValuesHolder.isStarted() || ofPropertyValuesHolder.isRunning())) {
            ofPropertyValuesHolder.cancel();
        }
        ofPropertyValuesHolder.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int rgb = Color.rgb(((Integer) ofPropertyValuesHolder.getAnimatedValue("RgbRed")).intValue(), ((Integer) ofPropertyValuesHolder.getAnimatedValue("RgbGreen")).intValue(), ((Integer) ofPropertyValuesHolder.getAnimatedValue("RgbBlue")).intValue());
                if (z) {
                    CustomDatePickerDialog.this.mLunarTab.setTextColor(rgb);
                    CustomDatePickerDialog.this.mGregorianTab.setTextColor((color + CustomDatePickerDialog.this.tabTextSelectColor) - rgb);
                    return;
                }
                CustomDatePickerDialog.this.mLunarTab.setTextColor((color + CustomDatePickerDialog.this.tabTextSelectColor) - rgb);
                CustomDatePickerDialog.this.mGregorianTab.setTextColor(rgb);
            }
        });
        ofPropertyValuesHolder.setDuration(this.mDuration);
        ofPropertyValuesHolder.start();
    }

    private void initTabView(Context context, View view) {
        this.mLunarTab = (TextView) view.findViewById(R.id.lunar);
        this.mGregorianTab = (TextView) view.findViewById(R.id.gregorian);
        this.mIndicator = view.findViewById(R.id.indicator);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.styleable.MZTheme);
        final int i = obtainStyledAttributes.getInt(R.styleable.MZTheme_mzThemeColor, context.getResources().getColor(R.color.mc_custom_date_picker_selected_lunar_color));
        final int i2 = obtainStyledAttributes.getInt(R.styleable.MZTheme_mzThemeColor, context.getResources().getColor(R.color.mc_custom_date_picker_selected_gregorian_color));
        obtainStyledAttributes.recycle();
        final int color = context.getResources().getColor(R.color.mc_custom_date_picker_unselected_color);
        int color2 = getContext().getResources().getColor(R.color.mc_custom_date_picker_unselected_tab_color);
        if (this.mDatePicker.isLunar()) {
            this.mLunarTab.setTextColor(this.tabTextSelectColor);
            this.mGregorianTab.setTextColor(color2);
        } else {
            this.mLunarTab.setTextColor(color2);
            this.mGregorianTab.setTextColor(this.tabTextSelectColor);
        }
        color2 = context.getResources().getDimensionPixelSize(R.dimen.mc_custom_picker_dicator_height);
        Drawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setColor(i2);
        gradientDrawable.setCornerRadius((float) (color2 / 2));
        this.mIndicator.setBackgroundDrawable(gradientDrawable);
        this.mDatePicker.setTextColor(i2, color, i2);
        this.mLunarTab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!CustomDatePickerDialog.this.mDatePicker.isLunar()) {
                    CustomDatePickerDialog.this.setTabColor(i, true);
                    CustomDatePickerDialog.this.mDatePicker.setTextColor(i, color, i);
                    CustomDatePickerDialog.this.getButton(-1).setTextColor(i);
                    if (Build.DEVICE.equals("mx4pro")) {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                CustomDatePickerDialog.this.mDatePicker.setIsDrawFading(false);
                                CustomDatePickerDialog.this.mDatePicker.setLunar(true);
                            }
                        }, CustomDatePickerDialog.this.mDuration);
                    } else {
                        CustomDatePickerDialog.this.mDatePicker.setLunar(true);
                    }
                }
            }
        });
        this.mGregorianTab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (CustomDatePickerDialog.this.mDatePicker.isLunar()) {
                    CustomDatePickerDialog.this.setTabColor(i2, false);
                    CustomDatePickerDialog.this.mDatePicker.setTextColor(i2, color, i2);
                    CustomDatePickerDialog.this.getButton(-1).setTextColor(i2);
                    if (Build.DEVICE.equals("mx4pro")) {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                CustomDatePickerDialog.this.mDatePicker.setIsDrawFading(false);
                                CustomDatePickerDialog.this.mDatePicker.setLunar(false);
                            }
                        }, CustomDatePickerDialog.this.mDuration);
                    } else {
                        CustomDatePickerDialog.this.mDatePicker.setLunar(false);
                    }
                }
            }
        });
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        if (this.mCallBack != null) {
            this.mDatePicker.clearFocus();
            this.mCallBack.onDateSet(this.mDatePicker, this.mDatePicker.getYear(), this.mDatePicker.getMonth(), this.mDatePicker.getDayOfMonth());
        }
    }

    public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
        this.mDatePicker.init(i, i2, i3, null, true);
    }

    public DatePicker getDatePicker() {
        return this.mDatePicker;
    }

    public void updateDate(int i, int i2, int i3) {
        this.mDatePicker.updateDate(i, i2, i3);
    }

    public Bundle onSaveInstanceState() {
        Bundle onSaveInstanceState = super.onSaveInstanceState();
        onSaveInstanceState.putInt(YEAR, this.mDatePicker.getYear());
        onSaveInstanceState.putInt(MONTH, this.mDatePicker.getMonth());
        onSaveInstanceState.putInt(DAY, this.mDatePicker.getDayOfMonth());
        return onSaveInstanceState;
    }

    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        this.mDatePicker.init(bundle.getInt(YEAR), bundle.getInt(MONTH), bundle.getInt(DAY), this, false);
    }

    public void setMinYear(int i) {
        if (i < 1900) {
            i = 1900;
        }
        Calendar instance = Calendar.getInstance();
        instance.set(i, 1, 1);
        this.mDatePicker.setMinDate(instance.getTimeInMillis());
    }

    public void setMaxYear(int i) {
        if (i > LunarCalendar.MAX_YEAR) {
            i = LunarCalendar.MAX_YEAR;
        }
        Calendar instance = Calendar.getInstance();
        instance.set(i, 1, 1);
        this.mDatePicker.setMaxDate(instance.getTimeInMillis());
    }
}

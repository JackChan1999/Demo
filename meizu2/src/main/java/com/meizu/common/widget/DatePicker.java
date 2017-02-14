package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.amap.api.maps2d.model.GroundOverlayOptions;
import com.meizu.common.R;
import com.meizu.common.util.LunarCalendar;
import com.meizu.common.widget.ScrollTextView.IDataAdapter;
import com.meizu.common.widget.ScrollTextView.OnScrollTextViewScrollListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class DatePicker extends FrameLayout {
    private static final int DEFAULT_END_YEAR = 2037;
    private static final int DEFAULT_START_YEAR = 1970;
    private static final int NUMBER_OF_MONTHS = 12;
    private boolean isLunar;
    private int mDay;
    private ScrollTextView mDayPicker;
    private TextView mDayUnit;
    private int mEndYear;
    private boolean mIsAccessibilityEnable;
    private boolean mIsDrawLine;
    boolean mIsLayoutRtl;
    private int mLayoutResId;
    String mLeap;
    private int mLineOneHeight;
    private Paint mLinePaint;
    private int mLineTwoHeight;
    String[] mLunarMouths;
    private int mLunarNormalTextSize;
    private int mLunarSelectTextSize;
    String[] mLunardays;
    private int mMonth;
    private volatile Locale mMonthLocale;
    private int mMonthOfDays;
    private ScrollTextView mMonthPicker;
    private TextView mMonthUnit;
    private Object mMonthUpdateLock;
    private String[] mMonths;
    private float mNormalItemHeight;
    private OnDateChangedListener mOnDateChangedListener;
    private int mOneScreenCount;
    String mOrder;
    private LinearLayout mPickerHolder;
    private float mSelectItemHeight;
    private String[] mShortMonths;
    private int mSolarNormalTextSize;
    private int mSolarSelectTextSize;
    private int mStartYear;
    private int mWidthPadding;
    private int mYear;
    private int mYearOfMonths;
    private ScrollTextView mYearPicker;
    private TextView mYearUnit;

    public interface OnDateChangedListener {
        void onDateChanged(DatePicker datePicker, int i, int i2, int i3);
    }

    class DateAdapter implements IDataAdapter {
        static final int SET_DAY = 3;
        static final int SET_MONTH = 2;
        static final int SET_YEAR = 1;
        int mType = 0;

        DateAdapter(int i) {
            this.mType = i;
        }

        public String getItemText(int i) {
            switch (this.mType) {
                case 1:
                    return String.valueOf(DatePicker.this.mStartYear + i);
                case 2:
                    if (DatePicker.this.isLunar) {
                        return DatePicker.this.getLunarMonths(i);
                    }
                    if (i < DatePicker.this.mMonths.length) {
                        return DatePicker.this.mMonths[i];
                    }
                    break;
                case 3:
                    if (DatePicker.this.isLunar) {
                        return DatePicker.this.getLunarDays(i);
                    }
                    return String.valueOf(i + 1);
            }
            return null;
        }

        public void onChanged(View view, int i, int i2) {
            int access$500 = DatePicker.this.getMonthDays();
            int access$600 = DatePicker.this.getYearMonths();
            switch (this.mType) {
                case 1:
                    DatePicker.this.mYear = DatePicker.this.mStartYear + i2;
                    if (!(access$600 == DatePicker.this.getYearMonths() || DatePicker.this.mMonthPicker == null)) {
                        access$600 = DatePicker.this.getYearMonths();
                        DatePicker.this.mMonthPicker.refreshCount(access$600);
                        if (access$600 - 1 < DatePicker.this.mMonth) {
                            DatePicker.this.mDay = access$500;
                            DatePicker.this.mMonth = access$600 - 1;
                            DatePicker.this.mMonthPicker.setCurrentItem(DatePicker.this.mMonth, true);
                        }
                    }
                    if (!(access$500 == DatePicker.this.getMonthDays() || DatePicker.this.mDayPicker == null)) {
                        access$500 = DatePicker.this.getMonthDays();
                        DatePicker.this.mDayPicker.refreshCount(access$500);
                        if (access$500 < DatePicker.this.mDay) {
                            DatePicker.this.mDay = access$500;
                            DatePicker.this.mDayPicker.setCurrentItem(DatePicker.this.mDay - 1, true);
                            break;
                        }
                    }
                    break;
                case 2:
                    DatePicker.this.mMonth = i2;
                    if (!(access$500 == DatePicker.this.getMonthDays() || DatePicker.this.mDayPicker == null)) {
                        access$500 = DatePicker.this.getMonthDays();
                        DatePicker.this.mDayPicker.refreshCount(access$500);
                        if (access$500 < DatePicker.this.mDay) {
                            DatePicker.this.mDay = access$500;
                            DatePicker.this.mDayPicker.setCurrentItem(DatePicker.this.mDay - 1, true);
                            break;
                        }
                    }
                    break;
                case 3:
                    DatePicker.this.mDay = i2 + 1;
                    break;
                default:
                    return;
            }
            if (DatePicker.this.mOnDateChangedListener != null) {
                DatePicker.this.mOnDateChangedListener.onDateChanged(DatePicker.this, DatePicker.this.mYear, DatePicker.this.mMonth, DatePicker.this.mDay);
            }
            DatePicker.this.sendAccessibilityEvent();
        }
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        private final int mDay;
        private final int mMonth;
        private final int mYear;

        private SavedState(Parcelable parcelable, int i, int i2, int i3) {
            super(parcelable);
            this.mYear = i;
            this.mMonth = i2;
            this.mDay = i3;
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mYear = parcel.readInt();
            this.mMonth = parcel.readInt();
            this.mDay = parcel.readInt();
        }

        public int getYear() {
            return this.mYear;
        }

        public int getMonth() {
            return this.mMonth;
        }

        public int getDay() {
            return this.mDay;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mYear);
            parcel.writeInt(this.mMonth);
            parcel.writeInt(this.mDay);
        }
    }

    public DatePicker(Context context) {
        this(context, null);
    }

    public DatePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DatePicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isLunar = false;
        this.mMonthUpdateLock = new Object();
        this.mOneScreenCount = 5;
        this.mLayoutResId = R.layout.mc_date_picker;
        this.mIsAccessibilityEnable = false;
        this.mIsLayoutRtl = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DatePicker);
        this.mStartYear = obtainStyledAttributes.getInt(R.styleable.DatePicker_mcStartYear, DEFAULT_START_YEAR);
        this.mEndYear = obtainStyledAttributes.getInt(R.styleable.DatePicker_mcEndYear, DEFAULT_END_YEAR);
        this.mLayoutResId = obtainStyledAttributes.getResourceId(R.styleable.DatePicker_mcInternalLayout, this.mLayoutResId);
        this.mOneScreenCount = obtainStyledAttributes.getInt(R.styleable.DatePicker_mcVisibleRow, this.mOneScreenCount);
        this.mSelectItemHeight = obtainStyledAttributes.getDimension(R.styleable.DatePicker_mcSelectItemHeight, this.mSelectItemHeight);
        this.mNormalItemHeight = obtainStyledAttributes.getDimension(R.styleable.DatePicker_mcNormalItemHeight, this.mNormalItemHeight);
        obtainStyledAttributes.recycle();
        inflate(getContext(), this.mLayoutResId, this);
        this.mMonthUnit = (TextView) findViewById(R.id.mc_scroll1_text);
        if (this.mMonthUnit != null) {
            this.mMonthUnit.setText(R.string.mc_date_time_month);
        }
        this.mDayUnit = (TextView) findViewById(R.id.mc_scroll2_text);
        if (this.mDayUnit != null) {
            this.mDayUnit.setText(R.string.mc_date_time_day);
        }
        this.mYearUnit = (TextView) findViewById(R.id.mc_scroll3_text);
        if (this.mYearUnit != null) {
            this.mYearUnit.setText(R.string.mc_date_time_year);
        }
        Calendar instance = Calendar.getInstance();
        this.mYear = instance.get(1);
        this.mMonth = instance.get(2);
        this.mDay = instance.get(5);
        this.mOnDateChangedListener = null;
        int actualMaximum = instance.getActualMaximum(5);
        this.mPickerHolder = (LinearLayout) findViewById(R.id.mc_column_parent);
        this.mDayPicker = (ScrollTextView) findViewById(R.id.mc_scroll2);
        if (!(this.mSelectItemHeight == 0.0f || this.mNormalItemHeight == 0.0f)) {
            this.mDayPicker.setItemHeight((float) ((int) this.mSelectItemHeight), (float) ((int) this.mNormalItemHeight));
        }
        this.mDayPicker.setData(new DateAdapter(3), GroundOverlayOptions.NO_DIMENSION, this.mDay - 1, actualMaximum, this.mOneScreenCount, 0, actualMaximum - 1, true);
        this.mMonthPicker = (ScrollTextView) findViewById(R.id.mc_scroll1);
        if (!(this.mSelectItemHeight == 0.0f || this.mNormalItemHeight == 0.0f)) {
            this.mMonthPicker.setItemHeight((float) ((int) this.mSelectItemHeight), (float) ((int) this.mNormalItemHeight));
        }
        this.mMonths = getShortMonths();
        this.mMonthPicker.setData(new DateAdapter(2), GroundOverlayOptions.NO_DIMENSION, this.mMonth, 12, this.mOneScreenCount, 0, 11, true);
        this.mYearPicker = (ScrollTextView) findViewById(R.id.mc_scroll3);
        this.mStartYear -= this.mOneScreenCount;
        this.mEndYear += this.mOneScreenCount;
        if (!(this.mSelectItemHeight == 0.0f || this.mNormalItemHeight == 0.0f)) {
            this.mYearPicker.setItemHeight((float) ((int) this.mSelectItemHeight), (float) ((int) this.mNormalItemHeight));
        }
        updateYearPicker();
        reorderPickers(this.mMonths);
        this.mMonthUnit.setVisibility(isNumeric(this.mMonths[0]) ? 0 : 8);
        adjustLayout4FocusedPosition();
        int paddingTop = this.mYearUnit.getPaddingTop();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics.setToDefaults();
        float f = displayMetrics.scaledDensity;
        float f2 = getResources().getDisplayMetrics().scaledDensity;
        f = ((f2 - f) * (this.mYearUnit.getTextSize() / f2)) / 1.3f;
        this.mYearUnit.setPadding(this.mYearUnit.getPaddingLeft(), (int) (((float) paddingTop) - f), this.mYearUnit.getPaddingRight(), this.mYearUnit.getPaddingBottom());
        this.mMonthUnit.setPadding(this.mMonthUnit.getPaddingLeft(), (int) (((float) paddingTop) - f), this.mMonthUnit.getPaddingRight(), this.mMonthUnit.getPaddingBottom());
        this.mDayUnit.setPadding(this.mDayUnit.getPaddingLeft(), (int) (((float) paddingTop) - f), this.mDayUnit.getPaddingRight(), this.mDayUnit.getPaddingBottom());
        if (!isEnabled()) {
            setEnabled(false);
        }
        this.mLunarNormalTextSize = context.getResources().getDimensionPixelOffset(R.dimen.mc_date_picker_normal_lunar_size);
        this.mLunarSelectTextSize = context.getResources().getDimensionPixelOffset(R.dimen.mc_date_picker_selected_lunar_size);
        this.mSolarNormalTextSize = context.getResources().getDimensionPixelOffset(R.dimen.mc_picker_normal_number_size);
        this.mSolarSelectTextSize = context.getResources().getDimensionPixelOffset(R.dimen.mc_picker_selected_number_size);
        this.mLineOneHeight = 0;
        this.mLineTwoHeight = 0;
        this.mWidthPadding = context.getResources().getDimensionPixelSize(R.dimen.mc_custom_time_picker_line_width_padding);
        this.mLinePaint = new Paint();
        obtainStyledAttributes = context.obtainStyledAttributes(R.styleable.MZTheme);
        int i2 = obtainStyledAttributes.getInt(R.styleable.MZTheme_mzThemeColor, context.getResources().getColor(R.color.mc_custom_date_picker_selected_gregorian_color));
        obtainStyledAttributes.recycle();
        this.mLinePaint.setColor(i2);
        this.mLinePaint.setAntiAlias(true);
        this.mLinePaint.setStrokeWidth((float) context.getResources().getDimensionPixelSize(R.dimen.mc_custom_time_picker_line_stroke_width));
        this.mIsDrawLine = false;
        setWillNotDraw(false);
        this.mLunarMouths = getResources().getStringArray(R.array.mc_custom_time_picker_lunar_month);
        this.mLunardays = getResources().getStringArray(R.array.mc_custom_time_picker_lunar_day);
        this.mLeap = getResources().getString(R.string.mc_time_picker_leap);
        if (Build.DEVICE.equals("mx4pro")) {
            this.mYearPicker.addScrollingListener(new OnScrollTextViewScrollListener() {
                public void onScrollingStarted(ScrollTextView scrollTextView) {
                }

                public void onScrollingFinished(ScrollTextView scrollTextView) {
                    scrollTextView.setIsDrawFading(true);
                }
            });
            this.mMonthPicker.addScrollingListener(new OnScrollTextViewScrollListener() {
                public void onScrollingStarted(ScrollTextView scrollTextView) {
                }

                public void onScrollingFinished(ScrollTextView scrollTextView) {
                    scrollTextView.setIsDrawFading(true);
                }
            });
            this.mDayPicker.addScrollingListener(new OnScrollTextViewScrollListener() {
                public void onScrollingStarted(ScrollTextView scrollTextView) {
                }

                public void onScrollingFinished(ScrollTextView scrollTextView) {
                    DatePicker.this.setIsDrawFading(true);
                }
            });
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        if (accessibilityManager != null) {
            this.mIsAccessibilityEnable = accessibilityManager.isEnabled();
        }
        if (this.mIsAccessibilityEnable) {
            setFocusable(true);
        }
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mDayPicker.setEnabled(z);
        this.mMonthPicker.setEnabled(z);
        this.mYearPicker.setEnabled(z);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(DatePicker.class.getName());
    }

    private void reorderPickers(String[] strArr) {
        DateFormat dateFormat;
        if (strArr[0].startsWith("1")) {
            dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
        } else {
            dateFormat = android.text.format.DateFormat.getMediumDateFormat(getContext());
        }
        if (dateFormat instanceof SimpleDateFormat) {
            this.mOrder = ((SimpleDateFormat) dateFormat).toPattern();
        } else {
            this.mOrder = new String(android.text.format.DateFormat.getDateFormatOrder(getContext()));
        }
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.mc_column1Layout);
        FrameLayout frameLayout2 = (FrameLayout) findViewById(R.id.mc_column2Layout);
        FrameLayout frameLayout3 = (FrameLayout) findViewById(R.id.mc_column3Layout);
        ImageView imageView = (ImageView) findViewById(R.id.mc_divider_bar1);
        ImageView imageView2 = (ImageView) findViewById(R.id.mc_divider_bar2);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mc_column_parent);
        linearLayout.removeAllViews();
        Object obj = null;
        Object obj2 = null;
        Object obj3 = null;
        Object obj4 = null;
        Object obj5 = null;
        Object obj6 = null;
        for (int i = 0; i < this.mOrder.length(); i++) {
            char charAt = this.mOrder.charAt(i);
            if (charAt == '\'') {
                obj = obj == null ? 1 : null;
            }
            if (obj == null) {
                int i2 = 0;
                if (charAt == 'd' && obj2 == null) {
                    linearLayout.addView(frameLayout2);
                    obj2 = 1;
                    i2 = 1;
                } else if ((charAt == 'M' || charAt == 'L') && obj3 == null) {
                    linearLayout.addView(frameLayout);
                    int i3 = 1;
                    i2 = 1;
                } else if (charAt == 'y' && obj4 == null) {
                    linearLayout.addView(frameLayout3);
                    int i4 = 1;
                    i2 = 1;
                }
                if (1 == i2) {
                    if (obj5 == null) {
                        linearLayout.addView(imageView);
                        obj5 = 1;
                    } else if (obj6 == null) {
                        linearLayout.addView(imageView2);
                        obj6 = 1;
                    }
                }
            }
        }
        if (obj3 == null) {
            linearLayout.addView(frameLayout);
        }
        if (obj2 == null) {
            linearLayout.addView(frameLayout2);
        }
        if (obj4 == null) {
            linearLayout.addView(frameLayout3);
        }
        this.mYearPicker.post(new Runnable() {
            public void run() {
                if (DatePicker.this.mYearPicker == null || VERSION.SDK_INT < 17 || DatePicker.this.mYearPicker.getLayoutDirection() != 1) {
                    DatePicker.this.mIsLayoutRtl = false;
                } else {
                    DatePicker.this.mIsLayoutRtl = true;
                }
                if (DatePicker.this.mIsLayoutRtl) {
                    FrameLayout frameLayout = (FrameLayout) DatePicker.this.findViewById(R.id.mc_column2Layout);
                    LayoutParams layoutParams = (LayoutParams) frameLayout.getLayoutParams();
                    layoutParams.rightMargin = 0;
                    layoutParams.leftMargin = 0;
                    frameLayout.setLayoutParams(layoutParams);
                    LayoutParams layoutParams2 = (LayoutParams) DatePicker.this.mYearPicker.getLayoutParams();
                    layoutParams2.gravity = 5;
                    layoutParams2.rightMargin = 0;
                    layoutParams2.leftMargin = 0;
                    DatePicker.this.mYearPicker.setLayoutParams(layoutParams2);
                    layoutParams2 = (LayoutParams) DatePicker.this.mYearUnit.getLayoutParams();
                    layoutParams2.gravity = 3;
                    layoutParams2.rightMargin = 0;
                    layoutParams2.leftMargin = 0;
                    DatePicker.this.mYearUnit.setLayoutParams(layoutParams2);
                    LayoutParams layoutParams3 = (LayoutParams) DatePicker.this.mMonthPicker.getLayoutParams();
                    layoutParams3.gravity = 5;
                    layoutParams3.rightMargin = 0;
                    layoutParams3.leftMargin = 0;
                    DatePicker.this.mMonthPicker.setLayoutParams(layoutParams3);
                    layoutParams3 = (LayoutParams) DatePicker.this.mMonthUnit.getLayoutParams();
                    layoutParams3.gravity = 3;
                    layoutParams3.rightMargin = 0;
                    layoutParams3.leftMargin = 0;
                    DatePicker.this.mMonthUnit.setLayoutParams(layoutParams2);
                    layoutParams2 = (LayoutParams) DatePicker.this.mDayPicker.getLayoutParams();
                    layoutParams2.gravity = 5;
                    layoutParams2.rightMargin = 0;
                    layoutParams2.leftMargin = 0;
                    DatePicker.this.mDayPicker.setLayoutParams(layoutParams2);
                    layoutParams2 = (LayoutParams) DatePicker.this.mDayUnit.getLayoutParams();
                    layoutParams2.gravity = 3;
                    layoutParams2.rightMargin = 0;
                    layoutParams2.leftMargin = 0;
                    DatePicker.this.mDayUnit.setLayoutParams(layoutParams2);
                }
            }
        });
    }

    public void updateDate(int i, int i2, int i3) {
        updateDate(i, i2, i3, true);
    }

    public void updateDate(int i, int i2, int i3, boolean z) {
        updateDate(i, i2, i3, z, true);
    }

    private void updateDate(int i, int i2, int i3, boolean z, boolean z2) {
        this.mYear = i;
        this.mMonth = i2;
        this.mDay = i3;
        this.mMonths = null;
        this.mMonths = getShortMonths();
        this.mYearPicker.setCurrentItem(this.mYear - this.mStartYear, z);
        if (this.mYearOfMonths != getYearMonths()) {
            this.mYearOfMonths = getYearMonths();
            this.mMonthPicker.refreshCount(this.mYearOfMonths);
        }
        this.mMonthPicker.setCurrentItem(this.mMonth, z);
        if (this.mMonthOfDays != getMonthDays()) {
            this.mMonthOfDays = getMonthDays();
            this.mDayPicker.refreshCount(getMonthDays());
        }
        this.mDayPicker.setCurrentItem(this.mDay - 1, z);
        if (z2) {
            reorderPickers(this.mMonths);
        }
    }

    private String[] getShortMonths() {
        int i = 0;
        Locale locale = Locale.getDefault();
        if (locale.equals(this.mMonthLocale) && this.mShortMonths != null) {
            return this.mShortMonths;
        }
        synchronized (this.mMonthUpdateLock) {
            if (!locale.equals(this.mMonthLocale)) {
                this.mShortMonths = new String[12];
                for (int i2 = 0; i2 < 12; i2++) {
                    this.mShortMonths[i2] = DateUtils.getMonthString(i2 + 0, 20);
                }
                if (this.mShortMonths[0].startsWith("1")) {
                    while (i < this.mShortMonths.length) {
                        this.mShortMonths[i] = String.valueOf(i + 1);
                        i++;
                    }
                }
                this.mMonthLocale = locale;
            }
        }
        return this.mShortMonths;
    }

    private String getLunarMonths(int i) {
        int leapMonth = LunarCalendar.leapMonth(this.mYear);
        if (leapMonth == 0) {
            if (i >= 12) {
                return null;
            }
        } else if (i >= 13) {
            return null;
        }
        if (leapMonth == 0 || i <= leapMonth - 1) {
            return this.mLunarMouths[i];
        }
        if (i != leapMonth) {
            return this.mLunarMouths[i - 1];
        }
        if (this.mIsLayoutRtl) {
            return "  " + this.mLeap + this.mLunarMouths[i - 1];
        }
        return this.mLeap + this.mLunarMouths[i - 1] + "  ";
    }

    private String getLunarDays(int i) {
        if (i > this.mLunardays.length - 1) {
            return null;
        }
        return this.mLunardays[i];
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mIsDrawLine) {
            int width = this.mPickerHolder.getWidth() - (this.mWidthPadding * 2);
            int width2 = (getWidth() - width) / 2;
            canvas.drawLine((float) width2, (float) this.mLineOneHeight, (float) (width2 + width), (float) this.mLineOneHeight, this.mLinePaint);
            canvas.drawLine((float) width2, (float) this.mLineTwoHeight, (float) (width2 + width), (float) this.mLineTwoHeight, this.mLinePaint);
        }
    }

    public void setIsDrawLine(boolean z) {
        this.mIsDrawLine = z;
    }

    public void setLineHeight(int i, int i2) {
        this.mLineOneHeight = i;
        this.mLineTwoHeight = i2;
    }

    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), this.mYear, this.mMonth, this.mDay);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mYear = savedState.getYear();
        this.mMonth = savedState.getMonth();
        this.mDay = savedState.getDay();
    }

    public void init(int i, int i2, int i3, OnDateChangedListener onDateChangedListener, boolean z) {
        if (!(this.mYear == i && this.mMonth == i2 && this.mDay == i3)) {
            updateDate(i, i2, i3, z);
        }
        this.mOnDateChangedListener = onDateChangedListener;
        sendAccessibilityEvent();
    }

    public void init(int i, int i2, int i3, OnDateChangedListener onDateChangedListener, boolean z, boolean z2) {
        if (!(this.mYear == i && this.mMonth == i2 && this.mDay == i3 && this.isLunar == z)) {
            if (z) {
                this.isLunar = z;
                this.mMonthPicker.setTextSize((float) this.mLunarSelectTextSize, (float) this.mLunarNormalTextSize);
                this.mDayPicker.setTextSize((float) this.mLunarSelectTextSize, (float) this.mLunarNormalTextSize);
                this.mDayUnit.setAlpha(0.0f);
                int leapMonth = LunarCalendar.leapMonth(i);
                if (!(leapMonth == 0 || i2 + 1 == leapMonth)) {
                    z2 = false;
                }
                if (leapMonth != 0 && (r10 || i2 > leapMonth)) {
                    i2++;
                }
                updateDate(i, i2, i3, false);
            } else {
                updateDate(i, i2, i3, false);
            }
        }
        this.mOnDateChangedListener = onDateChangedListener;
        sendAccessibilityEvent();
    }

    public int getYear() {
        return this.mYear;
    }

    public int getMonth() {
        return this.mMonth;
    }

    public int getDayOfMonth() {
        return this.mDay;
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            DateFormat dateFormat;
            Object toPattern;
            String[] shortMonths = getShortMonths();
            if (shortMonths[0].startsWith("1")) {
                dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
            } else {
                dateFormat = android.text.format.DateFormat.getMediumDateFormat(getContext());
            }
            if (dateFormat instanceof SimpleDateFormat) {
                toPattern = ((SimpleDateFormat) dateFormat).toPattern();
            } else {
                toPattern = new String(android.text.format.DateFormat.getDateFormatOrder(getContext()));
            }
            if (!this.mOrder.equals(toPattern)) {
                this.mMonths = shortMonths;
                reorderPickers(this.mMonths);
            }
        }
    }

    private void updateYearPicker() {
        this.mYearPicker.setData(new DateAdapter(1), GroundOverlayOptions.NO_DIMENSION, this.mYear - this.mStartYear, (this.mEndYear - this.mStartYear) + 1, this.mOneScreenCount, this.mOneScreenCount, (this.mEndYear - this.mStartYear) - this.mOneScreenCount, false);
    }

    public void setMaxDate(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        int i = instance.get(1);
        if (i != this.mEndYear && i > this.mStartYear) {
            this.mEndYear = i;
            this.mEndYear += this.mOneScreenCount;
            updateYearPicker();
        }
    }

    public void setMinDate(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        int i = instance.get(1);
        if (i != this.mStartYear && i < this.mEndYear) {
            this.mStartYear = i;
            this.mStartYear -= this.mOneScreenCount;
            updateYearPicker();
        }
    }

    public void setLunar(boolean z, boolean z2) {
        int[] solarToLunar;
        this.isLunar = z;
        int[] iArr = new int[]{this.mYear, this.mMonth + 1, this.mDay, 0};
        int i = iArr[0];
        int leapMonth = LunarCalendar.leapMonth(iArr[0]);
        int leapMonth2 = LunarCalendar.leapMonth(iArr[0] - 1);
        if (this.isLunar) {
            solarToLunar = LunarCalendar.solarToLunar(iArr[0], iArr[1], iArr[2]);
            if (!(i == solarToLunar[0] || leapMonth2 == 0 || (solarToLunar[3] != 1 && solarToLunar[1] <= leapMonth2)) || (i == solarToLunar[0] && leapMonth != 0 && (solarToLunar[3] == 1 || solarToLunar[1] > leapMonth))) {
                solarToLunar[1] = solarToLunar[1] + 1;
            }
            this.mMonthPicker.setTextSize((float) this.mLunarSelectTextSize, (float) this.mLunarNormalTextSize);
            this.mDayPicker.setTextSize((float) this.mLunarSelectTextSize, (float) this.mLunarNormalTextSize);
            this.mDayUnit.setAlpha(0.0f);
        } else {
            int i2;
            boolean z3;
            if (leapMonth == 0 || leapMonth >= iArr[1]) {
                i2 = iArr[1];
                z3 = false;
            } else if (leapMonth + 1 == iArr[1]) {
                i2 = iArr[1] - 1;
                z3 = true;
            } else if (leapMonth + 1 < iArr[1]) {
                i2 = iArr[1] - 1;
                z3 = false;
            } else {
                i2 = 0;
                z3 = false;
            }
            this.mMonthPicker.setTextSize((float) this.mSolarSelectTextSize, (float) this.mSolarNormalTextSize);
            this.mDayPicker.setTextSize((float) this.mSolarSelectTextSize, (float) this.mSolarNormalTextSize);
            this.mDayUnit.setAlpha(1.0f);
            solarToLunar = LunarCalendar.lunarToSolar(iArr[0], i2, iArr[2], z3);
        }
        updateDate(solarToLunar[0], solarToLunar[1] + -1 < 0 ? 12 : solarToLunar[1] - 1, solarToLunar[2], z2, false);
    }

    public void setLunar(boolean z) {
        setLunar(z, true);
    }

    public boolean isLunar() {
        return this.isLunar;
    }

    private int getMonthDays() {
        boolean z = false;
        boolean z2 = true;
        if (this.isLunar) {
            int i;
            int i2 = this.mMonth;
            int leapMonth = LunarCalendar.leapMonth(this.mYear);
            if (leapMonth != 0) {
                if (leapMonth != i2) {
                    z2 = false;
                }
                z = z2;
            }
            if (leapMonth == 0 || (leapMonth != 0 && this.mMonth < leapMonth)) {
                i = i2 + 1;
            } else {
                i = i2;
            }
            return LunarCalendar.daysInMonth(this.mYear, i, z);
        }
        Calendar instance = Calendar.getInstance();
        instance.set(5, 1);
        instance.set(1, this.mYear);
        instance.set(2, this.mMonth);
        return instance.getActualMaximum(5);
    }

    private int getYearMonths() {
        if (!this.isLunar || LunarCalendar.leapMonth(this.mYear) == 0) {
            return 12;
        }
        return 13;
    }

    private void adjustLayout4FocusedPosition() {
        this.mMonthUnit = (TextView) findViewById(R.id.mc_scroll1_text);
        if (this.mMonthUnit != null) {
            this.mMonthUnit.setText(R.string.mc_date_time_month);
        }
        this.mDayUnit = (TextView) findViewById(R.id.mc_scroll2_text);
        if (this.mDayUnit != null) {
            this.mDayUnit.setText(R.string.mc_date_time_day);
        }
        this.mYearUnit = (TextView) findViewById(R.id.mc_scroll3_text);
        if (this.mYearUnit != null) {
            this.mYearUnit.setText(R.string.mc_date_time_year);
        }
    }

    public static boolean isNumeric(String str) {
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }

    public void setTextColor(int i, int i2, int i3) {
        this.mDayPicker.setTextColor(i, i2);
        this.mMonthPicker.setTextColor(i, i2);
        this.mYearPicker.setTextColor(i, i2);
        this.mDayUnit.setTextColor(i3);
        this.mMonthUnit.setTextColor(i3);
        this.mYearUnit.setTextColor(i3);
    }

    public void setItemHeight(int i, int i2) {
        this.mDayPicker.setItemHeight((float) i, (float) i2);
        this.mMonthPicker.setItemHeight((float) i, (float) i2);
        this.mYearPicker.setItemHeight((float) i, (float) i2);
    }

    public TextView getDayUnit() {
        return this.mDayUnit;
    }

    public void setIsDrawFading(boolean z) {
        this.mYearPicker.setIsDrawFading(z);
        this.mMonthPicker.setIsDrawFading(z);
        this.mDayPicker.setIsDrawFading(z);
    }

    private String getTimeText(int i) {
        int i2;
        switch (i) {
            case 1:
                return String.valueOf(this.mYear);
            case 2:
                i2 = this.mMonth;
                if (this.isLunar) {
                    return getLunarMonths(i2);
                }
                if (this.mMonths == null) {
                    this.mMonths = getShortMonths();
                }
                if (i2 < this.mMonths.length) {
                    return this.mMonths[i2];
                }
                break;
            case 3:
                i2 = this.mDay - 1;
                if (this.isLunar) {
                    return getLunarDays(i2);
                }
                return String.valueOf(i2 + 1);
        }
        return "";
    }

    private void sendAccessibilityEvent() {
        if (this.mIsAccessibilityEnable) {
            setContentDescription((getTimeText(1) + this.mYearUnit.getText() + getTimeText(2) + this.mMonthUnit.getText() + getTimeText(3) + this.mDayUnit.getText()).replace(" ", "").replace("廿十", "二十").replace("廿", "二十"));
            sendAccessibilityEvent(4);
        }
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (!this.mIsAccessibilityEnable || accessibilityEvent.getEventType() != 32) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        accessibilityEvent.getText().add((getTimeText(1) + this.mYearUnit.getText() + getTimeText(2) + this.mMonthUnit.getText() + getTimeText(3) + this.mDayUnit.getText()).replace(" ", "").replace("廿十", "二十").replace("廿", "二十"));
        return false;
    }
}

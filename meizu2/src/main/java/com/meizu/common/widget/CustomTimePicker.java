package com.meizu.common.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.amap.api.maps2d.model.GroundOverlayOptions;
import com.meizu.common.R;
import com.meizu.common.util.LunarCalendar;
import com.meizu.common.widget.ScrollTextView.IDataAdapter;
import com.meizu.common.widget.ScrollTextView.OnScrollTextViewScrollListener;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public class CustomTimePicker extends FrameLayout {
    private static final int NUMBER_OF_MONTHS = 12;
    private static final String TAG = "CustomTimePicker";
    private boolean isLeapMonth;
    private boolean isLunar;
    private ScrollTextView mAmPmPicker;
    private final String mAmText;
    private final Calendar mCalendar;
    private int mCurrentHour;
    private int mCurrentMinute;
    private int mDay;
    private DayPicker mDayPicker;
    private TextView mDayUnit;
    private int mGregorianColor;
    private ScrollTextView mHourPicker;
    private TextView mHourUnit;
    private Boolean mIs24HourView;
    private boolean mIsAm;
    private int mLunarColor;
    private int mLunarMonthCount;
    private int mLunarNormalTextSize;
    private int mLunarSelectTextSize;
    private ScrollTextView mMinutePicker;
    private TextView mMinuteUnit;
    private int mMonth;
    private volatile Locale mMonthLocale;
    private int mMonthOfDays;
    private MonthPicker mMonthPicker;
    private TextView mMonthUnit;
    private Object mMonthUpdateLock;
    private int mOneScreenCount;
    private FrameLayout mPickerHolder;
    private final String mPmText;
    private String[] mShortMonths;
    private int mSolarNormalTextSize;
    private int mSolarSelectTextSize;
    private int mUnSlectColor;
    private int mYear;

    class DayPicker implements OnScrollTextViewScrollListener {
        private ScrollTextView picker;
        private int validEnd;
        private int validStart;

        public DayPicker(ScrollTextView scrollTextView) {
            this.picker = scrollTextView;
        }

        public void setSelectItemHeight(int i) {
            this.picker.setSelectItemHeight((float) i);
        }

        public void setData(TimeAdapter timeAdapter, int i, int i2, int i3, int i4, int i5, int i6, boolean z) {
            setValidEnd(i6);
            setValidStart(i5);
            this.picker.setData(timeAdapter, (float) i, i2, i3, i4, 0, i3 - 1, z);
            this.picker.addScrollingListener(this);
        }

        public int getCurrentItem() {
            return this.picker.getCurrentItem();
        }

        public void setCurrentItem(int i, boolean z) {
            this.picker.setCurrentItem(i, z);
        }

        public void refreshCount(int i) {
            this.picker.refreshCount(i);
        }

        public void setTextColor(int i, int i2) {
            this.picker.setTextColor(i, i2);
        }

        public void setTextSize(int i, int i2) {
            this.picker.setTextSize((float) i, (float) i2);
        }

        public void onScrollingStarted(ScrollTextView scrollTextView) {
        }

        public int getValidEnd() {
            return this.validEnd;
        }

        public int getValidStart() {
            return this.validStart;
        }

        public void setValidEnd(int i) {
            this.validEnd = i;
        }

        public void setValidStart(int i) {
            this.validStart = i;
        }

        public void onScrollingFinished(ScrollTextView scrollTextView) {
            this.picker.setCurrentItem(Math.max(Math.min(this.picker.getCurrentItem(), getValidEnd()), getValidStart()), true);
        }
    }

    class MonthPicker implements IDataAdapter {
        private int[] endDate = new int[4];
        private int endIndex;
        private int[] endLunarDate = new int[4];
        private int leapMonthIn1stYear;
        private int leapMonthIn2ndYear;
        private int lunarMonthsIn1stYear;
        private int lunarMonthsIn2ndYear;
        private String[] mMonths;
        private ScrollTextView picker;
        private int[] startDate = new int[4];
        private int startIndex;
        private int[] startLunarDate = new int[4];

        public MonthPicker(ScrollTextView scrollTextView) {
            this.picker = scrollTextView;
            this.mMonths = getShortMonths();
            calculateValidDateField();
        }

        public void setSelectItemHeight(int i) {
            this.picker.setSelectItemHeight((float) i);
        }

        public int getMonth(int[] iArr) {
            int currentItem = getCurrentItem();
            if (CustomTimePicker.this.isLunar) {
                if (currentItem >= this.lunarMonthsIn1stYear) {
                    currentItem -= this.lunarMonthsIn1stYear - 1;
                    if (iArr != null) {
                        iArr[0] = this.endLunarDate[0];
                    }
                    if (currentItem != this.leapMonthIn2ndYear + 1 || iArr == null) {
                        iArr[1] = 0;
                    } else {
                        iArr[1] = 1;
                    }
                    if (this.leapMonthIn2ndYear == 0 || currentItem <= this.leapMonthIn2ndYear) {
                        return currentItem;
                    }
                    return currentItem - 1;
                }
                currentItem += this.startLunarDate[1];
                if (iArr != null) {
                    iArr[0] = this.startLunarDate[0];
                }
                if (this.leapMonthIn1stYear == 0) {
                    iArr[1] = 0;
                    return currentItem;
                } else if (this.startLunarDate[3] == 1 && currentItem == this.leapMonthIn1stYear) {
                    iArr[1] = 1;
                    return currentItem;
                } else if (this.lunarMonthsIn1stYear <= (12 - this.leapMonthIn1stYear) + 1 || currentItem <= this.leapMonthIn1stYear) {
                    iArr[1] = 0;
                    return currentItem;
                } else {
                    iArr[1] = 1;
                    return currentItem - 1;
                }
            } else if (currentItem > 12 - this.startDate[1]) {
                currentItem -= 12 - this.startDate[1];
                if (iArr == null) {
                    return currentItem;
                }
                iArr[0] = this.endDate[0];
                iArr[1] = 0;
                return currentItem;
            } else {
                currentItem += this.startDate[1];
                if (iArr == null) {
                    return currentItem;
                }
                iArr[0] = this.startDate[0];
                iArr[1] = 0;
                return currentItem;
            }
        }

        public void setMonth(int i, int i2, int i3, boolean z) {
            if (i2 >= 0) {
                CustomTimePicker.this.isLeapMonth = z;
                if (CustomTimePicker.this.isLunar) {
                    if (i == this.startLunarDate[0]) {
                        if (this.leapMonthIn1stYear != 0 && this.startLunarDate[0] <= this.leapMonthIn1stYear && this.startLunarDate[3] != 1 && (i2 > this.leapMonthIn1stYear || (this.leapMonthIn1stYear == i2 && z))) {
                            i2++;
                        }
                        refreshCountAndCurrent(CustomTimePicker.this.mLunarMonthCount, i2 - this.startLunarDate[1]);
                    } else if (i == this.endLunarDate[0]) {
                        if (this.leapMonthIn2ndYear != 0 && (i2 > this.leapMonthIn2ndYear || (this.leapMonthIn2ndYear == i2 && z))) {
                            i2++;
                        }
                        refreshCountAndCurrent(CustomTimePicker.this.mLunarMonthCount, (this.lunarMonthsIn1stYear + i2) - 1);
                    }
                } else if (i2 <= 12) {
                    if (i == this.startDate[0] && i2 >= this.startDate[1]) {
                        refreshCountAndCurrent(12, Math.min(11, i2 - this.startDate[1]));
                    } else if (i == this.endDate[0] && i2 <= this.endDate[1]) {
                        refreshCountAndCurrent(12, 11 - (this.endDate[1] - i2));
                    }
                }
                setDayPickerValidField(i3);
            }
        }

        public void setDayPickerValidField(int i) {
            int access$2100 = CustomTimePicker.this.getMonthDays(CustomTimePicker.this.mYear, CustomTimePicker.this.mMonth, CustomTimePicker.this.isLunar);
            if (CustomTimePicker.this.isLunar) {
                if (CustomTimePicker.this.mYear == this.startLunarDate[0] && CustomTimePicker.this.mMonth == this.startLunarDate[1]) {
                    CustomTimePicker.this.mDayPicker.setValidStart(this.startLunarDate[2] - 1);
                } else {
                    CustomTimePicker.this.mDayPicker.setValidStart(0);
                }
                if (CustomTimePicker.this.mYear == this.endLunarDate[0] && CustomTimePicker.this.mMonth == this.endLunarDate[1]) {
                    CustomTimePicker.this.mDayPicker.setValidEnd(this.endLunarDate[2] - 1);
                } else {
                    CustomTimePicker.this.mDayPicker.setValidEnd(access$2100 - 1);
                }
            } else {
                if (CustomTimePicker.this.mYear == this.startDate[0] && CustomTimePicker.this.mMonth == this.startDate[1]) {
                    CustomTimePicker.this.mDayPicker.setValidStart(this.startDate[2] - 1);
                } else {
                    CustomTimePicker.this.mDayPicker.setValidStart(0);
                }
                if (CustomTimePicker.this.mYear == this.endDate[0] && CustomTimePicker.this.mMonth == this.endDate[1]) {
                    CustomTimePicker.this.mDayPicker.setValidEnd(this.endDate[2] - 1);
                } else {
                    CustomTimePicker.this.mDayPicker.setValidEnd(access$2100 - 1);
                }
            }
            CustomTimePicker.this.mDay = i;
            CustomTimePicker.this.mDay = Math.min(CustomTimePicker.this.mDay, access$2100);
            CustomTimePicker.this.mDay = Math.min(CustomTimePicker.this.mDay, CustomTimePicker.this.mDayPicker.getValidEnd() + 1);
            CustomTimePicker.this.mDay = Math.max(CustomTimePicker.this.mDay, CustomTimePicker.this.mDayPicker.getValidStart() + 1);
            CustomTimePicker.this.mDayPicker.setCurrentItem(CustomTimePicker.this.mDay - 1, true);
        }

        public int getCurrentItem() {
            return this.picker.getCurrentItem() - (CustomTimePicker.this.mOneScreenCount / 2);
        }

        public void refreshCountAndCurrent(int i, int i2) {
            this.picker.refreshCountAndCurrent(((CustomTimePicker.this.mOneScreenCount / 2) * 2) + i, (CustomTimePicker.this.mOneScreenCount / 2) + i2);
        }

        public void setTextSize(int i, int i2) {
            this.picker.setTextSize((float) i, (float) i2);
        }

        public void setTextColor(int i, int i2) {
            this.picker.setTextColor(i, i2);
        }

        public void setData(IDataAdapter iDataAdapter, float f, int i, int i2, int i3, int i4, int i5, boolean z) {
            this.startIndex = i4;
            this.endIndex = (((i3 / 2) * 2) + i5) + 1;
            this.picker.setData(this, f, i, i2 + ((i3 / 2) * 2), i3, this.startIndex, this.endIndex, z);
        }

        private void calculateValidDateField() {
            int i;
            CustomTimePicker.this.mCalendar.setTimeInMillis(System.currentTimeMillis());
            this.startDate[0] = CustomTimePicker.this.mCalendar.get(1);
            this.startDate[1] = CustomTimePicker.this.mCalendar.get(2) + 1;
            this.startDate[2] = CustomTimePicker.this.mCalendar.get(5);
            this.startDate[3] = 0;
            this.endDate[0] = this.startDate[1] == 1 ? this.startDate[0] : this.startDate[0] + 1;
            int[] iArr = this.endDate;
            if (this.startDate[1] - 1 <= 0) {
                i = 12;
            } else {
                i = this.startDate[1] - 1;
            }
            iArr[1] = i;
            this.endDate[2] = CustomTimePicker.this.getMonthDays(this.endDate[0], this.endDate[1], false);
            this.endDate[3] = 0;
            this.startLunarDate = LunarCalendar.solarToLunar(this.startDate[0], this.startDate[1], this.startDate[2]);
            this.endLunarDate = LunarCalendar.solarToLunar(this.endDate[0], this.endDate[1], this.endDate[2]);
            if (this.startLunarDate[0] == this.endLunarDate[0]) {
                this.lunarMonthsIn1stYear = (this.endLunarDate[1] - this.startLunarDate[1]) + 1;
                this.lunarMonthsIn2ndYear = 0;
                CustomTimePicker.this.mLunarMonthCount = this.lunarMonthsIn1stYear + this.lunarMonthsIn2ndYear;
                return;
            }
            this.lunarMonthsIn1stYear = (12 - this.startLunarDate[1]) + 1;
            i = LunarCalendar.leapMonth(this.startLunarDate[0]);
            this.leapMonthIn1stYear = i;
            if (i != 0 && (this.startLunarDate[1] < i || (i == this.startLunarDate[1] && this.startLunarDate[3] != 1))) {
                this.lunarMonthsIn1stYear++;
            }
            this.lunarMonthsIn2ndYear = this.endLunarDate[1];
            i = LunarCalendar.leapMonth(this.endLunarDate[0]);
            this.leapMonthIn2ndYear = i;
            if (i != 0 && (this.endLunarDate[1] > i || (this.endLunarDate[1] == i && this.endLunarDate[3] == 1))) {
                this.lunarMonthsIn2ndYear++;
            }
            CustomTimePicker.this.mLunarMonthCount = this.lunarMonthsIn1stYear + this.lunarMonthsIn2ndYear;
        }

        public String getItemText(int i) {
            if (CustomTimePicker.this.isLunar) {
                int access$2300 = i - (CustomTimePicker.this.mOneScreenCount / 2);
                if (access$2300 < 0 || access$2300 > CustomTimePicker.this.mLunarMonthCount - 1) {
                    return "";
                }
                int i2;
                if (access$2300 >= this.lunarMonthsIn1stYear) {
                    i2 = access$2300 - this.lunarMonthsIn1stYear;
                    access$2300 = this.endLunarDate[0];
                } else {
                    access$2300 += this.startLunarDate[1] - 1;
                    if (this.leapMonthIn1stYear != 0 && (this.startLunarDate[1] > this.leapMonthIn1stYear || this.startLunarDate[3] == 1 || (this.startLunarDate[1] < this.leapMonthIn1stYear && access$2300 >= this.leapMonthIn1stYear))) {
                        access$2300++;
                    }
                    i2 = access$2300;
                    access$2300 = this.startLunarDate[0];
                }
                return getLunarMonths(i2, access$2300);
            } else if (i < CustomTimePicker.this.mOneScreenCount / 2 || i > (CustomTimePicker.this.mOneScreenCount / 2) + 11) {
                return "";
            } else {
                return this.mMonths[(((this.startDate[1] - 1) + i) - (CustomTimePicker.this.mOneScreenCount / 2)) % 12];
            }
        }

        public void onChanged(View view, int i, int i2) {
            int access$2100 = CustomTimePicker.this.getMonthDays(CustomTimePicker.this.mYear, CustomTimePicker.this.mMonth, CustomTimePicker.this.isLunar);
            int access$2300 = i2 - (CustomTimePicker.this.mOneScreenCount / 2);
            if (CustomTimePicker.this.isLunar) {
                if (access$2300 >= this.lunarMonthsIn1stYear) {
                    CustomTimePicker.this.mYear = this.endLunarDate[0];
                } else {
                    CustomTimePicker.this.mYear = this.startLunarDate[0];
                }
            } else if (access$2300 > 12 - this.startDate[1]) {
                CustomTimePicker.this.mYear = this.endDate[0];
            } else {
                CustomTimePicker.this.mYear = this.startDate[0];
            }
            if (!CustomTimePicker.this.isLunar) {
                access$2300 = access$2300 > 12 - this.startDate[1] ? access$2300 - (12 - this.startDate[1]) : access$2300 + this.startDate[1];
            } else if (access$2300 >= this.lunarMonthsIn1stYear) {
                access$2300 -= this.lunarMonthsIn1stYear - 1;
                if (this.leapMonthIn2ndYear != 0 && access$2300 > this.leapMonthIn2ndYear) {
                    access$2300--;
                }
            } else {
                access$2300 += this.startLunarDate[1];
                if (this.leapMonthIn1stYear != 0 && this.startLunarDate[1] <= this.leapMonthIn1stYear && this.startLunarDate[3] != 1 && access$2300 > this.leapMonthIn1stYear) {
                    access$2300--;
                }
            }
            CustomTimePicker.this.mMonth = access$2300;
            if (!(access$2100 == CustomTimePicker.this.getMonthDays(CustomTimePicker.this.mYear, CustomTimePicker.this.mMonth, CustomTimePicker.this.isLunar) || CustomTimePicker.this.mDayPicker == null)) {
                CustomTimePicker.this.mDayPicker.refreshCount(CustomTimePicker.this.getMonthDays(CustomTimePicker.this.mYear, CustomTimePicker.this.mMonth, CustomTimePicker.this.isLunar));
            }
            setDayPickerValidField(CustomTimePicker.this.mDayPicker.getCurrentItem() + 1);
        }

        private String[] getShortMonths() {
            int i = 0;
            Locale locale = Locale.getDefault();
            if (locale.equals(CustomTimePicker.this.mMonthLocale) && CustomTimePicker.this.mShortMonths != null) {
                return CustomTimePicker.this.mShortMonths;
            }
            synchronized (CustomTimePicker.this.mMonthUpdateLock) {
                if (!locale.equals(CustomTimePicker.this.mMonthLocale)) {
                    CustomTimePicker.this.mShortMonths = new String[12];
                    for (int i2 = 0; i2 < 12; i2++) {
                        CustomTimePicker.this.mShortMonths[i2] = DateUtils.getMonthString(i2 + 0, 20);
                    }
                    if (CustomTimePicker.this.mShortMonths[0].startsWith("1")) {
                        while (i < CustomTimePicker.this.mShortMonths.length) {
                            CustomTimePicker.this.mShortMonths[i] = String.valueOf(i + 1);
                            i++;
                        }
                    }
                    CustomTimePicker.this.mMonthLocale = locale;
                }
            }
            return CustomTimePicker.this.mShortMonths;
        }

        private String getLunarMonths(int i, int i2) {
            int i3 = i % 13;
            int leapMonth = LunarCalendar.leapMonth(i2);
            if (leapMonth == 0) {
                if (i3 >= 12) {
                    return null;
                }
            } else if (i3 >= 13) {
                return null;
            }
            String[] stringArray = CustomTimePicker.this.getResources().getStringArray(R.array.mc_custom_time_picker_lunar_month);
            String string = CustomTimePicker.this.getResources().getString(R.string.mc_time_picker_leap);
            if (leapMonth == 0 || i3 <= leapMonth - 1) {
                return stringArray[i3];
            }
            if (i3 == leapMonth) {
                return string + stringArray[i3 - 1];
            }
            return stringArray[i3 - 1];
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
        private final int d;
        private final int h;
        private final int leapmonth;
        private final int lunar;
        private final int min;
        private final int mon;
        private final int y;

        private SavedState(Parcelable parcelable, int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
            int i6;
            int i7 = 1;
            super(parcelable);
            this.h = i;
            this.min = i2;
            this.y = i3;
            this.mon = i4;
            this.d = i5;
            if (z) {
                i6 = 1;
            } else {
                i6 = 0;
            }
            this.lunar = i6;
            if (!z2) {
                i7 = 0;
            }
            this.leapmonth = i7;
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.h = parcel.readInt();
            this.min = parcel.readInt();
            this.y = parcel.readInt();
            this.mon = parcel.readInt();
            this.d = parcel.readInt();
            this.lunar = parcel.readInt();
            this.leapmonth = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.h);
            parcel.writeInt(this.min);
            parcel.writeInt(this.y);
            parcel.writeInt(this.mon);
            parcel.writeInt(this.d);
            parcel.writeInt(this.lunar);
            parcel.writeInt(this.leapmonth);
        }
    }

    class TimeAdapter implements IDataAdapter {
        static final int SET_AMPM = 3;
        static final int SET_DAY = 5;
        static final int SET_HOUR = 1;
        static final int SET_MIN = 2;
        int mType = 0;

        TimeAdapter(int i) {
            this.mType = i;
        }

        public void onChanged(View view, int i, int i2) {
            switch (this.mType) {
                case 1:
                    CustomTimePicker.this.mCurrentHour = i2;
                    return;
                case 2:
                    CustomTimePicker.this.mCurrentMinute = i2;
                    return;
                case 3:
                    CustomTimePicker.this.mIsAm = i2 == 0;
                    return;
                case 5:
                    CustomTimePicker.this.mDay = i2 + 1;
                    return;
                default:
                    return;
            }
        }

        public String getItemText(int i) {
            switch (this.mType) {
                case 1:
                    if (CustomTimePicker.this.is24HourView()) {
                        return String.valueOf(i);
                    }
                    if (i == 0) {
                        i = 12;
                    }
                    return String.valueOf(i);
                case 2:
                    return String.valueOf(i);
                case 3:
                    if (i == 0) {
                        return CustomTimePicker.this.mAmText;
                    }
                    if (i == 1) {
                        return CustomTimePicker.this.mPmText;
                    }
                    break;
                case 5:
                    if (CustomTimePicker.this.isLunar) {
                        return CustomTimePicker.this.getLunarDays(i);
                    }
                    return String.valueOf(i + 1);
            }
            return null;
        }
    }

    public CustomTimePicker(Context context) {
        this(context, null);
    }

    public CustomTimePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CustomTimePicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurrentHour = 0;
        this.mCurrentMinute = 0;
        this.mIs24HourView = Boolean.valueOf(false);
        this.mIsAm = true;
        this.isLunar = false;
        this.isLeapMonth = false;
        this.mMonthUpdateLock = new Object();
        this.mOneScreenCount = 5;
        this.mCalendar = Calendar.getInstance();
        try {
            this.mCurrentHour = this.mCalendar.get(11);
            this.mCurrentMinute = this.mCalendar.get(12);
            this.mIs24HourView = Boolean.valueOf(DateFormat.is24HourFormat(context));
        } catch (Exception e) {
            this.mCurrentHour = 12;
            this.mCurrentMinute = 30;
            this.mIs24HourView = Boolean.valueOf(true);
        }
        if (!this.mIs24HourView.booleanValue() && this.mCurrentHour >= 12) {
            this.mCurrentHour -= 12;
            this.mIsAm = false;
        }
        String[] amPmStrings = new DateFormatSymbols().getAmPmStrings();
        this.mAmText = amPmStrings[0];
        this.mPmText = amPmStrings[1];
        this.mLunarNormalTextSize = context.getResources().getDimensionPixelOffset(R.dimen.mc_date_picker_normal_lunar_size);
        this.mLunarSelectTextSize = context.getResources().getDimensionPixelOffset(R.dimen.mc_date_picker_selected_lunar_size);
        this.mSolarNormalTextSize = context.getResources().getDimensionPixelOffset(R.dimen.mc_picker_normal_number_size);
        this.mSolarSelectTextSize = context.getResources().getDimensionPixelOffset(R.dimen.mc_picker_selected_number_size);
        inflateLayout();
        setBackgroundColor(-1);
    }

    private void inflateLayout() {
        if (getChildCount() > 0) {
            removeAllViews();
        }
        getResources().getDisplayMetrics();
        this.mOneScreenCount = 3;
        int i = R.layout.mc_custom_picker_24hour;
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.mc_custom_time_picker_select_h);
        inflate(getContext(), i, this);
        this.mPickerHolder = (FrameLayout) findViewById(R.id.picker_holder);
        this.mHourUnit = (TextView) findViewById(R.id.mc_scroll_hour_text);
        if (this.mHourUnit != null) {
            this.mHourUnit.setText(R.string.mc_date_time_hour);
        }
        this.mMinuteUnit = (TextView) findViewById(R.id.mc_scroll_min_text);
        if (this.mMinuteUnit != null) {
            this.mMinuteUnit.setText(R.string.mc_date_time_min);
        }
        this.mHourPicker = (ScrollTextView) findViewById(R.id.mc_scroll_hour);
        this.mHourPicker.setData(new TimeAdapter(1), GroundOverlayOptions.NO_DIMENSION, this.mCurrentHour, this.mIs24HourView.booleanValue() ? 24 : 12, this.mOneScreenCount, 0, this.mIs24HourView.booleanValue() ? 23 : 11, true);
        this.mHourPicker.setSelectItemHeight((float) dimensionPixelOffset);
        this.mMinutePicker = (ScrollTextView) findViewById(R.id.mc_scroll_min);
        this.mMinutePicker.setData(new TimeAdapter(2), GroundOverlayOptions.NO_DIMENSION, this.mCurrentMinute, 60, this.mOneScreenCount, 0, 59, true);
        this.mMinutePicker.setSelectItemHeight((float) dimensionPixelOffset);
        this.mAmPmPicker = (ScrollTextView) findViewById(R.id.mc_scroll_ampm);
        this.mAmPmPicker.setData(new TimeAdapter(3), GroundOverlayOptions.NO_DIMENSION, this.mIsAm ? 0 : 1, 2, this.mOneScreenCount, 0, 1, false);
        this.mAmPmPicker.setTextSize(getContext().getResources().getDimension(R.dimen.mz_picker_selected_ampm_size), getContext().getResources().getDimension(R.dimen.mz_picker_unselected_ampm_size));
        this.mAmPmPicker.setSelectItemHeight((float) dimensionPixelOffset);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.mc_column_ampm);
        if (this.mIs24HourView.booleanValue()) {
            frameLayout.setVisibility(8);
        } else {
            frameLayout.setVisibility(0);
        }
        this.mMonthUnit = (TextView) findViewById(R.id.mc_scroll_month_text);
        if (this.mMonthUnit != null) {
            this.mMonthUnit.setText(R.string.mc_date_time_month);
        }
        this.mDayUnit = (TextView) findViewById(R.id.mc_scroll_day_text);
        if (this.mDayUnit != null) {
            this.mDayUnit.setText(R.string.mc_date_time_day);
        }
        Calendar instance = Calendar.getInstance();
        this.mYear = instance.get(1);
        this.mMonth = instance.get(2);
        this.mDay = instance.get(5);
        int actualMaximum = instance.getActualMaximum(5);
        this.mDayPicker = new DayPicker((ScrollTextView) findViewById(R.id.mc_scroll_day));
        this.mDayPicker.setData(new TimeAdapter(5), -1, this.mDay - 1, actualMaximum, this.mOneScreenCount, this.mDay - 1, actualMaximum - 1, true);
        this.mDayPicker.setSelectItemHeight(dimensionPixelOffset);
        this.mMonthPicker = new MonthPicker((ScrollTextView) findViewById(R.id.mc_scroll_month));
        this.mMonthPicker.setData(null, GroundOverlayOptions.NO_DIMENSION, getMonthIndex(this.mMonth), 12, this.mOneScreenCount, 0, 11, false);
        this.mMonthPicker.setSelectItemHeight(dimensionPixelOffset);
        initTabView();
    }

    private void doTabAnimate() {
        int i = this.isLunar ? this.mGregorianColor : this.mLunarColor;
        setTabColor(i, !this.isLunar, true);
        setTextColor(i, this.mUnSlectColor, i);
    }

    private void initTabView() {
        this.mLunarColor = getResources().getColor(R.color.mc_custom_date_picker_selected_lunar_color);
        this.mGregorianColor = getResources().getColor(R.color.mc_custom_date_picker_selected_gregorian_color);
        this.mUnSlectColor = getResources().getColor(R.color.mc_custom_date_picker_unselected_color);
    }

    private void setTabColor(int i, boolean z, boolean z2) {
        getContext().getResources().getColor(R.color.mc_custom_date_picker_unselected_tab_color);
        if (!z) {
        }
    }

    public void updateTime(int i, int i2, boolean z) {
        boolean z2;
        boolean z3;
        int i3 = 0;
        if (!z) {
            this.mIsAm = true;
            if (this.mCurrentHour != i) {
                this.mCurrentHour = i;
                z2 = true;
            } else {
                z2 = false;
            }
            if (this.mCurrentHour >= 12) {
                this.mCurrentHour -= 12;
                this.mIsAm = false;
            }
        } else if (this.mCurrentHour != i) {
            this.mCurrentHour = i;
            z2 = true;
        } else {
            z2 = false;
        }
        if (this.mCurrentMinute != i2) {
            this.mCurrentMinute = i2;
            z3 = true;
        } else {
            z3 = false;
        }
        if (z != this.mIs24HourView.booleanValue()) {
            this.mIs24HourView = Boolean.valueOf(z);
            inflateLayout();
        }
        if (z2) {
            this.mHourPicker.refreshCurrent(this.mCurrentHour);
        }
        if (z3) {
            this.mMinutePicker.refreshCurrent(this.mCurrentMinute);
        }
        if (this.mAmPmPicker != null) {
            ScrollTextView scrollTextView = this.mAmPmPicker;
            if (!this.mIsAm) {
                i3 = 1;
            }
            scrollTextView.refreshCurrent(i3);
        }
    }

    private int getMonthIndex(int i) {
        int i2 = this.mOneScreenCount / 2;
        if (i < 0 || i > 11) {
            return i2;
        }
        int i3 = this.mCalendar.get(2);
        if (i >= i3) {
            return i2 + (i - i3);
        }
        return i2 + ((12 - i3) + i);
    }

    public void getTime(int[] iArr) {
        int i = 1;
        if (this.isLunar) {
            r2 = new int[3];
            r2 = LunarCalendar.lunarToSolar(this.mYear, this.mMonth, this.mDay, false);
            iArr[0] = r2[0];
            iArr[1] = r2[1];
            iArr[2] = r2[2];
        } else {
            iArr[0] = this.mYear;
            iArr[1] = this.mMonth;
            iArr[2] = this.mDay;
        }
        iArr[3] = getCurrentHour();
        iArr[4] = getCurrentMinute().intValue();
        if (!this.isLunar) {
            i = 0;
        }
        iArr[5] = i;
    }

    public long getTimeMillis() {
        int[] iArr = new int[6];
        getTime(iArr);
        Calendar instance = Calendar.getInstance();
        instance.set(iArr[0], iArr[1] - 1, iArr[2], iArr[3], iArr[4], 0);
        return instance.getTimeInMillis();
    }

    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), getCurrentHour(), this.mCurrentMinute, this.mYear, this.mMonth, this.mDay, this.isLunar, this.isLeapMonth);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        boolean z;
        boolean z2 = true;
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        updateTime(savedState.h, savedState.min, this.mIs24HourView.booleanValue());
        int access$1200 = savedState.y;
        int access$1300 = savedState.mon;
        int access$1400 = savedState.d;
        if (savedState.lunar == 1) {
            z = true;
        } else {
            z = false;
        }
        updateDate(access$1200, access$1300, access$1400, z, savedState.leapmonth == 1, false);
        int i = savedState.lunar == 1 ? this.mLunarColor : this.mGregorianColor;
        if (savedState.lunar != 1) {
            z2 = false;
        }
        setTabColor(i, z2, false);
    }

    public int getCurrentHour() {
        if (this.mIs24HourView.booleanValue()) {
            return this.mCurrentHour;
        }
        if (this.mIsAm) {
            return this.mCurrentHour;
        }
        return this.mCurrentHour + 12;
    }

    public boolean is24HourView() {
        return this.mIs24HourView.booleanValue();
    }

    public Integer getCurrentMinute() {
        return Integer.valueOf(this.mCurrentMinute);
    }

    public void setCurrentHour(Integer num) {
        if (num != null && num.intValue() != getCurrentHour()) {
            updateTime(num.intValue(), this.mCurrentMinute, this.mIs24HourView.booleanValue());
        }
    }

    public void setCurrentMinute(Integer num) {
        updateTime(getCurrentHour(), num.intValue(), this.mIs24HourView.booleanValue());
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            int i;
            boolean booleanValue = this.mIs24HourView.booleanValue();
            try {
                booleanValue = DateFormat.is24HourFormat(getContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (booleanValue != this.mIs24HourView.booleanValue()) {
                updateTime(getCurrentHour(), this.mCurrentMinute, booleanValue);
            }
            if (this.isLunar) {
                this.mMonthPicker.setTextSize(this.mLunarSelectTextSize, this.mLunarNormalTextSize);
                this.mDayPicker.setTextSize(this.mLunarSelectTextSize, this.mLunarNormalTextSize);
                this.mDayUnit.setAlpha(0.0f);
                i = this.mLunarColor;
                setTabColor(i, this.isLunar, false);
            } else {
                this.mMonthPicker.setTextSize(this.mSolarSelectTextSize, this.mSolarNormalTextSize);
                this.mDayPicker.setTextSize(this.mSolarSelectTextSize, this.mSolarNormalTextSize);
                this.mDayUnit.setAlpha(1.0f);
                i = this.mGregorianColor;
            }
            this.mMonthPicker.setTextColor(i, this.mUnSlectColor);
            this.mDayPicker.setTextColor(i, this.mUnSlectColor);
            this.mHourPicker.setTextColor(i, this.mUnSlectColor);
            this.mMinutePicker.setTextColor(i, this.mUnSlectColor);
            this.mAmPmPicker.setTextColor(i, this.mUnSlectColor);
            this.mMonthUnit.setTextColor(i);
            this.mDayUnit.setTextColor(i);
            this.mHourUnit.setTextColor(i);
            this.mMinuteUnit.setTextColor(i);
        }
    }

    public void setTextColor(int i, int i2, int i3) {
        this.mMonthPicker.setTextColor(i, i2);
        this.mDayPicker.setTextColor(i, i2);
        this.mHourPicker.setTextColor(i, i2);
        this.mMinutePicker.setTextColor(i, i2);
        if (this.mAmPmPicker != null) {
            this.mAmPmPicker.setTextColor(i, i2);
        }
        this.mHourUnit.setTextColor(i3);
        this.mMinuteUnit.setTextColor(i3);
        this.mMonthUnit.setTextColor(i3);
        this.mDayUnit.setTextColor(i3);
    }

    private int getMonthDays(int i, int i2, boolean z) {
        boolean z2 = true;
        if (z) {
            int leapMonth = LunarCalendar.leapMonth(i);
            if (leapMonth == 0) {
                z2 = false;
            } else if (leapMonth != i2) {
                z2 = false;
            }
            return LunarCalendar.daysInMonth(i, i2, z2);
        }
        Calendar instance = Calendar.getInstance();
        instance.set(5, 1);
        instance.set(1, i);
        instance.set(2, i2 - 1);
        return instance.getActualMaximum(5);
    }

    public void setLunar(boolean z) {
        boolean z2 = false;
        doTabAnimate();
        r0 = new int[4];
        int[] iArr = new int[]{this.mYear, this.mMonthPicker.getMonth(iArr)};
        r0[2] = this.mDayPicker.getCurrentItem() + 1;
        r0[3] = 0;
        if (z) {
            r0 = LunarCalendar.solarToLunar(r0[0], r0[1], r0[2]);
        } else {
            r0 = LunarCalendar.lunarToSolar(r0[0], r0[1], r0[2], iArr[1] == 1);
        }
        int i = r0[0];
        int i2 = r0[1];
        int i3 = r0[2];
        if (iArr[1] == 1) {
            z2 = true;
        }
        updateDate(i, i2, i3, z, z2);
    }

    public void updateDate(int i, int i2, int i3, boolean z, boolean z2, boolean z3) {
        this.isLunar = z;
        this.mYear = i;
        this.mMonth = i2;
        this.mDay = i3;
        this.mMonthPicker.setMonth(this.mYear, this.mMonth, this.mDay, z2);
        if (this.mMonthOfDays != getMonthDays(this.mYear, this.mMonth, z)) {
            this.mMonthOfDays = getMonthDays(this.mYear, this.mMonth, z);
            this.mDayPicker.refreshCount(getMonthDays(this.mYear, this.mMonth, z));
        }
        this.mDayPicker.setCurrentItem(this.mDay - 1, z3);
        if (this.isLunar) {
            this.mMonthPicker.setTextSize(this.mLunarSelectTextSize, this.mLunarNormalTextSize);
            this.mDayPicker.setTextSize(this.mLunarSelectTextSize, this.mLunarNormalTextSize);
            this.mDayUnit.setAlpha(0.0f);
            return;
        }
        this.mMonthPicker.setTextSize(this.mSolarSelectTextSize, this.mSolarNormalTextSize);
        this.mDayPicker.setTextSize(this.mSolarSelectTextSize, this.mSolarNormalTextSize);
        this.mDayUnit.setAlpha(1.0f);
    }

    public void updateDate(int i, int i2, int i3, boolean z, boolean z2) {
        updateDate(i, i2, i3, z, z2, true);
    }

    public void updateTimeMillis(long j, boolean z) {
        this.mCalendar.setTimeInMillis(j);
        updateTime(this.mCalendar.get(11), this.mCalendar.get(12), this.mIs24HourView.booleanValue());
        updateDate(this.mCalendar.get(1), this.mCalendar.get(2) + 1, this.mCalendar.get(5), false, false, z);
    }

    private boolean isInternational() {
        if (getResources().getConfiguration().locale.getCountry().equals("CN") || getResources().getConfiguration().locale.getCountry().equals("TW") || getResources().getConfiguration().locale.getCountry().equals("HK")) {
            return false;
        }
        return true;
    }

    private String getLunarDays(int i) {
        String[] stringArray = getResources().getStringArray(R.array.mc_custom_time_picker_lunar_day);
        if (i > stringArray.length - 1) {
            return null;
        }
        return stringArray[i];
    }

    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.mc_custom_time_picker_padding);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.mc_custom_time_picker_select_h);
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.mc_custom_time_picker_picker_height);
        this.mMonthPicker.setSelectItemHeight(dimensionPixelSize2);
        this.mDayPicker.setSelectItemHeight(dimensionPixelSize2);
        this.mHourPicker.setSelectItemHeight((float) dimensionPixelSize2);
        this.mMinutePicker.setSelectItemHeight((float) dimensionPixelSize2);
        LayoutParams layoutParams = (LayoutParams) this.mPickerHolder.getLayoutParams();
        layoutParams.leftMargin = dimensionPixelSize;
        layoutParams.rightMargin = dimensionPixelSize;
        layoutParams.height = dimensionPixelSize3;
        this.mPickerHolder.setLayoutParams(layoutParams);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(CustomTimePicker.class.getName());
    }
}

package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.api.maps2d.model.GroundOverlayOptions;
import com.meizu.common.R;
import com.meizu.common.widget.ScrollTextView.IDataAdapter;
import java.text.DateFormatSymbols;
import java.util.Calendar;

public class TimePicker extends FrameLayout {
    private ScrollTextView mAmPmPicker;
    private final String mAmText;
    private int mCurrentHour;
    private int mCurrentMinute;
    private ScrollTextView mHourPicker;
    private TextView mHourUnit;
    private Boolean mIs24HourView;
    private boolean mIsAccessibilityEnable;
    private boolean mIsAm;
    private boolean mIsDrawLine;
    private int mLineOneHeight;
    private Paint mLinePaint;
    private int mLineTwoHeight;
    private ScrollTextView mMinutePicker;
    private TextView mMinuteUnit;
    private OnTimeChangedListener mOnTimeChangedListener;
    private LinearLayout mPickerHolder;
    private final String mPmText;
    private int mWidthPadding;

    public interface OnTimeChangedListener {
        void onTimeChanged(TimePicker timePicker, int i, int i2);
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
        private final int mHour;
        private final int mMinute;

        private SavedState(Parcelable parcelable, int i, int i2) {
            super(parcelable);
            this.mHour = i;
            this.mMinute = i2;
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mHour = parcel.readInt();
            this.mMinute = parcel.readInt();
        }

        public int getHour() {
            return this.mHour;
        }

        public int getMinute() {
            return this.mMinute;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mHour);
            parcel.writeInt(this.mMinute);
        }
    }

    class TimeAdapter implements IDataAdapter {
        static final int SET_AMPM = 3;
        static final int SET_HOUR = 1;
        static final int SET_MIN = 2;
        int mType = 0;

        TimeAdapter(int i) {
            this.mType = i;
        }

        public void onChanged(View view, int i, int i2) {
            switch (this.mType) {
                case 1:
                    TimePicker.this.mCurrentHour = i2;
                    break;
                case 2:
                    TimePicker.this.mCurrentMinute = i2;
                    break;
                case 3:
                    TimePicker.this.mIsAm = i2 == 0;
                    break;
                default:
                    return;
            }
            if (TimePicker.this.mOnTimeChangedListener != null) {
                TimePicker.this.mOnTimeChangedListener.onTimeChanged(TimePicker.this, TimePicker.this.getCurrentHour(), TimePicker.this.getCurrentMinute().intValue());
            }
            TimePicker.this.sendAccessibilityEvent();
        }

        public String getItemText(int i) {
            switch (this.mType) {
                case 1:
                    if (TimePicker.this.is24HourView()) {
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
                        return TimePicker.this.mAmText;
                    }
                    if (i == 1) {
                        return TimePicker.this.mPmText;
                    }
                    break;
            }
            return null;
        }
    }

    public TimePicker(Context context) {
        this(context, null);
    }

    public TimePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TimePicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurrentHour = 0;
        this.mCurrentMinute = 0;
        this.mIs24HourView = Boolean.valueOf(false);
        this.mIsAm = true;
        this.mIsAccessibilityEnable = false;
        Calendar instance = Calendar.getInstance();
        try {
            this.mCurrentHour = instance.get(11);
            this.mCurrentMinute = instance.get(12);
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
        inflateLayout();
        this.mLineOneHeight = 0;
        this.mLineTwoHeight = 0;
        this.mWidthPadding = context.getResources().getDimensionPixelSize(R.dimen.mc_custom_time_picker_line_width_padding);
        this.mLinePaint = new Paint();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.styleable.MZTheme);
        int i2 = obtainStyledAttributes.getInt(R.styleable.MZTheme_mzThemeColor, context.getResources().getColor(R.color.mc_custom_date_picker_selected_gregorian_color));
        obtainStyledAttributes.recycle();
        this.mLinePaint.setColor(i2);
        this.mLinePaint.setAntiAlias(true);
        this.mLinePaint.setStrokeWidth((float) context.getResources().getDimensionPixelSize(R.dimen.mc_custom_time_picker_line_stroke_width));
        this.mIsDrawLine = false;
        setWillNotDraw(false);
        this.mPickerHolder = (LinearLayout) findViewById(R.id.mc_column_parent);
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        if (accessibilityManager != null) {
            this.mIsAccessibilityEnable = accessibilityManager.isEnabled();
        }
        if (this.mIsAccessibilityEnable) {
            setFocusable(true);
        }
        sendAccessibilityEvent();
    }

    private void inflateLayout() {
        if (getChildCount() > 0) {
            removeAllViews();
        }
        if (this.mIs24HourView.booleanValue()) {
            init24HourView();
        } else {
            init12HourView();
        }
        int paddingTop = this.mHourUnit.getPaddingTop();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics.setToDefaults();
        float f = displayMetrics.scaledDensity;
        float f2 = getResources().getDisplayMetrics().scaledDensity;
        f = ((f2 - f) * (this.mHourUnit.getTextSize() / f2)) / 1.3f;
        this.mHourUnit.setPadding(this.mHourUnit.getPaddingLeft(), (int) (((float) paddingTop) - f), this.mHourUnit.getPaddingRight(), this.mHourUnit.getPaddingBottom());
        this.mMinuteUnit.setPadding(this.mMinuteUnit.getPaddingLeft(), (int) (((float) paddingTop) - f), this.mMinuteUnit.getPaddingRight(), this.mMinuteUnit.getPaddingBottom());
        if (!isEnabled()) {
            setEnabled(false);
        }
    }

    private void init24HourView() {
        if (this.mIs24HourView.booleanValue()) {
            inflate(getContext(), R.layout.mc_time_picker_column_24, this);
            this.mHourUnit = (TextView) findViewById(R.id.mc_scroll1_text);
            if (this.mHourUnit != null) {
                this.mHourUnit.setText(R.string.mc_date_time_hour);
            }
            this.mMinuteUnit = (TextView) findViewById(R.id.mc_scroll2_text);
            if (this.mMinuteUnit != null) {
                this.mMinuteUnit.setText(R.string.mc_date_time_min);
            }
            this.mHourPicker = (ScrollTextView) findViewById(R.id.mc_scroll1);
            this.mHourPicker.setData(new TimeAdapter(1), GroundOverlayOptions.NO_DIMENSION, this.mCurrentHour, 24, 3, 0, 23, true);
            this.mMinutePicker = (ScrollTextView) findViewById(R.id.mc_scroll2);
            this.mMinutePicker.setData(new TimeAdapter(2), GroundOverlayOptions.NO_DIMENSION, this.mCurrentMinute, 60, 3, 0, 59, true);
            this.mAmPmPicker = null;
            this.mHourPicker.post(new Runnable() {
                public void run() {
                    int i = 1;
                    if (TimePicker.this.mHourPicker == null || VERSION.SDK_INT < 17 || TimePicker.this.mHourPicker.getLayoutDirection() != 1) {
                        i = 0;
                    }
                    if (i != 0) {
                        ((FrameLayout) TimePicker.this.findViewById(R.id.mc_column1Layout)).setPadding(TimePicker.this.getContext().getResources().getDimensionPixelSize(R.dimen.mc_time_picker_hour_padding_left_rtl_24), 0, TimePicker.this.getContext().getResources().getDimensionPixelSize(R.dimen.mc_time_picker_hour_padding_right_rtl_24), 0);
                        LayoutParams layoutParams = (LayoutParams) TimePicker.this.mHourPicker.getLayoutParams();
                        layoutParams.gravity = 5;
                        layoutParams.rightMargin = 0;
                        layoutParams.leftMargin = 0;
                        TimePicker.this.mHourPicker.setLayoutParams(layoutParams);
                        layoutParams = (LayoutParams) TimePicker.this.mHourUnit.getLayoutParams();
                        layoutParams.gravity = 3;
                        layoutParams.rightMargin = 0;
                        layoutParams.leftMargin = 0;
                        TimePicker.this.mHourUnit.setLayoutParams(layoutParams);
                        ((FrameLayout) TimePicker.this.findViewById(R.id.mc_column2Layout)).setPadding(TimePicker.this.getContext().getResources().getDimensionPixelSize(R.dimen.mc_time_picker_minute_padding_left_rtl_24), 0, 0, 0);
                        layoutParams = (LayoutParams) TimePicker.this.mMinutePicker.getLayoutParams();
                        layoutParams.gravity = 5;
                        layoutParams.width = TimePicker.this.getContext().getResources().getDimensionPixelSize(R.dimen.mc_time_picker_minute_picker_width_rtl_24);
                        layoutParams.leftMargin = 0;
                        TimePicker.this.mMinutePicker.setLayoutParams(layoutParams);
                        layoutParams = (LayoutParams) TimePicker.this.mMinuteUnit.getLayoutParams();
                        layoutParams.gravity = 3;
                        layoutParams.rightMargin = 0;
                        layoutParams.leftMargin = 0;
                        TimePicker.this.mMinuteUnit.setLayoutParams(layoutParams);
                    }
                }
            });
        }
    }

    private void init12HourView() {
        if (!this.mIs24HourView.booleanValue()) {
            inflate(getContext(), R.layout.mc_time_picker_column_12, this);
            this.mHourUnit = (TextView) findViewById(R.id.mc_scroll1_text);
            if (this.mHourUnit != null) {
                this.mHourUnit.setText(R.string.mc_date_time_hour);
            }
            this.mMinuteUnit = (TextView) findViewById(R.id.mc_scroll2_text);
            if (this.mMinuteUnit != null) {
                this.mMinuteUnit.setText(R.string.mc_date_time_min);
            }
            this.mHourPicker = (ScrollTextView) findViewById(R.id.mc_scroll1);
            this.mHourPicker.setData(new TimeAdapter(1), GroundOverlayOptions.NO_DIMENSION, this.mCurrentHour, 12, 3, 0, 11, true);
            this.mMinutePicker = (ScrollTextView) findViewById(R.id.mc_scroll2);
            this.mMinutePicker.setData(new TimeAdapter(2), GroundOverlayOptions.NO_DIMENSION, this.mCurrentMinute, 60, 3, 0, 59, true);
            this.mAmPmPicker = (ScrollTextView) findViewById(R.id.mc_scroll3);
            this.mAmPmPicker.setData(new TimeAdapter(3), GroundOverlayOptions.NO_DIMENSION, this.mIsAm ? 0 : 1, 2, 3, 0, 1, false);
            this.mAmPmPicker.setTextSize(getContext().getResources().getDimension(R.dimen.mz_picker_selected_ampm_size), getContext().getResources().getDimension(R.dimen.mz_picker_unselected_ampm_size));
            this.mHourPicker.post(new Runnable() {
                public void run() {
                    int i = 1;
                    if (TimePicker.this.mHourPicker == null || VERSION.SDK_INT < 17 || TimePicker.this.mHourPicker.getLayoutDirection() != 1) {
                        i = 0;
                    }
                    if (i != 0) {
                        LayoutParams layoutParams = (LayoutParams) TimePicker.this.mAmPmPicker.getLayoutParams();
                        layoutParams.rightMargin = 0;
                        layoutParams.leftMargin = 0;
                        TimePicker.this.mAmPmPicker.setLayoutParams(layoutParams);
                        ((FrameLayout) TimePicker.this.findViewById(R.id.mc_column1Layout)).setPadding(TimePicker.this.getContext().getResources().getDimensionPixelSize(R.dimen.mc_time_picker_hour_padding_left_rtl_12), 0, 0, 0);
                        layoutParams = (LayoutParams) TimePicker.this.mHourPicker.getLayoutParams();
                        layoutParams.gravity = 5;
                        layoutParams.rightMargin = 0;
                        layoutParams.leftMargin = TimePicker.this.getContext().getResources().getDimensionPixelSize(R.dimen.mc_time_picker_hour_picker_margin_left_rtl_12);
                        TimePicker.this.mHourPicker.setLayoutParams(layoutParams);
                        layoutParams = (LayoutParams) TimePicker.this.mHourUnit.getLayoutParams();
                        layoutParams.gravity = 3;
                        layoutParams.rightMargin = 0;
                        layoutParams.leftMargin = 0;
                        TimePicker.this.mHourUnit.setLayoutParams(layoutParams);
                        layoutParams = (LayoutParams) TimePicker.this.mMinutePicker.getLayoutParams();
                        layoutParams.gravity = 5;
                        layoutParams.rightMargin = 0;
                        layoutParams.leftMargin = TimePicker.this.getContext().getResources().getDimensionPixelSize(R.dimen.mc_time_picker_minute_picker_margin_left_rtl_12);
                        TimePicker.this.mMinutePicker.setLayoutParams(layoutParams);
                        layoutParams = (LayoutParams) TimePicker.this.mMinuteUnit.getLayoutParams();
                        layoutParams.gravity = 3;
                        layoutParams.rightMargin = 0;
                        layoutParams.leftMargin = 0;
                        TimePicker.this.mMinuteUnit.setLayoutParams(layoutParams);
                    }
                }
            });
        }
    }

    public void update(int i, int i2, boolean z) {
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

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mMinutePicker.setEnabled(z);
        this.mHourPicker.setEnabled(z);
        if (this.mAmPmPicker != null) {
            this.mAmPmPicker.setEnabled(z);
        }
    }

    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), getCurrentHour(), this.mCurrentMinute);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        update(savedState.mHour, savedState.mMinute, this.mIs24HourView.booleanValue());
    }

    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        this.mOnTimeChangedListener = onTimeChangedListener;
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
            update(num.intValue(), this.mCurrentMinute, this.mIs24HourView.booleanValue());
        }
    }

    public void setIs24HourView(Boolean bool) {
        update(getCurrentHour(), this.mCurrentMinute, bool.booleanValue());
    }

    public void setCurrentMinute(Integer num) {
        update(getCurrentHour(), num.intValue(), this.mIs24HourView.booleanValue());
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            boolean booleanValue = this.mIs24HourView.booleanValue();
            try {
                booleanValue = DateFormat.is24HourFormat(getContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (booleanValue != this.mIs24HourView.booleanValue()) {
                setIs24HourView(Boolean.valueOf(booleanValue));
            }
        }
    }

    public void setTextColor(int i, int i2, int i3) {
        this.mHourPicker.setTextColor(i, i2);
        this.mMinutePicker.setTextColor(i, i2);
        if (this.mAmPmPicker != null) {
            this.mAmPmPicker.setTextColor(i, i2);
        }
        this.mHourUnit.setTextColor(i3);
        this.mMinuteUnit.setTextColor(i3);
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

    private void sendAccessibilityEvent() {
        if (this.mIsAccessibilityEnable) {
            String str = "";
            if (!this.mIs24HourView.booleanValue()) {
                str = str + getTimeText(3);
            }
            setContentDescription(str + getTimeText(1) + this.mHourUnit.getText() + getTimeText(2) + this.mMinuteUnit.getText());
            sendAccessibilityEvent(4);
        }
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (!this.mIsAccessibilityEnable || accessibilityEvent.getEventType() != 32) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        String str = "";
        if (!this.mIs24HourView.booleanValue()) {
            str = str + getTimeText(3);
        }
        accessibilityEvent.getText().add(str + getTimeText(1) + this.mHourUnit.getText() + getTimeText(2) + this.mMinuteUnit.getText());
        return false;
    }

    private String getTimeText(int i) {
        int i2;
        switch (i) {
            case 1:
                i2 = this.mCurrentHour;
                if (is24HourView()) {
                    return String.valueOf(i2);
                }
                if (i2 == 0) {
                    i2 = 12;
                }
                return String.valueOf(i2);
            case 2:
                return String.valueOf(this.mCurrentMinute);
            case 3:
                Object obj;
                if (this.mIsAm) {
                    obj = null;
                } else {
                    i2 = 1;
                }
                if (obj == null) {
                    return this.mAmText;
                }
                if (obj == 1) {
                    return this.mPmText;
                }
                break;
        }
        return "";
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(TimePicker.class.getName());
    }
}

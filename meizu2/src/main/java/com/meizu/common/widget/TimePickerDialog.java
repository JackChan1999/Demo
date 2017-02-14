package com.meizu.common.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.meizu.common.R;
import com.meizu.common.widget.TimePicker.OnTimeChangedListener;

public class TimePickerDialog extends AlertDialog implements OnClickListener, OnTimeChangedListener {
    private static final String HOUR = "hour";
    private static final String IS_24_HOUR = "is24hour";
    private static final String MINUTE = "minute";
    private final OnTimeSetListener mCallback;
    int mInitialHourOfDay;
    int mInitialMinute;
    boolean mIs24HourView;
    private final TimePicker mTimePicker;

    public interface OnTimeSetListener {
        void onTimeSet(TimePicker timePicker, int i, int i2);
    }

    public TimePickerDialog(Context context, OnTimeSetListener onTimeSetListener, int i, int i2, boolean z) {
        this(context, 0, onTimeSetListener, i, i2, z);
    }

    public TimePickerDialog(Context context, int i, OnTimeSetListener onTimeSetListener, int i2, int i3, boolean z) {
        super(context, i);
        this.mCallback = onTimeSetListener;
        this.mInitialHourOfDay = i2;
        this.mInitialMinute = i3;
        this.mIs24HourView = z;
        setButton(-1, context.getText(R.string.mc_yes), this);
        setButton(-2, context.getText(17039360), (OnClickListener) null);
        View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.mc_time_picker_dialog, null);
        setView(inflate);
        this.mTimePicker = (TimePicker) inflate.findViewById(R.id.timePicker);
        this.mTimePicker.setIs24HourView(Boolean.valueOf(this.mIs24HourView));
        this.mTimePicker.setCurrentHour(Integer.valueOf(this.mInitialHourOfDay));
        this.mTimePicker.setCurrentMinute(Integer.valueOf(this.mInitialMinute));
        this.mTimePicker.setOnTimeChangedListener(this);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.styleable.MZTheme);
        final int i4 = obtainStyledAttributes.getInt(R.styleable.MZTheme_mzThemeColor, context.getResources().getColor(R.color.mc_custom_date_picker_selected_gregorian_color));
        obtainStyledAttributes.recycle();
        setTextColor(i4, context.getResources().getColor(R.color.mc_custom_date_picker_unselected_color), i4);
        this.mTimePicker.setIsDrawLine(true);
        this.mTimePicker.setLineHeight(context.getResources().getDimensionPixelSize(R.dimen.mc_time_picker_line_one_height), context.getResources().getDimensionPixelSize(R.dimen.mc_time_picker_line_two_height));
        setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface dialogInterface) {
                TimePickerDialog.this.getButton(-1).setTextColor(i4);
            }
        });
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        if (this.mCallback != null) {
            this.mTimePicker.clearFocus();
            this.mCallback.onTimeSet(this.mTimePicker, this.mTimePicker.getCurrentHour(), this.mTimePicker.getCurrentMinute().intValue());
        }
    }

    public void updateTime(int i, int i2) {
        this.mTimePicker.setCurrentHour(Integer.valueOf(i));
        this.mTimePicker.setCurrentMinute(Integer.valueOf(i2));
    }

    public void onTimeChanged(TimePicker timePicker, int i, int i2) {
    }

    public Bundle onSaveInstanceState() {
        Bundle onSaveInstanceState = super.onSaveInstanceState();
        onSaveInstanceState.putInt(HOUR, this.mTimePicker.getCurrentHour());
        onSaveInstanceState.putInt(MINUTE, this.mTimePicker.getCurrentMinute().intValue());
        onSaveInstanceState.putBoolean(IS_24_HOUR, this.mTimePicker.is24HourView());
        return onSaveInstanceState;
    }

    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        int i = bundle.getInt(HOUR);
        int i2 = bundle.getInt(MINUTE);
        this.mTimePicker.setIs24HourView(Boolean.valueOf(bundle.getBoolean(IS_24_HOUR)));
        this.mTimePicker.setCurrentHour(Integer.valueOf(i));
        this.mTimePicker.setCurrentMinute(Integer.valueOf(i2));
    }

    public void setTextColor(int i, int i2, int i3) {
        this.mTimePicker.setTextColor(i, i2, i3);
    }
}

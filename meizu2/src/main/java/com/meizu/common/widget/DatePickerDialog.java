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
import com.meizu.common.util.LunarCalendar;
import com.meizu.common.widget.DatePicker.OnDateChangedListener;
import java.util.Calendar;

public class DatePickerDialog extends AlertDialog implements OnClickListener, OnDateChangedListener {
    private static final String DAY = "day";
    private static final String MONTH = "month";
    private static final String YEAR = "year";
    private final OnDateSetListener mCallBack;
    private final DatePicker mDatePicker;

    public interface OnDateSetListener {
        void onDateSet(DatePicker datePicker, int i, int i2, int i3);
    }

    public DatePickerDialog(Context context, OnDateSetListener onDateSetListener, int i, int i2, int i3) {
        this(context, 0, onDateSetListener, i, i2, i3);
    }

    public DatePickerDialog(Context context, int i, OnDateSetListener onDateSetListener, int i2, int i3, int i4) {
        this(context, i, onDateSetListener, i2, i3, i4, false, false);
    }

    public DatePickerDialog(Context context, int i, OnDateSetListener onDateSetListener, int i2, int i3, int i4, boolean z, boolean z2) {
        super(context, i);
        this.mCallBack = onDateSetListener;
        setButton(-1, context.getText(R.string.mc_yes), this);
        setButton(-2, context.getText(17039360), (OnClickListener) null);
        View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.mc_date_picker_dialog, null);
        setView(inflate);
        this.mDatePicker = (DatePicker) inflate.findViewById(R.id.datePicker);
        this.mDatePicker.init(i2, i3, i4, this, z, z2);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.styleable.MZTheme);
        final int i5 = obtainStyledAttributes.getInt(R.styleable.MZTheme_mzThemeColor, context.getResources().getColor(R.color.mc_custom_date_picker_selected_gregorian_color));
        obtainStyledAttributes.recycle();
        setTextColor(i5, context.getResources().getColor(R.color.mc_custom_date_picker_unselected_color), i5);
        this.mDatePicker.setIsDrawLine(true);
        this.mDatePicker.setLineHeight(context.getResources().getDimensionPixelSize(R.dimen.mc_custom_time_picker_line_one_height), context.getResources().getDimensionPixelSize(R.dimen.mc_custom_time_picker_line_two_height));
        setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface dialogInterface) {
                DatePickerDialog.this.getButton(-1).setTextColor(i5);
            }
        });
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
        updateDate(i, i2, i3, true);
    }

    public void updateDate(int i, int i2, int i3, boolean z) {
        this.mDatePicker.updateDate(i, i2, i3, z);
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

    public void setTextColor(int i, int i2, int i3) {
        this.mDatePicker.setTextColor(i, i2, i3);
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

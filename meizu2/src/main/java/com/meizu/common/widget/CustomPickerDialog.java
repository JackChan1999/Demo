package com.meizu.common.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import com.meizu.common.R;
import com.meizu.common.widget.CustomPicker.ColumnData;
import com.meizu.common.widget.CustomPicker.OnCurrentItemChangedListener;

public class CustomPickerDialog extends AlertDialog implements OnClickListener {
    private static final String KEY_CURRENT_ITEMS = "key_current_items";
    private final OnValueSetListener mCallback;
    private final CustomPicker mPicker;
    private OnClickListener mPositiveBtnClickListener;

    public interface OnValueSetListener {
        void onValueSet(CustomPicker customPicker, int... iArr);
    }

    public CustomPickerDialog(Context context, OnValueSetListener onValueSetListener, int i, ColumnData... columnDataArr) {
        this(context, 0, onValueSetListener, i, columnDataArr);
    }

    public CustomPickerDialog(Context context, int i, OnValueSetListener onValueSetListener, int i2, ColumnData... columnDataArr) {
        super(context, i);
        this.mCallback = onValueSetListener;
        super.setButton(-1, context.getText(R.string.mc_yes), this);
        super.setButton(-2, context.getText(R.string.mc_cancel), (OnClickListener) null);
        this.mPicker = new CustomPicker(context, i2, columnDataArr);
        setView(this.mPicker);
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        if (this.mCallback != null) {
            this.mPicker.clearFocus();
            this.mCallback.onValueSet(this.mPicker, this.mPicker.getCurrentItems());
        }
        if (this.mPositiveBtnClickListener != null) {
            this.mPositiveBtnClickListener.onClick(dialogInterface, i);
        }
    }

    public void setButton(int i, CharSequence charSequence, OnClickListener onClickListener) {
        if (i == -1) {
            this.mPositiveBtnClickListener = onClickListener;
            onClickListener = this;
        }
        super.setButton(i, charSequence, onClickListener);
    }

    public void updateCurrentItems(int... iArr) {
        this.mPicker.updateCurrentItems(iArr);
    }

    public void setCurrentItem(int... iArr) {
        if (this.mPicker != null && iArr != null) {
            for (int i = 0; i < iArr.length; i++) {
                this.mPicker.setCurrentItem(i, iArr[i]);
            }
        }
    }

    public void setOnCurrentItemChangedListener(OnCurrentItemChangedListener onCurrentItemChangedListener) {
        if (this.mPicker != null) {
            this.mPicker.setOnCurrentItemChangedListener(onCurrentItemChangedListener);
        }
    }

    public Bundle onSaveInstanceState() {
        Bundle onSaveInstanceState = super.onSaveInstanceState();
        onSaveInstanceState.putIntArray(KEY_CURRENT_ITEMS, this.mPicker.getCurrentItems());
        return onSaveInstanceState;
    }

    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        this.mPicker.updateCurrentItems(bundle.getIntArray(KEY_CURRENT_ITEMS));
    }

    public void setTextColor(int i, int i2, int i3) {
        this.mPicker.setTextColor(i, i2, i3);
    }
}

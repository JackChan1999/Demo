package com.meizu.common.preference;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.DialerKeyListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.meizu.common.R;

public class EditPhoneNumberPreference extends EditTextPreference {
    private static final int CM_ACTIVATION = 1;
    private static final int CM_CONFIRM = 0;
    private static final String VALUE_OFF = "0";
    private static final String VALUE_ON = "1";
    private static final String VALUE_SEPARATOR = ":";
    private int mButtonClicked;
    private CharSequence mChangeNumberText;
    private boolean mChecked;
    private int mConfirmationMode;
    private Intent mContactListIntent;
    private ImageButton mContactPickButton;
    private OnFocusChangeListener mDialogFocusChangeListener;
    private OnDialogClosedListener mDialogOnClosedListener;
    private CharSequence mDisableText;
    private boolean mEmptyAllow;
    private CharSequence mEnableText;
    private String mEncodedText;
    private GetDefaultNumberListener mGetDefaultNumberListener;
    private Activity mParentActivity;
    private String mPhoneNumber;
    private int mPrefId;
    private CharSequence mSummaryOff;
    private CharSequence mSummaryOn;
    private TextWatcher watcher;

    public interface GetDefaultNumberListener {
        String onGetDefaultNumber(EditPhoneNumberPreference editPhoneNumberPreference);
    }

    public interface OnDialogClosedListener {
        void onDialogClosed(EditPhoneNumberPreference editPhoneNumberPreference, int i);
    }

    public EditPhoneNumberPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEmptyAllow = false;
        this.watcher = new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() != 0 || EditPhoneNumberPreference.this.isEmptyAllow()) {
                    enableButton(true);
                } else {
                    enableButton(false);
                }
            }

            private void enableButton(boolean z) {
                AlertDialog alertDialog = (AlertDialog) EditPhoneNumberPreference.this.getDialog();
                if (alertDialog == null) {
                    return;
                }
                if (EditPhoneNumberPreference.this.mConfirmationMode != 1) {
                    alertDialog.getButton(-1).setEnabled(z);
                } else if (EditPhoneNumberPreference.this.mChecked) {
                    alertDialog.getButton(-1).setEnabled(z);
                } else {
                    alertDialog.getButton(-3).setEnabled(z);
                }
            }
        };
        this.mEncodedText = null;
        setDialogLayoutResource(R.layout.mc_preference_editphonenumber);
        this.mContactListIntent = new Intent("android.intent.action.GET_CONTENT");
        this.mContactListIntent.setType("vnd.android.cursor.item/phone_v2");
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.EditPhoneNumberPreference, 0, R.style.Widget_MeizuCommon_EditPhoneNumberPreference);
        this.mEnableText = obtainStyledAttributes.getString(R.styleable.EditPhoneNumberPreference_mcEnableButtonText);
        this.mDisableText = obtainStyledAttributes.getString(R.styleable.EditPhoneNumberPreference_mcDisableButtonText);
        this.mChangeNumberText = obtainStyledAttributes.getString(R.styleable.EditPhoneNumberPreference_mcChangeNumButtonText);
        this.mConfirmationMode = obtainStyledAttributes.getInt(R.styleable.EditPhoneNumberPreference_mcConfirmMode, 0);
        obtainStyledAttributes.recycle();
        obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SwitchPreference, 0, 0);
        this.mSummaryOn = obtainStyledAttributes.getString(R.styleable.SwitchPreference_mcSummaryOn);
        this.mSummaryOff = obtainStyledAttributes.getString(R.styleable.SwitchPreference_mcSummaryOff);
        obtainStyledAttributes.recycle();
    }

    public EditPhoneNumberPreference(Context context) {
        this(context, null);
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        TextView textView = (TextView) view.findViewById(16908304);
        if (textView != null) {
            int i;
            CharSequence summary = this.mConfirmationMode == 1 ? this.mChecked ? this.mSummaryOn == null ? getSummary() : this.mSummaryOn : this.mSummaryOff == null ? getSummary() : this.mSummaryOff : getSummary();
            if (summary != null) {
                textView.setText(summary);
                i = 0;
            } else {
                i = 8;
            }
            if (i != textView.getVisibility()) {
                textView.setVisibility(i);
            }
        }
    }

    protected void onBindDialogView(View view) {
        this.mButtonClicked = -2;
        super.onBindDialogView(view);
        EditText editText = getEditText();
        if (editText != null) {
            if (this.mGetDefaultNumberListener != null) {
                String onGetDefaultNumber = this.mGetDefaultNumberListener.onGetDefaultNumber(this);
                if (onGetDefaultNumber != null) {
                    this.mPhoneNumber = onGetDefaultNumber;
                }
            }
            editText.setText(this.mPhoneNumber);
            if (editText.length() > 0) {
                editText.setSelection(0, editText.length());
            }
            editText.setMovementMethod(ArrowKeyMovementMethod.getInstance());
            editText.setKeyListener(DialerKeyListener.getInstance());
            editText.setOnFocusChangeListener(this.mDialogFocusChangeListener);
            editText.addTextChangedListener(this.watcher);
        }
    }

    protected void showDialog(Bundle bundle) {
        super.showDialog(bundle);
        if (TextUtils.isEmpty(this.mPhoneNumber) && getDialog() != null && !isEmptyAllow()) {
            ((AlertDialog) getDialog()).getButton(this.mConfirmationMode == 1 ? -3 : -1).setEnabled(false);
        }
    }

    public boolean isEmptyAllow() {
        return this.mEmptyAllow;
    }

    public void setEmptyAllow(boolean z) {
        this.mEmptyAllow = z;
    }

    protected void onAddEditTextToDialogView(View view, EditText editText) {
        ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.mc_edit_container);
        if (viewGroup != null) {
            viewGroup.addView(editText, -1, -2);
        }
    }

    protected void onPrepareDialogBuilder(Builder builder) {
        if (this.mConfirmationMode != 1) {
            return;
        }
        if (this.mChecked) {
            builder.setPositiveButton(this.mChangeNumberText, this);
            builder.setNeutralButton(this.mDisableText, this);
            return;
        }
        builder.setPositiveButton(null, null);
        builder.setNeutralButton(this.mEnableText, this);
    }

    public void setDialogOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.mDialogFocusChangeListener = onFocusChangeListener;
    }

    public void setDialogOnClosedListener(OnDialogClosedListener onDialogClosedListener) {
        this.mDialogOnClosedListener = onDialogClosedListener;
    }

    public void setParentActivity(Activity activity, int i) {
        this.mParentActivity = activity;
        this.mPrefId = i;
        this.mGetDefaultNumberListener = null;
    }

    public void setParentActivity(Activity activity, int i, GetDefaultNumberListener getDefaultNumberListener) {
        this.mParentActivity = activity;
        this.mPrefId = i;
        this.mGetDefaultNumberListener = getDefaultNumberListener;
    }

    public void onPickActivityResult(String str) {
        EditText editText = getEditText();
        if (editText != null) {
            editText.setText(str);
        }
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        boolean z = true;
        if (this.mConfirmationMode == 1 && i == -3) {
            if (isToggled()) {
                z = false;
            }
            setToggled(z);
        }
        this.mButtonClicked = i;
        super.onClick(dialogInterface, i);
    }

    protected void onDialogClosed(boolean z) {
        if (this.mButtonClicked == -1 || this.mButtonClicked == -3) {
            setPhoneNumber(getEditText().getText().toString());
            super.onDialogClosed(z);
            setText(getStringValue());
        } else {
            super.onDialogClosed(z);
        }
        if (this.mDialogOnClosedListener != null) {
            this.mDialogOnClosedListener.onDialogClosed(this, this.mButtonClicked);
        }
    }

    public boolean isToggled() {
        return this.mChecked;
    }

    public EditPhoneNumberPreference setToggled(boolean z) {
        this.mChecked = z;
        setText(getStringValue());
        notifyChanged();
        return this;
    }

    public String getPhoneNumber() {
        return PhoneNumberUtils.stripSeparators(this.mPhoneNumber);
    }

    protected String getRawPhoneNumber() {
        return this.mPhoneNumber;
    }

    public EditPhoneNumberPreference setPhoneNumber(String str) {
        this.mPhoneNumber = str;
        setText(getStringValue());
        notifyChanged();
        return this;
    }

    protected void onSetInitialValue(boolean z, Object obj) {
        if (z) {
            obj = getPersistedString(getStringValue());
        } else {
            String str = (String) obj;
        }
        setValueFromString(obj);
    }

    public boolean shouldDisableDependents() {
        if (this.mConfirmationMode == 1 && this.mEncodedText != null) {
            return this.mEncodedText.split(VALUE_SEPARATOR, 2)[0].equals("1");
        }
        if (TextUtils.isEmpty(this.mPhoneNumber) && this.mConfirmationMode == 0) {
            return true;
        }
        return false;
    }

    protected boolean persistString(String str) {
        this.mEncodedText = str;
        return super.persistString(str);
    }

    public EditPhoneNumberPreference setSummaryOn(CharSequence charSequence) {
        this.mSummaryOn = charSequence;
        if (isToggled()) {
            notifyChanged();
        }
        return this;
    }

    public EditPhoneNumberPreference setSummaryOn(int i) {
        return setSummaryOn(getContext().getString(i));
    }

    public CharSequence getSummaryOn() {
        return this.mSummaryOn;
    }

    public EditPhoneNumberPreference setSummaryOff(CharSequence charSequence) {
        this.mSummaryOff = charSequence;
        if (!isToggled()) {
            notifyChanged();
        }
        return this;
    }

    public EditPhoneNumberPreference setSummaryOff(int i) {
        return setSummaryOff(getContext().getString(i));
    }

    public CharSequence getSummaryOff() {
        return this.mSummaryOff;
    }

    protected void setValueFromString(String str) {
        if (this.mConfirmationMode == 1) {
            String[] split = str.split(VALUE_SEPARATOR, 2);
            setToggled(split[0].equals("1"));
            setPhoneNumber(split[1]);
            return;
        }
        setPhoneNumber(str);
    }

    protected String getStringValue() {
        if (this.mConfirmationMode != 1) {
            return getPhoneNumber();
        }
        return (isToggled() ? "1" : "0") + VALUE_SEPARATOR + getPhoneNumber();
    }

    public void showPhoneNumberDialog() {
        showDialog(null);
    }

    public EditPhoneNumberPreference setConfirmationMode(int i) {
        this.mConfirmationMode = i;
        notifyChanged();
        return this;
    }
}

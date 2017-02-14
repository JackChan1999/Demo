package com.meizu.common.preference;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.preference.Preference;
import android.preference.Preference.BaseSavedState;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import com.meizu.common.R;

public class EditTextPreference extends Preference implements OnAttachStateChangeListener, OnFocusChangeListener {
    private static EditTextPreference focusClass;
    private boolean isFocus;
    private EditText mEditText;
    private LayoutParams mParams;
    private String mText;

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        String text;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.text = parcel.readString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.text);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public EditTextPreference(Context context) {
        this(context, null);
    }

    public EditTextPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EditTextPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mText = "";
        this.mParams = null;
        this.isFocus = false;
        setLayoutResource(R.layout.mz_preference_edittext);
        this.mEditText = new EditText(context, attributeSet);
        initEditText(this.mEditText, context);
        setPersistent(false);
        this.mParams = new LayoutParams(-1, -1);
    }

    private void initEditText(EditText editText, Context context) {
        Resources resources = context.getResources();
        editText.setId(16908291);
        editText.setTextSize(16.0f);
        editText.setHintTextColor(resources.getColor(R.color.mz_edittext_preference_hint_text_color));
        editText.setBackgroundDrawable(null);
        editText.setPadding(0, 0, 0, 0);
        editText.setEnabled(true);
        editText.setFocusable(true);
        editText.setClickable(true);
        editText.setFocusableInTouchMode(true);
        editText.setIncludeFontPadding(false);
        editText.setOnFocusChangeListener(this);
        editText.addOnAttachStateChangeListener(this);
        editText.setImeOptions(33554432);
        editText.setPrivateImeOptions("com.meizu.input.candidateAlwaysVisible");
        if (!isPasswordInputType(editText.getInputType())) {
            editText.setSingleLine(true);
        }
    }

    private static boolean isPasswordInputType(int i) {
        int i2 = i & 4095;
        return i2 == 129 || i2 == 225 || i2 == 18;
    }

    protected View onCreateView(ViewGroup viewGroup) {
        ViewParent onCreateView = super.onCreateView(viewGroup);
        ViewParent parent = this.mEditText.getParent();
        if (parent != onCreateView) {
            if (parent != null) {
                ((ViewGroup) parent).removeView(this.mEditText);
            }
            ViewGroup viewGroup2 = (ViewGroup) onCreateView.findViewById(R.id.edittext_container);
            if (viewGroup2 != null) {
                viewGroup2.addView(this.mEditText, this.mParams);
            }
        }
        return onCreateView;
    }

    public void onBindView(View view) {
        super.onBindView(view);
    }

    public void onFocusChange(View view, boolean z) {
        this.isFocus = false;
        if (z) {
            focusClass = this;
            this.isFocus = true;
            return;
        }
        String obj = this.mEditText.getText().toString();
        if (callChangeListener(obj)) {
            setText(obj);
        }
    }

    public void setDialogTitle(int i) {
        setDialogTitle(getContext().getString(i));
    }

    public void setDialogTitle(CharSequence charSequence) {
        super.setTitle((String) charSequence);
    }

    public void setSummary(int i) {
        setSummary(getContext().getString(i));
    }

    public void setSummary(CharSequence charSequence) {
        setText((String) charSequence);
    }

    public void setText(String str) {
        boolean shouldDisableDependents = shouldDisableDependents();
        this.mText = str;
        persistString(str);
        if (!(this.mEditText == null || str == null || str.equals(this.mEditText.getText().toString()))) {
            this.mEditText.setText(str);
            if (this.mEditText.getText().length() > 0) {
                this.mEditText.setSelection(this.mEditText.getText().length());
            }
        }
        boolean shouldDisableDependents2 = shouldDisableDependents();
        if (shouldDisableDependents2 != shouldDisableDependents) {
            notifyDependencyChange(shouldDisableDependents2);
        }
    }

    public String getText() {
        String obj = this.mEditText.getText().toString();
        if (!obj.equals(this.mText) && callChangeListener(obj)) {
            setText(obj);
        }
        return this.mText;
    }

    protected Object onGetDefaultValue(TypedArray typedArray, int i) {
        return typedArray.getString(i);
    }

    protected void onSetInitialValue(boolean z, Object obj) {
        if (z) {
            obj = getPersistedString(this.mText);
        } else {
            String str = (String) obj;
        }
        setText(obj);
    }

    public boolean shouldDisableDependents() {
        return TextUtils.isEmpty(this.mText) || super.shouldDisableDependents();
    }

    public void onDependencyChanged(Preference preference, boolean z) {
        super.onDependencyChanged(preference, z);
        this.mEditText.setFocusableInTouchMode(!z);
        if (z) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(this.mEditText.getWindowToken(), 0);
            }
        }
    }

    public EditText getEditText() {
        return this.mEditText;
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        SavedState savedState = new SavedState(onSaveInstanceState);
        savedState.text = getText();
        return savedState;
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setText(savedState.text);
    }

    public void onViewAttachedToWindow(View view) {
        if (!TextUtils.equals(this.mEditText.getText(), this.mText)) {
            this.mEditText.setText(this.mText);
        }
        if (this.isFocus) {
            focusClass = this;
            this.mEditText.requestFocus();
        }
    }

    public void onViewDetachedFromWindow(View view) {
        if (focusClass == this) {
            this.isFocus = true;
            focusClass = null;
            return;
        }
        this.isFocus = false;
    }

    public void setAutoShowSoftInput(boolean z) {
        if (z) {
            this.mEditText.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) this.mEditText.getContext().getSystemService("input_method");
            if (inputMethodManager != null) {
                inputMethodManager.toggleSoftInput(0, 2);
            }
        }
    }
}

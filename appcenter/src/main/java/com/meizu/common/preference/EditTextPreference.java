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
import com.meizu.common.a.c;
import com.meizu.common.a.f;
import com.meizu.common.a.g;
import org.apache.commons.io.compress.zip.UnixStat;

public class EditTextPreference extends Preference implements OnAttachStateChangeListener, OnFocusChangeListener {
    private static EditTextPreference a;
    private EditText b;
    private String c;
    private LayoutParams d;
    private boolean e;

    private static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public /* synthetic */ Object createFromParcel(Parcel x0) {
                return a(x0);
            }

            public /* synthetic */ Object[] newArray(int x0) {
                return a(x0);
            }

            public SavedState a(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] a(int size) {
                return new SavedState[size];
            }
        };
        String a;

        public SavedState(Parcel source) {
            super(source);
            this.a = source.readString();
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(this.a);
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }
    }

    public EditTextPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditTextPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.c = "";
        this.d = null;
        this.e = false;
        setLayoutResource(g.mz_preference_edittext);
        this.b = new EditText(context, attrs);
        a(this.b, context);
        setPersistent(false);
        this.d = new LayoutParams(-1, -1);
    }

    private void a(EditText edit, Context context) {
        Resources res = context.getResources();
        edit.setId(16908291);
        edit.setTextSize(16.0f);
        edit.setHintTextColor(res.getColor(c.mz_edittext_preference_hint_text_color));
        edit.setBackgroundDrawable(null);
        edit.setPadding(0, 0, 0, 0);
        edit.setEnabled(true);
        edit.setFocusable(true);
        edit.setClickable(true);
        edit.setFocusableInTouchMode(true);
        edit.setIncludeFontPadding(false);
        edit.setOnFocusChangeListener(this);
        edit.addOnAttachStateChangeListener(this);
        edit.setImeOptions(33554432);
        edit.setPrivateImeOptions("com.meizu.input.candidateAlwaysVisible");
        if (!a(edit.getInputType())) {
            edit.setSingleLine(true);
        }
    }

    private static boolean a(int inputType) {
        int variation = inputType & UnixStat.PERM_MASK;
        return variation == 129 || variation == 225 || variation == 18;
    }

    protected View onCreateView(ViewGroup parent) {
        ViewParent layout = super.onCreateView(parent);
        ViewParent oldParent = this.b.getParent();
        if (oldParent != layout) {
            if (oldParent != null) {
                ((ViewGroup) oldParent).removeView(this.b);
            }
            ViewGroup container = (ViewGroup) layout.findViewById(f.edittext_container);
            if (container != null) {
                container.addView(this.b, this.d);
            }
        }
        return layout;
    }

    public void onBindView(View view) {
        super.onBindView(view);
    }

    public void onFocusChange(View v, boolean hasFocus) {
        this.e = false;
        if (hasFocus) {
            a = this;
            this.e = true;
            return;
        }
        String value = this.b.getText().toString();
        if (callChangeListener(value)) {
            a(value);
        }
    }

    public void setSummary(int summaryResId) {
        setSummary(getContext().getString(summaryResId));
    }

    public void setSummary(CharSequence summary) {
        a((String) summary);
    }

    public void a(String text) {
        boolean wasBlocking = shouldDisableDependents();
        this.c = text;
        persistString(text);
        if (!(this.b == null || text == null || text.equals(this.b.getText().toString()))) {
            this.b.setText(text);
            if (this.b.getText().length() > 0) {
                this.b.setSelection(this.b.getText().length());
            }
        }
        boolean isBlocking = shouldDisableDependents();
        if (isBlocking != wasBlocking) {
            notifyDependencyChange(isBlocking);
        }
    }

    public String a() {
        String value = this.b.getText().toString();
        if (!value.equals(this.c) && callChangeListener(value)) {
            a(value);
        }
        return this.c;
    }

    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        a(restoreValue ? getPersistedString(this.c) : (String) defaultValue);
    }

    public boolean shouldDisableDependents() {
        return TextUtils.isEmpty(this.c) || super.shouldDisableDependents();
    }

    public void onDependencyChanged(Preference dependency, boolean disableDependent) {
        super.onDependencyChanged(dependency, disableDependent);
        this.b.setFocusableInTouchMode(!disableDependent);
        if (disableDependent) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService("input_method");
            if (imm != null) {
                imm.hideSoftInputFromWindow(this.b.getWindowToken(), 0);
            }
        }
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        if (isPersistent()) {
            return superState;
        }
        SavedState myState = new SavedState(superState);
        myState.a = a();
        return myState;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        if (state == null || !state.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());
        a(myState.a);
    }

    public void onViewAttachedToWindow(View v) {
        if (!TextUtils.equals(this.b.getText(), this.c)) {
            this.b.setText(this.c);
        }
        if (this.e) {
            a = this;
            this.b.requestFocus();
        }
    }

    public void onViewDetachedFromWindow(View v) {
        if (a == this) {
            this.e = true;
            a = null;
            return;
        }
        this.e = false;
    }
}

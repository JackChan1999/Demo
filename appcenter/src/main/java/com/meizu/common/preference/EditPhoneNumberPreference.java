package com.meizu.common.preference;

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
import android.widget.TextView;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.common.a.f;
import com.meizu.common.a.g;
import com.meizu.common.a.i;
import com.meizu.common.a.j;

public class EditPhoneNumberPreference extends EditTextPreference {
    private int a;
    private OnFocusChangeListener b;
    private b c;
    private a d;
    private Intent e;
    private CharSequence f;
    private CharSequence g;
    private CharSequence h;
    private CharSequence i;
    private CharSequence j;
    private int k;
    private String l;
    private boolean m;
    private boolean n = false;
    private TextWatcher o = new TextWatcher(this) {
        final /* synthetic */ EditPhoneNumberPreference a;

        {
            this.a = r1;
        }

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() != 0 || this.a.a()) {
                a(true);
            } else {
                a(false);
            }
        }

        private void a(boolean enable) {
            AlertDialog dialog = (AlertDialog) this.a.getDialog();
            if (dialog == null) {
                return;
            }
            if (this.a.a != 1) {
                dialog.getButton(-1).setEnabled(enable);
            } else if (this.a.m) {
                dialog.getButton(-1).setEnabled(enable);
            } else {
                dialog.getButton(-3).setEnabled(enable);
            }
        }
    };
    private String p = null;

    public interface a {
        String a(EditPhoneNumberPreference editPhoneNumberPreference);
    }

    public interface b {
        void a(EditPhoneNumberPreference editPhoneNumberPreference, int i);
    }

    public EditPhoneNumberPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(g.mc_preference_editphonenumber);
        this.e = new Intent("android.intent.action.GET_CONTENT");
        this.e.setType("vnd.android.cursor.item/phone_v2");
        TypedArray a = context.obtainStyledAttributes(attrs, j.EditPhoneNumberPreference, 0, i.Widget_MeizuCommon_EditPhoneNumberPreference);
        this.f = a.getString(j.EditPhoneNumberPreference_mcEnableButtonText);
        this.g = a.getString(j.EditPhoneNumberPreference_mcDisableButtonText);
        this.h = a.getString(j.EditPhoneNumberPreference_mcChangeNumButtonText);
        this.a = a.getInt(j.EditPhoneNumberPreference_mcConfirmMode, 0);
        a.recycle();
        a = context.obtainStyledAttributes(attrs, j.SwitchPreference, 0, 0);
        this.i = a.getString(j.SwitchPreference_mcSummaryOn);
        this.j = a.getString(j.SwitchPreference_mcSummaryOff);
        a.recycle();
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        TextView summaryView = (TextView) view.findViewById(16908304);
        if (summaryView != null) {
            int vis;
            CharSequence sum = this.a == 1 ? this.m ? this.i == null ? getSummary() : this.i : this.j == null ? getSummary() : this.j : getSummary();
            if (sum != null) {
                summaryView.setText(sum);
                vis = 0;
            } else {
                vis = 8;
            }
            if (vis != summaryView.getVisibility()) {
                summaryView.setVisibility(vis);
            }
        }
    }

    protected void onBindDialogView(View view) {
        this.k = -2;
        super.onBindDialogView(view);
        EditText editText = getEditText();
        if (editText != null) {
            if (this.d != null) {
                String defaultNumber = this.d.a(this);
                if (defaultNumber != null) {
                    this.l = defaultNumber;
                }
            }
            editText.setText(this.l);
            if (editText.length() > 0) {
                editText.setSelection(0, editText.length());
            }
            editText.setMovementMethod(ArrowKeyMovementMethod.getInstance());
            editText.setKeyListener(DialerKeyListener.getInstance());
            editText.setOnFocusChangeListener(this.b);
            editText.addTextChangedListener(this.o);
        }
    }

    protected void showDialog(Bundle state) {
        super.showDialog(state);
        if (TextUtils.isEmpty(this.l) && getDialog() != null && !a()) {
            ((AlertDialog) getDialog()).getButton(this.a == 1 ? -3 : -1).setEnabled(false);
        }
    }

    public boolean a() {
        return this.n;
    }

    protected void onAddEditTextToDialogView(View dialogView, EditText editText) {
        ViewGroup container = (ViewGroup) dialogView.findViewById(f.mc_edit_container);
        if (container != null) {
            container.addView(editText, -1, -2);
        }
    }

    protected void onPrepareDialogBuilder(Builder builder) {
        if (this.a != 1) {
            return;
        }
        if (this.m) {
            builder.setPositiveButton(this.h, this);
            builder.setNeutralButton(this.g, this);
            return;
        }
        builder.setPositiveButton(null, null);
        builder.setNeutralButton(this.f, this);
    }

    public void onClick(DialogInterface dialog, int which) {
        boolean z = true;
        if (this.a == 1 && which == -3) {
            if (b()) {
                z = false;
            }
            a(z);
        }
        this.k = which;
        super.onClick(dialog, which);
    }

    protected void onDialogClosed(boolean positiveResult) {
        if (this.k == -1 || this.k == -3) {
            a(getEditText().getText().toString());
            super.onDialogClosed(positiveResult);
            setText(d());
        } else {
            super.onDialogClosed(positiveResult);
        }
        if (this.c != null) {
            this.c.a(this, this.k);
        }
    }

    public boolean b() {
        return this.m;
    }

    public EditPhoneNumberPreference a(boolean checked) {
        this.m = checked;
        setText(d());
        notifyChanged();
        return this;
    }

    public String c() {
        return PhoneNumberUtils.stripSeparators(this.l);
    }

    public EditPhoneNumberPreference a(String number) {
        this.l = number;
        setText(d());
        notifyChanged();
        return this;
    }

    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        b(restoreValue ? getPersistedString(d()) : (String) defaultValue);
    }

    public boolean shouldDisableDependents() {
        if (this.a == 1 && this.p != null) {
            return this.p.split(":", 2)[0].equals(PushConstants.CLICK_TYPE_ACTIVITY);
        }
        boolean shouldDisable = TextUtils.isEmpty(this.l) && this.a == 0;
        return shouldDisable;
    }

    protected boolean persistString(String value) {
        this.p = value;
        return super.persistString(value);
    }

    protected void b(String value) {
        if (this.a == 1) {
            String[] inValues = value.split(":", 2);
            a(inValues[0].equals(PushConstants.CLICK_TYPE_ACTIVITY));
            a(inValues[1]);
            return;
        }
        a(value);
    }

    protected String d() {
        if (this.a != 1) {
            return c();
        }
        return (b() ? PushConstants.CLICK_TYPE_ACTIVITY : "0") + ":" + c();
    }
}

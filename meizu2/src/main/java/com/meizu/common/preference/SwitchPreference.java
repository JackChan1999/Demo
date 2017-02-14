package com.meizu.common.preference;

import android.content.Context;
import android.os.Build.VERSION;
import android.preference.Preference;
import android.preference.TwoStatePreference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.meizu.common.R;
import com.meizu.common.widget.MzContactsContract.MzGroups;
import com.meizu.common.widget.Switch;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SwitchPreference extends TwoStatePreference {
    private static Method sOnPreferenceChanged;
    private static Field sRecycle;
    private final Listener mListener;
    private CharSequence mSwitchOff;
    private CharSequence mSwitchOn;
    boolean mUseAnim;

    class Listener implements OnCheckedChangeListener {
        private Listener() {
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (SwitchPreference.this.callChangeListener(Boolean.valueOf(z))) {
                SwitchPreference.this.setChecked(z);
                SwitchPreference.this.performPreferenceChanged();
                return;
            }
            compoundButton.setChecked(!z);
        }
    }

    public SwitchPreference(Context context, AttributeSet attributeSet, int i) {
        boolean z = true;
        super(context, attributeSet, i);
        this.mListener = new Listener();
        this.mUseAnim = true;
        try {
            if (sRecycle == null) {
                if (VERSION.SDK_INT >= 19) {
                    sRecycle = Preference.class.getDeclaredField("mCanRecycleLayout");
                } else {
                    sRecycle = Preference.class.getDeclaredField("mHasSpecifiedLayout");
                }
                sRecycle.setAccessible(true);
            }
            Field field = sRecycle;
            if (VERSION.SDK_INT < 19) {
                z = false;
            }
            field.set(this, Boolean.valueOf(z));
        } catch (Exception e) {
            e.printStackTrace();
        }
        context.obtainStyledAttributes(attributeSet, R.styleable.SwitchPreference, i, 0).recycle();
    }

    public SwitchPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.MeizuCommon_SwitchPreferenceStyle);
    }

    public SwitchPreference(Context context) {
        this(context, null);
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        View findViewById = view.findViewById(R.id.switchWidget);
        if (findViewById != null && (findViewById instanceof Checkable)) {
            if (findViewById instanceof Switch) {
                CharSequence charSequence;
                Switch switchR = (Switch) findViewById;
                switchR.setOnCheckedChangeListener(null);
                View findViewById2 = view.findViewById(16908312);
                if (findViewById2 != null) {
                    if (isChecked()) {
                        charSequence = ((Switch) findViewById).switchOn;
                    } else {
                        charSequence = ((Switch) findViewById).switchOff;
                    }
                    findViewById2.setContentDescription(charSequence);
                }
                charSequence = new String();
                if (getTitle() != null) {
                    charSequence = charSequence + getTitle() + MzGroups.GROUP_SPLIT_MARK_EXTRA;
                }
                if (getSummary() != null) {
                    charSequence = charSequence + getSummary() + MzGroups.GROUP_SPLIT_MARK_EXTRA;
                }
                switchR.setContentDescription(charSequence);
            }
            if (findViewById instanceof Switch) {
                Switch switchR2 = (Switch) findViewById;
                switchR2.setChecked(isChecked(), this.mUseAnim);
                this.mUseAnim = true;
                switchR2.setOnCheckedChangeListener(this.mListener);
            } else {
                ((Checkable) findViewById).setChecked(isChecked());
            }
        }
        syncSummaryView(view);
    }

    public void setChecked(boolean z) {
        setChecked(z, true);
    }

    public void setChecked(boolean z, boolean z2) {
        super.setChecked(z);
        this.mUseAnim = z2;
    }

    void syncSummaryView(View view) {
        int i = 0;
        TextView textView = (TextView) view.findViewById(16908304);
        if (textView != null) {
            int i2;
            CharSequence summary = getSummary();
            if (TextUtils.isEmpty(summary)) {
                i2 = 1;
            } else {
                textView.setText(summary);
                i2 = 0;
            }
            if (i2 != 0) {
                i = 8;
            }
            if (i != textView.getVisibility()) {
                textView.setVisibility(i);
            }
        }
    }

    public void setSwitchTextOn(CharSequence charSequence) {
        this.mSwitchOn = charSequence;
        notifyChanged();
    }

    public void setSwitchTextOff(CharSequence charSequence) {
        this.mSwitchOff = charSequence;
        notifyChanged();
    }

    public void setSwitchTextOn(int i) {
        setSwitchTextOn(getContext().getString(i));
    }

    public void setSwitchTextOff(int i) {
        setSwitchTextOff(getContext().getString(i));
    }

    public CharSequence getSwitchTextOn() {
        return this.mSwitchOn;
    }

    public CharSequence getSwitchTextOff() {
        return this.mSwitchOff;
    }

    private void performPreferenceChanged() {
        try {
            if (sOnPreferenceChanged == null) {
                sOnPreferenceChanged = SwitchPreference.class.getMethod("onPreferenceChange", new Class[0]);
                sOnPreferenceChanged.setAccessible(true);
            }
            sOnPreferenceChanged.invoke(this, new Object[0]);
        } catch (Exception e) {
        }
    }
}

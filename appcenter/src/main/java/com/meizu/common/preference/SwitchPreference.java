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
import com.meizu.common.a.b;
import com.meizu.common.a.f;
import com.meizu.common.a.j;
import com.meizu.common.widget.Switch;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SwitchPreference extends TwoStatePreference {
    private static Field c;
    private static Method d;
    boolean a;
    private final a b;

    private class a implements OnCheckedChangeListener {
        final /* synthetic */ SwitchPreference a;

        private a(SwitchPreference switchPreference) {
            this.a = switchPreference;
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (this.a.callChangeListener(Boolean.valueOf(isChecked))) {
                this.a.setChecked(isChecked);
                this.a.a();
                return;
            }
            buttonView.setChecked(!isChecked);
        }
    }

    public SwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        boolean z = true;
        super(context, attrs, defStyleAttr);
        this.b = new a();
        this.a = true;
        try {
            if (c == null) {
                if (VERSION.SDK_INT >= 19) {
                    c = Preference.class.getDeclaredField("mCanRecycleLayout");
                } else {
                    c = Preference.class.getDeclaredField("mHasSpecifiedLayout");
                }
                c.setAccessible(true);
            }
            Field field = c;
            if (VERSION.SDK_INT < 19) {
                z = false;
            }
            field.set(this, Boolean.valueOf(z));
        } catch (Exception e) {
            e.printStackTrace();
        }
        context.obtainStyledAttributes(attrs, j.SwitchPreference, defStyleAttr, 0).recycle();
    }

    public SwitchPreference(Context context, AttributeSet attrs) {
        this(context, attrs, b.MeizuCommon_SwitchPreferenceStyle);
    }

    public SwitchPreference(Context context) {
        this(context, null);
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        View checkableView = view.findViewById(f.switchWidget);
        if (checkableView != null && (checkableView instanceof Checkable)) {
            Switch switchView;
            if (checkableView instanceof Switch) {
                switchView = (Switch) checkableView;
                switchView.setOnCheckedChangeListener(null);
                View widgetLayout = view.findViewById(16908312);
                if (widgetLayout != null) {
                    CharSequence charSequence;
                    if (isChecked()) {
                        charSequence = ((Switch) checkableView).a;
                    } else {
                        charSequence = ((Switch) checkableView).b;
                    }
                    widgetLayout.setContentDescription(charSequence);
                }
                String contentDescription = new String();
                if (getTitle() != null) {
                    contentDescription = contentDescription + getTitle() + ",";
                }
                if (getSummary() != null) {
                    contentDescription = contentDescription + getSummary() + ",";
                }
                switchView.setContentDescription(contentDescription);
            }
            if (checkableView instanceof Switch) {
                switchView = (Switch) checkableView;
                switchView.setChecked(isChecked(), this.a);
                this.a = true;
                switchView.setOnCheckedChangeListener(this.b);
            } else {
                ((Checkable) checkableView).setChecked(isChecked());
            }
        }
        a(view);
    }

    public void setChecked(boolean checked) {
        a(checked, true);
    }

    public void a(boolean checked, boolean useAnim) {
        super.setChecked(checked);
        this.a = useAnim;
    }

    void a(View view) {
        TextView summaryView = (TextView) view.findViewById(16908304);
        if (summaryView != null) {
            boolean useDefaultSummary = true;
            if (1 != null) {
                CharSequence summary = getSummary();
                if (!TextUtils.isEmpty(summary)) {
                    summaryView.setText(summary);
                    useDefaultSummary = false;
                }
            }
            int newVisibility = 8;
            if (!useDefaultSummary) {
                newVisibility = 0;
            }
            if (newVisibility != summaryView.getVisibility()) {
                summaryView.setVisibility(newVisibility);
            }
        }
    }

    private void a() {
        try {
            if (d == null) {
                d = SwitchPreference.class.getMethod("onPreferenceChange", new Class[0]);
                d.setAccessible(true);
            }
            d.invoke(this, new Object[0]);
        } catch (Exception e) {
        }
    }
}

package com.meizu.common.preference;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceScreen;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import com.meizu.common.a.f;
import com.meizu.common.a.g;
import java.lang.reflect.Method;

public class CheckBoxPreferenceMz extends CheckBoxPreference {
    private static Method g;
    private View a;
    private View b;
    private boolean c = true;
    private boolean d = true;
    private OnClickListener e;
    private OnLongClickListener f;

    public CheckBoxPreferenceMz(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(g.mz_preference_checkbox);
    }

    public CheckBoxPreferenceMz(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutResource(g.mz_preference_checkbox);
    }

    protected View onCreateView(ViewGroup parent) {
        View layout = super.onCreateView(parent);
        this.a = layout.findViewById(f.mz_preference_text_layout);
        if (this.e != null) {
            this.a.setOnClickListener(this.e);
        }
        if (this.f != null) {
            this.a.setOnLongClickListener(this.f);
        }
        this.b = layout.findViewById(16908312);
        this.b.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ CheckBoxPreferenceMz a;

            {
                this.a = r1;
            }

            public void onClick(View v) {
                this.a.a();
            }
        });
        return layout;
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        if (isEnabled() && !this.c) {
            a(this.a, this.c);
        }
        if (isEnabled() && !this.d) {
            a(this.b, this.d);
        }
    }

    private void a(View v, boolean enabled) {
        v.setEnabled(enabled);
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = vg.getChildCount() - 1; i >= 0; i--) {
                a(vg.getChildAt(i), enabled);
            }
        }
    }

    private void a() {
        try {
            if (g == null) {
                g = CheckBoxPreference.class.getMethod("performClick", new Class[]{PreferenceScreen.class});
                g.setAccessible(true);
            }
            g.invoke(this, new Object[]{null});
        } catch (Exception e) {
        }
    }
}

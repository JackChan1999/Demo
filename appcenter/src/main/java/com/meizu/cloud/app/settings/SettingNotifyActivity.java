package com.meizu.cloud.app.settings;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.support.v7.app.AppCompatPreferenceActivity;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.b.a.l;
import com.meizu.cloud.statistics.b;
import com.meizu.common.preference.SwitchPreference;

public class SettingNotifyActivity extends AppCompatPreferenceActivity implements OnPreferenceChangeListener {
    private SwitchPreference a;
    private SwitchPreference b;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setOverScrollMode(2);
        addPreferencesFromResource(l.settings_notify);
        this.a = (SwitchPreference) findPreference("update_notify");
        this.a.setOnPreferenceChangeListener(this);
        if (x.b((Context) this)) {
            ((PreferenceScreen) findPreference("notification")).removePreference(this.a);
        }
        this.b = (SwitchPreference) findPreference("recommend_notify");
        this.b.setOnPreferenceChangeListener(this);
    }

    protected void onResume() {
        super.onResume();
        this.a.setChecked(a.a((Context) this).a());
        this.b.setChecked(a.a((Context) this).b());
    }

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        a(true);
    }

    public void a(boolean on) {
        Window win = getWindow();
        LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= 67108864;
        } else {
            winParams.flags &= -67108865;
        }
        win.setAttributes(winParams);
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if ("update_notify".equals(preference.getKey())) {
            if (a.a((Context) this).a()) {
                a.a((Context) this).a(false);
                return true;
            }
            a.a((Context) this).a(true);
            return true;
        } else if (!"recommend_notify".equals(preference.getKey())) {
            return false;
        } else {
            if (a.a((Context) this).b()) {
                a.a((Context) this).b(false);
                return true;
            }
            a.a((Context) this).b(true);
            return true;
        }
    }

    protected void onStart() {
        super.onStart();
        b.a().a(getClass().getSimpleName());
    }

    protected void onStop() {
        super.onStop();
        b.a().a(getClass().getSimpleName(), null);
    }
}

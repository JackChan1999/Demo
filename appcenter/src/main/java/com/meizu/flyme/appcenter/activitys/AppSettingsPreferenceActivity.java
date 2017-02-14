package com.meizu.flyme.appcenter.activitys;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import com.meizu.cloud.app.settings.SettingsPreferenceActivity;
import com.meizu.cloud.app.settings.a;
import com.meizu.cloud.statistics.b;
import com.meizu.common.preference.SwitchPreference;
import com.meizu.flyme.appcenter.desktopplugin.b.c;

public class AppSettingsPreferenceActivity extends SettingsPreferenceActivity {
    private SwitchPreference e;
    private boolean f = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.e = (SwitchPreference) findPreference("plugin_swicher");
        this.e.setOnPreferenceChangeListener(this);
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (!preference.getKey().equals("plugin_swicher")) {
            return super.onPreferenceChange(preference, newValue);
        }
        this.f = true;
        if (a.a((Context) this).g()) {
            a.a((Context) this).g(false);
            return true;
        }
        a.a((Context) this).g(true);
        return true;
    }

    protected void onResume() {
        super.onResume();
        this.e.setChecked(a.a((Context) this).g());
    }

    protected void onStop() {
        super.onStop();
        if (this.f) {
            c.a((Context) this, a.a((Context) this).g());
        }
        if (this.f && this.f != a.a((Context) this).g() && !a.a((Context) this).g()) {
            b.a().a("jxcj_setting", "plugin", null);
            Log.w("tan", "close");
        }
    }
}

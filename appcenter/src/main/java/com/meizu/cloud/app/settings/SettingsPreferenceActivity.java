package com.meizu.cloud.app.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.support.v7.app.AppCompatPreferenceActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.downlad.a;
import com.meizu.cloud.app.request.model.BaseServerAppInfo.Columns;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.b.a.l;
import com.meizu.cloud.statistics.b;
import com.meizu.common.preference.SwitchPreference;

public class SettingsPreferenceActivity extends AppCompatPreferenceActivity implements OnPreferenceChangeListener {
    private static final String e = SettingsPreferenceActivity.class.getSimpleName();
    protected SwitchPreference a;
    protected PreferenceScreen b;
    protected PreferenceScreen c;
    protected PreferenceScreen d;
    private SwitchPreference f;
    private SwitchPreference g;
    private SwitchPreference h;
    private Thread i;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setOverScrollMode(2);
        addPreferencesFromResource(l.settings);
        a().b(true);
        a().a(true);
        if (x.a((Context) this)) {
            this.f = (SwitchPreference) findPreference("auto_downlad");
            if (this.f != null) {
                this.f.setChecked(a.a((Context) this).c());
                this.f.setOnPreferenceChangeListener(this);
            }
        }
        if (x.a((Context) this)) {
            this.g = (SwitchPreference) findPreference(Columns.AUTO_INSTALL);
            if (this.g != null) {
                this.g.setChecked(a.a((Context) this).d());
                if (!a.a((Context) this).c()) {
                    this.g.setEnabled(false);
                }
                this.g.setOnPreferenceChangeListener(this);
            }
        }
        this.h = (SwitchPreference) findPreference("delete_apk");
        if (this.h != null) {
            this.h.setChecked(a.a((Context) this).e());
            this.h.setSummary(getString(i.delete_apk_subtitle, new Object[]{a.a((Context) this)}));
            this.h.setOnPreferenceChangeListener(this);
        }
        if (x.a((Context) this)) {
            this.a = (SwitchPreference) findPreference("filter_app");
            if (this.a != null) {
                this.a.setOnPreferenceChangeListener(this);
            }
        }
        this.b = (PreferenceScreen) findPreference("about");
        if (this.b != null) {
            this.b.setIntent(new Intent(this, SettingAboutActivity.class));
        }
        if (x.a((Context) this)) {
            this.c = (PreferenceScreen) findPreference("auto_update_exclude");
            if (this.c != null) {
                Intent intent = new Intent();
                intent.setAction("com.meizu.cloud.app.update.exclude");
                this.c.setIntent(intent);
            }
        }
        this.d = (PreferenceScreen) findPreference("notification");
        if (this.d != null) {
            intent = new Intent();
            if (x.a((Context) this)) {
                intent.setAction("com.meizu.flyme.appcenter.setting.notify");
            } else {
                intent.setAction("com.meizu.flyme.gamecenter.setting.notify");
            }
            this.d.setIntent(intent);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onStart() {
        super.onStart();
        b.a().a("myapp_setting");
    }

    protected void onStop() {
        super.onStop();
        b.a().a("myapp_setting", null);
    }

    protected void onDestroy() {
        if (this.i != null && this.i.isAlive()) {
            this.i.interrupt();
            this.i = null;
        }
        super.onDestroy();
    }

    public boolean onPreferenceChange(Preference preference, Object o) {
        if ("auto_downlad".equals(preference.getKey())) {
            if (a.a((Context) this).c()) {
                a.a((Context) this).c(false);
                this.g.setEnabled(false);
                return true;
            }
            a.a((Context) this).c(true);
            this.g.setEnabled(true);
            return true;
        } else if (Columns.AUTO_INSTALL.equals(preference.getKey())) {
            if (a.a((Context) this).d()) {
                a.a((Context) this).d(false);
                return true;
            }
            a.a((Context) this).d(true);
            return true;
        } else if ("delete_apk".equals(preference.getKey())) {
            if (a.a((Context) this).e()) {
                a.a((Context) this).e(false);
                return true;
            }
            a.a((Context) this).e(true);
            return true;
        } else if (!"filter_app".equals(preference.getKey())) {
            return false;
        } else {
            if (a.a((Context) this).f()) {
                a.a((Context) this).f(false);
                return true;
            }
            a.a((Context) this).f(true);
            return true;
        }
    }
}
